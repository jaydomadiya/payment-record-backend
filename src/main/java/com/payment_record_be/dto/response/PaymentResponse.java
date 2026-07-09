package com.payment_record_be.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payment history record")
public class PaymentResponse {

    private UUID id;
    private String paymentId;
    private String orderId;
    private String transactionId;
    private String userName;
    private String mobileNumber;
    private String email;
    private String upiId;
    private String paymentMethod;
    private BigDecimal amount;
    private String currency;
    private String paymentStatus;
    private String gatewayName;
    private String gatewayResponse;
    private String eventId;
    private String eventName;
    private Integer ticketQuantity;
    private String ticketType;
    private Instant createdAt;
    private Instant updatedAt;
}
