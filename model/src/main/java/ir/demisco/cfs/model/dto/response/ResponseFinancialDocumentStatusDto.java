package ir.demisco.cfs.model.dto.response;

public class ResponseFinancialDocumentStatusDto {

    private Long id;
    private Long financialDocumentStatusId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFinancialDocumentStatusId() {
        return financialDocumentStatusId;
    }

    public void setFinancialDocumentStatusId(Long financialDocumentStatusId) {
        this.financialDocumentStatusId = financialDocumentStatusId;
    }


    public static final class Builder {
        private ResponseFinancialDocumentStatusDto responseFinancialDocumentStatusDto;

        private Builder() {
            responseFinancialDocumentStatusDto = new ResponseFinancialDocumentStatusDto();
        }

        public static Builder aResponseFinancialDocumentStatusDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            responseFinancialDocumentStatusDto.setId(id);
            return this;
        }

        public Builder financialDocumentStatusId(Long financialDocumentStatusId) {
            responseFinancialDocumentStatusDto.setFinancialDocumentStatusId(financialDocumentStatusId);
            return this;
        }

        public ResponseFinancialDocumentStatusDto build() {
            return responseFinancialDocumentStatusDto;
        }
    }
}
