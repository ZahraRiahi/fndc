package ir.demisco.cfs.model.dto.request;

import java.time.LocalDateTime;
import java.util.Date;


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

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

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

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

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

    public Long getDateFilterFlg() {
        return dateFilterFlg;
    }

    public void setDateFilterFlg(Long dateFilterFlg) {
        this.dateFilterFlg = dateFilterFlg;
    }
}
