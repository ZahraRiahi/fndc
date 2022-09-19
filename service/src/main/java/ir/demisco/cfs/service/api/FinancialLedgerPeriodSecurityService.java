package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.CheckLedgerPermissionInputRequest;

public interface FinancialLedgerPeriodSecurityService {
    Boolean checkFinancialLedgerPeriodSecurity(CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest);
}
