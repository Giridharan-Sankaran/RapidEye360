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
import api from "../api";

const ClerkListPage: React.FC = () => {
  const [filteredData, setFilteredData] = useState<any[]>([]);
  const [clerkList, setClerkList] = useState<any>({});

  const createMap = (data: any) => {
  let userDataMap = {}
    data.map((item) => {
      //const { username, upcid, ...userItems } = item;
      if (item.username && !userDataMap[item.username]) {
        userDataMap[item.username] = [String(item.upcid)];
      }
      if (item.username && userDataMap[item.username]) {
        userDataMap[item.username].push(String(item.upcid));
      }
    });
    return userDataMap;
  };
  const fetchItems = async () => {
    try {
      const expiryResponse = await api.get("/clerkExpiry/listAll");
      const discountResponse = await api.get("/clerkDiscount/listAll");
      const expiryData = expiryResponse.data;
      const discountData = discountResponse.data;
      setFilteredData([...expiryData, ...discountData]);

      setClerkList({...createMap(expiryData), ...createMap(discountData) });
    } catch (error) {
      Alert.alert("Error fetching data:", error.message);
    }
  };
  

  useEffect(() => {
    fetchItems();

  }, []);

  const data = Object.entries(clerkList).map(([username, upcids]) => ({
    username,
    upcids: '{ ' + upcids.join(", ") + ' }', // Concatenate UPCIDs as a string
  }));

  const renderItem = ({ item }: { item: any }) => (
    <View style={styles.row}>
      <Text style={styles.cell}>{item.username}</Text>
      <Text style={[styles.cell, {flex: 1}]}>{item.upcids}</Text>
    </View>
  );

  return (
    <View style={styles.main}>
      <View style={styles.table}>
        <ScrollView horizontal={true}>
          <View style={styles.container}>
            <View style={styles.headerRow}>
              <Text style={styles.header}> Usernames</Text>
              <Text style={[styles.header, {flex:1}]}>Assigned UPCIDs</Text>
            </View>
            <FlatList
              data={data}
              renderItem={renderItem}
              keyExtractor={(item, index) => index.toString()}
              contentContainerStyle={{ paddingBottom: 20 }}
            />
          </View>
        </ScrollView>
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

  table: {
    width: "100%",
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
    fontSize: 14,
    color: "#fff",
    alignItems: "center",
    width: '20%',
  },
  checkbox: {
    paddingHorizontal: 20,
  },
  cell: {
    width: '20%',
    alignItems: "center",
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
  picker: {
    width: "55%",
    marginLeft: 0,
    alignSelf: "flex-start",
  },
});

export default ClerkListPage;
