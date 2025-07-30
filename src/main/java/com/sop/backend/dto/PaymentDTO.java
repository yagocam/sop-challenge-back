package com.sop.backend.dto;

import com.sop.backend.dto.list.ListCommitmentDTO;
import com.sop.backend.models.Commitment;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO
{
    private Long id;
    private String number;
    private LocalDateTime created_at;
    private BigDecimal amount;
    private String observation;
    @NotNull
    private Long commitment_id;
    private SimpleCommitmentDTO commitment;


}
