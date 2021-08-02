package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialDocumentStatusDto {

    private Long id;
    private String Code;
    private String name;
    private LocalDateTime deletedDate;

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

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public static Builder builder(){
        return  new Builder();
    }
    public static final class Builder {
        private FinancialDocumentStatusDto financialDocumentStatusDto;

        private Builder() {
            financialDocumentStatusDto = new FinancialDocumentStatusDto();
        }

        public static Builder financialDocumentStatusDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentStatusDto.setId(id);
            return this;
        }

        public Builder Code(String Code) {
            financialDocumentStatusDto.setCode(Code);
            return this;
        }

        public Builder name(String name) {
            financialDocumentStatusDto.setName(name);
            return this;
        }

        public Builder deletedDate(LocalDateTime deletedDate) {
            financialDocumentStatusDto.setDeletedDate(deletedDate);
            return this;
        }

        public FinancialDocumentStatusDto build() {
            return financialDocumentStatusDto;
        }
    }
}
