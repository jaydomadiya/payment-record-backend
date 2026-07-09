package com.payment_record_be.service.impl;

import com.payment_record_be.dto.request.PlanCreateRequest;
import com.payment_record_be.dto.request.PlanFilterRequest;
import com.payment_record_be.dto.request.PlanUpdateRequest;
import com.payment_record_be.dto.response.PlanResponse;
import com.payment_record_be.entity.SubscriptionPlan;
import com.payment_record_be.exception.DuplicatePlanCodeException;
import com.payment_record_be.exception.ResourceNotFoundException;
import com.payment_record_be.mapper.PlanMapper;
import com.payment_record_be.repository.SubscriptionPlanRepository;
import com.payment_record_be.service.PlanService;
import com.payment_record_be.specification.PlanSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final PlanMapper planMapper;

    @Override
    @Transactional
    public PlanResponse createPlan(PlanCreateRequest request) {
        log.info("Creating subscription plan with planCode={}", request.getPlanCode());

        if (subscriptionPlanRepository.existsByPlanCode(request.getPlanCode())) {
            log.warn("Duplicate plan code attempted: {}", request.getPlanCode());
            throw new DuplicatePlanCodeException("Plan already exists with planCode: " + request.getPlanCode());
        }

        SubscriptionPlan entity = planMapper.toEntity(request);

        try {
            SubscriptionPlan saved = subscriptionPlanRepository.save(entity);
            log.info("Plan created successfully with id={}", saved.getId());
            return planMapper.toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            log.error("Data integrity violation while creating planCode={}", request.getPlanCode(), ex);
            throw new DuplicatePlanCodeException("Plan already exists with planCode: " + request.getPlanCode());
        }
    }

    @Override
    @Transactional
    public PlanResponse updatePlan(UUID id, PlanUpdateRequest request) {
        log.info("Updating subscription plan id={}", id);

        SubscriptionPlan entity = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));

        planMapper.updateEntityFromRequest(request, entity);
        SubscriptionPlan saved = subscriptionPlanRepository.save(entity);
        log.info("Plan updated successfully id={}", saved.getId());
        return planMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deletePlan(UUID id) {
        log.info("Deleting subscription plan id={}", id);

        if (!subscriptionPlanRepository.existsById(id)) {
            log.warn("Subscription plan not found for deletion, id={}", id);
            throw new ResourceNotFoundException("Subscription plan not found");
        }

        subscriptionPlanRepository.deleteById(id);
        log.info("Subscription plan deleted successfully id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanResponse> getAllPlans(PlanFilterRequest filter, Pageable pageable) {
        log.debug("Fetching plans with filter={}, pageable={}", filter, pageable);
        Page<SubscriptionPlan> page = subscriptionPlanRepository.findAll(PlanSpecification.withFilters(filter), pageable);
        return page.map(planMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> getActivePlans() {
        log.debug("Fetching active plans sorted by displayOrder");
        return subscriptionPlanRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }
}
