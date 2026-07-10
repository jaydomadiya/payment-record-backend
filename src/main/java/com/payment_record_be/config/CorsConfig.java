package com.payment_record_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Global CORS configuration.
 *
 * Registered as a servlet {@link CorsFilter} (rather than only a
 * {@code WebMvcConfigurer.addCorsMappings}) so that CORS headers are applied
 * at the filter chain level — this keeps working unchanged if Spring
 * Security is added later, since a CorsFilter bean runs ahead of the
 * security filter chain and both can share this same
 * {@link CorsConfigurationSource}.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // allowedOriginPatterns("*") allows any origin (localhost, Vercel, Netlify,
        // custom domains, admin panel apps, etc.) while still permitting
        // allowCredentials(true) below — plain allowedOrigins("*") does not allow
        // that combination per the CORS spec.
        configuration.setAllowedOriginPatterns(List.of("*"));

        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Accept any request header (Authorization, Content-Type, custom headers, etc.)
        configuration.setAllowedHeaders(List.of("*"));

        // Headers the browser is allowed to read from the response.
        configuration.setExposedHeaders(List.of(
                "Authorization",
                "Content-Disposition",
                "Location",
                "X-Total-Count"
        ));

        // Set to true so cookies/Authorization headers survive cross-origin calls
        // from the admin panel; safe with allowedOriginPatterns (see note above).
        configuration.setAllowCredentials(true);

        // Cache the preflight (OPTIONS) response for 1 hour to cut down on
        // repeated preflight round-trips from browsers.
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource) {
        return new CorsFilter(corsConfigurationSource);
    }
}
