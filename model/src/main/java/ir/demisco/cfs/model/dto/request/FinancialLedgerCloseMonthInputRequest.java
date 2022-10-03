package ir.demisco.cfs.model.dto.request;

public class FinancialLedgerCloseMonthInputRequest {
    private Long financialLedgerMonthId;
    private Long financialLedgerTypeId;
    private Long financialPeriodId;
    private Long financialLedgerPeriodId;
    private Long organizationId;

    public Long getFinancialLedgerMonthId() {
        return financialLedgerMonthId;
    }

    public void setFinancialLedgerMonthId(Long financialLedgerMonthId) {
        this.financialLedgerMonthId = financialLedgerMonthId;
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

    public Long getFinancialLedgerPeriodId() {
        return financialLedgerPeriodId;
    }

    public void setFinancialLedgerPeriodId(Long financialLedgerPeriodId) {
        this.financialLedgerPeriodId = financialLedgerPeriodId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}
