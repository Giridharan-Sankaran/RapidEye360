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


const EditSupplierPage: React.FC<{
  isVisible: boolean;
  onClose: () => void;
  supplier: any;
}> = ({isVisible, onClose, supplier}) => {
  const [supplierName, setSupplierName] = useState('');
  const [contactMail, setEmail] = useState(supplier?.supplierEmail  ?? '');
  const [brand, setBrand] = useState('');
  const [contactNumber, setContactNumber] = useState(
    supplier?.supplierContact ?? '',
  );


  const handleEditSupplier = () => {
    api
      .put('/supplier/' + supplier.brandName, {
        supplierContact: contactNumber,
        supplierEmail: contactMail,
      })
      .then(response => {
        Alert.alert('Supplier successfully edited');
        onClose();
      })
      .catch(error => {
        Alert.alert('Error editing supplier. Please try again.');
      });
  };

  if (!isVisible) {
    return null; // Render nothing if not visible
  }

  return (
    <View style={styles.overlay}>
      <View style={styles.container}>
        <TouchableOpacity style={styles.closeButton} onPress={onClose}>
          <Text style={styles.closeText}> X </Text>
        </TouchableOpacity>
        <Text style={styles.label}>Brand Name</Text>
        <TextInput
          style={styles.input}
          placeholder="Brand"
          onChangeText={setBrand}
          value={supplier.brandName}
          editable={false}
        />
        <Text style={styles.label}>Supplier Email</Text>
        <TextInput
          style={styles.input}
          placeholder="Contact Email"
          onChangeText={text => setEmail(text)}
          value={contactMail}
          keyboardType="email-address"
        />
        <Text style={styles.label}>Supplier Contact</Text>
        <TextInput
          style={styles.input}
          placeholder="Contact Number"
          onChangeText={text => setContactNumber(text)}
          value={contactNumber}
          keyboardType="phone-pad"
        />

        <View style={styles.buttonContainer}>
          <TouchableOpacity style={styles.close} onPress={() => onClose()}>
            <Text style={styles.closeText}>Cancel</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={handleEditSupplier}>
            <Text style={styles.buttonText}>Save Supplier</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingHorizontal: 35,
    paddingVertical: 25,
    color: 'black',
    backgroundColor: 'white',
    padding: 20,
    width: '80%',
    maxHeight: '100%',
    minHeight: '85%',
    borderRadius: 8,
    // elevation: 5,
  },
  overlay: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  modalContainer: {
    backgroundColor: 'white',
    padding: 20,
    borderRadius: 10,
    elevation: 5, // For shadow (macOS)
  },
  closeButton: {
    height: 20,
    marginBottom: 10,
    borderRadius: 50,
    backgroundColor: '#fff',
    justifyContent: 'flex-end',
    flexDirection: 'row',
    alignItems: 'center',
  },
  input: {
    width: '100%',
    height: 'auto',
    borderWidth: 1,
    borderColor: '#667085',
    color: '#48505E',
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 5,
    paddingHorizontal: 10,
    marginBottom: 8,
    paddingVertical: 10,
  },
  label: {
    color: '#333333',
    marginBottom: 2,
    fontWeight: '300',
    fontSize: 12,
  },
  button: {
    height: 40,
    backgroundColor: '#E68A5C',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 5,
    marginTop: 10,
    paddingHorizontal: 20,
  },
  close: {
    height: 40,
    backgroundColor: '#fff',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 5,
    marginTop: 10,
  },
  buttonText: {
    color: '#fff',
    fontSize: 14,
    fontWeight: '500',
  },
  closeText: {
    color: '#E68A5C',
    fontSize: 14,
    fontWeight: '500',
  },
  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    gap: 15,
  },
});

export default EditSupplierPage;
