package ir.demisco.cfs.model.dto.response;


public class FinancialLedgerTypeResponse {
    private Long financialLedgerTypeId;
    private String description;
    private Boolean activeFlag;
    private Long financialCodingTypeId;
    private String financialNumberingTypeDescription;
    private String financialCodingTypeDescription;
    private String financialLedgerType;
    private String code;

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
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

    public boolean activeFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
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
    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private FinancialLedgerTypeResponse financialLedgerTypeResponse;

        private Builder() {
            financialLedgerTypeResponse = new FinancialLedgerTypeResponse();
        }

        public static Builder aFinancialLedgerTypeResponse() {
            return new Builder();
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialLedgerTypeResponse.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder description(String description) {
            financialLedgerTypeResponse.setDescription(description);
            return this;
        }
        public Builder code(String code) {
            financialLedgerTypeResponse.setCode(code);
            return this;
        }

        public Builder activeFlag(Boolean activeFlag) {
            financialLedgerTypeResponse.setActiveFlag(activeFlag);
            return this;
        }

        public Builder financialCodingTypeId(Long financialCodingTypeId) {
            financialLedgerTypeResponse.setFinancialCodingTypeId(financialCodingTypeId);
            return this;
        }

        public Builder financialNumberingTypeDescription(String financialNumberingTypeDescription) {
            financialLedgerTypeResponse.setFinancialNumberingTypeDescription(financialNumberingTypeDescription);
            return this;
        }

        public Builder financialCodingTypeDescription(String financialCodingTypeDescription) {
            financialLedgerTypeResponse.setFinancialCodingTypeDescription(financialCodingTypeDescription);
            return this;
        }

        public Builder financialLedgerType(String financialLedgerType) {
            financialLedgerTypeResponse.setFinancialLedgerType(financialLedgerType);
            return this;
        }

        public FinancialLedgerTypeResponse build() {
            return financialLedgerTypeResponse;
        }
    }
}
