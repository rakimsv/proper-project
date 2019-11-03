package com.rakim.project.eventplanner.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rakim.project.eventplanner.model.Plan;
import com.rakim.project.eventplanner.model.PlanRepository;
import com.rakim.project.eventplanner.model.User;
import com.rakim.project.eventplanner.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
class PlanController {

    private final Logger log = LoggerFactory.getLogger(PlanController.class);
    private PlanRepository planRepository;
    private UserRepository userRepository;

    public PlanController(PlanRepository planRepository, UserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/plans")
    Collection<Plan> plans(Principal principal) {
        return planRepository.findAllByUserId(principal.getName());
    }

    @GetMapping("/plan/{id}")
    ResponseEntity<?> getplan(@PathVariable Long id) {
        Optional<Plan> plan = planRepository.findById(id);
        return plan.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/plan")
    ResponseEntity<Plan> createPlan(@Valid @RequestBody Plan plan, @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("Request to create plan: {}", plan);
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();
        
        Optional<User> user = userRepository.findById(userId);
        plan.setUser(user.orElse(new User(userId,
                        details.get("name").toString(), details.get("email").toString())));
        
        Plan result = planRepository.save(plan);
        return ResponseEntity.created(new URI("/api/plan/" + result.getId()))
                .body(result);
    }

    @PostMapping("/plan/{id}")
    ResponseEntity<Plan> updatePlan(@Valid @RequestBody Plan plan) {
        log.info("Request to update plan: {}", plan);
        Plan result = planRepository.save(plan);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/plan/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable Long id) {
        log.info("Request to delete plan: {}", id);
        planRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}