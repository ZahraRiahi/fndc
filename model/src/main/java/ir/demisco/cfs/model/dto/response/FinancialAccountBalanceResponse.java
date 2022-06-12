package ir.demisco.cfs.model.dto.response;

public class FinancialAccountBalanceResponse {
    private Long financialAccountLevel;
    private Long financialAccountParentId;
    private Long financialAccountId;
    private String financialAccountCode;
    private String financialAccountDescription;
    private String sumDebit;
    private String sumCredit;
    private String befDebit;
    private String befCredit;
    private String remDebit;
    private String remCredit;
    private String color;
    private Long recordType;
    private FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel;

    public Long getFinancialAccountLevel() {
        return financialAccountLevel;
    }

    public void setFinancialAccountLevel(Long financialAccountLevel) {
        this.financialAccountLevel = financialAccountLevel;
    }

    public Long getFinancialAccountParentId() {
        return financialAccountParentId;
    }

    public void setFinancialAccountParentId(Long financialAccountParentId) {
        this.financialAccountParentId = financialAccountParentId;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    public String getFinancialAccountCode() {
        return financialAccountCode;
    }

    public void setFinancialAccountCode(String financialAccountCode) {
        this.financialAccountCode = financialAccountCode;
    }

    public String getDescription() {
        return financialAccountDescription;
    }

    public void setFinancialAccountDescription(String financialAccountDescription) {
        this.financialAccountDescription = financialAccountDescription;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setRemCredit(String remCredit) {
        this.remCredit = remCredit;
    }

    public static FinancialAccountBalanceResponse.Builder builder() {
        return new FinancialAccountBalanceResponse.Builder();
    }

    public Long getRecordType() {
        return recordType;
    }

    public void setRecordType(Long recordType) {
        this.recordType = recordType;
    }
    public FinancialAccountTurnOverSummarizeResponse getFinancialAccountTurnOverSummarizeModel() {
        return financialAccountTurnOverSummarizeModel;
    }

    public void setFinancialAccountTurnOverSummarizeModel(FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel) {
        this.financialAccountTurnOverSummarizeModel = financialAccountTurnOverSummarizeModel;
    }
    public static final class Builder {
        private FinancialAccountBalanceResponse financialAccountBalanceResponse;

        private Builder() {
            financialAccountBalanceResponse = new FinancialAccountBalanceResponse();
        }

        public static Builder financialAccountBalanceResponse() {
            return new Builder();
        }

        public Builder financialAccountLevel(Long financialAccountLevel) {
            financialAccountBalanceResponse.setFinancialAccountLevel(financialAccountLevel);
            return this;
        }

        public Builder financialAccountParentId(Long financialAccountParentId) {
            financialAccountBalanceResponse.setFinancialAccountParentId(financialAccountParentId);
            return this;
        }

        public Builder financialAccountId(Long financialAccountId) {
            financialAccountBalanceResponse.setFinancialAccountId(financialAccountId);
            return this;
        }

        public Builder financialAccountCode(String financialAccountCode) {
            financialAccountBalanceResponse.setFinancialAccountCode(financialAccountCode);
            return this;
        }

        public Builder financialAccountDescription(String financialAccountDescription) {
            financialAccountBalanceResponse.setFinancialAccountDescription(financialAccountDescription);
            return this;
        }

        public Builder sumDebit(String sumDebit) {
            financialAccountBalanceResponse.setSumDebit(sumDebit);
            return this;
        }

        public Builder sumCredit(String sumCredit) {
            financialAccountBalanceResponse.setSumCredit(sumCredit);
            return this;
        }

        public Builder befDebit(String befDebit) {
            financialAccountBalanceResponse.setBefDebit(befDebit);
            return this;
        }

        public Builder befCredit(String befCredit) {
            financialAccountBalanceResponse.setBefCredit(befCredit);
            return this;
        }

        public Builder remDebit(String remDebit) {
            financialAccountBalanceResponse.setRemDebit(remDebit);
            return this;
        }

        public Builder remCredit(String remCredit) {
            financialAccountBalanceResponse.setRemCredit(remCredit);
            return this;
        }

        public Builder color(String color) {
            financialAccountBalanceResponse.setColor(color);
            return this;
        }

        public FinancialAccountBalanceResponse.Builder recordType(Long recordType) {
            financialAccountBalanceResponse.setRecordType(recordType);
            return this;
        }

        public FinancialAccountBalanceResponse build() {
            return financialAccountBalanceResponse;
        }
    }
}
