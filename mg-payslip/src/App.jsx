import Header from './components/Header';
import PayslipList from './components/PayslipList';
import EmployeeList from './components/EmployeeList';
import { hasRole } from './auth/keycloak';

export default function App() {
  return (
    <main style={{ maxWidth: 960, margin: '32px auto', fontFamily: 'system-ui, sans-serif' }}>
      <Header />
      <PayslipList />
      {hasRole('HR_ADMIN') && <EmployeeList />}
    </main>
  );
}
