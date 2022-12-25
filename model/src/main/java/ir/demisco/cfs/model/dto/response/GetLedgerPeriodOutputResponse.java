package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class GetLedgerPeriodOutputResponse {
    private Long financialLedgerPeriodId;
    private Long financialPeriodId;
    private LocalDateTime periodStartDate;
    private LocalDateTime periodEndDate;
    private String periodDescription;
    private Long openingDocNumber;
    private LocalDateTime openingDocDate;
    private Long openingDocId;
    private Long temporaryDocNumber;
    private LocalDateTime temporaryDocDate;
    private Long permanentDocNumber;
    private Long permanentDocId;
    private LocalDateTime permanentDocDate;
    private Long ledgerPeriodStatusId;
    private String ledgerPeriodStatusDesc;
    private Long tempClosedFlag;
    private Long hasOpeningFlag;
    private Long permanentCloseFlag;
    private Long temporaryDocId;

    public Long getFinancialLedgerPeriodId() {
        return financialLedgerPeriodId;
    }

    public void setFinancialLedgerPeriodId(Long financialLedgerPeriodId) {
        this.financialLedgerPeriodId = financialLedgerPeriodId;
    }

    public Long getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(Long financialPeriodId) {
        this.financialPeriodId = financialPeriodId;
    }

    public LocalDateTime getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodStartDate(LocalDateTime periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    public LocalDateTime getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(LocalDateTime periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public String getPeriodDescription() {
        return periodDescription;
    }

    public void setPeriodDescription(String periodDescription) {
        this.periodDescription = periodDescription;
    }

    public Long getOpeningDocNumber() {
        return openingDocNumber;
    }

    public void setOpeningDocNumber(Long openingDocNumber) {
        this.openingDocNumber = openingDocNumber;
    }

    public LocalDateTime getOpeningDocDate() {
        return openingDocDate;
    }

    public void setOpeningDocDate(LocalDateTime openingDocDate) {
        this.openingDocDate = openingDocDate;
    }

    public Long getTemporaryDocNumber() {
        return temporaryDocNumber;
    }

    public void setTemporaryDocNumber(Long temporaryDocNumber) {
        this.temporaryDocNumber = temporaryDocNumber;
    }

    public LocalDateTime getTemporaryDocDate() {
        return temporaryDocDate;
    }

    public void setTemporaryDocDate(LocalDateTime temporaryDocDate) {
        this.temporaryDocDate = temporaryDocDate;
    }

    public Long getPermanentDocNumber() {
        return permanentDocNumber;
    }

    public void setPermanentDocNumber(Long permanentDocNumber) {
        this.permanentDocNumber = permanentDocNumber;
    }

    public LocalDateTime getPermanentDocDate() {
        return permanentDocDate;
    }

    public void setPermanentDocDate(LocalDateTime permanentDocDate) {
        this.permanentDocDate = permanentDocDate;
    }

    public Long getLedgerPeriodStatusId() {
        return ledgerPeriodStatusId;
    }

    public void setLedgerPeriodStatusId(Long ledgerPeriodStatusId) {
        this.ledgerPeriodStatusId = ledgerPeriodStatusId;
    }

    public String getLedgerPeriodStatusDesc() {
        return ledgerPeriodStatusDesc;
    }

    public void setLedgerPeriodStatusDesc(String ledgerPeriodStatusDesc) {
        this.ledgerPeriodStatusDesc = ledgerPeriodStatusDesc;
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

    public Long getPermanentDocId() {
        return permanentDocId;
    }

    public void setPermanentDocId(Long permanentDocId) {
        this.permanentDocId = permanentDocId;
    }

    public Long getOpeningDocId() {
        return openingDocId;
    }

    public void setOpeningDocId(Long openingDocId) {
        this.openingDocId = openingDocId;
    }

    public Long getTemporaryDocId() {
        return temporaryDocId;
    }

    public void setTemporaryDocId(Long temporaryDocId) {
        this.temporaryDocId = temporaryDocId;
    }
    public static GetLedgerPeriodOutputResponse.Builder builder() {
        return new GetLedgerPeriodOutputResponse.Builder();
    }

    public static final class Builder {
        private GetLedgerPeriodOutputResponse getLedgerPeriodOutputResponse;

        private Builder() {
            getLedgerPeriodOutputResponse = new GetLedgerPeriodOutputResponse();
        }

        public static Builder aGetLedgerPeriodOutputResponse() {
            return new Builder();
        }

        public Builder financialLedgerPeriodId(Long financialLedgerPeriodId) {
            getLedgerPeriodOutputResponse.setFinancialLedgerPeriodId(financialLedgerPeriodId);
            return this;
        }

        public Builder financialPeriodId(Long financialPeriodId) {
            getLedgerPeriodOutputResponse.setFinancialPeriodId(financialPeriodId);
            return this;
        }

        public Builder periodStartDate(LocalDateTime periodStartDate) {
            getLedgerPeriodOutputResponse.setPeriodStartDate(periodStartDate);
            return this;
        }

        public Builder periodEndDate(LocalDateTime periodEndDate) {
            getLedgerPeriodOutputResponse.setPeriodEndDate(periodEndDate);
            return this;
        }

        public Builder periodDescription(String periodDescription) {
            getLedgerPeriodOutputResponse.setPeriodDescription(periodDescription);
            return this;
        }

        public Builder openingDocNumber(Long openingDocNumber) {
            getLedgerPeriodOutputResponse.setOpeningDocNumber(openingDocNumber);
            return this;
        }

        public Builder openingDocDate(LocalDateTime openingDocDate) {
            getLedgerPeriodOutputResponse.setOpeningDocDate(openingDocDate);
            return this;
        }

        public Builder temporaryDocNumber(Long temporaryDocNumber) {
            getLedgerPeriodOutputResponse.setTemporaryDocNumber(temporaryDocNumber);
            return this;
        }

        public Builder temporaryDocDate(LocalDateTime temporaryDocDate) {
            getLedgerPeriodOutputResponse.setTemporaryDocDate(temporaryDocDate);
            return this;
        }

        public Builder permanentDocNumber(Long permanentDocNumber) {
            getLedgerPeriodOutputResponse.setPermanentDocNumber(permanentDocNumber);
            return this;
        }

        public Builder permanentDocDate(LocalDateTime permanentDocDate) {
            getLedgerPeriodOutputResponse.setPermanentDocDate(permanentDocDate);
            return this;
        }

        public Builder ledgerPeriodStatusId(Long ledgerPeriodStatusId) {
            getLedgerPeriodOutputResponse.setLedgerPeriodStatusId(ledgerPeriodStatusId);
            return this;
        }

        public Builder ledgerPeriodStatusDesc(String ledgerPeriodStatusDesc) {
            getLedgerPeriodOutputResponse.setLedgerPeriodStatusDesc(ledgerPeriodStatusDesc);
            return this;
        }

        public Builder tempClosedFlag(Long tempClosedFlag) {
            getLedgerPeriodOutputResponse.setTempClosedFlag(tempClosedFlag);
            return this;
        }

        public Builder hasOpeningFlag(Long hasOpeningFlag) {
            getLedgerPeriodOutputResponse.setHasOpeningFlag(hasOpeningFlag);
            return this;
        }

        public Builder permanentCloseFlag(Long permanentCloseFlag) {
            getLedgerPeriodOutputResponse.setPermanentCloseFlag(permanentCloseFlag);
            return this;
        }

        public Builder permanentDocId(Long permanentDocId) {
            getLedgerPeriodOutputResponse.setPermanentDocId(permanentDocId);
            return this;
        }

        public Builder openingDocId(Long openingDocId) {
            getLedgerPeriodOutputResponse.setOpeningDocId(openingDocId);
            return this;
        }

        public Builder temporaryDocId(Long temporaryDocId) {
            getLedgerPeriodOutputResponse.setTemporaryDocId(temporaryDocId);
            return this;
        }
        public GetLedgerPeriodOutputResponse build() {
            return getLedgerPeriodOutputResponse;
        }
    }
}
