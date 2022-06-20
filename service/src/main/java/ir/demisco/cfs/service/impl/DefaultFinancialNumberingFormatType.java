package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialNumberingFormatTypeDto;
import ir.demisco.cfs.service.api.FinancialNumberingFormatTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public DataSourceResult getNumberingFormatType(DataSourceRequest dataSourceRequest) {
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        DataSourceResult dataSourceResult = gridFilterService.filter(dataSourceRequest, financialNumberingFormatTypeGridProvider);
        List<FinancialNumberingFormatTypeDto> formatTypeDtos =  (List<FinancialNumberingFormatTypeDto>) dataSourceResult.getData();
        formatTypeDtos.forEach((FinancialNumberingFormatTypeDto financialNumberingFormatTypeDto) -> {
            financialNumberingFormatTypeDto.setDefaultReset( financialNumberingFormatTypeDto.getFormat().replace("$SRL",""));
        });
        dataSourceResult.setData(formatTypeDtos);
        return dataSourceResult;
    }
}
