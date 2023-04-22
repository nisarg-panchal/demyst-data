package com.nisarg.demyst.bean;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MonthlyBalance {
  private int year;
  private int month;
  private BigDecimal profitOrLoss;
  private BigDecimal assetsValue;

}
