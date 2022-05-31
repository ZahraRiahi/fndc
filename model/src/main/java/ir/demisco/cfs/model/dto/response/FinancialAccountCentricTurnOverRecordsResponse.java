package ir.demisco.cfs.model.dto.response;

public class FinancialAccountCentricTurnOverRecordsResponse {
    private String accountDescription;
    private String centricAccountDes1;
    private String centricAccountDes2;
    private String centricAccountDes3;
    private String centricAccountDes4;
    private String centricAccountDes5;
    private String centricAccountDes6;
    private String debitAmount;
    private String creditAmount;
    private String remainDebit;
    private String remainCredit;
    private String remainAmount;
    private Long accountId;
    private String accountCode;
    private Long centricAccountId1;
    private Long centricAccountId2;
    private Long centricAccountId3;
    private Long centricAccountId4;
    private Long centricAccountId5;
    private Long centricAccountId6;
    private FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel;

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

    public FinancialAccountTurnOverSummarizeResponse getFinancialAccountTurnOverSummarizeModel() {
        return financialAccountTurnOverSummarizeModel;
    }

    public void setFinancialAccountTurnOverSummarizeModel(FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel) {
        this.financialAccountTurnOverSummarizeModel = financialAccountTurnOverSummarizeModel;
    }

    public static FinancialAccountCentricTurnOverRecordsResponse.Builder builder() {
        return new FinancialAccountCentricTurnOverRecordsResponse.Builder();
    }

    public static final class Builder {
        private FinancialAccountCentricTurnOverRecordsResponse financialAccountCentricTurnOverRecordsResponse;

        private Builder() {
            financialAccountCentricTurnOverRecordsResponse = new FinancialAccountCentricTurnOverRecordsResponse();
        }

        public static Builder aFinancialAccountCentricTurnOverRecordsResponse() {
            return new Builder();
        }

        public Builder accountDescription(String accountDescription) {
            financialAccountCentricTurnOverRecordsResponse.setAccountDescription(accountDescription);
            return this;
        }

        public Builder centricAccountDes1(String centricAccountDes1) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountDes1(centricAccountDes1);
            return this;
        }

        public Builder centricAccountDes2(String centricAccountDes2) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountDes2(centricAccountDes2);
            return this;
        }

        public Builder centricAccountDes3(String centricAccountDes3) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountDes3(centricAccountDes3);
            return this;
        }

        public Builder centricAccountDes4(String centricAccountDes4) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountDes4(centricAccountDes4);
            return this;
        }

        public Builder centricAccountDes5(String centricAccountDes5) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountDes5(centricAccountDes5);
            return this;
        }

        public Builder centricAccountDes6(String centricAccountDes6) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountDes6(centricAccountDes6);
            return this;
        }

        public Builder debitAmount(String debitAmount) {
            financialAccountCentricTurnOverRecordsResponse.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(String creditAmount) {
            financialAccountCentricTurnOverRecordsResponse.setCreditAmount(creditAmount);
            return this;
        }

        public Builder remainDebit(String remainDebit) {
            financialAccountCentricTurnOverRecordsResponse.setRemainDebit(remainDebit);
            return this;
        }

        public Builder remainCredit(String remainCredit) {
            financialAccountCentricTurnOverRecordsResponse.setRemainCredit(remainCredit);
            return this;
        }

        public Builder remainAmount(String remainAmount) {
            financialAccountCentricTurnOverRecordsResponse.setRemainAmount(remainAmount);
            return this;
        }

        public Builder accountId(Long accountId) {
            financialAccountCentricTurnOverRecordsResponse.setAccountId(accountId);
            return this;
        }

        public Builder accountCode(String accountCode) {
            financialAccountCentricTurnOverRecordsResponse.setAccountCode(accountCode);
            return this;
        }

        public Builder centricAccountId1(Long centricAccountId1) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountId1(centricAccountId1);
            return this;
        }

        public Builder centricAccountId2(Long centricAccountId2) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountId2(centricAccountId2);
            return this;
        }

        public Builder centricAccountId3(Long centricAccountId3) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountId3(centricAccountId3);
            return this;
        }

        public Builder centricAccountId4(Long centricAccountId4) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountId4(centricAccountId4);
            return this;
        }

        public Builder centricAccountId5(Long centricAccountId5) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountId5(centricAccountId5);
            return this;
        }

        public Builder centricAccountId6(Long centricAccountId6) {
            financialAccountCentricTurnOverRecordsResponse.setCentricAccountId6(centricAccountId6);
            return this;
        }

        public FinancialAccountCentricTurnOverRecordsResponse build() {
            return financialAccountCentricTurnOverRecordsResponse;
        }
    }
}
