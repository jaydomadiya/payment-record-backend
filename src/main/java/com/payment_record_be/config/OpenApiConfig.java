package com.payment_record_be.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI paymentHistoryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payment History API")
                        .description("APIs to store and query UPI payment transactions for the Admin Panel")
                        .version("v1")
                        .contact(new Contact().name("Payment Record Team")));
    }
}
