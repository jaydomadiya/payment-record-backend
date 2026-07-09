package com.payment_record_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload for storing a UPI payment transaction. Every field is optional — the "
        + "mobile app may send complete, partial, or minimal data. Any field that is omitted is stored "
        + "as NULL. Only data-type correctness is validated (e.g. amount must be numeric if present); "
        + "no field is required.")
public class PaymentRequest {

    @Size(max = 100, message = "paymentId must not exceed 100 characters")
    @Schema(description = "Optional. Unique payment identifier issued by the gateway.", example = "pay_NqZ8x7YkjKl9")
    private String paymentId;

    @Size(max = 100, message = "orderId must not exceed 100 characters")
    @Schema(description = "Optional.", example = "order_9A33XWu170gUtm")
    private String orderId;

    @Size(max = 100)
    @Schema(description = "Optional.")
    private String transactionId;

    @Size(max = 150)
    @Schema(description = "Optional.")
    private String userName;

    @Size(max = 15)
    @Schema(description = "Optional.")
    private String mobileNumber;

    @Size(max = 150)
    @Schema(description = "Optional.")
    private String email;

    @Size(max = 100)
    @Schema(description = "Optional.")
    private String upiId;

    @Size(max = 50)
    @Schema(description = "Optional.", example = "UPI")
    private String paymentMethod;

    @Schema(description = "Optional. Must be a numeric value if present.", example = "500")
    private BigDecimal amount;

    @Size(max = 10)
    @Schema(description = "Optional.", example = "INR")
    private String currency;

    @Size(max = 50)
    @Schema(description = "Optional.", example = "SUCCESS")
    private String paymentStatus;

    @Size(max = 100)
    @Schema(description = "Optional.")
    private String gatewayName;

    @Schema(description = "Optional. Raw gateway response payload, stored for auditing/reconciliation.")
    private String gatewayResponse;

    @Size(max = 100)
    @Schema(description = "Optional.")
    private String eventId;

    @Size(max = 200)
    @Schema(description = "Optional.")
    private String eventName;

    @Schema(description = "Optional. Must be a numeric value if present.")
    private Integer ticketQuantity;

    @Size(max = 50)
    @Schema(description = "Optional.")
    private String ticketType;
}
