package com.healthCareAnalyzer.Health_Care_Backend.controller.doctor;

import com.healthCareAnalyzer.Health_Care_Backend.dto.doctor.UpdateStageRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.appointment.AppointmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor/appointment")
@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class DoctorAppointmentsController {

    private final AppointmentService appointmentService;

    public DoctorAppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/getAllAppointmentsWaitingForApproval")
    public ResponseEntity<?> getAllAppointmentsWaitingForApproval(HttpServletRequest request) {
        return appointmentService.getAllAppointmentsWaitingForApproval(request);
    }

    @PostMapping("/updateStage")
    public ResponseEntity<?> updateStage(@Valid @RequestBody UpdateStageRequestDto updateStageRequestDtoList, HttpServletRequest request) {
        return appointmentService.updateStage(updateStageRequestDtoList, request);
    }

    @GetMapping("/getAllUpcomingAppointments")
    public ResponseEntity<?> getAllUpcomingAppointments(HttpServletRequest request) {
        return appointmentService.getAllUpcomingAppointments(request);
    }

    @GetMapping("/getUpcomingAppointmentBYId")
    public ResponseEntity<?> getUpcomingAppointmentById(@RequestParam(name = "aptId") Long appointmentId, HttpServletRequest request) {
        return appointmentService.getUpcomingAppointmentById(appointmentId, request);
    }


}
