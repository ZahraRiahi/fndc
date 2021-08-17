package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_DOCUMENT_ITEM_CURRENCY" , schema = "fndc")
public class FinancialDocumentItemCurrency extends AuditModel<Long> {

    private Long                   id;
    private FinancialDocumentItem  financialDocumentItem;
    private Long                   foreignAmount;
    private Long                   exchangeRate;
    private MoneyType              moneyType;
    private MoneyPricingReference  moneyPricingReference;
    private LocalDateTime DeletedDate;

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

    public Long getForeignAmount() {
        return foreignAmount;
    }

    public void setForeignAmount(Long foreignAmount) {
        this.foreignAmount = foreignAmount;
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
