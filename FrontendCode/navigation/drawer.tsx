// Sidebar.tsx
import React, {useState, useContext} from 'react';
import InventoryPage from '../pages/inventory';
import SupplierPage from '../pages/suppliers';
import ExpiryPage from '../pages/expiry';
import DiscountPage from '../pages/discount';
import ClerkListPage from '../pages/clerklist';

import {View, StyleSheet, TouchableOpacity, Text} from 'react-native';
import {AuthContext} from '../context/AuthContext';

const Sidebar: React.FC<{navigation: any}> = ({navigation}) => {
  const {logout} = useContext(AuthContext);
  const [selectedScreen, setSelectedScreen] = useState('Inventory');
  const navigateToScreen = (screenName: any) => {
    setSelectedScreen(screenName);
  };

  return (
    <View style={styles.mainContainer}>
      <View style={styles.sidebar}>
        <View >
          <TouchableOpacity
            style={[styles.item, selectedScreen === 'Inventory' && styles.selectedBg]}
            onPress={() => navigateToScreen('Inventory')}>
            <Text
              style={[
                styles.texts,
                selectedScreen === 'Inventory' && styles.selectedBox,
              ]}>
              Inventory
            </Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.item, selectedScreen === 'Expiry' && styles.selectedBg]}
            onPress={() => navigateToScreen('Expiry')}>
            <Text
              style={[
                styles.texts,
                selectedScreen === 'Expiry' && styles.selectedBox,
              ]}>
              Expiring Items
            </Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.item, selectedScreen === 'Discount' && styles.selectedBg]}
            onPress={() => navigateToScreen('Discount')}>
            <Text
              style={[
                styles.texts,
                selectedScreen === 'Discount' && styles.selectedBox,
              ]}>
              Discounted Items
            </Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.item, selectedScreen === 'Supplier' && styles.selectedBg]}
            onPress={() => navigateToScreen('Supplier')}>
            <Text
              style={[
                styles.texts,
                selectedScreen === 'Supplier' && styles.selectedBox,
              ]}>
              Supplier
            </Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.item, selectedScreen === 'ClerkList' && styles.selectedBg]}
            onPress={() => navigateToScreen('ClerkList')}>
            <Text
              style={[
                styles.texts,
                selectedScreen === 'ClerkList' && styles.selectedBox,
              ]}>
              Clerk List
            </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.logoutCont}>
        <TouchableOpacity
            style={[styles.item]}
            onPress={() => logout()}>
            <Text
              style={[
                styles.texts,
                styles.logout
              ]}>
                Logout
            </Text>
          </TouchableOpacity>
        </View>
      </View>

      <View style={styles.container}>
        {selectedScreen === 'Inventory' && <InventoryPage />}
        {selectedScreen === 'Supplier' && <SupplierPage />}
        {selectedScreen === 'Expiry' && <ExpiryPage />}
        {selectedScreen === 'Discount' && <DiscountPage />}
        {selectedScreen === 'ClerkList' && <ClerkListPage />}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    flexDirection: 'row',
  },
  container: {
    width: '85%',
    backgroundColor: '#F0F1F3',
    //padding: 20,
  },
  sidebar: {
    width: '15%',
    paddingVertical: 20,
    backgroundColor: '#fff',
    //justifyContent: 'space-between',
  },
  selectedBox: {
    color: '#fff',
  },
  texts: {
    color: '#5D6679',
    fontFamily: 'Inter',
    marginLeft: 20
  },
  item: {
    paddingVertical: 10,
    
    borderBottomColor: '#ccc',
  },
  logoutCont: {
    marginTop: '95%',
  },
  logout:{
    color: 'red',
  },
  selectedBg: {
    backgroundColor:  '#E68A5C',
  }
});

export default Sidebar;
