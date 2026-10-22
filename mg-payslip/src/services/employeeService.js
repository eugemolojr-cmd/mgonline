import http from '../api/http';

// Employee management is served by the backend microservice (HR_ADMIN only)
export const employeeService = {
  list: () => http.get('/api/employees').then((r) => r.data),
};
