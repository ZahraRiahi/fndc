package ir.demisco.cfs.model.dto.response;

import java.util.Date;
import java.util.List;

public class FinancialDocumentTransferDto {

    private Long id;
    private List<Long> financialDocumentItemIdList;
    private int transferType;
    private Date date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
