package ir.demisco.cfs.model.dto.response;

public class FinancialPeriodLedgerGetResponse {
    private Long financialLedgerPeriodId;
    private Long financialLedgerTypeId;
    private String financialLedgerTypeDescription;

    public Long getFinancialLedgerPeriodId() {
        return financialLedgerPeriodId;
    }

    public void setFinancialLedgerPeriodId(Long financialLedgerPeriodId) {
        this.financialLedgerPeriodId = financialLedgerPeriodId;
    }

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

    public static FinancialPeriodLedgerGetResponse.Builder builder() {
        return new FinancialPeriodLedgerGetResponse.Builder();
    }
    public static final class Builder {
        private FinancialPeriodLedgerGetResponse financialPeriodLedgerGetResponse;

        private Builder() {
            financialPeriodLedgerGetResponse = new FinancialPeriodLedgerGetResponse();
        }

        public static Builder financialPeriodLedgerGetResponse() {
            return new Builder();
        }

        public Builder financialLedgerPeriodId(Long financialLedgerPeriodId) {
            financialPeriodLedgerGetResponse.setFinancialLedgerPeriodId(financialLedgerPeriodId);
            return this;
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialPeriodLedgerGetResponse.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder financialLedgerTypeDescription(String financialLedgerTypeDescription) {
            financialPeriodLedgerGetResponse.setFinancialLedgerTypeDescription(financialLedgerTypeDescription);
            return this;
        }

        public FinancialPeriodLedgerGetResponse build() {
            return financialPeriodLedgerGetResponse;
        }
    }
}
