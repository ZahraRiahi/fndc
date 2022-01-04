package ir.demisco.cfs.model.dto.request;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class FinancialDocumentTransferRequest {
    private Long id;
    private List<Long> financialDocumentItemIdList;
    private int transferType;
    private LocalDateTime date;
    private String targetDocumentNumber;
    private Boolean allItemFlag;
    private Long organizationId;

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

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTargetDocumentNumber() {
        return targetDocumentNumber;
    }

    public void setTargetDocumentNumber(String targetDocumentNumber) {
        this.targetDocumentNumber = targetDocumentNumber;
    }

    public Boolean getAllItemFlag() {
        return allItemFlag;
    }

    public void setAllItemFlag(Boolean allItemFlag) {
        this.allItemFlag = allItemFlag;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

}
