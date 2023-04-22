import LoanApplicationService from './services/LoanApplicationService';
import {toast, ToastContainer} from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";

function LoanApplication() {
  const handleSubmit = (e: any) => {
    e.preventDefault();
    const target = e.target;
    console.log(target.accountingProvider.value);
    const loanApplicationRequest = {
      businessName: target.businessName.value,
      loanAmount: target.loanAmount.value,
      accountingProvider: target.accountingProvider.value
    };
    LoanApplicationService.processLoanApplication(loanApplicationRequest)
    .then(response => {
      console.log(JSON.stringify(response));
      if (response && response.data.preAssessment) {
        console.log("Here");
        let approvedAmount = target.loanAmount.value * (response.data.preAssessment / 100);
        toast.info(response.data.preAssessment + "% of applied loan amount! \n Approved amount:" + approvedAmount, {
          position: toast.POSITION.TOP_CENTER,
          theme: "colored"
        });
      }
    }).catch(reason => {
      console.error('Error occurred while processing loan: ' + reason);
    });
  }

  return (
      <form onSubmit={handleSubmit}>
        <div className="row">
          <h1>Welcome to Loan Application</h1>
          <div className="row">
            <label htmlFor="businessName" className="form-label">Business Name</label>
            <input id="businessName" className="form-control" aria-label="Business Name" required
                   placeholder="Enter Business Name"/>
          </div>
          <div className="row">
            <label htmlFor="loanAmount" className="form-label">Loan Amount</label>
            <input id="loanAmount" className="form-control" aria-label="Loan Amount" required
                   placeholder="Enter Loan Amount"/>
          </div>
          <div className="row">
            <label htmlFor="accountingProvider" className="form-label">Loan Amount</label>
            <select id="accountingProvider" className="form-select" aria-label="Accounting Provider">
              <option defaultValue="-1">Choose Accounting Provider</option>
              <option value="XERO">Xero</option>
              <option value="MYOB">MYOB</option>
            </select>
          </div>
          <div className="row">
            <button
                type="submit"
                className="btn btn-primary"
                value="Apply for Approval">
              Apply for Approval
            </button>
          </div>
        </div>
        <ToastContainer />
      </form>
  );
}

export default LoanApplication;