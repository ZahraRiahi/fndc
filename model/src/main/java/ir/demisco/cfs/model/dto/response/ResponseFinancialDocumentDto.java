package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ResponseFinancialDocumentDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long financialNumberingTypeId;
    private Long fromNumber;
    private Long toNumber;
    private List<Long> financialDocumentStatusDtoListId;
    private String description;
    private Long FromAccountId;
    private Long toAccountId;
    private String FromAccountCode;
    private String toAccountCode;
    private Long centricAccountId;
    private Long centricAccountTypeId;
    private Long userId;
    private Long priceTypeId;
    private Long fromPrice;
    private Long toPrice;
    private Double tolerance;
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

    public Long getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(Long fromNumber) {
        this.fromNumber = fromNumber;
    }

    public Long getToNumber() {
        return toNumber;
    }

    public void setToNumber(Long toNumber) {
        this.toNumber = toNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFromAccountId() {
        return FromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        FromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public String getFromAccountCode() {
        return FromAccountCode;
    }

    public void setFromAccountCode(String fromAccountCode) {
        FromAccountCode = fromAccountCode;
    }

    public String getToAccountCode() {
        return toAccountCode;
    }

    public void setToAccountCode(String toAccountCode) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public static Builder  builder(){
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

        public Builder fromNumber(Long fromNumber) {
            responseFinancialDocumentDto.setFromNumber(fromNumber);
            return this;
        }

        public Builder toNumber(Long toNumber) {
            responseFinancialDocumentDto.setToNumber(toNumber);
            return this;
        }

        public Builder description(String description) {
            responseFinancialDocumentDto.setDescription(description);
            return this;
        }

        public Builder FromAccountId(Long FromAccountId) {
            responseFinancialDocumentDto.setFromAccountId(FromAccountId);
            return this;
        }

        public Builder toAccountId(Long toAccountId) {
            responseFinancialDocumentDto.setToAccountId(toAccountId);
            return this;
        }

        public Builder FromAccountCode(String FromAccountCode) {
            responseFinancialDocumentDto.setFromAccountCode(FromAccountCode);
            return this;
        }

        public Builder toAccountCode(String toAccountCode) {
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

        public Builder userId(Long userId) {
            responseFinancialDocumentDto.setUserId(userId);
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

        public Builder paramMap(Map<String, Object> paramMap) {
            responseFinancialDocumentDto.setParamMap(paramMap);
            return this;
        }

        public ResponseFinancialDocumentDto build() {
            return responseFinancialDocumentDto;
        }
    }
}
