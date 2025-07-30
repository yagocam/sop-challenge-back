package com.sop.backend.repositories;

import com.sop.backend.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT COUNT(c) FROM Payment c WHERE FUNCTION('date_part', 'year', c.created_at) = :year")
    long countByYear(@Param("year") int year);
}
