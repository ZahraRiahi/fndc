package ir.demisco.cfs.model.dto.response;

public class RequestDocumentStructureDto {

    private Long FinancialDocumentId;
    private Long financialStructureId;

    public Long getFinancialDocumentId() {
        return FinancialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        FinancialDocumentId = financialDocumentId;
    }

    public Long getFinancialStructureId() {
        return financialStructureId;
    }

    public void setFinancialStructureId(Long financialStructureId) {
        this.financialStructureId = financialStructureId;
    }
}
