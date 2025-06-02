package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerMapper INSTANCE = new CustomerMapper();

    public static CustomerDto toCustomerDto(Customer customer) {

     return   CustomerDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .build();



    }
    public static Customer toCustomer(CustomerDto customerDto) {

      return   Customer.builder()
                 .name(customerDto.getName())
                 .email(customerDto.getEmail())
                 .mobileNumber(customerDto.getMobileNumber())
                 .build();
    }
}
