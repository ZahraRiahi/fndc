package ir.demisco.cfs.model.dto.response;

import java.util.List;

public class FinancialDocumentAccountDto {

    private Long id;
    private List<Long> financialDocumentItemIdList;
    private Long  financialAccountId;
    private Long newFinancialAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getFinancialDocumentItemIdList() {
        return financialDocumentItemIdList;
    }

    public void setFinancialDocumentItemIdList(List<Long> financialDocumentItemIdList) {
        this.financialDocumentItemIdList = financialDocumentItemIdList;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    public Long getNewFinancialAccountId() {
        return newFinancialAccountId;
    }

    public void setNewFinancialAccountId(Long newFinancialAccountId) {
        this.newFinancialAccountId = newFinancialAccountId;
    }
}
