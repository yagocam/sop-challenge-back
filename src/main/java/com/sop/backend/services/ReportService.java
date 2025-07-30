package com.sop.backend.services;

import com.sop.backend.dto.ExpenseDTO;
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

    public ReportService( ExpenseService expenseService) {
        this.expenseService = expenseService;
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
}
