package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "Accounts",
        description = "Schema to hold Account information")
public class AccountsDto {
    @Schema(description = "Account number of Eazy bank account")
    @NotEmpty(message = "Account number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number must be 10 digits")
    private Long accountNumber;
    @NotEmpty(message = "AccountType  can not be null or empty")
    @Schema(description = "Account type of Eazy bank account",example = "savings")
    private String accountType;
    @NotEmpty(message = "BranchAddress can not be null or empty")
    @Schema(description = "Branch address of Eazy bank Account ")
    private String branchAddress;
}
