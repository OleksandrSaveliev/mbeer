package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    Map<UUID, CustomerDTO> customers = new HashMap<>();

    public CustomerServiceImpl() {
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Sasha")
                .version(1)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Yura")
                .version(1)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        customers.put(customer1.getId(), customer1);
        customers.put(customer2.getId(), customer2);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID customerId) {
        return Optional.of(customers.get(customerId));
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        CustomerDTO createdCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .version(1)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        customers.put(createdCustomer.getId(), createdCustomer);

        return createdCustomer;
    }

    @Override
    public void updateCustomerFully(UUID id, CustomerDTO customer) {
        CustomerDTO existing = customers.get(id);
        if (existing != null) {
            existing.setCustomerName(customer.getCustomerName());
            existing.setVersion(existing.getVersion() + 1);
            existing.setUpdatedTime(LocalDateTime.now());
        }
    }

    @Override
    public void deleteCustomer(UUID id) {
        customers.remove(id);
    }

    @Override
    public void uprateCustomerPartially(UUID id, CustomerDTO customer) {
        CustomerDTO existing = customers.get(id);

        if (existing == null) {
            return;
        }

        if (StringUtils.hasText(customer.getCustomerName())) {
            existing.setCustomerName(customer.getCustomerName());
            existing.setVersion(existing.getVersion() + 1);
            existing.setUpdatedTime(LocalDateTime.now());
        }

    }
}
