package ir.demisco.cfs.model.dto.response;



public class FinancialNumberingFormatTypeDto {

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
        private FinancialNumberingFormatTypeDto financialNumberingFormatTypeDto;

        private Builder() {
            financialNumberingFormatTypeDto = new FinancialNumberingFormatTypeDto();
        }

        public static Builder financialNumberingFormatTypeDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialNumberingFormatTypeDto.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialNumberingFormatTypeDto.setDescription(description);
            return this;
        }

        public FinancialNumberingFormatTypeDto build() {
            return financialNumberingFormatTypeDto;
        }
    }
}
