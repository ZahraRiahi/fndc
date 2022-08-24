package ir.demisco.cfs.model.dto.request;

public class FinancialSecurityFilterRequest {
    private Long userId;
    private Long departmentId;
    private Long financialDepartmentId;
    private Long financialLedgerId;
    private Long financialPeriodId;
    private Long documentTypeId;
    private Long subjectId;
    private String activityCode;
    private Boolean inputFromConfigFlag;
    private Long creatorUserId;
    private Long organizationId;
    private Long financialLedgerTypeId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getFinancialDepartmentId() {
        return financialDepartmentId;
    }

    public void setFinancialDepartmentId(Long financialDepartmentId) {
        this.financialDepartmentId = financialDepartmentId;
    }

    public Long getFinancialLedgerId() {
        return financialLedgerId;
    }

    public void setFinancialLedgerId(Long financialLedgerId) {
        this.financialLedgerId = financialLedgerId;
    }

    public Long getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(Long financialPeriodId) {
        this.financialPeriodId = financialPeriodId;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public Boolean getInputFromConfigFlag() {
        return inputFromConfigFlag;
    }

    public void setInputFromConfigFlag(Boolean inputFromConfigFlag) {
        this.inputFromConfigFlag = inputFromConfigFlag;
    }

    public Long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(Long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getFinancialLedgerTypeId() {
        return financialLedgerTypeId;
    }

    public void setFinancialLedgerTypeId(Long financialLedgerTypeId) {
        this.financialLedgerTypeId = financialLedgerTypeId;
    }
}
