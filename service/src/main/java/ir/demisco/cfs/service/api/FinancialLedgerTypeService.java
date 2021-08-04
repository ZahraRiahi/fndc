package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

import java.util.List;

public interface FinancialLedgerTypeService {

    List<FinancialLedgerTypeDto> getFinancialLedgerType(Long organizationId);

    DataSourceResult financialLedgerTypeList(DataSourceRequest dataSourceRequest);
}
