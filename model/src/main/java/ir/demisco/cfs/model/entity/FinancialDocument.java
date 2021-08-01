package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Organization;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_DOCUMENT" , schema = "fndc")
public class FinancialDocument  extends AuditModel<Long> {

    private LocalDateTime documentDate;
    private String    description;
    private FinancialDocumentStatus  financialDocumentStatus;
    private Long permanentDocumentNumber;
    private Boolean automaticFlag;
    private Organization organization;
    private FinancialDocumentType financialDocumentType;
    private Long financialPeriodId;
    private FinancialLedgerType financialLedgerType;
    private FinancialDepartment financialDepartment;
    private Long documentNumber;
    private LocalDateTime DeletedDate;


    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_document_generator", sequenceName = "sq_financial_document")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_document_generator")
    public Long getId() {
        return super.getId();
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_DOCUMENT_STATUS_ID")
    public FinancialDocumentStatus getFinancialDocumentStatus() {
        return financialDocumentStatus;
    }

    public void setFinancialDocumentStatus(FinancialDocumentStatus financialDocumentStatus) {
        this.financialDocumentStatus = financialDocumentStatus;
    }

    public Long getPermanentDocumentNumber() {
        return permanentDocumentNumber;
    }

    public void setPermanentDocumentNumber(Long permanentDocumentNumber) {
        this.permanentDocumentNumber = permanentDocumentNumber;
    }

    public Boolean getAutomaticFlag() {
        return automaticFlag;
    }

    public void setAutomaticFlag(Boolean automaticFlag) {
        this.automaticFlag = automaticFlag;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_DOCUMENT_TYPE_ID")
    public FinancialDocumentType getFinancialDocumentType() {
        return financialDocumentType;
    }

    public void setFinancialDocumentType(FinancialDocumentType financialDocumentType) {
        this.financialDocumentType = financialDocumentType;
    }

    public Long getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(Long financialPeriodId) {
        this.financialPeriodId = financialPeriodId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_LEDGER_TYPE_ID")
    public FinancialLedgerType getFinancialLedgerType() {
        return financialLedgerType;
    }

    public void setFinancialLedgerType(FinancialLedgerType financialLedgerType) {
        this.financialLedgerType = financialLedgerType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_DEPARTMENT_ID")
    public FinancialDepartment getFinancialDepartment() {
        return financialDepartment;
    }

    public void setFinancialDepartment(FinancialDepartment financialDepartment) {
        this.financialDepartment = financialDepartment;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDateTime getDeletedDate() {
        return DeletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        DeletedDate = deletedDate;
    }
}
