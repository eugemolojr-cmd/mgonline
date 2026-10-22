package com.metroguards.backend.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequest(
        @NotBlank String username,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        String jobTitle,
        String department,
        LocalDate hireDate,
        @NotNull @Positive BigDecimal monthlySalary) {
}
