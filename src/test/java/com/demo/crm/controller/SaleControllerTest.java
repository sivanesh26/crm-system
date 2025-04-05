package com.demo.crm.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.crm.model.Customer;
import com.demo.crm.model.Sale;
import com.demo.crm.service.SaleService;

@WebMvcTest(SaleController.class)
@AutoConfigureMockMvc(addFilters = false)
class SaleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SaleService saleService;

	@Test
	void testGetAllSales() throws Exception {
		Customer customer = new Customer("Vijay", "vijay@example.com", "1234567890", "Bangalore");
		customer.setId(1L);

		Sale sale1 = new Sale(1L, "Product A", 100.0, LocalDate.now(), customer);
		Sale sale2 = new Sale(2L, "Product B", 200.0, LocalDate.now(), customer);

		when(saleService.getAllSales()).thenReturn(Arrays.asList(sale1, sale2));

		mockMvc.perform(get("/sales")).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(2)))
				.andExpect(jsonPath("$[0].product", is("Product A")));
	}

	@Test
	void testGetSaleById() throws Exception {
		Customer customer = new Customer("Vijay", "vijay@example.com", "1234567890", "Bangalore");
		customer.setId(1L);
		Sale sale = new Sale(1L, "Product A", 150.0, LocalDate.now(), customer);

		when(saleService.getSaleById(1L)).thenReturn(sale);

		mockMvc.perform(get("/sales/1")).andExpect(status().isOk()).andExpect(jsonPath("$.product", is("Product A")));
	}

	@Test
	void testAddSale() throws Exception {
		Customer customer = new Customer("Vijay", "vijay@example.com", "1234567890", "Bangalore");
		customer.setId(1L);
		Sale sale = new Sale(1L, "Product A", 150.0, LocalDate.of(2024, 4, 5), customer);

		when(saleService.addSale(Mockito.any(Sale.class))).thenReturn(sale);

		mockMvc.perform(post("/sales").contentType(MediaType.APPLICATION_JSON).content(
				"{\"product\":\"Product A\",\"amount\":150.0,\"date\":\"2024-04-05\",\"customer\":{\"id\":1}}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.product", is("Product A")));
	}

	@Test
	void testUpdateSale() throws Exception {
		Customer customer = new Customer("Vijay", "vijay@example.com", "1234567890", "Bangalore");
		customer.setId(1L);
		Sale updatedSale = new Sale(1L, "Updated Product", 200.0, LocalDate.of(2024, 4, 6), customer);

		when(saleService.updateSale(Mockito.eq(1L), Mockito.any(Sale.class))).thenReturn(updatedSale);

		mockMvc.perform(put("/sales/1").contentType(MediaType.APPLICATION_JSON).content(
				"{\"product\":\"Updated Product\",\"amount\":200.0,\"date\":\"2024-04-06\",\"customer\":{\"id\":1}}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.product", is("Updated Product")));
	}

	@Test
	void testDeleteSale() throws Exception {
		doNothing().when(saleService).deleteSale(1L);

		mockMvc.perform(delete("/sales/1")).andExpect(status().isOk())
				.andExpect(content().string("Sale deleted successfully!"));
	}
}
