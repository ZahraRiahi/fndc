package ir.demisco.cfs.model.dto.request;

import java.util.List;

public class InsertLedgerPeriodInputRequest {
    private Long financialLedgerTypeId;
    private List<Long> financialPeriodId;
    private Long id;
    private Long organizationId;

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public List<Long> getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(List<Long> financialPeriodId) {
        this.financialPeriodId = financialPeriodId;
    }

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
}
