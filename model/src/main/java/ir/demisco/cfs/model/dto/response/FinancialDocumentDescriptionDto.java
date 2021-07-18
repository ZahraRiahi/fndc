package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialDocumentDescriptionDto {

    private Long id;
    private Long organizationId;
    private String    description;
    private LocalDateTime deletedDate;

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

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private FinancialDocumentDescriptionDto financialDocumentDescriptionDto;

        private Builder() {
            financialDocumentDescriptionDto = new FinancialDocumentDescriptionDto();
        }

        public static Builder financialDocumentDescriptionDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentDescriptionDto.setId(id);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialDocumentDescriptionDto.setOrganizationId(organizationId);
            return this;
        }

        public Builder description(String description) {
            financialDocumentDescriptionDto.setDescription(description);
            return this;
        }

        public Builder deletedDate(LocalDateTime deletedDate) {
            financialDocumentDescriptionDto.setDeletedDate(deletedDate);
            return this;
        }

        public FinancialDocumentDescriptionDto build() {
            return financialDocumentDescriptionDto;
        }
    }
}
