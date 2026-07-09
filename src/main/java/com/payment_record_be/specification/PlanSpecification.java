package com.payment_record_be.specification;

import com.payment_record_be.dto.request.PlanFilterRequest;
import com.payment_record_be.entity.SubscriptionPlan;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class PlanSpecification {

    private PlanSpecification() {
    }

    public static Specification<SubscriptionPlan> withFilters(PlanFilterRequest filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter == null) {
                return predicate;
            }

            if (StringUtils.hasText(filter.getPlanCode())) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("planCode")), "%" + filter.getPlanCode().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filter.getPlanName())) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("planName")), "%" + filter.getPlanName().toLowerCase() + "%"));
            }

            if (filter.getIsActive() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isActive"), filter.getIsActive()));
            }

            return predicate;
        };
    }
}
