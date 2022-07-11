package ir.demisco.cfs.model.dto.request;

public class FinancialLedgerPeriodRequest {
    private Long financialPeriodId;
    private Long financialLedgerTypeId;
    private Long finLedgerPeriodStatId;
    private Long id;

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

    public Long getFinLedgerPeriodStatId() {
        return finLedgerPeriodStatId;
    }

    public void setFinLedgerPeriodStatId(Long finLedgerPeriodStatId) {
        this.finLedgerPeriodStatId = finLedgerPeriodStatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
