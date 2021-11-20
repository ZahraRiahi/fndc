package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentCentricTurnOverResponse {
    private Long accountId;
    private String accountCode;
    private String accountDescription;
    private String centricAccountDes1;
    private String centricAccountDes2;
    private String centricAccountDes3;
    private String centricAccountDes4;
    private String centricAccountDes5;
    private String centricAccountDes6;
    private Long centricAccountId1;
    private Long centricAccountId2;
    private Long centricAccountId3;
    private Long centricAccountId4;
    private Long centricAccountId5;
    private Long centricAccountId6;
    private Double debitAmount;
    private Double creditAmount;
    private Double remainDebit;
    private Double remainCredit;
    private Double remainAmount;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

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

    public Long getCentricAccountId1() {
        return centricAccountId1;
    }

    public void setCentricAccountId1(Long centricAccountId1) {
        this.centricAccountId1 = centricAccountId1;
    }

    public Long getCentricAccountId2() {
        return centricAccountId2;
    }

    public void setCentricAccountId2(Long centricAccountId2) {
        this.centricAccountId2 = centricAccountId2;
    }

    public Long getCentricAccountId3() {
        return centricAccountId3;
    }

    public void setCentricAccountId3(Long centricAccountId3) {
        this.centricAccountId3 = centricAccountId3;
    }

    public Long getCentricAccountId4() {
        return centricAccountId4;
    }

    public void setCentricAccountId4(Long centricAccountId4) {
        this.centricAccountId4 = centricAccountId4;
    }

    public Long getCentricAccountId5() {
        return centricAccountId5;
    }

    public void setCentricAccountId5(Long centricAccountId5) {
        this.centricAccountId5 = centricAccountId5;
    }

    public Long getCentricAccountId6() {
        return centricAccountId6;
    }

    public void setCentricAccountId6(Long centricAccountId6) {
        this.centricAccountId6 = centricAccountId6;
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

        public Builder accountId(Long accountId) {
            financialDocumentCentricTurnOverResponse.setAccountId(accountId);
            return this;
        }

        public Builder accountCode(String accountCode) {
            financialDocumentCentricTurnOverResponse.setAccountCode(accountCode);
            return this;
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
        public Builder centricAccountId1(Long centricAccountId1) {
            financialDocumentCentricTurnOverResponse.setCentricAccountId1(centricAccountId1);
            return this;
        }
        public Builder centricAccountId2(Long centricAccountId2) {
            financialDocumentCentricTurnOverResponse.setCentricAccountId2(centricAccountId2);
            return this;
        }
        public Builder centricAccountId3(Long centricAccountId3) {
            financialDocumentCentricTurnOverResponse.setCentricAccountId3(centricAccountId3);
            return this;
        }
        public Builder centricAccountId4(Long centricAccountId4) {
            financialDocumentCentricTurnOverResponse.setCentricAccountId4(centricAccountId4);
            return this;
        }
        public Builder centricAccountId5(Long centricAccountId5) {
            financialDocumentCentricTurnOverResponse.setCentricAccountId5(centricAccountId5);
            return this;
        }
        public Builder centricAccountId6(Long centricAccountId6) {
            financialDocumentCentricTurnOverResponse.setCentricAccountId6(centricAccountId6);
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
