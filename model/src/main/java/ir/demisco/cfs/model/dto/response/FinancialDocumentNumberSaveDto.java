package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentNumberSaveDto {

    private Long id;
    private Long financialNumberingTypeId;
    private String financialNumberingTypeDescription;
    private Long  FinancialDocumentId;
    private String documentNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFinancialNumberingTypeId() {
        return financialNumberingTypeId;
    }

    public void setFinancialNumberingTypeId(Long financialNumberingTypeId) {
        this.financialNumberingTypeId = financialNumberingTypeId;
    }

    public String getFinancialNumberingTypeDescription() {
        return financialNumberingTypeDescription;
    }

    public void setFinancialNumberingTypeDescription(String financialNumberingTypeDescription) {
        this.financialNumberingTypeDescription = financialNumberingTypeDescription;
    }

    public Long getFinancialDocumentId() {
        return FinancialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        FinancialDocumentId = financialDocumentId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public static Builder builder(){
        return new Builder();
    }


    public static final class Builder {
        private FinancialDocumentNumberSaveDto financialDocumentNumberSaveDto;

        private Builder() {
            financialDocumentNumberSaveDto = new FinancialDocumentNumberSaveDto();
        }

        public static Builder aFinancialDocumentNumberSaveDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentNumberSaveDto.setId(id);
            return this;
        }

        public Builder financialNumberingTypeId(Long financialNumberingTypeId) {
            financialDocumentNumberSaveDto.setFinancialNumberingTypeId(financialNumberingTypeId);
            return this;
        }

        public Builder financialNumberingTypeDescription(String financialNumberingTypeDescription) {
            financialDocumentNumberSaveDto.setFinancialNumberingTypeDescription(financialNumberingTypeDescription);
            return this;
        }

        public Builder FinancialDocumentId(Long FinancialDocumentId) {
            financialDocumentNumberSaveDto.setFinancialDocumentId(FinancialDocumentId);
            return this;
        }

        public Builder documentNumber(String documentNumber) {
            financialDocumentNumberSaveDto.setDocumentNumber(documentNumber);
            return this;
        }

        public FinancialDocumentNumberSaveDto build() {
            return financialDocumentNumberSaveDto;
        }
    }
}
