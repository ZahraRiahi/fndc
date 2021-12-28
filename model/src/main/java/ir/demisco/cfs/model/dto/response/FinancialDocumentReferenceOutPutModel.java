package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialDocumentReferenceOutPutModel {
    private Long id;
    private Long financialDocumentItemId;
    private Long referenceNumber;
    private Date referenceDate;
    private String referenceDescription;

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

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
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

    public static FinancialDocumentReferenceOutPutModel.Builder builder() {
        return new FinancialDocumentReferenceOutPutModel.Builder();
    }

    public static final class Builder {
        private FinancialDocumentReferenceOutPutModel financialDocumentReferenceOutPutModel;

        private Builder() {
            financialDocumentReferenceOutPutModel = new FinancialDocumentReferenceOutPutModel();
        }

        public static Builder financialDocumentReferenceOutPutModel() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentReferenceOutPutModel.setId(id);
            return this;
        }

        public Builder financialDocumentItemId(Long financialDocumentItemId) {
            financialDocumentReferenceOutPutModel.setFinancialDocumentItemId(financialDocumentItemId);
            return this;
        }

        public Builder referenceNumber(Long referenceNumber) {
            financialDocumentReferenceOutPutModel.setReferenceNumber(referenceNumber);
            return this;
        }

        public Builder referenceDate(Date referenceDate) {
            financialDocumentReferenceOutPutModel.setReferenceDate(referenceDate);
            return this;
        }

        public Builder referenceDescription(String referenceDescription) {
            financialDocumentReferenceOutPutModel.setReferenceDescription(referenceDescription);
            return this;
        }

        public FinancialDocumentReferenceOutPutModel build() {
            return financialDocumentReferenceOutPutModel;
        }
    }
}
