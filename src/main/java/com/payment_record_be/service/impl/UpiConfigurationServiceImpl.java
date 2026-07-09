package com.payment_record_be.service.impl;

import com.payment_record_be.dto.request.UpiConfigurationCreateRequest;
import com.payment_record_be.dto.request.UpiConfigurationUpdateRequest;
import com.payment_record_be.dto.response.ActiveUpiResponse;
import com.payment_record_be.dto.response.UpiConfigurationResponse;
import com.payment_record_be.entity.UpiConfiguration;
import com.payment_record_be.exception.ResourceNotFoundException;
import com.payment_record_be.mapper.UpiConfigurationMapper;
import com.payment_record_be.repository.UpiConfigurationRepository;
import com.payment_record_be.service.UpiConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpiConfigurationServiceImpl implements UpiConfigurationService {

    private final UpiConfigurationRepository upiConfigurationRepository;
    private final UpiConfigurationMapper upiConfigurationMapper;

    @Override
    @Transactional
    public UpiConfigurationResponse createConfiguration(UpiConfigurationCreateRequest request) {
        log.info("Creating UPI configuration");

        UpiConfiguration entity = upiConfigurationMapper.toEntity(request);
        UpiConfiguration saved = upiConfigurationRepository.save(entity);

        log.info("UPI configuration created successfully with id={}", saved.getId());
        return upiConfigurationMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public UpiConfigurationResponse updateConfiguration(UUID id, UpiConfigurationUpdateRequest request) {
        log.info("Updating UPI configuration id={}", id);

        UpiConfiguration entity = upiConfigurationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UPI configuration not found with id: " + id));

        upiConfigurationMapper.updateEntityFromRequest(request, entity);
        UpiConfiguration saved = upiConfigurationRepository.save(entity);

        log.info("UPI configuration updated successfully id={}", saved.getId());
        return upiConfigurationMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteConfiguration(UUID id) {
        log.info("Deleting UPI configuration id={}", id);

        if (!upiConfigurationRepository.existsById(id)) {
            throw new ResourceNotFoundException("UPI configuration not found with id: " + id);
        }

        upiConfigurationRepository.deleteById(id);
        log.info("UPI configuration deleted successfully id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UpiConfigurationResponse> getAllConfigurations(Pageable pageable) {
        log.debug("Fetching UPI configurations with pageable={}", pageable);
        return upiConfigurationRepository.findAll(pageable).map(upiConfigurationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ActiveUpiResponse getActiveConfiguration() {
        log.debug("Fetching active UPI configuration");
        UpiConfiguration entity = upiConfigurationRepository.findFirstByIsActiveTrueOrderByUpdatedAtDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No active UPI configuration found"));
        return upiConfigurationMapper.toActiveResponse(entity);
    }
}
