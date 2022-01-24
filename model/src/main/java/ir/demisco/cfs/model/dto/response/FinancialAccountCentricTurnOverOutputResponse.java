package ir.demisco.cfs.model.dto.response;

import java.util.List;

public class FinancialAccountCentricTurnOverOutputResponse {
    private List<FinancialAccountCentricTurnOverRecordsResponse> financialAccountCentricTurnOverRecordsModel;
    private FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel;

    public List<FinancialAccountCentricTurnOverRecordsResponse> getFinancialAccountCentricTurnOverRecordsModel() {
        return financialAccountCentricTurnOverRecordsModel;
    }

    public void setFinancialAccountCentricTurnOverRecordsModel(List<FinancialAccountCentricTurnOverRecordsResponse> financialAccountCentricTurnOverRecordsModel) {
        this.financialAccountCentricTurnOverRecordsModel = financialAccountCentricTurnOverRecordsModel;
    }

    public FinancialAccountTurnOverSummarizeResponse getFinancialAccountTurnOverSummarizeModel() {
        return financialAccountTurnOverSummarizeModel;
    }

    public void setFinancialAccountTurnOverSummarizeModel(FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel) {
        this.financialAccountTurnOverSummarizeModel = financialAccountTurnOverSummarizeModel;
    }
}
