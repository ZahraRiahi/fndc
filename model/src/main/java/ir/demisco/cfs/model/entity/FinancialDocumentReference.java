package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Column;
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
@Table(name = "FINANCIAL_DOCUMENT_REFRENCE" , schema = "fndc")
public class FinancialDocumentReference  extends AuditModel<Long> {

    private Long                   id;
    private FinancialDocumentItem  financialDocumentItem;
    private Long                   referenceNumber;
    private Date                   referenceDate;
    private String                 referenceDescription;
    private LocalDateTime          deletedDate;

    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_document_reference_generator", sequenceName = "sq_financial_document_refrence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_document_reference_generator")
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

    @Column(name = "REFRENCE_NUMBER")
    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @Column(name = "REFRENCE_DATE")
    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    @Column(name = "REFRENCE_DESCRIPTION")
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
