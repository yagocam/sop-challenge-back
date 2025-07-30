package com.sop.backend.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments", uniqueConstraints = @UniqueConstraint(columnNames = "number"))
@SQLDelete(sql = "UPDATE payments SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String number;
    private LocalDateTime created_at;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
    private String observation;
    @ManyToOne(optional = false)
    @JoinColumn(name = "commitment_id")
    private Commitment commitment;
}
