package ir.demisco.cfs.model.dto.request;

public class FinancialDocumentTypeRequest {
    private Long organizationId;
    private Long financialSystemId;
    private Long searchStatusFlag;
    private SecurityModelRequest securityModelRequest;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getFinancialSystemId() {
        return financialSystemId;
    }

    public void setFinancialSystemId(Long financialSystemId) {
        this.financialSystemId = financialSystemId;
    }

    public Long getSearchStatusFlag() {
        return searchStatusFlag;
    }

    public void setSearchStatusFlag(Long searchStatusFlag) {
        this.searchStatusFlag = searchStatusFlag;
    }

    public SecurityModelRequest getSecurityModelRequest() {
        return securityModelRequest;
    }

    public void setSecurityModelRequest(SecurityModelRequest securityModelRequest) {
        this.securityModelRequest = securityModelRequest;
    }
}
