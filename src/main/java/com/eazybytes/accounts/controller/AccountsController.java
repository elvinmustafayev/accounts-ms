package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.DependentSchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@Tag(
        name="CRUD REST APIs for Accounts in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
)

public class AccountsController {
    private final IAccountService iaccountService;
    @Operation(summary = "Create Account REST API",
    description = "REST API to create new Customer & Account inside EazyBank")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "201",
            description = "Http status created"),
           @ApiResponse(
                   responseCode = "500",
                   description = "Http Status Internal Server Error",
                 content = @Content(
                          schema=@Schema(implementation = ErrorResponseDto.class)
                  )
           )
   })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iaccountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));

    }

    @GetMapping ("/fetch")
    @Operation(summary = "Fetch user Account",description = "Endpoint to get user account details")
    public ResponseEntity<CustomerDto> fetchAccountDetails(
            @Parameter( name ="mobile num", description = "Mobile number of the user to retrieve", example = "0703234320")
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",message = "mobile number must be 10 digits")String mobileNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iaccountService.fetchAccountDetails(mobileNumber));
    }

    @PutMapping("/update")
    @Operation(summary = "Update user account",description = "Endpoint to update user account details")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean updated = iaccountService.updateAccount(customerDto);
        if (updated) {
            return  ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",message = "mobile number must be 10 digits")String mobileNumber) {
        boolean deleted = iaccountService.deleteAccount(mobileNumber);
        if (deleted) {
            return ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
    }
}
