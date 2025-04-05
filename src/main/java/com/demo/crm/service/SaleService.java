package com.demo.crm.service;

import com.demo.crm.model.Customer;
import com.demo.crm.model.Sale;
import com.demo.crm.repository.CustomerRepository;
import com.demo.crm.repository.SaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SaleService {

	private static final Logger logger = LoggerFactory.getLogger(SaleService.class);

	private final SaleRepository saleRepository;
	private final CustomerRepository customerRepository;

	@Autowired
	public SaleService(SaleRepository saleRepository, CustomerRepository customerRepository) {
		this.saleRepository = saleRepository;
		this.customerRepository = customerRepository;
	}

	public List<Sale> getAllSales() {
		logger.info("Fetching all sales records");
		return saleRepository.findAll();
	}

	public Sale getSaleById(Long id) {
		logger.info("Fetching sale with ID: {}", id);
		return saleRepository.findById(id).orElseThrow(() -> {
			logger.error("Sale not found with ID: {}", id);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found with ID: " + id);
		});
	}

	public Sale addSale(Sale sale) {
		if (sale.getCustomer() == null || sale.getCustomer().getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer ID is required");
		}

		Long customerId = sale.getCustomer().getId();
		logger.info("Adding new sale for customer ID: {}", customerId);

		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
			logger.error("Customer not found with ID: {}", customerId);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + customerId);
		});

		sale.setCustomer(customer);
		return saleRepository.save(sale);
	}

	public Sale updateSale(Long id, Sale updatedSale) {
		logger.info("Updating sale with ID: {}", id);
		return saleRepository.findById(id).map(sale -> {
			sale.setAmount(updatedSale.getAmount());
			sale.setDate(updatedSale.getDate());
			sale.setProduct(updatedSale.getProduct());

			if (updatedSale.getCustomer() != null && updatedSale.getCustomer().getId() != null) {
				Long customerId = updatedSale.getCustomer().getId();
				Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
					logger.error("Customer not found with ID: {}", customerId);
					return new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Customer not found with ID: " + customerId);
				});

				sale.setCustomer(customer);
			}

			return saleRepository.save(sale);
		}).orElseThrow(() -> {
			logger.error("Sale not found with ID: {}", id);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found with ID: " + id);
		});
	}

	public void deleteSale(Long id) {
		logger.info("Deleting sale with ID: {}", id);
		if (!saleRepository.existsById(id)) {
			logger.error("Sale not found with ID: {}", id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found with ID: " + id);
		}
		saleRepository.deleteById(id);
	}
}
