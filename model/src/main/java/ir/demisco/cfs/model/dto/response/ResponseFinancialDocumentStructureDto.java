package ir.demisco.cfs.model.dto.response;

public class ResponseFinancialDocumentStructureDto {

    private Long DocumentStructureId;
    private Long sequence;
    private String description;
    private Long FinancialCodingTypeId;

    public Long getDocumentStructureId() {
        return DocumentStructureId;
    }

    public void setDocumentStructureId(Long documentStructureId) {
        DocumentStructureId = documentStructureId;
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
        return FinancialCodingTypeId;
    }

    public void setFinancialCodingTypeId(Long financialCodingTypeId) {
        FinancialCodingTypeId = financialCodingTypeId;
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

        public Builder DocumentStructureId(Long DocumentStructureId) {
            responseFinancialDocumentStructureDto.setDocumentStructureId(DocumentStructureId);
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

        public Builder FinancialCodingTypeId(Long FinancialCodingTypeId) {
            responseFinancialDocumentStructureDto.setFinancialCodingTypeId(FinancialCodingTypeId);
            return this;
        }

        public ResponseFinancialDocumentStructureDto build() {
            return responseFinancialDocumentStructureDto;
        }
    }
}
