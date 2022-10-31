package ir.demisco.cfs.model.dto.response;

public class FinancialLedgerClosingTempOutputResponse {
    private Long sequence;
    private Long financialAccountId;
    private Long centricAccountId1;
    private Long centricAccountId2;
    private Long centricAccountId3;
    private Long centricAccountId4;
    private Long centricAccountId5;
    private Long centricAccountId6;
    private Double remDebit;
    private Double remCredit;
    private String docItemDes;

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
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

    public String getDocItemDes() {
        return docItemDes;
    }

    public void setDocItemDes(String docItemDes) {
        this.docItemDes = docItemDes;
    }
    public static FinancialLedgerClosingTempOutputResponse.Builder builder() {
        return new FinancialLedgerClosingTempOutputResponse.Builder();
    }
    public static final class Builder {
        private FinancialLedgerClosingTempOutputResponse financialLedgerClosingTempOutputResponse;

        private Builder() {
            financialLedgerClosingTempOutputResponse = new FinancialLedgerClosingTempOutputResponse();
        }

        public static Builder financialLedgerClosingTempOutputResponse() {
            return new Builder();
        }

        public Builder sequence(Long sequence) {
            financialLedgerClosingTempOutputResponse.setSequence(sequence);
            return this;
        }

        public Builder financialAccountId(Long financialAccountId) {
            financialLedgerClosingTempOutputResponse.setFinancialAccountId(financialAccountId);
            return this;
        }

        public Builder centricAccountId1(Long centricAccountId1) {
            financialLedgerClosingTempOutputResponse.setCentricAccountId1(centricAccountId1);
            return this;
        }

        public Builder centricAccountId2(Long centricAccountId2) {
            financialLedgerClosingTempOutputResponse.setCentricAccountId2(centricAccountId2);
            return this;
        }

        public Builder centricAccountId3(Long centricAccountId3) {
            financialLedgerClosingTempOutputResponse.setCentricAccountId3(centricAccountId3);
            return this;
        }

        public Builder centricAccountId4(Long centricAccountId4) {
            financialLedgerClosingTempOutputResponse.setCentricAccountId4(centricAccountId4);
            return this;
        }

        public Builder centricAccountId5(Long centricAccountId5) {
            financialLedgerClosingTempOutputResponse.setCentricAccountId5(centricAccountId5);
            return this;
        }

        public Builder centricAccountId6(Long centricAccountId6) {
            financialLedgerClosingTempOutputResponse.setCentricAccountId6(centricAccountId6);
            return this;
        }

        public Builder remDebit(Double remDebit) {
            financialLedgerClosingTempOutputResponse.setRemDebit(remDebit);
            return this;
        }

        public Builder remCredit(Double remCredit) {
            financialLedgerClosingTempOutputResponse.setRemCredit(remCredit);
            return this;
        }

        public Builder docItemDes(String docItemDes) {
            financialLedgerClosingTempOutputResponse.setDocItemDes(docItemDes);
            return this;
        }

        public FinancialLedgerClosingTempOutputResponse build() {
            return financialLedgerClosingTempOutputResponse;
        }
    }
}
