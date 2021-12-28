package ir.demisco.cfs.model.dto.request;

public class FinancialDocumentItemRequest {
    private Long financialDocumentId;
    private Long financialDocumentItemId;
    private Object FinancialDocumentItem;

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

    public Object getFinancialDocumentItem() {
        return FinancialDocumentItem;
    }

    public void setFinancialDocumentItem(Object financialDocumentItem) {
        FinancialDocumentItem = financialDocumentItem;
    }
}
