package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialDocumentFilterResponse {
    private Long id;
    private String documentNumber;
    private LocalDateTime documentDate;
    private Long errorType;
    private String listSeq;
    private String errorMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
    }

    public Long getErrorType() {
        return errorType;
    }

    public void setErrorType(Long errorType) {
        this.errorType = errorType;
    }

    public String getListSeq() {
        return listSeq;
    }

    public void setListSeq(String listSeq) {
        this.listSeq = listSeq;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public static FinancialDocumentFilterResponse.Builder builder() {
        return new FinancialDocumentFilterResponse.Builder();
    }
    public static final class Builder {
        private FinancialDocumentFilterResponse financialDocumentFilterResponse;

        private Builder() {
            financialDocumentFilterResponse = new FinancialDocumentFilterResponse();
        }

        public static Builder بinancialDocumentFilterResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialDocumentFilterResponse.setId(id);
            return this;
        }

        public Builder documentNumber(String documentNumber) {
            financialDocumentFilterResponse.setDocumentNumber(documentNumber);
            return this;
        }

        public Builder documentDate(LocalDateTime documentDate) {
            financialDocumentFilterResponse.setDocumentDate(documentDate);
            return this;
        }

        public Builder errorType(Long errorType) {
            financialDocumentFilterResponse.setErrorType(errorType);
            return this;
        }

        public Builder listSeq(String listSeq) {
            financialDocumentFilterResponse.setListSeq(listSeq);
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            financialDocumentFilterResponse.setErrorMessage(errorMessage);
            return this;
        }

        public FinancialDocumentFilterResponse build() {
            return financialDocumentFilterResponse;
        }
    }
}
