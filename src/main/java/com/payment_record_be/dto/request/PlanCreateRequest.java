package com.payment_record_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Payload for creating a new subscription plan")
public class PlanCreateRequest {

    @NotBlank(message = "planCode is required")
    @Size(max = 50, message = "planCode must not exceed 50 characters")
    @Schema(description = "Unique, immutable code identifying the plan", example = "PLAN_MONTHLY")
    private String planCode;

    @NotBlank(message = "planName is required")
    @Size(max = 150, message = "planName must not exceed 150 characters")
    @Schema(example = "Monthly Plan")
    private String planName;

    @Size(max = 1000, message = "description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "amount must be a valid monetary value")
    private BigDecimal amount;

    @Size(max = 10)
    @Schema(example = "INR")
    private String currency;

    @NotNull(message = "durationDays is required")
    @Min(value = 1, message = "durationDays must be greater than 0")
    private Integer durationDays;

    @Min(value = 0, message = "displayOrder must not be negative")
    private Integer displayOrder;

    @Schema(description = "Defaults to true when omitted")
    private Boolean isActive;
}
