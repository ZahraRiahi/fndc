package ir.demisco.cfs.model.dto.response;

public class FinancialAccountTurnOverSummarizeResponse {
    private Long sumDebit;
    private Long sumCredit;
    private Long summarizeDebit;
    private Long summarizeCredit;
    private Long summarizeAmount;
    private Long recordType;

    public Long getSumDebit() {
        return sumDebit;
    }

    public void setSumDebit(Long sumDebit) {
        this.sumDebit = sumDebit;
    }

    public Long getSumCredit() {
        return sumCredit;
    }

    public void setSumCredit(Long sumCredit) {
        this.sumCredit = sumCredit;
    }

    public Long getSummarizeDebit() {
        return summarizeDebit;
    }

    public void setSummarizeDebit(Long summarizeDebit) {
        this.summarizeDebit = summarizeDebit;
    }

    public Long getSummarizeCredit() {
        return summarizeCredit;
    }

    public void setSummarizeCredit(Long summarizeCredit) {
        this.summarizeCredit = summarizeCredit;
    }

    public Long getSummarizeAmount() {
        return summarizeAmount;
    }

    public void setSummarizeAmount(Long summarizeAmount) {
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

        public Builder sumDebit(Long sumDebit) {
            financialAccountTurnOverSummarizeResponse.setSumDebit(sumDebit);
            return this;
        }

        public Builder sumCredit(Long sumCredit) {
            financialAccountTurnOverSummarizeResponse.setSumCredit(sumCredit);
            return this;
        }

        public Builder summarizeDebit(Long summarizeDebit) {
            financialAccountTurnOverSummarizeResponse.setSummarizeDebit(summarizeDebit);
            return this;
        }

        public Builder summarizeCredit(Long summarizeCredit) {
            financialAccountTurnOverSummarizeResponse.setSummarizeCredit(summarizeCredit);
            return this;
        }

        public Builder summarizeAmount(Long summarizeAmount) {
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
