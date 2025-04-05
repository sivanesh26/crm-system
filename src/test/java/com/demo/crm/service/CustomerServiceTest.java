package com.demo.crm.service;

import com.demo.crm.model.Customer;
import com.demo.crm.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerService customerService;

	private Customer customer;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		customer = new Customer("Sivanesh", "sivanesh@gmail.com", "1234567890", "chennai");
		customer.setId(1L);
	}

	@Test
	void testGetAllCustomers() {
		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));

		List<Customer> customers = customerService.getAllCustomers();

		assertFalse(customers.isEmpty());
		assertEquals(1, customers.size());
		assertEquals("Sivanesh", customers.get(0).getName());
		verify(customerRepository, times(1)).findAll();
	}

	@Test
	void testGetCustomerById() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

		Customer foundCustomer = customerService.getCustomerById(1L);

		assertNotNull(foundCustomer);
		assertEquals("Sivanesh", foundCustomer.getName());
		verify(customerRepository, times(1)).findById(1L);
	}

	@Test
	void testAddCustomer() {
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		Customer savedCustomer = customerService.addCustomer(customer);

		assertNotNull(savedCustomer);
		assertEquals("Sivanesh", savedCustomer.getName());
		verify(customerRepository, times(1)).save(customer);
	}

	@Test
	void testUpdateCustomer() {
		Customer updatedCustomer = new Customer("Ashok", "ashok@gmail.com", "9876543210", "coimbaore");

		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

		Customer result = customerService.updateCustomer(1L, updatedCustomer);

		assertNotNull(result);
		assertEquals("Ashok", result.getName());
		assertEquals("ashok@gmail.com", result.getEmail());
		assertEquals("9876543210", result.getPhone());
		assertEquals("coimbaore", result.getAddress());

		verify(customerRepository, times(1)).findById(1L);
		verify(customerRepository, times(1)).save(any(Customer.class));
	}

	@Test
	void testDeleteCustomer() {
		when(customerRepository.existsById(1L)).thenReturn(true);
		doNothing().when(customerRepository).deleteById(1L);

		assertDoesNotThrow(() -> customerService.deleteCustomer(1L));

		verify(customerRepository, times(1)).existsById(1L);
		verify(customerRepository, times(1)).deleteById(1L);
	}

}
