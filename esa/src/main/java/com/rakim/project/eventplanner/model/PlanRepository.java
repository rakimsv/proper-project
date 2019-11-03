package com.rakim.project.eventplanner.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Plan findByName(String name);
    List<Plan> findAllByUserId(String id);
}