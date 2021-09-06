package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.service.api.FinancialConfigService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DefaultFinancialConfig implements FinancialConfigService {
    private final GridFilterService gridFilterService;
    private final FinancialConfigListGridProvider financialConfigListGridProvider;

    public DefaultFinancialConfig(GridFilterService gridFilterService, FinancialConfigListGridProvider financialConfigListGridProvider) {
        this.gridFilterService = gridFilterService;
        this.financialConfigListGridProvider = financialConfigListGridProvider;
    }

    @Override
    @Transactional(readOnly = true)
    public DataSourceResult getFinancialConfigByOrganizationIdAndUserAndDepartment(DataSourceRequest dataSourceRequest) {
        return gridFilterService.filter(dataSourceRequest, financialConfigListGridProvider);
    }

}
