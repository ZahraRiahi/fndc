package ir.demisco.cfs.model.dto.request;

public class FinancialDocumentReportDriverRequest {
    private FinancialDocumentReportRequest financialDocumentReportRequest;
    private ReportDriverRequest reportDriverRequest;

    public FinancialDocumentReportRequest getFinancialDocumentReportRequest() {
        return financialDocumentReportRequest;
    }

    public void setFinancialDocumentReportRequest(FinancialDocumentReportRequest financialDocumentReportRequest) {
        this.financialDocumentReportRequest = financialDocumentReportRequest;
    }

    public ReportDriverRequest getReportDriverRequest() {
        return reportDriverRequest;
    }

    public void setReportDriverRequest(ReportDriverRequest reportDriverRequest) {
        this.reportDriverRequest = reportDriverRequest;
    }

}