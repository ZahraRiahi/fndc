package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialDocumentReportDriverRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface FinancialAccountService {
    DataSourceResult getFinancialDocument(DataSourceRequest dataSourceRequest);

    DataSourceResult getFinancialDocumentCentricTurnOver(DataSourceRequest dataSourceRequest);

    DataSourceResult getFinancialDocumentBalanceReport(DataSourceRequest dataSourceRequest);

    DataSourceResult getFinancialDocumentCentricBalanceReport(DataSourceRequest dataSourceRequest);

    byte[] report(FinancialDocumentReportDriverRequest financialDocumentReportDriverRequest);
}
