package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ResponseFinancialDocumentDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long financialNumberingTypeId;
    private Long fromNumberId;
    private Long toNumberId;
    private List<Long> financialDocumentStatusDtoListId;
    private String description;
    private Long fromAccountId;
    private Long toAccountId;
    private Long fromAccountCode;
    private Long toAccountCode;
    private Long centricAccountId;
    private Long centricAccountTypeId;
    private Long documentUserId;
    private Long priceTypeId;
    private Long fromPrice;
    private Long toPrice;
    private Double tolerance;
    private Long financialDocumentTypeId;
    private String activityCode;
    private Long departmentId;
    private Long ledgerTypeId;
    private Object fromNumber;
    private Object toNumber;
    private Long fromPriceAmount;
    private Long toPriceAmount;
    private Object priceType;
    Map<String, Object> paramMap;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getFinancialNumberingTypeId() {
        return financialNumberingTypeId;
    }

    public void setFinancialNumberingTypeId(Long financialNumberingTypeId) {
        this.financialNumberingTypeId = financialNumberingTypeId;
    }

    public Long getFromNumberId() {
        return fromNumberId;
    }

    public void setFromNumberId(Long fromNumberId) {
        this.fromNumberId = fromNumberId;
    }

    public Long getToNumberId() {
        return toNumberId;
    }

    public void setToNumberId(Long toNumberId) {
        this.toNumberId = toNumberId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Long getFromAccountCode() {
        return fromAccountCode;
    }

    public void setFromAccountCode(Long fromAccountCode) {
        this.fromAccountCode = fromAccountCode;
    }

    public Long getToAccountCode() {
        return toAccountCode;
    }

    public void setToAccountCode(Long toAccountCode) {
        this.toAccountCode = toAccountCode;
    }

    public Long getCentricAccountId() {
        return centricAccountId;
    }

    public void setCentricAccountId(Long centricAccountId) {
        this.centricAccountId = centricAccountId;
    }

    public Long getCentricAccountTypeId() {
        return centricAccountTypeId;
    }

    public void setCentricAccountTypeId(Long centricAccountTypeId) {
        this.centricAccountTypeId = centricAccountTypeId;
    }

    public Long getDocumentUserId() {
        return documentUserId;
    }

    public void setDocumentUserId(Long documentUserId) {
        this.documentUserId = documentUserId;
    }

    public Long getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(Long priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public Long getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(Long fromPrice) {
        this.fromPrice = fromPrice;
    }

    public Long getToPrice() {
        return toPrice;
    }

    public void setToPrice(Long toPrice) {
        this.toPrice = toPrice;
    }

    public Double getTolerance() {
        return tolerance;
    }

    public void setTolerance(Double tolerance) {
        this.tolerance = tolerance;
    }

    public Long getFinancialDocumentTypeId() {
        return financialDocumentTypeId;
    }

    public void setFinancialDocumentTypeId(Long financialDocumentTypeId) {
        this.financialDocumentTypeId = financialDocumentTypeId;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getLedgerTypeId() {
        return ledgerTypeId;
    }

    public void setLedgerTypeId(Long ledgerTypeId) {
        this.ledgerTypeId = ledgerTypeId;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Object getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(Object fromNumber) {
        this.fromNumber = fromNumber;
    }

    public Object getToNumber() {
        return toNumber;
    }

    public void setToNumber(Object toNumber) {
        this.toNumber = toNumber;
    }

    public Long getFromPriceAmount() {
        return fromPriceAmount;
    }

    public void setFromPriceAmount(Long fromPriceAmount) {
        this.fromPriceAmount = fromPriceAmount;
    }

    public Long getToPriceAmount() {
        return toPriceAmount;
    }

    public void setToPriceAmount(Long toPriceAmount) {
        this.toPriceAmount = toPriceAmount;
    }

    public Object getPriceType() {
        return priceType;
    }

    public void setPriceType(Object priceType) {
        this.priceType = priceType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Long> getFinancialDocumentStatusDtoListId() {
        return financialDocumentStatusDtoListId;
    }

    public void setFinancialDocumentStatusDtoListId(List<Long> financialDocumentStatusDtoListId) {
        this.financialDocumentStatusDtoListId = financialDocumentStatusDtoListId;
    }


    public static final class Builder {
        private ResponseFinancialDocumentDto responseFinancialDocumentDto;

        private Builder() {
            responseFinancialDocumentDto = new ResponseFinancialDocumentDto();
        }

        public static Builder responseFinancialDocumentDto() {
            return new Builder();
        }

        public Builder startDate(LocalDateTime startDate) {
            responseFinancialDocumentDto.setStartDate(startDate);
            return this;
        }

        public Builder endDate(LocalDateTime endDate) {
            responseFinancialDocumentDto.setEndDate(endDate);
            return this;
        }

        public Builder financialNumberingTypeId(Long financialNumberingTypeId) {
            responseFinancialDocumentDto.setFinancialNumberingTypeId(financialNumberingTypeId);
            return this;
        }

        public Builder fromNumberId(Long fromNumberId) {
            responseFinancialDocumentDto.setFromNumberId(fromNumberId);
            return this;
        }

        public Builder toNumberId(Long toNumberId) {
            responseFinancialDocumentDto.setToNumberId(toNumberId);
            return this;
        }

        public Builder description(String description) {
            responseFinancialDocumentDto.setDescription(description);
            return this;
        }

        public Builder fromAccountId(Long fromAccountId) {
            responseFinancialDocumentDto.setFromAccountId(fromAccountId);
            return this;
        }

        public Builder toAccountId(Long toAccountId) {
            responseFinancialDocumentDto.setToAccountId(toAccountId);
            return this;
        }

        public Builder fromAccountCode(Long fromAccountCode) {
            responseFinancialDocumentDto.setFromAccountCode(fromAccountCode);
            return this;
        }

        public Builder toAccountCode(Long toAccountCode) {
            responseFinancialDocumentDto.setToAccountCode(toAccountCode);
            return this;
        }

        public Builder centricAccountId(Long centricAccountId) {
            responseFinancialDocumentDto.setCentricAccountId(centricAccountId);
            return this;
        }

        public Builder centricAccountTypeId(Long centricAccountTypeId) {
            responseFinancialDocumentDto.setCentricAccountTypeId(centricAccountTypeId);
            return this;
        }

        public Builder documentUserId(Long documentUserId) {
            responseFinancialDocumentDto.setDocumentUserId(documentUserId);
            return this;
        }

        public Builder priceTypeId(Long priceTypeId) {
            responseFinancialDocumentDto.setPriceTypeId(priceTypeId);
            return this;
        }

        public Builder fromPrice(Long fromPrice) {
            responseFinancialDocumentDto.setFromPrice(fromPrice);
            return this;
        }

        public Builder toPrice(Long toPrice) {
            responseFinancialDocumentDto.setToPrice(toPrice);
            return this;
        }

        public Builder tolerance(Double tolerance) {
            responseFinancialDocumentDto.setTolerance(tolerance);
            return this;
        }

        public Builder financialDocumentTypeId(Long financialDocumentTypeId) {
            responseFinancialDocumentDto.setFinancialDocumentTypeId(financialDocumentTypeId);
            return this;
        }

        public Builder activityCode(String activityCode) {
            responseFinancialDocumentDto.setActivityCode(activityCode);
            return this;
        }

        public Builder departmentId(Long departmentId) {
            responseFinancialDocumentDto.setDepartmentId(departmentId);
            return this;
        }

        public Builder ledgerTypeId(Long ledgerTypeId) {
            responseFinancialDocumentDto.setLedgerTypeId(ledgerTypeId);
            return this;
        }

        public Builder paramMap(Map<String, Object> paramMap) {
            responseFinancialDocumentDto.setParamMap(paramMap);
            return this;
        }

        public Builder fromNumber(Object fromNumber) {
            responseFinancialDocumentDto.setFromNumber(fromNumber);
            return this;
        }
        public Builder toNumber(Object toNumber) {
            responseFinancialDocumentDto.setToNumber(toNumber);
            return this;
        }
        public Builder fromPriceAmount(Long fromPriceAmount) {
            responseFinancialDocumentDto.setFromPriceAmount(fromPriceAmount);
            return this;
        }

        public Builder toPriceAmount(Long toPriceAmount) {
            responseFinancialDocumentDto.setToPriceAmount(toPriceAmount);
            return this;
        }
        public Builder priceType(Object priceType) {
            responseFinancialDocumentDto.setPriceType(priceType);
            return this;
        }
        public ResponseFinancialDocumentDto build() {
            return responseFinancialDocumentDto;
        }
    }
}
