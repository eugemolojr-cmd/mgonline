import { createRoot } from 'react-dom/client';
import keycloak from './auth/keycloak';
import App from './App';

keycloak
  .init({ onLoad: 'login-required', pkceMethod: 'S256' })
  .then((authenticated) => {
    if (!authenticated) return keycloak.login();
    createRoot(document.getElementById('root')).render(<App />);
  })
  .catch((err) => {
    document.getElementById('root').innerText = 'Authentication failed: ' + err;
  });
