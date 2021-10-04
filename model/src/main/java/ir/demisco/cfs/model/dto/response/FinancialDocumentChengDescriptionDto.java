package ir.demisco.cfs.model.dto.response;

import java.util.List;

public class FinancialDocumentChengDescriptionDto {

    private Long id;
    private List<Long> financialDocumentItemIdList;
    private String oldDescription;
    private String newDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getFinancialDocumentItemIdList() {
        return financialDocumentItemIdList;
    }

    public void setFinancialDocumentItemIdList(List<Long> financialDocumentItemIdList) {
        this.financialDocumentItemIdList = financialDocumentItemIdList;
    }

    public String getOldDescription() {
        return oldDescription;
    }

    public void setOldDescription(String oldDescription) {
        this.oldDescription = oldDescription;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
}
