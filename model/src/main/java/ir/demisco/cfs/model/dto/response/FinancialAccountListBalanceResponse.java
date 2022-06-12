package ir.demisco.cfs.model.dto.response;

import java.util.List;

public class FinancialAccountListBalanceResponse {
    private List<FinancialAccountBalanceResponse> financialAccountBalanceRecordsModel;
    private FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel;

    public List<FinancialAccountBalanceResponse> getFinancialAccountBalanceRecordsModel() {
        return financialAccountBalanceRecordsModel;
    }

    public void setFinancialAccountBalanceRecordsModel(List<FinancialAccountBalanceResponse> financialAccountBalanceRecordsModel) {
        this.financialAccountBalanceRecordsModel = financialAccountBalanceRecordsModel;
    }

    public FinancialAccountTurnOverSummarizeResponse getFinancialAccountTurnOverSummarizeModel() {
        return financialAccountTurnOverSummarizeModel;
    }

    public void setFinancialAccountTurnOverSummarizeModel(FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel) {
        this.financialAccountTurnOverSummarizeModel = financialAccountTurnOverSummarizeModel;
    }
}
