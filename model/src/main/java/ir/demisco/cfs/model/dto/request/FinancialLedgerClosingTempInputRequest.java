package ir.demisco.cfs.model.dto.request;

public class FinancialLedgerClosingTempInputRequest {
    private Long financialLedgerTypeId;
    private Long financialPeriodId;
    private Long financialLedgerPeriodId;
    private Long organizationId;
    private Long financialDepartmentId;
    private Long departmentId;
    private String financialPeriodDes;

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

    public Long getFinancialDepartmentId() {
        return financialDepartmentId;
    }

    public void setFinancialDepartmentId(Long financialDepartmentId) {
        this.financialDepartmentId = financialDepartmentId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getFinancialPeriodDes() {
        return financialPeriodDes;
    }

    public void setFinancialPeriodDes(String financialPeriodDes) {
        this.financialPeriodDes = financialPeriodDes;
    }
}
