package com.sop.backend.dto.list;

import com.sop.backend.dto.PaymentDTO;
import com.sop.backend.models.Expense;
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
public class ListCommitmentDTO {
    private Long id;
    private String number;
    private LocalDateTime created_at;
    private BigDecimal amount;
    private String observation;
    private Long expense_id;
}
