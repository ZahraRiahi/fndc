package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentTypeGetDto {

    private Long id;
    private String description;
    private Boolean activeFlag;
    private Boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private FinancialDocumentTypeGetDto financialDocumentTypeGetDto;

        private Builder() {
            financialDocumentTypeGetDto = new FinancialDocumentTypeGetDto();
        }

        public static Builder aFinancialDocumentTypeGetDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentTypeGetDto.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialDocumentTypeGetDto.setDescription(description);
            return this;
        }

        public Builder activeFlag(Boolean activeFlag) {
            financialDocumentTypeGetDto.setActiveFlag(activeFlag);
            return this;
        }

        public Builder disabled(Boolean disabled) {
            financialDocumentTypeGetDto.setDisabled(disabled);
            return this;
        }

        public FinancialDocumentTypeGetDto build() {
            return financialDocumentTypeGetDto;
        }
    }
}
