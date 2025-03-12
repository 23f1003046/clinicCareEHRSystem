package com.healthCareAnalyzer.Health_Care_Backend.dto.billing;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillGeneratorRequestDto {
    @NotBlank
    @Email(message = "Invalid Email Id")
    private String username;
}
