// ClerkSidebar.tsx
import React, {useState, useContext} from 'react';
import InventoryPage from '../pages/inventory';


import {View, StyleSheet, TouchableOpacity, Text} from 'react-native';
import {AuthContext} from '../context/AuthContext';
import ClerkExpiryPage from '../pages/clerkExpiry';
import ClerkDiscountPage from '../pages/clerkDiscount';

const ClerkSidebar: React.FC<{navigation: any}> = ({navigation}) => {
  const {logout} = useContext(AuthContext);
  const [selectedScreen, setSelectedScreen] = useState('Inventory');
  const navigateToScreen = (screenName: any) => {
    setSelectedScreen(screenName);
  };

  return (
    <View style={styles.mainContainer}>
      <View style={styles.sidebar}>
        <View>
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
            style={[styles.item, selectedScreen === 'Clerk Expiry' && styles.selectedBg]}
            onPress={() => navigateToScreen('Clerk Expiry')}>
            <Text
              style={[
                styles.texts,
                selectedScreen === 'Clerk Expiry' && styles.selectedBox,
              ]}>
              Clerk Expiry
            </Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.item, selectedScreen === 'Clerk Discount' && styles.selectedBg]}
            onPress={() => navigateToScreen('Clerk Discount')}>
            <Text
              style={[
                styles.texts,
                selectedScreen === 'Clerk Discount' && styles.selectedBox,
              ]}>
              Clerk Discount
            </Text>
          </TouchableOpacity>
        </View>

        <View style={{marginTop: '95%'}}>
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
        {selectedScreen === 'Clerk Expiry' && <ClerkExpiryPage />}
        {selectedScreen === 'Clerk Discount' && <ClerkDiscountPage />}
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
    padding: 20,
  },
  sidebar: {
    width: '15%',
    paddingVertical: 20,
    backgroundColor: '#fff',
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
  logout:{
    color: 'red',
  },
  selectedBg: {
    backgroundColor:  '#E68A5C',
  }
});

export default ClerkSidebar;
