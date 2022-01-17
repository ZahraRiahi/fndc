package ir.demisco.cfs.model.dto.request;

public class ControlFinancialAccountNatureTypeRequest {
    private Long financialDocumentId;
    private Long financialDocumentItemId;
    private Long financialAccountId;

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

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }
}
