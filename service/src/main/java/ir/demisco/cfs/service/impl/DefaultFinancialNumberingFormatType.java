package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.service.api.FinancialNumberingFormatTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultFinancialNumberingFormatType  implements FinancialNumberingFormatTypeService {

    private final GridFilterService gridFilterService;
    private final FinancialNumberingFormatTypeGridProvider financialNumberingFormatTypeGridProvider;

    public DefaultFinancialNumberingFormatType(GridFilterService gridFilterService,FinancialNumberingFormatTypeGridProvider financialNumberingFormatTypeGridProvider) {
        this.gridFilterService = gridFilterService;
        this.financialNumberingFormatTypeGridProvider = financialNumberingFormatTypeGridProvider;
    }

    @Override
    @Transactional
    public DataSourceResult getNumberingFormatByOrganizationId(DataSourceRequest dataSourceRequest) {
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest, financialNumberingFormatTypeGridProvider);
    }
}
