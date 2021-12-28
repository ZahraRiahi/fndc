package ir.demisco.cfs.service.api;

import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface FinancialAccountService {
    DataSourceResult getFinancialDocument(DataSourceRequest dataSourceRequest);

    DataSourceResult getFinancialDocumentCentricTurnOver(DataSourceRequest dataSourceRequest);

    DataSourceResult getFinancialDocumentBalanceReport(DataSourceRequest dataSourceRequest);
}
