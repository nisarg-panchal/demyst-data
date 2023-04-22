package com.nisarg.demyst.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.TRACE;

import com.nisarg.demyst.bean.AccountingProvider;
import com.nisarg.demyst.bean.BalanceSheet;
import com.nisarg.demyst.bean.LoanApplication;
import com.nisarg.demyst.bean.LoanProcessResponse;
import com.nisarg.demyst.service.AccountProviderService;
import com.nisarg.demyst.service.LoanProcessor;
import java.io.IOException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
    origins = "http://localhost:5173",
    allowedHeaders = "*",
    methods = {GET, POST, OPTIONS, TRACE, DELETE, HEAD, PATCH, PUT}
)
@RestController
@Slf4j
@RequestMapping("/api/v1/accounting-provider")
@RequiredArgsConstructor
public class AccountingProviderController {

  private final AccountProviderService accountProviderService;
  private final LoanProcessor loanProcessor;

  @GetMapping("/{provider}")
  public ResponseEntity<BalanceSheet> getBalanceSheet(@PathVariable String provider)
      throws IOException {
    return ResponseEntity.ok()
        .body(new BalanceSheet(accountProviderService.getBalanceSheetData(provider)));
  }

  @PostMapping("/process-loan-app")
  public ResponseEntity<LoanProcessResponse> processLoanApplication(
      @RequestBody @NonNull LoanApplication loanApplication) throws IOException {
    log.info(loanApplication.toString());
    return ResponseEntity.ok().body(loanProcessor.processLoan(loanApplication));
  }

  @GetMapping("/dummy")
  public ResponseEntity<LoanApplication> getLoanApplication() {
    return ResponseEntity.ok().body(new LoanApplication("ABC", new BigDecimal("50000"), AccountingProvider.XERO, null));
  }
}
