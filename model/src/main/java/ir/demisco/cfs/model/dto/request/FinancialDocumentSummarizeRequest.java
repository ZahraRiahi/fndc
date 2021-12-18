package ir.demisco.cfs.model.dto.request;

import java.util.List;

public class FinancialDocumentSummarizeRequest {
    private Long financialDocumentId;
    private List<Long> financialDocumentItems;

    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    public List<Long> getFinancialDocumentItems() {
        return financialDocumentItems;
    }

    public void setFinancialDocumentItems(List<Long> financialDocumentItems) {
        this.financialDocumentItems = financialDocumentItems;
    }
}
