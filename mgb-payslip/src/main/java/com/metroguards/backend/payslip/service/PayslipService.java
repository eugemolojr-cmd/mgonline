package com.metroguards.backend.payslip.service;

import com.metroguards.backend.common.exception.NotFoundException;
import com.metroguards.backend.employee.model.Employee;
import com.metroguards.backend.employee.repository.EmployeeRepository;
import com.metroguards.backend.payslip.dto.PayslipRequest;
import com.metroguards.backend.payslip.dto.PayslipResponse;
import com.metroguards.backend.payslip.model.Payslip;
import com.metroguards.backend.payslip.repository.PayslipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PayslipService {

    private final PayslipRepository payslipRepository;
    private final EmployeeRepository employeeRepository;

    /** Payslips of the logged-in user (matched on Keycloak preferred_username). */
    public List<PayslipResponse> findForUsername(String username) {
        return payslipRepository.findByEmployeeUsernameOrderByPeriodYearDescPeriodMonthDesc(username)
                .stream().map(this::toResponse).toList();
    }

    public List<PayslipResponse> findForEmployee(Long employeeId) {
        return payslipRepository.findByEmployeeIdOrderByPeriodYearDescPeriodMonthDesc(employeeId)
                .stream().map(this::toResponse).toList();
    }

    /** HR can read any payslip; everyone else only their own. */
    public PayslipResponse findById(Long id, String username, boolean hrAdmin) {
        Payslip p = payslipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payslip " + id + " not found"));
        if (!hrAdmin && !p.getEmployee().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only view your own payslips");
        }
        return toResponse(p);
    }

    @Transactional
    public PayslipResponse create(PayslipRequest req) {
        Employee employee = employeeRepository.findById(req.employeeId())
                .orElseThrow(() -> new NotFoundException("Employee " + req.employeeId() + " not found"));
        if (payslipRepository.existsByEmployeeIdAndPeriodYearAndPeriodMonth(
                employee.getId(), req.periodYear(), req.periodMonth())) {
            throw new DataIntegrityViolationException("Payslip already exists for this period");
        }
        Payslip p = new Payslip();
        p.setEmployee(employee);
        p.setPeriodYear(req.periodYear());
        p.setPeriodMonth(req.periodMonth());
        p.setBasicPay(req.basicPay());
        p.setAllowances(req.allowances());
        p.setDeductions(req.deductions());
        p.setTax(req.tax());
        p.setNetPay(req.basicPay().add(req.allowances()).subtract(req.deductions()).subtract(req.tax()));
        return toResponse(payslipRepository.save(p));
    }

    private PayslipResponse toResponse(Payslip p) {
        Employee e = p.getEmployee();
        return new PayslipResponse(p.getId(), e.getId(), e.getFirstName() + " " + e.getLastName(), e.getUsername(),
                e.getJobTitle(), e.getDepartment(), p.getPeriodYear(), p.getPeriodMonth(), p.getBasicPay(),
                p.getAllowances(), p.getDeductions(), p.getTax(), p.getNetPay(), p.getIssuedAt());
    }
}
