package com.demo.crm.controller;

import com.demo.crm.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private ReportService reportService;

	@GetMapping("/total-sales-per-customer")
	public List<Object[]> getTotalSalesPerCustomer() {
		logger.info("Fetching total sales per customer");
		return reportService.getTotalSalesPerCustomer();
	}

	@GetMapping("/monthly-sales-trends")
	public List<Object[]> getMonthlySalesTrends() {
		logger.info("Fetching monthly sales trends");
		return reportService.getMonthlySalesTrends();
	}

	@GetMapping("/top-customers")
	public List<Object[]> getTopCustomersByRevenue() {
		logger.info("Fetching top customers by revenue");
		return reportService.getTopCustomersByRevenue();
	}

	@GetMapping("/highest-selling-product")
	public List<Object[]> getHighestSellingProduct() {
		logger.info("Fetching highest selling product");
		return reportService.getHighestSellingProduct();
	}
}
