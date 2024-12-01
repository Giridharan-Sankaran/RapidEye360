import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Alert,
  ScrollView,
  FlatList,
} from "react-native";
import api from "../api";
import { globalStyles } from "../styles";
import CheckBox from "expo-checkbox";
import { Picker } from "@react-native-picker/picker";

const AddDiscountModal: React.FC<{
  isVisible: boolean;
  handleExpiry: (se) => void;
  onClose: () => void;
}> = ({ isVisible, handleExpiry, onClose }) => {
  const [filteredData, setFilteredData] = useState<any[]>([]);
  const [selectedItems, setSelectedItems] = useState<any[]>([]);
  const discount = [
    0, 1, 2, 3, 4, 5, 10, 15, 20, 25, 30, 40, 50, 60, 70, 80, 90, 100,
  ];

  const addDiscount = (itemId: any, discount: any, item) => {
    const itemIndex = selectedItems.findIndex((item) => item.upcid === itemId);
    if (itemIndex !== -1) {
      setSelectedItems((prevItems) =>
        prevItems.map((item) => {
          if (item.upcid === itemId) {
            return { upcid: item.upcid, discount: Number(discount), discountPrice:Math.floor(item.originalPrice - (item.originalPrice * (discount / 100))), ...item };
          }
          return item;
        })
      );
    } else {
      setSelectedItems((prevItems) => [
        ...prevItems,
        { upcid: itemId, discount: Number(discount), ...item, discountPrice:Math.floor(item.originalPrice - (item.originalPrice * (discount / 100)))},
      ]);
    }
  };


  const fetchItems = async () => {
    api
      .get("/products/all")
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

  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, "0"); // Adding 1 because months are zero-based
    const day = date.getDate().toString().padStart(2, "0");
    const readableFormat = `${year}-${month}-${day}`;
    return readableFormat;
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
      <Text style={styles.cell}>{truncateText(item.name)}</Text>
      <Text style={styles.cell}>{item.price}</Text>
      <Text style={styles.cell}>{formatDate(item.dateOfExpiry)}</Text>
      <Text style={styles.cell}>
        <Picker
          selectedValue={Number(selectedItems.filter((selected) => selected.upcid === item.upcid)[0]?.discount) ?? 0}
          style={styles.picker}
          onValueChange={(itemValue) => addDiscount(item.upcid, itemValue, {name: item.name, brand: item.brand, aisleNumber: item.aisleNumber, originalPrice: item.price, dateOfExpiry: item.dateOfExpiry, upcid: item.upcid})}
        >
          <Picker.Item label="Select Discount" value="" />
          {discount.map((discount, index) => (
            <Picker.Item
              key={index}
              label={String(discount)}
              value={discount}
              color="black"
            />
          ))}
        </Picker>
      </Text>
      {/* <Text style={styles.cell}>
        {" "}
        <CheckBox
          style={[globalStyles.checkbox]}
          value={selectedItems.some((selected) => selected.id === item.id)}
          onValueChange={() => reOrder(item.id, 0)}
          color={
            selectedItems.some((selected) => selected.id === item.id)
              ? "#E68A5C"
              : "#ccc"
          }
        />
      </Text> */}
    </View>
  );
  if (!isVisible) {
    return null; // Render nothing if not visible
  }

  return (
    <View style={[globalStyles.overlay]}>
      <View style={[globalStyles.container, { overflow: "scroll" }]}>
        <TouchableOpacity style={globalStyles.closeButton} onPress={onClose}>
          <Text style={globalStyles.closeText}> X </Text>
        </TouchableOpacity>
        <View style={{ width: "100%",  height: "85%" }}>
          <View style={{ height: "100%", }}>
            <View style={styles.headerRow}>
              <Text style={styles.header}>Product Name</Text>
              <Text style={styles.header}>Price</Text>
              <Text style={styles.header}>Expiry Date</Text>
              <Text style={styles.header}>Discount</Text>

              {/* <Text style={styles.header}>Apply</Text> */}
            </View>
            <FlatList
              data={filteredData}
              renderItem={renderItem}
              keyExtractor={(item) => item.upcid}
              contentContainerStyle={{ paddingBottom: 20 }}
            />
          </View>
        </View>

        <View style={globalStyles.buttonContainer}>
          <TouchableOpacity
            style={globalStyles.close}
            onPress={() => onClose()}
          >
            <Text style={globalStyles.closeText}>Cancel</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={globalStyles.button}
            onPress={() => {
              handleExpiry(selectedItems);
              onClose();
            }}
          >
            <Text style={globalStyles.buttonText}>Add Discounted Products</Text>
          </TouchableOpacity>
        </View>
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
  container: {
    //flex: 1,
    width: "100%",
    overflow: "scroll",
    paddingHorizontal: 20,
  },
  input: {
    width: "40%",
    borderWidth: 1,
    borderColor: "#667085",
    color: "#48505E",
    borderRadius: 5,
    paddingHorizontal: 10,
    paddingVertical: 1,
    alignSelf: "flex-start",
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
    //width: "19%",
    flex: 1,
    fontSize: 14,
    color: "#fff",
    alignItems: "center",
  },

  cell: {
    //width: "19%",
    flex: 1,
    alignItems: "center",
    fontSize: 12,
    color: "#48505E",
  },
  check: {
    width: "19%",
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
  picker: {
    width: "55%",
    marginLeft: 0,
    alignSelf: "flex-start",
  },
});
export default AddDiscountModal;
