package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDocumentTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeGetDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentTypeDto;
import ir.demisco.cfs.model.entity.FinancialDocumentType;
import ir.demisco.cfs.service.api.FinancialDocumentTypeService;
import ir.demisco.cfs.service.repository.FinancialConfigRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentTypeRepository;
import ir.demisco.cfs.service.repository.FinancialSystemRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialDocumentType implements FinancialDocumentTypeService {

    private final FinancialDocumentTypeRepository financialDocumentTypeRepository;
    private final GridFilterService gridFilterService;
    private final FinancialDocumentTypeProvider financialDocumentTypeProvider;
    private final OrganizationRepository organizationRepository;
    private final FinancialSystemRepository financialSystemRepository;
    private final FinancialConfigRepository financialConfigRepository;

    public DefaultFinancialDocumentType(FinancialDocumentTypeRepository financialDocumentTypeRepository, GridFilterService gridFilterService, FinancialDocumentTypeProvider financialDocumentTypeProvider, OrganizationRepository organizationRepository, FinancialSystemRepository financialSystemRepository, FinancialConfigRepository financialConfigRepository) {
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
        this.gridFilterService = gridFilterService;
        this.financialDocumentTypeProvider = financialDocumentTypeProvider;
        this.organizationRepository = organizationRepository;
        this.financialSystemRepository = financialSystemRepository;
        this.financialConfigRepository = financialConfigRepository;
    }

    @Override
    @Transactional
    public List<FinancialDocumentTypeGetDto> getNumberingFormatByOrganizationId(Long organizationId, Long userId, FinancialDocumentTypeRequest financialDocumentTypeRequest) {
        String financialSystem = null;
        if (financialDocumentTypeRequest.getFinancialSystemId() != null) {
            financialSystem = "financialSystem";
        } else {
            financialDocumentTypeRequest.setFinancialSystemId(0L);
        }
        return financialDocumentTypeRepository.findByOrganizationId(financialDocumentTypeRequest.getSearchStatusFlag(), SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentTypeRequest.getSecurityModelRequest().getActivityCode()
                , new TypedParameterValue(StandardBasicTypes.LONG, financialDocumentTypeRequest.getSecurityModelRequest().getFinancialPeriodId())
                , new TypedParameterValue(StandardBasicTypes.LONG, financialDocumentTypeRequest.getSecurityModelRequest().getFinancialDepartmentId())
                , new TypedParameterValue(StandardBasicTypes.LONG, financialDocumentTypeRequest.getSecurityModelRequest().getFinancialLedgerId())
                , new TypedParameterValue(StandardBasicTypes.LONG, financialDocumentTypeRequest.getSecurityModelRequest().getDepartmentId())
                , SecurityHelper.getCurrentUser().getUserId(), financialSystem, financialDocumentTypeRequest.getFinancialSystemId())

                .stream().map(objects -> FinancialDocumentTypeGetDto.builder()
                        .id(Long.parseLong(objects[0] == null ? null : objects[0].toString()))
                        .description(objects[1] == null ? null : objects[1].toString())
                        .activeFlag(Integer.parseInt(objects[2].toString()) == 1)
                        .disabled(Integer.parseInt(objects[3].toString()) == 1)
                        .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean deleteFinancialDocumentTypeById(Long financialDocumentTypeId) {
        FinancialDocumentType financialDocumentType = financialDocumentTypeRepository.findById(financialDocumentTypeId)
                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        Long documentTypeIdForDelete = financialDocumentTypeRepository.getDocumentTypeIdForDelete(financialDocumentType.getId());
        if (documentTypeIdForDelete > 0) {
            throw new RuleException("fin.financialDocumentType.check.for.delete");
        } else {
            financialDocumentTypeRepository.deleteById(financialDocumentTypeId);
            return true;
        }
    }

    @Override
    public DataSourceResult getFinancialDocumentTypeOrganizationIdAndFinancialSystemId(DataSourceRequest dataSourceRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("organization", organizationId, DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest, financialDocumentTypeProvider);
    }

    @Override
    public ResponseFinancialDocumentTypeDto save(FinancialDocumentTypeDto financialDocumentTypeDto) {

        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        FinancialDocumentType financialDocumentType = financialDocumentTypeRepository.
                findById(financialDocumentTypeDto.getId() == null ? 0L : financialDocumentTypeDto.getId()).orElse(new FinancialDocumentType());
        financialDocumentType.setDescription(financialDocumentTypeDto.getDescription());
        financialDocumentType.setOrganization(organizationRepository.getOne(organizationId));
        financialDocumentType.setActiveFlag(financialDocumentTypeDto.getActiveFlag());
        financialDocumentType.setAutomaticFlag(false);
        financialDocumentType.setFinancialSystem(financialSystemRepository.getOne(financialDocumentTypeDto.getFinancialSystemId()));
        financialDocumentTypeRepository.save(financialDocumentType);
        return convertToDto(financialDocumentType);
    }

    @Override
    public ResponseFinancialDocumentTypeDto update(FinancialDocumentTypeDto financialDocumentTypeDto) {
        FinancialDocumentType financialDocumentType = financialDocumentTypeRepository.
                findById(financialDocumentTypeDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        if (!financialDocumentTypeDto.getAutomaticFlag()) {
            if (!financialDocumentTypeDto.getActiveFlag()) {
                List<Long> financialConfigCount = financialConfigRepository.findByFinancialConfigByFinancialDocumentTypeId(financialDocumentTypeDto.getId());
                if (financialConfigCount.size() != 0) {
                    throw new RuleException("fin.financialDocumentType.update");
                }
            }
            financialDocumentType.setDescription(financialDocumentTypeDto.getDescription());
            financialDocumentType.setActiveFlag(financialDocumentTypeDto.getActiveFlag());
            financialDocumentType.setFinancialSystem(financialSystemRepository.getOne(financialDocumentTypeDto.getFinancialSystemId()));
            financialDocumentTypeRepository.save(financialDocumentType);
            return convertToDto(financialDocumentType);
        } else {
            throw new RuleException("fin.financialDocumentType.notEditDocument");
        }
    }

    private ResponseFinancialDocumentTypeDto convertToDto(FinancialDocumentType financialDocumentType) {

        return ResponseFinancialDocumentTypeDto.builder()
                .id(financialDocumentType.getId())
                .description(financialDocumentType.getDescription())
                .activeFlag(financialDocumentType.getActiveFlag())
                .automaticFlag(financialDocumentType.getAutomaticFlag())
                .financialSystemId(financialDocumentType.getFinancialSystem().getId())
                .organizationId(financialDocumentType.getOrganization().getId())
                .message("عملیات موفقیت آمیز بود")
                .build();
    }
}
