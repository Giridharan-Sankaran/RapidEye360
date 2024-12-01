import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  TextInput,
  FlatList,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Alert,
} from "react-native";
import CheckBox from "expo-checkbox";
import axios from "axios";
import api from "../api";
import SelectFilters from "./selectFilters";
import { globalStyles } from "../styles";
import AddExpiryModal from "./addExpiry";
import ClerkExpiryPage from "../clerkExpiry";

<ClerkExpiryPage updateFilteredData={updateFilteredData} />

const updateFilteredData = (updatedItems) => {
  setFilteredData(updatedItems);
};


const ExpiryPage: React.FC = () => {
  const [filteredData, setFilteredData] = useState<any[]>([]);
  const [selectedItems, setSelectedItems] = useState<any[]>([]);
  const [selectAll, setSelectAll] = useState<boolean>(false);
  const [addExpiryModalVisible, setExpiryModalVisible] = useState(false);
  const [filterModalVisible, setFilterModalVisible] = useState(false);

  const fetchItems = async () => {
    api
      .get("/expiry/listAll")
      .then((response) => {
        const productsData: any[] = response.data;
        setFilteredData(productsData);
      })
      .catch((error) => {
        Alert.alert("Error fetching products:", error);
      });
  };

  const onFilter = (filter: any) => {
    let link = "";
    const date = new Date(filter.value);
    if (filter.attribute == "UPCID") {
      link = "/expiry/" + filter.attribute + "/" + filter.value;
    } else if (filter.attribute == "expirydate") {
      link =
        "/expiry/" + filter.attribute + "?dateOfExpiry=" + date.toISOString();
    }
    api
      .get(link)
      .then((response) => {
        const productsData: any[] = response.data;
        setFilteredData(productsData);
      })
      .catch((error) => {
        Alert.alert("Error fetching products:", error);
      });
  };

  useEffect(() => {
    fetchItems();
  }, []);

  const handleProductExpiry = (selectedProducts: any[]) => {
    const products = selectedProducts.map(({ id, ...rest }) => rest);
    const request = products.length > 1 ? products : products[0];
    const apiString =
      selectedProducts.length > 1
        ? "/clerkExpiry/saveExpiries"
        : "/clerkExpiry/saveExpiry";
    api
      .post(apiString, request)
      .then((response) => {
        setFilteredData([...filteredData, ...response.data]);
        Alert.alert("Product successfully added");
      })
      .catch((error) => {
        Alert.alert("Error:", error);
      });
  };

  const toggleItemSelection = (itemId: any, aisleNumber: any, item) => {
    const itemIndex = selectedItems.findIndex((item) => item.upcid === itemId);
    if (itemIndex !== -1) {
      setSelectedItems((prevItems) =>
        prevItems.filter((item) => item.upcid !== itemId)
      );
    } else {
      setSelectedItems((prevItems) => [
        ...prevItems,
        { upcid: itemId, aisleNumber, ...item },
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
    // Send delete request for each selected item
    selectedItems.forEach(async (item) => {
      try {
        await api.delete(`/products/remove/${item.upcid}/${item.aisleNumber}`);
        setFilteredData(
          filteredData.filter(
            (item) =>
              !selectedItems.some((selected) => selected.upcid === item.id)
          )
        );
      } catch (error) {
        console.error("Error deleting item:", error);
        Alert.alert("Error", "Failed to delete item");
      }
    });
    await fetchItems();
    Alert.alert("Delete Successful", "Item(s) deleted successfully");
    setSelectedItems([]);
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


  const renderItem = ({ item }: { item: any }) => (
    <View style={styles.row}>
      <CheckBox
        style={globalStyles.checkbox}
        value={selectedItems.some((selected) => selected.upcid === item.upcid)}
        onValueChange={() =>
          toggleItemSelection(item.upcid, item.aisleNumber, item)
        }
        color={
          selectedItems.some((selected) => selected.upcid === item.upcid)
            ? "#E68A5C"
            : "#ccc"
        }
      />
      
      <Text style={styles.cell}>{truncateText(item.upcid)}</Text>
      <Text style={styles.cell}>{truncateText(item.name)}</Text>
      <Text style={styles.cell}>{truncateText(item.category)}</Text>
      <Text style={styles.cell}>{item.aisleNumber}</Text>
      <Text style={styles.cell}>{formatDate(item.dateOfExpiry)}</Text>
      <Text style={styles.cell}>{item.status}</Text>
    </View>
  );

  return (
    <View style={styles.main}>
      <View style={styles.table}>
        <ScrollView horizontal={true}>
          <View style={styles.container}>
            <View style={styles.headerRow}>
              <CheckBox
                style={globalStyles.checkbox}
                value={selectAll}
                onValueChange={() => toggleSelectAll()}
                color={selectAll ? "#E68A5C" : "#ccc"}
              />
              <Text style={styles.header}>UPCID</Text>
              <Text style={styles.header}>Name</Text>
              <Text style={styles.header}>Category</Text>
              <Text style={styles.header}>Aisle Number</Text>
              <Text style={styles.header}>Expiry Date</Text>
              <Text style={styles.header}>Status</Text>
            </View>
            <FlatList
              data={filteredData}
              renderItem={renderItem}
              keyExtractor={(item) => item.upcid}
              contentContainerStyle={{ paddingBottom: 20 }}
            />
          </View>
        </ScrollView>
      </View>

      <View style={styles.filters}>
        <TouchableOpacity
          style={[
            styles.button,
            selectedItems.length < 1 && styles.disabledButton,
          ]}
          onPress={() => handleProductExpiry(selectedItems)}
        >
          <Text>Add Expiry</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.button}
          onPress={() => setFilterModalVisible(true)}
        >
          <Text>Filters</Text>
        </TouchableOpacity>
        {/* <TouchableOpacity style={styles.button} onPress={() => handleDelete()}>
          <Text>Submit</Text>
        </TouchableOpacity> */}
      </View>

      <SelectFilters
        isVisible={filterModalVisible}
        onClose={() => {
          setFilterModalVisible(false);
        }}
        onFilter={onFilter}
      />

      <AddExpiryModal
        isVisible={addExpiryModalVisible}
        handleExpiry={handleProductExpiry}
        onClose={() => {
          fetchItems();
          setExpiryModalVisible(false);
        }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  main: {
    flexDirection: "row",
    paddingTop: 20,
    height: "100%",
  },
  filters: {
    width: "24%",
    paddingLeft: 25,
    paddingVertical: 40,
  },
  table: {
    width: "75%",
  },
  container: {
    flex: 1,
    width: 1200,
    overflow: "scroll",
    paddingHorizontal: 20,
  },
  searchInput: {
    height: 40,
    borderWidth: 1,
    borderColor: "#ccc",
    borderRadius: 5,
    paddingHorizontal: 10,
    marginBottom: 20,
    color: "black",
    paddingVertical: 10,
  },
  row: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    paddingVertical: 12,
    paddingHorizontal: 10,
    borderBottomWidth: 1,
    borderColor: "#ccc",
  },
  headerRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    paddingVertical: 15,
    paddingHorizontal: 10,
    marginBottom: 10,
    borderWidth: 1,
    borderRadius: 5,
    borderColor: "transparent",
    backgroundColor: "#E68A5C",
  },
  header: {
    flex: 1,
    fontSize: 14,
    color: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  checkbox: {
    paddingHorizontal: 20,
  },
  cell: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    fontSize: 12,
    color: "#48505E",
  },
  button: {
    backgroundColor: "#E68A5C",
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 5,
    alignItems: "center",
    marginTop: 20,
  },
  disabledButton: {
    backgroundColor: "#ccc",
  },
});

export default ExpiryPage;
