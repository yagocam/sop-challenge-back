package com.sop.backend.controllers;

import com.sop.backend.dto.CommitmentDTO;
import com.sop.backend.dto.ExpenseDTO;
import com.sop.backend.dto.PaymentDTO;
import com.sop.backend.services.CommitmentService;
import com.sop.backend.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commitments")
@RequiredArgsConstructor
public class CommitmentController {
    private final CommitmentService service;

    @PostMapping
    public ResponseEntity<CommitmentDTO> create(@RequestBody @Valid CommitmentDTO dto) {

        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CommitmentDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommitmentDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommitmentDTO> update(@PathVariable Long id, @RequestBody @Valid CommitmentDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
