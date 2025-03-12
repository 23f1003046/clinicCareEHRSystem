package com.healthCareAnalyzer.Health_Care_Backend.dto.billing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillGeneratorResponseDto {

    private Float doctorFee;
    private HashMap<String, Float> labTestBillData;
    private HashMap<String, Float> medicineBillData;

}
