package ir.demisco.cfs.model.dto.request;

import java.util.Map;

public class FinancialDocumentTypeRequest {
    private Long organizationId;
    private Long financialSystemId;
    private Long searchStatusFlag;
    private SecurityModelRequest securityModelRequest;
    private Long id;
    Map<String, Object> paramMap;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
