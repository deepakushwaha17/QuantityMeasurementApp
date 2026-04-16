package com.quantity.repository;

import com.quantity.models.MeasurementHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementHistoryRepository extends JpaRepository<MeasurementHistory, Long> {

    List<MeasurementHistory> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<MeasurementHistory> findByUserIdAndOperationOrderByCreatedAtDesc(
            Long userId, String operation);
}