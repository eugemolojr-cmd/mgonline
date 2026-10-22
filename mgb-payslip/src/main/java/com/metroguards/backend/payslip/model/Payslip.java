package com.metroguards.backend.payslip.model;

import com.metroguards.backend.employee.model.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payslips")
@Getter
@Setter
@NoArgsConstructor
public class Payslip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private int periodYear;

    @Column(nullable = false)
    private int periodMonth;

    @Column(nullable = false)
    private BigDecimal basicPay;

    @Column(nullable = false)
    private BigDecimal allowances;

    @Column(nullable = false)
    private BigDecimal deductions;

    @Column(nullable = false)
    private BigDecimal tax;

    @Column(nullable = false)
    private BigDecimal netPay;

    @Column(nullable = false)
    private Instant issuedAt = Instant.now();
}
