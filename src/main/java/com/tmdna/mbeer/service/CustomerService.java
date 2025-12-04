package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.CustomerDTO;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    Optional<CustomerDTO> getCustomerById(UUID customerId);

    CustomerDTO createCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerFully(UUID id, CustomerDTO customer);

    boolean deleteCustomer(UUID id);

    void uprateCustomerPartially(UUID id, CustomerDTO customer);
}
