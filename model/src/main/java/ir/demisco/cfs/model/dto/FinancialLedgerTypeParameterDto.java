package ir.demisco.cfs.model.dto;


import ir.demisco.cfs.model.dto.request.FinancialSecurityFilterRequest;

import java.util.Map;

public class FinancialLedgerTypeParameterDto extends FinancialSecurityFilterRequest {
    private Long financialLedgerTypeId;
    private String description;
    private String financialLedgerType;
    private boolean activeFlag;
    private Long financialCodingTypeId;
    private String financialCodingType;
    private String financialNumberingTypeDescription;
    private String financialCodingTypeDescription;
    Map<String, Object> paramMap;

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public String getFinancialLedgerType() {
        return financialLedgerType;
    }

    public void setFinancialLedgerType(String financialLedgerType) {
        this.financialLedgerType = financialLedgerType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getFinancialCodingTypeId() {
        return financialCodingTypeId;
    }

    public void setFinancialCodingTypeId(Long financialCodingTypeId) {
        this.financialCodingTypeId = financialCodingTypeId;
    }

    public String getFinancialNumberingTypeDescription() {
        return financialNumberingTypeDescription;
    }

    public void setFinancialNumberingTypeDescription(String financialNumberingTypeDescription) {
        this.financialNumberingTypeDescription = financialNumberingTypeDescription;
    }

    public String getFinancialCodingTypeDescription() {
        return financialCodingTypeDescription;
    }

    public void setFinancialCodingTypeDescription(String financialCodingTypeDescription) {
        this.financialCodingTypeDescription = financialCodingTypeDescription;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public String getFinancialCodingType() {
        return financialCodingType;
    }

    public void setFinancialCodingType(String financialCodingType) {
        this.financialCodingType = financialCodingType;
    }
}