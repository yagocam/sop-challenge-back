package com.sop.backend.repositories;

import com.sop.backend.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository  extends JpaRepository<Expense, Long> {
}
