package com.healthCareAnalyzer.Health_Care_Backend.controller.billing;

import com.healthCareAnalyzer.Health_Care_Backend.dto.billing.BillGeneratorRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.billing.BillingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
@PreAuthorize("hasAuthority('ROLE_RECEPTIONIST')")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class BillingController {
    private final BillingService billingService;
    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }
    // generate to get username
    // post to send bill details
    // payment to get to send qr data
    @PostMapping("/generate")
    public ResponseEntity<?> generateBill(@RequestBody @Valid BillGeneratorRequestDto billGeneratorRequestDto){
        return billingService.billGenerator(billGeneratorRequestDto);
    }
}
