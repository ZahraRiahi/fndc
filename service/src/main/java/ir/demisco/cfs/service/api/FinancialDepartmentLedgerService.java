package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialDepartmentLedgerRequest;

import java.util.List;

public interface FinancialDepartmentLedgerService {
    /**
     * تخصیص دفتر مالی به شعبه
     */
    Boolean saveFinancialDepartmentLedger(List<FinancialDepartmentLedgerRequest> financialDepartmentLedgerRequests);
}
