package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentDescriptionOrganizationDto {

    private Long id;
    private Long organizationId;
    private String    description;

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

    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private FinancialDocumentDescriptionOrganizationDto financialDocumentDescriptionOrganizationDto;

        private Builder() {
            financialDocumentDescriptionOrganizationDto = new FinancialDocumentDescriptionOrganizationDto();
        }

        public static Builder financialDocumentDescriptionOrganizationDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentDescriptionOrganizationDto.setId(id);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialDocumentDescriptionOrganizationDto.setOrganizationId(organizationId);
            return this;
        }

        public Builder description(String description) {
            financialDocumentDescriptionOrganizationDto.setDescription(description);
            return this;
        }

        public FinancialDocumentDescriptionOrganizationDto build() {
            return financialDocumentDescriptionOrganizationDto;
        }
    }
}
