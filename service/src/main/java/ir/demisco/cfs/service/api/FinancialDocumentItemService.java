package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialDocumentItemOutPutResponse;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface FinancialDocumentItemService {

    DataSourceResult getFinancialDocumentItemList(DataSourceRequest dataSourceRequest);

    FinancialDocumentItemOutPutResponse getFinancialDocumentItemById(Long financialDocumentItemId);

    Boolean deleteFinancialDocumentItemById(Long financialDocumentItemId);
}
