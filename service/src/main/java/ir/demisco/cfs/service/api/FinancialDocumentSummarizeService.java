package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialDocumentSummarizeRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentSummarizeResponse;


public interface FinancialDocumentSummarizeService {
    FinancialDocumentSummarizeResponse getFinancialDocumentByFinancialDocumentId(FinancialDocumentSummarizeRequest financialDocumentSummarizeRequest);
}
