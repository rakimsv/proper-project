package com.rakim.project.eventplanner.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rakim.project.eventplanner.model.Plan;
import com.rakim.project.eventplanner.model.PlanRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
class PlanController {

    private final Logger log = LoggerFactory.getLogger(PlanController.class);
    private PlanRepository planRepository;

    public PlanController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @GetMapping("/plans")
    Collection<Plan> plans() {
        return planRepository.findAll();
    }

    @GetMapping("/plan/{id}")
    ResponseEntity<?> getplan(@PathVariable Long id) {
        Optional<Plan> plan = planRepository.findById(id);
        return plan.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/plan")
    ResponseEntity<Plan> createplan(@Valid @RequestBody Plan plan) throws URISyntaxException {
        log.info("Request to create plan: {}", plan);
        Plan result = planRepository.save(plan);
        return ResponseEntity.created(new URI("/api/plan/" + result.getId()))
                .body(result);
    }

    @PostMapping("/plan/{id}")
    ResponseEntity<Plan> updateplan(@Valid @RequestBody Plan plan) {
        log.info("Request to update plan: {}", plan);
        Plan result = planRepository.save(plan);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/plan/{id}")
    public ResponseEntity<?> deleteplan(@PathVariable Long id) {
        log.info("Request to delete plan: {}", id);
        planRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}