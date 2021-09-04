package ir.demisco.cfs.model.dto.response;

public class FinancialDocumentAccountDto {

    private Long Id;
    private Long  financialAccountId;
    private Long newFinancialAccountId;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    public Long getNewFinancialAccountId() {
        return newFinancialAccountId;
    }

    public void setNewFinancialAccountId(Long newFinancialAccountId) {
        this.newFinancialAccountId = newFinancialAccountId;
    }
}
