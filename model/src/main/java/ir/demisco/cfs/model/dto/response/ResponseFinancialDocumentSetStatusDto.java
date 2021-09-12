package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ResponseFinancialDocumentSetStatusDto {

    private Long id;
    private Date documentDate;
    private String    description;
    private Long financialDocumentTypeId;
    private String financialDocumentTypeDescription;
    private Long documentNumber;
    private LocalDateTime deletedDate;
    private Long userId;
    private String userName;
    private Double  debitAmount;
    private Double  creditAmount;
    private String fullDescription;
    private List<FinancialDocumentErrorDto> financialDocumentErrorDtoList;
    private boolean errorFoundFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFinancialDocumentTypeId() {
        return financialDocumentTypeId;
    }

    public void setFinancialDocumentTypeId(Long financialDocumentTypeId) {
        this.financialDocumentTypeId = financialDocumentTypeId;
    }

    public String getFinancialDocumentTypeDescription() {
        return financialDocumentTypeDescription;
    }

    public void setFinancialDocumentTypeDescription(String financialDocumentTypeDescription) {
        this.financialDocumentTypeDescription = financialDocumentTypeDescription;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public List<FinancialDocumentErrorDto> getFinancialDocumentErrorDtoList() {
        return financialDocumentErrorDtoList;
    }

    public void setFinancialDocumentErrorDtoList(List<FinancialDocumentErrorDto> financialDocumentErrorDtoList) {
        this.financialDocumentErrorDtoList = financialDocumentErrorDtoList;
    }

    public boolean isErrorFoundFlag() {
        return errorFoundFlag;
    }

    public void setErrorFoundFlag(boolean errorFoundFlag) {
        this.errorFoundFlag = errorFoundFlag;
    }


    public static Builder builder(){
        return new Builder();
    }
    public static final class Builder {
        private ResponseFinancialDocumentSetStatusDto responseFinancialDocumentSetStatusDto;

        private Builder() {
            responseFinancialDocumentSetStatusDto = new ResponseFinancialDocumentSetStatusDto();
        }

        public static Builder responseFinancialDocumentSetStatusDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            responseFinancialDocumentSetStatusDto.setId(id);
            return this;
        }

        public Builder documentDate(Date documentDate) {
            responseFinancialDocumentSetStatusDto.setDocumentDate(documentDate);
            return this;
        }

        public Builder description(String description) {
            responseFinancialDocumentSetStatusDto.setDescription(description);
            return this;
        }

        public Builder financialDocumentTypeId(Long financialDocumentTypeId) {
            responseFinancialDocumentSetStatusDto.setFinancialDocumentTypeId(financialDocumentTypeId);
            return this;
        }

        public Builder financialDocumentTypeDescription(String financialDocumentTypeDescription) {
            responseFinancialDocumentSetStatusDto.setFinancialDocumentTypeDescription(financialDocumentTypeDescription);
            return this;
        }

        public Builder documentNumber(Long documentNumber) {
            responseFinancialDocumentSetStatusDto.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder deletedDate(LocalDateTime deletedDate) {
            responseFinancialDocumentSetStatusDto.setDeletedDate(deletedDate);
            return this;
        }

        public Builder userId(Long userId) {
            responseFinancialDocumentSetStatusDto.setUserId(userId);
            return this;
        }

        public Builder userName(String userName) {
            responseFinancialDocumentSetStatusDto.setUserName(userName);
            return this;
        }

        public Builder debitAmount(Double debitAmount) {
            responseFinancialDocumentSetStatusDto.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(Double creditAmount) {
            responseFinancialDocumentSetStatusDto.setCreditAmount(creditAmount);
            return this;
        }

        public Builder fullDescription(String fullDescription) {
            responseFinancialDocumentSetStatusDto.setFullDescription(fullDescription);
            return this;
        }

        public Builder financialDocumentErrorDtoList(List<FinancialDocumentErrorDto> financialDocumentErrorDtoList) {
            responseFinancialDocumentSetStatusDto.setFinancialDocumentErrorDtoList(financialDocumentErrorDtoList);
            return this;
        }

        public Builder errorFoundFlag(boolean errorFoundFlag) {
            responseFinancialDocumentSetStatusDto.setErrorFoundFlag(errorFoundFlag);
            return this;
        }

        public ResponseFinancialDocumentSetStatusDto build() {
            return responseFinancialDocumentSetStatusDto;
        }
    }
}
