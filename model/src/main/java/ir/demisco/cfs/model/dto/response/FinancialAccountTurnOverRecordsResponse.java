package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialAccountTurnOverRecordsResponse {
    private Date documentDate;
    private String documentNumber;
    private String description;
    private Long financialDocumentId;
    private String debitAmount;
    private String creditAmount;
    private String remainDebit;
    private String remainCredit;
    private String remainAmount;
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

    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
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

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
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
        public Builder financialDocumentId(Long financialDocumentId) {
            financialAccountTurnOverRecordsResponse.setFinancialDocumentId(financialDocumentId);
            return this;
        }
        public Builder debitAmount(String debitAmount) {
            financialAccountTurnOverRecordsResponse.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(String creditAmount) {
            financialAccountTurnOverRecordsResponse.setCreditAmount(creditAmount);
            return this;
        }

        public Builder remainDebit(String remainDebit) {
            financialAccountTurnOverRecordsResponse.setRemainDebit(remainDebit);
            return this;
        }

        public Builder remainCredit(String remainCredit) {
            financialAccountTurnOverRecordsResponse.setRemainCredit(remainCredit);
            return this;
        }

        public Builder remainAmount(String remainAmount) {
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
