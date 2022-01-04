package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialDocumentTransferOutputResponse {
    private Long documentId;
    private Date date;
    private String documentNumber;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public static FinancialDocumentTransferOutputResponse.Builder builder() {
        return new FinancialDocumentTransferOutputResponse.Builder();
    }

    public static final class Builder {
        private FinancialDocumentTransferOutputResponse financialDocumentTransferOutputResponse;

        private Builder() {
            financialDocumentTransferOutputResponse = new FinancialDocumentTransferOutputResponse();
        }

        public static Builder financialDocumentTransferOutputResponse() {
            return new Builder();
        }

        public Builder documentId(Long documentId) {
            financialDocumentTransferOutputResponse.setDocumentId(documentId);
            return this;
        }

        public Builder date(Date date) {
            financialDocumentTransferOutputResponse.setDate(date);
            return this;
        }

        public Builder documentNumber(String documentNumber) {
            financialDocumentTransferOutputResponse.setDocumentNumber(documentNumber);
            return this;
        }

        public FinancialDocumentTransferOutputResponse build() {
            return financialDocumentTransferOutputResponse;
        }
    }
}
