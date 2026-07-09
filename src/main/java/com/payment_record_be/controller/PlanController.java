package com.payment_record_be.controller;

import com.payment_record_be.dto.response.PlanResponse;
import com.payment_record_be.response.ApiResponse;
import com.payment_record_be.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
@Tag(name = "Subscription Plans", description = "Public API consumed by the mobile app to fetch active subscription plans")
public class PlanController {

    private final PlanService planService;

    @GetMapping
    @Operation(summary = "Get active plans", description = "Returns only active subscription plans, sorted by displayOrder ascending")
    public ResponseEntity<ApiResponse<List<PlanResponse>>> getActivePlans() {
        List<PlanResponse> plans = planService.getActivePlans();
        return ResponseEntity.ok(ApiResponse.success("Active plans fetched successfully", plans));
    }
}
