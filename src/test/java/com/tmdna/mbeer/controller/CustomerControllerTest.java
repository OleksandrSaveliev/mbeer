package com.tmdna.mbeer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmdna.mbeer.config.ApiPaths;
import com.tmdna.mbeer.exception.NotFoundException;
import com.tmdna.mbeer.dto.CustomerDTO;
import com.tmdna.mbeer.service.CustomerService;
import com.tmdna.mbeer.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void getCustomerWithNotFoundException() throws Exception {
        given(customerService.getCustomerById(any(UUID.class)))
                .willReturn(Optional.empty());

        mvc.perform(get(ApiPaths.Customer.WITH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertInstanceOf(NotFoundException.class, result.getResolvedException()));
    }

    @Test
    void updateBeerPartially() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().getFirst();

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Name");

        mvc.perform(patch(ApiPaths.Customer.WITH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).uprateCustomerPartially(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertEquals(customer.getId(), uuidArgumentCaptor.getValue());
        assertEquals(customerMap.get("customerName"), customerArgumentCaptor.getValue().getCustomerName());
    }

    @Test
    void deleteCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().getFirst();

        mvc.perform(delete(ApiPaths.Customer.WITH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(uuidArgumentCaptor.capture());

        assertEquals(customer.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    void uprateCustomerFully() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().getFirst();

        mvc.perform(put(ApiPaths.Customer.WITH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerFully(any(UUID.class), any(CustomerDTO.class));
    }

    @Test
    void createCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().getFirst();
        customer.setId(null);
        customer.setUpdatedTime(null);
        customer.setCreatedTime(null);
        customer.setVersion(null);

        given(customerService.createCustomer(any(CustomerDTO.class)))
                .willReturn(customerServiceImpl.getAllCustomers().get(1));

        mvc.perform(post(ApiPaths.Customer.BASE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getCustomer() throws Exception {

        CustomerDTO customer = customerServiceImpl.getAllCustomers().getFirst();

        given(customerService.getCustomerById(customer.getId())).willReturn(Optional.of(customer));

        mvc.perform(get(ApiPaths.Customer.WITH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(customer.getCustomerName())));
    }

    @Test
    void getAllCustomers() throws Exception {

        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mvc.perform(get(ApiPaths.Customer.BASE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(customerServiceImpl.getAllCustomers().size())));
    }
}