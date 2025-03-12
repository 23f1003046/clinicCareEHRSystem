package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillEntity,Long> {
}
