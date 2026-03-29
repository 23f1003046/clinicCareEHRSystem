package com.healthCareAnalyzer.Health_Care_Backend.service.billing;

import com.healthCareAnalyzer.Health_Care_Backend.dto.billing.BillGeneratorRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.billing.BillGeneratorResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.*;
import com.healthCareAnalyzer.Health_Care_Backend.repository.*;
import com.healthCareAnalyzer.Health_Care_Backend.utility.ExtractUsernameFromToken;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.el.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class BillingService {
    private final AppointmentRepository appointmentRepository;
    private final PhlebotomistTestRepository phlebotomistTestRepository;
    private final LabTestsRepository labTestsRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicineInventoryRepository medicineInventoryRepository;

    public BillingService(AppointmentRepository appointmentRepository, PhlebotomistTestRepository phlebotomistTestRepository, LabTestsRepository labTestsRepository, PrescriptionRepository prescriptionRepository, MedicineInventoryRepository medicineInventoryRepository) {
        this.appointmentRepository = appointmentRepository;
        this.phlebotomistTestRepository = phlebotomistTestRepository;
        this.labTestsRepository = labTestsRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.medicineInventoryRepository = medicineInventoryRepository;
    }

    public ResponseEntity<?> billGenerator(BillGeneratorRequestDto billGeneratorRequestDto){
        String username=billGeneratorRequestDto.getUsername();
        Optional<AppointmentEntity> appointmentEntity=appointmentRepository.findByPatient_UserEntity_UsernameAndStage(username,"receptionist");
        if(appointmentEntity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Appointment Details Not found");
        }

        AppointmentEntity appointmentEntityData =appointmentEntity.get();

        Float doctorFee=appointmentEntityData.getDoctor().getDocFee();
        Optional<PhlebotomistTestEntity> optionalPhlebotomistTestEntity = phlebotomistTestRepository.findByAppointmentEntity(appointmentEntityData);
        HashMap<String, Float> labTestBillData = new HashMap<>();

        if(optionalPhlebotomistTestEntity.isPresent()) {

            List<Long> labTestIds = Arrays.asList(optionalPhlebotomistTestEntity.get().getLabTestIds());

            List<LabTestsEntity> labTestsEntityList = labTestsRepository.findByLabTestIdIn(labTestIds);

            for (LabTestsEntity labTestsEntity : labTestsEntityList) {
                labTestBillData.put(labTestsEntity.getLabTestName(), labTestsEntity.getPrice());
            }

        }

        Optional<PrescriptionEntity> optionalPrescriptionEntity = prescriptionRepository.findByAppointmentEntity(appointmentEntityData);
        HashMap<String, Float> medicineBillData = new HashMap<>();

        if(optionalPrescriptionEntity.isPresent()){

            List<Long> medicineIds = Arrays.asList(optionalPrescriptionEntity.get().getMedicineIds());

            List<MedicineInventoryEntity> medicineInventoryEntityList = medicineInventoryRepository.findByMedicineIdIn(medicineIds);

            for (MedicineInventoryEntity medicineInventoryEntity: medicineInventoryEntityList){
                medicineBillData.put(medicineInventoryEntity.getMedicineName(), medicineInventoryEntity.getPrice());
            }

        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No prescription data found!");
        }

        BillGeneratorResponseDto billGeneratorResponseDto = getBillGeneratorResponseDto(labTestBillData, medicineBillData, doctorFee);

        return ResponseEntity.ok().body(billGeneratorResponseDto);
    }

    private static @NotNull BillGeneratorResponseDto getBillGeneratorResponseDto(HashMap<String, Float> labTestBillData, HashMap<String, Float> medicineBillData, Float doctorFee) {

        BillGeneratorResponseDto billGeneratorResponseDto = new BillGeneratorResponseDto();
        billGeneratorResponseDto.setDoctorFee(doctorFee);
        billGeneratorResponseDto.setLabTestBillData(labTestBillData);
        billGeneratorResponseDto.setMedicineBillData(medicineBillData);
        return billGeneratorResponseDto;
    }
}
