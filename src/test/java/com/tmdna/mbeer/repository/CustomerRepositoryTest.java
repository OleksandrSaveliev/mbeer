package com.tmdna.mbeer.repository;

import com.tmdna.mbeer.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void saveCustomerTest() {
        final String customerName = "testCustomerName";

        Customer savedCustomer = customerRepository.save(Customer.builder()
                .customerName(customerName)
                .build());

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        assertEquals(customerName, savedCustomer.getCustomerName());
    }
}