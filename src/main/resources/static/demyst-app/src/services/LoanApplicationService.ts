import axios from 'axios';
import LoanApplicationData from "../data/LoanApplicationData";

class LoanApplicationService {
  processLoanApplication(loanApplicationObj: LoanApplicationData) {
    console.log(JSON.stringify(loanApplicationObj));
    return axios.post('http://localhost:8080/api/v1/accounting-provider/process-loan-app', loanApplicationObj);
  }

  fetchBalanceSheet(provider: string) {
    return axios.get('http://localhost:8080/api/v1/accounting-provider/'+provider);
  }
}

export default new LoanApplicationService;