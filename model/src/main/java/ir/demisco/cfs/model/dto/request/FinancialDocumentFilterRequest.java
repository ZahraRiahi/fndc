package ir.demisco.cfs.model.dto.request;

import java.time.LocalDateTime;
import java.util.Map;

public class FinancialDocumentFilterRequest {
    private Long organizationId;
    private Long financialDepartmentId;
    private Object financialDepartment;
    private Object department;
    private Object financialLedgerType;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long departmentId;
    private Long financialLedgerTypeId;
    Map<String, Object> paramMap;
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

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
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

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Object getFinancialDepartment() {
        return financialDepartment;
    }

    public void setFinancialDepartment(Object financialDepartment) {
        this.financialDepartment = financialDepartment;
    }

    public Object getDepartment() {
        return department;
    }

    public void setDepartment(Object department) {
        this.department = department;
    }

    public Object getFinancialLedgerType() {
        return financialLedgerType;
    }

    public void setFinancialLedgerType(Object financialLedgerType) {
        this.financialLedgerType = financialLedgerType;
    }
}
