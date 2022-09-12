package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialLedgerPeriodRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface FinancialLedgerPeriodService {
    Boolean saveFinancialLedgerPeriod(FinancialLedgerPeriodRequest financialLedgerPeriodRequest);

    DataSourceResult getFinancialLedgerPeriodList(DataSourceRequest dataSourceRequest);
}
