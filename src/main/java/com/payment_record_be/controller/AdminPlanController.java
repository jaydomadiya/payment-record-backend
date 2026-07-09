package com.payment_record_be.controller;

import com.payment_record_be.dto.request.PlanCreateRequest;
import com.payment_record_be.dto.request.PlanFilterRequest;
import com.payment_record_be.dto.request.PlanUpdateRequest;
import com.payment_record_be.dto.response.PlanResponse;
import com.payment_record_be.response.ApiResponse;
import com.payment_record_be.response.PageResponse;
import com.payment_record_be.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/plans")
@RequiredArgsConstructor
@Tag(name = "Admin - Subscription Plans", description = "Admin APIs to manage subscription plans")
public class AdminPlanController {

    private final PlanService planService;

    @PostMapping
    @Operation(summary = "Create a subscription plan", description = "Creates a new subscription plan with a unique planCode")
    public ResponseEntity<ApiResponse<PlanResponse>> createPlan(@Valid @RequestBody PlanCreateRequest request) {
        PlanResponse response = planService.createPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Plan created successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a subscription plan",
            description = "Updates plan name, description, amount, duration, display order and active status. planCode is immutable.")
    public ResponseEntity<ApiResponse<PlanResponse>> updatePlan(
            @PathVariable UUID id, @Valid @RequestBody PlanUpdateRequest request) {
        PlanResponse response = planService.updatePlan(id, request);
        return ResponseEntity.ok(ApiResponse.success("Plan updated successfully", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subscription plan", description = "Deletes the subscription plan identified by its UUID")
    public ResponseEntity<ApiResponse<Void>> deletePlan(@PathVariable UUID id) {
        planService.deletePlan(id);
        return ResponseEntity.ok(ApiResponse.success("Subscription plan deleted successfully", null));
    }

    @GetMapping
    @Operation(summary = "Get all plans", description = "Returns all subscription plans, paginated and sorted, latest updates first by default")
    public ResponseEntity<ApiResponse<PageResponse<PlanResponse>>> getAllPlans(
            @Parameter(description = "Search by plan code") @RequestParam(required = false) String planCode,
            @Parameter(description = "Search by plan name") @RequestParam(required = false) String planName,
            @Parameter(description = "Filter by active status") @RequestParam(required = false) Boolean isActive,
            @PageableDefault(size = 20, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PlanFilterRequest filter = PlanFilterRequest.builder()
                .planCode(planCode)
                .planName(planName)
                .isActive(isActive)
                .build();

        Page<PlanResponse> page = planService.getAllPlans(filter, pageable);
        return ResponseEntity.ok(ApiResponse.success("Plans fetched successfully", PageResponse.from(page)));
    }
}
