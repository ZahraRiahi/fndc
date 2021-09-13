package ir.demisco.cfs.model.dto.response;

import java.util.List;

public class ResponseFinancialDocumentItemDto {

    private Long id;
    private Long sequenceNumber;
    private Long financialAccountId;
    private Double debitAmount;
    private Double creditAmount;
    private String description;
    private Long centricAccountId1;
    private Long centricAccountId2;
    private Long centricAccountId3;
    private Long centricAccountId4;
    private Long centricAccountId5;
    private Long centricAccountId6;
    private List<FinancialDocumentReferenceDto> documentReferenceList;
    private List<FinancialDocumentItemCurrencyDto>  documentItemCurrencyList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<FinancialDocumentReferenceDto> getDocumentReferenceList() {
        return documentReferenceList;
    }

    public void setDocumentReferenceList(List<FinancialDocumentReferenceDto> documentReferenceList) {
        this.documentReferenceList = documentReferenceList;
    }

    public List<FinancialDocumentItemCurrencyDto> getDocumentItemCurrencyList() {
        return documentItemCurrencyList;
    }

    public void setDocumentItemCurrencyList(List<FinancialDocumentItemCurrencyDto> documentItemCurrencyList) {
        this.documentItemCurrencyList = documentItemCurrencyList;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private ResponseFinancialDocumentItemDto responseFinancialDocumentItemDto;

        private Builder() {
            responseFinancialDocumentItemDto = new ResponseFinancialDocumentItemDto();
        }

        public static Builder responseFinancialDocumentItemDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            responseFinancialDocumentItemDto.setId(id);
            return this;
        }

        public Builder sequenceNumber(Long sequenceNumber) {
            responseFinancialDocumentItemDto.setSequenceNumber(sequenceNumber);
            return this;
        }

        public Builder financialAccountId(Long financialAccountId) {
            responseFinancialDocumentItemDto.setFinancialAccountId(financialAccountId);
            return this;
        }

        public Builder debitAmount(Double debitAmount) {
            responseFinancialDocumentItemDto.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(Double creditAmount) {
            responseFinancialDocumentItemDto.setCreditAmount(creditAmount);
            return this;
        }

        public Builder description(String description) {
            responseFinancialDocumentItemDto.setDescription(description);
            return this;
        }

        public Builder centricAccountId1(Long centricAccountId1) {
            responseFinancialDocumentItemDto.setCentricAccountId1(centricAccountId1);
            return this;
        }

        public Builder centricAccountId2(Long centricAccountId2) {
            responseFinancialDocumentItemDto.setCentricAccountId2(centricAccountId2);
            return this;
        }

        public Builder centricAccountId3(Long centricAccountId3) {
            responseFinancialDocumentItemDto.setCentricAccountId3(centricAccountId3);
            return this;
        }

        public Builder centricAccountId4(Long centricAccountId4) {
            responseFinancialDocumentItemDto.setCentricAccountId4(centricAccountId4);
            return this;
        }

        public Builder centricAccountId5(Long centricAccountId5) {
            responseFinancialDocumentItemDto.setCentricAccountId5(centricAccountId5);
            return this;
        }

        public Builder centricAccountId6(Long centricAccountId6) {
            responseFinancialDocumentItemDto.setCentricAccountId6(centricAccountId6);
            return this;
        }

        public Builder documentReferenceList(List<FinancialDocumentReferenceDto> documentReferenceList) {
            responseFinancialDocumentItemDto.setDocumentReferenceList(documentReferenceList);
            return this;
        }

        public Builder documentItemCurrencyList(List<FinancialDocumentItemCurrencyDto> documentItemCurrencyList) {
            responseFinancialDocumentItemDto.setDocumentItemCurrencyList(documentItemCurrencyList);
            return this;
        }

        public ResponseFinancialDocumentItemDto build() {
            return responseFinancialDocumentItemDto;
        }
    }
}
