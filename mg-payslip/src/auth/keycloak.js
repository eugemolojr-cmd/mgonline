import Keycloak from 'keycloak-js';

// Same-origin through the nginx gateway: http://localhost/auth
const keycloak = new Keycloak({
  url: `${window.location.origin}/auth`,
  realm: 'metroguards',
  clientId: 'metroguards-web',
});

export const hasRole = (role) => keycloak.hasRealmRole(role);

export default keycloak;
