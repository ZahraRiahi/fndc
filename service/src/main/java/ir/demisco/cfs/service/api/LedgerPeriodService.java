package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialLedgerCloseMonthInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerClosingTempInputRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface LedgerPeriodService {
    Boolean closeMonth(FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest);

    Boolean openMonth(FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest);

    Boolean closingTemp(FinancialLedgerClosingTempInputRequest financialLedgerClosingTempInputRequest);

    DataSourceResult getLedgerPeriodMonthList(DataSourceRequest dataSourceRequest);
}
