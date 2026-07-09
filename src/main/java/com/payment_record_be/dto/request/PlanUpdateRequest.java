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
@Schema(description = "Payload for updating an existing subscription plan. planCode cannot be changed.")
public class PlanUpdateRequest {

    @NotBlank(message = "planName is required")
    @Size(max = 150, message = "planName must not exceed 150 characters")
    private String planName;

    @Size(max = 1000, message = "description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "amount must be a valid monetary value")
    private BigDecimal amount;

    @NotNull(message = "durationDays is required")
    @Min(value = 1, message = "durationDays must be greater than 0")
    private Integer durationDays;

    @NotNull(message = "displayOrder is required")
    @Min(value = 0, message = "displayOrder must not be negative")
    private Integer displayOrder;

    @NotNull(message = "isActive is required")
    private Boolean isActive;
}
