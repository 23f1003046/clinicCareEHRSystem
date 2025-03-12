package com.healthCareAnalyzer.Health_Care_Backend.service.phlebotomistTest;

import com.healthCareAnalyzer.Health_Care_Backend.dto.phlebotomist.CreatePhlebotomistTestRecordDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.phlebotomist.SaveLabTestRecordsDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.LabTestsEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.PhlebotomistEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.PhlebotomistTestEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.AppointmentRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.LabTestsRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.PhlebotomistTestRepository;
import com.healthCareAnalyzer.Health_Care_Backend.utility.ExtractUsernameFromToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PhlebotomistTestService {
    private final AppointmentRepository appointmentRepository;
    private final PhlebotomistTestRepository phlebotomistTestRepository;
    private final LabTestsRepository labTestsRepository;
    private final ExtractUsernameFromToken extractUsernameFromToken;

    public PhlebotomistTestService(AppointmentRepository appointmentRepository, PhlebotomistTestRepository phlebotomistTestRepository, LabTestsRepository labTestsRepository, ExtractUsernameFromToken extractUsernameFromToken) {
        this.appointmentRepository = appointmentRepository;
        this.phlebotomistTestRepository = phlebotomistTestRepository;
        this.labTestsRepository = labTestsRepository;
        this.extractUsernameFromToken = extractUsernameFromToken;
    }

    public ResponseEntity<?> createPhlebotomistTestRecord(@Valid CreatePhlebotomistTestRecordDto createPhlebotomistTestRecordDto) {

        Optional<AppointmentEntity> appointmentEntity = appointmentRepository.findById(createPhlebotomistTestRecordDto.getAppointmentId());
        if (appointmentEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }

        List<LabTestsEntity> labTestsEntityList = labTestsRepository.findByLabTestIdIn(createPhlebotomistTestRecordDto.getLabTestIds());

        if (labTestsEntityList.size() != createPhlebotomistTestRecordDto.getLabTestIds().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lab test Ids do not exist");
        }

        PhlebotomistTestEntity phlebotomistTestEntity = new PhlebotomistTestEntity();
        phlebotomistTestEntity.setAppointmentEntity(appointmentEntity.get());
        phlebotomistTestEntity.setLabTestIds(createPhlebotomistTestRecordDto.getLabTestIds().toArray(new Long[0]));

        phlebotomistTestRepository.save(phlebotomistTestEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body("Phlebotomist test record created");
    }

    public ResponseEntity<?> getAllPendingTests() {

        List<PhlebotomistTestEntity> phlebotomistTestEntityList = phlebotomistTestRepository.findByAppointmentEntity_Stage("phlebotomist");

        for (PhlebotomistTestEntity phlebotomistTestEntity : phlebotomistTestEntityList) {

            List<LabTestsEntity> labTestsEntityList = labTestsRepository.findByLabTestIdIn(Arrays.asList(phlebotomistTestEntity.getLabTestIds()));
            phlebotomistTestEntity.setLabTestsEntityList(labTestsEntityList);

        }

        return ResponseEntity.status(HttpStatus.OK).body(phlebotomistTestEntityList);
    }

    public ResponseEntity<?> saveLabTestRecords(@Valid SaveLabTestRecordsDto saveLabTestRecordsDto) {

        Optional<PhlebotomistTestEntity> optionalPhlebotomistTestEntity = phlebotomistTestRepository.findById(saveLabTestRecordsDto.getPhlebotomistTestId());
        if (optionalPhlebotomistTestEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Phlebotomist test not found");
        }
        PhlebotomistTestEntity phlebotomistTestEntity = optionalPhlebotomistTestEntity.get();
        phlebotomistTestEntity.setPatientTestData(saveLabTestRecordsDto.getLabTestRecords());
        phlebotomistTestRepository.save(phlebotomistTestEntity);

        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(saveLabTestRecordsDto.getAppointmentId());
        if (optionalAppointmentEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }
        AppointmentEntity appointmentEntity = optionalAppointmentEntity.get();
        appointmentEntity.setStage("doctor_v2");
        appointmentRepository.save(appointmentEntity);

        return ResponseEntity.ok().body("Phlebotomist test record saved successfully");
    }

    public ResponseEntity<?> getPhlebotomistTestRecordsByAppointmentId(Long appointmentId, HttpServletRequest request) {

        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(appointmentId);
        if (optionalAppointmentEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }

        if(!optionalAppointmentEntity.get().getDoctor().getUserEntity().getUsername().equals(extractUsernameFromToken.extractUsernameFromToken(request))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Optional<PhlebotomistTestEntity> optionalPhlebotomistTestEntity = phlebotomistTestRepository.findByAppointmentEntity(optionalAppointmentEntity.get());
        if (optionalPhlebotomistTestEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Phlebotomist test not found");
        }

        PhlebotomistTestEntity phlebotomistTestEntity = optionalPhlebotomistTestEntity.get();
        Long[] labTestIds = phlebotomistTestEntity.getLabTestIds();
        List<String> patientTestData = Stream.of(phlebotomistTestEntity.getPatientTestData()).filter(e -> !e.equals(",")).toList();

        HashMap<String,HashMap<String,String>> patientTestDataMap = new HashMap<>();

        for(Long labTestId : labTestIds) {
            LabTestsEntity labTestsEntity = labTestsRepository.findById(labTestId).get();
            String[] fieldNamesList = labTestsEntity.getLabTestFields();
            HashMap<String,String> fieldsMap = new HashMap<>();

            for (String fieldName : fieldNamesList) {
                fieldsMap.putIfAbsent(fieldName, patientTestData.getFirst());
                patientTestData.removeFirst();
            }
            patientTestDataMap.putIfAbsent(labTestsEntity.getLabTestName(),fieldsMap);
        }

        return ResponseEntity.status(HttpStatus.OK).body(patientTestDataMap);


    }
}
