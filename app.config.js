import { version } from './package.json';

const BUNDLE_IDENTIFIER = 'fr.fiesta.jarvis';

const ICON_PATH = './src/assets/icon.png';
const SPLASH_PATH = './src/assets/splash-icon.png';
const ADAPTIVE_ICON_PATH = './src/assets/adaptive-icon.png';

export default ({ config }) => ({
  ...config,
  name: 'Jarvis',
  slug: 'jarvis',
  version,
  orientation: 'portrait',
  icon: ICON_PATH,
  userInterfaceStyle: 'automatic',
  newArchEnabled: true,
  splash: {
    image: SPLASH_PATH,
    resizeMode: 'contain',
    backgroundColor: '#ffffff',
  },
  platforms: ['ios', 'android'],
  ios: {
    bundleIdentifier: BUNDLE_IDENTIFIER,
    supportsTablet: true,
  },
  android: {
    package: BUNDLE_IDENTIFIER,
    adaptiveIcon: {
      foregroundImage: ADAPTIVE_ICON_PATH,
      backgroundColor: '#ffffff',
    },
    edgeToEdgeEnabled: true,
    predictiveBackGestureEnabled: false,
  },
  extra: {
    eas: {
      projectId: 'c4757339-eb11-4e8d-b0ac-db2a146f1377',
    },
  },
  plugins: ['expo-localization'],
});
