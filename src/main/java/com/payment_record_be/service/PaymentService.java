package com.payment_record_be.service;

import com.payment_record_be.dto.request.PaymentFilterRequest;
import com.payment_record_be.dto.request.PaymentRequest;
import com.payment_record_be.dto.response.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentResponse savePayment(PaymentRequest request);

    Page<PaymentResponse> getPayments(PaymentFilterRequest filter, Pageable pageable);
}
