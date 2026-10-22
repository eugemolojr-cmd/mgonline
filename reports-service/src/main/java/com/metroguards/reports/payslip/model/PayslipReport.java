package com.metroguards.reports.payslip.model;

import java.math.BigDecimal;
import java.time.Instant;

/** Mirrors backend-service's PayslipResponse. */
public record PayslipReport(
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
