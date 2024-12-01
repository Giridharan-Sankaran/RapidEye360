// api.js
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  // headers: {
  //   'Access-Control-Allow-Origin': '*',
  //   'Content-Type': 'application/json',
  // },
  // withCredentials: true,
  // credentials: 'same-origin',
});

// Add a request interceptor
api.interceptors.request.use(
  async (config) => {
    // Get the token from AsyncStorage or wherever you have stored it
    const token = await AsyncStorage.getItem('user');
    // Set the token to the request headers
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
