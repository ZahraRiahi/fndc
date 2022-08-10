package ir.demisco.cfs.model.dto.response;

public class FinancialAccountTurnOverSummarizeResponse {
    private Long sumDebit;
    private Long sumCredit;
    private String summarizeDebit;
    private String summarizeCredit;
    private String summarizeAmount;
    private String remainDebit;
    private String remainCredit;
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

    public String getSummarizeDebit() {
        return summarizeDebit;
    }

    public void setSummarizeDebit(String summarizeDebit) {
        this.summarizeDebit = summarizeDebit;
    }

    public String getSummarizeCredit() {
        return summarizeCredit;
    }

    public void setSummarizeCredit(String summarizeCredit) {
        this.summarizeCredit = summarizeCredit;
    }

    public String getSummarizeAmount() {
        return summarizeAmount;
    }

    public void setSummarizeAmount(String summarizeAmount) {
        this.summarizeAmount = summarizeAmount;
    }

    public Long getRecordType() {
        return recordType;
    }

    public String getRemainDebit() {
        return remainDebit;
    }

    public void setRemainDebit(String remainDebit) {
        this.remainDebit = remainDebit;
    }

    public String getRemainCredit() {
        return remainCredit;
    }

    public void setRemainCredit(String remainCredit) {
        this.remainCredit = remainCredit;
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

        public Builder summarizeDebit(String summarizeDebit) {
            financialAccountTurnOverSummarizeResponse.setSummarizeDebit(summarizeDebit);
            return this;
        }

        public Builder summarizeCredit(String summarizeCredit) {
            financialAccountTurnOverSummarizeResponse.setSummarizeCredit(summarizeCredit);
            return this;
        }

        public Builder summarizeAmount(String summarizeAmount) {
            financialAccountTurnOverSummarizeResponse.setSummarizeAmount(summarizeAmount);
            return this;
        }

        public Builder recordType(Long recordType) {
            financialAccountTurnOverSummarizeResponse.setRecordType(recordType);
            return this;
        }
        public Builder remainDebit(String remainDebit) {
            financialAccountTurnOverSummarizeResponse.setRemainDebit(remainDebit);
            return this;
        }
        public Builder remainCredit(String remainCredit) {
            financialAccountTurnOverSummarizeResponse.setRemainCredit(remainCredit);
            return this;
        }
        public FinancialAccountTurnOverSummarizeResponse build() {
            return financialAccountTurnOverSummarizeResponse;
        }
    }
}
