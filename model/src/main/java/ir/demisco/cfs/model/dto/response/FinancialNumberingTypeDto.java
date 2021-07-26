package ir.demisco.cfs.model.dto.response;

public class FinancialNumberingTypeDto {

    private Long id;
    private String    description;

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

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private FinancialNumberingTypeDto financialNumberingTypeDto;

        private Builder() {
            financialNumberingTypeDto = new FinancialNumberingTypeDto();
        }

        public static Builder financialNumberingTypeDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialNumberingTypeDto.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialNumberingTypeDto.setDescription(description);
            return this;
        }

        public FinancialNumberingTypeDto build() {
            return financialNumberingTypeDto;
        }
    }
}
