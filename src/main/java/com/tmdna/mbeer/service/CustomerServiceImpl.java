package com.tmdna.mbeer.service;

import com.tmdna.mbeer.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    Map<UUID, Customer> customers = new HashMap<>();

    public CustomerServiceImpl() {
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Sasha")
                .version(1)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
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
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Optional<Customer> getCustomerById(UUID customerId) {
        return Optional.of(customers.get(customerId));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer createdCustomer = Customer.builder()
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
    public void updateCustomerFully(UUID id, Customer customer) {
        Customer existing = customers.get(id);
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
    public void uprateCustomerPartially(UUID id, Customer customer) {
        Customer existing = customers.get(id);

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
