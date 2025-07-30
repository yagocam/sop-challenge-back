package com.sop.backend.mapper;

import com.sop.backend.dto.CommitmentDTO;
import com.sop.backend.dto.ExpenseDTO;
import com.sop.backend.dto.SimpleCommitmentDTO;
import com.sop.backend.dto.list.ListCommitmentDTO;
import com.sop.backend.models.Commitment;
import com.sop.backend.models.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommitmentMapper {
    CommitmentMapper INSTANCE = Mappers.getMapper(CommitmentMapper.class);

    CommitmentDTO toDto(Commitment commitment);

    Commitment toEntity(CommitmentDTO commitmentDTO);

    Commitment toEntity(ListCommitmentDTO commitmentDTO);
    SimpleCommitmentDTO toSimpleDTO(Commitment commitment);

}
