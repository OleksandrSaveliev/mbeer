package com.tmdna.mbeer.controller;

import com.tmdna.mbeer.config.ApiPaths;
import com.tmdna.mbeer.exception.NotFoundException;
import com.tmdna.mbeer.dto.CustomerDTO;
import com.tmdna.mbeer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(ApiPaths.Customer.BASE)
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping(ApiPaths.ID)
    public ResponseEntity<Void> uprateCustomerPartially(
            @PathVariable("id") UUID id,
            @RequestBody CustomerDTO customer
    ) {
        customerService.uprateCustomerPartially(id, customer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(ApiPaths.ID)
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(ApiPaths.ID)
    public ResponseEntity<Void> uprateCustomerFully(
            @PathVariable("id") UUID id,
            @RequestBody CustomerDTO customer
    ) {
        customerService.updateCustomerFully(id, customer);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping(ApiPaths.ID)
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") UUID id) {

        log.debug("Getting user with Id: {}", id);

        return ResponseEntity.ok(customerService.getCustomerById(id)
                .orElseThrow(NotFoundException::new));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer) {
        CustomerDTO createdCustomer = customerService.createCustomer(customer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCustomer.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdCustomer);
    }

}
