package com.demo.crm.controller;

import com.demo.crm.model.Sale;
import com.demo.crm.service.SaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

	private static final Logger logger = LoggerFactory.getLogger(SaleController.class);

	@Autowired
	private SaleService saleService;

	@GetMapping
	public ResponseEntity<List<Sale>> getAllSales() {
		logger.info("Fetching all sales");
		return ResponseEntity.ok(saleService.getAllSales());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
		logger.info("Fetching sale with ID: {}", id);
		return ResponseEntity.ok(saleService.getSaleById(id));
	}

	@PostMapping
	public ResponseEntity<Sale> addSale(@RequestBody Sale sale) {
		logger.info("Adding new sale for customer ID: {}", sale.getCustomer().getId());
		Sale savedSale = saleService.addSale(sale);
		return ResponseEntity.status(201).body(savedSale);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Sale> updateSale(@PathVariable Long id, @RequestBody Sale updatedSale) {
		logger.info("Updating sale with ID: {}", id);
		Sale sale = saleService.updateSale(id, updatedSale);
		return ResponseEntity.ok(sale);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSale(@PathVariable Long id) {
		logger.info("Deleting sale with ID: {}", id);
		saleService.deleteSale(id);
		return ResponseEntity.ok("Sale deleted successfully!");
	}
}
