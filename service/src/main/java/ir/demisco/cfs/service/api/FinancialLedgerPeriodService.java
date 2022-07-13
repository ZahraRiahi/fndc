package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialLedgerPeriodRequest;

public interface FinancialLedgerPeriodService {
    Boolean saveFinancialLedgerPeriod(FinancialLedgerPeriodRequest financialLedgerPeriodRequest);
}
