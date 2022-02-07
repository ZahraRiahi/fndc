package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialDocumentSecurityInputRequest;

public interface FinancialDocumentSecurityService {
    Boolean getFinancialDocumentSecurity(FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest);
}
