package ir.demisco.cfs.model.dto.response;

import java.util.List;

public class FinancialAccountTurnOverOutputResponse {
    private List<FinancialAccountTurnOverRecordsResponse> financialAccountTurnOverRecordsResponseModel;
    private FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel;

    public List<FinancialAccountTurnOverRecordsResponse> getFinancialAccountTurnOverRecordsResponseModel() {
        return financialAccountTurnOverRecordsResponseModel;
    }

    public void setFinancialAccountTurnOverRecordsResponseModel(List<FinancialAccountTurnOverRecordsResponse> financialAccountTurnOverRecordsResponseModel) {
        this.financialAccountTurnOverRecordsResponseModel = financialAccountTurnOverRecordsResponseModel;
    }

    public FinancialAccountTurnOverSummarizeResponse getFinancialAccountTurnOverSummarizeModel() {
        return financialAccountTurnOverSummarizeModel;
    }

    public void setFinancialAccountTurnOverSummarizeModel(FinancialAccountTurnOverSummarizeResponse financialAccountTurnOverSummarizeModel) {
        this.financialAccountTurnOverSummarizeModel = financialAccountTurnOverSummarizeModel;
    }

}
