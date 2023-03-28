package ir.demisco.cfs.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Map;


public class FinancialDocumentReportRequest {
    private Long financialAccountId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private LocalDateTime periodStartDate;
    private Long documentNumberingTypeId;
    private Long centricAccountId1;
    private Long centricAccountId2;
    private Long referenceNumber;
    private String fromNumber;
    private String toNumber;
    private Long summarizingType;
    private Long ledgerTypeId;
    private Long organizationId;
    private Long dateFilterFlg;
    private Object centricAccount1;
    private Object centricAccount2;
    private Object referenceNumberObject;
    Map<String, Object> paramMap;

    public Object getCentricAccount1() {
        return centricAccount1;
    }

    public void setCentricAccount1(Object centricAccount1) {
        this.centricAccount1 = centricAccount1;
    }

    public Object getCentricAccount2() {
        return centricAccount2;
    }

    public void setCentricAccount2(Object centricAccount2) {
        this.centricAccount2 = centricAccount2;
    }

    public Object getReferenceNumberObject() {
        return referenceNumberObject;
    }

    public void setReferenceNumberObject(Object referenceNumberObject) {
        this.referenceNumberObject = referenceNumberObject;
    }

    public LocalDateTime getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodStartDate(LocalDateTime periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    @JsonProperty("fromDate")
    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    @JsonProperty("toDate")
    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public Long getDocumentNumberingTypeId() {
        return documentNumberingTypeId;
    }

    public void setDocumentNumberingTypeId(Long documentNumberingTypeId) {
        this.documentNumberingTypeId = documentNumberingTypeId;
    }

    public Long getCentricAccountId1() {
        return centricAccountId1;
    }

    public void setCentricAccountId1(Long centricAccountId1) {
        this.centricAccountId1 = centricAccountId1;
    }

    public Long getCentricAccountId2() {
        return centricAccountId2;
    }

    public void setCentricAccountId2(Long centricAccountId2) {
        this.centricAccountId2 = centricAccountId2;
    }

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @JsonProperty("FROM_NUMBER")
    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    @JsonProperty("TO_NUMBER")
    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public Long getSummarizingType() {
        return summarizingType;
    }

    public void setSummarizingType(Long summarizingType) {
        this.summarizingType = summarizingType;
    }

    public Long getLedgerTypeId() {
        return ledgerTypeId;
    }

    public void setLedgerTypeId(Long ledgerTypeId) {
        this.ledgerTypeId = ledgerTypeId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @JsonProperty("FILTER_FLG")
    public Long getDateFilterFlg() {
        return dateFilterFlg;
    }

    public void setDateFilterFlg(Long dateFilterFlg) {
        this.dateFilterFlg = dateFilterFlg;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public static FinancialDocumentReportRequest.Builder builder() {
        return new FinancialDocumentReportRequest.Builder();
    }

    public static final class Builder {
        private FinancialDocumentReportRequest financialDocumentReportRequest;

        private Builder() {
            financialDocumentReportRequest = new FinancialDocumentReportRequest();
        }

        public static Builder financialDocumentReportRequest() {
            return new Builder();
        }

        public Builder financialAccountId(Long financialAccountId) {
            financialDocumentReportRequest.setFinancialAccountId(financialAccountId);
            return this;
        }

        public Builder fromDate(LocalDateTime fromDate) {
            financialDocumentReportRequest.setFromDate(fromDate);
            return this;
        }

        public Builder toDate(LocalDateTime toDate) {
            financialDocumentReportRequest.setToDate(toDate);
            return this;
        }

        public Builder periodStartDate(LocalDateTime periodStartDate) {
            financialDocumentReportRequest.setPeriodStartDate(periodStartDate);
            return this;
        }

        public Builder documentNumberingTypeId(Long documentNumberingTypeId) {
            financialDocumentReportRequest.setDocumentNumberingTypeId(documentNumberingTypeId);
            return this;
        }

        public Builder centricAccountId1(Long centricAccountId1) {
            financialDocumentReportRequest.setCentricAccountId1(centricAccountId1);
            return this;
        }

        public Builder centricAccountId2(Long centricAccountId2) {
            financialDocumentReportRequest.setCentricAccountId2(centricAccountId2);
            return this;
        }

        public Builder referenceNumber(Long referenceNumber) {
            financialDocumentReportRequest.setReferenceNumber(referenceNumber);
            return this;
        }

        public Builder fromNumber(String fromNumber) {
            financialDocumentReportRequest.setFromNumber(fromNumber);
            return this;
        }

        public Builder toNumber(String toNumber) {
            financialDocumentReportRequest.setToNumber(toNumber);
            return this;
        }

        public Builder summarizingType(Long summarizingType) {
            financialDocumentReportRequest.setSummarizingType(summarizingType);
            return this;
        }

        public Builder ledgerTypeId(Long ledgerTypeId) {
            financialDocumentReportRequest.setLedgerTypeId(ledgerTypeId);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialDocumentReportRequest.setOrganizationId(organizationId);
            return this;
        }

        public Builder dateFilterFlg(Long dateFilterFlg) {
            financialDocumentReportRequest.setDateFilterFlg(dateFilterFlg);
            return this;
        }

        public Builder centricAccount1(Object centricAccount1) {
            financialDocumentReportRequest.setCentricAccount1(centricAccount1);
            return this;
        }

        public Builder centricAccount2(Object centricAccount2) {
            financialDocumentReportRequest.setCentricAccount2(centricAccount2);
            return this;
        }

        public Builder referenceNumberObject(Object referenceNumberObject) {
            financialDocumentReportRequest.setReferenceNumberObject(referenceNumberObject);
            return this;
        }

        public Builder paramMap(Map<String, Object> paramMap) {
            financialDocumentReportRequest.setParamMap(paramMap);
            return this;
        }

        public FinancialDocumentReportRequest build() {
            return financialDocumentReportRequest;
        }
    }

    @Override
    public String toString() {
        return "FinancialDocumentReportRequest{" +
                "financialAccountId=" + financialAccountId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", periodStartDate=" + periodStartDate +
                ", documentNumberingTypeId=" + documentNumberingTypeId +
                ", centricAccountId1=" + centricAccountId1 +
                ", centricAccountId2=" + centricAccountId2 +
                ", referenceNumber=" + referenceNumber +
                ", fromNumber='" + fromNumber + '\'' +
                ", toNumber='" + toNumber + '\'' +
                ", summarizingType=" + summarizingType +
                ", ledgerTypeId=" + ledgerTypeId +
                ", organizationId=" + organizationId +
                ", dateFilterFlg=" + dateFilterFlg +
                ", centricAccount1=" + centricAccount1 +
                ", centricAccount2=" + centricAccount2 +
                ", referenceNumberObject=" + referenceNumberObject +
                ", paramMap=" + paramMap +
                '}';
    }
}
