package com.metroguards.reports.payslip.controller;

import com.metroguards.reports.payslip.model.PayslipReport;
import com.metroguards.reports.payslip.service.PayslipPdfService;
import com.metroguards.reports.payslip.service.PayslipReportService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports/payslips")
public class PayslipReportController {

    private final PayslipReportService reportService;
    private final PayslipPdfService pdfService;

    public PayslipReportController(PayslipReportService reportService, PayslipPdfService pdfService) {
        this.reportService = reportService;
        this.pdfService = pdfService;
    }

    @GetMapping("/me")
    public List<PayslipReport> mine(@AuthenticationPrincipal Jwt jwt) {
        return reportService.getMyPayslips(jwt.getTokenValue());
    }

    @GetMapping("/{id}")
    public PayslipReport get(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return reportService.getPayslip(id, jwt.getTokenValue());
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> pdf(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        PayslipReport payslip = reportService.getPayslip(id, jwt.getTokenValue());
        byte[] pdf = pdfService.render(payslip);
        String filename = "payslip-%d-%02d.pdf".formatted(payslip.periodYear(), payslip.periodMonth());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(filename).build().toString())
                .body(pdf);
    }
}
