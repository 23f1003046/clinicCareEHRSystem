package com.healthCareAnalyzer.Health_Care_Backend.service.labTest;

import com.healthCareAnalyzer.Health_Care_Backend.dto.labTest.AddNewLabTestRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.LabTestsEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.LabTestsRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabTestService {

    private final LabTestsRepository labTestsRepository;

    public LabTestService(LabTestsRepository labTestsRepository) {
        this.labTestsRepository = labTestsRepository;
    }

    public ResponseEntity<?> getAllLabTests() {

        return ResponseEntity.ok(labTestsRepository.findAll());

    }

    public ResponseEntity<?> addNewLabTests(@Valid List<AddNewLabTestRequestDto> addNewLabTestRequestDtoList) {

        List<LabTestsEntity> labTestsEntityList = new ArrayList<>();
        for (AddNewLabTestRequestDto addNewLabTestRequestDto : addNewLabTestRequestDtoList) {
            LabTestsEntity labTestsEntity = new LabTestsEntity();
            labTestsEntity.setLabTestName(addNewLabTestRequestDto.getLabTestName());
            labTestsEntity.setLabTestFields(addNewLabTestRequestDto.getLabTestFields());
            labTestsEntity.setPrice(addNewLabTestRequestDto.getLabTestPrice());
            labTestsEntityList.add(labTestsEntity);
        }

        labTestsRepository.saveAll(labTestsEntityList);
        return ResponseEntity.ok("Successfully added Lab Tests");

    }
}
