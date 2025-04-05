package com.demo.crm.service;

import com.demo.crm.repository.SaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

	private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

	@Autowired
	private SaleRepository saleRepository;

	public List<Object[]> getTotalSalesPerCustomer() {
		logger.info("Fetching total sales per customer");
		return saleRepository.getTotalSalesPerCustomer();
	}

	public List<Object[]> getMonthlySalesTrends() {
		logger.info("Fetching monthly sales trends");
		return saleRepository.getMonthlySalesTrends();
	}

	public List<Object[]> getTopCustomersByRevenue() {
		logger.info("Fetching top customers by revenue");
		return saleRepository.getTopCustomersByRevenue();
	}

	public List<Object[]> getHighestSellingProduct() {
		logger.info("Fetching highest selling product");
		return saleRepository.getHighestSellingProduct();
	}
}
