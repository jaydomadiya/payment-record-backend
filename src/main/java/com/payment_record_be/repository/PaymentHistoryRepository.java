package com.payment_record_be.repository;

import com.payment_record_be.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, UUID>,
        JpaSpecificationExecutor<PaymentHistory> {

    boolean existsByPaymentId(String paymentId);
}
