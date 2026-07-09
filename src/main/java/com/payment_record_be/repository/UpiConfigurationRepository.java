package com.payment_record_be.repository;

import com.payment_record_be.entity.UpiConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UpiConfigurationRepository extends JpaRepository<UpiConfiguration, UUID> {

    Optional<UpiConfiguration> findFirstByIsActiveTrueOrderByUpdatedAtDesc();
}
