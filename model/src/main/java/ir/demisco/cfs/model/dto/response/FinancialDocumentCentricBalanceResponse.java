package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentCentricBalanceResponse {
    private String financialAccountDescription;
    private Long financialAccountId;
    private String sumDebit;
    private String sumCredit;
    private String befDebit;
    private String befCredit;
    private String remDebit;
    private String remCredit;
    private String centricAccountDescription;
    private Long recordType;
    private Long cnacId1;
    private Long cnacId2;
    private FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel;

    public String getFinancialAccountDescription() {
        return financialAccountDescription;
    }

    public void setFinancialAccountDescription(String financialAccountDescription) {
        this.financialAccountDescription = financialAccountDescription;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    public String getSumDebit() {
        return sumDebit;
    }

    public void setSumDebit(String sumDebit) {
        this.sumDebit = sumDebit;
    }

    public String getSumCredit() {
        return sumCredit;
    }

    public void setSumCredit(String sumCredit) {
        this.sumCredit = sumCredit;
    }

    public String getBefDebit() {
        return befDebit;
    }

    public void setBefDebit(String befDebit) {
        this.befDebit = befDebit;
    }

    public String getBefCredit() {
        return befCredit;
    }

    public void setBefCredit(String befCredit) {
        this.befCredit = befCredit;
    }

    public String getRemDebit() {
        return remDebit;
    }

    public void setRemDebit(String remDebit) {
        this.remDebit = remDebit;
    }

    public String getRemCredit() {
        return remCredit;
    }

    public void setRemCredit(String remCredit) {
        this.remCredit = remCredit;
    }

    public String getCentricAccountDescription() {
        return centricAccountDescription;
    }

    public void setCentricAccountDescription(String centricAccountDescription) {
        this.centricAccountDescription = centricAccountDescription;
    }

    public Long getRecordType() {
        return recordType;
    }

    public void setRecordType(Long recordType) {
        this.recordType = recordType;
    }

    public Long getCnacId1() {
        return cnacId1;
    }

    public void setCnacId1(Long cnacId1) {
        this.cnacId1 = cnacId1;
    }

    public Long getCnacId2() {
        return cnacId2;
    }

    public void setCnacId2(Long cnacId2) {
        this.cnacId2 = cnacId2;
    }

    public FinancialAccountTurnOverSummarizeResponse getFinancialAccountTurnOverSummarizeModel() {
        return financialAccountTurnOverSummarizeModel;
    }

    public void setFinancialAccountTurnOverSummarizeModel(FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel) {
        this.financialAccountTurnOverSummarizeModel = financialAccountTurnOverSummarizeModel;
    }

    public static FinancialDocumentCentricBalanceResponse.Builder builder() {
        return new FinancialDocumentCentricBalanceResponse.Builder();
    }

    public static final class Builder {
        private FinancialDocumentCentricBalanceResponse financialDocumentCentricBalanceResponse;

        private Builder() {
            financialDocumentCentricBalanceResponse = new FinancialDocumentCentricBalanceResponse();
        }

        public static Builder financialDocumentCentricBalanceResponse() {
            return new Builder();
        }

        public Builder financialAccountDescription(String financialAccountDescription) {
            financialDocumentCentricBalanceResponse.setFinancialAccountDescription(financialAccountDescription);
            return this;
        }

        public Builder financialAccountId(Long financialAccountId) {
            financialDocumentCentricBalanceResponse.setFinancialAccountId(financialAccountId);
            return this;
        }

        public Builder sumDebit(String sumDebit) {
            financialDocumentCentricBalanceResponse.setSumDebit(sumDebit);
            return this;
        }

        public Builder sumCredit(String sumCredit) {
            financialDocumentCentricBalanceResponse.setSumCredit(sumCredit);
            return this;
        }

        public Builder befDebit(String befDebit) {
            financialDocumentCentricBalanceResponse.setBefDebit(befDebit);
            return this;
        }

        public Builder befCredit(String befCredit) {
            financialDocumentCentricBalanceResponse.setBefCredit(befCredit);
            return this;
        }

        public Builder remDebit(String remDebit) {
            financialDocumentCentricBalanceResponse.setRemDebit(remDebit);
            return this;
        }

        public Builder remCredit(String remCredit) {
            financialDocumentCentricBalanceResponse.setRemCredit(remCredit);
            return this;
        }

        public Builder centricAccountDescription(String centricAccountDescription) {
            financialDocumentCentricBalanceResponse.setCentricAccountDescription(centricAccountDescription);
            return this;
        }

        public Builder recordType(Long recordType) {
            financialDocumentCentricBalanceResponse.setRecordType(recordType);
            return this;
        }

        public Builder cnacId1(Long cnacId1) {
            financialDocumentCentricBalanceResponse.setCnacId1(cnacId1);
            return this;
        }

        public Builder cnacId2(Long cnacId2) {
            financialDocumentCentricBalanceResponse.setCnacId2(cnacId2);
            return this;
        }

        public FinancialDocumentCentricBalanceResponse build() {
            return financialDocumentCentricBalanceResponse;
        }
    }
}
