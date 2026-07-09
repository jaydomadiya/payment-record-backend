package com.payment_record_be.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring's relaxed property binder (used to build the DataSource) does not fail on an
 * unresolvable ${...} placeholder the way @Value does — it silently passes the raw,
 * unresolved text through, which then surfaces deep inside HikariCP/Hibernate as a
 * confusing "Driver claims to not accept jdbcUrl, ${SPRING_DATASOURCE_URL}" error.
 * This runs before any bean is created and fails fast with an explicit message naming
 * exactly which variable is missing.
 */
public class RequiredEnvironmentValidator implements EnvironmentPostProcessor {

    private static final String[] REQUIRED_PROPERTIES = {
            "SPRING_DATASOURCE_URL",
            "SPRING_DATASOURCE_USERNAME",
            "SPRING_DATASOURCE_PASSWORD"
    };

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        List<String> missing = new ArrayList<>();
        for (String property : REQUIRED_PROPERTIES) {
            String value = environment.getProperty(property);
            if (value == null || value.isBlank()) {
                missing.add(property);
            }
        }

        if (!missing.isEmpty()) {
            throw new IllegalStateException(
                    "Missing required environment variable(s): " + String.join(", ", missing)
                            + ". Set them on the hosting platform (e.g. Render service -> Environment) "
                            + "or in application-local.properties for local development.");
        }
    }
}
