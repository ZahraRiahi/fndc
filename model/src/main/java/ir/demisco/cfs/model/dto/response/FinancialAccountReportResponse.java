package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialAccountReportResponse {
    private Date documentDate;
    private String documentNumber;
    private String description;
    private Double debitAmount;
    private Double creditAmount;
    private Double remainDebit;
    private Double remainCredit;
    private Double remainAmount;

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

    public static FinancialAccountReportResponse.Builder builder() {
        return new FinancialAccountReportResponse.Builder();
    }

    public static final class Builder {
        private FinancialAccountReportResponse financialAccountReportResponse;

        private Builder() {
            financialAccountReportResponse = new FinancialAccountReportResponse();
        }

        public static Builder aFinancialAccountReportResponse() {
            return new Builder();
        }

        public Builder documentDate(Date documentDate) {
            financialAccountReportResponse.setDocumentDate(documentDate);
            return this;
        }

        public Builder documentNumber(String documentNumber) {
            financialAccountReportResponse.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder description(String description) {
            financialAccountReportResponse.setDescription(description);
            return this;
        }

        public Builder debitAmount(Double debitAmount) {
            financialAccountReportResponse.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(Double creditAmount) {
            financialAccountReportResponse.setCreditAmount(creditAmount);
            return this;
        }

        public Builder remainDebit(Double remainDebit) {
            financialAccountReportResponse.setRemainDebit(remainDebit);
            return this;
        }

        public Builder remainCredit(Double remainCredit) {
            financialAccountReportResponse.setRemainCredit(remainCredit);
            return this;
        }

        public Builder remainAmount(Double remainAmount) {
            financialAccountReportResponse.setRemainAmount(remainAmount);
            return this;
        }

        public FinancialAccountReportResponse build() {
            return financialAccountReportResponse;
        }
    }
}
