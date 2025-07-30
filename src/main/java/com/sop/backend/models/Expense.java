package com.sop.backend.models;

import com.sop.backend.Enum.ExpenseStatus;
import com.sop.backend.Enum.ExpenseType;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "expenses", uniqueConstraints = @UniqueConstraint(columnNames = "protocolNumber"))
@SQLDelete(sql = "UPDATE expenses SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String protocol_number;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ExpenseType type;
    private LocalDateTime created_at;
    @NotNull
    private LocalDateTime expires_at;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @NotBlank
    private String responsable;
    private String description;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    private ExpenseStatus status;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commitment> commitments = new ArrayList<>();

    public ExpenseStatus getStatus() {
        if (commitments.isEmpty()) return ExpenseStatus.WAITING_COMMITMENT;
        BigDecimal totalCommitment = this.commitments.stream()
                .map(Commitment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPago = this.commitments.stream()
                .flatMap(c -> c.getPayments().stream())
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalCommitment.compareTo(this.amount) < 0) return ExpenseStatus.PARTIALLY_COMMITED;
        if (totalCommitment.compareTo(this.amount) == 0 && totalPago.compareTo(BigDecimal.ZERO) == 0)
            return ExpenseStatus.WAITING_PAYMENT;
        if (totalPago.compareTo(this.amount) < 0) return ExpenseStatus.PARTIALLY_PAYED;
        if (totalPago.compareTo(this.amount) == 0) return ExpenseStatus.PAYED;
        return ExpenseStatus.WAITING_COMMITMENT;
    }
}
