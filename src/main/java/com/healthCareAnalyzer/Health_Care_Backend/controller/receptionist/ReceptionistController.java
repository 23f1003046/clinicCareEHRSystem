package com.healthCareAnalyzer.Health_Care_Backend.controller.receptionist;

import com.healthCareAnalyzer.Health_Care_Backend.dto.prescription.UpdateStageByAppointmentIdDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.appointment.AppointmentService;
import com.healthCareAnalyzer.Health_Care_Backend.service.prescription.PrescriptionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receptionist")
@PreAuthorize("hasAuthority('ROLE_RECEPTIONIST')")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class ReceptionistController {

    private final PrescriptionService prescriptionService;
    private final AppointmentService appointmentService;

    public ReceptionistController(PrescriptionService prescriptionService, AppointmentService appointmentService) {
        this.prescriptionService = prescriptionService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/getAllPendingPrescriptions")
    public ResponseEntity<?> getAllPendingPrescriptions() {
        return prescriptionService.getAllPendingPrescriptions();
    }

    @PostMapping("/updateStage")
    public ResponseEntity<?> updateStage(@Valid @RequestBody UpdateStageByAppointmentIdDto updateStageByAppointmentIdDto) {
        return appointmentService.updateStageToCompletedByAppointmentId(updateStageByAppointmentIdDto);
    }

}
