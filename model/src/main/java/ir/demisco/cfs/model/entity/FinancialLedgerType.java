package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Organization;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_LEDGER_TYPE", schema = "fndc")
public class FinancialLedgerType extends AuditModel<Long> {

    private String description;
    private FinancialCodingType financialCodingType;
    private Organization organization;
    private LocalDateTime DeletedDate;
    private boolean activeFlag;
    private String code;


    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_ledger_type_generator", sequenceName = "Sq_Financial_Ledger_Type")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_ledger_type_generator")
    public Long getId() {
        return super.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_CODING_TYPE_ID")
    public FinancialCodingType getFinancialCodingType() {
        return financialCodingType;
    }

    public void setFinancialCodingType(FinancialCodingType financialCodingType) {
        this.financialCodingType = financialCodingType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public LocalDateTime getDeletedDate() {
        return DeletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        DeletedDate = deletedDate;
    }

    @Column(name = "ACTIVE_FLAG")
    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Column(name = "CODE")
    public String getCode() { return code; }

    public void setCode(String code) { this.code = code;
    }
}
