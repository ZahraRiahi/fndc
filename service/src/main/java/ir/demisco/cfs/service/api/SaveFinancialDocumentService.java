package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialDocumentItemRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentItemResponse;
import ir.demisco.cfs.model.dto.response.FinancialDocumentSaveDto;

import java.util.List;

public interface SaveFinancialDocumentService {

    FinancialDocumentSaveDto saveDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto);

    FinancialDocumentSaveDto updateDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto);

    FinancialDocumentSaveDto getFinancialDocumentInfo(FinancialDocumentDto financialDocumentDto);

    List<FinancialDocumentItemResponse> getFinancialDocumentItem(FinancialDocumentItemRequest financialDocumentItemRequest);
}
