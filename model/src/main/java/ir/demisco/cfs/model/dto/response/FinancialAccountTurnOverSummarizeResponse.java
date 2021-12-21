package ir.demisco.cfs.model.dto.response;

public class FinancialAccountTurnOverSummarizeResponse {
    private Double sumDebit;
    private Double sumCredit;
    private Double summarizeDebit;
    private Double summarizeCredit;
    private Double summarizeAmount;
    private Long recordType;

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

    public Double getSummarizeDebit() {
        return summarizeDebit;
    }

    public void setSummarizeDebit(Double summarizeDebit) {
        this.summarizeDebit = summarizeDebit;
    }

    public Double getSummarizeCredit() {
        return summarizeCredit;
    }

    public void setSummarizeCredit(Double summarizeCredit) {
        this.summarizeCredit = summarizeCredit;
    }

    public Double getSummarizeAmount() {
        return summarizeAmount;
    }

    public void setSummarizeAmount(Double summarizeAmount) {
        this.summarizeAmount = summarizeAmount;
    }

    public Long getRecordType() {
        return recordType;
    }

    public void setRecordType(Long recordType) {
        this.recordType = recordType;
    }
    public static FinancialAccountTurnOverSummarizeResponse.Builder builder() {
        return new FinancialAccountTurnOverSummarizeResponse.Builder();
    }
    public static final class Builder {
        private FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeResponse;

        private Builder() {
            financialAccountTurnOverSummarizeResponse = new FinancialAccountTurnOverSummarizeResponse();
        }

        public static Builder financialAccountTurnOverSummarizeResponse() {
            return new Builder();
        }

        public Builder sumDebit(Double sumDebit) {
            financialAccountTurnOverSummarizeResponse.setSumDebit(sumDebit);
            return this;
        }

        public Builder sumCredit(Double sumCredit) {
            financialAccountTurnOverSummarizeResponse.setSumCredit(sumCredit);
            return this;
        }

        public Builder summarizeDebit(Double summarizeDebit) {
            financialAccountTurnOverSummarizeResponse.setSummarizeDebit(summarizeDebit);
            return this;
        }

        public Builder summarizeCredit(Double summarizeCredit) {
            financialAccountTurnOverSummarizeResponse.setSummarizeCredit(summarizeCredit);
            return this;
        }

        public Builder summarizeAmount(Double summarizeAmount) {
            financialAccountTurnOverSummarizeResponse.setSummarizeAmount(summarizeAmount);
            return this;
        }

        public Builder recordType(Long recordType) {
            financialAccountTurnOverSummarizeResponse.setRecordType(recordType);
            return this;
        }

        public FinancialAccountTurnOverSummarizeResponse build() {
            return financialAccountTurnOverSummarizeResponse;
        }
    }
}
