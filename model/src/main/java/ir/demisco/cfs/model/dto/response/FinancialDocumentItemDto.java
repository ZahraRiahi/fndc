package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;
public class FinancialDocumentItemDto {

    private Long id;
    private LocalDateTime documentDate;
    private Long documentNumber;
    private Long sequenceNumber;
    private Long financialAccountId;
    private String financialAccountDescription;
    private String description;
    private Long debitAmount;
    private Long creditAmount;
    private String fullDescription;
    private String centricAccountDescription;
    private String financialAccountCode;
    private Long financialDocumentId;
    private Long departmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
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

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getFinancialAccountCode() {
        return financialAccountCode;
    }

    public void setFinancialAccountCode(String financialAccountCode) {
        this.financialAccountCode = financialAccountCode;
    }

    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private FinancialDocumentItemDto financialDocumentItemDto;

        private Builder() {
            financialDocumentItemDto = new FinancialDocumentItemDto();
        }

        public static Builder aFinancialDocumentItemDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentItemDto.setId(id);
            return this;
        }

        public Builder documentDate(LocalDateTime documentDate) {
            financialDocumentItemDto.setDocumentDate(documentDate);
            return this;
        }

        public Builder documentNumber(Long documentNumber) {
            financialDocumentItemDto.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder sequenceNumber(Long sequenceNumber) {
            financialDocumentItemDto.setSequenceNumber(sequenceNumber);
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
        public Builder financialAccountCode(String financialAccountCode) {
            financialDocumentItemDto.setFinancialAccountCode(financialAccountCode);
            return this;
        }

        public Builder financialDocumentId(Long financialDocumentId) {
            financialDocumentItemDto.setFinancialDocumentId(financialDocumentId);
            return this;
        }
        public Builder departmentId(Long departmentId) {
            financialDocumentItemDto.setDepartmentId(departmentId);
            return this;
        }
        public FinancialDocumentItemDto build() {
            return financialDocumentItemDto;
        }
    }
}
