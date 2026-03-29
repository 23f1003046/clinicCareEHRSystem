package com.healthCareAnalyzer.Health_Care_Backend.service.databaseSchedulers;

import com.healthCareAnalyzer.Health_Care_Backend.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class AppointmentTableMaintenance {

    private final AppointmentRepository appointmentRepository;

    public AppointmentTableMaintenance(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Run every day at midnight
    @Scheduled(cron = "0 * * * * *")
    public void deleteOldRecords() {
        appointmentRepository.deleteUnapprovedAppointments(LocalDate.now());
        log.warn("Old records deleted successfully!");
    }
}

