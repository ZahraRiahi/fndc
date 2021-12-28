package ir.demisco.cfs.model.dto.response;

import java.math.BigDecimal;

public class FinancialDocumentItemCurrencyOutPutModel {
    private Long id;
    private Long financialDocumentItemId;
    private Long exchangeRate;
    private Long moneyTypeId;
    private String moneyTypeDescription;
    private Long moneyPricingReferenceId;
    private String moneyPricingReferenceDescription;
    private BigDecimal foreignDebitAmount;
    private BigDecimal foreignCreditAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFinancialDocumentItemId() {
        return financialDocumentItemId;
    }

    public void setFinancialDocumentItemId(Long financialDocumentItemId) {
        this.financialDocumentItemId = financialDocumentItemId;
    }

    public Long getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Long exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Long getMoneyTypeId() {
        return moneyTypeId;
    }

    public void setMoneyTypeId(Long moneyTypeId) {
        this.moneyTypeId = moneyTypeId;
    }

    public String getMoneyTypeDescription() {
        return moneyTypeDescription;
    }

    public void setMoneyTypeDescription(String moneyTypeDescription) {
        this.moneyTypeDescription = moneyTypeDescription;
    }

    public Long getMoneyPricingReferenceId() {
        return moneyPricingReferenceId;
    }

    public void setMoneyPricingReferenceId(Long moneyPricingReferenceId) {
        this.moneyPricingReferenceId = moneyPricingReferenceId;
    }

    public String getMoneyPricingReferenceDescription() {
        return moneyPricingReferenceDescription;
    }

    public void setMoneyPricingReferenceDescription(String moneyPricingReferenceDescription) {
        this.moneyPricingReferenceDescription = moneyPricingReferenceDescription;
    }

    public BigDecimal getForeignDebitAmount() {
        return foreignDebitAmount;
    }

    public void setForeignDebitAmount(BigDecimal foreignDebitAmount) {
        this.foreignDebitAmount = foreignDebitAmount;
    }

    public BigDecimal getForeignCreditAmount() {
        return foreignCreditAmount;
    }

    public void setForeignCreditAmount(BigDecimal foreignCreditAmount) {
        this.foreignCreditAmount = foreignCreditAmount;
    }
    public static FinancialDocumentItemCurrencyOutPutModel.Builder builder() {
        return new FinancialDocumentItemCurrencyOutPutModel.Builder();
    }
    public static final class Builder {
        private FinancialDocumentItemCurrencyOutPutModel financialDocumentItemCurrencyOutPutModel;

        private Builder() {
            financialDocumentItemCurrencyOutPutModel = new FinancialDocumentItemCurrencyOutPutModel();
        }

        public static Builder financialDocumentItemCurrencyOutPutModel() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentItemCurrencyOutPutModel.setId(id);
            return this;
        }

        public Builder financialDocumentItemId(Long financialDocumentItemId) {
            financialDocumentItemCurrencyOutPutModel.setFinancialDocumentItemId(financialDocumentItemId);
            return this;
        }

        public Builder exchangeRate(Long exchangeRate) {
            financialDocumentItemCurrencyOutPutModel.setExchangeRate(exchangeRate);
            return this;
        }

        public Builder moneyTypeId(Long moneyTypeId) {
            financialDocumentItemCurrencyOutPutModel.setMoneyTypeId(moneyTypeId);
            return this;
        }

        public Builder moneyTypeDescription(String moneyTypeDescription) {
            financialDocumentItemCurrencyOutPutModel.setMoneyTypeDescription(moneyTypeDescription);
            return this;
        }

        public Builder moneyPricingReferenceId(Long moneyPricingReferenceId) {
            financialDocumentItemCurrencyOutPutModel.setMoneyPricingReferenceId(moneyPricingReferenceId);
            return this;
        }

        public Builder moneyPricingReferenceDescription(String moneyPricingReferenceDescription) {
            financialDocumentItemCurrencyOutPutModel.setMoneyPricingReferenceDescription(moneyPricingReferenceDescription);
            return this;
        }

        public Builder foreignDebitAmount(BigDecimal foreignDebitAmount) {
            financialDocumentItemCurrencyOutPutModel.setForeignDebitAmount(foreignDebitAmount);
            return this;
        }

        public Builder foreignCreditAmount(BigDecimal foreignCreditAmount) {
            financialDocumentItemCurrencyOutPutModel.setForeignCreditAmount(foreignCreditAmount);
            return this;
        }

        public FinancialDocumentItemCurrencyOutPutModel build() {
            return financialDocumentItemCurrencyOutPutModel;
        }
    }
}
