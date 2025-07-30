package com.sop.backend.controllers;

import com.sop.backend.Enum.ExpenseType;
import com.sop.backend.dto.ExpenseDTO;
import com.sop.backend.models.Expense;
import com.sop.backend.services.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseControllerTest {

    @InjectMocks
    private ExpenseController controller;

    @Mock
    private ExpenseService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        ExpenseDTO dto = buildExpenseDTO();
        Expense expense = buildExpense();

        when(service.create(dto)).thenReturn(expense);

        ResponseEntity<Expense> response = controller.create(dto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(expense, response.getBody());
    }

    @Test
    void testFindAll() {
        List<ExpenseDTO> list = List.of(buildExpenseDTO());

        when(service.findAll()).thenReturn(list);

        ResponseEntity<List<ExpenseDTO>> response = controller.findAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(list, response.getBody());
    }

    @Test
    void testFindById() {
        ExpenseDTO dto = buildExpenseDTO();

        when(service.findById(1L)).thenReturn(dto);

        ResponseEntity<ExpenseDTO> response = controller.findById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdate() {
        ExpenseDTO dto = buildExpenseDTO();

        when(service.update(eq(1L), any(ExpenseDTO.class))).thenReturn(dto);

        ResponseEntity<ExpenseDTO> response = controller.update(1L, dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(service).delete(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(service, times(1)).delete(1L);
    }

    private ExpenseDTO buildExpenseDTO() {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setType(ExpenseType.OUTROS);
        dto.setResponsable("João");
        dto.setDescription("Compra de papel");
        dto.setAmount(new BigDecimal("1000.00"));
        dto.setExpires_at(LocalDateTime.now().plusDays(10));
        return dto;
    }

    private Expense buildExpense() {
        Expense e = new Expense();
        e.setId(1L);
        e.setProtocol_number("12345.123456/2025-01");
        e.setType(ExpenseType.OBRA_DE_EDIFICACAO);
        e.setResponsable("João");
        e.setDescription("Compra de papel");
        e.setAmount(new BigDecimal("1000.00"));
        e.setCreated_at(LocalDateTime.now());
        e.setExpires_at(LocalDateTime.now().plusDays(10));
        e.setStatus(e.getStatus());
        return e;
    }
}
