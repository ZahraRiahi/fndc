package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentCentricTurnOverResponse {
    private String accountDescription;
    private String centricAccountDes1;
    private String centricAccountDes2;
    private String centricAccountDes3;
    private String centricAccountDes4;
    private String centricAccountDes5;
    private String centricAccountDes6;
    private Double debitAmount;
    private Double creditAmount;
    private Double remainDebit;
    private Double remainCredit;
    private Double remainAmount;

    public String getAccountDescription() {
        return accountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    public String getCentricAccountDes1() {
        return centricAccountDes1;
    }

    public void setCentricAccountDes1(String centricAccountDes1) {
        this.centricAccountDes1 = centricAccountDes1;
    }

    public String getCentricAccountDes2() {
        return centricAccountDes2;
    }

    public void setCentricAccountDes2(String centricAccountDes2) {
        this.centricAccountDes2 = centricAccountDes2;
    }

    public String getCentricAccountDes3() {
        return centricAccountDes3;
    }

    public void setCentricAccountDes3(String centricAccountDes3) {
        this.centricAccountDes3 = centricAccountDes3;
    }

    public String getCentricAccountDes4() {
        return centricAccountDes4;
    }

    public void setCentricAccountDes4(String centricAccountDes4) {
        this.centricAccountDes4 = centricAccountDes4;
    }

    public String getCentricAccountDes5() {
        return centricAccountDes5;
    }

    public void setCentricAccountDes5(String centricAccountDes5) {
        this.centricAccountDes5 = centricAccountDes5;
    }

    public String getCentricAccountDes6() {
        return centricAccountDes6;
    }

    public void setCentricAccountDes6(String centricAccountDes6) {
        this.centricAccountDes6 = centricAccountDes6;
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

    public static FinancialDocumentCentricTurnOverResponse.Builder builder() {
        return new FinancialDocumentCentricTurnOverResponse.Builder();
    }

    public static final class Builder {
        private FinancialDocumentCentricTurnOverResponse financialDocumentCentricTurnOverResponse;

        private Builder() {
            financialDocumentCentricTurnOverResponse = new FinancialDocumentCentricTurnOverResponse();
        }

        public static Builder financialDocumentCentricTurnOverResponse() {
            return new Builder();
        }

        public Builder accountDescription(String accountDescription) {
            financialDocumentCentricTurnOverResponse.setAccountDescription(accountDescription);
            return this;
        }

        public Builder centricAccountDes1(String centricAccountDes1) {
            financialDocumentCentricTurnOverResponse.setCentricAccountDes1(centricAccountDes1);
            return this;
        }

        public Builder centricAccountDes2(String centricAccountDes2) {
            financialDocumentCentricTurnOverResponse.setCentricAccountDes2(centricAccountDes2);
            return this;
        }

        public Builder centricAccountDes3(String centricAccountDes3) {
            financialDocumentCentricTurnOverResponse.setCentricAccountDes3(centricAccountDes3);
            return this;
        }

        public Builder centricAccountDes4(String centricAccountDes4) {
            financialDocumentCentricTurnOverResponse.setCentricAccountDes4(centricAccountDes4);
            return this;
        }

        public Builder centricAccountDes5(String centricAccountDes5) {
            financialDocumentCentricTurnOverResponse.setCentricAccountDes5(centricAccountDes5);
            return this;
        }

        public Builder centricAccountDes6(String centricAccountDes6) {
            financialDocumentCentricTurnOverResponse.setCentricAccountDes6(centricAccountDes6);
            return this;
        }

        public Builder debitAmount(Double debitAmount) {
            financialDocumentCentricTurnOverResponse.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(Double creditAmount) {
            financialDocumentCentricTurnOverResponse.setCreditAmount(creditAmount);
            return this;
        }

        public Builder remainDebit(Double remainDebit) {
            financialDocumentCentricTurnOverResponse.setRemainDebit(remainDebit);
            return this;
        }

        public Builder remainCredit(Double remainCredit) {
            financialDocumentCentricTurnOverResponse.setRemainCredit(remainCredit);
            return this;
        }
        public Builder remainAmount(Double remainAmount) {
            financialDocumentCentricTurnOverResponse.setRemainAmount(remainAmount);
            return this;
        }
        public FinancialDocumentCentricTurnOverResponse build() {
            return financialDocumentCentricTurnOverResponse;
        }
    }
}
