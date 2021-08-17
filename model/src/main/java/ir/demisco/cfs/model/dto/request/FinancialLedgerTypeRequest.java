package ir.demisco.cfs.model.dto.request;

import java.util.List;

public class FinancialLedgerTypeRequest {
    private Long financialLedgerTypeId;
    private String description;
    private Long financialCodingTypeId;
    private Long organizationId;
    private Boolean activeFlag;
    private  List<Long> numberingTypeIdList;

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFinancialCodingTypeId() {
        return financialCodingTypeId;
    }

    public void setFinancialCodingTypeId(Long financialCodingTypeId) {
        this.financialCodingTypeId = financialCodingTypeId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public List<Long> getNumberingTypeIdList() {
        return numberingTypeIdList;
    }

    public void setNumberingTypeIdList(List<Long> numberingTypeIdList) {
        this.numberingTypeIdList = numberingTypeIdList;
    }
}
