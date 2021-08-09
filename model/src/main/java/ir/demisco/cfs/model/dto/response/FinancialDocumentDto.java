package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

public class FinancialDocumentDto {

    private Long id;
    private Date documentDate;
    private String    description;
    private Long financialDocumentTypeId;
    private String financialDocumentTypeDescription;
    private Long documentNumber;
    private LocalDateTime deletedDate;
    private Long userId;
    private String userName;
    private Long  debitAmount;
    private Long  creditAmount;
    private String fullDescription;

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

    public Long getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Long debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Long getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Long creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public static Builder builder(){
      return new Builder();
    }


    public static final class Builder {
        private FinancialDocumentDto financialDocumentDto;

        private Builder() {
            financialDocumentDto = new FinancialDocumentDto();
        }

        public static Builder financialDocumentDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentDto.setId(id);
            return this;
        }

        public Builder documentDate(Date documentDate) {
            financialDocumentDto.setDocumentDate(documentDate);
            return this;
        }

        public Builder description(String description) {
            financialDocumentDto.setDescription(description);
            return this;
        }

        public Builder financialDocumentTypeId(Long financialDocumentTypeId) {
            financialDocumentDto.setFinancialDocumentTypeId(financialDocumentTypeId);
            return this;
        }

        public Builder financialDocumentTypeDescription(String financialDocumentTypeDescription) {
            financialDocumentDto.setFinancialDocumentTypeDescription(financialDocumentTypeDescription);
            return this;
        }

        public Builder documentNumber(Long documentNumber) {
            financialDocumentDto.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder deletedDate(LocalDateTime deletedDate) {
            financialDocumentDto.setDeletedDate(deletedDate);
            return this;
        }

        public Builder userId(Long userId) {
            financialDocumentDto.setUserId(userId);
            return this;
        }

        public Builder userName(String userName) {
            financialDocumentDto.setUserName(userName);
            return this;
        }

        public Builder debitAmount(Long debitAmount) {
            financialDocumentDto.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(Long creditAmount) {
            financialDocumentDto.setCreditAmount(creditAmount);
            return this;
        }

        public Builder fullDescription(String fullDescription) {
            financialDocumentDto.setFullDescription(fullDescription);
            return this;
        }

        public FinancialDocumentDto build() {
            return financialDocumentDto;
        }
    }
}
