-- DEV SEED DATA: remove for production. Usernames match the Keycloak realm users.
INSERT INTO employees (username, first_name, last_name, email, job_title, department, hire_date, monthly_salary) VALUES
 ('hr.admin',  'Hannah', 'Reyes',     'hr.admin@metroguards.local',  'HR Manager',     'Human Resources', '2020-02-03', 65000.00),
 ('employee1', 'Juan',   'Dela Cruz', 'employee1@metroguards.local', 'Security Officer','Operations',       '2022-06-15', 28000.00);

INSERT INTO payslips (employee_id, period_year, period_month, basic_pay, allowances, deductions, tax, net_pay)
SELECT id, 2026, 8, 28000.00, 2000.00, 1500.00, 1800.00, 26700.00 FROM employees WHERE username = 'employee1';
INSERT INTO payslips (employee_id, period_year, period_month, basic_pay, allowances, deductions, tax, net_pay)
SELECT id, 2026, 9, 28000.00, 2500.00, 1500.00, 1850.00, 27150.00 FROM employees WHERE username = 'employee1';
INSERT INTO payslips (employee_id, period_year, period_month, basic_pay, allowances, deductions, tax, net_pay)
SELECT id, 2026, 9, 65000.00, 5000.00, 3000.00, 9000.00, 58000.00 FROM employees WHERE username = 'hr.admin';
