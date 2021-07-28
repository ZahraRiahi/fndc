package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeGetDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentTypeDto;
import ir.demisco.cfs.model.entity.FinancialDocumentType;
import ir.demisco.cfs.service.api.FinancialDocumentTypeService;
import ir.demisco.cfs.service.repository.FinancialDocumentTypeRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialDocumentType implements FinancialDocumentTypeService {

    private final FinancialDocumentTypeRepository financialDocumentTypeRepository;
    private final GridFilterService gridFilterService;
    private final FinancialDocumentTypeProvider financialDocumentTypeProvider;

    public DefaultFinancialDocumentType(FinancialDocumentTypeRepository financialDocumentTypeRepository, GridFilterService gridFilterService, FinancialDocumentTypeProvider financialDocumentTypeProvider) {
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
        this.gridFilterService = gridFilterService;
        this.financialDocumentTypeProvider = financialDocumentTypeProvider;
    }

    @Override
    @Transactional
    public List<FinancialDocumentTypeGetDto> getNumberingFormatByOrganizationId(Long organizationId, ResponseFinancialDocumentTypeDto responseFinancialDocumentTypeDto) {
        return financialDocumentTypeRepository.findByOrganizationId(organizationId, responseFinancialDocumentTypeDto.getSearchStatusFlag(),
                responseFinancialDocumentTypeDto.getSearchStatusFlag() == null ? null : "true")
                .stream().map(financialDocumentType -> FinancialDocumentTypeGetDto.builder()
                        .id(financialDocumentType.getId())
                        .description(financialDocumentType.getDescription())
                        .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean deleteFinancialDocumentTypeById(Long financialDocumentTypeId) {
        FinancialDocumentType financialDocumentType = financialDocumentTypeRepository.findById(financialDocumentTypeId)
                .orElseThrow(() -> new RuleException("سند یافت نشد"));
        financialDocumentType.setDeletedDate(LocalDateTime.now());
        financialDocumentTypeRepository.save(financialDocumentType);
        return true;
    }

    @Override
    public DataSourceResult getFinancialDocumentTypeOrganizationIdAndFinancialSystemId(DataSourceRequest dataSourceRequest) {
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest, financialDocumentTypeProvider);
    }

}
