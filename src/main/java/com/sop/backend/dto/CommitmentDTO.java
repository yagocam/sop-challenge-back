package com.sop.backend.dto;

import com.sop.backend.dto.list.ListPaymentDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommitmentDTO {
    private Long id;
    private String number;
    private LocalDateTime created_at;
    private BigDecimal amount;
    private String observation;

    private SimpleExpenseDTO expense;

    @NotNull
    private Long expense_id;

    @Builder.Default
    private List<ListPaymentDTO> payments = new ArrayList<>();

}