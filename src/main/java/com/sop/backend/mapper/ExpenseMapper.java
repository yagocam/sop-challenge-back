package com.sop.backend.mapper;

import com.sop.backend.dto.ExpenseDTO;
import com.sop.backend.models.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

     ExpenseDTO toDto(Expense expense) ;

     Expense toEntity(ExpenseDTO expenseDTO) ;
}
