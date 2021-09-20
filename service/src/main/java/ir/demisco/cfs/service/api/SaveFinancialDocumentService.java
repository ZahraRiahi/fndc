package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialDocumentDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentSaveDto;

public interface SaveFinancialDocumentService {

    FinancialDocumentSaveDto saveDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto);

    FinancialDocumentSaveDto updateDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto);

    FinancialDocumentSaveDto getFinancialDocumentInfo(FinancialDocumentDto financialDocumentDto);
}