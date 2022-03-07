package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialDocumentTypeDto {
    private Long id;
    private String description;
    private Boolean activeFlag;
    private Boolean automaticFlag;
    private Long financialSystemId;
    private Long organizationId;
    private LocalDateTime DeletedDate;
    private String financialSystemDescription;

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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public LocalDateTime getDeletedDate() {
        return DeletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        DeletedDate = deletedDate;
    }

    public String getFinancialSystemDescription() {
        return financialSystemDescription;
    }

    public void setFinancialSystemDescription(String financialSystemDescription) {
        this.financialSystemDescription = financialSystemDescription;
    }

    public static FinancialDocumentTypeDto.Builder builder() {
        return new FinancialDocumentTypeDto.Builder();
    }

    public static final class Builder {
        private FinancialDocumentTypeDto financialDocumentTypeDto;

        private Builder() {
            financialDocumentTypeDto = new FinancialDocumentTypeDto();
        }

        public static Builder aFinancialDocumentTypeDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentTypeDto.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialDocumentTypeDto.setDescription(description);
            return this;
        }

        public Builder activeFlag(Boolean activeFlag) {
            financialDocumentTypeDto.setActiveFlag(activeFlag);
            return this;
        }

        public Builder automaticFlag(Boolean automaticFlag) {
            financialDocumentTypeDto.setAutomaticFlag(automaticFlag);
            return this;
        }

        public Builder financialSystemId(Long financialSystemId) {
            financialDocumentTypeDto.setFinancialSystemId(financialSystemId);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialDocumentTypeDto.setOrganizationId(organizationId);
            return this;
        }

        public Builder DeletedDate(LocalDateTime DeletedDate) {
            financialDocumentTypeDto.setDeletedDate(DeletedDate);
            return this;
        }

        public Builder financialSystemDescription(String financialSystemDescription) {
            financialDocumentTypeDto.setFinancialSystemDescription(financialSystemDescription);
            return this;
        }

        public FinancialDocumentTypeDto build() {
            return financialDocumentTypeDto;
        }
    }

}

