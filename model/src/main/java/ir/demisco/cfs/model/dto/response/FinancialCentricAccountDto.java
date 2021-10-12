package ir.demisco.cfs.model.dto.response;

import java.util.List;

public class FinancialCentricAccountDto {

    private Long id;
    private Long accountId;
    private List<Long> financialDocumentItemIdList;
    private Long centricAccountId;
    private Long newCentricAccountId;
    private Long financialAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<Long> getFinancialDocumentItemIdList() {
        return financialDocumentItemIdList;
    }

    public void setFinancialDocumentItemIdList(List<Long> financialDocumentItemIdList) {
        this.financialDocumentItemIdList = financialDocumentItemIdList;
    }

    public Long getCentricAccountId() {
        return centricAccountId;
    }

    public void setCentricAccountId(Long centricAccountId) {
        this.centricAccountId = centricAccountId;
    }

    public Long getNewCentricAccountId() {
        return newCentricAccountId;
    }

    public void setNewCentricAccountId(Long newCentricAccountId) {
        this.newCentricAccountId = newCentricAccountId;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }
}
