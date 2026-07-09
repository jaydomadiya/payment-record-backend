# syntax=docker/dockerfile:1

########################
# Build stage
########################
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /workspace

# Copy only the files needed to resolve dependencies first so this layer is
# reused by Docker's cache whenever only application source changes.
COPY mvnw pom.xml ./
COPY .mvn/ .mvn/
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline

# Now copy the source and build the application jar.
COPY src ./src
RUN ./mvnw -B -DskipTests package

########################
# Runtime stage
########################
FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

# Run as a non-root user.
RUN addgroup --system spring && adduser --system --ingroup spring --no-create-home spring

# Only the built jar is copied into the runtime image (finalName=app in pom.xml).
COPY --from=build /workspace/target/app.jar app.jar
RUN chown spring:spring app.jar

USER spring

# Render assigns the external port via $PORT at runtime; default to 8080 for
# `docker run -p 8080:8080 payment-api` without any extra flags.
ENV PORT=8080
EXPOSE 8080

# SPRING_DATASOURCE_URL / SPRING_DATASOURCE_USERNAME / SPRING_DATASOURCE_PASSWORD /
# SPRING_PROFILES_ACTIVE are read directly from the environment by Spring Boot's
# relaxed property binding — no extra wiring needed here.
# "exec" replaces the shell as PID 1 so SIGTERM from Render/Docker reaches the JVM
# directly for graceful shutdown, instead of being swallowed by the shell.
CMD ["sh", "-c", "exec java -Dserver.port=${PORT} -jar app.jar"]
