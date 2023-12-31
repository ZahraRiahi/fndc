package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

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

@Entity
@Table(name = "ACCOUNT_STRUCTURE_LEVEL", schema = "fnac")
public class AccountStructureLevel extends AuditModel<Long> {

    private Long id;
    private Long structureLevel;
    private String structureLevelCode;
    private FinancialAccountStructure financialAccountStructure;
    private FinancialAccount financialAccount;
    private LocalDateTime deletedDate;
    private FinancialAccount relatedAccount;

    @Override
    @Id
    @SequenceGenerator(schema = "fnac", name = "ACCOUNT_STRUCTURE_LEVEL_generator", sequenceName = "sq_ACCOUNT_STRUCTURE_LEVEL", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_STRUCTURE_LEVEL_generator")
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getStructureLevel() {
        return structureLevel;
    }

    public void setStructureLevel(Long structureLevel) {
        this.structureLevel = structureLevel;
    }

    public String getStructureLevelCode() {
        return structureLevelCode;
    }

    public void setStructureLevelCode(String structureLevelCode) {
        this.structureLevelCode = structureLevelCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_STRUCTURE_ID")
    public FinancialAccountStructure getFinancialAccountStructure() {
        return financialAccountStructure;
    }

    public void setFinancialAccountStructure(FinancialAccountStructure financialAccountStructure) {
        this.financialAccountStructure = financialAccountStructure;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_ACCOUNT_ID")
    public FinancialAccount getFinancialAccount() {
        return financialAccount;
    }

    public void setFinancialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RELATED_ACCOUNT_ID")
    public FinancialAccount getRelatedAccount() {
        return relatedAccount;
    }

    public void setRelatedAccount(FinancialAccount relatedAccount) {
        this.relatedAccount = relatedAccount;
    }
}
