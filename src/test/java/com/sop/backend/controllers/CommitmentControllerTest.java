package com.sop.backend.controllers;

import com.sop.backend.dto.CommitmentDTO;
import com.sop.backend.services.CommitmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommitmentControllerTest {

    @InjectMocks
    private CommitmentController controller;

    @Mock
    private CommitmentService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        CommitmentDTO input = buildCommitmentDTO();
        CommitmentDTO saved = buildCommitmentDTO();
        saved.setNumber("2025NE0001");

        when(service.create(input)).thenReturn(saved);

        ResponseEntity<CommitmentDTO> response = controller.create(input);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(saved, response.getBody());
    }

    @Test
    void testFindAll() {
        List<CommitmentDTO> list = List.of(buildCommitmentDTO());

        when(service.findAll()).thenReturn(list);

        ResponseEntity<List<CommitmentDTO>> response = controller.findAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(list, response.getBody());
    }

    @Test
    void testFindById() {
        CommitmentDTO dto = buildCommitmentDTO();

        when(service.findById(1L)).thenReturn(dto);

        ResponseEntity<CommitmentDTO> response = controller.findById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdate() {
        CommitmentDTO dto = buildCommitmentDTO();

        when(service.update(eq(1L), any(CommitmentDTO.class))).thenReturn(dto);

        ResponseEntity<CommitmentDTO> response = controller.update(1L, dto);

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

    private CommitmentDTO buildCommitmentDTO() {
        CommitmentDTO dto = new CommitmentDTO();
        dto.setId(1L);
        dto.setAmount(new BigDecimal("1500.00"));
        dto.setObservation("Observação de teste");
        dto.setCreated_at(LocalDateTime.now());
        dto.setExpense_id(10L);
        return dto;
    }
}
