package ir.demisco.cfs.model.dto.response;

import java.util.Date;
import java.util.List;

public class FinancialDocumentSaveDto {

    private Long financialDocumentId;
    private Date documentDate;
    private String permanentDocumentNumber;
    private String documentNumber;
    private Long financialDocumentTypeId;
    private String financialDocumentTypeDescription;
    private Long financialDocumentStatusId;
    private String financialDocumentStatusCode;
    private String financialDocumentStatusDescription;
    private String description;
    private Long organizationId;
    private Long financialPeriodId;
    private String financialPeriodDescription;
    private Long financialLedgerTypeId;
    private String financialLedgerTypeDescription;
    private Long departmentId;
    private String departmentName;
    private Boolean automaticFlag;
    private Long financialDepartmentId;
    private List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList;


    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<ResponseFinancialDocumentItemDto> getFinancialDocumentItemDtoList() {
        return financialDocumentItemDtoList;
    }

    public void setFinancialDocumentItemDtoList(List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList) {
        this.financialDocumentItemDtoList = financialDocumentItemDtoList;
    }

    public Long getFinancialDepartmentId() {
        return financialDepartmentId;
    }

    public void setFinancialDepartmentId(Long financialDepartmentId) {
        this.financialDepartmentId = financialDepartmentId;
    }

    public String getPermanentDocumentNumber() {
        return permanentDocumentNumber;
    }

    public void setPermanentDocumentNumber(String permanentDocumentNumber) {
        this.permanentDocumentNumber = permanentDocumentNumber;
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

    public Boolean getAutomaticFlag() {
        return automaticFlag;
    }

    public void setAutomaticFlag(Boolean automaticFlag) {
        this.automaticFlag = automaticFlag;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private FinancialDocumentSaveDto financialDocumentSaveDto;

        private Builder() {
            financialDocumentSaveDto = new FinancialDocumentSaveDto();
        }

        public static Builder financialDocumentSaveDto() {
            return new Builder();
        }

        public Builder financialDocumentId(Long financialDocumentId) {
            financialDocumentSaveDto.setFinancialDocumentId(financialDocumentId);
            return this;
        }

        public Builder documentDate(Date documentDate) {
            financialDocumentSaveDto.setDocumentDate(documentDate);
            return this;
        }

        public Builder permanentDocumentNumber(String permanentDocumentNumber) {
            financialDocumentSaveDto.setPermanentDocumentNumber(permanentDocumentNumber);
            return this;
        }

        public Builder documentNumber(String documentNumber) {
            financialDocumentSaveDto.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder financialDocumentTypeId(Long financialDocumentTypeId) {
            financialDocumentSaveDto.setFinancialDocumentTypeId(financialDocumentTypeId);
            return this;
        }

        public Builder financialDocumentTypeDescription(String financialDocumentTypeDescription) {
            financialDocumentSaveDto.setFinancialDocumentTypeDescription(financialDocumentTypeDescription);
            return this;
        }

        public Builder financialDocumentStatusId(Long financialDocumentStatusId) {
            financialDocumentSaveDto.setFinancialDocumentStatusId(financialDocumentStatusId);
            return this;
        }

        public Builder financialDocumentStatusCode(String financialDocumentStatusCode) {
            financialDocumentSaveDto.setFinancialDocumentStatusCode(financialDocumentStatusCode);
            return this;
        }
        public Builder financialDocumentStatusDescription(String financialDocumentStatusDescription) {
            financialDocumentSaveDto.setFinancialDocumentStatusDescription(financialDocumentStatusDescription);
            return this;
        }
        public Builder description(String description) {
            financialDocumentSaveDto.setDescription(description);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialDocumentSaveDto.setOrganizationId(organizationId);
            return this;
        }

        public Builder financialPeriodId(Long financialPeriodId) {
            financialDocumentSaveDto.setFinancialPeriodId(financialPeriodId);
            return this;
        }

        public Builder financialPeriodDescription(String financialPeriodDescription) {
            financialDocumentSaveDto.setFinancialPeriodDescription(financialPeriodDescription);
            return this;
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            financialDocumentSaveDto.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder financialLedgerTypeDescription(String financialLedgerTypeDescription) {
            financialDocumentSaveDto.setFinancialLedgerTypeDescription(financialLedgerTypeDescription);
            return this;
        }

        public Builder departmentId(Long departmentId) {
            financialDocumentSaveDto.setDepartmentId(departmentId);
            return this;
        }

        public Builder departmentName(String departmentName) {
            financialDocumentSaveDto.setDepartmentName(departmentName);
            return this;
        }

        public Builder automaticFlag(Boolean automaticFlag) {
            financialDocumentSaveDto.setAutomaticFlag(automaticFlag);
            return this;
        }

        public Builder financialDocumentItemDtoList(List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList) {
            financialDocumentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
            return this;
        }
        public Builder financialDepartmentId(Long financialDepartmentId) {
            financialDocumentSaveDto.setFinancialDepartmentId(financialDepartmentId);
            return this;
        }
        public FinancialDocumentSaveDto build() {
            return financialDocumentSaveDto;
        }
    }
}
