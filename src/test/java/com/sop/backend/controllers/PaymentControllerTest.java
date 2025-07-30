package com.sop.backend.controllers;

import com.sop.backend.dto.PaymentDTO;
import com.sop.backend.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        // Arrange
        PaymentDTO request = createPaymentDTO();
        when(paymentService.create(any(PaymentDTO.class))).thenReturn(request);

        // Act
        ResponseEntity<PaymentDTO> response = paymentController.create(request);

        // Assert
        assertEquals(201, response.getStatusCode().value());
        assertEquals(request, response.getBody());
    }

    @Test
    void testFindAll() {
        List<PaymentDTO> list = List.of(createPaymentDTO());
        when(paymentService.findAll()).thenReturn(list);

        ResponseEntity<List<PaymentDTO>> response = paymentController.findAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(list, response.getBody());
    }

    @Test
    void testFindById() {
        PaymentDTO dto = createPaymentDTO();
        when(paymentService.findById(1L)).thenReturn(dto);

        ResponseEntity<PaymentDTO> response = paymentController.findById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdate() {
        PaymentDTO dto = createPaymentDTO();
        when(paymentService.update(eq(1L), any(PaymentDTO.class))).thenReturn(dto);

        ResponseEntity<PaymentDTO> response = paymentController.update(1L, dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(paymentService).delete(1L);

        ResponseEntity<Void> response = paymentController.delete(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(paymentService, times(1)).delete(1L);
    }

    private PaymentDTO createPaymentDTO() {
        PaymentDTO dto = new PaymentDTO();
        dto.setAmount(new BigDecimal("100.00"));
        dto.setObservation("Teste");
        dto.setCreated_at(LocalDateTime.now());
        return dto;
    }
}
