package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;

import java.util.List;

public interface FinancialLedgerTypeService {

    List<FinancialLedgerTypeDto> getFinancialLedgerType(Long organizationId);
}
