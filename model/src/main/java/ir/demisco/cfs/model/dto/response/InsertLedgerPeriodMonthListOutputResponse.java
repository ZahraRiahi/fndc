package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class InsertLedgerPeriodMonthListOutputResponse {
    private Long minDocTmpNumber;
    private Long maxDocTmpNumber;
    private Long financialPeriodId;
    private Long financialLedgerTypeId;
    private String monthDescription;
    private LocalDateTime monthStartDate;
    private LocalDateTime endDateMonthEndDate;
    private Long financialLedgerMonthId;
    private Long monthStatusId;
    private String monthStatusCode;
    private String monthStatusDescription;
    private Long financialDocumentOpeningId;
    private Long financialDocumentTemproryId;
    private Long financialDocumentPermanentId;
    private Long tempClosedFlag;
    private Long hasOpeningFlag;
    private Long permanentCloseFlag;
    private Long minDocPrmNUmber;
    private Long maxDocPrmNumber;

    public Long getMinDocTmpNumber() {
        return minDocTmpNumber;
    }

    public void setMinDocTmpNumber(Long minDocTmpNumber) {
        this.minDocTmpNumber = minDocTmpNumber;
    }

    public Long getMaxDocTmpNumber() {
        return maxDocTmpNumber;
    }

    public void setMaxDocTmpNumber(Long maxDocTmpNumber) {
        this.maxDocTmpNumber = maxDocTmpNumber;
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

    public String getMonthDescription() {
        return monthDescription;
    }

    public void setMonthDescription(String monthDescription) {
        this.monthDescription = monthDescription;
    }

    public LocalDateTime getMonthStartDate() {
        return monthStartDate;
    }

    public void setMonthStartDate(LocalDateTime monthStartDate) {
        this.monthStartDate = monthStartDate;
    }

    public LocalDateTime getEndDateMonthEndDate() {
        return endDateMonthEndDate;
    }

    public void setEndDateMonthEndDate(LocalDateTime endDateMonthEndDate) {
        this.endDateMonthEndDate = endDateMonthEndDate;
    }

    public Long getFinancialLedgerMonthId() {
        return financialLedgerMonthId;
    }

    public void setFinancialLedgerMonthId(Long financialLedgerMonthId) {
        this.financialLedgerMonthId = financialLedgerMonthId;
    }

    public Long getMonthStatusId() {
        return monthStatusId;
    }

    public void setMonthStatusId(Long monthStatusId) {
        this.monthStatusId = monthStatusId;
    }

    public String getMonthStatusCode() {
        return monthStatusCode;
    }

    public void setMonthStatusCode(String monthStatusCode) {
        this.monthStatusCode = monthStatusCode;
    }

    public String getMonthStatusDescription() {
        return monthStatusDescription;
    }

    public void setMonthStatusDescription(String monthStatusDescription) {
        this.monthStatusDescription = monthStatusDescription;
    }

    public Long getFinancialDocumentOpeningId() {
        return financialDocumentOpeningId;
    }

    public void setFinancialDocumentOpeningId(Long financialDocumentOpeningId) {
        this.financialDocumentOpeningId = financialDocumentOpeningId;
    }

    public Long getFinancialDocumentTemproryId() {
        return financialDocumentTemproryId;
    }

    public void setFinancialDocumentTemproryId(Long financialDocumentTemproryId) {
        this.financialDocumentTemproryId = financialDocumentTemproryId;
    }

    public Long getFinancialDocumentPermanentId() {
        return financialDocumentPermanentId;
    }

    public void setFinancialDocumentPermanentId(Long financialDocumentPermanentId) {
        this.financialDocumentPermanentId = financialDocumentPermanentId;
    }

    public Long getTempClosedFlag() {
        return tempClosedFlag;
    }

    public void setTempClosedFlag(Long tempClosedFlag) {
        this.tempClosedFlag = tempClosedFlag;
    }

    public Long getHasOpeningFlag() {
        return hasOpeningFlag;
    }

    public void setHasOpeningFlag(Long hasOpeningFlag) {
        this.hasOpeningFlag = hasOpeningFlag;
    }

    public Long getPermanentCloseFlag() {
        return permanentCloseFlag;
    }

    public void setPermanentCloseFlag(Long permanentCloseFlag) {
        this.permanentCloseFlag = permanentCloseFlag;
    }

    public Long getMinDocPrmNUmber() {
        return minDocPrmNUmber;
    }

    public void setMinDocPrmNUmber(Long minDocPrmNUmber) {
        this.minDocPrmNUmber = minDocPrmNUmber;
    }

    public Long getMaxDocPrmNumber() {
        return maxDocPrmNumber;
    }

    public void setMaxDocPrmNumber(Long maxDocPrmNumber) {
        this.maxDocPrmNumber = maxDocPrmNumber;
    }
    public static InsertLedgerPeriodMonthListOutputResponse.Builder builder() {
        return new InsertLedgerPeriodMonthListOutputResponse.Builder();
    }
    public static final class Builder {
        private InsertLedgerPeriodMonthListOutputResponse insertLedgerPeriodMonthListOutputResponse;

        private Builder() {
            insertLedgerPeriodMonthListOutputResponse = new InsertLedgerPeriodMonthListOutputResponse();
        }

        public static Builder insertLedgerPeriodMonthListOutputResponse() {
            return new Builder();
        }

        public Builder minDocTmpNumber(Long minDocTmpNumber) {
            insertLedgerPeriodMonthListOutputResponse.setMinDocTmpNumber(minDocTmpNumber);
            return this;
        }

        public Builder maxDocTmpNumber(Long maxDocTmpNumber) {
            insertLedgerPeriodMonthListOutputResponse.setMaxDocTmpNumber(maxDocTmpNumber);
            return this;
        }

        public Builder financialPeriodId(Long financialPeriodId) {
            insertLedgerPeriodMonthListOutputResponse.setFinancialPeriodId(financialPeriodId);
            return this;
        }

        public Builder financialLedgerTypeId(Long financialLedgerTypeId) {
            insertLedgerPeriodMonthListOutputResponse.setFinancialLedgerTypeId(financialLedgerTypeId);
            return this;
        }

        public Builder monthDescription(String monthDescription) {
            insertLedgerPeriodMonthListOutputResponse.setMonthDescription(monthDescription);
            return this;
        }

        public Builder monthStartDate(LocalDateTime monthStartDate) {
            insertLedgerPeriodMonthListOutputResponse.setMonthStartDate(monthStartDate);
            return this;
        }

        public Builder endDateMonthEndDate(LocalDateTime endDateMonthEndDate) {
            insertLedgerPeriodMonthListOutputResponse.setEndDateMonthEndDate(endDateMonthEndDate);
            return this;
        }

        public Builder financialLedgerMonthId(Long financialLedgerMonthId) {
            insertLedgerPeriodMonthListOutputResponse.setFinancialLedgerMonthId(financialLedgerMonthId);
            return this;
        }

        public Builder monthStatusId(Long monthStatusId) {
            insertLedgerPeriodMonthListOutputResponse.setMonthStatusId(monthStatusId);
            return this;
        }

        public Builder monthStatusCode(String monthStatusCode) {
            insertLedgerPeriodMonthListOutputResponse.setMonthStatusCode(monthStatusCode);
            return this;
        }

        public Builder monthStatusDescription(String monthStatusDescription) {
            insertLedgerPeriodMonthListOutputResponse.setMonthStatusDescription(monthStatusDescription);
            return this;
        }

        public Builder financialDocumentOpeningId(Long financialDocumentOpeningId) {
            insertLedgerPeriodMonthListOutputResponse.setFinancialDocumentOpeningId(financialDocumentOpeningId);
            return this;
        }

        public Builder financialDocumentTemproryId(Long financialDocumentTemproryId) {
            insertLedgerPeriodMonthListOutputResponse.setFinancialDocumentTemproryId(financialDocumentTemproryId);
            return this;
        }

        public Builder financialDocumentPermanentId(Long financialDocumentPermanentId) {
            insertLedgerPeriodMonthListOutputResponse.setFinancialDocumentPermanentId(financialDocumentPermanentId);
            return this;
        }

        public Builder tempClosedFlag(Long tempClosedFlag) {
            insertLedgerPeriodMonthListOutputResponse.setTempClosedFlag(tempClosedFlag);
            return this;
        }

        public Builder hasOpeningFlag(Long hasOpeningFlag) {
            insertLedgerPeriodMonthListOutputResponse.setHasOpeningFlag(hasOpeningFlag);
            return this;
        }

        public Builder permanentCloseFlag(Long permanentCloseFlag) {
            insertLedgerPeriodMonthListOutputResponse.setPermanentCloseFlag(permanentCloseFlag);
            return this;
        }

        public Builder minDocPrmNUmber(Long minDocPrmNUmber) {
            insertLedgerPeriodMonthListOutputResponse.setMinDocPrmNUmber(minDocPrmNUmber);
            return this;
        }

        public Builder maxDocPrmNumber(Long maxDocPrmNumber) {
            insertLedgerPeriodMonthListOutputResponse.setMaxDocPrmNumber(maxDocPrmNumber);
            return this;
        }

        public InsertLedgerPeriodMonthListOutputResponse build() {
            return insertLedgerPeriodMonthListOutputResponse;
        }
    }
}
