package com.nisarg.demyst.bean;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanProcessResponse {
  private String businessName;
  private BigDecimal averageProfit;
  private int preAssessment;
}
