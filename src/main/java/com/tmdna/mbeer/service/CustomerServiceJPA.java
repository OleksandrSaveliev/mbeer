package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.CustomerDTO;
import com.tmdna.mbeer.mapper.CustomerMapper;
import com.tmdna.mbeer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID customerId) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(
                customerRepository.findById(customerId).orElse(null)));
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomerFully(UUID id, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomer(UUID id) {

    }

    @Override
    public void uprateCustomerPartially(UUID id, CustomerDTO customer) {

    }
}
