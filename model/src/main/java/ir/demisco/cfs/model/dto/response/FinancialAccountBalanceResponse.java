package ir.demisco.cfs.model.dto.response;

public class FinancialAccountBalanceResponse {
    private Long financialAccountLevel;
    private Long financialAccountParentId;
    private Long financialAccountId;
    private String financialAccountCode;
    private String financialAccountDescription;
    private Long sumDebit;
    private Long sumCredit;
    private Long befDebit;
    private Long befCredit;
    private Long remDebit;
    private Long remCredit;

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

    public Long getSumDebit() {
        return sumDebit;
    }

    public void setSumDebit(Long sumDebit) {
        this.sumDebit = sumDebit;
    }

    public Long getSumCredit() {
        return sumCredit;
    }

    public void setSumCredit(Long sumCredit) {
        this.sumCredit = sumCredit;
    }

    public Long getBefDebit() {
        return befDebit;
    }

    public void setBefDebit(Long befDebit) {
        this.befDebit = befDebit;
    }

    public Long getBefCredit() {
        return befCredit;
    }

    public void setBefCredit(Long befCredit) {
        this.befCredit = befCredit;
    }

    public Long getRemDebit() {
        return remDebit;
    }

    public void setRemDebit(Long remDebit) {
        this.remDebit = remDebit;
    }

    public Long getRemCredit() {
        return remCredit;
    }

    public void setRemCredit(Long remCredit) {
        this.remCredit = remCredit;
    }

    public static FinancialAccountBalanceResponse.Builder builder() {
        return new FinancialAccountBalanceResponse.Builder();
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

        public Builder sumDebit(Long sumDebit) {
            financialAccountBalanceResponse.setSumDebit(sumDebit);
            return this;
        }

        public Builder sumCredit(Long sumCredit) {
            financialAccountBalanceResponse.setSumCredit(sumCredit);
            return this;
        }

        public Builder befDebit(Long befDebit) {
            financialAccountBalanceResponse.setBefDebit(befDebit);
            return this;
        }

        public Builder befCredit(Long befCredit) {
            financialAccountBalanceResponse.setBefCredit(befCredit);
            return this;
        }

        public Builder remDebit(Long remDebit) {
            financialAccountBalanceResponse.setRemDebit(remDebit);
            return this;
        }

        public Builder remCredit(Long remCredit) {
            financialAccountBalanceResponse.setRemCredit(remCredit);
            return this;
        }

        public FinancialAccountBalanceResponse build() {
            return financialAccountBalanceResponse;
        }
    }
}
