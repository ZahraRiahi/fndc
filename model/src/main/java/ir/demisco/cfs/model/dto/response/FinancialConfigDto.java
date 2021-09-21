package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialConfigDto {
    private Long id;
    private Long organizationId;
    private Long financialDepartmentId;
    private Long userId;
    private Long financialDocumentTypeId;
    private String documentDescription;
    private Long financialLedgerTypeId;
    private Long financialPeriodId;
    private String financialDepartmentCode;
    private String financialDepartmentName;
    private LocalDateTime financialPeriodStartDate;
    private LocalDateTime financialPeriodEndDate;
    private String financialPeriodDescription;
    private String financialDocumentTypeDescription;
    private String financialLedgerTypeDescription;

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

    public Long getFinancialDepartmentId() {
        return financialDepartmentId;
    }

    public void setFinancialDepartmentId(Long financialDepartmentId) {
        this.financialDepartmentId = financialDepartmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFinancialDocumentTypeId() {
        return financialDocumentTypeId;
    }

    public void setFinancialDocumentTypeId(Long financialDocumentTypeId) {
        this.financialDocumentTypeId = financialDocumentTypeId;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public Long getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(Long financialPeriodId) {
        this.financialPeriodId = financialPeriodId;
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

    public LocalDateTime getFinancialPeriodStartDate() {
        return financialPeriodStartDate;
    }

    public void setFinancialPeriodStartDate(LocalDateTime financialPeriodStartDate) {
        this.financialPeriodStartDate = financialPeriodStartDate;
    }

    public LocalDateTime getFinancialPeriodEndDate() {
        return financialPeriodEndDate;
    }

    public void setFinancialPeriodEndDate(LocalDateTime financialPeriodEndDate) {
        this.financialPeriodEndDate = financialPeriodEndDate;
    }

    public String getFinancialPeriodDescription() {
        return financialPeriodDescription;
    }

    public void setFinancialPeriodDescription(String financialPeriodDescription) {
        this.financialPeriodDescription = financialPeriodDescription;
    }

    public String getFinancialDocumentTypeDescription() {
        return financialDocumentTypeDescription;
    }

    public void setFinancialDocumentTypeDescription(String financialDocumentTypeDescription) {
        this.financialDocumentTypeDescription = financialDocumentTypeDescription;
    }

    public String getFinancialLedgerTypeDescription() {
        return financialLedgerTypeDescription;
    }

    public void setFinancialLedgerTypeDescription(String financialLedgerTypeDescription) {
        this.financialLedgerTypeDescription = financialLedgerTypeDescription;
    }

    public static FinancialConfigDto.Builder builder() {
        return new FinancialConfigDto.Builder();
    }

    public static final class Builder {
        private FinancialConfigDto financialConfigDto;

        private Builder() {
            financialConfigDto = new FinancialConfigDto();
        }

        public static Builder aFinancialConfigDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialConfigDto.setId(id);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialConfigDto.setOrganizationId(organizationId);
            return this;
        }

        public Builder financialDepartmentId(Long financialDepartmentId) {
            financialConfigDto.setFinancialDepartmentId(financialDepartmentId);
            return this;
        }

        public Builder userId(Long userId) {
            financialConfigDto.setUserId(userId);
            return this;
        }

        public Builder financialDocumentTypeId(Long financialDocumentTypeId) {
            financialConfigDto.setFinancialDocumentTypeId(financialDocumentTypeId);
            return this;
        }

        public Builder documentDescription(String documentDescription) {
            financialConfigDto.setDocumentDescription(documentDescription);
            return this;
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialConfigDto.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder financialPeriodId(Long financialPeriodId) {
            financialConfigDto.setFinancialPeriodId(financialPeriodId);
            return this;
        }

        public Builder financialDepartmentCode(String financialDepartmentCode) {
            financialConfigDto.setFinancialDepartmentCode(financialDepartmentCode);
            return this;
        }

        public Builder financialDepartmentName(String financialDepartmentName) {
            financialConfigDto.setFinancialDepartmentName(financialDepartmentName);
            return this;
        }

        public Builder financialPeriodStartDate(LocalDateTime financialPeriodStartDate) {
            financialConfigDto.setFinancialPeriodStartDate(financialPeriodStartDate);
            return this;
        }

        public Builder financialPeriodEndDate(LocalDateTime financialPeriodEndDate) {
            financialConfigDto.setFinancialPeriodEndDate(financialPeriodEndDate);
            return this;
        }

        public Builder financialPeriodDescription(String financialPeriodDescription) {
            financialConfigDto.setFinancialPeriodDescription(financialPeriodDescription);
            return this;
        }

        public Builder financialDocumentTypeDescription(String financialDocumentTypeDescription) {
            financialConfigDto.setFinancialDocumentTypeDescription(financialDocumentTypeDescription);
            return this;
        }

        public Builder financialLedgerTypeDescription(String financialLedgerTypeDescription) {
            financialConfigDto.setFinancialLedgerTypeDescription(financialLedgerTypeDescription);
            return this;
        }

        public FinancialConfigDto build() {
            return financialConfigDto;
        }
    }
}
