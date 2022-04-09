package ir.demisco.cfs.model.dto.response;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class FinancialDocumentItemCurrencyDto {

    private Long financialDocumentItemCurrencyId;
    private Long financialDocumentItemId;
    private Long exchangeRate;
    private Long moneyTypeId;
    private String moneyTypeDescription;
    private Long moneyPricingReferenceId;
    private String moneyPricingReferenceDescription;
    private BigDecimal foreignDebitAmount;
    private BigDecimal foreignCreditAmount;

    public Long getFinancialDocumentItemCurrencyId() {
        return financialDocumentItemCurrencyId;
    }

    public void setFinancialDocumentItemCurrencyId(Long financialDocumentItemCurrencyId) {
        this.financialDocumentItemCurrencyId = financialDocumentItemCurrencyId;
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

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private FinancialDocumentItemCurrencyDto financialDocumentItemCurrencyDto;

        private Builder() {
            financialDocumentItemCurrencyDto = new FinancialDocumentItemCurrencyDto();
        }

        public static Builder aFinancialDocumentItemCurrencyDto() {
            return new Builder();
        }

        public Builder financialDocumentItemCurrencyId(Long financialDocumentItemCurrencyId) {
            financialDocumentItemCurrencyDto.setFinancialDocumentItemCurrencyId(financialDocumentItemCurrencyId);
            return this;
        }

        public Builder financialDocumentItemId(Long financialDocumentItemId) {
            financialDocumentItemCurrencyDto.setFinancialDocumentItemId(financialDocumentItemId);
            return this;
        }

        public Builder exchangeRate(Long exchangeRate) {
            financialDocumentItemCurrencyDto.setExchangeRate(exchangeRate);
            return this;
        }

        public Builder moneyTypeId(Long moneyTypeId) {
            financialDocumentItemCurrencyDto.setMoneyTypeId(moneyTypeId);
            return this;
        }

        public Builder moneyTypeDescription(String moneyTypeDescription) {
            financialDocumentItemCurrencyDto.setMoneyTypeDescription(moneyTypeDescription);
            return this;
        }

        public Builder moneyPricingReferenceId(Long moneyPricingReferenceId) {
            financialDocumentItemCurrencyDto.setMoneyPricingReferenceId(moneyPricingReferenceId);
            return this;
        }

        public Builder moneyPricingReferenceDescription(String moneyPricingReferenceDescription) {
            financialDocumentItemCurrencyDto.setMoneyPricingReferenceDescription(moneyPricingReferenceDescription);
            return this;
        }

        public Builder foreignCreditAmount(BigDecimal foreignCreditAmountStr) {
            financialDocumentItemCurrencyDto.setForeignDebitAmount(foreignCreditAmountStr);
            return this;
        }

        public Builder foreignDebitAmount(BigDecimal foreignDebitAmountStr) {
            financialDocumentItemCurrencyDto.setForeignCreditAmount(foreignDebitAmountStr);
            return this;
        }

        public FinancialDocumentItemCurrencyDto build() {
            return financialDocumentItemCurrencyDto;
        }
    }

    class MyDoubleDesirializer extends JsonSerializer<Double> {

        @Override
        public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            // TODO Auto-generated method stub

            BigDecimal d = new BigDecimal(value);
            gen.writeNumber(d.toPlainString());
        }

    }
}
