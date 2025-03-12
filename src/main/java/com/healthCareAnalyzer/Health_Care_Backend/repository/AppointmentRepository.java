package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    @Query(" SELECT a.slot.slotId FROM AppointmentEntity a WHERE a.doctor.doctorId=?1 and a.dateOfAppointment=?2 and (a.stage='approval' or a.stage='diagnose') ")
    List<Long> findOpenSlots(@NotNull Long doctorId, @NotNull LocalDate dateOfAppointment);

    @Query("SELECT a.stage FROM AppointmentEntity a WHERE a.patient.patientId=?1")
    List<String> findStageByPatientId(Long patientId);


    @Transactional
    @Modifying
    @Query("DELETE FROM AppointmentEntity a WHERE a.stage='approval' and a.dateOfAppointment<?1")
    void deleteUnapprovedAppointments(LocalDate localDate);

    @Query("SELECT new AppointmentEntity (a.appointmentId,a.patient,a.slot,a.dateOfAppointment,a.dateOfBooking) FROM AppointmentEntity a WHERE a.doctor.userEntity.username=?1 and a.stage='approval' ")
    List<AppointmentEntity> findAppointmentsWaitingForApproval(String username);

    @Transactional
    @Modifying
    @Query("UPDATE AppointmentEntity a SET a.stage=?3 WHERE a.appointmentId=?1 and a.doctor.userEntity.username=?2")
    int updateStageByIdAndDoctor(@NotBlank Long appointmentId, String username, String stageName);

    @Query("SELECT new AppointmentEntity (a.appointmentId,a.patient,a.slot,a.dateOfAppointment,a.dateOfBooking) FROM AppointmentEntity a WHERE a.doctor.userEntity.username=?1 and a.stage in ('doctor_v1','doctor_v2') ")
    List<AppointmentEntity> findAllUpcomingAppointments(String username);

    List<AppointmentEntity> findByAppointmentIdAndDoctor_UserEntity_Username(Long appointmentId, String username);

    Optional<AppointmentEntity> findByAppointmentIdAndDoctor_UserEntity_UsernameAndStageNotIn(Long appointmentId, String doctor_userEntity_username, Collection<String> stage);

    List<AppointmentEntity> findByPatient_UserEntity_Username(String username);

    Optional<AppointmentEntity> findByPatient_UserEntity_UsernameAndStage(String username, String receptionist);
}
