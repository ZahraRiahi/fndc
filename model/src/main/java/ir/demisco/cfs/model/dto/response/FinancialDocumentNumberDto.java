package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialDocumentNumberDto {

    private Long financialDocumentId;
    private Long numberingType;
    private LocalDateTime date;
    private Long financialPeriodId;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(Long financialPeriodId) {
        this.financialPeriodId = financialPeriodId;
    }

    public Long getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Long financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    public Long getNumberingType() {
        return numberingType;
    }

    public void setNumberingType(Long numberingType) {
        this.numberingType = numberingType;
    }
}
