package com.ranggarifqi.moneymanager.controllers.v1;

import com.ranggarifqi.moneymanager.account.IAccountService;
import com.ranggarifqi.moneymanager.account.dto.AccountResponse;
import com.ranggarifqi.moneymanager.account.dto.CreateAccountDTO;
import com.ranggarifqi.moneymanager.account.dto.CreateAccountResponse;
import com.ranggarifqi.moneymanager.common.response.APIResponse;
import com.ranggarifqi.moneymanager.common.response.ErrorResponse;
import com.ranggarifqi.moneymanager.model.Account;
import com.ranggarifqi.moneymanager.model.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/accounts")
public class AccountController {

  Logger logger = LoggerFactory.getLogger(AccountController.class);

  private final IAccountService accountService;

  @Autowired
  public AccountController(IAccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping(value = "")
  @ResponseBody
  public ResponseEntity<APIResponse<List<AccountResponse>>> findByOwnerId(Authentication authentication) {
    User user = this.getAuthenticatedUser(authentication);

    this.logger.info("Find Accounts By owner id: " + user.getId().toString());

    try {
      List<Account> result = this.accountService.findByUserId(user.getId());

      List<AccountResponse> response = result.stream().map(
              account -> new AccountResponse(
                      account.getId().toString(),
                      account.getUser().getId().toString(),
                      account.getAccountType(),
                      account.getName(),
                      account.getBalance(),
                      account.getCreatedAt(),
                      account.getUpdatedAt()
              )
      ).toList();

      return APIResponse.constructResponse(response);
    } catch (Exception e) {
      throw ErrorResponse.construct(e);
    }
  }

  @GetMapping(value = "/{id}")
  @ResponseBody
  public ResponseEntity<APIResponse<AccountResponse>> findById(@PathVariable String id, Authentication authentication){
    this.logger.info("Find Account by id " + id);

    User user = this.getAuthenticatedUser(authentication);

    try {
      Account account = this.accountService.findById(id, user.getId());

      return APIResponse.constructResponse(new AccountResponse(account.getId().toString(), account.getUser().getId().toString(), account.getAccountType(), account.getName(), account.getBalance(), account.getCreatedAt(), account.getUpdatedAt()));
    } catch (Exception e) {
      throw ErrorResponse.construct(e);
    }
  }

  @PostMapping(value = "")
  public ResponseEntity<APIResponse<CreateAccountResponse>> create(@Valid @RequestBody CreateAccountDTO body, Authentication authentication) {
    this.logger.info("Creating a new account");
    try {

      User user = this.getAuthenticatedUser(authentication);

      Account newAccount = this.accountService.create(body, user.getId().toString());
      CreateAccountResponse response = new CreateAccountResponse(newAccount.getId(), newAccount.getAccountType(), newAccount.getUser().getId().toString(), newAccount.getName(), newAccount.getBalance());

      return APIResponse.constructResponse(response);
    } catch (Exception e) {
      throw ErrorResponse.construct(e);
    }
  }

  private User getAuthenticatedUser(Authentication authentication) {
    return (User) authentication.getPrincipal();
  }
}
