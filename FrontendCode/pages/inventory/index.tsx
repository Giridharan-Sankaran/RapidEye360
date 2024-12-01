import React, { useState, useEffect, useContext } from "react";
import {
  View,
  Text,
  FlatList,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Alert,
} from "react-native";
import { globalStyles } from "../styles";
import { AuthContext } from "../../context/AuthContext";
import CheckBox from "expo-checkbox";
import AddProductModal from "./addProduct";
import axios from "axios";
import api from "../api";
import SelectFilters from "./selectFilters";

const InventoryPage: React.FC = () => {
  const { userDetails } = useContext(AuthContext);

  const [searchQuery, setSearchQuery] = useState("");
  const [filteredData, setFilteredData] = useState<any[]>([]);
  const [products, setProducts] = useState<any[]>([]);
  const [selectedItems, setSelectedItems] = useState<any[]>([]);
  const [selectAll, setSelectAll] = useState<boolean>(false);
  const [addProductModalVisible, setAddProductModalVisible] = useState(false);
  const [filterModalVisible, setFilterModalVisible] = useState(false);
  const [categories, setCategories] = useState<any[]>([]);
  const [brands, setBrands] = useState<any[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [entriesPerPage, setEntriesPerPage] = useState(15);
  const [acknowledgmentMessage, setAcknowledgmentMessage] = useState<string>("");


  const fetchItems = async () => {
    api
      .get("/products/all")
      .then((response) => {
        const productsData: any[] = response.data;
        setProducts(productsData);
        setFilteredData(productsData);
        const uniqueCategories = Array.from(
          new Set(productsData.map((product: any) => product.category))
        );
        const uniqueBrands = Array.from(
          new Set(productsData.map((product: any) => product.brand))
        );
        setCategories(uniqueCategories);
        setBrands(uniqueBrands);
      })
      .catch((error) => {
        Alert.alert("Error fetching products:", error);
      });
  };

  const onFilter = (filter: any) => {
    let link = "";
    const queryParams: any = {};

    if (
      filter.attribute === "Category" ||
      filter.attribute === "Brand" ||
      filter.attribute === "UPCID"
    ) {
      link = "/products/" + filter.attribute + "/" + filter.value;
    } else if (filter.attribute === "expiryDate") {
      link = "/products/" + filter.attribute;
      queryParams.dateOfExpiry = new Date(filter.value); // Add dateOfExpiry to query parameters
    } else if (filter.attribute === "procurementDate") {
      link = "/products/" + filter.attribute;
      queryParams.dateOfProcurement = new Date(filter.value); // Add dateOfProcurement to query parameters
    }

    api
      .get(link, { params: queryParams })
      .then((response) => {
        const productsData: any[] = response.data;
        filter.attribute == "UPCID"
          ? setProducts([productsData])
          : setProducts(productsData);
        filter.attribute == "UPCID"
          ? setFilteredData([productsData])
          : setFilteredData(productsData);
      })
      .catch((error) => {
        Alert.alert("Error fetching products:", error);
      });
  };

  useEffect(() => {
    fetchItems();
  }, []);

  const handleSearch = (text: string) => {
    setSearchQuery(text);
    const filtered = products.filter((item) =>
      item.name.toLowerCase().includes(text.toLowerCase())
    );
    setFilteredData(filtered);
  };

  const toggleItemSelection = (itemId: any, aisleNumber: any) => {
    const itemIndex = selectedItems.findIndex((item) => item.upcid === itemId);
    if (itemIndex !== -1) {
      setSelectedItems((prevItems) =>
        prevItems.filter((item) => item.upcid !== itemId)
      );
    } else {
      setSelectedItems((prevItems) => [
        ...prevItems,
        { upcid: itemId, aisleNumber },
      ]);
    }
  };

  const toggleSelectAll = () => {
    setSelectAll((prevSelectAll) => !prevSelectAll);
    if (!selectAll) {
      setSelectedItems([...filteredData]); // Select all items
    } else {
      setSelectedItems([]); // Deselect all items
    }
  };

  const handleDelete = async () => {
    try {
      // Send delete request for each selected item
      await Promise.all(selectedItems.map(async (item) => {
        await api.delete(`/products/remove/${item.upcid}/${item.aisleNumber}`);
      }));
      // Update filtered data to remove deleted items
      const updatedData = filteredData.filter(
        (item) => !selectedItems.some((selected) => selected.upcid === item.upcid)
      );
      setFilteredData(updatedData);
      // Show acknowledgment message
      setAcknowledgmentMessage("Item(s) deleted successfully");
      // Clear selected items
      setSelectedItems([]);
      // Hide acknowledgment message after 3 seconds
      setTimeout(() => {
        setAcknowledgmentMessage("");
      }, 3000);
    } catch (error) {
      console.error("Error deleting item:", error);
      Alert.alert("Error", "Failed to delete item");
    }
  };
  
  

  function truncateText(text: string) {
    const maxLength = 15;
    if (text && text.length > maxLength) {
      return text.substring(0, maxLength - 3) + "...";
    } else {
      return text ?? "...";
    }
  }

  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, "0"); // Adding 1 because months are zero-based
    const day = date.getDate().toString().padStart(2, "0");
    const readableFormat = `${year}-${month}-${day}`;
    return readableFormat;
  };

  const totalPages = Math.ceil(filteredData.length / entriesPerPage);
  const handlePagination = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };
  const paginationControls = Array.from(Array(totalPages).keys()).map(
    (page) => (
      <TouchableOpacity key={page} onPress={() => handlePagination(page + 1)}>
        <Text>{page + 1}</Text>
      </TouchableOpacity>
    )
  );

  const startIndex = (currentPage - 1) * entriesPerPage;
  const endIndex = currentPage * entriesPerPage;
  const itemsForCurrentPage = filteredData.slice(startIndex, endIndex);

  const handleNextPage = () => {
    setCurrentPage((prevPage) => Math.min(prevPage + 1, totalPages));
  };

  const handlePrevPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const renderItem = ({ item }: { item: any }) => (
    <View style={globalStyles.invrow}>
      <CheckBox
        style={globalStyles.checkbox}
        value={selectedItems.some((selected) => selected.upcid === item.upcid)}
        onValueChange={() => toggleItemSelection(item.upcid, item.aisleNumber)}
        color={
          selectedItems.some((selected) => selected.upcid === item.upcid)
            ? "#E68A5C"
            : "#ccc"
        }
      />
      <Text style={globalStyles.invcell}>{item.upcid}</Text>
      <Text style={globalStyles.invcell}>{truncateText(item.name)}</Text>
      <Text style={globalStyles.invcell}>{truncateText(item.brand)}</Text>
      <Text style={globalStyles.invcell}>{truncateText(item.category)}</Text>
      <Text style={globalStyles.invcell}>{item.price}</Text>
      <Text style={globalStyles.invcell}>{item.quantity}</Text>
      <Text style={globalStyles.invcell}>
        {formatDate(item.dateOfProcurement)}
      </Text>
      <Text style={globalStyles.invcell}>{formatDate(item.dateOfExpiry)}</Text>
    </View>
  );

// const acknowledgmentMessage = "Record Deleted Successfully.";

  return (
    <View style={globalStyles.invmain}>
      <View style={globalStyles.invtable}>
        <ScrollView horizontal={true}>
          <View style={globalStyles.invcontainer}>
          <View style={globalStyles.acknowledgmentContainer}>
            {acknowledgmentMessage ? (
            <Text style={globalStyles.acknowledgmentText}>{acknowledgmentMessage}</Text>
            ) : null}
            </View>
            <View style={globalStyles.invheaderRow}>
              <CheckBox
                style={globalStyles.checkbox}
                value={selectAll}
                onValueChange={() => toggleSelectAll()}
                color={selectAll ? "#E68A5C" : "#ccc"}
              />
              <Text style={globalStyles.invheader}>UPCID</Text>
              <Text style={globalStyles.invheader}>Product</Text>
              <Text style={globalStyles.invheader}>Brand</Text>
              <Text style={globalStyles.invheader}>Category</Text>
              <Text style={globalStyles.invheader}>Price</Text>
              <Text style={globalStyles.invheader}>Quantity</Text>
              <Text style={globalStyles.invheader}>Procurement Date</Text>
              <Text style={globalStyles.invheader}>Expiry Date</Text>
            </View>
            <FlatList
              data={itemsForCurrentPage}
              renderItem={renderItem}
              keyExtractor={(item) => item.upcid}
              contentContainerStyle={{ paddingBottom: 20 }}
            />
          </View>
        </ScrollView>
        {filteredData.length && (
          <View style={paginationFrontend.paginationContainer}>
            <TouchableOpacity
              onPress={handlePrevPage}
              disabled={currentPage === 1}
            >
              <Text
                style={[
                  paginationFrontend.paginationButton,
                  currentPage === 1 && paginationFrontend.disabledButtonText,
                ]}
              >
                Previous
              </Text>
            </TouchableOpacity>
            <Text style={paginationFrontend.pageNumberText}>
              {currentPage} / {totalPages}
            </Text>
            <TouchableOpacity
              onPress={handleNextPage}
              disabled={currentPage === totalPages}
            >
              <Text
                style={[
                  paginationFrontend.paginationButton,
                  currentPage === totalPages &&
                    paginationFrontend.disabledButtonText,
                ]}
              >
                Next
              </Text>
            </TouchableOpacity>
          </View>
        )}
      </View>
      <View style={globalStyles.invfilters}>
        <TouchableOpacity
          style={[
            globalStyles.button,
            userDetails?.role !== "Manager" && globalStyles.disabledButton,
          ]}
          disabled={userDetails?.role !== "Manager"}
          onPress={() => setAddProductModalVisible(true)}
        >
          <Text>Add New Product</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={globalStyles.button}
          onPress={() => setFilterModalVisible(true)}
        >
          <Text>Filters</Text>
        </TouchableOpacity>
        <TouchableOpacity
          disabled={userDetails?.role !== "Manager"}
          style={[
            globalStyles.button,
            userDetails?.role !== "Manager" && globalStyles.disabledButton,
          ]}
          onPress={() => handleDelete()}
        >
          <Text>Delete Selected Products</Text>
        </TouchableOpacity>
      </View>

      <AddProductModal
        isVisible={addProductModalVisible}
        onClose={() => {
          fetchItems();
          setAddProductModalVisible(false);
        }}
      />
      <SelectFilters
        isVisible={filterModalVisible}
        onClose={() => {
          setFilterModalVisible(false);
        }}
        onFilter={onFilter}
        categories={categories}
        brands={brands}
      />
    </View>
  );
};

const paginationFrontend = StyleSheet.create({
  paginationContainer: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    margin: 10,
  },
  paginationButton: {
    paddingHorizontal: 15,
    paddingVertical: 10,
    backgroundColor: "#E68A5C",
    borderRadius: 5,
    marginHorizontal: 5,
    color: "white",
  },
  pageNumberText: {
    paddingHorizontal: 10,
  },
  disabledButtonText: {
    backgroundColor: "#ccc",
  },
});

export default InventoryPage;
