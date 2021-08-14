package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import org.bouncycastle.asn1.cms.TimeStampedData;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LEDGER_NUMBERING_TYPE", schema = "fndc")
public class LedgerNumberingType extends AuditModel<Long> {

    private FinancialNumberingType financialNumberingType;
    private FinancialLedgerType financialLedgerType;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "ledger_numbering_type_generator", sequenceName = "sq_ledger_numbering_type")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ledger_numbering_type_generator")
    public Long getId() {
        return super.getId();
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_NUMBERING_TYPE_ID")
    public FinancialNumberingType getFinancialNumberingType() {
        return financialNumberingType;
    }

    public void setFinancialNumberingType(FinancialNumberingType financialNumberingType) {
        this.financialNumberingType = financialNumberingType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_LEDGER_TYPE_ID")
    public FinancialLedgerType getFinancialLedgerType() {
        return financialLedgerType;
    }

    public void setFinancialLedgerType(FinancialLedgerType financialLedgerType) {
        this.financialLedgerType = financialLedgerType;
    }
    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
