package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "FINANCIAL_DOCUMENT_REFRENCE" , schema = "fndc")
public class FinancialDocumentRefrence  extends AuditModel<Long> {

    private Long                   id;
    private FinancialDocumentItem  financialDocumentItem;
    private Long                   referenceNumber;
    private Date                   referenceDate;
    private String                 referenceDescription;
    private LocalDateTime          deletedDate;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_DOCUMENT_ITEM_ID")
    public FinancialDocumentItem getFinancialDocumentItem() {
        return financialDocumentItem;
    }

    public void setFinancialDocumentItem(FinancialDocumentItem financialDocumentItem) {
        this.financialDocumentItem = financialDocumentItem;
    }

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getReferenceDescription() {
        return referenceDescription;
    }

    public void setReferenceDescription(String referenceDescription) {
        this.referenceDescription = referenceDescription;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
