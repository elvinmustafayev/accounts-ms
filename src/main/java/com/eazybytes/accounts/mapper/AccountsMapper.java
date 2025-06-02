package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.entity.Accounts;

public class AccountsMapper {
    public static AccountsMapper INSTANCE = new AccountsMapper();

    public static AccountsDto toAccountsDto(Accounts accounts) {
         return AccountsDto.builder()
                 .accountNumber(accounts.getAccountNumber())
                 .accountType(accounts.getAccountType())
                 .branchAddress(accounts.getBranchAddress()).build();
    }
    public static Accounts toAccounts(AccountsDto accountsDto) {
              return Accounts.builder()
                              .accountNumber(accountsDto.getAccountNumber())
                              .accountType(accountsDto.getAccountType())
                              .branchAddress(accountsDto.getBranchAddress()).build();
    }

}
