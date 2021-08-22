package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentAccountMessageDto {

    private Long id;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        private FinancialDocumentAccountMessageDto financialDocumentAccountMessageDto;

        private Builder() {
            financialDocumentAccountMessageDto = new FinancialDocumentAccountMessageDto();
        }

        public static Builder financialDocumentAccountMessageDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentAccountMessageDto.setId(id);
            return this;
        }

        public Builder message(String message) {
            financialDocumentAccountMessageDto.setMessage(message);
            return this;
        }

        public FinancialDocumentAccountMessageDto build() {
            return financialDocumentAccountMessageDto;
        }
    }
}
