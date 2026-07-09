package com.payment_record_be.mapper;

import com.payment_record_be.dto.request.UpiConfigurationCreateRequest;
import com.payment_record_be.dto.request.UpiConfigurationUpdateRequest;
import com.payment_record_be.dto.response.ActiveUpiResponse;
import com.payment_record_be.dto.response.UpiConfigurationResponse;
import com.payment_record_be.entity.UpiConfiguration;
import org.springframework.stereotype.Component;

@Component
public class UpiConfigurationMapper {

    public UpiConfiguration toEntity(UpiConfigurationCreateRequest request) {
        return UpiConfiguration.builder()
                .upiId(request.getUpiId())
                .imageUrl(request.getImageUrl())
                .isActive(request.getIsActive())
                .build();
    }

    public void updateEntityFromRequest(UpiConfigurationUpdateRequest request, UpiConfiguration entity) {
        if (request.getUpiId() != null) {
            entity.setUpiId(request.getUpiId());
        }
        if (request.getImageUrl() != null) {
            entity.setImageUrl(request.getImageUrl());
        }
        if (request.getIsActive() != null) {
            entity.setIsActive(request.getIsActive());
        }
    }

    public UpiConfigurationResponse toResponse(UpiConfiguration entity) {
        return UpiConfigurationResponse.builder()
                .id(entity.getId())
                .upiId(entity.getUpiId())
                .imageUrl(entity.getImageUrl())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ActiveUpiResponse toActiveResponse(UpiConfiguration entity) {
        return ActiveUpiResponse.builder()
                .upiId(entity.getUpiId())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
