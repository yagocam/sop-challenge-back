package com.sop.backend.controllers.reports;

import com.sop.backend.services.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/expenses/pdf")
    public ResponseEntity<byte[]> getExpensesReport() throws JRException {
        byte[] data = reportService.generateExpensesReport();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }
    @GetMapping("/commitments/pdf")
    public ResponseEntity<byte[]> getCommitmentsReport() throws JRException {
        byte[] data = reportService.generateCommitmentsReport();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=commitments_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }
    @GetMapping("/payments/pdf")
    public ResponseEntity<byte[]> getPaymentsReport() throws JRException {
        byte[] data = reportService.generatePaymentsReport();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payments_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }
}
