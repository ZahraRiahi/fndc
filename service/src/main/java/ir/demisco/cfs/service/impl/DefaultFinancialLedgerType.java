package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.FinancialLedgerTypeParameterDto;
import ir.demisco.cfs.model.dto.request.FinancialLedgerTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentLedgerDto;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentLedgerResponse;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeResponse;
import ir.demisco.cfs.model.entity.*;
import ir.demisco.cfs.service.api.FinancialLedgerTypeService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.basic.model.entity.org.Organization;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialLedgerType implements FinancialLedgerTypeService {

    private final FinancialLedgerTypeRepository financialLedgerTypeRepository;
    private final FinancialCodingTypeRepository financialCodingTypeRepository;
    private final OrganizationRepository organizationRepository;
    private final FinancialNumberingTypeRepository financialNumberingTypeRepository;
    private final LedgerNumberingTypeRepository ledgerNumberingTypeRepository;
    private final FinancialDepartmentLedgerRepository financialDepartmentLedgerRepository;

    public DefaultFinancialLedgerType(FinancialLedgerTypeRepository financialLedgerTypeRepository, FinancialCodingTypeRepository financialCodingTypeRepository
            , OrganizationRepository organizationRepository, FinancialNumberingTypeRepository financialNumberingTypeRepository, LedgerNumberingTypeRepository ledgerNumberingTypeRepository, FinancialDepartmentLedgerRepository financialDepartmentLedgerRepository) {
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialCodingTypeRepository = financialCodingTypeRepository;
        this.organizationRepository = organizationRepository;
        this.financialNumberingTypeRepository = financialNumberingTypeRepository;
        this.ledgerNumberingTypeRepository = ledgerNumberingTypeRepository;
        this.financialDepartmentLedgerRepository = financialDepartmentLedgerRepository;
    }

    @Override
    @Transactional()
    public List<FinancialLedgerTypeDto> getFinancialLedgerType(Long organizationId) {
        List<FinancialLedgerType> financialLedgerType = financialLedgerTypeRepository.findFinancialLedgerTypeByOrganizationId(organizationId);
        return financialLedgerType.stream().map(e -> FinancialLedgerTypeDto.builder().id(e.getId())
                .description(e.getDescription())
                .code(e.getCode())
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DataSourceResult financialLedgerTypeList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialLedgerTypeParameterDto param = setParameterToDto(filters);
        if (param.getOrganizationId() == null) {
            Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
            param.setOrganizationId(organizationId);
        }
        List<Object[]> list = financialLedgerTypeRepository.financialLedgerTypeList(param.getOrganizationId(), param.getFinancialCodingTypeId()
                , param.getFinancialCodingType(), param.getFinancialLedgerTypeId(), param.getFinancialLedgerType());
        List<FinancialLedgerTypeResponse> financialLedgerTypeResponses = list.stream().map(item ->
                FinancialLedgerTypeResponse.builder()
                        .financialLedgerTypeId(Long.parseLong(item[0].toString()))
                        .description(item[1] == null ? "" : item[1].toString())
                        .code(item[2] == null ? "" : item[2].toString())
                        .financialCodingTypeId(Long.parseLong(item[3].toString()))
                        .activeFlag(Integer.parseInt(item[4].toString()) == 1)
                        .financialCodingTypeDescription(item[5] == null ? "" : item[5].toString())
                        .financialNumberingTypeDescription(item[6] == null ? "" : item[6].toString())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialLedgerTypeResponses.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setTotal(list.size());
        return dataSourceResult;


    }

    private FinancialLedgerTypeParameterDto setParameterToDto(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto = new FinancialLedgerTypeParameterDto();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
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

    @Override
    @Transactional
    public Boolean saveFinancialLedgerType(FinancialLedgerTypeRequest financialLedgerTypeRequest) {
        Long financialLedgerTypeId = financialLedgerTypeRequest.getFinancialLedgerTypeId();
        Long financialCodingTypeId = financialLedgerTypeRequest.getFinancialCodingTypeId();
        if (financialCodingTypeId == null || financialCodingTypeId < 0) {
            throw new RuleException("fin.financialLedgerType.insertCodingType");
        }
        List<Long> numberingTypeIdList = financialLedgerTypeRequest.getNumberingTypeIdList();
        for (Long numberingTypeId : numberingTypeIdList) {
            if (numberingTypeId == null) {
                throw new RuleException("fin.financialLedgerType.numberingType");
            }
        }
        if (financialLedgerTypeId == null) {
            insertFinancialLedgerType(financialLedgerTypeRequest);
            return true;
        }
        Optional<FinancialLedgerType> financialLedgerTypeTbl = financialLedgerTypeRepository.findById(financialLedgerTypeId);
        if (financialLedgerTypeTbl.isPresent()) {
            FinancialLedgerType financialLedgerType = financialLedgerTypeTbl.get();
            updateFinancialLedgerType(financialLedgerType, financialLedgerTypeRequest);
        } else {
            insertFinancialLedgerType(financialLedgerTypeRequest);
        }
        return true;
    }

    @Override
    public List<FinancialDepartmentLedgerResponse> getFinancialLedgerByDepartmentId(FinancialDepartmentLedgerDto departmentLedgerDto) {
        List<Object[]> financialDepartmentLedgerListObject = financialDepartmentLedgerRepository.findByFinancialDepartmentId(departmentLedgerDto.getFinancialDepartmentId());
        return financialDepartmentLedgerListObject.stream().map(objects -> FinancialDepartmentLedgerResponse.builder().financialLedgerTypeId(Long.parseLong(objects[0].toString()))
                .description(objects[1].toString())
                .build()).collect(Collectors.toList());
    }


    @Transactional
    public Boolean insertFinancialLedgerType(FinancialLedgerTypeRequest financialLedgerTypeRequest) {
        FinancialLedgerType financialLedgerTypeNew = new FinancialLedgerType();
        financialLedgerTypeNew.setDescription(financialLedgerTypeRequest.getDescription() != null ? financialLedgerTypeRequest.getDescription() : null);
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        String financialLedgerTypeCodeByOrganizationId = financialLedgerTypeRepository.findFinancialLedgerTypeCodeByOrganizationId(organizationId);
        financialLedgerTypeNew.setCode(financialLedgerTypeCodeByOrganizationId);
        FinancialCodingType financialCodingType = financialCodingTypeRepository.findById(financialLedgerTypeRequest.getFinancialCodingTypeId()).orElseThrow(() -> new RuleException("fin.financialLedgerType.notValidCodingType"));
        financialLedgerTypeNew.setFinancialCodingType(financialCodingType);
//        Optional<FinancialCodingType> financialCodingType = financialCodingTypeRepository.findById(financialCodingTypeId);
//        if (financialCodingType.isPresent()) {
//            financialLedgerTypeNew.setFinancialCodingType(financialCodingType.get());
//        } else {
//            throw new RuleException("fin.financialLedgerType.notValidCodingType");
//        }
        Optional<Organization> organization = organizationRepository.findById(financialLedgerTypeRequest.getOrganizationId());
        financialLedgerTypeNew.setOrganization(organization.get());
        financialLedgerTypeNew.setActiveFlag(financialLedgerTypeRequest.getActiveFlag());
        FinancialLedgerType financialLedgerType = financialLedgerTypeRepository.save(financialLedgerTypeNew);
        List<Long> financialNumberingTypeIdList = financialLedgerTypeRequest.getNumberingTypeIdList();
        saveLedgerNumberingType(financialNumberingTypeIdList, financialLedgerTypeRequest, financialLedgerType);
        return true;
    }

    private Boolean updateFinancialLedgerType(FinancialLedgerType financialLedgerType, FinancialLedgerTypeRequest financialLedgerTypeRequest) {
        Long financialLedgerTypeDtoId = financialLedgerTypeRequest.getFinancialLedgerTypeId();
        if (financialLedgerType.getId().equals(financialLedgerTypeDtoId)) {
            financialLedgerType.setActiveFlag(financialLedgerTypeRequest.getActiveFlag());
            financialLedgerTypeRepository.save(financialLedgerType);
        }
        updateLedgerNumberingType(financialLedgerType, financialLedgerTypeRequest);
        return true;
    }

    private Boolean updateLedgerNumberingType(FinancialLedgerType financialLedgerType, FinancialLedgerTypeRequest financialLedgerTypeRequest) {
        List<Long> legerNumberingTypeByFinancialLedgerTypeId = ledgerNumberingTypeRepository.getLegerNumberingTypeByFinancialLedgerTypeId(financialLedgerType.getId());
        for (Long legerNumberingType : legerNumberingTypeByFinancialLedgerTypeId) {
            LedgerNumberingType ledgerNumberingType = ledgerNumberingTypeRepository.findById(legerNumberingType).orElseThrow(() -> new RuleException("fin.financialLedgerType.notValidCodingType"));
            ledgerNumberingTypeRepository.deleteById(ledgerNumberingType.getId());
        }
        List<Long> financialNumberingListRequest = financialLedgerTypeRequest.getNumberingTypeIdList();
        saveLedgerNumberingType(financialNumberingListRequest, financialLedgerTypeRequest, new FinancialLedgerType());
        return true;
    }

    private Boolean saveLedgerNumberingType(List<Long> financialNumberingListRequest, FinancialLedgerTypeRequest financialLedgerTypeRequest, FinancialLedgerType financialLedgerType) {
        Long financialLedgerTypeIdRequest = financialLedgerTypeRequest.getFinancialLedgerTypeId();
        for (Long financialNumberingTypeId : financialNumberingListRequest) {
            FinancialLedgerType financialLedgerTypeFromInsert = financialLedgerTypeRepository.findById(financialLedgerType.getId()).orElseThrow(() -> new RuleException("fin.financialLedgerType.notValidNumberingType"));
            FinancialLedgerType financialLedgerTypeFromUpdate = financialLedgerTypeRepository.findById(financialLedgerTypeIdRequest).orElseThrow(() -> new RuleException("fin.financialLedgerType.notValidNumberingType"));
            LedgerNumberingType ledgerNumberingTypeNew = new LedgerNumberingType();
            FinancialNumberingType financialNumberingType = financialNumberingTypeRepository.findById(financialNumberingTypeId).orElseThrow(() -> new RuleException("fin.financialLedgerType.notValidNumberingType"));
            Long countByLedgerTypeIdAndNumberingTypeIdAndDeleteDate = ledgerNumberingTypeRepository.getCountByLedgerTypeIdAndNumberingTypeIdAndDeleteDate(financialLedgerTypeRequest.getFinancialLedgerTypeId()
                    , financialNumberingType.getId());
            if (countByLedgerTypeIdAndNumberingTypeIdAndDeleteDate > 0) {
                throw new RuleException("fin.financialLedgerType.existNumberingTypeInDepartment");
            }
            if (financialLedgerTypeIdRequest == null) {
                ledgerNumberingTypeNew.setFinancialLedgerType(financialLedgerTypeFromInsert);
            } else {
                ledgerNumberingTypeNew.setFinancialLedgerType(financialLedgerTypeFromUpdate);
            }
            ledgerNumberingTypeNew.setFinancialNumberingType(financialNumberingType);
            ledgerNumberingTypeRepository.save(ledgerNumberingTypeNew);
        }
        return true;
    }
}
