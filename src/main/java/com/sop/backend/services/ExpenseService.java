package com.sop.backend.services;

import com.sop.backend.dto.ExpenseDTO;
import com.sop.backend.mapper.ExpenseMapper;
import com.sop.backend.models.Commitment;
import com.sop.backend.models.Expense;
import com.sop.backend.repositories.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository repository;
    private CommitmentService commitmentService;

    public List<ExpenseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ExpenseMapper.INSTANCE::toDto)
                .toList();
    }

    public ExpenseDTO findById(Long id) {
        Expense expense = repository.findById(id).orElseThrow(() -> new RuntimeException("Despesa não encontrada!"));
        return ExpenseMapper.INSTANCE.toDto(expense);
    }

    @Transactional
    public Expense create(ExpenseDTO dto) {
        Expense expense = new Expense();

        expense.setProtocol_number(generateProtocolNumber());
        expense.setType(dto.getType());
        expense.setCreated_at(LocalDateTime.now());
        expense.setExpires_at(dto.getExpires_at());
        expense.setResponsable(dto.getResponsable());
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setStatus(expense.getStatus());

        return repository.save(expense);
    }


    @Transactional
    public void delete(Long id) {
        Expense expense = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        if (!expense.getCommitments().isEmpty()) {
            throw new IllegalStateException("Não é permitido excluir uma Despesa com Empenhos associados.");
        }

        repository.delete(expense);
    }

    public ExpenseDTO update(Long id, ExpenseDTO dto) {
        Expense expense = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        BigDecimal existingCommitmentsTotal = expense.getCommitments().stream()
                .map(Commitment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (dto.getAmount().compareTo(existingCommitmentsTotal) < 0) {
            throw new RuntimeException("O novo valor da despesa é menor que a soma dos compromissos existentes.");
        }
        expense.setCreated_at(LocalDateTime.now());
        expense.setAmount(dto.getAmount());
        expense.setExpires_at(dto.getExpires_at());
        expense.setType(dto.getType());
        expense.setResponsable(dto.getResponsable());
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setStatus(dto.getStatus());

        return ExpenseMapper.INSTANCE.toDto(repository.save(expense));
    }


    private String generateProtocolNumber() {
        String year = String.valueOf(LocalDate.now().getYear());
        String randomPart1 = String.format("%05d", new Random().nextInt(99999));
        String randomPart2 = String.format("%06d", new Random().nextInt(999999));
        String suffix = String.format("%02d", new Random().nextInt(99));

        return String.format("%s.%s/%s-%s", randomPart1, randomPart2, year, suffix);
    }
}
