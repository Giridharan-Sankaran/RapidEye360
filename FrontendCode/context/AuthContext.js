// AuthContext.js
import React, {createContext, useState, useContext, useEffect} from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios  from 'axios';
import { Alert } from 'react-native';
export const AuthContext = createContext();

export const AuthProvider = ({children}) => {
  const [user, setUser] = useState(null);
  const [userDetails, setUserDetails] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // Check if the user is already logged in
    const fetchUser = async () => {
      try {
        const storedUser = await AsyncStorage.getItem('user');
        const storedUserDetails = await AsyncStorage.getItem('userDetails');
        if (storedUser) {
          setUser(JSON.parse(storedUser)) 
        }
        if(storedUserDetails){
          setUserDetails(JSON.parse(storedUserDetails))
        }
        setLoading(false);
      } catch (e) {

        setLoading(false);
      }
    };

    fetchUser();
  }, []);

  const login = async (userName, passWord) => {
    setLoading(true);
    try {
      const response = await axios.post('http://localhost:8080/login', {
         userName,
         passWord ,
      });
      const data = await response.data;
      setUser(data.token);
      setUserDetails(data);
      await AsyncStorage.setItem('user', data.token);
      await AsyncStorage.setItem('userDetails', JSON.stringify(data));
      setLoading(false);
    } catch (e) {
      Alert.alert("Error logging in. Please try again");
      setLoading(false);
    }
  };

  const logout = async () => {
    try {
      await AsyncStorage.removeItem('user');
      await AsyncStorage.removeItem('userDetails');
      setUser(null);
      setUserDetails(null);
    } catch (e) {
      Alert.alert("Logout failed");
    }
  };

  return (
    <AuthContext.Provider value={{user, userDetails, loading, login, logout}}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
