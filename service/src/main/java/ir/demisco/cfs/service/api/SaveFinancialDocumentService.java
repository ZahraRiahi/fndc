package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialDocumentDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentSaveDto;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface SaveFinancialDocumentService {

    FinancialDocumentSaveDto saveDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto);

    FinancialDocumentSaveDto updateDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto);

    FinancialDocumentSaveDto getFinancialDocumentInfo(FinancialDocumentDto financialDocumentDto);

    DataSourceResult getFinancialDocumentItem(DataSourceRequest dataSourceRequest);
}
