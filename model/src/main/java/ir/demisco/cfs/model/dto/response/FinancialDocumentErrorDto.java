package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentErrorDto {

    private Long financialDocumentId;
    private Long financialDocumentItemId;
    private Long financialDocumentItemSequence;
    private String message;

    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    public Long getFinancialDocumentItemId() {
        return financialDocumentItemId;
    }

    public void setFinancialDocumentItemId(Long financialDocumentItemId) {
        this.financialDocumentItemId = financialDocumentItemId;
    }

    public Long getFinancialDocumentItemSequence() {
        return financialDocumentItemSequence;
    }

    public void setFinancialDocumentItemSequence(Long financialDocumentItemSequence) {
        this.financialDocumentItemSequence = financialDocumentItemSequence;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static Builder builder(){
        return new Builder();
    }
    public static final class Builder {
        private FinancialDocumentErrorDto financialDocumentErrorDto;

        private Builder() {
            financialDocumentErrorDto = new FinancialDocumentErrorDto();
        }

        public static Builder financialDocumentErrorDto() {
            return new Builder();
        }

        public Builder financialDocumentId(Long financialDocumentId) {
            financialDocumentErrorDto.setFinancialDocumentId(financialDocumentId);
            return this;
        }

        public Builder financialDocumentItemId(Long financialDocumentItemId) {
            financialDocumentErrorDto.setFinancialDocumentItemId(financialDocumentItemId);
            return this;
        }

        public Builder financialDocumentItemSequence(Long financialDocumentItemSequence) {
            financialDocumentErrorDto.setFinancialDocumentItemSequence(financialDocumentItemSequence);
            return this;
        }

        public Builder message(String message) {
            financialDocumentErrorDto.setMessage(message);
            return this;
        }

        public FinancialDocumentErrorDto build() {
            return financialDocumentErrorDto;
        }
    }
}
