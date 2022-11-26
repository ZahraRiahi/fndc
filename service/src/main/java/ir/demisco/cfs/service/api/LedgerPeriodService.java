package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialLedgerCloseMonthInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerClosingTempInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerClosingTempRequest;
import ir.demisco.cfs.model.dto.request.InsertLedgerPeriodInputRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface LedgerPeriodService {
    Boolean closeMonth(FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest);

    Boolean openMonth(FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest);

    Boolean closingTemp(FinancialLedgerClosingTempInputRequest financialLedgerClosingTempInputRequest);

    DataSourceResult getLedgerPeriodMonthList(DataSourceRequest dataSourceRequest);

    Boolean insertLedgerPeriod(InsertLedgerPeriodInputRequest insertLedgerPeriodInputRequest);

    Boolean delClosingTemp(FinancialLedgerClosingTempRequest financialLedgerClosingTempRequest);

    Boolean closingPermanent(FinancialLedgerClosingTempInputRequest financialLedgerClosingTempInputRequest);

    Boolean delClosingPermanent(FinancialLedgerClosingTempRequest financialLedgerClosingTempRequest);

    Boolean openingDocument(FinancialLedgerClosingTempInputRequest financialLedgerClosingTempInputRequest);

    Boolean delOpeningDocument(FinancialLedgerClosingTempRequest financialLedgerClosingTempRequest);

    DataSourceResult getLedgerPeriodList(DataSourceRequest dataSourceRequest);

    Boolean openingMonth(FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest);

    Boolean permanentCheck(FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest);
}
