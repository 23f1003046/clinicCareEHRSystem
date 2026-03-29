package com.healthCareAnalyzer.Health_Care_Backend.controller.patient;

import com.healthCareAnalyzer.Health_Care_Backend.dto.patient.BookAppointmentRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.patient.GetAllDoctorsResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.patient.GetOpenSlotsRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.appointment.AppointmentService;
import com.healthCareAnalyzer.Health_Care_Backend.service.patient.PatientAppointmentBookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient/appointment")
@PreAuthorize("hasAuthority('ROLE_PATIENT')")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class PatientAppointmentController {

    private final PatientAppointmentBookingService patientAppointmentBookingService;
    private final AppointmentService appointmentService;

    public PatientAppointmentController(PatientAppointmentBookingService patientAppointmentBookingService, AppointmentService appointmentService) {
        this.patientAppointmentBookingService = patientAppointmentBookingService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/getAllDoctors")
    public ResponseEntity<?> getAllDoctors() {

        List<GetAllDoctorsResponseDto> doctorsDtoList = patientAppointmentBookingService.getAllDoctors();
        return ResponseEntity.status(HttpStatus.OK).body(doctorsDtoList);

    }

    @PostMapping("/getOpenSlots")
    public ResponseEntity<?> getOpenSlots(@Valid @RequestBody GetOpenSlotsRequestDto openSlotsRequestDto) {

        return patientAppointmentBookingService.getOpenSlots(openSlotsRequestDto);

    }

    @PostMapping("/bookAppointment")
    public ResponseEntity<?> bookAppointment(@Valid @RequestBody BookAppointmentRequestDto bookAppointmentRequestDto, HttpServletRequest httpServletRequest) {

        return patientAppointmentBookingService.bookAppointment(bookAppointmentRequestDto, httpServletRequest);

    }

    @GetMapping("/getAppointmentDetails")
    public ResponseEntity<?> getAppointmentDetails(HttpServletRequest httpServletRequest) {
        return appointmentService.getAppointmentDetailsByPatientId(httpServletRequest);
    }
}


