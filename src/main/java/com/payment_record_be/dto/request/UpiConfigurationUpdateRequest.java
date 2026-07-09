package com.payment_record_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload for partially updating a UPI configuration. Only fields present (non-null) in the request are updated.")
public class UpiConfigurationUpdateRequest {

    @Size(max = 255, message = "upiId must not exceed 255 characters")
    @Schema(description = "UPI ID displayed to the mobile app", example = "merchant@oksbi")
    private String upiId;

    @URL(message = "imageUrl must be a valid URL")
    @Size(max = 2048, message = "imageUrl must not exceed 2048 characters")
    @Schema(description = "URL of the QR code / UPI image", example = "https://example.com/qr.png")
    private String imageUrl;

    @Schema(description = "Whether this configuration is the one served to the mobile app")
    private Boolean isActive;
}
