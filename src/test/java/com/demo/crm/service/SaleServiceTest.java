package com.demo.crm.service;

import com.demo.crm.model.Customer;
import com.demo.crm.model.Sale;
import com.demo.crm.repository.CustomerRepository;
import com.demo.crm.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaleServiceTest {

	private SaleRepository saleRepository;
	private CustomerRepository customerRepository;
	private SaleService saleService;

	@BeforeEach
	void setUp() {
		saleRepository = mock(SaleRepository.class);
		customerRepository = mock(CustomerRepository.class);
		saleService = new SaleService(saleRepository, customerRepository);
	}

	@Test
	void testGetAllSales() {
		Sale sale1 = new Sale(1L, "Product A", 100.0, LocalDate.now(), new Customer());
		Sale sale2 = new Sale(2L, "Product B", 200.0, LocalDate.now(), new Customer());

		when(saleRepository.findAll()).thenReturn(Arrays.asList(sale1, sale2));

		var sales = saleService.getAllSales();

		assertEquals(2, sales.size());
		verify(saleRepository, times(1)).findAll();
	}

	@Test
	void testGetSaleById() {
		Sale sale = new Sale(1L, "Product A", 150.0, LocalDate.now(), new Customer());

		when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));

		Sale foundSale = saleService.getSaleById(1L);

		assertNotNull(foundSale);
		assertEquals("Product A", foundSale.getProduct());
	}

	@Test
	void testAddSale() {
		Customer customer = new Customer("Raj", "raj@gmail.com", "1234567890", "Mysore");
		customer.setId(1L);

		Sale sale = new Sale(null, "Product C", 300.0, LocalDate.now(), customer);

		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(saleRepository.save(ArgumentMatchers.any(Sale.class))).thenReturn(sale);

		Sale savedSale = saleService.addSale(sale);

		assertNotNull(savedSale);
		verify(saleRepository, times(1)).save(sale);
	}

	@Test
	void testDeleteSaleS() {
		when(saleRepository.existsById(1L)).thenReturn(true);

		saleService.deleteSale(1L);

		verify(saleRepository, times(1)).deleteById(1L);
	}

	@Test
	void testUpdateSale() {
		Customer customer = new Customer("Kishore", "kishore@gmail.com", "9876543210", "Delhi");
		customer.setId(1L);

		Sale existingSale = new Sale(1L, "Product D", 250.0, LocalDate.now(), customer);
		Sale updatedSale = new Sale(1L, "Product E", 400.0, LocalDate.now(), customer);

		when(saleRepository.findById(1L)).thenReturn(Optional.of(existingSale));
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(saleRepository.save(existingSale)).thenReturn(updatedSale);

		Sale result = saleService.updateSale(1L, updatedSale);

		assertEquals("Product E", result.getProduct());
		assertEquals(400.0, result.getAmount());
	}
}
