package com.payment_record_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dynamic filters for the admin plan listing")
public class PlanFilterRequest {

    @Schema(description = "Search by plan code (partial match)")
    private String planCode;

    @Schema(description = "Search by plan name (partial match)")
    private String planName;

    @Schema(description = "Filter by active status")
    private Boolean isActive;
}
