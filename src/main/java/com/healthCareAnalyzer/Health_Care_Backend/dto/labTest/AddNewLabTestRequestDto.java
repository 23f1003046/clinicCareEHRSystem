package com.healthCareAnalyzer.Health_Care_Backend.dto.labTest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewLabTestRequestDto {

    @NotBlank
    private String labTestName;
    @NotEmpty
    private String[] labTestFields;
    @NotNull
    private Float labTestPrice;

}
