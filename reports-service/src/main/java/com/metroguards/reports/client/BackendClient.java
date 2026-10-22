package com.metroguards.reports.client;

import com.metroguards.reports.payslip.model.PayslipReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/** Calls backend-service on behalf of the user by forwarding their JWT. */
@Component
public class BackendClient {

    private final RestClient rest;

    public BackendClient(RestClient.Builder builder, @Value("${app.backend.base-url}") String baseUrl) {
        this.rest = builder.baseUrl(baseUrl).build();
    }

    public PayslipReport getPayslip(Long id, String token) {
        return rest.get().uri("/api/payslips/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(PayslipReport.class);
    }

    public List<PayslipReport> getMyPayslips(String token) {
        return rest.get().uri("/api/payslips/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<List<PayslipReport>>() {});
    }
}
