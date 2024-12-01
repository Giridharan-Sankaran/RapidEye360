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
import axios from "axios";
import api from "../api";
//import SelectFilters from "./selectFilters";
import { globalStyles } from "../styles";
import { AuthContext } from "../../context/AuthContext";

const ClerkExpiryPage: React.FC = ({updateFilteredData}) => {
  const { userDetails } = useContext(AuthContext);

  const [filteredData, setFilteredData] = useState<any[]>([]);
  const [selectedItems, setSelectedItems] = useState<any[]>([]);
  const [selectAll, setSelectAll] = useState<boolean>(false);

  const fetchItems = async () => {
    api
      .get("/clerkExpiry/listAll")
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
    console.log("Toggling item with ID:", itemId);
    const itemIndex = selectedItems.findIndex(item => item.upcid === itemId);
    if (itemIndex !== -1) {
      console.log("Removing item:", itemId);
      setSelectedItems(prevItems => prevItems.filter(item => item.upcid !== itemId));
    } else {
      console.log("Adding item:", itemId);
      setSelectedItems(prevItems => [...prevItems, { upcid: itemId, aisleNumber }]);
    }
  };
  


  const handleDelete = async () => {
    try {
      const itemIds = selectedItems.map(item => item.upcid);
      const requestData = selectedItems.map(item => ({
        upcid: item.upcid,
        aisleNumber: item.aisleNumber,
        category: item.category,
        name: item.name,
        brand: item.brand,
        status: item.status,
        dateOfExipry: item.dateOfExpiry
        // Add other properties of the item as needed
      }));
    
      const requestBody = {
        items: requestData,
        status: "Completed"
      };
      
      const items = await api.put("/expiry/statusUpdate/updateExpiry",requestBody);
      setFilteredData(prevData => prevData.filter(item => !itemIds.includes(item.upcid)));
      Alert.alert("Delete Successful", "Item(s) deleted successfully");
      setSelectedItems([]);
      updateFilteredData(filteredData.filter(item => !itemIds.includes(item.upcid)));
    } catch (error) {
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

  const renderItem = ({ item }: { item: any }) => (
    <View style={styles.row}>
      <Text style={styles.cell}>{truncateText(item.upcid)}</Text>
      <Text style={styles.cell}>{truncateText(item.name)}</Text>
      <Text style={styles.cell}>{truncateText(item.category)}</Text>
      <Text style={styles.cell}>{item.aisleNumber}</Text>
      <Text style={styles.cell}>{item.status ?? item.statusField ?? "Pending"}</Text>
      <Text style={styles.cell}>{formatDate(item.dateOfExpiry)}</Text>
      <Text style={styles.cell}>
        {" "}
        <CheckBox
          style={globalStyles.checkbox}
          value={selectedItems.some(
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
              <Text style={styles.header}>Aisle Number</Text>
              <Text style={styles.header}>Status</Text>
              <Text style={styles.header}>Expiry Date</Text>
              <Text style={styles.header}>Remove</Text>
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
        <TouchableOpacity style={styles.button} onPress={() => handleDelete()}>
          <Text>Submit</Text>
        </TouchableOpacity>
      </View>
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

export default ClerkExpiryPage;
