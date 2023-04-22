package com.nisarg.demyst.bean;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {

  private String businessName;
  private BigDecimal loanAmount;
  private AccountingProvider accountingProvider;
  private BalanceSheet balanceSheet;
}
