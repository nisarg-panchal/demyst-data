package com.nisarg.demyst.service;

import com.nisarg.demyst.bean.BalanceSheet;
import com.nisarg.demyst.bean.LoanApplication;
import com.nisarg.demyst.bean.LoanProcessResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanProcessorImpl implements LoanProcessor {

  private final AccountProviderService accountProviderService;

  @Override
  public LoanProcessResponse processLoan(@NonNull LoanApplication loanApplication)
      throws IOException {
    LoanProcessResponse loanProcessResponse = new LoanProcessResponse(20);
    loanApplication.setBalanceSheet(new BalanceSheet(accountProviderService.getBalanceSheetData(
        loanApplication.getAccountingProvider().name())));
    if (loanApplication.getBalanceSheet() != null) {
      double avgProfit = loanApplication.getBalanceSheet().getSheet().parallelStream()
          .mapToDouble(monthlyBalance -> monthlyBalance.getProfitOrLoss().doubleValue()).average()
          .getAsDouble();
      double avgAssetValue = loanApplication.getBalanceSheet().getSheet().parallelStream()
          .mapToDouble(monthlyBalance -> monthlyBalance.getAssetsValue().doubleValue()).average()
          .getAsDouble();
      if (avgProfit > 0) {
        loanProcessResponse.setPreAssessment(60);
        if (avgAssetValue > loanApplication.getLoanAmount().doubleValue()) {
          loanProcessResponse.setPreAssessment(100);
        }
      }

    }
    return loanProcessResponse;
  }
}
