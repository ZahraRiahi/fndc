package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialDocumentReferenceDto {

    private Long     financialDocumentReferenceId;
    private Long     financialDocumentItemId;
    private String     referenceNumber;
    private Date referenceDate;
    private String     referenceDescription;

    public Long getFinancialDocumentReferenceId() {
        return financialDocumentReferenceId;
    }

    public void setFinancialDocumentReferenceId(Long financialDocumentReferenceId) {
        this.financialDocumentReferenceId = financialDocumentReferenceId;
    }

    public Long getFinancialDocumentItemId() {
        return financialDocumentItemId;
    }

    public void setFinancialDocumentItemId(Long financialDocumentItemId) {
        this.financialDocumentItemId = financialDocumentItemId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getReferenceDescription() {
        return referenceDescription;
    }

    public void setReferenceDescription(String referenceDescription) {
        this.referenceDescription = referenceDescription;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder {
        private FinancialDocumentReferenceDto financialDocumentReferenceDto;

        private Builder() {
            financialDocumentReferenceDto = new FinancialDocumentReferenceDto();
        }

        public static Builder aFinancialDocumentReferenceDto() {
            return new Builder();
        }

        public Builder financialDocumentReferenceId(Long financialDocumentReferenceId) {
            financialDocumentReferenceDto.setFinancialDocumentReferenceId(financialDocumentReferenceId);
            return this;
        }

        public Builder financialDocumentItemId(Long financialDocumentItemId) {
            financialDocumentReferenceDto.setFinancialDocumentItemId(financialDocumentItemId);
            return this;
        }

        public Builder referenceNumber(String referenceNumber) {
            financialDocumentReferenceDto.setReferenceNumber(referenceNumber);
            return this;
        }

        public Builder referenceDate(Date referenceDate) {
            financialDocumentReferenceDto.setReferenceDate(referenceDate);
            return this;
        }

        public Builder referenceDescription(String referenceDescription) {
            financialDocumentReferenceDto.setReferenceDescription(referenceDescription);
            return this;
        }

        public FinancialDocumentReferenceDto build() {
            return financialDocumentReferenceDto;
        }
    }
}
