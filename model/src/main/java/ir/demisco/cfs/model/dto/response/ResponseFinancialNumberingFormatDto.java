package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class ResponseFinancialNumberingFormatDto {

    private Long id;
    private Long financialNumberingFormatTypeId;
    private String financialNumberingFormatTypeDescription;
    private Long financialNumberingTypeId;
    private String    description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFinancialNumberingFormatTypeId() {
        return financialNumberingFormatTypeId;
    }

    public void setFinancialNumberingFormatTypeId(Long financialNumberingFormatTypeId) {
        this.financialNumberingFormatTypeId = financialNumberingFormatTypeId;
    }

    public String getFinancialNumberingFormatTypeDescription() {
        return financialNumberingFormatTypeDescription;
    }

    public void setFinancialNumberingFormatTypeDescription(String financialNumberingFormatTypeDescription) {
        this.financialNumberingFormatTypeDescription = financialNumberingFormatTypeDescription;
    }

    public Long getFinancialNumberingTypeId() {
        return financialNumberingTypeId;
    }

    public void setFinancialNumberingTypeId(Long financialNumberingTypeId) {
        this.financialNumberingTypeId = financialNumberingTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static Builder builder(){ return new Builder();}

    public static final class Builder {
        private ResponseFinancialNumberingFormatDto responseFinancialNumberingFormatDto;

        private Builder() {
            responseFinancialNumberingFormatDto = new ResponseFinancialNumberingFormatDto();
        }

        public static Builder aResponseFinancialNumberingFormatDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            responseFinancialNumberingFormatDto.setId(id);
            return this;
        }

        public Builder financialNumberingFormatTypeId(Long financialNumberingFormatTypeId) {
            responseFinancialNumberingFormatDto.setFinancialNumberingFormatTypeId(financialNumberingFormatTypeId);
            return this;
        }

        public Builder financialNumberingFormatTypeDescription(String financialNumberingFormatTypeDescription) {
            responseFinancialNumberingFormatDto.setFinancialNumberingFormatTypeDescription(financialNumberingFormatTypeDescription);
            return this;
        }

        public Builder financialNumberingTypeId(Long financialNumberingTypeId) {
            responseFinancialNumberingFormatDto.setFinancialNumberingTypeId(financialNumberingTypeId);
            return this;
        }

        public Builder description(String description) {
            responseFinancialNumberingFormatDto.setDescription(description);
            return this;
        }

        public ResponseFinancialNumberingFormatDto build() {
            return responseFinancialNumberingFormatDto;
        }
    }
}
