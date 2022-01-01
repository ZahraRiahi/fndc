package ir.demisco.cfs.model.dto.response;

public class ControlFinancialAccountNatureTypeByAccountResponse {
    private Double sumDebit;
    private Double sumCredit;
    private Long accountNatureTypeId;

    public Double getSumDebit() {
        return sumDebit;
    }

    public void setSumDebit(Double sumDebit) {
        this.sumDebit = sumDebit;
    }

    public Double getSumCredit() {
        return sumCredit;
    }

    public void setSumCredit(Double sumCredit) {
        this.sumCredit = sumCredit;
    }

    public Long getAccountNatureTypeId() {
        return accountNatureTypeId;
    }

    public void setAccountNatureTypeId(Long accountNatureTypeId) {
        this.accountNatureTypeId = accountNatureTypeId;
    }
    public static ControlFinancialAccountNatureTypeByAccountResponse.Builder builder() {
        return new ControlFinancialAccountNatureTypeByAccountResponse.Builder();
    }
    public static final class Builder {
        private ControlFinancialAccountNatureTypeByAccountResponse controlFinancialAccountNatureTypeByAccountResponse;

        private Builder() {
            controlFinancialAccountNatureTypeByAccountResponse = new ControlFinancialAccountNatureTypeByAccountResponse();
        }

        public static Builder controlFinancialAccountNatureTypeByAccountResponse() {
            return new Builder();
        }

        public Builder sumDebit(Double sumDebit) {
            controlFinancialAccountNatureTypeByAccountResponse.setSumDebit(sumDebit);
            return this;
        }

        public Builder sumCredit(Double sumCredit) {
            controlFinancialAccountNatureTypeByAccountResponse.setSumCredit(sumCredit);
            return this;
        }

        public Builder accountNatureTypeId(Long accountNatureTypeId) {
            controlFinancialAccountNatureTypeByAccountResponse.setAccountNatureTypeId(accountNatureTypeId);
            return this;
        }

        public ControlFinancialAccountNatureTypeByAccountResponse build() {
            return controlFinancialAccountNatureTypeByAccountResponse;
        }
    }
}
