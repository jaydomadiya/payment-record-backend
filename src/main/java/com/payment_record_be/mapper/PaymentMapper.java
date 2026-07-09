package com.payment_record_be.mapper;

import com.payment_record_be.dto.request.PaymentRequest;
import com.payment_record_be.dto.response.PaymentResponse;
import com.payment_record_be.entity.PaymentHistory;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentHistory toEntity(PaymentRequest request) {
        return PaymentHistory.builder()
                .paymentId(request.getPaymentId())
                .orderId(request.getOrderId())
                .transactionId(request.getTransactionId())
                .userName(request.getUserName())
                .mobileNumber(request.getMobileNumber())
                .email(request.getEmail())
                .upiId(request.getUpiId())
                .paymentMethod(request.getPaymentMethod())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentStatus(request.getPaymentStatus())
                .gatewayName(request.getGatewayName())
                .gatewayResponse(request.getGatewayResponse())
                .eventId(request.getEventId())
                .eventName(request.getEventName())
                .ticketQuantity(request.getTicketQuantity())
                .ticketType(request.getTicketType())
                .build();
    }

    public PaymentResponse toResponse(PaymentHistory entity) {
        return PaymentResponse.builder()
                .id(entity.getId())
                .paymentId(entity.getPaymentId())
                .orderId(entity.getOrderId())
                .transactionId(entity.getTransactionId())
                .userName(entity.getUserName())
                .mobileNumber(entity.getMobileNumber())
                .email(entity.getEmail())
                .upiId(entity.getUpiId())
                .paymentMethod(entity.getPaymentMethod())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .paymentStatus(entity.getPaymentStatus())
                .gatewayName(entity.getGatewayName())
                .gatewayResponse(entity.getGatewayResponse())
                .eventId(entity.getEventId())
                .eventName(entity.getEventName())
                .ticketQuantity(entity.getTicketQuantity())
                .ticketType(entity.getTicketType())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
