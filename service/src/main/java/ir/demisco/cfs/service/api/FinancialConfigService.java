package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialConfigRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface FinancialConfigService {
    DataSourceResult getFinancialConfigByOrganizationIdAndUserAndDepartment(DataSourceRequest dataSourceRequest, Long organizationId);

    Boolean saveOrUpdateFinancialConfig(FinancialConfigRequest financialConfigRequest);
}
