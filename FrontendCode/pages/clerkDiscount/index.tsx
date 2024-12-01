import React, { useState, useEffect, useContext } from "react";
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
import { AuthContext } from "../../context/AuthContext";
import api from "../api";
//import SelectFilters from "./selectFilters";
import { globalStyles } from "../styles";

const ClerkDiscountPage: React.FC = () => {
  const { userDetails } = useContext(AuthContext);

  const [filteredData, setFilteredData] = useState<any[]>([]);
  const [selectedItems, setSelectedItems] = useState<any[]>([]);
  const [selectAll, setSelectAll] = useState<boolean>(false);

  const fetchItems = async () => {
    api
      .get("/clerkDiscount/listAll")
      .then((response) => {
        const productsData: any[] = response.data;
        const specific = productsData.filter(
          (product) => product.username == userDetails.username
        );
        setFilteredData(specific);
      })
      .catch((error) => {
        Alert.alert("Error fetching products:", error);
      });
  };


  useEffect(() => {
    fetchItems();
  }, []);

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

      const requestData = selectedItems.map(item => ({
        upcid: item.upcid,
        aisleNumber: item.aisleNumber,
        category: item.category,
        name: item.name,
        brand: item.brand,
        status: item.status,
        dateOfExipry: item.dateOfExpiry,
        originalPrice: item.originalPrice,
        discountPrice: item.discountPrice
        // Add other properties of the item as needed
      }));
    
      const requestBody = {
        items: requestData,
        status: "Completed"
      };

      const items = await api.put("/discount/statusUpdate/updateDiscount", requestBody);
      setFilteredData(items.data);
      await fetchItems();
    } catch (error) {
      Alert.alert("Error", "Failed to delete item");
    }
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


  const renderItem = ({ item }: { item: any }) => (
    <View style={styles.row}>
      <Text style={styles.cell}>{truncateText(item.upcid)}</Text>
      <Text style={styles.cell}>{truncateText(item.name)}</Text>
      <Text style={styles.cell}>{truncateText(item.category)}</Text>
      <Text style={styles.cell}>{truncateText(item.brand)}</Text>
      <Text style={styles.cell}>{item.aisleNumber}</Text>
      <Text style={styles.cell}>{item.originalPrice}</Text>
      <Text style={styles.cell}>{item.discountPrice ?? item.originalPrice}</Text>
      <Text style={styles.cell}>{item.status ?? item.statusField ?? "Pending"}</Text>
      <Text style={styles.cell}>
        {" "}
        <CheckBox
          style={globalStyles.checkbox}
          value={ selectedItems.some(
            (selected) => selected.upcid === item.upcid
          )}
          onValueChange={() =>
            toggleItemSelection(item.upcid, item.aisleNumber)
          }
          color={
            selectedItems.some((selected) => selected.upcid === item.upcid)
              ? "#E68A5C"
              : "#ccc"
          }
        />
      </Text>
    </View>
  );

  return (
    <View style={styles.main}>
      <View style={styles.table}>
        <ScrollView horizontal={true}>
          <View style={styles.container}>
            <View style={styles.headerRow}>
              <Text style={styles.header}>UPCID</Text>
              <Text style={styles.header}>Name</Text>
              <Text style={styles.header}>Category</Text>
              <Text style={styles.header}>Brand</Text>
              <Text style={styles.header}>Aisle Number</Text>
              <Text style={styles.header}>Original Price</Text>
              <Text style={styles.header}>Discount Price</Text>
              <Text style={styles.header}>Status</Text>
              <Text style={styles.cell}>
        {" "}
        <CheckBox
          style={globalStyles.checkbox}
          value={false}
          color={
              "#E68A5C"
          }
        />
      </Text>
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
        {/* <TouchableOpacity
          style={styles.button}
          onPress={() => setAddProductModalVisible(true)}
        >
          <Text>Add Expiry</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.button}
          onPress={() => setFilterModalVisible(true)}
        >
          <Text>Filters</Text>
        </TouchableOpacity> */}
        <TouchableOpacity style={styles.button} onPress={() => handleDelete()}>
          <Text>Submit</Text>
        </TouchableOpacity>
      </View>

      {/* <SelectFilters
        isVisible={filterModalVisible}
        onClose={() => {
          setFilterModalVisible(false);
        }}
        onFilter={onFilter}
      /> */}
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
});

export default ClerkDiscountPage;
