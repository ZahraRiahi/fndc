package ir.demisco.cfs.model.dto.request;

public class FinancialDepartmentLedgerRequest {

    private Long financialDepartmentLedgerId;
    private Long financialDepartmentId;
    private Long financialLedgerTypeId;

    public Long getFinancialDepartmentLedgerId() {
        return financialDepartmentLedgerId;
    }

    public void setFinancialDepartmentLedgerId(Long financialDepartmentLedgerId) {
        this.financialDepartmentLedgerId = financialDepartmentLedgerId;
    }

    public Long getFinancialDepartmentId() {
        return financialDepartmentId;
    }

    public void setFinancialDepartmentId(Long financialDepartmentId) {
        this.financialDepartmentId = financialDepartmentId;
    }

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

}
