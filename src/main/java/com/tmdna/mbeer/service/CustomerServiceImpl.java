package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.CustomerDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    Map<UUID, CustomerDto> customers = new HashMap<>();

    public CustomerServiceImpl() {
        CustomerDto customer1 = CustomerDto.builder()
                .id(UUID.randomUUID())
                .customerName("Sasha")
                .version(1)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        CustomerDto customer2 = CustomerDto.builder()
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
    public List<CustomerDto> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Optional<CustomerDto> getCustomerById(UUID customerId) {
        return Optional.of(customers.get(customerId));
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customer) {
        CustomerDto createdCustomer = CustomerDto.builder()
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
    public void updateCustomerFully(UUID id, CustomerDto customer) {
        CustomerDto existing = customers.get(id);
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
    public void uprateCustomerPartially(UUID id, CustomerDto customer) {
        CustomerDto existing = customers.get(id);

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
