package ir.demisco.cfs.model.dto.response;

import java.util.List;

public class FinancialDocumentItemResponse {
    private Long id;
    private Long financialDocumentId;
    private Long sequenceNumber;
    private Long financialAccountId;
    private String financialAccountDescription;
    private Double debitAmount;
    private Double creditAmount;
    private String description;
    private Long centricAccountId1;
    private String centricAccountDescription1;
    private Long centricAccountId2;
    private String centricAccountDescription2;
    private Long centricAccountId3;
    private String centricAccountDescription3;
    private Long centricAccountId4;
    private String centricAccountDescription4;
    private Long centricAccountId5;
    private String centricAccountDescription5;
    private Long centricAccountId6;
    private String centricAccountDescription6;
    private String financialDocumentStatusCode;
    private String centricAccountDescription;
    private List<FinancialDocumentReferenceOutPutModel> documentReferenceList;
    private List<FinancialDocumentItemCurrencyOutPutModel> documentItemCurrencyList;
    private Long accountRelationTypeId;

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

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    public String getFinancialAccountDescription() {
        return financialAccountDescription;
    }

    public void setFinancialAccountDescription(String financialAccountDescription) {
        this.financialAccountDescription = financialAccountDescription;
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

    public Long getCentricAccountId1() {
        return centricAccountId1;
    }

    public void setCentricAccountId1(Long centricAccountId1) {
        this.centricAccountId1 = centricAccountId1;
    }

    public String getCentricAccountDescription1() {
        return centricAccountDescription1;
    }

    public void setCentricAccountDescription1(String centricAccountDescription1) {
        this.centricAccountDescription1 = centricAccountDescription1;
    }

    public Long getCentricAccountId2() {
        return centricAccountId2;
    }

    public void setCentricAccountId2(Long centricAccountId2) {
        this.centricAccountId2 = centricAccountId2;
    }

    public String getCentricAccountDescription2() {
        return centricAccountDescription2;
    }

    public void setCentricAccountDescription2(String centricAccountDescription2) {
        this.centricAccountDescription2 = centricAccountDescription2;
    }

    public Long getCentricAccountId3() {
        return centricAccountId3;
    }

    public void setCentricAccountId3(Long centricAccountId3) {
        this.centricAccountId3 = centricAccountId3;
    }

    public String getCentricAccountDescription3() {
        return centricAccountDescription3;
    }

    public void setCentricAccountDescription3(String centricAccountDescription3) {
        this.centricAccountDescription3 = centricAccountDescription3;
    }

    public Long getCentricAccountId4() {
        return centricAccountId4;
    }

    public void setCentricAccountId4(Long centricAccountId4) {
        this.centricAccountId4 = centricAccountId4;
    }

    public String getCentricAccountDescription4() {
        return centricAccountDescription4;
    }

    public void setCentricAccountDescription4(String centricAccountDescription4) {
        this.centricAccountDescription4 = centricAccountDescription4;
    }

    public Long getCentricAccountId5() {
        return centricAccountId5;
    }

    public void setCentricAccountId5(Long centricAccountId5) {
        this.centricAccountId5 = centricAccountId5;
    }

    public String getCentricAccountDescription5() {
        return centricAccountDescription5;
    }

    public void setCentricAccountDescription5(String centricAccountDescription5) {
        this.centricAccountDescription5 = centricAccountDescription5;
    }

    public Long getCentricAccountId6() {
        return centricAccountId6;
    }

    public void setCentricAccountId6(Long centricAccountId6) {
        this.centricAccountId6 = centricAccountId6;
    }

    public String getCentricAccountDescription6() {
        return centricAccountDescription6;
    }

    public void setCentricAccountDescription6(String centricAccountDescription6) {
        this.centricAccountDescription6 = centricAccountDescription6;
    }

    public String getFinancialDocumentStatusCode() {
        return financialDocumentStatusCode;
    }

    public void setFinancialDocumentStatusCode(String financialDocumentStatusCode) {
        this.financialDocumentStatusCode = financialDocumentStatusCode;
    }

    public String getCentricAccountDescription() {
        return centricAccountDescription;
    }

    public void setCentricAccountDescription(String centricAccountDescription) {
        this.centricAccountDescription = centricAccountDescription;
    }

    public List<FinancialDocumentReferenceOutPutModel> getDocumentReferenceList() {
        return documentReferenceList;
    }

    public void setDocumentReferenceList(List<FinancialDocumentReferenceOutPutModel> documentReferenceList) {
        this.documentReferenceList = documentReferenceList;
    }

    public List<FinancialDocumentItemCurrencyOutPutModel> getDocumentItemCurrencyList() {
        return documentItemCurrencyList;
    }

    public void setDocumentItemCurrencyList(List<FinancialDocumentItemCurrencyOutPutModel> documentItemCurrencyList) {
        this.documentItemCurrencyList = documentItemCurrencyList;
    }

    public Long getAccountRelationTypeId() {
        return accountRelationTypeId;
    }

    public void setAccountRelationTypeId(Long accountRelationTypeId) {
        this.accountRelationTypeId = accountRelationTypeId;
    }

    public static FinancialDocumentItemResponse.Builder builder() {
        return new FinancialDocumentItemResponse.Builder();
    }

    public static final class Builder {
        private FinancialDocumentItemResponse financialDocumentItemResponse;

        private Builder() {
            financialDocumentItemResponse = new FinancialDocumentItemResponse();
        }

        public static Builder financialDocumentItemResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentItemResponse.setId(id);
            return this;
        }

        public Builder financialDocumentId(Long financialDocumentId) {
            financialDocumentItemResponse.setFinancialDocumentId(financialDocumentId);
            return this;
        }

        public Builder sequenceNumber(Long sequenceNumber) {
            financialDocumentItemResponse.setSequenceNumber(sequenceNumber);
            return this;
        }

        public Builder financialAccountId(Long financialAccountId) {
            financialDocumentItemResponse.setFinancialAccountId(financialAccountId);
            return this;
        }

        public Builder financialAccountDescription(String financialAccountDescription) {
            financialDocumentItemResponse.setFinancialAccountDescription(financialAccountDescription);
            return this;
        }

        public Builder debitAmount(Double debitAmount) {
            financialDocumentItemResponse.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(Double creditAmount) {
            financialDocumentItemResponse.setCreditAmount(creditAmount);
            return this;
        }

        public Builder description(String description) {
            financialDocumentItemResponse.setDescription(description);
            return this;
        }

        public Builder centricAccountId1(Long centricAccountId1) {
            financialDocumentItemResponse.setCentricAccountId1(centricAccountId1);
            return this;
        }

        public Builder centricAccountDescription1(String centricAccountDescription1) {
            financialDocumentItemResponse.setCentricAccountDescription1(centricAccountDescription1);
            return this;
        }

        public Builder centricAccountId2(Long centricAccountId2) {
            financialDocumentItemResponse.setCentricAccountId2(centricAccountId2);
            return this;
        }

        public Builder centricAccountDescription2(String centricAccountDescription2) {
            financialDocumentItemResponse.setCentricAccountDescription2(centricAccountDescription2);
            return this;
        }

        public Builder centricAccountId3(Long centricAccountId3) {
            financialDocumentItemResponse.setCentricAccountId3(centricAccountId3);
            return this;
        }

        public Builder centricAccountDescription3(String centricAccountDescription3) {
            financialDocumentItemResponse.setCentricAccountDescription3(centricAccountDescription3);
            return this;
        }

        public Builder centricAccountId4(Long centricAccountId4) {
            financialDocumentItemResponse.setCentricAccountId4(centricAccountId4);
            return this;
        }

        public Builder centricAccountDescription4(String centricAccountDescription4) {
            financialDocumentItemResponse.setCentricAccountDescription4(centricAccountDescription4);
            return this;
        }

        public Builder centricAccountId5(Long centricAccountId5) {
            financialDocumentItemResponse.setCentricAccountId5(centricAccountId5);
            return this;
        }

        public Builder centricAccountDescription5(String centricAccountDescription5) {
            financialDocumentItemResponse.setCentricAccountDescription5(centricAccountDescription5);
            return this;
        }

        public Builder centricAccountId6(Long centricAccountId6) {
            financialDocumentItemResponse.setCentricAccountId6(centricAccountId6);
            return this;
        }

        public Builder centricAccountDescription6(String centricAccountDescription6) {
            financialDocumentItemResponse.setCentricAccountDescription6(centricAccountDescription6);
            return this;
        }

        public Builder financialDocumentStatusCode(String financialDocumentStatusCode) {
            financialDocumentItemResponse.setFinancialDocumentStatusCode(financialDocumentStatusCode);
            return this;
        }

        public Builder centricAccountDescription(String centricAccountDescription) {
            financialDocumentItemResponse.setCentricAccountDescription(centricAccountDescription);
            return this;
        }

        public Builder documentReferenceList(List<FinancialDocumentReferenceOutPutModel> documentReferenceList) {
            financialDocumentItemResponse.setDocumentReferenceList(documentReferenceList);
            return this;
        }

        public Builder documentItemCurrencyList(List<FinancialDocumentItemCurrencyOutPutModel> documentItemCurrencyList) {
            financialDocumentItemResponse.setDocumentItemCurrencyList(documentItemCurrencyList);
            return this;
        }

        public Builder accountRelationTypeId(Long accountRelationTypeId) {
            financialDocumentItemResponse.setAccountRelationTypeId(accountRelationTypeId);
            return this;
        }

        public FinancialDocumentItemResponse build() {
            return financialDocumentItemResponse;
        }
    }
}
