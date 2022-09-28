package ir.demisco.cfs.model.dto.request;

public class GetLedgerPeriodMonthStatusRequest {
    private Long financialLedgerMonthId;
    private Long financialLedgerPeriodId;
    private Long nextPrevMonth;
    private Long checkOtherPeriods;

    public Long getFinancialLedgerMonthId() {
        return financialLedgerMonthId;
    }

    public void setFinancialLedgerMonthId(Long financialLedgerMonthId) {
        this.financialLedgerMonthId = financialLedgerMonthId;
    }

    public Long getFinancialLedgerPeriodId() {
        return financialLedgerPeriodId;
    }

    public void setFinancialLedgerPeriodId(Long financialLedgerPeriodId) {
        this.financialLedgerPeriodId = financialLedgerPeriodId;
    }

    public Long getNextPrevMonth() {
        return nextPrevMonth;
    }

    public void setNextPrevMonth(Long nextPrevMonth) {
        this.nextPrevMonth = nextPrevMonth;
    }

    public Long getCheckOtherPeriods() {
        return checkOtherPeriods;
    }

    public void setCheckOtherPeriods(Long checkOtherPeriods) {
        this.checkOtherPeriods = checkOtherPeriods;
    }
}
