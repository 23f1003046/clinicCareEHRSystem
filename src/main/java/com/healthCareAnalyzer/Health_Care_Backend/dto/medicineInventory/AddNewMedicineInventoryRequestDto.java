package com.healthCareAnalyzer.Health_Care_Backend.dto.medicineInventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewMedicineInventoryRequestDto {

    @NotBlank
    public String medicineName;
    @NotNull
    private Integer medicineQuantity;
    @NotBlank
    private String medicineSerialNumber;
    @NotNull
    private Float medicinePrice;
}
