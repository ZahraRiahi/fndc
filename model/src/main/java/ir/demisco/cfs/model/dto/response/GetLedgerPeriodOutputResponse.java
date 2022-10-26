package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class GetLedgerPeriodOutputResponse {
    private Long financialLedgerPeriodId;
    private Long financialPeriodId;
    private Date periodStartDate;
    private Date periodEndDate;
    private String periodDescription;
    private Long openingDocNumber;
    private Date openingDocDate;
    private Long temporaryDocNumber;
    private Date temporaryDocDate;
    private Long permanentDocNumber;
    private Date permanentDocDate;
    private Long ledgerPeriodStatusId;
    private String ledgerPeriodStatusDesc;
    private Long tempClosedFlag;
    private Long hasOpeningFlag;
    private Long permanentCloseFlag;

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

    public Date getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodStartDate(Date periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    public Date getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(Date periodEndDate) {
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

    public Date getOpeningDocDate() {
        return openingDocDate;
    }

    public void setOpeningDocDate(Date openingDocDate) {
        this.openingDocDate = openingDocDate;
    }

    public Long getTemporaryDocNumber() {
        return temporaryDocNumber;
    }

    public void setTemporaryDocNumber(Long temporaryDocNumber) {
        this.temporaryDocNumber = temporaryDocNumber;
    }

    public Date getTemporaryDocDate() {
        return temporaryDocDate;
    }

    public void setTemporaryDocDate(Date temporaryDocDate) {
        this.temporaryDocDate = temporaryDocDate;
    }

    public Long getPermanentDocNumber() {
        return permanentDocNumber;
    }

    public void setPermanentDocNumber(Long permanentDocNumber) {
        this.permanentDocNumber = permanentDocNumber;
    }

    public Date getPermanentDocDate() {
        return permanentDocDate;
    }

    public void setPermanentDocDate(Date permanentDocDate) {
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

        public Builder periodStartDate(Date periodStartDate) {
            getLedgerPeriodOutputResponse.setPeriodStartDate(periodStartDate);
            return this;
        }

        public Builder periodEndDate(Date periodEndDate) {
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

        public Builder openingDocDate(Date openingDocDate) {
            getLedgerPeriodOutputResponse.setOpeningDocDate(openingDocDate);
            return this;
        }

        public Builder temporaryDocNumber(Long temporaryDocNumber) {
            getLedgerPeriodOutputResponse.setTemporaryDocNumber(temporaryDocNumber);
            return this;
        }

        public Builder temporaryDocDate(Date temporaryDocDate) {
            getLedgerPeriodOutputResponse.setTemporaryDocDate(temporaryDocDate);
            return this;
        }

        public Builder permanentDocNumber(Long permanentDocNumber) {
            getLedgerPeriodOutputResponse.setPermanentDocNumber(permanentDocNumber);
            return this;
        }

        public Builder permanentDocDate(Date permanentDocDate) {
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

        public GetLedgerPeriodOutputResponse build() {
            return getLedgerPeriodOutputResponse;
        }
    }
}
