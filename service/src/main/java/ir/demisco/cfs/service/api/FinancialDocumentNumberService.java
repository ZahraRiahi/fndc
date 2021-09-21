package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberSaveDto;

public interface FinancialDocumentNumberService {

    FinancialDocumentNumberSaveDto documentNumberSave(FinancialDocumentNumberSaveDto financialDocumentNumberSaveDto);
}
