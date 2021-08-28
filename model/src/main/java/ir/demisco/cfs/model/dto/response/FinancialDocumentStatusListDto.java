package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentStatusListDto {

    private Long id;
    private String Code;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private FinancialDocumentStatusListDto financialDocumentStatusListDto;

        private Builder() {
            financialDocumentStatusListDto = new FinancialDocumentStatusListDto();
        }

        public static Builder financialDocumentStatusListDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentStatusListDto.setId(id);
            return this;
        }

        public Builder Code(String Code) {
            financialDocumentStatusListDto.setCode(Code);
            return this;
        }

        public Builder name(String name) {
            financialDocumentStatusListDto.setName(name);
            return this;
        }

        public FinancialDocumentStatusListDto build() {
            return financialDocumentStatusListDto;
        }
    }
}
