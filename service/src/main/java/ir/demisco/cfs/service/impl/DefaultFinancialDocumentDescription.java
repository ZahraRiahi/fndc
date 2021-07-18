package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.service.api.FinancialDocumentDescriptionService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultFinancialDocumentDescription implements FinancialDocumentDescriptionService {

    private final GridFilterService gridFilterService;
    private final FinancialDocumentDescriptionListGridProvider  documentDescriptionListGridProvider;

    public DefaultFinancialDocumentDescription(GridFilterService gridFilterService, FinancialDocumentDescriptionListGridProvider documentDescriptionListGridProvider) {
        this.gridFilterService = gridFilterService;
        this.documentDescriptionListGridProvider = documentDescriptionListGridProvider;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialDocumentByOrganizationId(Long organizationId,DataSourceRequest dataSourceRequest) {
        Asserts.notNull(organizationId, "organizationId is null");
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters()
                .add(DataSourceRequest.FilterDescriptor.create("organization.id", organizationId, DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest,documentDescriptionListGridProvider);
    }
}
