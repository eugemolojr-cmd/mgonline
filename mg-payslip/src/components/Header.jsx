import keycloak from '../auth/keycloak';

export default function Header() {
  const name = keycloak.tokenParsed?.name || keycloak.tokenParsed?.preferred_username;
  return (
    <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 24 }}>
      <h1 style={{ margin: 0 }}>Metroguards Payslips</h1>
      <div>
        <span style={{ marginRight: 12 }}>{name}</span>
        <button onClick={() => keycloak.logout({ redirectUri: window.location.origin })}>Log out</button>
      </div>
    </header>
  );
}
