package com.sop.backend.repositories;

import com.sop.backend.models.Commitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommitmentRepository extends JpaRepository<Commitment, Long> {
    @Query("SELECT COUNT(c) FROM Commitment c WHERE FUNCTION('date_part', 'year', c.created_at) = :year")
    long countByYear(@Param("year") int year);

}
