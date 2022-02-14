package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialSecurityFilterRequest;
import ir.demisco.cfs.model.dto.response.FinancialSecurityOutputResponse;

public interface FinancialSecurityService {
    FinancialSecurityOutputResponse hasPermission(FinancialSecurityFilterRequest financialSecurityFilterRequest, Long organizationId);

    int resultSet(FinancialSecurityFilterRequest financialSecurityFilterRequest);
}
