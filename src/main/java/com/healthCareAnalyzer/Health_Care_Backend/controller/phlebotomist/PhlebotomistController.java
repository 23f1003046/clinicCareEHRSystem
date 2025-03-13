package com.healthCareAnalyzer.Health_Care_Backend.controller.phlebotomist;

import com.healthCareAnalyzer.Health_Care_Backend.dto.phlebotomist.SaveLabTestRecordsDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.phlebotomistTest.PhlebotomistTestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phlebotomist")
@PreAuthorize("hasAuthority('ROLE_PHLEBOTOMIST')")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class PhlebotomistController {

    private final PhlebotomistTestService phlebotomistTestService;

    public PhlebotomistController(PhlebotomistTestService phlebotomistTestService) {
        this.phlebotomistTestService = phlebotomistTestService;
    }

    @GetMapping("/getAllPendingTests")
    public ResponseEntity<?> getAllPendingTests() {
        return phlebotomistTestService.getAllPendingTests();
    }

    @PostMapping("/saveLabTestRecords")
    public ResponseEntity<?> saveLabTestRecords(@Valid @RequestBody SaveLabTestRecordsDto saveLabTestRecordsDto) {
        return phlebotomistTestService.saveLabTestRecords(saveLabTestRecordsDto);
    }

}
