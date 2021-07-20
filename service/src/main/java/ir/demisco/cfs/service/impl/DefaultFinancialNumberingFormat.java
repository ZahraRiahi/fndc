package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.service.api.FinancialNumberingFormatService;
import ir.demisco.cfs.service.repository.FinancialNumberingFormatRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

@Service
public class DefaultFinancialNumberingFormat implements FinancialNumberingFormatService {

    private final GridFilterService gridFilterService;
    private final OrganizationRepository organizationRepository;
    private final FinancialNumberingFormatGridProvider financialNumberingFormatGridProvider;
    private final FinancialNumberingFormatRepository financialNumberingFormatRepository;

    public DefaultFinancialNumberingFormat(GridFilterService gridFilterService, OrganizationRepository organizationRepository,
                                           FinancialNumberingFormatGridProvider financialNumberingFormatGridProvider,
                                           FinancialNumberingFormatRepository financialNumberingFormatRepository) {
        this.gridFilterService = gridFilterService;
        this.organizationRepository = organizationRepository;
        this.financialNumberingFormatGridProvider = financialNumberingFormatGridProvider;
        this.financialNumberingFormatRepository = financialNumberingFormatRepository;
    }

    @Override
    public DataSourceResult getNumberingFormatByOrganizationId(Long organizationId, DataSourceRequest dataSourceRequest) {

        Asserts.notNull(organizationId, "organizationId is null");
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters()
                .add(DataSourceRequest.FilterDescriptor.create("organization.id", organizationId, DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("financialNumberingFormatType.deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("financialNumberingType.deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest,financialNumberingFormatGridProvider);
    }
}
