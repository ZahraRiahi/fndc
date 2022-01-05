package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialNumberingFormatDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialNumberingFormatDto;
import ir.demisco.cfs.model.entity.FinancialNumberingFormat;
import ir.demisco.cfs.service.api.FinancialNumberingFormatService;
import ir.demisco.cfs.service.repository.FinancialNumberingFormatRepository;
import ir.demisco.cfs.service.repository.FinancialNumberingFormatTypeRepository;
import ir.demisco.cfs.service.repository.FinancialNumberingTypeRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class DefaultFinancialNumberingFormat implements FinancialNumberingFormatService {

    private final GridFilterService gridFilterService;
    private final OrganizationRepository organizationRepository;
    private final FinancialNumberingFormatGridProvider financialNumberingFormatGridProvider;
    private final FinancialNumberingFormatRepository financialNumberingFormatRepository;
    private final FinancialNumberingFormatTypeRepository financialNumberingFormatTypeRepository;
    private final FinancialNumberingTypeRepository financialNumberingTypeRepository;

    public DefaultFinancialNumberingFormat(GridFilterService gridFilterService, OrganizationRepository organizationRepository,
                                           FinancialNumberingFormatGridProvider financialNumberingFormatGridProvider,
                                           FinancialNumberingFormatRepository financialNumberingFormatRepository, FinancialNumberingFormatTypeRepository financialNumberingFormatTypeRepository, FinancialNumberingTypeRepository financialNumberingTypeRepository) {
        this.gridFilterService = gridFilterService;
        this.organizationRepository = organizationRepository;
        this.financialNumberingFormatGridProvider = financialNumberingFormatGridProvider;
        this.financialNumberingFormatRepository = financialNumberingFormatRepository;
        this.financialNumberingFormatTypeRepository = financialNumberingFormatTypeRepository;
        this.financialNumberingTypeRepository = financialNumberingTypeRepository;
    }

    @Override
    @Transactional
    public DataSourceResult getNumberingFormatByOrganizationId(Long organizationId, DataSourceRequest dataSourceRequest) {

//        Asserts.notNull(organizationId, "organizationId is null");
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters()
                .add(DataSourceRequest.FilterDescriptor.create("organization.id", organizationId, DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("financialNumberingFormatType.deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("financialNumberingType.deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest, financialNumberingFormatGridProvider);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean save(FinancialNumberingFormatDto financialNumberingFormatDto) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Object formatType;
        if (financialNumberingFormatDto.getFinancialNumberingFormatTypeId() != null) {
            formatType = "formatType";
        } else {
            financialNumberingFormatDto.setFinancialNumberingFormatTypeId(0L);
            formatType = null;
        }
        FinancialNumberingFormat financialNumberingFormat =
                financialNumberingFormatRepository.getFormatByType(formatType, financialNumberingFormatDto.getFinancialNumberingFormatTypeId(),
                        financialNumberingFormatDto.getFinancialNumberingTypeId(), organizationId);
        if (financialNumberingFormat != null) {
            throw new RuleException("fin.financialNumberingFormat.existNumberingFormat");
        } else {
            FinancialNumberingFormat numberingFormat = financialNumberingFormatRepository.findById(financialNumberingFormatDto.getId() == null ? 0L :
                    financialNumberingFormatDto.getId()).orElse(new FinancialNumberingFormat());
            numberingFormat.setOrganization(organizationRepository.getOne(organizationId));
            numberingFormat.setDescription(financialNumberingFormatDto.getDescription());
            numberingFormat.setFinancialNumberingFormatType(financialNumberingFormatDto.getFinancialNumberingFormatTypeId() != 0 ?
                    financialNumberingFormatTypeRepository.getOne(financialNumberingFormatDto.getFinancialNumberingFormatTypeId()) : null);
            numberingFormat.setFinancialNumberingType(financialNumberingTypeRepository.getOne(financialNumberingFormatDto.getFinancialNumberingTypeId()));
            numberingFormat.setReseter(financialNumberingFormatDto.getReseter());
            numberingFormat.setSerialLength(financialNumberingFormatDto.getSerialLength());
            numberingFormat.setFirstSerial(financialNumberingFormatDto.getFirstSerial());
            financialNumberingFormatRepository.save(numberingFormat);
            return true;
        }
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean upDate(FinancialNumberingFormatDto financialNumberingFormatDto) {
        throw new RuleException("fin.financialNumberingFormat.update");
    }

    private ResponseFinancialNumberingFormatDto convertNumberingFormatToDto(FinancialNumberingFormat updateFormat) {

        return ResponseFinancialNumberingFormatDto.builder()
                .id(updateFormat.getId())
                .description(updateFormat.getDescription())
                .financialNumberingFormatTypeId(updateFormat.getFinancialNumberingFormatType() != null ?
                        updateFormat.getFinancialNumberingFormatType().getId() : null)
                .financialNumberingFormatTypeDescription(updateFormat.getFinancialNumberingFormatType() != null ?
                        updateFormat.getFinancialNumberingFormatType().getDescription() : null)
                .financialNumberingTypeId(updateFormat.getFinancialNumberingType().getId())
                .message("عملیات موفقیت آمیز بود")
                .build();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteNumberingFormatById(Long numberingFormatId) {
        FinancialNumberingFormat deleteNumberingFormat = financialNumberingFormatRepository.findById(numberingFormatId)
                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        deleteNumberingFormat.setDeletedDate(LocalDateTime.now());
        financialNumberingFormatRepository.save(deleteNumberingFormat);
        return true;
    }
}
