package com.metroguards.reports.payslip.service;

import com.metroguards.reports.client.BackendClient;
import com.metroguards.reports.payslip.model.PayslipReport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayslipReportService {

    private final BackendClient backendClient;

    public PayslipReportService(BackendClient backendClient) {
        this.backendClient = backendClient;
    }

    public PayslipReport getPayslip(Long id, String token) {
        return backendClient.getPayslip(id, token);
    }

    public List<PayslipReport> getMyPayslips(String token) {
        return backendClient.getMyPayslips(token);
    }
}
