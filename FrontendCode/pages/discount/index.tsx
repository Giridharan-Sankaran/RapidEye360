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
import { globalStyles } from "../styles";
import { Picker } from "@react-native-picker/picker";
import api from "../api";
import AddDiscountModal from "./addDiscount";

const DiscountPage: React.FC = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const [filteredData, setFilteredData] = useState<any[]>([]);
  const [selectedItems, setSelectedItems] = useState<any[]>([]);
  const [reorderItems, setReorderItems] = useState<any[]>([]);
  const [selectAll, setSelectAll] = useState<boolean>(false);
  const [addExpiryModalVisible, setExpiryModalVisible] = useState(false);
  const discount = [
    0, 1, 2, 3, 4, 5, 10, 15, 20, 25, 30, 40, 50, 60, 70, 80, 90, 100,
  ];

  const addDiscount = (itemId: any, discount: any, item) => {
    const itemIndex = selectedItems.findIndex((item) => item.upcid === itemId);
    if (itemIndex !== -1) {
      setSelectedItems((prevItems) =>
        prevItems.map((item) => {
          if (item.upcid === itemId) {
            return {
              upcid: item.upcid,
              discount: Number(discount),
              discountPrice: Math.ceil(
                item.originalPrice - item.originalPrice * (discount / 100)
              ) ?? item.originalPrice,
              ...item,
            };
          }
          return item;
        })
      );
    } else {
      setSelectedItems((prevItems) => [
        ...prevItems,
        {
          upcid: itemId,
          discount: Number(discount),
          ...item,
          discountPrice: Math.ceil(
            item.originalPrice - item.originalPrice * (discount / 100) ?? item.originalPrice
          ),
        },
      ]);
    }
  };

  const fetchItems = async () => {
    api
      .get("/discount/listAll")
      .then((response) => {
        const dicount = response.data;
        setFilteredData(dicount);
      })
      .catch((error) => {
        Alert.alert("Error fetching discounts:", error);
      });
  };

  useEffect(() => {
    fetchItems();
  }, []);

  function truncateText(text: string) {
    const maxLength = 25;
    if (text && text.length > maxLength) {
      return text.substring(0, maxLength - 3) + "...";
    } else {
      return text ?? "...";
    }
  }

  const handleProductExpiry = async (selectedProducts: any[]) => {
    const products = selectedProducts.map(({ id, ...rest }) => rest);
    const request = products.length > 1 ? products : products[0];
    const apiString =
      selectedProducts.length > 1
        ? "/clerkDiscount/saveDiscounts"
        : "/clerkDiscount/saveDiscount";
   await api.post(apiString, request)
   products.map(async (product) => {
    await api.put(`/discount/${product.upcid}`, {...product});
   })
   await fetchItems()

  };

  const renderItem = ({ item }: { item: any }) => (
    <View style={styles.row}>
      <CheckBox
        style={[globalStyles.checkbox]}
        value={selectedItems.some((selected) => selected.upcid === item.upcid)}
        color={
          selectedItems.some((selected) => selected.upcid === item.upcid)
            ? "#E68A5C"
            : "#ccc"
        }
      />
      <Text style={styles.cell}>{item.upcid}</Text>
      <Text style={styles.cell}>{truncateText(item.name)}</Text>
      <Text style={styles.cell}>{truncateText(item.category)}</Text>
      <Text style={styles.cell}>{item.price ?? item.originalPrice}</Text>
      <Text style={styles.cell}>{item.discountPrice ?? item.originalPrice}</Text>
      <Text style={styles.cell}>{item.aisleNumber}</Text>
      <Text style={styles.cell}>{item.status}</Text>
      <Text style={styles.cell}>
        <Picker
          selectedValue={
            Number(
              selectedItems.filter(
                (selected) => selected.upcid === item.upcid
              )[0]?.discount
            ) ?? 0
          }
          style={styles.picker}
          onValueChange={(itemValue) =>
            addDiscount(item.upcid, itemValue, {
              name: item.name,
              brand: item.brand,
              aisleNumber: item.aisleNumber,
              originalPrice: item.originalPrice,
              dateOfExpiry: item.dateOfExpiry,
              upcid: item.upcid,
            })
          }
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
     
    </View>
  );

  return (
    <View style={styles.main}>
      <View style={styles.table}>
        <ScrollView horizontal={true}>
          <View style={styles.container}>
            <View style={styles.headerRow}>
              <CheckBox style={[globalStyles.checkbox]} value={false} />
              <Text style={styles.header}>UPCID</Text>
              <Text style={styles.header}>Product Name</Text>
              <Text style={styles.header}>Category</Text>
              <Text style={styles.header}>Price</Text>
              <Text style={styles.header}>Discount</Text>
              <Text style={styles.header}>Aisle Number</Text>
              <Text style={styles.header}>Status</Text>
              <Text style={styles.header}>Add Discount</Text>

              {/* <Text style={styles.header}>Apply</Text> */}
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
          style={[
            styles.button,
            selectedItems.length < 1 && styles.disabledButton,
          ]}
          onPress={() => handleProductExpiry(selectedItems)}
        >
          <Text>Apply Discounts</Text>
        </TouchableOpacity>
      </View>

      <AddDiscountModal
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

export default DiscountPage;
