package com.drifter.spring_6_reactive.mappers;

import com.drifter.spring_6_reactive.domain.Customer;
import com.drifter.spring_6_reactive.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);
    CustomerDTO customerToCustomerDto(Customer customer);
}
