package com.sop.backend.dto;

import com.sop.backend.Enum.ExpenseStatus;
import com.sop.backend.Enum.ExpenseType;
import com.sop.backend.dto.list.ListCommitmentDTO;
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
public class ExpenseDTO {
    private Long id;
    private String protocol_number;
    private ExpenseType type;
    private LocalDateTime created_at;
    private LocalDateTime expires_at;
    private String responsable;
    private String description;
    private BigDecimal amount;
    private ExpenseStatus status;
    @Builder.Default
    private List<ListCommitmentDTO> commitments = new ArrayList<>();

}
