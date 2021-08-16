package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.FinancialLedgerTypeParameterDto;
import ir.demisco.cfs.model.dto.request.FinancialLedgerTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeResponse;
import ir.demisco.cfs.model.entity.FinancialCodingType;
import ir.demisco.cfs.model.entity.FinancialLedgerType;
import ir.demisco.cfs.model.entity.FinancialNumberingType;
import ir.demisco.cfs.model.entity.LedgerNumberingType;
import ir.demisco.cfs.service.api.FinancialLedgerTypeService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.basic.model.entity.org.Organization;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    public DefaultFinancialLedgerType(FinancialLedgerTypeRepository financialLedgerTypeRepository, FinancialCodingTypeRepository financialCodingTypeRepository
            , OrganizationRepository organizationRepository, FinancialNumberingTypeRepository financialNumberingTypeRepository, LedgerNumberingTypeRepository ledgerNumberingTypeRepository) {
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialCodingTypeRepository = financialCodingTypeRepository;
        this.organizationRepository = organizationRepository;
        this.financialNumberingTypeRepository = financialNumberingTypeRepository;
        this.ledgerNumberingTypeRepository = ledgerNumberingTypeRepository;
    }

    @Override
    @Transactional()
    public List<FinancialLedgerTypeDto> getFinancialLedgerType(Long organizationId) {
        List<FinancialLedgerType> financialLedgerType = financialLedgerTypeRepository.findFinancialLedgerTypeByOrganizationId(organizationId);
        return financialLedgerType.stream().map(e -> FinancialLedgerTypeDto.builder().id(e.getId())
                .description(e.getDescription())
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
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list = financialLedgerTypeRepository.financialLedgerTypeList(param.getOrganizationId(), param.getFinancialCodingTypeId()
                , param.getFinancialCodingType(), param.getFinancialLedgerTypeId(), param.getFinancialLedgerType(), pageable);
        List<FinancialLedgerTypeResponse> financialLedgerTypeResponses = list.stream().map(item ->
                FinancialLedgerTypeResponse.builder()
                        .financialLedgerTypeId(Long.parseLong(item[0].toString()))
                        .description(item[1] == null ? "" : item[1].toString())
                        .financialCodingTypeId(Long.parseLong(item[2].toString()))
                        .activeFlag(Integer.parseInt(item[3].toString()) == 1)
                        .financialCodingTypeDescription(item[4] == null ? "" : item[4].toString())
                        .financialNumberingTypeDescription(item[5] == null ? "" : item[5].toString())
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
            throw new RuleException("شناسه کدینگ، خود را وارد نمایید");
        }
        List<Long> numberingTypeIdList = financialLedgerTypeRequest.getNumberingTypeIdList();
        for (Long numberingTypeId : numberingTypeIdList) {
            if (numberingTypeId == null) {
                throw new RuleException("شناسه انواع شماره گذاری، خود را وارد نمایید.");
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

    @Transactional
    public Boolean insertFinancialLedgerType(FinancialLedgerTypeRequest financialLedgerTypeRequest) {
        FinancialLedgerType financialLedgerTypeNew = new FinancialLedgerType();
        financialLedgerTypeNew.setDescription(financialLedgerTypeRequest.getDescription());
        Long financialCodingTypeId = financialLedgerTypeRequest.getFinancialCodingTypeId();
        Optional<FinancialCodingType> financialCodingType = financialCodingTypeRepository.findById(financialCodingTypeId);
        if (financialCodingType.isPresent()) {
            financialLedgerTypeNew.setFinancialCodingType(financialCodingType.get());
        } else {
            throw new RuleException("شناسه کدینگ، وارد شده معتبر نمی باشد ");
        }
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
            Optional<LedgerNumberingType> ledgerNumberingType = ledgerNumberingTypeRepository.findById(legerNumberingType);
            LedgerNumberingType ledgerNumberingTypeUpdate = ledgerNumberingType.get();
            ledgerNumberingTypeUpdate.setDeletedDate(LocalDateTime.now());
            ledgerNumberingTypeRepository.save(ledgerNumberingTypeUpdate);
        }
        List<Long> financialNumberingListRequest = financialLedgerTypeRequest.getNumberingTypeIdList();
        saveLedgerNumberingType(financialNumberingListRequest, financialLedgerTypeRequest, new FinancialLedgerType());
        return true;
    }

    private Boolean saveLedgerNumberingType(List<Long> financialNumberingListRequest, FinancialLedgerTypeRequest financialLedgerTypeRequest, FinancialLedgerType financialLedgerType) {
        Long financialLedgerTypeIdRequest = financialLedgerTypeRequest.getFinancialLedgerTypeId();
        for (Long financialNumberingTypeId : financialNumberingListRequest) {
            LedgerNumberingType ledgerNumberingTypeNew = new LedgerNumberingType();
            Optional<FinancialNumberingType> financialNumberingTypeTbl = financialNumberingTypeRepository.findById(financialNumberingTypeId);
            if (financialNumberingTypeTbl.isPresent()) {
                Long countByLedgerTypeIdAndNumberingTypeIdAndDeleteDate = ledgerNumberingTypeRepository.getCountByLedgerTypeIdAndNumberingTypeIdAndDeleteDate(financialLedgerTypeRequest.getFinancialLedgerTypeId()
                        , financialNumberingTypeTbl.get().getId());
                if (countByLedgerTypeIdAndNumberingTypeIdAndDeleteDate > 0) {
                    throw new RuleException("نوع شماره گذاری، برای این نوع دفتر مالی، قبلا ثبت شده است.");
                }
                if (financialLedgerTypeIdRequest == null) {
                    Optional<FinancialLedgerType> financialLedgerTypeFromInsert = financialLedgerTypeRepository.findById(financialLedgerType.getId());
                    if (financialLedgerTypeFromInsert.isPresent()) {
                        ledgerNumberingTypeNew.setFinancialLedgerType(financialLedgerTypeFromInsert.get());
                    } else {
                        throw new RuleException("شناسه نوع دفتر مالی، وارد شده معتبر نمی باشد.");
                    }
                } else {
                    Optional<FinancialLedgerType> financialLedgerTypeFromUpdate = financialLedgerTypeRepository.findById(financialLedgerTypeIdRequest);
                    if (financialLedgerTypeFromUpdate.isPresent()) {
                        ledgerNumberingTypeNew.setFinancialLedgerType(financialLedgerTypeFromUpdate.get());
                    } else {
                        throw new RuleException("شناسه نوع دفتر مالی، وارد شده معتبر نمی باشد.");
                    }
                }
                ledgerNumberingTypeNew.setFinancialNumberingType(financialNumberingTypeTbl.get());
                ledgerNumberingTypeRepository.save(ledgerNumberingTypeNew);
            } else {
                throw new RuleException("شناسه نوع شماره گذاری، وارد شده معتبر نمی باشد");
            }
        }
        return true;
    }
}
