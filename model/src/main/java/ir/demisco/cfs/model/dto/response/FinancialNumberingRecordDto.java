package ir.demisco.cfs.model.dto.response;

public class FinancialNumberingRecordDto {

    private Long numberingTypeId;
    private Long financialDocumentId;
    private String financialDocumentNumber;

    public Long getNumberingTypeId() {
        return numberingTypeId;
    }

    public void setNumberingTypeId(Long numberingTypeId) {
        this.numberingTypeId = numberingTypeId;
    }

    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    public String getFinancialDocumentNumber() {
        return financialDocumentNumber;
    }

    public void setFinancialDocumentNumber(String financialDocumentNumber) {
        this.financialDocumentNumber = financialDocumentNumber;
    }
}
