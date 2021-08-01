package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialDocumentDto {

    private Long id;
    private LocalDateTime documentDate;
    private String    description;
    private Long  financialDocumentStatusId;
    private String financialDocumentStatusCode;
    private String financialDocumentStatusName;
    private Long permanentDocumentNumber;
    private Boolean automaticFlag;
    private Long organizationId;
    private Long financialDocumentTypeId;
    private String financialDocumentTypeDescription;
    private Long financialPeriodId;
    private Long financialLedgerTypeId;
    private String financialLedgerTypeDescription;
    private Long financialDepartmentId;
    private String financialDepartmentCode;
    private String financialDepartmentName;
    private Long documentNumber;
    private LocalDateTime deletedDate;

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

    public String getFinancialDocumentStatusCode() {
        return financialDocumentStatusCode;
    }

    public void setFinancialDocumentStatusCode(String financialDocumentStatusCode) {
        this.financialDocumentStatusCode = financialDocumentStatusCode;
    }

    public String getFinancialDocumentStatusName() {
        return financialDocumentStatusName;
    }

    public void setFinancialDocumentStatusName(String financialDocumentStatusName) {
        this.financialDocumentStatusName = financialDocumentStatusName;
    }

    public Long getPermanentDocumentNumber() {
        return permanentDocumentNumber;
    }

    public void setPermanentDocumentNumber(Long permanentDocumentNumber) {
        this.permanentDocumentNumber = permanentDocumentNumber;
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

    public String getFinancialDocumentTypeDescription() {
        return financialDocumentTypeDescription;
    }

    public void setFinancialDocumentTypeDescription(String financialDocumentTypeDescription) {
        this.financialDocumentTypeDescription = financialDocumentTypeDescription;
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

    public String getFinancialDepartmentCode() {
        return financialDepartmentCode;
    }

    public void setFinancialDepartmentCode(String financialDepartmentCode) {
        this.financialDepartmentCode = financialDepartmentCode;
    }

    public String getFinancialDepartmentName() {
        return financialDepartmentName;
    }

    public void setFinancialDepartmentName(String financialDepartmentName) {
        this.financialDepartmentName = financialDepartmentName;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }



    public static Builder builder(){
      return new Builder();
    }

    public static final class Builder {
        private FinancialDocumentDto financialDocumentDto;

        private Builder() {
            financialDocumentDto = new FinancialDocumentDto();
        }

        public static Builder financialDocumentDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentDto.setId(id);
            return this;
        }

        public Builder documentDate(LocalDateTime documentDate) {
            financialDocumentDto.setDocumentDate(documentDate);
            return this;
        }

        public Builder description(String description) {
            financialDocumentDto.setDescription(description);
            return this;
        }

        public Builder financialDocumentStatusId(Long financialDocumentStatusId) {
            financialDocumentDto.setFinancialDocumentStatusId(financialDocumentStatusId);
            return this;
        }

        public Builder financialDocumentStatusCode(String financialDocumentStatusCode) {
            financialDocumentDto.setFinancialDocumentStatusCode(financialDocumentStatusCode);
            return this;
        }

        public Builder financialDocumentStatusName(String financialDocumentStatusName) {
            financialDocumentDto.setFinancialDocumentStatusName(financialDocumentStatusName);
            return this;
        }

        public Builder permanentDocumentNumber(Long permanentDocumentNumber) {
            financialDocumentDto.setPermanentDocumentNumber(permanentDocumentNumber);
            return this;
        }

        public Builder automaticFlag(Boolean automaticFlag) {
            financialDocumentDto.setAutomaticFlag(automaticFlag);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialDocumentDto.setOrganizationId(organizationId);
            return this;
        }

        public Builder financialDocumentTypeId(Long financialDocumentTypeId) {
            financialDocumentDto.setFinancialDocumentTypeId(financialDocumentTypeId);
            return this;
        }

        public Builder financialDocumentTypeDescription(String financialDocumentTypeDescription) {
            financialDocumentDto.setFinancialDocumentTypeDescription(financialDocumentTypeDescription);
            return this;
        }

        public Builder financialPeriodId(Long financialPeriodId) {
            financialDocumentDto.setFinancialPeriodId(financialPeriodId);
            return this;
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialDocumentDto.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder financialLedgerTypeDescription(String financialLedgerTypeDescription) {
            financialDocumentDto.setFinancialLedgerTypeDescription(financialLedgerTypeDescription);
            return this;
        }

        public Builder financialDepartmentId(Long financialDepartmentId) {
            financialDocumentDto.setFinancialDepartmentId(financialDepartmentId);
            return this;
        }

        public Builder financialDepartmentCode(String financialDepartmentCode) {
            financialDocumentDto.setFinancialDepartmentCode(financialDepartmentCode);
            return this;
        }

        public Builder financialDepartmentName(String financialDepartmentName) {
            financialDocumentDto.setFinancialDepartmentName(financialDepartmentName);
            return this;
        }

        public Builder documentNumber(Long documentNumber) {
            financialDocumentDto.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder deletedDate(LocalDateTime deletedDate) {
            financialDocumentDto.setDeletedDate(deletedDate);
            return this;
        }

        public FinancialDocumentDto build() {
            return financialDocumentDto;
        }
    }
}
