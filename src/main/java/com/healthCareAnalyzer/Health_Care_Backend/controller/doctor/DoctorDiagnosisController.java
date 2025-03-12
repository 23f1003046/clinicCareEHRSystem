package com.healthCareAnalyzer.Health_Care_Backend.controller.doctor;

import com.healthCareAnalyzer.Health_Care_Backend.dto.doctor.UpdateStageRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.doctorFeedback.CreateDoctorFeedbackRecordDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.phlebotomist.CreatePhlebotomistTestRecordDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.prescription.CreatePrescriptionRecordDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.appointment.AppointmentService;
import com.healthCareAnalyzer.Health_Care_Backend.service.doctorFeedback.DoctorFeedbackService;
import com.healthCareAnalyzer.Health_Care_Backend.service.labTest.LabTestService;
import com.healthCareAnalyzer.Health_Care_Backend.service.medicineInventory.MedicineInventoryService;
import com.healthCareAnalyzer.Health_Care_Backend.service.phlebotomistTest.PhlebotomistTestService;
import com.healthCareAnalyzer.Health_Care_Backend.service.prescription.PrescriptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor/diagnose")
@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
@Slf4j
public class DoctorDiagnosisController {

    private final LabTestService labTestService;
    private final MedicineInventoryService medicineInventoryService;
    private final AppointmentService appointmentService;
    private final PhlebotomistTestService phlebotomistTestService;
    private final PrescriptionService prescriptionService;
    private final DoctorFeedbackService doctorFeedbackService;

    public DoctorDiagnosisController(LabTestService labTestService, MedicineInventoryService medicineInventoryService, AppointmentService appointmentService, PhlebotomistTestService phlebotomistTestService, PrescriptionService prescriptionService, DoctorFeedbackService doctorFeedbackService) {
        this.labTestService = labTestService;
        this.medicineInventoryService = medicineInventoryService;
        this.appointmentService = appointmentService;
        this.phlebotomistTestService = phlebotomistTestService;
        this.prescriptionService = prescriptionService;
        this.doctorFeedbackService = doctorFeedbackService;
    }

    @GetMapping("/getAllLabTests")
    public ResponseEntity<?> getAllLabTests() {
        return labTestService.getAllLabTests();
    }

    @GetMapping("/getAllMedicineInventory")
    public ResponseEntity<?> getAllMedicineInventory() {
        return medicineInventoryService.getAllMedicineInventory();
    }

    @PostMapping("/updateStage")
    public ResponseEntity<?> updateStage(@Valid @RequestBody UpdateStageRequestDto updateStageRequestDtoList, HttpServletRequest request) {
        return appointmentService.updateStage(updateStageRequestDtoList, request);
    }

    @PostMapping("/createPhlebotomistTestRecord")
    public ResponseEntity<?> createPhlebotomistTestRecord(@Valid @RequestBody CreatePhlebotomistTestRecordDto createPhlebotomistTestRecordDto) {
        return phlebotomistTestService.createPhlebotomistTestRecord(createPhlebotomistTestRecordDto);
    }

    @PostMapping("/createPrescriptionRecord")
    public ResponseEntity<?> createPrescriptionRecord(@Valid @RequestBody CreatePrescriptionRecordDto createPrescriptionRecordDto) {
        return prescriptionService.createPrescriptionRecord(createPrescriptionRecordDto);
    }

    @PostMapping("/createDoctorFeedbackRecord")
    public ResponseEntity<?> createDoctorFeedbackRecord(@Valid @RequestBody CreateDoctorFeedbackRecordDto createDoctorFeedbackRecordDto) {
        return doctorFeedbackService.createDoctorFeedbackRecord(createDoctorFeedbackRecordDto);
    }

    @GetMapping("/getPhlebotomistTestRecords")
    public ResponseEntity<?> getPhlebotomistTestRecords(@RequestParam Long appointmentId, HttpServletRequest request) {
        return phlebotomistTestService.getPhlebotomistTestRecordsByAppointmentId(appointmentId,request);

    }


}
