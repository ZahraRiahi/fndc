package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Department;
import ir.demisco.cloud.basic.model.entity.org.Organization;
import ir.demisco.cloud.basic.model.entity.sec.User;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_config", schema = "fndc")
public class FinancialConfig extends AuditModel<Long> {

    private Organization organization;
    private FinancialDepartment financialDepartment;
    private User user;
    private FinancialDocumentType financialDocumentType;
    private String documentDescription;
    private FinancialLedgerType financialLedgerType;
    private FinancialPeriod financialPeriod;
    private LocalDateTime deletedDate;
    private Department department;


    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_config_generator", sequenceName = "sq_financial_config")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_config_generator")
    public Long getId() {
        return super.getId();
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
    @JoinColumn(name = "FINANCIAL_DEPARTMENT_ID")
    public FinancialDepartment getFinancialDepartment() {
        return financialDepartment;
    }

    public void setFinancialDepartment(FinancialDepartment financialDepartment) {
        this.financialDepartment = financialDepartment;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_DOCUMENT_TYPE_ID")
    public FinancialDocumentType getFinancialDocumentType() {
        return financialDocumentType;
    }

    public void setFinancialDocumentType(FinancialDocumentType financialDocumentType) {
        this.financialDocumentType = financialDocumentType;
    }

    @Column(name = "DOCUMENT_DESCRIPTION")
    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
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
    @JoinColumn(name = "FINANCIAL_PERIOD_ID")
    public FinancialPeriod getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(FinancialPeriod financialPeriod) {
        this.financialPeriod = financialPeriod;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
