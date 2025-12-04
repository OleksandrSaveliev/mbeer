package com.tmdna.mbeer.mapper;

import com.tmdna.mbeer.dto.CustomerDTO;
import com.tmdna.mbeer.model.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDTO customerToCustomerDto (Customer customer);
    Customer customerDtoToCustomer (CustomerDTO customerDTO);
}
