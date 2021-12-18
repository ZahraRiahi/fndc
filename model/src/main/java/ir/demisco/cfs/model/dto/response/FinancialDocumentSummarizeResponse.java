package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentSummarizeResponse {
    private Long recordsCount;
    private Long sumDebitAmount;
    private Long sumCreditAmount;
    private Long remainAmount;
    private Long selectedRecordsCount;
    private Long selectedSumDebitAmount;
    private Long selectedSumCreditAmount;
    private Long selectedRemainAmount;

    public Long getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(Long recordsCount) {
        this.recordsCount = recordsCount;
    }

    public Long getSumDebitAmount() {
        return sumDebitAmount;
    }

    public void setSumDebitAmount(Long sumDebitAmount) {
        this.sumDebitAmount = sumDebitAmount;
    }

    public Long getSumCreditAmount() {
        return sumCreditAmount;
    }

    public void setSumCreditAmount(Long sumCreditAmount) {
        this.sumCreditAmount = sumCreditAmount;
    }

    public Long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Long remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Long getSelectedRecordsCount() {
        return selectedRecordsCount;
    }

    public void setSelectedRecordsCount(Long selectedRecordsCount) {
        this.selectedRecordsCount = selectedRecordsCount;
    }

    public Long getSelectedSumDebitAmount() {
        return selectedSumDebitAmount;
    }

    public void setSelectedSumDebitAmount(Long selectedSumDebitAmount) {
        this.selectedSumDebitAmount = selectedSumDebitAmount;
    }

    public Long getSelectedSumCreditAmount() {
        return selectedSumCreditAmount;
    }

    public void setSelectedSumCreditAmount(Long selectedSumCreditAmount) {
        this.selectedSumCreditAmount = selectedSumCreditAmount;
    }

    public Long getSelectedRemainAmount() {
        return selectedRemainAmount;
    }

    public void setSelectedRemainAmount(Long selectedRemainAmount) {
        this.selectedRemainAmount = selectedRemainAmount;
    }

    public static FinancialDocumentSummarizeResponse.Builder builder() {
        return new FinancialDocumentSummarizeResponse.Builder();
    }

    public static final class Builder {
        private FinancialDocumentSummarizeResponse financialDocumentSummarizeResponse;

        private Builder() {
            financialDocumentSummarizeResponse = new FinancialDocumentSummarizeResponse();
        }

        public static Builder financialDocumentSummarizeResponse() {
            return new Builder();
        }

        public Builder recordsCount(Long recordsCount) {
            financialDocumentSummarizeResponse.setRecordsCount(recordsCount);
            return this;
        }

        public Builder sumDebitAmount(Long sumDebitAmount) {
            financialDocumentSummarizeResponse.setSumDebitAmount(sumDebitAmount);
            return this;
        }

        public Builder sumCreditAmount(Long sumCreditAmount) {
            financialDocumentSummarizeResponse.setSumCreditAmount(sumCreditAmount);
            return this;
        }

        public Builder remainAmount(Long remainAmount) {
            financialDocumentSummarizeResponse.setRemainAmount(remainAmount);
            return this;
        }

        public Builder selectedRecordsCount(Long selectedRecordsCount) {
            financialDocumentSummarizeResponse.setSelectedRecordsCount(selectedRecordsCount);
            return this;
        }

        public Builder selectedSumDebitAmount(Long selectedSumDebitAmount) {
            financialDocumentSummarizeResponse.setSelectedSumDebitAmount(selectedSumDebitAmount);
            return this;
        }

        public Builder selectedSumCreditAmount(Long selectedSumCreditAmount) {
            financialDocumentSummarizeResponse.setSelectedSumCreditAmount(selectedSumCreditAmount);
            return this;
        }

        public Builder selectedRemainAmount(Long selectedRemainAmount) {
            financialDocumentSummarizeResponse.setSelectedRemainAmount(selectedRemainAmount);
            return this;
        }

        public FinancialDocumentSummarizeResponse build() {
            return financialDocumentSummarizeResponse;
        }
    }
}
