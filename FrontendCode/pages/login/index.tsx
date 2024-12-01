import React, {useState, useContext} from 'react';
import {useNavigation} from '@react-navigation/native';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Image,
  ActivityIndicator
} from 'react-native';
import {AuthContext} from '../../context/AuthContext';

const Login: React.FC<{navigation: any}> = ({navigation}) => {
  const {login, loading} = useContext(AuthContext);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const headerImage = require('../../assets/logo.png');


  return (
    <View style={styles.container}>
      <View style={styles.headingContainer}>
        <Image source={headerImage}/>
      </View>
      <View style={styles.formContainer}>
      <Text style={styles.heading}>Log in to your account</Text>
      <Text style={styles.subHeading}>Welcome back! Please enter your details.</Text>
      <Text style={styles.label}>Email</Text>
      <TextInput
        style={styles.input}
        placeholder="Email"
         
        placeholderTextColor="#999"
        onChangeText={text => setEmail(text)}
        value={email}
        keyboardType="email-address"
        autoCapitalize="none"
      />
      <Text style={styles.label}>Password</Text>
      <TextInput
        style={styles.input}
        placeholder="Password"
        placeholderTextColor="#999"
        onChangeText={text => setPassword(text)}
        value={password}
        secureTextEntry
      />
      <TouchableOpacity style={styles.button} onPress={() => login(email, password)}>
        {loading ? <ActivityIndicator size={'small'} /> : <Text style={styles.buttonText}>Login</Text>}
      </TouchableOpacity>
      </View>


    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 20,
    gap: 100,
  },
  headingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  formContainer:{
    flex: 1, 
    marginHorizontal: '10%'
  },
  heading: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 4,
    color: '#333333'
  },
  subHeading: {
    fontSize: 16,
    marginBottom: 20,
    color: '#555555'
  },
  input: {
    width: '100%',
    height: 40,
    borderWidth: 1,
    borderColor: '#667085',
    color: '#48505E',
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 5,
    paddingHorizontal: 10,
    marginBottom: 20,
    paddingVertical: 10,
  },
  button: {
    width: '100%',
    height: 40,
    backgroundColor: '#E68A5C',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 5,
  },
  buttonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold',
  },
  label: {
    color: '#333333',
    marginBottom: 5,
  }
});

export default Login;
