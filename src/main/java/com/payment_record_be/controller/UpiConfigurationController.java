package com.payment_record_be.controller;

import com.payment_record_be.dto.response.ActiveUpiResponse;
import com.payment_record_be.response.ApiResponse;
import com.payment_record_be.service.UpiConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/upi")
@RequiredArgsConstructor
@Tag(name = "UPI Configuration", description = "Public API consumed by the mobile app to fetch the active UPI configuration")
public class UpiConfigurationController {

    private final UpiConfigurationService upiConfigurationService;

    @GetMapping
    @Operation(summary = "Get active UPI configuration",
            description = "Returns the UPI ID and image URL currently active, for display in the mobile app")
    public ResponseEntity<ApiResponse<ActiveUpiResponse>> getActiveConfiguration() {
        ActiveUpiResponse response = upiConfigurationService.getActiveConfiguration();
        return ResponseEntity.ok(ApiResponse.success("Active UPI configuration fetched successfully", response));
    }
}
