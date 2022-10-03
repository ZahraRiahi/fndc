package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialLedgerCloseMonthInputRequest;

public interface LedgerPeriodService {
    Boolean closeMonth(FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest);
}
