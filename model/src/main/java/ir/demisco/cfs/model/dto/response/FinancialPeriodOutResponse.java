package ir.demisco.cfs.model.dto.response;

public class FinancialPeriodOutResponse {
    private Long financialLedgerTypeId;
    private String financialLedgerTypeDescription;

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public String getFinancialLedgerTypeDescription() {
        return financialLedgerTypeDescription;
    }

    public void setFinancialLedgerTypeDescription(String financialLedgerTypeDescription) {
        this.financialLedgerTypeDescription = financialLedgerTypeDescription;
    }

    public static FinancialPeriodOutResponse.Builder builder() {
        return new FinancialPeriodOutResponse.Builder();
    }

    public static final class Builder {
        private FinancialPeriodOutResponse financialPeriodOutResponse;

        private Builder() {
            financialPeriodOutResponse = new FinancialPeriodOutResponse();
        }

        public static Builder financialPeriodOutResponse() {
            return new Builder();
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialPeriodOutResponse.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder financialLedgerTypeDescription(String financialLedgerTypeDescription) {
            financialPeriodOutResponse.setFinancialLedgerTypeDescription(financialLedgerTypeDescription);
            return this;
        }

        public FinancialPeriodOutResponse build() {
            return financialPeriodOutResponse;
        }
    }
}

