package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialDocumentHeaderOutputResponse {
    private Long id;
    private LocalDateTime  documentDate;
    private String description;
    private Long financialDocumentStatusId;
    private Boolean automaticFlag;
    private Long organizationId;
    private Long financialDocumentTypeId;
    private Long financialPeriodId;
    private Long financialLedgerTypeId;
    private Long financialDepartmentId;
    private Long creatorId;
    private Long lastModifierId;
    private String permanentDocumentNumber;
    private String documentNumber;
    private Long departmentId;
    private Long financialSystemId;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getFinancialDocumentTypeId() {
        return financialDocumentTypeId;
    }

    public void setFinancialDocumentTypeId(Long financialDocumentTypeId) {
        this.financialDocumentTypeId = financialDocumentTypeId;
    }

    public Long getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(Long financialPeriodId) {
        this.financialPeriodId = financialPeriodId;
    }

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public Long getFinancialDepartmentId() {
        return financialDepartmentId;
    }

    public void setFinancialDepartmentId(Long financialDepartmentId) {
        this.financialDepartmentId = financialDepartmentId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(Long lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public String getPermanentDocumentNumber() {
        return permanentDocumentNumber;
    }

    public void setPermanentDocumentNumber(String permanentDocumentNumber) {
        this.permanentDocumentNumber = permanentDocumentNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getFinancialSystemId() {
        return financialSystemId;
    }

    public void setFinancialSystemId(Long financialSystemId) {
        this.financialSystemId = financialSystemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static FinancialDocumentHeaderOutputResponse.Builder builder() {
        return new FinancialDocumentHeaderOutputResponse.Builder();
    }
    public static final class Builder {
        private FinancialDocumentHeaderOutputResponse financialDocumentHeaderOutputResponse;

        private Builder() {
            financialDocumentHeaderOutputResponse = new FinancialDocumentHeaderOutputResponse();
        }

        public static Builder financialDocumentHeaderOutputResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentHeaderOutputResponse.setId(id);
            return this;
        }

        public Builder documentDate(LocalDateTime documentDate) {
            financialDocumentHeaderOutputResponse.setDocumentDate(documentDate);
            return this;
        }

        public Builder description(String description) {
            financialDocumentHeaderOutputResponse.setDescription(description);
            return this;
        }

        public Builder financialDocumentStatusId(Long financialDocumentStatusId) {
            financialDocumentHeaderOutputResponse.setFinancialDocumentStatusId(financialDocumentStatusId);
            return this;
        }

        public Builder automaticFlag(Boolean automaticFlag) {
            financialDocumentHeaderOutputResponse.setAutomaticFlag(automaticFlag);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialDocumentHeaderOutputResponse.setOrganizationId(organizationId);
            return this;
        }

        public Builder financialDocumentTypeId(Long financialDocumentTypeId) {
            financialDocumentHeaderOutputResponse.setFinancialDocumentTypeId(financialDocumentTypeId);
            return this;
        }

        public Builder financialPeriodId(Long financialPeriodId) {
            financialDocumentHeaderOutputResponse.setFinancialPeriodId(financialPeriodId);
            return this;
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialDocumentHeaderOutputResponse.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder financialDepartmentId(Long financialDepartmentId) {
            financialDocumentHeaderOutputResponse.setFinancialDepartmentId(financialDepartmentId);
            return this;
        }

        public Builder creatorId(Long creatorId) {
            financialDocumentHeaderOutputResponse.setCreatorId(creatorId);
            return this;
        }

        public Builder lastModifierId(Long lastModifierId) {
            financialDocumentHeaderOutputResponse.setLastModifierId(lastModifierId);
            return this;
        }

        public Builder permanentDocumentNumber(String permanentDocumentNumber) {
            financialDocumentHeaderOutputResponse.setPermanentDocumentNumber(permanentDocumentNumber);
            return this;
        }

        public Builder documentNumber(String documentNumber) {
            financialDocumentHeaderOutputResponse.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder departmentId(Long departmentId) {
            financialDocumentHeaderOutputResponse.setDepartmentId(departmentId);
            return this;
        }

        public Builder financialSystemId(Long financialSystemId) {
            financialDocumentHeaderOutputResponse.setFinancialSystemId(financialSystemId);
            return this;
        }
        public Builder userId(Long userId) {
            financialDocumentHeaderOutputResponse.setUserId(userId);
            return this;
        }
        public FinancialDocumentHeaderOutputResponse build() {
            return financialDocumentHeaderOutputResponse;
        }
    }
}
