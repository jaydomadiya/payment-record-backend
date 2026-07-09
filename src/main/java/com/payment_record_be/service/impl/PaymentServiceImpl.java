package com.payment_record_be.service.impl;

import com.payment_record_be.dto.request.PaymentFilterRequest;
import com.payment_record_be.dto.request.PaymentRequest;
import com.payment_record_be.dto.response.PaymentResponse;
import com.payment_record_be.entity.PaymentHistory;
import com.payment_record_be.exception.DuplicatePaymentException;
import com.payment_record_be.mapper.PaymentMapper;
import com.payment_record_be.repository.PaymentHistoryRepository;
import com.payment_record_be.service.PaymentService;
import com.payment_record_be.specification.PaymentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponse savePayment(PaymentRequest request) {
        log.info("Saving payment record for paymentId={}, orderId={}", request.getPaymentId(), request.getOrderId());

        boolean hasPaymentId = StringUtils.hasText(request.getPaymentId());
        if (hasPaymentId && paymentHistoryRepository.existsByPaymentId(request.getPaymentId())) {
            log.warn("Duplicate payment attempted for paymentId={}", request.getPaymentId());
            throw new DuplicatePaymentException("Payment already exists with paymentId: " + request.getPaymentId());
        }

        PaymentHistory entity = paymentMapper.toEntity(request);

        try {
            PaymentHistory saved = paymentHistoryRepository.save(entity);
            log.info("Payment record saved successfully with id={}", saved.getId());
            return paymentMapper.toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            if (hasPaymentId) {
                log.error("Data integrity violation while saving paymentId={}", request.getPaymentId(), ex);
                throw new DuplicatePaymentException("Payment already exists with paymentId: " + request.getPaymentId());
            }
            throw ex;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPayments(PaymentFilterRequest filter, Pageable pageable) {
        log.debug("Fetching payments with filter={}, pageable={}", filter, pageable);
        Page<PaymentHistory> page = paymentHistoryRepository.findAll(PaymentSpecification.withFilters(filter), pageable);
        return page.map(paymentMapper::toResponse);
    }
}
