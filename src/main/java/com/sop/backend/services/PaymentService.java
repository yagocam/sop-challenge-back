package com.sop.backend.services;

import com.sop.backend.dto.PaymentDTO;
import com.sop.backend.mapper.CommitmentMapper;
import com.sop.backend.mapper.PaymentMapper;
import com.sop.backend.models.Commitment;
import com.sop.backend.models.Expense;
import com.sop.backend.models.Payment;
import com.sop.backend.repositories.CommitmentRepository;
import com.sop.backend.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;
    @Autowired
    private CommitmentRepository commitmentRepository;

    public List<PaymentDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(PaymentMapper.INSTANCE::toDto)
                .toList();
    }

    public PaymentDTO findById(Long id) {
        Payment payment = repository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado!"));
        return PaymentMapper.INSTANCE.toDto(payment);
    }

    @Transactional
    public PaymentDTO create(PaymentDTO paymentDTO) {
        Commitment commitment = commitmentRepository.findById(paymentDTO.getCommitment_id()).orElseThrow(()
                -> new EntityNotFoundException("Empenho não encontrado"));
        if (!checkCommitment(commitment)) {
            throw new IllegalArgumentException("A soma dos pagamentos ultrapassa o valor do empenho.");
        }
        paymentDTO.setNumber(generatePaymentNumber());
        paymentDTO.setCommitment(CommitmentMapper.INSTANCE.toSimpleDTO(commitment));
        paymentDTO.setCommitment_id(paymentDTO.getCommitment_id());
        paymentDTO.setCreated_at(LocalDateTime.now());
        Payment saved = repository.save(PaymentMapper.INSTANCE.toEntity(paymentDTO));
        commitment.getPayments().add(saved);
       Expense expense = commitment.getExpense();
       expense.setStatus(expense.getStatus());
        return PaymentMapper.INSTANCE.toDto(saved);
    }

    private boolean checkCommitment(Commitment commitment) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        if (commitment.getPayments() != null && !commitment.getPayments().isEmpty()) {
            for (Payment p : commitment.getPayments()) {
                totalAmount = totalAmount.add(p.getAmount());
            }
        }

        assert commitment.getPayments() != null;
        BigDecimal totalPayment = commitment.getPayments().stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = totalPayment.add(totalAmount);

        return total.compareTo(commitment.getAmount()) <= 0;
    }

    private String generatePaymentNumber() {
        int year = Year.now().getValue();

        long count = repository.countByYear(year) + 1;

        String sequence = String.format("%04d", count);

        return year + "NE" + sequence;
    }

    @Transactional
    public void delete(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        repository.delete(payment);
    }

    public PaymentDTO update(Long id, PaymentDTO dto) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        payment.setCreated_at(dto.getCreated_at());
        payment.setAmount(dto.getAmount());
        payment.setObservation(dto.getObservation());


        return PaymentMapper.INSTANCE.toDto(repository.save(payment));
    }
}
