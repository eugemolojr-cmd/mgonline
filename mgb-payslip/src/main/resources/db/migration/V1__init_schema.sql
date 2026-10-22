CREATE TABLE employees (
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR(100) NOT NULL UNIQUE,   -- matches Keycloak preferred_username
    first_name      VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    job_title       VARCHAR(100),
    department      VARCHAR(100),
    hire_date       DATE,
    monthly_salary  NUMERIC(12,2) NOT NULL
);

CREATE TABLE payslips (
    id            BIGSERIAL PRIMARY KEY,
    employee_id   BIGINT NOT NULL REFERENCES employees(id),
    period_year   INT NOT NULL,
    period_month  INT NOT NULL CHECK (period_month BETWEEN 1 AND 12),
    basic_pay     NUMERIC(12,2) NOT NULL,
    allowances    NUMERIC(12,2) NOT NULL DEFAULT 0,
    deductions    NUMERIC(12,2) NOT NULL DEFAULT 0,
    tax           NUMERIC(12,2) NOT NULL DEFAULT 0,
    net_pay       NUMERIC(12,2) NOT NULL,
    issued_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (employee_id, period_year, period_month)
);

CREATE INDEX idx_payslips_employee ON payslips(employee_id);
