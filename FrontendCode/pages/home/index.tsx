// Sidebar.tsx
import React, {useState} from 'react';
import InventoryPage from '../inventory'; // Import your inventory page
import SupplierPage from '../suppliers'; // Import your supplier page
import { View, StyleSheet, TouchableOpacity, Text } from 'react-native';

const Sidebar: React.FC<{navigation: any}> = ({navigation}) =>{
    const [selectedScreen, setSelectedScreen] = useState('Inventory');
  const navigateToScreen = (screenName:any) => {
   setSelectedScreen(screenName);
  };

  return (
    <View style={styles.mainContainer}>
        <View style={styles.sidebar}>
        <TouchableOpacity
        style={styles.item}
        onPress={() => navigateToScreen('Inventory')}
      >
        <Text>Home</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.item}
        onPress={() => navigateToScreen('Supplier')}
      >
        <Text>Profile</Text>
      </TouchableOpacity>
        </View>
 <View style={styles.container}>
    {selectedScreen === 'Inventory' && <InventoryPage />}
    {selectedScreen === 'Supplier' && <SupplierPage />}
 </View>
      {/* Add more sidebar items as needed */}
    </View>
  );
};

const styles = StyleSheet.create({
    mainContainer: {
    flex: 1,
    flexDirection: 'row',
    },
  container: {
    flex: 2,
    backgroundColor: '#795959',
    padding: 20,
  },
  sidebar: {
    flex: 1,
    backgroundColor: '#795959',
    padding: 20,
  },
  item: {
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
  },
});

export default Sidebar;
