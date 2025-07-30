package com.sop.backend.controllers;

import com.sop.backend.dto.CommitmentDTO;
import com.sop.backend.dto.PaymentDTO;
import com.sop.backend.services.CommitmentService;
import com.sop.backend.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor

public class PaymentController {
    private final PaymentService service;

    @Operation(summary = "Cria um novo pagamento")
    @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso")
    @PostMapping
    public ResponseEntity<PaymentDTO> create(@RequestBody @Valid PaymentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }
    @Operation(summary = "Lista todos os pagamentos")
    @ApiResponse(responseCode = "200", description = "Lista de pagamentos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<PaymentDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    @Operation(summary = "Busca um pagamento pelo ID")
    @ApiResponse(responseCode = "200", description = "Pagamento encontrado")
    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @Operation(summary = "Atualiza um pagamento existente")
    @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> update(@PathVariable Long id, @RequestBody @Valid PaymentDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    @Operation(summary = "Exclui um pagamento pelo ID")
    @ApiResponse(responseCode = "204", description = "Pagamento excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
