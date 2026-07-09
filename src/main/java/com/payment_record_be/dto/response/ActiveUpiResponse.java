package com.payment_record_be.dto.response;

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
@Schema(description = "The UPI ID and image currently shown to the mobile app")
public class ActiveUpiResponse {

    private String upiId;
    private String imageUrl;
}
