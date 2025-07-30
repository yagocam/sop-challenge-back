package com.sop.backend.controllers;

import com.sop.backend.dto.CommitmentDTO;
import com.sop.backend.dto.ExpenseDTO;
import com.sop.backend.models.Expense;
import com.sop.backend.services.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor

public class ExpenseController {
    @Autowired
    private final ExpenseService service;

    @Operation(summary = "Cria uma nova despesa")
    @ApiResponse(responseCode = "201", description = "Despesa criada com sucesso")
    @PostMapping
    public ResponseEntity<Expense> create(@RequestBody @Valid ExpenseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @Operation(summary = "Lista todas as despesas")
    @ApiResponse(responseCode = "200", description = "Lista de despesas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Busca uma despesa pelo ID")
    @ApiResponse(responseCode = "200", description = "Despesa encontrada")
    @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Atualiza uma despesa existente")
    @ApiResponse(responseCode = "200", description = "Despesa atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> update(@PathVariable Long id, @RequestBody @Valid ExpenseDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Exclui uma despesa pelo ID")
    @ApiResponse(responseCode = "204", description = "Despesa excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
