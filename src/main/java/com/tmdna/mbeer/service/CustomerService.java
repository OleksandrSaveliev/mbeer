package com.tmdna.mbeer.service;

import com.tmdna.mbeer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(UUID customerId);

    Customer createCustomer(Customer customer);

    void updateCustomerFully(UUID id, Customer customer);

    void deleteCustomer(UUID id);

    void uprateCustomerPartially(UUID id, Customer customer);
}
