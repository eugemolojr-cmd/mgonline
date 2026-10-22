package com.metroguards.reports.payslip.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.metroguards.reports.payslip.model.PayslipReport;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class PayslipPdfService {

    private static final Font TITLE = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    private static final Font BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
    private static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 11);

    public byte[] render(PayslipReport p) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc = new Document(PageSize.A4, 48, 48, 48, 48);
            PdfWriter.getInstance(doc, out);
            doc.open();

            String period = Month.of(p.periodMonth()).getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + p.periodYear();
            doc.add(new Paragraph("Metroguards - Payslip", TITLE));
            doc.add(new Paragraph("Pay period: " + period, NORMAL));
            doc.add(new Paragraph(" "));

            doc.add(new Paragraph("Employee: " + p.employeeName(), BOLD));
            doc.add(new Paragraph("Job title: " + nullToDash(p.jobTitle()) + "    Department: " + nullToDash(p.department()), NORMAL));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(new float[]{3f, 1.5f});
            table.setWidthPercentage(100);
            row(table, "Basic pay", p.basicPay(), false);
            row(table, "Allowances", p.allowances(), false);
            row(table, "Deductions", p.deductions(), false);
            row(table, "Tax", p.tax(), false);
            row(table, "NET PAY", p.netPay(), true);
            doc.add(table);

            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate payslip PDF", e);
        }
    }

    private void row(PdfPTable table, String label, BigDecimal amount, boolean emphasis) {
        Font f = emphasis ? BOLD : NORMAL;
        PdfPCell l = new PdfPCell(new Phrase(label, f));
        PdfPCell r = new PdfPCell(new Phrase(String.format("%,.2f", amount), f));
        r.setHorizontalAlignment(Element.ALIGN_RIGHT);
        if (emphasis) {
            l.setBackgroundColor(new Color(235, 235, 235));
            r.setBackgroundColor(new Color(235, 235, 235));
        }
        l.setPadding(6);
        r.setPadding(6);
        table.addCell(l);
        table.addCell(r);
    }

    private String nullToDash(String s) {
        return s == null ? "-" : s;
    }
}
