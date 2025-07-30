package com.sop.backend.dto;


import com.sop.backend.Enum.ExpenseStatus;
import com.sop.backend.Enum.ExpenseType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleExpenseDTO {
    private Long id;
    private String protocol_number;
    private ExpenseType type;
    private LocalDateTime created_at;
    private LocalDateTime expires_at;
    private String responsable;
    private String description;
    private BigDecimal amount;
    private ExpenseStatus status;

}
