package com.nisarg.demyst.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.nisarg.demyst.bean.AccountingProvider;
import com.nisarg.demyst.bean.BalanceSheet;
import com.nisarg.demyst.bean.LoanApplication;
import com.nisarg.demyst.bean.LoanProcessResponse;
import com.nisarg.demyst.bean.MonthlyBalance;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LoanProcessorTest {

  @Autowired
  private LoanProcessorImpl loanProcessor;

  @MockBean
  private AccountProviderService accountProviderService;

  public BalanceSheet prepareBalanceSheetWithLosses() {
    String[] losses = {"10000", "-10000", "20000", "-25000", "5000", "-10000", "5000", "2500",
        "-2500", "4000", "-40000", "30000"};
    String[] assetValues = {"10000", "-10000", "20000", "-25000", "5000", "-10000", "5000", "2500",
        "-2500", "4000", "-40000", "30000"};
    BalanceSheet balanceSheetInLoss = new BalanceSheet(new LinkedList<>());
    for (int i = 0; i < 12; i++) {
      MonthlyBalance monthlyBalance = new MonthlyBalance(2022, i + 1, new BigDecimal(losses[i]),
          new BigDecimal(assetValues[i]));
      balanceSheetInLoss.getSheet().add(monthlyBalance);
    }
    return balanceSheetInLoss;
  }

  public BalanceSheet prepareBalanceSheetWithProfit() {
    String[] profitLoss = {"10000", "10000", "20000", "25000", "5000", "10000", "5000", "2500",
        "-2500", "4000", "4000", "30000"};
    String[] assetValues = {"10000", "10000", "20000", "25000", "5000", "10000", "5000", "2500",
        "2500", "4000", "40000", "30000"};
    BalanceSheet balanceSheetInLoss = new BalanceSheet(new LinkedList<>());
    for (int i = 0; i < 12; i++) {
      MonthlyBalance monthlyBalance = new MonthlyBalance(2022, i + 1, new BigDecimal(profitLoss[i]),
          new BigDecimal(assetValues[i]));
      balanceSheetInLoss.getSheet().add(monthlyBalance);
    }
    return balanceSheetInLoss;
  }

  @BeforeEach
  void setUp() {
    prepareBalanceSheetWithLosses();
  }

  @Test
  void processLoanWhileInLosses() throws IOException {
    BalanceSheet balanceSheet = prepareBalanceSheetWithLosses();
    when(accountProviderService.getBalanceSheetData("XERO")).thenReturn(balanceSheet.getSheet());
    assertThat(loanProcessor.processLoan(
        LoanApplication
            .builder()
            .loanAmount(new BigDecimal("50000"))
            .accountingProvider(AccountingProvider.XERO)
            .build())).isEqualTo(LoanProcessResponse.builder().preAssessment(20).build());
  }

  @Test
  void processLoanWhileInProfit() throws IOException {
    BalanceSheet balanceSheet = prepareBalanceSheetWithProfit();
    when(accountProviderService.getBalanceSheetData("XERO")).thenReturn(balanceSheet.getSheet());
    assertThat(loanProcessor.processLoan(
        LoanApplication
            .builder()
            .loanAmount(new BigDecimal("50000"))
            .accountingProvider(AccountingProvider.XERO)
            .build())).isEqualTo(LoanProcessResponse.builder().preAssessment(60).build());
  }

  @Test
  void processLoanWhileInProfitWhileLoanAmountIsSameOrLessThanAssetValue() throws IOException {
    BalanceSheet balanceSheet = prepareBalanceSheetWithProfit();
    when(accountProviderService.getBalanceSheetData("XERO")).thenReturn(balanceSheet.getSheet());
    assertThat(loanProcessor.processLoan(
        LoanApplication
            .builder()
            .loanAmount(new BigDecimal("5000"))
            .accountingProvider(AccountingProvider.XERO)
            .build())).isEqualTo(LoanProcessResponse.builder().preAssessment(100).build());
  }
}