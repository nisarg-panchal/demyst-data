package com.nisarg.demyst.service;

import com.nisarg.demyst.bean.LoanApplication;
import com.nisarg.demyst.bean.LoanProcessResponse;
import java.io.IOException;

public interface LoanProcessor {
  LoanProcessResponse processLoan(LoanApplication loanApplication) throws IOException;
}
