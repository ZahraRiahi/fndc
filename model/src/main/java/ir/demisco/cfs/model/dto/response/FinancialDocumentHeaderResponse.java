package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialDocumentHeaderResponse {
    private Long id;
    private Date documentDate;
    private String documentNumber;
    private Long financialDocumentTypeId;
    private String financialDocumentTypeDescription;
    private Long financialDocumentStatusId;
    private Boolean automaticFlag;
    private String description;
    private Long organizationId;
    private Long financialLedgerTypeId;
    private String financialLedgerTypeDescription;
    private Long financialDepartmentId;
    private String financialDepartmentName;
    private Long financialPeriodId;
    private String financialPeriodDescription;
    private String financialDocumentStatusCode;
    private String financialDocumentStatusDescription;
    private Long creatorId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Long getFinancialDocumentTypeId() {
        return financialDocumentTypeId;
    }

    public void setFinancialDocumentTypeId(Long financialDocumentTypeId) {
        this.financialDocumentTypeId = financialDocumentTypeId;
    }

    public String getFinancialDocumentTypeDescription() {
        return financialDocumentTypeDescription;
    }

    public void setFinancialDocumentTypeDescription(String financialDocumentTypeDescription) {
        this.financialDocumentTypeDescription = financialDocumentTypeDescription;
    }

    public Long getFinancialDocumentStatusId() {
        return financialDocumentStatusId;
    }

    public void setFinancialDocumentStatusId(Long financialDocumentStatusId) {
        this.financialDocumentStatusId = financialDocumentStatusId;
    }

    public Boolean getAutomaticFlag() {
        return automaticFlag;
    }

    public void setAutomaticFlag(Boolean automaticFlag) {
        this.automaticFlag = automaticFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public String getFinancialLedgerTypeDescription() {
        return financialLedgerTypeDescription;
    }

    public void setFinancialLedgerTypeDescription(String financialLedgerTypeDescription) {
        this.financialLedgerTypeDescription = financialLedgerTypeDescription;
    }

    public Long getFinancialDepartmentId() {
        return financialDepartmentId;
    }

    public void setFinancialDepartmentId(Long financialDepartmentId) {
        this.financialDepartmentId = financialDepartmentId;
    }

    public String getFinancialDepartmentName() {
        return financialDepartmentName;
    }

    public void setFinancialDepartmentName(String financialDepartmentName) {
        this.financialDepartmentName = financialDepartmentName;
    }

    public Long getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(Long financialPeriodId) {
        this.financialPeriodId = financialPeriodId;
    }

    public String getFinancialPeriodDescription() {
        return financialPeriodDescription;
    }

    public void setFinancialPeriodDescription(String financialPeriodDescription) {
        this.financialPeriodDescription = financialPeriodDescription;
    }

    public String getFinancialDocumentStatusCode() {
        return financialDocumentStatusCode;
    }

    public void setFinancialDocumentStatusCode(String financialDocumentStatusCode) {
        this.financialDocumentStatusCode = financialDocumentStatusCode;
    }

    public String getFinancialDocumentStatusDescription() {
        return financialDocumentStatusDescription;
    }

    public void setFinancialDocumentStatusDescription(String financialDocumentStatusDescription) {
        this.financialDocumentStatusDescription = financialDocumentStatusDescription;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public static FinancialDocumentHeaderResponse.Builder builder() {
        return new FinancialDocumentHeaderResponse.Builder();
    }

    public static final class Builder {
        private FinancialDocumentHeaderResponse financialDocumentHeaderResponse;

        private Builder() {
            financialDocumentHeaderResponse = new FinancialDocumentHeaderResponse();
        }

        public static Builder financialDocumentHeaderResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentHeaderResponse.setId(id);
            return this;
        }

        public Builder documentDate(Date documentDate) {
            financialDocumentHeaderResponse.setDocumentDate(documentDate);
            return this;
        }

        public Builder documentNumber(String documentNumber) {
            financialDocumentHeaderResponse.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder financialDocumentTypeId(Long financialDocumentTypeId) {
            financialDocumentHeaderResponse.setFinancialDocumentTypeId(financialDocumentTypeId);
            return this;
        }

        public Builder financialDocumentTypeDescription(String financialDocumentTypeDescription) {
            financialDocumentHeaderResponse.setFinancialDocumentTypeDescription(financialDocumentTypeDescription);
            return this;
        }

        public Builder financialDocumentStatusId(Long financialDocumentStatusId) {
            financialDocumentHeaderResponse.setFinancialDocumentStatusId(financialDocumentStatusId);
            return this;
        }

        public Builder automaticFlag(Boolean automaticFlag) {
            financialDocumentHeaderResponse.setAutomaticFlag(automaticFlag);
            return this;
        }

        public Builder description(String description) {
            financialDocumentHeaderResponse.setDescription(description);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialDocumentHeaderResponse.setOrganizationId(organizationId);
            return this;
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialDocumentHeaderResponse.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder financialLedgerTypeDescription(String financialLedgerTypeDescription) {
            financialDocumentHeaderResponse.setFinancialLedgerTypeDescription(financialLedgerTypeDescription);
            return this;
        }

        public Builder financialDepartmentId(Long financialDepartmentId) {
            financialDocumentHeaderResponse.setFinancialDepartmentId(financialDepartmentId);
            return this;
        }

        public Builder financialDepartmentName(String financialDepartmentName) {
            financialDocumentHeaderResponse.setFinancialDepartmentName(financialDepartmentName);
            return this;
        }

        public Builder financialPeriodId(Long financialPeriodId) {
            financialDocumentHeaderResponse.setFinancialPeriodId(financialPeriodId);
            return this;
        }

        public Builder financialPeriodDescription(String financialPeriodDescription) {
            financialDocumentHeaderResponse.setFinancialPeriodDescription(financialPeriodDescription);
            return this;
        }

        public Builder financialDocumentStatusCode(String financialDocumentStatusCode) {
            financialDocumentHeaderResponse.setFinancialDocumentStatusCode(financialDocumentStatusCode);
            return this;
        }

        public Builder financialDocumentStatusDescription(String financialDocumentStatusDescription) {
            financialDocumentHeaderResponse.setFinancialDocumentStatusDescription(financialDocumentStatusDescription);
            return this;
        }

        public Builder creatorId(Long creatorId) {
            financialDocumentHeaderResponse.setCreatorId(creatorId);
            return this;
        }

        public FinancialDocumentHeaderResponse build() {
            return financialDocumentHeaderResponse;
        }
    }
}
