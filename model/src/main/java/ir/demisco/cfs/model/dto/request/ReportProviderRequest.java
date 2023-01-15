package ir.demisco.cfs.model.dto.request;

public class ReportProviderRequest {
    private Long LEDGER_TYPE_ID;
    private Long DOCUMENT_NUMBERING_TYPE_ID;
    private String centricAccountId1;
    private String centricAccountId2;
    private String referenceNumber;
    private Long financialAccountId;
    private Long summarizingType;
    private String DATE_FILTER_FLG;

    public Long getLEDGER_TYPE_ID() {
        return LEDGER_TYPE_ID;
    }

    public void setLEDGER_TYPE_ID(Long LEDGER_TYPE_ID) {
        this.LEDGER_TYPE_ID = LEDGER_TYPE_ID;
    }

    public Long getDOCUMENT_NUMBERING_TYPE_ID() {
        return DOCUMENT_NUMBERING_TYPE_ID;
    }

    public void setDOCUMENT_NUMBERING_TYPE_ID(Long DOCUMENT_NUMBERING_TYPE_ID) {
        this.DOCUMENT_NUMBERING_TYPE_ID = DOCUMENT_NUMBERING_TYPE_ID;
    }

    public String getCentricAccountId1() {
        return centricAccountId1;
    }

    public void setCentricAccountId1(String centricAccountId1) {
        this.centricAccountId1 = centricAccountId1;
    }

    public String getCentricAccountId2() {
        return centricAccountId2;
    }

    public void setCentricAccountId2(String centricAccountId2) {
        this.centricAccountId2 = centricAccountId2;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    public Long getSummarizingType() {
        return summarizingType;
    }

    public void setSummarizingType(Long summarizingType) {
        this.summarizingType = summarizingType;
    }

    public String getDATE_FILTER_FLG() {
        return DATE_FILTER_FLG;
    }

    public void setDATE_FILTER_FLG(String DATE_FILTER_FLG) {
        this.DATE_FILTER_FLG = DATE_FILTER_FLG;
    }
}
