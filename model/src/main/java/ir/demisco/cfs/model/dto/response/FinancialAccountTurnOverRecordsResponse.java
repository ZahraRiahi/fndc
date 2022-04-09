package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialAccountTurnOverRecordsResponse {
    private Date documentDate;
    private String documentNumber;
    private String description;
    private Double debitAmount;
    private Double creditAmount;
    private Double remainDebit;
    private Double remainCredit;
    private Double remainAmount;
    private Long recordType;

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

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getRemainDebit() {
        return remainDebit;
    }

    public void setRemainDebit(Double remainDebit) {
        this.remainDebit = remainDebit;
    }

    public Double getRemainCredit() {
        return remainCredit;
    }

    public void setRemainCredit(Double remainCredit) {
        this.remainCredit = remainCredit;
    }

    public Double getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Double remainAmount) {
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

        public Builder debitAmount(Double debitAmount) {
            financialAccountTurnOverRecordsResponse.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(Double creditAmount) {
            financialAccountTurnOverRecordsResponse.setCreditAmount(creditAmount);
            return this;
        }

        public Builder remainDebit(Double remainDebit) {
            financialAccountTurnOverRecordsResponse.setRemainDebit(remainDebit);
            return this;
        }

        public Builder remainCredit(Double remainCredit) {
            financialAccountTurnOverRecordsResponse.setRemainCredit(remainCredit);
            return this;
        }

        public Builder remainAmount(Double remainAmount) {
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
