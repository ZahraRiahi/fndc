package ir.demisco.cfs.model.dto.response;

public class ResponseFinancialDocumentStructureDto {

    private Long documentStructureId;
    private Long sequence;
    private String description;
    private Long financialCodingTypeId;

    public Long getDocumentStructureId() {
        return documentStructureId;
    }

    public void setDocumentStructureId(Long documentStructureId) {
        this.documentStructureId = documentStructureId;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFinancialCodingTypeId() {
        return financialCodingTypeId;
    }

    public void setFinancialCodingTypeId(Long financialCodingTypeId) {
        this.financialCodingTypeId = financialCodingTypeId;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private ResponseFinancialDocumentStructureDto responseFinancialDocumentStructureDto;

        private Builder() {
            responseFinancialDocumentStructureDto = new ResponseFinancialDocumentStructureDto();
        }

        public static Builder responseFinancialDocumentStructureDto() {
            return new Builder();
        }

        public Builder documentStructureId(Long documentStructureId) {
            responseFinancialDocumentStructureDto.setDocumentStructureId(documentStructureId);
            return this;
        }

        public Builder sequence(Long sequence) {
            responseFinancialDocumentStructureDto.setSequence(sequence);
            return this;
        }

        public Builder description(String description) {
            responseFinancialDocumentStructureDto.setDescription(description);
            return this;
        }

        public Builder financialCodingTypeId(Long financialCodingTypeId) {
            responseFinancialDocumentStructureDto.setFinancialCodingTypeId(financialCodingTypeId);
            return this;
        }

        public ResponseFinancialDocumentStructureDto build() {
            return responseFinancialDocumentStructureDto;
        }
    }
}
