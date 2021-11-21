package ir.demisco.cfs.model.dto.response;

public class FinancialAccountBalanceResponse {
    private Long financialAccountLevel;
    private Long financialAccountParentId;
    private Long financialAccountId;
    private String financialAccountCode;
    private String financialAccountDescription;
    private Double sumDebit;
    private Double sumCredit;
    private Double befDebit;
    private Double befCredit;
    private Double remDebit;
    private Double remCredit;

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

    public Double getSumDebit() {
        return sumDebit;
    }

    public void setSumDebit(Double sumDebit) {
        this.sumDebit = sumDebit;
    }

    public Double getSumCredit() {
        return sumCredit;
    }

    public void setSumCredit(Double sumCredit) {
        this.sumCredit = sumCredit;
    }

    public Double getBefDebit() {
        return befDebit;
    }

    public void setBefDebit(Double befDebit) {
        this.befDebit = befDebit;
    }

    public Double getBefCredit() {
        return befCredit;
    }

    public void setBefCredit(Double befCredit) {
        this.befCredit = befCredit;
    }

    public Double getRemDebit() {
        return remDebit;
    }

    public void setRemDebit(Double remDebit) {
        this.remDebit = remDebit;
    }

    public Double getRemCredit() {
        return remCredit;
    }

    public void setRemCredit(Double remCredit) {
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

        public Builder sumDebit(Double sumDebit) {
            financialAccountBalanceResponse.setSumDebit(sumDebit);
            return this;
        }

        public Builder sumCredit(Double sumCredit) {
            financialAccountBalanceResponse.setSumCredit(sumCredit);
            return this;
        }

        public Builder befDebit(Double befDebit) {
            financialAccountBalanceResponse.setBefDebit(befDebit);
            return this;
        }

        public Builder befCredit(Double befCredit) {
            financialAccountBalanceResponse.setBefCredit(befCredit);
            return this;
        }

        public Builder remDebit(Double remDebit) {
            financialAccountBalanceResponse.setRemDebit(remDebit);
            return this;
        }

        public Builder remCredit(Double remCredit) {
            financialAccountBalanceResponse.setRemCredit(remCredit);
            return this;
        }

        public FinancialAccountBalanceResponse build() {
            return financialAccountBalanceResponse;
        }
    }
}
