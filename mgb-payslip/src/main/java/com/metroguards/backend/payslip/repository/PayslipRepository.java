package com.metroguards.backend.payslip.repository;

import com.metroguards.backend.payslip.model.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayslipRepository extends JpaRepository<Payslip, Long> {

    List<Payslip> findByEmployeeUsernameOrderByPeriodYearDescPeriodMonthDesc(String username);

    List<Payslip> findByEmployeeIdOrderByPeriodYearDescPeriodMonthDesc(Long employeeId);

    boolean existsByEmployeeIdAndPeriodYearAndPeriodMonth(Long employeeId, int year, int month);
}
