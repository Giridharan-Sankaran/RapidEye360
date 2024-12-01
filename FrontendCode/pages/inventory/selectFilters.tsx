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
import { globalStyles } from '../styles';
import DatePicker from 'react-native-modern-datepicker';
import {Picker} from '@react-native-picker/picker';


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
  const [category, setCategory] = useState('');
  const [dateOfExpiry, setExpiryDate] = useState('');
  const [dateOfProcurement, setProcurementDate] = useState('');

  const [filter, setFilter] = useState<Filter>({attribute: '', value: ''});
  const [displayFilters, setDisplayFilters] = useState(false);

  const clearFilter = () => {
    setFilter({attribute: '', value: ''});
    onClose();
  };

  const setFilterAttribute = (attribute: string) => {
    setFilter({attribute: attribute, value: filter.value});
  };

  const parseDate = (date: string) => {
    const dateArray = date.split('/').join('-');
    return dateArray;
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

        <View style={globalStyles.filters}>
          <View style={globalStyles.filterSection}>
            <TouchableOpacity
              style={globalStyles.filterHeader}
              onPress={() => setDisplayFilters(!displayFilters)}>
              <Text style={globalStyles.filterHeaderText}>Filter By</Text>
            </TouchableOpacity>
            {displayFilters && (
              <View style={globalStyles.filterContainer}>
                <TouchableOpacity
                  style={[globalStyles.filterButton, globalStyles.first]}
                  onPress={() => setFilterAttribute('UPCID')}>
                  <Text style={globalStyles.filterText}>UPCID</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  style={globalStyles.filterButton}
                  onPress={() => setFilterAttribute('Brand')}>
                  <Text style={globalStyles.filterText}>Brand</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  style={globalStyles.filterButton}
                  onPress={() => setFilterAttribute('Category')}>
                  <Text style={globalStyles.filterText}>Category</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  style={globalStyles.filterButton}
                  onPress={() => setFilterAttribute('procurementDate')}>
                  <Text style={globalStyles.filterText}>Procurement Date</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  style={globalStyles.filterButton}
                  onPress={() => setFilterAttribute('expiryDate')}>
                  <Text style={globalStyles.filterText}>Expiry Date</Text>
                </TouchableOpacity>
              </View>
            )}
          </View>

          <View style={globalStyles.selectedFilterContainer}>
            {filter.attribute == 'UPCID' && (
              <TextInput
                style={globalStyles.input}
                value={productId}
                onChangeText={text => {
                  setFilter({...filter, value: text});
                  setProductId(text);
                }}
              />
            )}
            {filter.attribute == 'procurementDate' && (
              <DatePicker
                mode="calendar"
                selected={dateOfProcurement}
                style={globalStyles.datePicker}
                options={{
                  textHeaderColor: '#FFA25B',
                  textFontSize: 12,
                  textHeaderFontSize: 13,
                  borderColor: 'transparent',
                  selectedTextColor: '#fff',
                  mainColor: '#F4722B',
                  textSecondaryColor: '#D6C7A1',
                  // selectedDayBackgroundColor: '#F4722B',
                }}
                onDateChange={date => {
                  setFilter({...filter, value: parseDate(date)});
                  setProcurementDate(date);
                }}
              />
            )}
            {filter.attribute == 'expiryDate' && (
              <DatePicker
                mode="calendar"
                selected={dateOfExpiry}
                style={globalStyles.datePicker}
                options={{
                  textHeaderColor: '#FFA25B',
                  textFontSize: 12,
                  textHeaderFontSize: 13,
                  borderColor: 'transparent',
                  selectedTextColor: '#fff',
                  mainColor: '#F4722B',
                  textSecondaryColor: '#D6C7A1',
                  // selectedDayBackgroundColor: '#F4722B',
                }}
                onDateChange={date => {
                  setFilter({...filter, value: parseDate(date)});
                  setExpiryDate(date);
                }}
              />
            )}
            {filter.attribute == 'Category' && (
              <View style={globalStyles.picker}>
                <Picker
                  selectedValue={category}
                  style={globalStyles.picker}
                  onValueChange={itemValue => {
                    setFilter({...filter, value: itemValue});
                    setCategory(itemValue);
                  }}>
                  <Picker.Item label="Select Category" value="" />
                  {categories.map((category, index) => (
                    <Picker.Item
                      key={index}
                      label={category}
                      value={category}
                    />
                  ))}
                </Picker>
              </View>
            )}
            {filter.attribute == 'Brand' && (
              <View style={globalStyles.picker}>
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
            )}
          </View>
        </View>
        <View style={globalStyles.buttonContainer}>
          <TouchableOpacity style={globalStyles.close} onPress={clearFilter}>
            <Text style={globalStyles.closeText}>Cancel</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={globalStyles.button}
            onPress={() => {
              onFilter(filter);
              onClose();
            }}>
            <Text style={globalStyles.buttonText}>Apply Filters</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};



export default SelectFilters;
