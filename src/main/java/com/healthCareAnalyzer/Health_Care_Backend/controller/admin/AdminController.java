package com.healthCareAnalyzer.Health_Care_Backend.controller.admin;

import com.healthCareAnalyzer.Health_Care_Backend.dto.admin.*;
import com.healthCareAnalyzer.Health_Care_Backend.dto.labTest.AddNewLabTestRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.medicineInventory.AddNewMedicineInventoryRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.admin.AdminService;
import com.healthCareAnalyzer.Health_Care_Backend.service.labTest.LabTestService;
import com.healthCareAnalyzer.Health_Care_Backend.service.medicineInventory.MedicineInventoryService;
import com.healthCareAnalyzer.Health_Care_Backend.service.stage.StageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class AdminController {


    private final AdminService adminService;
    private final StageService stageService;
    private final LabTestService labTestService;
    private final MedicineInventoryService medicineInventoryService;


    @Autowired
    public AdminController(AdminService adminService, StageService stageService, LabTestService labTestService, MedicineInventoryService medicineInventoryService) {
        this.adminService = adminService;
        this.stageService = stageService;
        this.labTestService = labTestService;
        this.medicineInventoryService = medicineInventoryService;
    }

    @PostMapping("/updateDashboardPassword")
    public ResponseEntity<?> updateDashboardPassword(@RequestBody @Valid UpdateDashboardPasswordRequestDto updateDashboardPasswordRequestDto, HttpServletRequest httpServletRequest) {

        return adminService.updateDashboardPassword(updateDashboardPasswordRequestDto.getOldPassword(), updateDashboardPasswordRequestDto.getNewPassword(), updateDashboardPasswordRequestDto.getRetypeNewPassword(), httpServletRequest);

    }

    @GetMapping("/fetchDisabledUsers")
    public ResponseEntity<?> fetchDisabledUsers() {

        return adminService.fetchDisabledUsers();

    }

    @PostMapping("/enableDisabledUser")
    public ResponseEntity<?> enableDisabledUser(@Valid @RequestBody UsernameDto usernameDto) {

        return adminService.enableDisabledUser(usernameDto.getUsername());

    }

    @PostMapping("/deleteDisabledUser")
    public ResponseEntity<?> deleteDisabledUser(@Valid @RequestBody UsernameRoleDto usernameRoleDto) {

        return adminService.deleteDisabledUser(usernameRoleDto.getUsername(), usernameRoleDto.getRole());


    }

    @PostMapping("/addNewSlots")
    public ResponseEntity<?> addNewSlots(@Valid @RequestBody List<AddNewAppointmentSlotsRequestDto> addNewAppointmentSlotsRequestDto) {

        adminService.addNewSlots(addNewAppointmentSlotsRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully added new slots");

    }

    @PostMapping("/addNewStages")
    public ResponseEntity<?> addNewStages(@Valid @RequestBody AddNewStagesRequestDto addNewStagesRequestDto) {

        return stageService.addNewStages(addNewStagesRequestDto.getStageList());
    }

    @PostMapping("/addNewLabTests")
    public ResponseEntity<?> addNewLabTests(@Valid @RequestBody List<AddNewLabTestRequestDto> addNewLabTestRequestDtoList) {

        return labTestService.addNewLabTests(addNewLabTestRequestDtoList);

    }

    @PostMapping("/addNewMedicines")
    public ResponseEntity<?> addNewMedicines(@Valid @RequestBody List<AddNewMedicineInventoryRequestDto> addNewMedicineInventoryRequestDtoList) {

        return medicineInventoryService.addNewMedicines(addNewMedicineInventoryRequestDtoList);

    }


}
