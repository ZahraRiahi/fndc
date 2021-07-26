package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.service.api.FinancialNumberingTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.springframework.stereotype.Service;

@Service
public class DefaultFinancialNumberingType implements FinancialNumberingTypeService{

    private final GridFilterService gridFilterService;
    private final FinancialNumberingTypeGridProvider financialNumberingTypeGridProvider;

    public DefaultFinancialNumberingType(GridFilterService gridFilterService, FinancialNumberingTypeGridProvider financialNumberingTypeGridProvider) {
        this.gridFilterService = gridFilterService;
        this.financialNumberingTypeGridProvider = financialNumberingTypeGridProvider;
    }


    @Override
    public DataSourceResult getNumberingType(DataSourceRequest dataSourceRequest) {

        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest, financialNumberingTypeGridProvider);
    }
}
