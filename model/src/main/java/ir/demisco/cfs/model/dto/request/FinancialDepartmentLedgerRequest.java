package ir.demisco.cfs.model.dto.request;

public class FinancialDepartmentLedgerRequest {

    private Long financialDepartmentLedgerId;
    private Long departmentId;
    private Long financialLedgerTypeId;
    private Long financialDepartment;

    public Long getFinancialDepartmentLedgerId() {
        return financialDepartmentLedgerId;
    }

    public void setFinancialDepartmentLedgerId(Long financialDepartmentLedgerId) {
        this.financialDepartmentLedgerId = financialDepartmentLedgerId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }

    public Long getFinancialDepartment() {
        return financialDepartment;
    }

    public void setFinancialDepartment(Long financialDepartment) {
        this.financialDepartment = financialDepartment;
    }
}
