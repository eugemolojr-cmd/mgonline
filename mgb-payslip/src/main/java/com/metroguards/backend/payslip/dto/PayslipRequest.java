package com.metroguards.backend.payslip.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PayslipRequest(
        @NotNull Long employeeId,
        @Min(2000) @Max(2100) int periodYear,
        @Min(1) @Max(12) int periodMonth,
        @NotNull @PositiveOrZero BigDecimal basicPay,
        @NotNull @PositiveOrZero BigDecimal allowances,
        @NotNull @PositiveOrZero BigDecimal deductions,
        @NotNull @PositiveOrZero BigDecimal tax) {
}
