package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.FinancialLedgerTypeParameterDto;
import ir.demisco.cfs.model.dto.request.FinancialLedgerTypeRequest;
import ir.demisco.cfs.model.dto.request.FinancialSecurityFilterRequest;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentLedgerDto;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentLedgerResponse;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeListResponse;
import ir.demisco.cfs.model.entity.FinancialCodingType;
import ir.demisco.cfs.model.entity.FinancialLedgerType;
import ir.demisco.cfs.model.entity.FinancialNumberingType;
import ir.demisco.cfs.model.entity.LedgerNumberingType;
import ir.demisco.cfs.service.api.FinancialLedgerTypeService;
import ir.demisco.cfs.service.repository.FinancialCodingTypeRepository;
import ir.demisco.cfs.service.repository.FinancialDepartmentLedgerRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cfs.service.repository.FinancialNumberingTypeRepository;
import ir.demisco.cfs.service.repository.LedgerNumberingTypeRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public List<FinancialLedgerTypeDto> getFinancialLedgerType(FinancialSecurityFilterRequest financialSecurityFilterRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialSecurityFilterRequest.setOrganizationId(organizationId);
        financialSecurityFilterRequest.setUserId(SecurityHelper.getCurrentUser().getUserId());
        financialSecurityFilterRequest.setCreatorUserId(SecurityHelper.getCurrentUser().getUserId());
        if (financialSecurityFilterRequest.getUserId() == null) {
            throw new RuleException("fin.security.check.user.id");
        }
        if (financialSecurityFilterRequest.getDepartmentId() == null) {
            throw new RuleException("fin.security.check.department.id");
        }
        if (financialSecurityFilterRequest.getActivityCode() == null) {
            throw new RuleException("fin.security.check.activity.code");
        }
        if (financialSecurityFilterRequest.getInputFromConfigFlag() == null) {
            throw new RuleException("fin.security.check.input.from.config.flag");
        }
        return financialLedgerTypeRepository.findFinancialLedgerTypeByOrganizationId(
                organizationId,
                organizationId,
                financialSecurityFilterRequest.getActivityCode()
                , new TypedParameterValue(StandardBasicTypes.LONG, financialSecurityFilterRequest.getFinancialPeriodId())
                , new TypedParameterValue(StandardBasicTypes.LONG, financialSecurityFilterRequest.getDocumentTypeId())
                , new TypedParameterValue(StandardBasicTypes.LONG, financialSecurityFilterRequest.getCreatorUserId())
                , new TypedParameterValue(StandardBasicTypes.LONG, financialSecurityFilterRequest.getFinancialDepartmentId())
                , financialSecurityFilterRequest.getDepartmentId()
                , financialSecurityFilterRequest.getUserId())
                .stream().map(item -> FinancialLedgerTypeDto.builder()
                        .id(Long.parseLong(item[0] == null ? null : item[0].toString()))
                        .code(item[1] == null ? null : item[1].toString())
                        .description(item[2] == null ? null : item[2].toString())
                        .disabled(Integer.parseInt(item[3].toString()) == 1)
                        .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DataSourceResult financialLedgerTypeList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialLedgerTypeParameterDto param = setParameterToDto(filters);
        param.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        param.setUserId(SecurityHelper.getCurrentUser().getUserId());
        param.setCreatorUserId(SecurityHelper.getCurrentUser().getUserId());
        if (param.getUserId() == null) {
            throw new RuleException("fin.security.check.user.id");
        }
        if (param.getDepartmentId() == null) {
            throw new RuleException("fin.security.check.department.id");
        }
        if (param.getActivityCode() == null) {
            throw new RuleException("fin.security.check.activity.code");
        }
        if (param.getInputFromConfigFlag() == null) {
            throw new RuleException("fin.security.check.input.from.config.flag");
        }

        List<Object[]> list = financialLedgerTypeRepository.financialLedgerTypeList(param.getOrganizationId(), -1L
                , param.getActivityCode()
                , new TypedParameterValue(StandardBasicTypes.LONG, param.getFinancialPeriodId())
                , new TypedParameterValue(StandardBasicTypes.LONG, param.getDocumentTypeId())
                , new TypedParameterValue(StandardBasicTypes.LONG, param.getCreatorUserId())
                , new TypedParameterValue(StandardBasicTypes.LONG, param.getFinancialDepartmentId())
                , param.getDepartmentId()
                , param.getUserId()
                , param.getFinancialCodingTypeId()
                , param.getFinancialCodingType()
                , param.getFinancialLedgerTypeId()
                , param.getFinancialLedgerType());
        List<Sort.Order> sorts = new ArrayList<>();
        dataSourceRequest.getSort()
                .forEach((DataSourceRequest.SortDescriptor sortDescriptor) ->
                        {
                            if (sortDescriptor.getDir().equals("asc")) {
                                sorts.add(Sort.Order.asc(sortDescriptor.getField()));
                            } else {
                                sorts.add(Sort.Order.desc(sortDescriptor.getField()));
                            }
                        }
                );
        List<FinancialLedgerTypeListResponse> financialLedgerTypeListResponses =
                list.stream().map(item -> FinancialLedgerTypeListResponse.builder()
                        .financialLedgerTypeId(Long.parseLong(item[0].toString()))
                        .description(item[1] == null ? "" : item[1].toString())
                        .financialCodingTypeId(Long.parseLong(item[2].toString()))
                        .activeFlag(Integer.parseInt(item[3].toString()) == 1)
                        .financialCodingTypeDescription(item[4] == null ? "" : item[4].toString())
                        .financialNumberingTypeDescription(item[5] == null ? "" : item[5].toString())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialLedgerTypeListResponses.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setTotal(list.size());
        return dataSourceResult;


    }

    private FinancialLedgerTypeParameterDto setParameterToDto(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto = new FinancialLedgerTypeParameterDto();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "id":
                    checkIdSet(financialLedgerTypeParameterDto, item);
                    break;
                case "financialCodingTypeId":
                    checkFinancialCodingTypeIdSet(financialLedgerTypeParameterDto, item);
                    break;
                case "departmentId":
                    checkDepartmentIdSet(financialLedgerTypeParameterDto, item);
                    break;
                case "financialDepartmentId":
                    checkFinancialDepartmentIdSet(financialLedgerTypeParameterDto, item);
                    break;
                case "financialLedgerId":
                    checkFinancialLedgerIdSet(financialLedgerTypeParameterDto, item);
                    break;
                case "financialPeriodId":
                    checkFinancialPeriodIdSet(financialLedgerTypeParameterDto, item);
                    break;
                case "documentTypeId":
                    checkDocumentTypeIdSet(financialLedgerTypeParameterDto, item);
                    break;
                case "subjectId":
                    checkSubjectIdSet(financialLedgerTypeParameterDto, item);
                    break;
                case "activityCode":
                    checkActivityCodeSet(financialLedgerTypeParameterDto, item);
                    break;
                case "inputFromConfigFlag":
                    checkInputFromConfigFlag(financialLedgerTypeParameterDto, item);
                    break;
                default:

                    break;
            }
        }
        return financialLedgerTypeParameterDto;
    }

    private void checkInputFromConfigFlag(FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialLedgerTypeParameterDto.setInputFromConfigFlag((Boolean) item.getValue());
        } else {
            financialLedgerTypeParameterDto.setInputFromConfigFlag(null);
        }
    }

    private void checkIdSet(FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialLedgerTypeParameterDto.setFinancialLedgerType("setFinancialLedgerType");
            financialLedgerTypeParameterDto.setFinancialLedgerTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            financialLedgerTypeParameterDto.setFinancialLedgerType(null);
            financialLedgerTypeParameterDto.setFinancialLedgerTypeId(0L);
        }
    }

    private void checkFinancialCodingTypeIdSet(FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialLedgerTypeParameterDto.setFinancialCodingType("financialCodingType");
            financialLedgerTypeParameterDto.setFinancialCodingTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            financialLedgerTypeParameterDto.setFinancialCodingType(null);
            financialLedgerTypeParameterDto.setFinancialCodingTypeId(0L);
        }
    }

    private void checkDepartmentIdSet(FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialLedgerTypeParameterDto.setDepartmentId(Long.parseLong(item.getValue().toString()));
        } else {
            financialLedgerTypeParameterDto.setDepartmentId(null);
        }
    }

    private void checkFinancialDepartmentIdSet(FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialLedgerTypeParameterDto.setFinancialDepartmentId(Long.parseLong(item.getValue().toString()));
        } else {
            financialLedgerTypeParameterDto.setFinancialDepartmentId(null);
        }
    }

    private void checkFinancialLedgerIdSet(FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialLedgerTypeParameterDto.setFinancialLedgerId(Long.parseLong(item.getValue().toString()));
        } else {
            financialLedgerTypeParameterDto.setFinancialLedgerId(null);
        }
    }

    private void checkFinancialPeriodIdSet(FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialLedgerTypeParameterDto.setDocumentTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            financialLedgerTypeParameterDto.setDocumentTypeId(null);
        }
    }

    private void checkDocumentTypeIdSet(FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialLedgerTypeParameterDto.setDocumentTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            financialLedgerTypeParameterDto.setDocumentTypeId(null);
        }
    }

    private void checkSubjectIdSet(FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialLedgerTypeParameterDto.setSubjectId(Long.parseLong(item.getValue().toString()));
        } else {
            financialLedgerTypeParameterDto.setSubjectId(null);
        }
    }

    private void checkActivityCodeSet(FinancialLedgerTypeParameterDto financialLedgerTypeParameterDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialLedgerTypeParameterDto.setActivityCode(item.getValue().toString());
        } else {
            financialLedgerTypeParameterDto.setActivityCode(null);
        }
    }

    @Override
    @Transactional
    public void saveFinancialLedgerType(FinancialLedgerTypeRequest financialLedgerTypeRequest) {
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
            return;
        }
        Optional<FinancialLedgerType> financialLedgerTypeTbl = financialLedgerTypeRepository.findById(financialLedgerTypeId);
        if (financialLedgerTypeTbl.isPresent()) {
            FinancialLedgerType financialLedgerType = financialLedgerTypeTbl.get();
            updateFinancialLedgerType(financialLedgerType, financialLedgerTypeRequest);
        } else {
            insertFinancialLedgerType(financialLedgerTypeRequest);
        }
    }

    @Override
    public List<FinancialDepartmentLedgerResponse> getFinancialLedgerByDepartmentId(FinancialDepartmentLedgerDto departmentLedgerDto) {
        List<Object[]> financialDepartmentLedgerListObject = financialDepartmentLedgerRepository.findByFinancialDepartmentId(departmentLedgerDto.getDepartmentId());
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
        financialLedgerTypeNew.setOrganization(organizationRepository.getOne(SecurityHelper.getCurrentUser().getOrganizationId()));
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
            FinancialLedgerType financialLedgerTypeFromInsert = null;
            if (financialLedgerType.getId() != null) {
                financialLedgerTypeFromInsert = financialLedgerTypeRepository.findById(financialLedgerType.getId()).orElseThrow(() -> new RuleException("fin.financialLedgerType.notValidNumberingType"));
            }
            FinancialLedgerType financialLedgerTypeFromUpdate = null;
            if (financialLedgerTypeRequest.getFinancialLedgerTypeId() != null && financialLedgerTypeIdRequest!=null) {
                financialLedgerTypeFromUpdate = financialLedgerTypeRepository.findById(financialLedgerTypeIdRequest).orElseThrow(() -> new RuleException("fin.financialLedgerType.notValidNumberingType"));
            }
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

