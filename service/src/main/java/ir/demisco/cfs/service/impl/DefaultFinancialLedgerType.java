package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.FinancialLedgerTypeParameterDto;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeResponse;
import ir.demisco.cfs.model.entity.FinancialLedgerType;
import ir.demisco.cfs.service.api.FinancialLedgerTypeService;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DefaultFinancialLedgerType implements FinancialLedgerTypeService {

    private final FinancialLedgerTypeRepository financialDocumentTypeRepository;
    private final GridFilterService gridFilterService;

    public DefaultFinancialLedgerType(FinancialLedgerTypeRepository financialDocumentTypeRepository, GridFilterService gridFilterService) {
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
        this.gridFilterService = gridFilterService;
    }

    @Override
    @Transactional()
    public List<FinancialLedgerTypeDto> getFinancialLedgerType(Long organizationId) {
        List<FinancialLedgerType> financialLedgerType = financialDocumentTypeRepository.findFinancialLedgerTypeByOrganizationId(organizationId);
        return financialLedgerType.stream().map(e -> FinancialLedgerTypeDto.builder().id(e.getId())
                .description(e.getDescription())
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DataSourceResult financialLedgerTypeList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialLedgerTypeParameterDto param = setParameterToDto(filters);
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list = financialDocumentTypeRepository.financialLedgerTypeList(param.getOrganizationId(), param.getFinancialCodingTypeId()
                , param.getFinancialCodingType(), param.getFinancialLedgerTypeId(), param.getFinancialLedgerType(), pageable);
        List<FinancialLedgerTypeResponse> financialLedgerTypeResponses = list.stream().map(item ->
                FinancialLedgerTypeResponse.builder()
                        .id(Long.parseLong(item[0].toString()))
                        .description(item[1].toString())
                        .financialCodingTypeId(Long.parseLong(item[2].toString()))
                        .activeFlag(Integer.parseInt(item[3].toString()) == 1)
                        .financialCodingTypeDescription(item[4].toString())
                        .financialNumberingTypeDescription(item[5] == null ? "" :item[5].toString() )
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialLedgerTypeResponses);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;

    }

    private FinancialLedgerTypeParameterDto setParameterToDto(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto = new FinancialLedgerTypeParameterDto();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "organization.id":
                    financialLedgerTypeParameterDto.setOrganizationId(Long.parseLong(item.getValue().toString()));
                    break;
                case "financialCodingType.id":
                    if (item.getValue() != null) {
                        financialLedgerTypeParameterDto.setFinancialCodingType("financialCodingType");
                        financialLedgerTypeParameterDto.setFinancialCodingTypeId(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialLedgerTypeParameterDto.setFinancialCodingType(null);
                        financialLedgerTypeParameterDto.setFinancialCodingTypeId(0L);
                    }
                    break;
                case "id":
                    if (item.getValue() != null) {
                        financialLedgerTypeParameterDto.setFinancialLedgerType("setFinancialLedgerType");
                        financialLedgerTypeParameterDto.setFinancialLedgerTypeId(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialLedgerTypeParameterDto.setFinancialLedgerType(null);
                        financialLedgerTypeParameterDto.setFinancialLedgerTypeId(0L);
                    }
                    break;
            }
        }
        return financialLedgerTypeParameterDto;
    }
}
