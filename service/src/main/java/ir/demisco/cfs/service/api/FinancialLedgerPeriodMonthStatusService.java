package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.GetLedgerPeriodMonthStatusRequest;

public interface FinancialLedgerPeriodMonthStatusService {
    Long getLedgerPeriodMonthStatus(GetLedgerPeriodMonthStatusRequest getLedgerPeriodMonthStatusRequest);

}
