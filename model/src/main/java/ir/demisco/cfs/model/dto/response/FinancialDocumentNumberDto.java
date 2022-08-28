package ir.demisco.cfs.model.dto.response;

import java.util.List;

public class FinancialDocumentNumberDto {

    private Long financialDocumentId;
    private Long numberingType;
    private Long organizationId;
    private List<Long> financialDocumentIdList;

    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    public Long getNumberingType() {
        return numberingType;
    }

    public void setNumberingType(Long numberingType) {
        this.numberingType = numberingType;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public List<Long> getFinancialDocumentIdList() {
        return financialDocumentIdList;
    }

    public void setFinancialDocumentIdList(List<Long> financialDocumentIdList) {
        this.financialDocumentIdList = financialDocumentIdList;
    }
}
