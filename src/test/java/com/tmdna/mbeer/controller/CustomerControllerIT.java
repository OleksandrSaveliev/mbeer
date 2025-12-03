package com.tmdna.mbeer.controller;

import com.tmdna.mbeer.dto.CustomerDTO;
import com.tmdna.mbeer.exception.NotFoundException;
import com.tmdna.mbeer.model.Customer;
import com.tmdna.mbeer.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    CustomerController controller;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void getCustomerByIdNotFoundTest () {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");

        assertThrows(NotFoundException.class,() -> controller.getCustomerById(id));
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