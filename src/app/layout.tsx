import '@styles/global.css';

import Onboarding from '@app/onboarding';
import ReactQueryProvider from '@components/providers/ReactQueryProvider';
import { useColorScheme } from '@hooks/useColorScheme';
import {
  DarkTheme,
  DefaultTheme,
  NavigationContainer,
  Theme,
  ThemeProvider,
} from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { PortalHost } from '@rn-primitives/portal';
import { StatusBar } from 'expo-status-bar';
import { useLayoutEffect, useState } from 'react';

const LIGHT_THEME: Theme = DefaultTheme;

const DARK_THEME: Theme = DarkTheme;

const RootStack = createNativeStackNavigator();

const RootLayout: React.FC = () => {
  const { isDarkColorScheme } = useColorScheme();
  const [isColorSchemeLoaded, setIsColorSchemeLoaded] = useState(false);

  useLayoutEffect(() => {
    const timer = setTimeout(() => {
      setIsColorSchemeLoaded(true);
    }, 0);

    return () => clearTimeout(timer);
  }, []);

  if (!isColorSchemeLoaded) {
    // TODO: Add a loading screen
    return null;
  }

  return (
    <ReactQueryProvider>
      <ThemeProvider value={isDarkColorScheme ? DARK_THEME : LIGHT_THEME}>
        <StatusBar style={isDarkColorScheme ? 'light' : 'dark'} />

        <NavigationContainer
          theme={isDarkColorScheme ? DARK_THEME : LIGHT_THEME}
        >
          <RootStack.Navigator screenOptions={{ headerShown: false }}>
            <RootStack.Screen name="onboarding" component={Onboarding} />
          </RootStack.Navigator>
        </NavigationContainer>

        <PortalHost name="root" />
      </ThemeProvider>
    </ReactQueryProvider>
  );
};

export default RootLayout;
