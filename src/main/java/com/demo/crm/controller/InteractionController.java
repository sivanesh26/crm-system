package com.demo.crm.controller;

import com.demo.crm.model.Interaction;
import com.demo.crm.service.InteractionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/interactions")
public class InteractionController {

	private static final Logger logger = LoggerFactory.getLogger(InteractionController.class);
	private final InteractionService interactionService;

	public InteractionController(InteractionService interactionService) {
		this.interactionService = interactionService;
	}

	@GetMapping
	public ResponseEntity<List<Interaction>> getAllInteractions() {
		logger.info("Fetching all interactions");
		List<Interaction> interactions = interactionService.getAllInteractions();
		return ResponseEntity.ok(interactions);
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<Interaction>> getInteractionsByCustomer(@PathVariable Long customerId) {
		logger.info("Fetching interactions for customer ID: {}", customerId);
		List<Interaction> interactions = interactionService.getInteractionsByCustomerId(customerId);
		return ResponseEntity.ok(interactions);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Interaction>> getInteractionById(@PathVariable Long id) {
		logger.info("Fetching interaction with ID: {}", id);
		Optional<Interaction> interaction = interactionService.getInteractionById(id);
		return ResponseEntity.ok(interaction);
	}

	@PostMapping
	public ResponseEntity<Interaction> addInteraction(@RequestBody Interaction interaction) {
		logger.info("Adding new interaction for customer ID: {}", interaction.getCustomer().getId());
		Interaction savedInteraction = interactionService.addInteraction(interaction);
		logger.info("Interaction added successfully with ID: {}", savedInteraction.getId());
		return ResponseEntity.ok(savedInteraction);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInteraction(@PathVariable Long id) {
		logger.warn("Deleting interaction with ID: {}", id);
		interactionService.deleteInteraction(id);
		logger.info("Interaction with ID: {} deleted successfully", id);
		return ResponseEntity.ok("Interaction deleted successfully");
	}
}
