import { useEffect, useState } from 'react';
import { employeeService } from '../services/employeeService';

export default function EmployeeList() {
  const [employees, setEmployees] = useState([]);

  useEffect(() => {
    employeeService.list().then(setEmployees).catch(() => setEmployees([]));
  }, []);

  return (
    <section>
      <h2>Employees (HR)</h2>
      <ul>
        {employees.map((e) => (
          <li key={e.id}>{e.firstName} {e.lastName} - {e.jobTitle} ({e.username})</li>
        ))}
      </ul>
    </section>
  );
}
