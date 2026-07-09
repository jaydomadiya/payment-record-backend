package com.payment_record_be.service;

import com.payment_record_be.dto.request.UpiConfigurationCreateRequest;
import com.payment_record_be.dto.request.UpiConfigurationUpdateRequest;
import com.payment_record_be.dto.response.ActiveUpiResponse;
import com.payment_record_be.dto.response.UpiConfigurationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UpiConfigurationService {

    UpiConfigurationResponse createConfiguration(UpiConfigurationCreateRequest request);

    UpiConfigurationResponse updateConfiguration(UUID id, UpiConfigurationUpdateRequest request);

    void deleteConfiguration(UUID id);

    Page<UpiConfigurationResponse> getAllConfigurations(Pageable pageable);

    ActiveUpiResponse getActiveConfiguration();
}
