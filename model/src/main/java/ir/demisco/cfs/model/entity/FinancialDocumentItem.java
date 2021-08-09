package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_DOCUMENT_ITEM" , schema = "fndc")
public class FinancialDocumentItem extends AuditModel<Long> {

    private FinancialDocument  financialDocument;
    private Long sequenceNumber;
    private Double debitAmount;
    private Double creditAmount;
    private String description;
    private FinancialAccount financialAccount;
    private CentricAccount centricAccountId1;
    private CentricAccount centricAccountId2;
    private CentricAccount centricAccountId3;
    private CentricAccount centricAccountId4;
    private CentricAccount centricAccountId5;
    private CentricAccount centricAccountId6;
    private LocalDateTime deletedDate;


    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_document_item_generator", sequenceName = "Sq_Financial_Document_Item;")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_document_item_generator")
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_DOCUMENT_ID")
    public FinancialDocument getFinancialDocument() {
        return financialDocument;
    }

    public void setFinancialDocument(FinancialDocument financialDocument) {
        this.financialDocument = financialDocument;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_ACCOUNT_ID")
    public FinancialAccount getFinancialAccount() {
        return financialAccount;
    }

    public void setFinancialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CENTRIC_ACCOUNT_ID_1")
    public CentricAccount getCentricAccountId1() {
        return centricAccountId1;
    }

    public void setCentricAccountId1(CentricAccount centricAccountId1) {
        this.centricAccountId1 = centricAccountId1;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CENTRIC_ACCOUNT_ID_2")
    public CentricAccount getCentricAccountId2() {
        return centricAccountId2;
    }

    public void setCentricAccountId2(CentricAccount centricAccountId2) {
        this.centricAccountId2 = centricAccountId2;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CENTRIC_ACCOUNT_ID_3")
    public CentricAccount getCentricAccountId3() {
        return centricAccountId3;
    }

    public void setCentricAccountId3(CentricAccount centricAccountId3) {
        this.centricAccountId3 = centricAccountId3;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CENTRIC_ACCOUNT_ID_4")
    public CentricAccount getCentricAccountId4() {
        return centricAccountId4;
    }

    public void setCentricAccountId4(CentricAccount centricAccountId4) {
        this.centricAccountId4 = centricAccountId4;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CENTRIC_ACCOUNT_ID_5")
    public CentricAccount getCentricAccountId5() {
        return centricAccountId5;
    }

    public void setCentricAccountId5(CentricAccount centricAccountId5) {
        this.centricAccountId5 = centricAccountId5;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CENTRIC_ACCOUNT_ID_6")
    public CentricAccount getCentricAccountId6() {
        return centricAccountId6;
    }

    public void setCentricAccountId6(CentricAccount centricAccountId6) {
        this.centricAccountId6 = centricAccountId6;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
