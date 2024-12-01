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
import AddSupplierPage from "./addSupplier";
import EditSupplierPage from "./editSupplier";
import SelectFilters from "./filterSupplier";
import axios from "axios";
import api from "../api";
import { globalStyles } from "../styles";
 
const SupplierPage: React.FC = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const [filteredData, setFilteredData] = useState<any[]>([]);
  const [suppliers, setSuppliers] = useState<any[]>([]);
  const [selectedItems, setSelectedItems] = useState<any[]>([]);
  const [reorderItems, setReorderItems] = useState<any[]>([]);
  const [selectAll, setSelectAll] = useState<boolean>(false);
  const [addSupplierModalVisible, setAddSupplierModalVisible] = useState(false);
  const [filterModalVisible, setFilterModalVisible] = useState(false);
  const [editFilterModalVisible, setEditFilterModalVisible] = useState(false);
  const [categories, setCategories] = useState<any[]>([]);
  const [brands, setBrands] = useState<any[]>([]);
 
  const fetchItems = async () => {
    api
      .get("/supplier/listAll")
      .then((response) => {
        const supplierData = response.data.filter(
          (supplier: any) => supplier.brandName
        );
        setSuppliers(supplierData);
        const uniqueBrands = Array.from(
          new Set(supplierData.map((product: any) => product.brandName))
        );
        setCategories(uniqueBrands);
        setBrands(uniqueBrands);
        setFilteredData(supplierData);
      })
      .catch((error) => {
        Alert.alert("Error fetching suppliers:", error);
      });
  };
 
  const onFilter = (filter: any) => {
    api
      .get("/supplier/brandName/" + filter.value)
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
 
  const handleSearch = (text: string) => {
    setSearchQuery(text);
    const filtered = suppliers.filter((item) =>
      item.brandName.toLowerCase().includes(text.toLowerCase())
    );
    setFilteredData(filtered);
  };
 
  const toggleItemSelection = (
    itemId: any,
    brandName: any,
    supplierEmail: any,
    supplierContact: any
  ) => {
    const itemIndex = selectedItems.findIndex((item) => item.id === itemId);
    if (itemIndex !== -1) {
      setSelectedItems((prevItems) =>
        prevItems.filter((item) => item.id !== itemId)
      );
    } else {
      setSelectedItems((prevItems) => [
        ...prevItems,
        { id: itemId, brandName, supplierContact, supplierEmail },
      ]);
    }
  };
 
  const reOrder = (itemId: any, updatedquantity: any) => {
    const itemIndex = reorderItems.findIndex((item) => item.id === itemId);
    if (itemIndex !== -1) {
      setReorderItems((prevItems) =>
        prevItems.filter((item) => item.id !== itemId)
      );
    } else {
      setReorderItems((prevItems) => [...prevItems, { id: itemId, updatedquantity }]);
    }
  };
 
  const sendEmails = async () => {
    api
    .post("/send-emails", selectedItems)
    .then((response) => {
      Alert.alert(JSON.stringify(response))
    })
    .catch((error) => {
      Alert.alert("Error fetching suppliers:", error);
    });
  };
 
  const addQuantity = (itemId: any, updatedquantity: any, item: any) => {
    const itemIndex = selectedItems.findIndex((selectedItem) => selectedItem.id === itemId);
    if (itemIndex !== -1) {
      setSelectedItems((prevItems) =>
        prevItems.map((selectedItem) => {
          if (selectedItem.id === itemId) {
            return { ...selectedItem, updatedquantity: Number(updatedquantity) };
          }
          return selectedItem;
        })
      );
    } else {
      setSelectedItems((prevItems) => [...prevItems, { id: itemId, productName: item.brandName, updatedquantity: Number(updatedquantity), ...item }]);
    }
  };
 
  const toggleSelectAll = () => {
    setSelectAll((prevSelectAll) => !prevSelectAll);
    if (!selectAll) {
      setSelectedItems([...filteredData]);
    } else {
      setSelectedItems([]);
    }
  };
 
  const renderItem = ({ item }: { item: any }) => (
    <View style={styles.row}>
      <CheckBox
        style={globalStyles.checkbox}
        value={selectedItems.some((selected) => selected.id === item.id)}
        onValueChange={() =>
          toggleItemSelection(
            item.id,
            item.brandName,
            item.supplierEmail,
            item.supplierContact
          )
        }
        color={
          selectedItems.some((selected) => selected.id === item.id)
            ? "#E68A5C"
            : "#ccc"
        }
      />
      <Text style={styles.cell}>{item.brandName}</Text>
      <Text style={styles.cell}>{item.supplierEmail}</Text>
      <Text style={styles.cell}>{item.supplierContact}</Text>
      <Text style={styles.cell}>{item.quantity}</Text>
      <View style={styles.cell}>
        <TextInput
          style={styles.input}
          placeholder="0"
          placeholderTextColor={"#999"}
          onChangeText={(text) => addQuantity(item.id, text, item)}
          keyboardType="number-pad"
        />
      </View>
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
              <Text style={styles.header}>Brand Name</Text>
              <Text style={styles.header}>Supplier Email</Text>
              <Text style={styles.header}>Contact Number</Text>
              <Text style={styles.header}>Quantity</Text>
              <Text style={styles.header}>Update Quantity</Text>
            </View>
            <FlatList
              data={filteredData}
              renderItem={renderItem}
              keyExtractor={(item) => item.id}
              contentContainerStyle={{ paddingBottom: 20 }}
            />
          </View>
        </ScrollView>
      </View>
 
      <View style={styles.filters}>
        <TouchableOpacity
          style={styles.button}
          onPress={() => setAddSupplierModalVisible(true)}
        >
          <Text>Add Supplier</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.button}
          onPress={() => setFilterModalVisible(true)}
        >
          <Text>Filter By Brand</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[
            styles.button,
            selectedItems.length !== 1 && styles.disabledButton,
          ]}
          disabled={selectedItems.length !== 1}
          onPress={() => setEditFilterModalVisible(true)}
        >
          <Text>Edit Supplier</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[
            styles.button,
            selectedItems.length < 1 && styles.disabledButton,
          ]}
          disabled={selectedItems.length < 1}
          onPress={() => sendEmails()}
        >
          <Text>Reorder</Text>
        </TouchableOpacity>
      </View>
 
      <AddSupplierPage
        isVisible={addSupplierModalVisible}
        onClose={() => {
          fetchItems();
          setAddSupplierModalVisible(false);
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
      <EditSupplierPage
        isVisible={editFilterModalVisible}
        onClose={() => {
          fetchItems();
          setEditFilterModalVisible(false);
        }}
        supplier={selectedItems[0]}
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
  row: {
    flexDirection: "row",
    alignItems: "center",
    paddingVertical: 15,
    paddingHorizontal: 10,
    borderBottomWidth: 1,
    borderColor: "#ccc",
  },
  headerRow: {
    flexDirection: "row",
    alignItems: "center",
    paddingVertical: 12,
    paddingHorizontal: 10,
    marginBottom: 10,
    borderWidth: 1,
    borderRadius: 5,
    borderColor: "transparent",
    backgroundColor: "#E68A5C",
  },
  header: {
    minWidth: "19%",
    fontSize: 14,
    color: "#fff",
    alignItems: "center",
  },
  checkbox: {
    paddingHorizontal: 20,
  },
  cell: {
    width: "19%",
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
});
 
export default SupplierPage;
 