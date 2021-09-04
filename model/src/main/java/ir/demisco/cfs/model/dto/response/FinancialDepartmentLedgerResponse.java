package ir.demisco.cfs.model.dto.response;

public class FinancialDepartmentLedgerResponse {
    private Long financialLedgerTypeId;
    private String description;

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

    public static FinancialDepartmentLedgerResponse.Builder builder() {
        return new FinancialDepartmentLedgerResponse.Builder();
    }

    public static final class Builder {
        private FinancialDepartmentLedgerResponse financialDepartmentLedgerResponse;

        private Builder() {
            financialDepartmentLedgerResponse = new FinancialDepartmentLedgerResponse();
        }

        public static Builder aFinancialDepartmentLedgerResponse() {
            return new Builder();
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialDepartmentLedgerResponse.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder description(String description) {
            financialDepartmentLedgerResponse.setDescription(description);
            return this;
        }

        public FinancialDepartmentLedgerResponse build() {
            return financialDepartmentLedgerResponse;
        }
    }
}
