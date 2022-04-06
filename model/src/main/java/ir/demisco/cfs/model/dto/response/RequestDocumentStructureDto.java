package ir.demisco.cfs.model.dto.response;

public class RequestDocumentStructureDto {

    private Long financialDocumentId;
    private Long financialStructureId;

    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    public Long getFinancialStructureId() {
        return financialStructureId;
    }

    public void setFinancialStructureId(Long financialStructureId) {
        this.financialStructureId = financialStructureId;
    }
}
