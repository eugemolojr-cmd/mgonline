import axios from 'axios';
import keycloak from '../auth/keycloak';

const http = axios.create();

// Attach a fresh access token to every request.
http.interceptors.request.use(async (config) => {
  await keycloak.updateToken(30);
  config.headers.Authorization = `Bearer ${keycloak.token}`;
  return config;
});

export default http;
