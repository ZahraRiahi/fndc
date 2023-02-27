package ir.demisco.cfs.model.dto.request;

public class FinancialBalanceReportDriverRequest {
    private FinancialAccountBalanceRequest financialAccountBalanceRequest;
    private ReportDriverRequest reportDriverRequest;

    public FinancialAccountBalanceRequest getFinancialAccountBalanceRequest() {
        return financialAccountBalanceRequest;
    }

    public void setFinancialAccountBalanceRequest(FinancialAccountBalanceRequest financialAccountBalanceRequest) {
        this.financialAccountBalanceRequest = financialAccountBalanceRequest;
    }

    public ReportDriverRequest getReportDriverRequest() {
        return reportDriverRequest;
    }

    public void setReportDriverRequest(ReportDriverRequest reportDriverRequest) {
        this.reportDriverRequest = reportDriverRequest;
    }
}
