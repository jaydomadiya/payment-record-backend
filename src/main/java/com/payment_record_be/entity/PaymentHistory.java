package com.payment_record_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "payment_history",
        indexes = {
                @Index(name = "idx_payment_history_payment_id", columnList = "payment_id", unique = true),
                @Index(name = "idx_payment_history_order_id", columnList = "order_id"),
                @Index(name = "idx_payment_history_mobile_number", columnList = "mobile_number"),
                @Index(name = "idx_payment_history_event_id", columnList = "event_id"),
                @Index(name = "idx_payment_history_payment_status", columnList = "payment_status"),
                @Index(name = "idx_payment_history_created_at", columnList = "created_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentHistory implements Serializable {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "payment_id", length = 100)
    private String paymentId;

    @Column(name = "order_id", length = 100)
    private String orderId;

    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Column(name = "user_name", length = 150)
    private String userName;

    @Column(name = "mobile_number", length = 15)
    private String mobileNumber;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "upi_id", length = 100)
    private String upiId;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "payment_status", length = 50)
    private String paymentStatus;

    @Column(name = "gateway_name", length = 100)
    private String gatewayName;

    @Column(name = "gateway_response", columnDefinition = "TEXT")
    private String gatewayResponse;

    @Column(name = "event_id", length = 100)
    private String eventId;

    @Column(name = "event_name", length = 200)
    private String eventName;

    @Column(name = "ticket_quantity")
    private Integer ticketQuantity;

    @Column(name = "ticket_type", length = 50)
    private String ticketType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
