package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialNumberingTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeOutputResponse;
import ir.demisco.cfs.service.api.FinancialNumberingTypeService;
import ir.demisco.cfs.service.repository.FinancialNumberingTypeRepository;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialNumberingType implements FinancialNumberingTypeService {
    private final FinancialNumberingTypeRepository financialNumberingTypeRepository;
    private final GridFilterService gridFilterService;
    private final FinancialNumberingTypeGridProvider financialNumberingTypeGridProvider;

    public DefaultFinancialNumberingType(FinancialNumberingTypeRepository financialNumberingTypeRepository, GridFilterService gridFilterService, FinancialNumberingTypeGridProvider financialNumberingTypeGridProvider) {
        this.financialNumberingTypeRepository = financialNumberingTypeRepository;
        this.gridFilterService = gridFilterService;
        this.financialNumberingTypeGridProvider = financialNumberingTypeGridProvider;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<FinancialNumberingTypeOutputResponse> getNumberingTypeLov(FinancialNumberingTypeRequest financialNumberingTypeRequest) {
        if (financialNumberingTypeRequest.getFromDate() == null && financialNumberingTypeRequest.getToDate() == null) {
            List<Object[]> financialNumberingTypeList = financialNumberingTypeRepository.findByFinancialNumberingType();
            return financialNumberingTypeList.stream().map(e -> FinancialNumberingTypeOutputResponse.builder().id(Long.parseLong(e[0].toString()))
                    .description(e[1] == null ? "" : e[1].toString())
                    .fromCode(null)
                    .toCode(null)
                    .serialLength(0L)
                    .build()).collect(Collectors.toList());
        } else {
            List<Object[]> financialNumberingTypeList = financialNumberingTypeRepository.findByFinancialNumberingTypeAndOrganizationIdAndFromAndToDate(SecurityHelper.getCurrentUser().getOrganizationId(), financialNumberingTypeRequest.getFromDate(), financialNumberingTypeRequest.getToDate(), SecurityHelper.getCurrentUser().getUserId());

            return financialNumberingTypeList.stream().map(e -> FinancialNumberingTypeOutputResponse.builder().id(Long.parseLong(e[0].toString()))
                    .description(gatItemForString(e, 1))
                    .fromCode(e[3] == null ? "" : e[3].toString())
                    .toCode(e[4] == null ? "" : e[4].toString())
                    .serialLength(Long.parseLong(e[2] == null ? null : e[2].toString()))
                    .build()).collect(Collectors.toList());
        }

    }
    private String gatItemForString(Object[] e, int i) {
        return e[i] == null ? null : e[i].toString();
    }
    @Override
    public DataSourceResult getNumberingType(DataSourceRequest dataSourceRequest) {
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest, financialNumberingTypeGridProvider);
    }
}

