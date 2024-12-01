// navigation/StackNavigator.tsx
import React, {useContext} from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import Login from '../pages/login'; // Import your login page
import Sidebar from './drawer'; // Import your home page
import ClerkSidebar from './clerkDrawer';
import UserSelection from '../pages/selectType';
import {AuthContext} from '../context/AuthContext';

const Stack = createStackNavigator();

const AuthStack = () => {
  return (
    <Stack.Navigator initialRouteName="UserSelection">
      <Stack.Screen name="UserSelection" component={UserSelection} />
      <Stack.Screen name="Login" component={Login} />
    </Stack.Navigator>
  );
};



const AppStack = () => {
  const {userDetails} = useContext(AuthContext);
  return userDetails?.role === "Manager" ? (
    <Stack.Navigator initialRouteName="Home">
      <Stack.Screen name="Home" component={Sidebar} />
    </Stack.Navigator>
  ) : (
    <Stack.Navigator initialRouteName="Home">
      <Stack.Screen name="Home" component={ClerkSidebar} />
    </Stack.Navigator>
  );
};



const StackNavigator: React.FC = () => {
  const {user} = useContext(AuthContext);
  return user ? <AppStack /> : <AuthStack />;
};
export default StackNavigator;

