import React, { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native';
 
const UserSelection: React.FC<{navigation: any}> = ({navigation}) => {
  const [selectedUserType, setSelectedUserType] = useState('');
  const headerImage = require('../../assets/head.png'); 
  const handleUserTypeSelection = (userType: string) => {
    setSelectedUserType(userType);
  };

  return (
    <View style={styles.container}>
      <View style={styles.row}>
        <TouchableOpacity
          style={[
            styles.box,
            selectedUserType === 'manager' && styles.selectedBox,
          ]}
          onPress={() => handleUserTypeSelection('manager')}>
          <Image source={headerImage} style={styles.headerImage} />
          <Text style={styles.boxText}>Store Manager</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[
            styles.box,
            selectedUserType === 'clerk' && styles.selectedBox,
          ]}
          onPress={() => handleUserTypeSelection('clerk')}>
          <Image source={headerImage} style={styles.headerImage} />
          <Text style={styles.boxText}>Store Clerk</Text>
        </TouchableOpacity>
      </View>
      <TouchableOpacity style={styles.selectButton} onPress={() => navigation.navigate('Login')}>
        <Text style={styles.selectButtonText}>Select</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 20,
    fontFamily: 'Inter',
  },
  row: {
    flexDirection: 'row',
    marginBottom: 20,
    gap: 20,
  },
  box: {
    height: 200,
    width: 250,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 25,
    marginRight: 10,
    backgroundColor: '#D8C3A5',
  },
  selectedBox: {
    borderColor: '#E68A5C',
    borderWidth: 3,
  },
  boxText: {
    fontSize: 16,
    color: '#333333',
    fontFamily: 'Inter-Regular',
  },
  selectButton: {
    backgroundColor: '#E68A5C',
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 5,
    alignItems: 'center',
    marginTop: 20,
    width: 250,
  },
  selectButtonText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#fff',
  },
  headerImage: {
    width: 80, // Adjust width as needed
    height: 80, // Adjust height as needed
    marginBottom: 20,
    borderTopLeftRadius: 5,
    borderTopRightRadius: 5,
  },
});

export default UserSelection;
