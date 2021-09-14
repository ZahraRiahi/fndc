package ir.demisco.cfs.model.dto.response;

public class ResponseDocumentStructureDto {

    private Long id;
    private Long financialAccountId;
    private String financialAccountCode;
    private String financialAccountDescription;
    private Double debit;
    private Double credit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFinancialAccountDescription() {
        return financialAccountDescription;
    }

    public void setFinancialAccountDescription(String financialAccountDescription) {
        this.financialAccountDescription = financialAccountDescription;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public  static Builder builder(){
        return  new Builder();
    }


    public static final class Builder {
        private ResponseDocumentStructureDto responseDocumentStructureDto;

        private Builder() {
            responseDocumentStructureDto = new ResponseDocumentStructureDto();
        }

        public static Builder aResponseDocumentStructureDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            responseDocumentStructureDto.setId(id);
            return this;
        }

        public Builder financialAccountId(Long financialAccountId) {
            responseDocumentStructureDto.setFinancialAccountId(financialAccountId);
            return this;
        }

        public Builder financialAccountCode(String financialAccountCode) {
            responseDocumentStructureDto.setFinancialAccountCode(financialAccountCode);
            return this;
        }

        public Builder financialAccountDescription(String financialAccountDescription) {
            responseDocumentStructureDto.setFinancialAccountDescription(financialAccountDescription);
            return this;
        }

        public Builder debit(Double debit) {
            responseDocumentStructureDto.setDebit(debit);
            return this;
        }

        public Builder credit(Double credit) {
            responseDocumentStructureDto.setCredit(credit);
            return this;
        }

        public ResponseDocumentStructureDto build() {
            return responseDocumentStructureDto;
        }
    }
}
