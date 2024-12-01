import React, {useState} from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Alert,
} from 'react-native';
import api from '../api';
import { globalStyles } from '../styles';

const AddProductModal: React.FC<{isVisible: boolean; onClose: () => void}> = ({
  isVisible,
  onClose,
}) => {
  const [brand, setBrand] = useState('');
  const [productId, setProductId] = useState('');
  const [name, setProductName] = useState('');
  const [category, setCategory] = useState('');
  const [price, setPrice] = useState('');
  const [quantity, setQuantity] = useState('');
  const [difference, setThresholdValue] = useState('');
  const [dateOfExpiry, setExpiryDate] = useState('');
  const [dateOfProcurement, setProcurementDate] = useState('');
  const [aisleNumber, setAisleNumber] = useState('');

  const parseDate = (date: any) => {
    const dateArray = date.split('/');
    const year = dateArray[0];
    const month = dateArray[1];
    const day = dateArray[2];
    return new Date(year, month, day);
  };

  const handleAddProduct = () => {
    // Handle adding product logic here, such as sending product data to server

    api.post('/products/create', {
      category: category,
      name: name,
      brand: brand,
      price: price,
      quantity: quantity,
      dateOfProcurement: parseDate(dateOfProcurement),
      dateOfExpiry: parseDate(dateOfExpiry),
      difference: difference,
      aisleNumber: aisleNumber,
    })
  .then(response => {
    Alert.alert("Product successfully added");
    setBrand('');
    setProductId('');
    setProductName('');
    setCategory('');
    setPrice('');
    setQuantity('');
    setThresholdValue('');
    setExpiryDate('');
    setProcurementDate('');
    setAisleNumber('');
    onClose();

  })
  .catch(error => {
    Alert.alert('Error:', error);

  });
  };

  if (!isVisible) {
    return null; // Render nothing if not visible
  }

  return (
    <View style={globalStyles.overlay}>
      <View style={globalStyles.container}>
        <TouchableOpacity style={globalStyles.closeButton} onPress={onClose}>
          <Text style={globalStyles.closeText}> X </Text>
        </TouchableOpacity>
        <Text style={globalStyles.label}>Brand</Text>
        <TextInput
          style={globalStyles.input}
          placeholder="Product Brand"
          placeholderTextColor="#999"
          onChangeText={setBrand}
          value={brand}
          keyboardType="numeric"
        />
        <Text style={globalStyles.label}>Product Name</Text>
        <TextInput
          style={globalStyles.input}
          placeholder="Product Name"
          placeholderTextColor="#999"
          onChangeText={setProductName}
          value={name}
        />
        <Text style={globalStyles.label}>Category</Text>
        <TextInput
          style={globalStyles.input}
          placeholder="Category"
          placeholderTextColor="#999"
          onChangeText={setCategory}
          value={category}
        />
        <Text style={globalStyles.label}>Difference</Text>
        <TextInput
          style={globalStyles.input}
          placeholder="Difference"
          placeholderTextColor="#999"
          onChangeText={setThresholdValue}
          value={difference}
          keyboardType="numeric"
        />
        <Text style={globalStyles.label}>Price</Text>
        <TextInput
          style={globalStyles.input}
          placeholder="Buying Price"
          placeholderTextColor="#999"
          onChangeText={setPrice}
          value={price}
          keyboardType="numeric"
        />
        <Text style={globalStyles.label}>Quantity</Text>
        <TextInput
          style={globalStyles.input}
          placeholder="Quantity"
          placeholderTextColor="#999"
          onChangeText={setQuantity}
          value={quantity}
          keyboardType="numeric"
        />
        <Text style={globalStyles.label}>Date of Procurement</Text>
        <TextInput
          style={globalStyles.input}
          placeholder="Date of Procurement"
          placeholderTextColor="#999"
          onChangeText={setProcurementDate}
          value={dateOfProcurement}
          dataDetectorTypes={"all"}
        />
        <Text style={globalStyles.label}>Date of Expiry</Text>
        <TextInput
          style={globalStyles.input}
          placeholder="Date of Expiry"
          placeholderTextColor="#999"
          onChangeText={setExpiryDate}
          value={dateOfExpiry}
          dataDetectorTypes={"all"}
        />
        <Text style={globalStyles.label}>Aisle Number</Text>
        <TextInput
          style={globalStyles.input}
          placeholder="Aisle Number"
          placeholderTextColor="#999"
          onChangeText={setAisleNumber}
          value={aisleNumber}
          keyboardType="numeric"
        />

        <View style={globalStyles.buttonContainer}>
          <TouchableOpacity style={globalStyles.close} onPress={() => onClose()}>
            <Text style={globalStyles.closeText}>Cancel</Text>
          </TouchableOpacity>
          <TouchableOpacity style={globalStyles.button} onPress={handleAddProduct}>
            <Text style={globalStyles.buttonText}>Add Product</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};



export default AddProductModal;
