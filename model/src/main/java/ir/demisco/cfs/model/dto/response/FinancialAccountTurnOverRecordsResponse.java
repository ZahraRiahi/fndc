package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialAccountTurnOverRecordsResponse {
    private Date documentDate;
    private String documentNumber;
    private String description;
    private Long debitAmount;
    private Long creditAmount;
    private Long remainDebit;
    private Long remainCredit;
    private Long remainAmount;
    private Long recordType;

    private FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel;


    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Long debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Long getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Long creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Long getRemainDebit() {
        return remainDebit;
    }

    public void setRemainDebit(Long remainDebit) {
        this.remainDebit = remainDebit;
    }

    public Long getRemainCredit() {
        return remainCredit;
    }

    public void setRemainCredit(Long remainCredit) {
        this.remainCredit = remainCredit;
    }

    public Long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Long remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Long getRecordType() {
        return recordType;
    }

    public void setRecordType(Long recordType) {
        this.recordType = recordType;
    }
    public static FinancialAccountTurnOverRecordsResponse.Builder builder() {
        return new FinancialAccountTurnOverRecordsResponse.Builder();
    }

    public FinancialAccountTurnOverSummarizeResponse getFinancialAccountTurnOverSummarizeModel() {
        return financialAccountTurnOverSummarizeModel;
    }

    public void setFinancialAccountTurnOverSummarizeModel(FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel) {
        this.financialAccountTurnOverSummarizeModel = financialAccountTurnOverSummarizeModel;
    }

    public static final class Builder {
        private FinancialAccountTurnOverRecordsResponse financialAccountTurnOverRecordsResponse;

        private Builder() {
            financialAccountTurnOverRecordsResponse = new FinancialAccountTurnOverRecordsResponse();
        }

        public static Builder financialAccountTurnOverRecordsResponse() {
            return new Builder();
        }

        public Builder documentDate(Date documentDate) {
            financialAccountTurnOverRecordsResponse.setDocumentDate(documentDate);
            return this;
        }

        public Builder documentNumber(String documentNumber) {
            financialAccountTurnOverRecordsResponse.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder description(String description) {
            financialAccountTurnOverRecordsResponse.setDescription(description);
            return this;
        }

        public Builder debitAmount(Long debitAmount) {
            financialAccountTurnOverRecordsResponse.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(Long creditAmount) {
            financialAccountTurnOverRecordsResponse.setCreditAmount(creditAmount);
            return this;
        }

        public Builder remainDebit(Long remainDebit) {
            financialAccountTurnOverRecordsResponse.setRemainDebit(remainDebit);
            return this;
        }

        public Builder remainCredit(Long remainCredit) {
            financialAccountTurnOverRecordsResponse.setRemainCredit(remainCredit);
            return this;
        }

        public Builder remainAmount(Long remainAmount) {
            financialAccountTurnOverRecordsResponse.setRemainAmount(remainAmount);
            return this;
        }

        public Builder recordType(Long recordType) {
            financialAccountTurnOverRecordsResponse.setRecordType(recordType);
            return this;
        }

        public FinancialAccountTurnOverRecordsResponse build() {
            return financialAccountTurnOverRecordsResponse;
        }
    }
}
