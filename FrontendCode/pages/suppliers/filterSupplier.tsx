import React, {useState} from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  Button,
  StyleSheet,
  Alert,
} from 'react-native';
import api from '../api';
import {Picker} from '@react-native-picker/picker';
import {set} from 'react-native-reanimated';

interface Filter {
  attribute: string;
  value: string;
}

const SelectFilters: React.FC<{
  isVisible: boolean;
  onClose: () => void;
  onFilter: (filter: Filter) => void;
  categories: any[];
  brands: any[];
}> = ({isVisible, onClose, onFilter, categories, brands}) => {
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

  const [filter, setFilter] = useState<Filter>({attribute: '', value: ''});
  const [displayFilters, setDisplayFilters] = useState(false);

  const clearFilter = () => {
    setFilter({attribute: '', value: ''});
    onClose();
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

        <View style={styles.filters}>
          <View style={styles.filterSection}>
            <View style={styles.filterHeader}>
              <Text style={styles.filterHeaderText}>Filter By Brand</Text>
            </View>
          </View>

          <View style={styles.selectedFilterContainer}>
            <View style={styles.picker}>
              <Picker
                selectedValue={brand}
                onValueChange={itemValue => {
                  setFilter({...filter, value: itemValue});
                  setBrand(itemValue);
                }}>
                <Picker.Item label="Select Brand" value="" />
                {brands.map((brand, index) => (
                  <Picker.Item key={index} label={brand} value={brand} />
                ))}
              </Picker>
            </View>
          </View>
        </View>
        <View style={styles.buttonContainer}>
          <TouchableOpacity style={styles.close} onPress={clearFilter}>
            <Text style={styles.closeText}>Cancel</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={styles.button}
            onPress={() => {
              onFilter(filter);
              onClose();
            }}>
            <Text style={styles.buttonText}>Apply Filters</Text>
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
    width: '85%',
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
  text: {
    justifyContent: 'center',
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
  filters: {
    flexDirection: 'row',
    height: '75%',
    justifyContent: 'space-between',
    marginBottom: 10,
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
  filterSection: {
    marginBottom: 10,
    width: '24%',
    marginTop: 20,
  },
  selectedFilterContainer: {
    width: '75%',
    marginLeft: 30,
    marginTop: 20,
  },
  filterContainer: {
    flexWrap: 'wrap',
    width: '100%',
    marginBottom: 10,
    marginTop: 10,
    borderWidth: 1,
    borderColor: '#c0c0c0',
  },
  filterHeader: {
    height: 30,
    width: '100%',
    paddingHorizontal: 20,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#E68A5C',
    borderRadius: 5,
    marginBottom: 5,
  },
  filterHeaderText: {
    fontSize: 14,
    fontWeight: '500',
  },
  filterButton: {
    height: 30,
    width: '100%',
    paddingHorizontal: 20,
    justifyContent: 'center',
    alignItems: 'center',
    borderColor: '#c0c0c0',
    borderTopWidth: 1,
  },
  first: {
    borderTopWidth: 0,
  },
  filterText: {
    color: '#999',
    fontSize: 14,
  },
  datePicker: {
    height: 'auto',
    width: '65%',
    marginLeft: 30,
  },
  picker: {
    width: '85%',
  },
});

export default SelectFilters;
