package ir.demisco.cfs.model.dto.response;


public class FinancialDepartmentResponse {
    private Long departmentId;
    private String code;
    private String name;
    private Long financialLedgerTypeId;
    private String ledgerTypeDescription;
    private Long financialDepartmentLedgerId;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public String getLedgerTypeDescription() {
        return ledgerTypeDescription;
    }

    public void setLedgerTypeDescription(String ledgerTypeDescription) {
        this.ledgerTypeDescription = ledgerTypeDescription;
    }

    public Long getFinancialDepartmentLedgerId() {
        return financialDepartmentLedgerId;
    }

    public void setFinancialDepartmentLedgerId(Long financialDepartmentLedgerId) {
        this.financialDepartmentLedgerId = financialDepartmentLedgerId;
    }
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private FinancialDepartmentResponse financialDepartmentResponse;

        private Builder() {
            financialDepartmentResponse = new FinancialDepartmentResponse();
        }

        public static Builder financialDepartmentResponse() {
            return new Builder();
        }

        public Builder departmentId(Long departmentId) {
            financialDepartmentResponse.setDepartmentId(departmentId);
            return this;
        }

        public Builder name(String name) {
            financialDepartmentResponse.setName(name);
            return this;
        }

        public Builder code(String code) {
            financialDepartmentResponse.setCode(code);
            return this;
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialDepartmentResponse.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder ledgerTypeDescription(String ledgerTypeDescription) {
            financialDepartmentResponse.setLedgerTypeDescription(ledgerTypeDescription);
            return this;
        }

        public Builder financialDepartmentLedgerId(Long financialDepartmentLedgerId) {
            financialDepartmentResponse.setFinancialDepartmentLedgerId(financialDepartmentLedgerId);
            return this;
        }

        public FinancialDepartmentResponse build() {
            return financialDepartmentResponse;
        }
    }
}
