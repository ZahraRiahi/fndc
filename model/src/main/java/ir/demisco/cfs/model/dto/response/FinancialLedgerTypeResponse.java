package ir.demisco.cfs.model.dto.response;


public class FinancialLedgerTypeResponse {
    private Long id;
    private String description;
    private Boolean activeFlag;
    private Long financialCodingTypeId;
    private String financialNumberingTypeDescription;
    private String financialCodingTypeDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private FinancialLedgerTypeResponse financialLedgerTypeResponse;

        private Builder() {
            financialLedgerTypeResponse = new FinancialLedgerTypeResponse();
        }

        public static Builder financialLedgerTypeResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialLedgerTypeResponse.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialLedgerTypeResponse.setDescription(description);
            return this;
        }

        public Builder activeFlag(boolean activeFlag) {
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

        public FinancialLedgerTypeResponse build() {
            return financialLedgerTypeResponse;
        }
    }
}
