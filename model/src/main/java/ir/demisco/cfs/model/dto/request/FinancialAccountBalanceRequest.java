package ir.demisco.cfs.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private Boolean flgBef;
    private String printMode;
    Map<String, Object> paramMap;

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

    @JsonProperty("HAS_REMAIN")
    public Boolean getHasRemain() {
        return hasRemain;
    }

    public void setHasRemain(Boolean hasRemain) {
        this.hasRemain = hasRemain;
    }

    @JsonProperty("SHOW_HIGHER_LEVELS")
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

    @JsonProperty("FROM_FINANCIAL_ACCOUNT_CODE")
    public String getFromFinancialAccountCode() {
        return fromFinancialAccountCode;
    }

    public void setFromFinancialAccountCode(String fromFinancialAccountCode) {
        this.fromFinancialAccountCode = fromFinancialAccountCode;
    }

    @JsonProperty("TO_FINANCIAL_ACCOUNT_CODE")
    public String getToFinancialAccountCode() {
        return toFinancialAccountCode;
    }

    public void setToFinancialAccountCode(String toFinancialAccountCode) {
        this.toFinancialAccountCode = toFinancialAccountCode;
    }

    @JsonProperty("FILTER_FLG")
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

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    @JsonProperty("FLG_BEF")
    public Boolean getFlgBef() {
        return flgBef;
    }

    public void setFlgBef(Boolean flgBef) {
        this.flgBef = flgBef;
    }

    @JsonProperty("PRINT_MODE")
    public String getPrintMode() {
        return printMode;
    }

    public void setPrintMode(String printMode) {
        this.printMode = printMode;
    }

    @Override
    public String toString() {
        return "FinancialAccountBalanceRequest{" +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", periodStartDate=" + periodStartDate +
                ", documentNumberingTypeId=" + documentNumberingTypeId +
                ", structureLevel=" + structureLevel +
                ", hasRemain=" + hasRemain +
                ", showHigherLevels=" + showHigherLevels +
                ", fromNumber='" + fromNumber + '\'' +
                ", toNumber='" + toNumber + '\'' +
                ", ledgerTypeId=" + ledgerTypeId +
                ", organizationId=" + organizationId +
                ", dateFilterFlg=" + dateFilterFlg +
                ", fromFinancialAccountCode=" + fromFinancialAccountCode +
                ", toFinancialAccountCode=" + toFinancialAccountCode +
                ", flgBef=" + flgBef +
                ", paramMap=" + paramMap +
                '}';
    }
}
