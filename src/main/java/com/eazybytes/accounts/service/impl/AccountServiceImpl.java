package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final RestTemplateCustomizer restTemplateCustomizer;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.toCustomer(customerDto);
        Optional<Customer> optionalCustomer= customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with this mobile number" + customerDto.getMobileNumber());
        }
        Customer savedCustomer=customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));

    }

    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {

        Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer" ,"mobilenNumber",mobileNumber));

        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(()-> new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.toCustomerDto(customer);
        customerDto.setAccountsDto(AccountsMapper.toAccountsDto(accounts));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto!=null) {
            Accounts accounts;
            accounts=accountRepository.findById(accountsDto.getAccountNumber()).
                orElseThrow(()->new ResourceNotFoundException("Account","accountNumber",accountsDto.getAccountNumber().toString()));
         accounts.setAccountType(accountsDto.getAccountType());
         accounts.setBranchAddress(accountsDto.getBranchAddress());
         accountRepository.save(accounts);
        Customer customer= customerRepository.findById(accounts.getCustomerId())
                    .orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber", customerDto.getMobileNumber()));
          customer.setName(customerDto.getName());
          customer.setMobileNumber(customerDto.getMobileNumber());
          customer.setEmail(customerDto.getEmail());
           customerRepository.save(customer);
            isUpdated = true;
        }




        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer= customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Accounts createNewAccount(Customer savedCustomer) {
       long randomAccNumber = 1000000000L+new Random().nextInt(900000000);

       return Accounts.builder()
                .customerId(savedCustomer.getCustomerId())
                .accountNumber(randomAccNumber)
                .accountType(AccountsConstants.SAVINGS)
                .branchAddress(AccountsConstants.ADDRESS)
                .build();

    }
}
