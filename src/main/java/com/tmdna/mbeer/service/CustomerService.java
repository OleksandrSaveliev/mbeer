package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();

    Optional<CustomerDto> getCustomerById(UUID customerId);

    CustomerDto createCustomer(CustomerDto customer);

    void updateCustomerFully(UUID id, CustomerDto customer);

    void deleteCustomer(UUID id);

    void uprateCustomerPartially(UUID id, CustomerDto customer);
}
