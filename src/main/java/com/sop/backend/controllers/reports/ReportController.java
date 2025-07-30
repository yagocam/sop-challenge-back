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
}
