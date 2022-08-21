package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Department;
import ir.demisco.cloud.basic.model.entity.org.Organization;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "FINANCIAL_DOCUMENT", schema = "fndc")
public class FinancialDocument extends AuditModel<Long> {

    private Long id;
    private Date documentDate;
    private String description;
    private FinancialDocumentStatus financialDocumentStatus;
    private String permanentDocumentNumber;
    private Boolean automaticFlag;
    private Organization organization;
    private FinancialDocumentType financialDocumentType;
    private FinancialPeriod financialPeriod;
    private FinancialLedgerType financialLedgerType;
    private FinancialDepartment financialDepartment;
    private String documentNumber;
    private Department department;
    private LocalDateTime deletedDate;


    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_document_generator", sequenceName = "sq_financial_document")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_document_generator")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
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

    public String getPermanentDocumentNumber() {
        return permanentDocumentNumber;
    }

    public void setPermanentDocumentNumber(String permanentDocumentNumber) {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_PERIOD_ID")
    public FinancialPeriod getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(FinancialPeriod financialPeriod) {
        this.financialPeriod = financialPeriod;
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

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
