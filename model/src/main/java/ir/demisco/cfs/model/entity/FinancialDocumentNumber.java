package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_DOCUMENT_NUMBER" , schema = "fndc")
public class FinancialDocumentNumber extends AuditModel<Long> {

    private FinancialNumberingType financialNumberingType;
    private FinancialDocument financialDocument;
    private Long  documentNumber;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_document_number_generator", sequenceName = "sq_financial_document_number")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_document_number_generator")
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
    @JoinColumn(name = "FINANCIAL_DOCUMENT_ID")
    public FinancialDocument getFinancialDocument() {
        return financialDocument;
    }

    public void setFinancialDocument(FinancialDocument financialDocument) {
        this.financialDocument = financialDocument;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
