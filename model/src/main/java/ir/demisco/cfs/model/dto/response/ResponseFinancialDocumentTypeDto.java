package ir.demisco.cfs.model.dto.response;

public class ResponseFinancialDocumentTypeDto {

    private Long id;
    private Long organizationId;
    private String description;
    private Boolean   activeFlag;
    private Boolean automaticFlag;
    private Long financialSystemId;
    private String financialSystemDescription;
    private Boolean searchStatusFlag;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
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

    public Boolean getAutomaticFlag() {
        return automaticFlag;
    }

    public void setAutomaticFlag(Boolean automaticFlag) {
        this.automaticFlag = automaticFlag;
    }

    public Long getFinancialSystemId() {
        return financialSystemId;
    }

    public void setFinancialSystemId(Long financialSystemId) {
        this.financialSystemId = financialSystemId;
    }

    public String getFinancialSystemDescription() {
        return financialSystemDescription;
    }

    public void setFinancialSystemDescription(String financialSystemDescription) {
        this.financialSystemDescription = financialSystemDescription;
    }

    public Boolean getSearchStatusFlag() {
        return searchStatusFlag;
    }

    public void setSearchStatusFlag(Boolean searchStatusFlag) {
        this.searchStatusFlag = searchStatusFlag;
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
        private ResponseFinancialDocumentTypeDto responseFinancialDocumentTypeDto;

        private Builder() {
            responseFinancialDocumentTypeDto = new ResponseFinancialDocumentTypeDto();
        }

        public static Builder responseFinancialDocumentTypeDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            responseFinancialDocumentTypeDto.setId(id);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            responseFinancialDocumentTypeDto.setOrganizationId(organizationId);
            return this;
        }

        public Builder description(String description) {
            responseFinancialDocumentTypeDto.setDescription(description);
            return this;
        }

        public Builder message(String message) {
            responseFinancialDocumentTypeDto.setMessage(message);
            return this;
        }

        public Builder activeFlag(Boolean activeFlag) {
            responseFinancialDocumentTypeDto.setActiveFlag(activeFlag);
            return this;
        }

        public Builder automaticFlag(Boolean automaticFlag) {
            responseFinancialDocumentTypeDto.setAutomaticFlag(automaticFlag);
            return this;
        }

        public Builder searchStatusFlag(Boolean searchStatusFlag) {
            responseFinancialDocumentTypeDto.setSearchStatusFlag(searchStatusFlag);
            return this;
        }

        public Builder financialSystemId(Long financialSystemId) {
            responseFinancialDocumentTypeDto.setFinancialSystemId(financialSystemId);
            return this;
        }

        public Builder financialSystemDescription(String financialSystemDescription) {
            responseFinancialDocumentTypeDto.setFinancialSystemDescription(financialSystemDescription);
            return this;
        }

        public ResponseFinancialDocumentTypeDto build() {
            return responseFinancialDocumentTypeDto;
        }
    }
}
