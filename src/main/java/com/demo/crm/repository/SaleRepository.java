package com.demo.crm.repository;

import com.demo.crm.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT c.name, SUM(s.amount) FROM Sale s JOIN s.customer c GROUP BY c.name")
	List<Object[]> getTotalSalesPerCustomer();

	@Query("SELECT FUNCTION('MONTH', s.date), SUM(s.amount) FROM Sale s GROUP BY FUNCTION('MONTH', s.date) ORDER BY FUNCTION('MONTH', s.date)")
	List<Object[]> getMonthlySalesTrends();

	@Query("SELECT c.name, SUM(s.amount) FROM Sale s JOIN s.customer c GROUP BY c.name ORDER BY SUM(s.amount) DESC")
	List<Object[]> getTopCustomersByRevenue();

	@Query("SELECT s.product, SUM(s.amount) FROM Sale s GROUP BY s.product ORDER BY SUM(s.amount) DESC")
	List<Object[]> getHighestSellingProduct();
}
