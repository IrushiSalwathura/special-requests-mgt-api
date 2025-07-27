package com.personal.specialrequestsmanagementapi.repositories;

import com.personal.specialrequestsmanagementapi.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findByEmail(String email);
    boolean existsByEmail(String email);
}
