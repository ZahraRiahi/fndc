package ir.demisco.cfs.model.dto.request;

public class FinancialDocumentSecurityInputRequest {
    private Long financialDocumentId;
    private Long financialDocumentItemId;
    private String activityCode;
    private SecurityModelRequest securityModelRequest;

    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    public Long getFinancialDocumentItemId() {
        return financialDocumentItemId;
    }

    public void setFinancialDocumentItemId(Long financialDocumentItemId) {
        this.financialDocumentItemId = financialDocumentItemId;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public SecurityModelRequest getSecurityModelRequest() {
        return securityModelRequest;
    }

    public void setSecurityModelRequest(SecurityModelRequest securityModelRequest) {
        this.securityModelRequest = securityModelRequest;
    }
}
