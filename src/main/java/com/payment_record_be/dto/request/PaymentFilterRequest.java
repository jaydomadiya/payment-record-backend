package com.payment_record_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dynamic filters for querying payment history")
public class PaymentFilterRequest {

    @Schema(description = "Search by payment id (partial match)")
    private String paymentId;

    @Schema(description = "Search by mobile number (partial match)")
    private String mobileNumber;

    @Schema(description = "Filter by exact event id")
    private String eventId;

    @Schema(description = "Filter by payment status, e.g. SUCCESS, FAILED, PENDING")
    private String paymentStatus;

    @Schema(description = "Filter by payment method, e.g. UPI, CARD, NETBANKING")
    private String paymentMethod;

    @Schema(description = "Start date (inclusive), format yyyy-MM-dd")
    private LocalDate fromDate;

    @Schema(description = "End date (inclusive), format yyyy-MM-dd")
    private LocalDate toDate;
}
