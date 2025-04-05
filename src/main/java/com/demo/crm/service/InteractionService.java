package com.demo.crm.service;

import com.demo.crm.model.Customer;
import com.demo.crm.model.Interaction;
import com.demo.crm.repository.InteractionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionService {

	private static final Logger logger = LoggerFactory.getLogger(InteractionService.class);
	private final InteractionRepository interactionRepository;
	private final CustomerService customerService;

	@Autowired
	public InteractionService(InteractionRepository interactionRepository, CustomerService customerService) {
		this.interactionRepository = interactionRepository;
		this.customerService = customerService;
	}

	public List<Interaction> getAllInteractions() {
		logger.info("Fetching all interactions from the database");
		List<Interaction> interactions = interactionRepository.findAll();
		logger.info("Found {} interactions", interactions.size());
		return interactions;
	}

	public List<Interaction> getInteractionsByCustomerId(Long customerId) {
		logger.info("Fetching interactions for customer ID: {}", customerId);
		List<Interaction> interactions = interactionRepository.findByCustomerId(customerId);
		logger.info("Found {} interactions for customer ID: {}", interactions.size(), customerId);
		return interactions;
	}

	public Optional<Interaction> getInteractionById(Long id) {
		logger.info("Fetching interaction with ID: {}", id);
		Optional<Interaction> interaction = interactionRepository.findById(id);
		if (interaction.isPresent()) {
			logger.info("Interaction found with ID: {}", id);
		} else {
			logger.warn("No interaction found with ID: {}", id);
		}
		return interaction;
	}

	public Interaction addInteraction(Interaction interaction) {
		Long customerId = interaction.getCustomer().getId();
		Customer fullCustomer = customerService.getCustomerById(customerId); // fetch complete customer
		interaction.setCustomer(fullCustomer); // set full customer info
		logger.info("Saving new interaction for customer: {}", fullCustomer.getName());
		Interaction savedInteraction = interactionRepository.save(interaction);
		logger.info("Interaction saved successfully with ID: {}", savedInteraction.getId());
		return savedInteraction;
	}

	public void deleteInteraction(Long id) {
		logger.warn("Deleting interaction with ID: {}", id);
		interactionRepository.deleteById(id);
		logger.info("Interaction with ID: {} deleted successfully", id);
	}
}
