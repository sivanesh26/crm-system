package com.demo.crm.controller;

import com.demo.crm.model.Customer;
import com.demo.crm.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	@Test
	void testGetAllCustomers() throws Exception {
		Customer customer1 = new Customer("siva", "siva@gamil.com", "1234567890", "coimbatore");
		Customer customer2 = new Customer("surya", "surya@gamil.com", "0987654321", "chennai");

		when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

		mockMvc.perform(get("/customers")).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(2)))
				.andExpect(jsonPath("$[0].name", is("siva")));
	}

	@Test
	void testGetCustomerById() throws Exception {
		Customer customer = new Customer("siva", "siva@gmail.com", "1234567890", "coimbatore");
		customer.setId(1L);

		when(customerService.getCustomerById(1L)).thenReturn(customer);

		mockMvc.perform(get("/customers/1")).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("siva")))
				.andExpect(jsonPath("$.email", is("siva@gmail.com"))).andExpect(jsonPath("$.phone", is("1234567890")))
				.andExpect(jsonPath("$.address", is("coimbatore")));
	}

	@Test
	void testAddCustomer() throws Exception {
		Customer customer = new Customer("siva", "siva@gmail.com", "1234567890", "coimbatore");

		when(customerService.addCustomer(Mockito.any(Customer.class))).thenReturn(customer);

		mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\":\"siva\", \"email\":\"siva@gamil.com\", \"phone\":\"1234567890\", \"address\":\"coimbaotre\"}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("siva")));
	}

	@Test
	void testDeleteCustomer() throws Exception {
		doNothing().when(customerService).deleteCustomer(1L);

		mockMvc.perform(delete("/customers/1")).andExpect(status().isOk())
				.andExpect(content().string("Customer deleted successfully!"));
	}

	@Test
	void testUpdateCustomer() throws Exception {
		Customer updatedCustomer = new Customer("siva Updated", "siva.new@gmail.com", "1112223333", "new coimbaore");
		updatedCustomer.setId(1L);

		when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(updatedCustomer);

		mockMvc.perform(put("/customers/1").contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\":\"siva Updated\", \"email\":\"siva.new@gmail.com\", \"phone\":\"1112223333\", \"address\":\"new coimbaore\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", is("siva Updated")));
	}
}
