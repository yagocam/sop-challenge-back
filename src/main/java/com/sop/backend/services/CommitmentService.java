package com.sop.backend.services;

import com.sop.backend.dto.CommitmentDTO;
import com.sop.backend.dto.PaymentDTO;
import com.sop.backend.dto.list.ListPaymentDTO;
import com.sop.backend.mapper.CommitmentMapper;
import com.sop.backend.mapper.PaymentMapper;
import com.sop.backend.models.Commitment;
import com.sop.backend.models.Expense;
import com.sop.backend.models.Payment;
import com.sop.backend.repositories.CommitmentRepository;
import com.sop.backend.repositories.ExpenseRepository;
import com.sop.backend.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Objects;

@Service
public class CommitmentService {
    @Autowired
    private CommitmentRepository repository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    PaymentRepository paymentRepository;

    public List<CommitmentDTO> findAll() {
        return repository.findAll().stream().map(CommitmentMapper.INSTANCE::toDto).toList();
    }

    public CommitmentDTO findById(Long id) {
        Commitment commitment = repository.findById(id).orElseThrow(() -> new RuntimeException("Empenho não encontrado!"));
        return CommitmentMapper.INSTANCE.toDto(commitment);
    }


    @Transactional
    public CommitmentDTO create(CommitmentDTO commitmentDTO) {
        Commitment commitment = CommitmentMapper.INSTANCE.toEntity(commitmentDTO);

        commitment.setNumber(this.generateCommitmentNumber());
        commitment.setCreated_at(LocalDateTime.now());
        Expense expense = expenseRepository.findById(commitmentDTO.getExpense_id()).orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        if (!this.checkTotalAmount(expense, commitmentDTO)) {
            throw new RuntimeException("Total empenhado ultrapassa o valor da despesa.");

        }
        expense.getCommitments().add(commitment);
        expense.setStatus(expense.getStatus());
        commitment.setExpense(expense);
        commitment = repository.save(commitment);

        return CommitmentMapper.INSTANCE.toDto(commitment);
    }

    private String generateCommitmentNumber() {
        int year = Year.now().getValue();

        long count = repository.countByYear(year) + 1;

        String sequence = String.format("%04d", count);

        return year + "NE" + sequence;
    }


    @Transactional
    public void delete(Long id) {
        Commitment commitment = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Empenho não encontrado"));

        if (!commitment.getPayments().isEmpty()) {
            throw new IllegalStateException("Não é permitido excluir um empenho com pagamentos associados.");
        }

        repository.delete(commitment);
    }

    public CommitmentDTO update(Long id, CommitmentDTO dto) {
        ;
        Commitment commitment = repository.findById(id).orElseThrow(() -> new RuntimeException("Empenho não encontrado"));

        commitment.setCreated_at(dto.getCreated_at());
        commitment.setAmount(dto.getAmount());
        commitment.setObservation(dto.getObservation());


        return CommitmentMapper.INSTANCE.toDto(repository.save(commitment));
    }

    public boolean checkTotalAmount(Expense expense, CommitmentDTO dto) {
        BigDecimal totalCommitment = expense.getCommitments().stream().map(Commitment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAmount = totalCommitment.add(dto.getAmount());

        return totalAmount.compareTo(expense.getAmount()) <= 0;
    }
}
