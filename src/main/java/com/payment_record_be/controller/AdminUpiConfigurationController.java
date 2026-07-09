package com.payment_record_be.controller;

import com.payment_record_be.dto.request.UpiConfigurationCreateRequest;
import com.payment_record_be.dto.request.UpiConfigurationUpdateRequest;
import com.payment_record_be.dto.response.UpiConfigurationResponse;
import com.payment_record_be.response.ApiResponse;
import com.payment_record_be.response.PageResponse;
import com.payment_record_be.service.UpiConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/upi")
@RequiredArgsConstructor
@Tag(name = "Admin - UPI Configuration", description = "Admin APIs to manage UPI configurations")
public class AdminUpiConfigurationController {

    private final UpiConfigurationService upiConfigurationService;

    @PostMapping
    @Operation(summary = "Create a UPI configuration",
            description = "Creates a new UPI configuration. All fields are optional; omitted fields are stored as NULL.")
    public ResponseEntity<ApiResponse<UpiConfigurationResponse>> createConfiguration(
            @Valid @RequestBody UpiConfigurationCreateRequest request) {
        UpiConfigurationResponse response = upiConfigurationService.createConfiguration(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("UPI configuration created successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a UPI configuration",
            description = "Partially updates a UPI configuration. Only fields present (non-null) in the request are updated.")
    public ResponseEntity<ApiResponse<UpiConfigurationResponse>> updateConfiguration(
            @PathVariable UUID id, @Valid @RequestBody UpiConfigurationUpdateRequest request) {
        UpiConfigurationResponse response = upiConfigurationService.updateConfiguration(id, request);
        return ResponseEntity.ok(ApiResponse.success("UPI configuration updated successfully", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a UPI configuration", description = "Deletes the selected UPI configuration")
    public ResponseEntity<ApiResponse<Void>> deleteConfiguration(@PathVariable UUID id) {
        upiConfigurationService.deleteConfiguration(id);
        return ResponseEntity.ok(ApiResponse.success("UPI configuration deleted successfully", null));
    }

    @GetMapping
    @Operation(summary = "Get all UPI configurations",
            description = "Returns all UPI configurations, paginated and sorted, latest records first by default")
    public ResponseEntity<ApiResponse<PageResponse<UpiConfigurationResponse>>> getAllConfigurations(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UpiConfigurationResponse> page = upiConfigurationService.getAllConfigurations(pageable);
        return ResponseEntity.ok(ApiResponse.success("UPI configurations fetched successfully", PageResponse.from(page)));
    }
}
