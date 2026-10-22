package com.metroguards.backend.employee.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String jobTitle,
        String department,
        LocalDate hireDate,
        BigDecimal monthlySalary) {
}
