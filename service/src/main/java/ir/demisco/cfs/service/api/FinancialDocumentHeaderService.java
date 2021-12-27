package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialDocumentHeaderResponse;

public interface FinancialDocumentHeaderService {
    FinancialDocumentHeaderResponse getFinancialDocumentHeaderByDocumentId(Long financialDocumentId);
}
