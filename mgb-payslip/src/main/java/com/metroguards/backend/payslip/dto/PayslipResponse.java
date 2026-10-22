package com.metroguards.backend.payslip.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PayslipResponse(
        Long id,
        Long employeeId,
        String employeeName,
        String username,
        String jobTitle,
        String department,
        int periodYear,
        int periodMonth,
        BigDecimal basicPay,
        BigDecimal allowances,
        BigDecimal deductions,
        BigDecimal tax,
        BigDecimal netPay,
        Instant issuedAt) {
}
