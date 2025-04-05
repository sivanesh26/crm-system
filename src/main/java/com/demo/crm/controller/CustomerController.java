package com.demo.crm.controller;

import com.demo.crm.model.Customer;
import com.demo.crm.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		logger.info("Fetching all customers");
		List<Customer> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		logger.info("Fetching customer with ID: {}", id);
		Customer customer = customerService.getCustomerById(id);
		return ResponseEntity.ok(customer);
	}

	@PostMapping
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		logger.info("Adding new customer: {} - Address: {}", customer.getName(), customer.getAddress());
		Customer savedCustomer = customerService.addCustomer(customer);
		return ResponseEntity.status(201).body(savedCustomer);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
		logger.info("Updating customer with ID: {} - New Address: {}", id, updatedCustomer.getAddress());
		Customer customer = customerService.updateCustomer(id, updatedCustomer);
		return ResponseEntity.ok(customer);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
		logger.info("Deleting customer with ID: {}", id);
		customerService.deleteCustomer(id);
		return ResponseEntity.ok("Customer deleted successfully!");
	}
}
