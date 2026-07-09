package com.payment_record_be.mapper;

import com.payment_record_be.dto.request.PlanCreateRequest;
import com.payment_record_be.dto.request.PlanUpdateRequest;
import com.payment_record_be.dto.response.PlanResponse;
import com.payment_record_be.entity.SubscriptionPlan;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PlanMapper {

    private static final String DEFAULT_CURRENCY = "INR";

    public SubscriptionPlan toEntity(PlanCreateRequest request) {
        return SubscriptionPlan.builder()
                .planCode(request.getPlanCode())
                .planName(request.getPlanName())
                .description(request.getDescription())
                .amount(request.getAmount())
                .currency(StringUtils.hasText(request.getCurrency()) ? request.getCurrency() : DEFAULT_CURRENCY)
                .durationDays(request.getDurationDays())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .isActive(request.getIsActive() != null ? request.getIsActive() : Boolean.TRUE)
                .build();
    }

    public void updateEntityFromRequest(PlanUpdateRequest request, SubscriptionPlan entity) {
        entity.setPlanName(request.getPlanName());
        entity.setDescription(request.getDescription());
        entity.setAmount(request.getAmount());
        entity.setDurationDays(request.getDurationDays());
        entity.setDisplayOrder(request.getDisplayOrder());
        entity.setIsActive(request.getIsActive());
    }

    public PlanResponse toResponse(SubscriptionPlan entity) {
        return PlanResponse.builder()
                .id(entity.getId())
                .planCode(entity.getPlanCode())
                .planName(entity.getPlanName())
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .durationDays(entity.getDurationDays())
                .displayOrder(entity.getDisplayOrder())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
