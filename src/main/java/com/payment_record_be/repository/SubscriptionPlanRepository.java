package com.payment_record_be.repository;

import com.payment_record_be.entity.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, UUID>,
        JpaSpecificationExecutor<SubscriptionPlan> {

    boolean existsByPlanCode(String planCode);

    List<SubscriptionPlan> findByIsActiveTrueOrderByDisplayOrderAsc();
}
