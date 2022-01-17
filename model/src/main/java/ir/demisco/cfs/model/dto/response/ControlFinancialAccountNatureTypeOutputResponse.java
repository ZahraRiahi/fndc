package ir.demisco.cfs.model.dto.response;

public class ControlFinancialAccountNatureTypeOutputResponse {
    private Double sumDebit;
    private Double sumCredit;
    private Long accountNatureTypeId;
    private String financialAccountDescription;
    private String resultMessage;

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

    public String getFinancialAccountDescription() {
        return financialAccountDescription;
    }

    public void setFinancialAccountDescription(String financialAccountDescription) {
        this.financialAccountDescription = financialAccountDescription;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public static ControlFinancialAccountNatureTypeOutputResponse.Builder builder() {
        return new ControlFinancialAccountNatureTypeOutputResponse.Builder();
    }

    public static final class Builder {
        private ControlFinancialAccountNatureTypeOutputResponse controlFinancialAccountNatureTypeOutputResponse;

        private Builder() {
            controlFinancialAccountNatureTypeOutputResponse = new ControlFinancialAccountNatureTypeOutputResponse();
        }

        public static Builder controlFinancialAccountNatureTypeOutputResponse() {
            return new Builder();
        }

        public Builder sumDebit(Double sumDebit) {
            controlFinancialAccountNatureTypeOutputResponse.setSumDebit(sumDebit);
            return this;
        }

        public Builder sumCredit(Double sumCredit) {
            controlFinancialAccountNatureTypeOutputResponse.setSumCredit(sumCredit);
            return this;
        }

        public Builder accountNatureTypeId(Long accountNatureTypeId) {
            controlFinancialAccountNatureTypeOutputResponse.setAccountNatureTypeId(accountNatureTypeId);
            return this;
        }

        public Builder financialAccountDescription(String financialAccountDescription) {
            controlFinancialAccountNatureTypeOutputResponse.setFinancialAccountDescription(financialAccountDescription);
            return this;
        }

        public Builder resultMessage(String resultMessage) {
            controlFinancialAccountNatureTypeOutputResponse.setResultMessage(resultMessage);
            return this;
        }

        public ControlFinancialAccountNatureTypeOutputResponse build() {
            return controlFinancialAccountNatureTypeOutputResponse;
        }
    }
}
