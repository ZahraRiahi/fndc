
package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Department;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_DEPARTMENT_LEDGER", schema = "fndc")
public class FinancialDepartmentLedger extends AuditModel<Long> {
    private Long id;
    private Department department;
    private FinancialLedgerType financialLedgerType;
    private LocalDateTime deletedDate;

    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_department_ledger_generator", sequenceName = "sq_financial_department_ledger")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_department_ledger_generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_LEDGER_TYPE_ID")
    public FinancialLedgerType getFinancialLedgerType() {
        return financialLedgerType;
    }

    public void setFinancialLedgerType(FinancialLedgerType financialLedgerTypeId) {
        this.financialLedgerType = financialLedgerTypeId;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}

