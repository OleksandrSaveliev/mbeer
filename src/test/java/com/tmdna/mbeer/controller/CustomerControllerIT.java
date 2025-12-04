package com.tmdna.mbeer.controller;

import com.tmdna.mbeer.dto.CustomerDTO;
import com.tmdna.mbeer.exception.NotFoundException;
import com.tmdna.mbeer.mapper.CustomerMapper;
import com.tmdna.mbeer.model.Customer;
import com.tmdna.mbeer.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {
    private static final UUID FAILED_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Autowired
    CustomerController controller;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void deleteCustomerByIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> controller.deleteCustomer(FAILED_ID));
    }

    @Test
    void updateCustomerPartiallyNotFoundTest() {
        CustomerDTO customerDTO = CustomerDTO.builder().build();
        assertThrows(NotFoundException.class, () -> controller.updateCustomerPartially(FAILED_ID, customerDTO));
    }

    @Rollback
    @Transactional
    @Test
    void updateCustomerPartially() {
        Customer customer = customerRepository.findAll().getFirst();
        UUID id = customer.getId();
        final String newName = "New Name";
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setCustomerName(newName);

        ResponseEntity<Void> response = controller.updateCustomerPartially(id, customerDTO);
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertEquals(newName, customerRepository.findById(id).orElseThrow(NotFoundException::new).getCustomerName());
    }

    @Rollback
    @Transactional
    @Test
    void deleteCustomerByIdTest() {
        UUID id = customerRepository.findAll().getFirst().getId();

        assertEquals(HttpStatusCode.valueOf(204), controller.deleteCustomer(id).getStatusCode());
        assertThat(customerRepository.findById(id)).isEmpty();
    }

    @Test
    void updateCustomerFullyNotFoundTest() {
        CustomerDTO customerDTO = CustomerDTO.builder().build();
        assertThrows(NotFoundException.class, () -> controller.updateCustomerFully(FAILED_ID, customerDTO));
    }

    @Rollback
    @Transactional
    @Test
    void updateCustomerFullyTest() {
        Customer customer = customerRepository.findAll().getFirst();
        UUID id = customer.getId();
        final String newName = "New Name";
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setCustomerName(newName);

        ResponseEntity<Void> response = controller.updateCustomerFully(id, customerDTO);
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertEquals(newName, customerRepository.findById(id).orElseThrow(NotFoundException::new).getCustomerName());
    }

    @Test
    void getCustomerByIdNotFoundTest () {

        assertThrows(NotFoundException.class, () -> controller.getCustomerById(FAILED_ID));
    }

    @Test
    void getCustomerByIdTest () {
        Customer customer = customerRepository.findAll().getFirst();

        assertNotNull(controller.getCustomerById(customer.getId()));
    }

    @Test
    void getAllCustomersTest() {
        List<CustomerDTO> customers = controller.getAllCustomers().getBody();

        Assertions.assertNotNull(customers);
        assertEquals(3, customers.size());
    }

    @Rollback
    @Transactional
    @Test
    void getEmptyCustomerList () {
        customerRepository.deleteAll();
        List<CustomerDTO> customers = controller.getAllCustomers().getBody();

        Assertions.assertNotNull(customers);
        assertEquals(0, customers.size());
    }
}