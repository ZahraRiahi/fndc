package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

public class FinancialDocumentItemDto {

    private Long id;
    private Date date;
    private Long documentNumber;
    private Long financialAccountId;
    private String  financialAccountDescription;
    private String    description;
    private Long  debitAmount;
    private Long  creditAmount;
    private String fullDescription;
    private String centricAccountDescription;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    public String getFinancialAccountDescription() {
        return financialAccountDescription;
    }

    public void setFinancialAccountDescription(String financialAccountDescription) {
        this.financialAccountDescription = financialAccountDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCentricAccountDescription() {
        return centricAccountDescription;
    }

    public void setCentricAccountDescription(String centricAccountDescription) {
        this.centricAccountDescription = centricAccountDescription;
    }

    public  static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private FinancialDocumentItemDto financialDocumentItemDto;

        private Builder() {
            financialDocumentItemDto = new FinancialDocumentItemDto();
        }

        public static Builder financialDocumentItemDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentItemDto.setId(id);
            return this;
        }

        public Builder date(Date date) {
            financialDocumentItemDto.setDate(date);
            return this;
        }

        public Builder documentNumber(Long documentNumber) {
            financialDocumentItemDto.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder financialAccountId(Long financialAccountId) {
            financialDocumentItemDto.setFinancialAccountId(financialAccountId);
            return this;
        }

        public Builder financialAccountDescription(String financialAccountDescription) {
            financialDocumentItemDto.setFinancialAccountDescription(financialAccountDescription);
            return this;
        }

        public Builder description(String description) {
            financialDocumentItemDto.setDescription(description);
            return this;
        }

        public Builder debitAmount(Long debitAmount) {
            financialDocumentItemDto.setDebitAmount(debitAmount);
            return this;
        }

        public Builder creditAmount(Long creditAmount) {
            financialDocumentItemDto.setCreditAmount(creditAmount);
            return this;
        }

        public Builder fullDescription(String fullDescription) {
            financialDocumentItemDto.setFullDescription(fullDescription);
            return this;
        }

        public Builder centricAccountDescription(String centricAccountDescription) {
            financialDocumentItemDto.setCentricAccountDescription(centricAccountDescription);
            return this;
        }

        public FinancialDocumentItemDto build() {
            return financialDocumentItemDto;
        }
    }
}
