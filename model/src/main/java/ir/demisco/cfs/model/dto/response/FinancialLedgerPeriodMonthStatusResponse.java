package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialLedgerPeriodMonthStatusResponse {
    private Long monthNumber;
    private Date startDate;
    private Date endDate;

    public Long getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Long monthNumber) {
        this.monthNumber = monthNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public static FinancialLedgerPeriodMonthStatusResponse.Builder builder() {
        return new FinancialLedgerPeriodMonthStatusResponse.Builder();
    }
    public static final class Builder {
        private FinancialLedgerPeriodMonthStatusResponse financialLedgerPeriodMonthStatusResponse;

        private Builder() {
            financialLedgerPeriodMonthStatusResponse = new FinancialLedgerPeriodMonthStatusResponse();
        }

        public static Builder financialLedgerPeriodMonthStatusResponse() {
            return new Builder();
        }

        public Builder monthNumber(Long monthNumber) {
            financialLedgerPeriodMonthStatusResponse.setMonthNumber(monthNumber);
            return this;
        }

        public Builder startDate(Date startDate) {
            financialLedgerPeriodMonthStatusResponse.setStartDate(startDate);
            return this;
        }

        public Builder endDate(Date endDate) {
            financialLedgerPeriodMonthStatusResponse.setEndDate(endDate);
            return this;
        }

        public FinancialLedgerPeriodMonthStatusResponse build() {
            return financialLedgerPeriodMonthStatusResponse;
        }
    }
}
