package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentItemOutPutResponse {
    private Long id;
    private Long financialDocumentId;
    private Long sequenceNumber;
    private Double debitAmount;
    private Double creditAmount;
    private String description;
    private Long financialAccountId;
    private Long centricAccountId1;
    private Long centricAccountId2;
    private Long centricAccountId3;
    private Long centricAccountId4;
    private Long centricAccountId5;
    private Long centricAccountId6;
    private Long creatorId;
    private Long lastModifierId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(Long lastModifierId) {
        this.lastModifierId = lastModifierId;
    }
    public static FinancialDocumentItemOutPutResponse.Builder builder() {
        return new FinancialDocumentItemOutPutResponse.Builder();
    }
    public static final class Builder {
        private FinancialDocumentItemOutPutResponse financialDocumentItemOutPutResponse;

        private Builder() {
            financialDocumentItemOutPutResponse = new FinancialDocumentItemOutPutResponse();
        }

        public static Builder financialDocumentItemOutPutResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentItemOutPutResponse.setId(id);
            return this;
        }

        public Builder financialDocumentId(Long financialDocumentId) {
            financialDocumentItemOutPutResponse.setFinancialDocumentId(financialDocumentId);
            return this;
        }

        public Builder sequenceNumber(Long sequenceNumber) {
            financialDocumentItemOutPutResponse.setSequenceNumber(sequenceNumber);
            return this;
        }

        public Builder debitAmount(Double debitAmount) {
            financialDocumentItemOutPutResponse.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(Double creditAmount) {
            financialDocumentItemOutPutResponse.setCreditAmount(creditAmount);
            return this;
        }

        public Builder description(String description) {
            financialDocumentItemOutPutResponse.setDescription(description);
            return this;
        }

        public Builder financialAccountId(Long financialAccountId) {
            financialDocumentItemOutPutResponse.setFinancialAccountId(financialAccountId);
            return this;
        }

        public Builder centricAccountId1(Long centricAccountId1) {
            financialDocumentItemOutPutResponse.setCentricAccountId1(centricAccountId1);
            return this;
        }

        public Builder centricAccountId2(Long centricAccountId2) {
            financialDocumentItemOutPutResponse.setCentricAccountId2(centricAccountId2);
            return this;
        }

        public Builder centricAccountId3(Long centricAccountId3) {
            financialDocumentItemOutPutResponse.setCentricAccountId3(centricAccountId3);
            return this;
        }

        public Builder centricAccountId4(Long centricAccountId4) {
            financialDocumentItemOutPutResponse.setCentricAccountId4(centricAccountId4);
            return this;
        }

        public Builder centricAccountId5(Long centricAccountId5) {
            financialDocumentItemOutPutResponse.setCentricAccountId5(centricAccountId5);
            return this;
        }

        public Builder centricAccountId6(Long centricAccountId6) {
            financialDocumentItemOutPutResponse.setCentricAccountId6(centricAccountId6);
            return this;
        }

        public Builder creatorId(Long creatorId) {
            financialDocumentItemOutPutResponse.setCreatorId(creatorId);
            return this;
        }

        public Builder lastModifierId(Long lastModifierId) {
            financialDocumentItemOutPutResponse.setLastModifierId(lastModifierId);
            return this;
        }

        public FinancialDocumentItemOutPutResponse build() {
            return financialDocumentItemOutPutResponse;
        }
    }
}
