package ir.demisco.cfs.model.dto.response;

import ir.demisco.cloud.basic.model.entity.org.Organization;

import java.time.LocalDateTime;

public class LedgerNumberingTypeDto {
    private Long id;
    private Long financialNumberingTypeId;
    private String financialNumberingTypeDescription;
    private LocalDateTime financialNumberingTypeDeletedDate;
    private Long financialLedgerTypeId;
    private String financialLedgerTypeDescription;
    private Organization financialLedgerTypeOrganization;
    private LocalDateTime financialLedgerTypeDeletedDate;
    private boolean financialLedgerTypeActiveFlag;
    private LocalDateTime deletedDate;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDeleteDate() {
        return deletedDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deletedDate = deleteDate;
    }

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public Long getFinancialNumberingTypeId() {
        return financialNumberingTypeId;
    }

    public void setFinancialNumberingTypeId(Long financialNumberingTypeId) {
        this.financialNumberingTypeId = financialNumberingTypeId;
    }

    public String getFinancialNumberingTypeDescription() {
        return financialNumberingTypeDescription;
    }

    public void setFinancialNumberingTypeDescription(String financialNumberingTypeDescription) {
        this.financialNumberingTypeDescription = financialNumberingTypeDescription;
    }

    public LocalDateTime getFinancialNumberingTypeDeletedDate() {
        return financialNumberingTypeDeletedDate;
    }

    public void setFinancialNumberingTypeDeletedDate(LocalDateTime financialNumberingTypeDeletedDate) {
        this.financialNumberingTypeDeletedDate = financialNumberingTypeDeletedDate;
    }

    public String getFinancialLedgerTypeDescription() {
        return financialLedgerTypeDescription;
    }

    public void setFinancialLedgerTypeDescription(String financialLedgerTypeDescription) {
        this.financialLedgerTypeDescription = financialLedgerTypeDescription;
    }

    public Organization getFinancialLedgerTypeOrganization() {
        return financialLedgerTypeOrganization;
    }

    public void setFinancialLedgerTypeOrganization(Organization financialLedgerTypeOrganization) {
        this.financialLedgerTypeOrganization = financialLedgerTypeOrganization;
    }

    public LocalDateTime getFinancialLedgerTypeDeletedDate() {
        return financialLedgerTypeDeletedDate;
    }

    public void setFinancialLedgerTypeDeletedDate(LocalDateTime financialLedgerTypeDeletedDate) {
        this.financialLedgerTypeDeletedDate = financialLedgerTypeDeletedDate;
    }

    public boolean isFinancialLedgerTypeActiveFlag() {
        return financialLedgerTypeActiveFlag;
    }

    public void setFinancialLedgerTypeActiveFlag(boolean financialLedgerTypeActiveFlag) {
        this.financialLedgerTypeActiveFlag = financialLedgerTypeActiveFlag;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private LedgerNumberingTypeDto ledgerNumberingTypeDto;

        private Builder() {
            ledgerNumberingTypeDto = new LedgerNumberingTypeDto();
        }

        public static Builder aLedgerNumberingTypeDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            ledgerNumberingTypeDto.setId(id);
            return this;
        }

        public Builder financialNumberingTypeId(Long financialNumberingTypeId) {
            ledgerNumberingTypeDto.setFinancialNumberingTypeId(financialNumberingTypeId);
            return this;
        }

        public Builder financialNumberingTypeDescription(String financialNumberingTypeDescription) {
            ledgerNumberingTypeDto.setFinancialNumberingTypeDescription(financialNumberingTypeDescription);
            return this;
        }

        public Builder financialNumberingTypeDeletedDate(LocalDateTime financialNumberingTypeDeletedDate) {
            ledgerNumberingTypeDto.setFinancialNumberingTypeDeletedDate(financialNumberingTypeDeletedDate);
            return this;
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            ledgerNumberingTypeDto.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder financialLedgerTypeDescription(String financialLedgerTypeDescription) {
            ledgerNumberingTypeDto.setFinancialLedgerTypeDescription(financialLedgerTypeDescription);
            return this;
        }

        public Builder financialLedgerTypeOrganization(Organization financialLedgerTypeOrganization) {
            ledgerNumberingTypeDto.setFinancialLedgerTypeOrganization(financialLedgerTypeOrganization);
            return this;
        }

        public Builder financialLedgerTypeDeletedDate(LocalDateTime financialLedgerTypeDeletedDate) {
            ledgerNumberingTypeDto.setFinancialLedgerTypeDeletedDate(financialLedgerTypeDeletedDate);
            return this;
        }

        public Builder financialLedgerTypeActiveFlag(boolean financialLedgerTypeActiveFlag) {
            ledgerNumberingTypeDto.setFinancialLedgerTypeActiveFlag(financialLedgerTypeActiveFlag);
            return this;
        }

        public Builder deleteDate(LocalDateTime deleteDate) {
            ledgerNumberingTypeDto.setDeleteDate(deleteDate);
            return this;
        }

        public LedgerNumberingTypeDto build() {
            return ledgerNumberingTypeDto;
        }
    }
}
