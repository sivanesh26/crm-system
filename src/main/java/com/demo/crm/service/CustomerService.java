package com.demo.crm.service;

import com.demo.crm.model.Customer;
import com.demo.crm.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<Customer> getAllCustomers() {
		logger.info("Fetching all customers");
		return customerRepository.findAll();
	}

	public Customer getCustomerById(Long id) {
		logger.info("Fetching customer with ID: {}", id);
		return customerRepository.findById(id).orElseThrow(() -> {
			logger.error("Customer not found with ID: {}", id);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + id);
		});
	}

	public Customer addCustomer(Customer customer) {
		logger.info("Adding new customer: {}", customer.getName());
		return customerRepository.save(customer);
	}

	public Customer updateCustomer(Long id, Customer updatedCustomer) {
		logger.info("Updating customer with ID: {}", id);
		Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> {
			logger.error("Customer not found with ID: {}", id);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + id);
		});

		existingCustomer.setName(updatedCustomer.getName());
		existingCustomer.setEmail(updatedCustomer.getEmail());
		existingCustomer.setPhone(updatedCustomer.getPhone());
		existingCustomer.setAddress(updatedCustomer.getAddress());
		return customerRepository.save(existingCustomer);
	}

	public void deleteCustomer(Long id) {
		logger.info("Deleting customer with ID: {}", id);
		if (!customerRepository.existsById(id)) {
			logger.error("Customer not found with ID: {}", id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + id);
		}
		customerRepository.deleteById(id);
	}
}
