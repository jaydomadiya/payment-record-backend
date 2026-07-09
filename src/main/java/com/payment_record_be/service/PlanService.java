package com.payment_record_be.service;

import com.payment_record_be.dto.request.PlanCreateRequest;
import com.payment_record_be.dto.request.PlanFilterRequest;
import com.payment_record_be.dto.request.PlanUpdateRequest;
import com.payment_record_be.dto.response.PlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PlanService {

    PlanResponse createPlan(PlanCreateRequest request);

    PlanResponse updatePlan(UUID id, PlanUpdateRequest request);

    void deletePlan(UUID id);

    Page<PlanResponse> getAllPlans(PlanFilterRequest filter, Pageable pageable);

    List<PlanResponse> getActivePlans();
}
