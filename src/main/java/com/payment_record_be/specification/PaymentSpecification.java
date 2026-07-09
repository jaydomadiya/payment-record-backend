package com.payment_record_be.specification;

import com.payment_record_be.dto.request.PaymentFilterRequest;
import com.payment_record_be.entity.PaymentHistory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.time.ZoneId;

public final class PaymentSpecification {

    private PaymentSpecification() {
    }

    public static Specification<PaymentHistory> withFilters(PaymentFilterRequest filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter == null) {
                return predicate;
            }

            if (StringUtils.hasText(filter.getPaymentId())) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("paymentId")), "%" + filter.getPaymentId().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filter.getMobileNumber())) {
                predicate = cb.and(predicate,
                        cb.like(root.get("mobileNumber"), "%" + filter.getMobileNumber() + "%"));
            }

            if (StringUtils.hasText(filter.getEventId())) {
                predicate = cb.and(predicate, cb.equal(root.get("eventId"), filter.getEventId()));
            }

            if (StringUtils.hasText(filter.getPaymentStatus())) {
                predicate = cb.and(predicate,
                        cb.equal(cb.upper(root.get("paymentStatus")), filter.getPaymentStatus().toUpperCase()));
            }

            if (StringUtils.hasText(filter.getPaymentMethod())) {
                predicate = cb.and(predicate,
                        cb.equal(cb.upper(root.get("paymentMethod")), filter.getPaymentMethod().toUpperCase()));
            }

            if (filter.getFromDate() != null) {
                var fromInstant = filter.getFromDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createdAt"), fromInstant));
            }

            if (filter.getToDate() != null) {
                var toInstant = filter.getToDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdAt"), toInstant));
            }

            return predicate;
        };
    }
}
