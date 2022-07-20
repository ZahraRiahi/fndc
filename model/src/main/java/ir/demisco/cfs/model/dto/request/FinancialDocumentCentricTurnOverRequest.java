package ir.demisco.cfs.model.dto.request;

import java.time.LocalDateTime;
import java.util.Map;

public class FinancialDocumentCentricTurnOverRequest {
    private Long financialAccountId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long documentNumberingTypeId;
    private String fromNumber;
    private String toNumber;
    private Boolean flgHasRemind;
    private Boolean flgRelatedOther;
    private Boolean flgWithParentLevel;
    private Long ledgerTypeId;
    private Long dateFilterFlg;
    private LocalDateTime periodStartDate;
    private Long referenceNumber;
    private Object referenceNumberObject;
    private Object financialAccount;
    private Object cnacIdObj1;
    private Object cnacIdObj2;
    private Object cnatIdObj1;
    private Object cnatIdObj2;
    private Long cnacId1;
    private Long cnacId2;
    private Long cnatId1;
    private Long cnatId2;
    Map<String, Object> paramMap;

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

    public Boolean getFlgHasRemind() {
        return flgHasRemind;
    }

    public void setFlgHasRemind(Boolean flgHasRemind) {
        this.flgHasRemind = flgHasRemind;
    }

    public Boolean getFlgRelatedOther() {
        return flgRelatedOther;
    }

    public void setFlgRelatedOther(Boolean flgRelatedOther) {
        this.flgRelatedOther = flgRelatedOther;
    }

    public Boolean getFlgWithParentLevel() {
        return flgWithParentLevel;
    }

    public void setFlgWithParentLevel(Boolean flgWithParentLevel) {
        this.flgWithParentLevel = flgWithParentLevel;
    }

    public Long getLedgerTypeId() {
        return ledgerTypeId;
    }

    public void setLedgerTypeId(Long ledgerTypeId) {
        this.ledgerTypeId = ledgerTypeId;
    }

    public Long getDateFilterFlg() {
        return dateFilterFlg;
    }

    public void setDateFilterFlg(Long dateFilterFlg) {
        this.dateFilterFlg = dateFilterFlg;
    }

    public LocalDateTime getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodStartDate(LocalDateTime periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Object getReferenceNumberObject() {
        return referenceNumberObject;
    }

    public void setReferenceNumberObject(Object referenceNumberObject) {
        this.referenceNumberObject = referenceNumberObject;
    }

    public Object getFinancialAccount() {
        return financialAccount;
    }

    public void setFinancialAccount(Object financialAccount) {
        this.financialAccount = financialAccount;
    }

    public Object getCnacIdObj1() {
        return cnacIdObj1;
    }

    public void setCnacIdObj1(Object cnacIdObj1) {
        this.cnacIdObj1 = cnacIdObj1;
    }

    public Object getCnacIdObj2() {
        return cnacIdObj2;
    }

    public void setCnacIdObj2(Object cnacIdObj2) {
        this.cnacIdObj2 = cnacIdObj2;
    }

    public Object getCnatIdObj1() {
        return cnatIdObj1;
    }

    public void setCnatIdObj1(Object cnatIdObj1) {
        this.cnatIdObj1 = cnatIdObj1;
    }

    public Object getCnatIdObj2() {
        return cnatIdObj2;
    }

    public void setCnatIdObj2(Object cnatIdObj2) {
        this.cnatIdObj2 = cnatIdObj2;
    }

    public Long getCnacId1() {
        return cnacId1;
    }

    public void setCnacId1(Long cnacId1) {
        this.cnacId1 = cnacId1;
    }

    public Long getCnacId2() {
        return cnacId2;
    }

    public void setCnacId2(Long cnacId2) {
        this.cnacId2 = cnacId2;
    }

    public Long getCnatId1() {
        return cnatId1;
    }

    public void setCnatId1(Long cnatId1) {
        this.cnatId1 = cnatId1;
    }

    public Long getCnatId2() {
        return cnatId2;
    }

    public void setCnatId2(Long cnatId2) {
        this.cnatId2 = cnatId2;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
