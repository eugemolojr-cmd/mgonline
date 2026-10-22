package com.metroguards.backend.payslip.controller;

import com.metroguards.backend.common.security.AuthUtils;
import com.metroguards.backend.payslip.dto.PayslipRequest;
import com.metroguards.backend.payslip.dto.PayslipResponse;
import com.metroguards.backend.payslip.service.PayslipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payslips")
@RequiredArgsConstructor
public class PayslipController {

    private final PayslipService service;

    /** Logged-in employee's own payslips. */
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('EMPLOYEE','HR_ADMIN')")
    public List<PayslipResponse> mine(Authentication auth) {
        return service.findForUsername(auth.getName());
    }

    /** HR: payslips of a given employee. */
    @GetMapping
    @PreAuthorize("hasRole('HR_ADMIN')")
    public List<PayslipResponse> byEmployee(@RequestParam Long employeeId) {
        return service.findForEmployee(employeeId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','HR_ADMIN')")
    public PayslipResponse get(@PathVariable Long id, Authentication auth) {
        return service.findById(id, auth.getName(), AuthUtils.isHrAdmin(auth));
    }

    @PostMapping
    @PreAuthorize("hasRole('HR_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public PayslipResponse create(@Valid @RequestBody PayslipRequest request) {
        return service.create(request);
    }
}
