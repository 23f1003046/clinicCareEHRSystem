package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.PhlebotomistTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhlebotomistTestRepository extends JpaRepository<PhlebotomistTestEntity, Long> {
    List<PhlebotomistTestEntity> findByAppointmentEntity_Stage(String phlebotomist);

    Optional<PhlebotomistTestEntity> findByAppointmentEntity(AppointmentEntity appointmentEntityData);
}
