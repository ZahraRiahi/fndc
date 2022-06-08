package ir.demisco.cfs.model.dto.response;

import java.util.List;

public class FinancialAccountCentricBalanceResponse {
    private List<FinancialDocumentCentricBalanceResponse> financialAccountCentricBalanceRecordsModel;
    private FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel;

    public List<FinancialDocumentCentricBalanceResponse> getFinancialAccountCentricBalanceRecordsModel() {
        return financialAccountCentricBalanceRecordsModel;
    }

    public void setFinancialAccountCentricBalanceRecordsModel(List<FinancialDocumentCentricBalanceResponse> financialAccountCentricBalanceRecordsModel) {
        this.financialAccountCentricBalanceRecordsModel = financialAccountCentricBalanceRecordsModel;
    }

    public FinancialAccountTurnOverSummarizeResponse getFinancialAccountTurnOverSummarizeModel() {
        return financialAccountTurnOverSummarizeModel;
    }

    public void setFinancialAccountTurnOverSummarizeModel(FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel) {
        this.financialAccountTurnOverSummarizeModel = financialAccountTurnOverSummarizeModel;
    }
}
