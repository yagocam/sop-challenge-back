package com.sop.backend.mapper;

import com.sop.backend.dto.CommitmentDTO;
import com.sop.backend.dto.PaymentDTO;
import com.sop.backend.dto.list.ListPaymentDTO;
import com.sop.backend.models.Commitment;
import com.sop.backend.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDTO toDto(Payment payment);

    Payment toEntity(PaymentDTO paymentDTO);

    Payment toEntity(ListPaymentDTO dto);
}
