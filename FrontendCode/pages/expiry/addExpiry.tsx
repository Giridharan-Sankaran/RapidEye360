import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Alert,
} from "react-native";
import api from "../api";
import { globalStyles } from "../styles";
import CheckBox from "expo-checkbox";
import { Picker } from "@react-native-picker/picker";

const AddExpiryModal: React.FC<{ isVisible: boolean; handleExpiry: (se)=> void ; onClose: () => void}> = ({
  isVisible,
  handleExpiry,
  onClose,

}) => {
  const [filteredData, setFilteredData] = useState<any[]>([]);
  const [currentSelection, setCurrentSelection] = useState("");
  const [selectedProducts, setSelectedProducts] = useState<any[]>([]);
 
  const parseDate = (date: any) => {
    const dateArray = date.split("/");
    const year = dateArray[0];
    const month = dateArray[1];
    const day = dateArray[2];
    return new Date(year, month, day);
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
    const hours = date.getHours().toString().padStart(2, "0");
    const minutes = date.getMinutes().toString().padStart(2, "0");
    const seconds = date.getSeconds().toString().padStart(2, "0");
    const readableFormat = `${year}-${month}-${day}`;
    return readableFormat;
  };

  const handleProductExpiry = () => {
    const products = selectedProducts.map(({ id, ...rest }) => rest);
    const apiString =
      selectedProducts.length > 1
        ? "/clerkExpiry/saveExpiries"
        : "/clerkExpiry/saveExpiry";
    api
      .post(apiString, products)
      .then((response) => {
        Alert.alert("Product successfully added");
        onClose()
      })
      .catch((error) => {
        Alert.alert("Error:", error);
      });
  };

  const handleProductSelection = (product) => {
    // Toggle product selection
    const itemIndex = selectedProducts.findIndex(
      (item) => item.upcid === product.upcid
    );
    const productExp = filteredData.filter(
      (item) => item.upcid === product.upcid
    )[0];
    setCurrentSelection(productExp.name);

    if (itemIndex !== -1) {
      setSelectedProducts((prevItems) =>
        prevItems.filter((item) => item.upcid !== product)
      );
    } else {
      setSelectedProducts((prevItems) => [...prevItems, { ...productExp }]);
    }
  };

  if (!isVisible) {
    return null; // Render nothing if not visible
  }

  return (
    <View style={globalStyles.overlay}>
      <View style={[globalStyles.container, { overflow: "scroll" }]}>
        <TouchableOpacity style={globalStyles.closeButton} onPress={onClose}>
          <Text style={globalStyles.closeText}> X </Text>
        </TouchableOpacity>
        {filteredData.map((product) => (
          <View
            key={product.id}
            style={{ flexDirection: "row", gap: 10, marginBottom: 6 }}
          >
            <CheckBox
              style={globalStyles.checkbox}
              value={selectedProducts.some(
                (selected) => selected.upcid === product.upcid
              )}
              onValueChange={() => handleProductSelection(product)}
              color={
                selectedProducts.some(
                  (selected) => selected.upcid === product.upcid
                )
                  ? "#E68A5C"
                  : "#ccc"
              }
            />
            <Text>
              {product.name} | Expires on: {formatDate(product.dateOfExpiry)}
            </Text>
          </View>
        ))}

        <View style={globalStyles.buttonContainer}>
          <TouchableOpacity
            style={globalStyles.close}
            onPress={() => onClose()}
          >
            <Text style={globalStyles.closeText}>Cancel</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={globalStyles.button}
            onPress={()=>{handleExpiry(selectedProducts); onClose()}}
          >
            <Text style={globalStyles.buttonText}>Add Expired Products</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};

export default AddExpiryModal;
