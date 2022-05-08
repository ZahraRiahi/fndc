package ir.demisco.cfs.model.dto.request;

import java.time.LocalDateTime;
import java.util.Map;

public class FinancialAccountBalanceRequest {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long documentNumberingTypeId;
    private String fromNumber;
    private String toNumber;
    private Long structureLevel;
    private Long ledgerTypeId;
    private Boolean hasRemain;
    private Boolean showHigherLevels;
    private Long organizationId;
    private String fromFinancialAccountCode;
    private String toFinancialAccountCode;
    private Long dateFilterFlg;
    private LocalDateTime periodStartDate;

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

    public Long getStructureLevel() {
        return structureLevel;
    }

    public void setStructureLevel(Long structureLevel) {
        this.structureLevel = structureLevel;
    }

    public Long getLedgerTypeId() {
        return ledgerTypeId;
    }

    public void setLedgerTypeId(Long ledgerTypeId) {
        this.ledgerTypeId = ledgerTypeId;
    }

    public Boolean getHasRemain() {
        return hasRemain;
    }

    public void setHasRemain(Boolean hasRemain) {
        this.hasRemain = hasRemain;
    }

    public Boolean getShowHigherLevels() {
        return showHigherLevels;
    }

    public void setShowHigherLevels(Boolean showHigherLevels) {
        this.showHigherLevels = showHigherLevels;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getFromFinancialAccountCode() {
        return fromFinancialAccountCode;
    }

    public void setFromFinancialAccountCode(String fromFinancialAccountCode) {
        this.fromFinancialAccountCode = fromFinancialAccountCode;
    }

    public String getToFinancialAccountCode() {
        return toFinancialAccountCode;
    }

    public void setToFinancialAccountCode(String toFinancialAccountCode) {
        this.toFinancialAccountCode = toFinancialAccountCode;
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


}
