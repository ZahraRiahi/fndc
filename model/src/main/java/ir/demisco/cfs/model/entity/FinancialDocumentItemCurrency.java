package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_DOCUMENT_ITEM_CURRENCY" , schema = "fndc")
public class FinancialDocumentItemCurrency extends AuditModel<Long> {

    private Long                   id;
    private FinancialDocumentItem  financialDocumentItem;
    private Double                   foreignDebitAmount;
    private Double                   foreignCreditAmount;
    private Long                   exchangeRate;
    private MoneyType              moneyType;
    private MoneyPricingReference  moneyPricingReference;
    private LocalDateTime DeletedDate;

    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_document_item_reference_generator", sequenceName = "sq_financial_document_item_currency")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_document_item_reference_generator")
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

    public Double getForeignDebitAmount() {
        return foreignDebitAmount;
    }

    public void setForeignDebitAmount(Double foreignDebitAmount) {
        this.foreignDebitAmount = foreignDebitAmount;
    }

    public Double getForeignCreditAmount() {
        return foreignCreditAmount;
    }

    public void setForeignCreditAmount(Double foreignCreditAmount) {
        this.foreignCreditAmount = foreignCreditAmount;
    }

    public Long getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Long exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MONEY_TYPE_ID")
    public MoneyType getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(MoneyType moneyType) {
        this.moneyType = moneyType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MONEY_PRICING_REFRENCE_ID")
    public MoneyPricingReference getMoneyPricingReference() {
        return moneyPricingReference;
    }

    public void setMoneyPricingReference(MoneyPricingReference moneyPricingReference) {
        this.moneyPricingReference = moneyPricingReference;
    }

    public LocalDateTime getDeletedDate() {
        return DeletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        DeletedDate = deletedDate;
    }
}
