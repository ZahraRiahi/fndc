package ir.demisco.cfs.model.dto.response;

import ir.demisco.cfs.model.entity.FinancialDocumentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseFinancialDocumentDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long financialNumberingTypeId;
    private Long fromNumber;
    private Long toNumber;
    private List<FinancialDocumentStatusDto> financialDocumentStatusDtoList;
    private String description;
    private Long FromAccountId;
    private Long toAccountId;
    private Long CentricAccountId;

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

    public List<FinancialDocumentStatusDto> getFinancialDocumentStatusDtoList() {
        return financialDocumentStatusDtoList;
    }

    public void setFinancialDocumentStatusDtoList(List<FinancialDocumentStatusDto> financialDocumentStatusDtoList) {
        this.financialDocumentStatusDtoList = financialDocumentStatusDtoList;
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

    public Long getCentricAccountId() {
        return CentricAccountId;
    }

    public void setCentricAccountId(Long centricAccountId) {
        CentricAccountId = centricAccountId;
    }

    public static Builder  builder(){
        return new Builder();
    }

    public static final class Builder {
        private ResponseFinancialDocumentDto responseFinancialDocumentDto;

        private Builder() {
            responseFinancialDocumentDto = new ResponseFinancialDocumentDto();
        }

        public static Builder aResponseFinancialDocumentDto() {
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

        public Builder financialDocumentStatusDtoList(List<FinancialDocumentStatusDto> financialDocumentStatusDtoList) {
            responseFinancialDocumentDto.setFinancialDocumentStatusDtoList(financialDocumentStatusDtoList);
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

        public Builder CentricAccountId(Long CentricAccountId) {
            responseFinancialDocumentDto.setCentricAccountId(CentricAccountId);
            return this;
        }

        public ResponseFinancialDocumentDto build() {
            return responseFinancialDocumentDto;
        }
    }
}
