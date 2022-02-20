package ir.demisco.cfs.model.dto.response;

public class FinancialLedgerTypeListResponse {
    private Long financialLedgerTypeId;
    private String description;
    private Boolean activeFlag;
    private Long financialCodingTypeId;
    private String financialNumberingTypeDescription;
    private String financialCodingTypeDescription;

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
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
        private FinancialLedgerTypeListResponse financialLedgerTypeListResponse;

        private Builder() {
            financialLedgerTypeListResponse = new FinancialLedgerTypeListResponse();
        }

        public static Builder aFinancialLedgerTypeResponse() {
            return new Builder();
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialLedgerTypeListResponse.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder description(String description) {
            financialLedgerTypeListResponse.setDescription(description);
            return this;
        }

        public Builder activeFlag(Boolean activeFlag) {
            financialLedgerTypeListResponse.setActiveFlag(activeFlag);
            return this;
        }

        public Builder financialCodingTypeId(Long financialCodingTypeId) {
            financialLedgerTypeListResponse.setFinancialCodingTypeId(financialCodingTypeId);
            return this;
        }

        public Builder financialNumberingTypeDescription(String financialNumberingTypeDescription) {
            financialLedgerTypeListResponse.setFinancialNumberingTypeDescription(financialNumberingTypeDescription);
            return this;
        }

        public Builder financialCodingTypeDescription(String financialCodingTypeDescription) {
            financialLedgerTypeListResponse.setFinancialCodingTypeDescription(financialCodingTypeDescription);
            return this;
        }

        public FinancialLedgerTypeListResponse build() {
            return financialLedgerTypeListResponse;
        }
    }

}
