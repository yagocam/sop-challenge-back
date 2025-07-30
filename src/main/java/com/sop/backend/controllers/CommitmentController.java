package com.sop.backend.controllers;

import com.sop.backend.dto.CommitmentDTO;
import com.sop.backend.dto.ExpenseDTO;
import com.sop.backend.dto.PaymentDTO;
import com.sop.backend.services.CommitmentService;
import com.sop.backend.services.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


    @Operation(summary = "Cria um novo compromisso")
    @ApiResponse(responseCode = "200", description = "Compromisso criado com sucesso")
    @PostMapping
    public ResponseEntity<CommitmentDTO> create(@RequestBody @Valid CommitmentDTO dto) {

        return ResponseEntity.ok(service.create(dto));
    }
    @Operation(summary = "Lista todos os compromissos")
    @ApiResponse(responseCode = "200", description = "Lista de compromissos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<CommitmentDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Busca um compromisso pelo ID")
    @ApiResponse(responseCode = "200", description = "Compromisso encontrado")
    @ApiResponse(responseCode = "404", description = "Compromisso não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<CommitmentDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @Operation(summary = "Atualiza um compromisso existente")
    @ApiResponse(responseCode = "200", description = "Compromisso atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Compromisso não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<CommitmentDTO> update(@PathVariable Long id, @RequestBody @Valid CommitmentDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    @Operation(summary = "Exclui um compromisso pelo ID")
    @ApiResponse(responseCode = "204", description = "Compromisso excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Compromisso não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
