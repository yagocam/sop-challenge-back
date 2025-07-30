package com.sop.backend.services;

import com.sop.backend.dto.CommitmentDTO;
import com.sop.backend.dto.ExpenseDTO;
import com.sop.backend.dto.PaymentDTO;
import com.sop.backend.models.Expense;
import com.sop.backend.repositories.ExpenseRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ExpenseService expenseService;
    private final CommitmentService commitmentService;
    private final PaymentService paymentService;
    public ReportService( ExpenseService expenseService, CommitmentService commitmentService, PaymentService paymentService ) {
        this.expenseService = expenseService;
        this.commitmentService = commitmentService;
        this.paymentService = paymentService;
    }

    public byte[] generateExpensesReport() throws JRException {
        List<ExpenseDTO> expenseDTOList  = expenseService.findAll();
        List<Map<String, Object>> data = expenseDTOList.stream().map(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("protocol_number", dto.getProtocol_number());
            map.put("description", dto.getDescription());
            map.put("amount", dto.getAmount());
            map.put("status", dto.getStatus() != null ? dto.getStatus().name() : "");
            return map;
        }).toList();

        InputStream reportStream = getClass().getResourceAsStream("/reports/expenses_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    public byte[] generateCommitmentsReport() throws JRException {
        List<CommitmentDTO> commitmentDTOList  = commitmentService.findAll();
        List<Map<String, Object>> data = commitmentDTOList.stream().map(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("number", dto.getNumber());
            map.put("observation", dto.getObservation());
            map.put("amount", dto.getAmount());
            map.put("created_at", dto.getCreated_at());
            return map;
        }).toList();

        InputStream reportStream = getClass().getResourceAsStream("/reports/commitments_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    public byte[] generatePaymentsReport() throws JRException {
        List<PaymentDTO> paymentDTOList  = paymentService.findAll();
        List<Map<String, Object>> data = paymentDTOList.stream().map(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("number", dto.getNumber());
            map.put("observation", dto.getObservation());
            map.put("amount", dto.getAmount());
            map.put("created_at", dto.getCreated_at());
            return map;
        }).toList();

        InputStream reportStream = getClass().getResourceAsStream("/reports/payments_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
