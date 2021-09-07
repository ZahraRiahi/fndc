package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialConfigRequest;
import ir.demisco.cfs.model.dto.response.FinancialConfigDto;
import ir.demisco.cfs.model.entity.FinancialConfig;
import ir.demisco.cfs.service.api.FinancialConfigService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DefaultFinancialConfig implements FinancialConfigService {
    private final GridFilterService gridFilterService;
    private final FinancialConfigListGridProvider financialConfigListGridProvider;
    private final FinancialConfigRepository financialConfigRepository;
    private final OrganizationRepository organizationRepository;
    private final FinancialDepartmentRepository financialDepartmentRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final FinancialDocumentTypeRepository financialDocumentTypeRepository;
    private final FinancialLedgerTypeRepository financialLedgerTypeRepository;
    private final FinancialPeriodRepository financialPeriodRepository;

    public DefaultFinancialConfig(GridFilterService gridFilterService, FinancialConfigListGridProvider financialConfigListGridProvider, FinancialConfigRepository financialConfigRepository, OrganizationRepository organizationRepository, FinancialDepartmentRepository financialDepartmentRepository, ApplicationUserRepository applicationUserRepository, FinancialDocumentTypeRepository financialDocumentTypeRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository, FinancialPeriodRepository financialPeriodRepository) {
        this.gridFilterService = gridFilterService;
        this.financialConfigListGridProvider = financialConfigListGridProvider;
        this.financialConfigRepository = financialConfigRepository;
        this.organizationRepository = organizationRepository;
        this.financialDepartmentRepository = financialDepartmentRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialPeriodRepository = financialPeriodRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DataSourceResult getFinancialConfigByOrganizationIdAndUserAndDepartment(DataSourceRequest dataSourceRequest, Long organizationId) {
        return gridFilterService.filter(dataSourceRequest, financialConfigListGridProvider);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean saveOrUpdateFinancialConfig(FinancialConfigRequest financialConfigRequest) {
        FinancialConfig financialConfig = financialConfigRepository.findById(financialConfigRequest.getId() == null ? 0 : financialConfigRequest.getId()).orElse(new FinancialConfig());
        if (financialConfig.getId() != null) {
            financialConfig.setDeletedDate(LocalDateTime.now());
        }
        FinancialConfig financialConfigNew = new FinancialConfig();
        financialConfigNew.setOrganization(organizationRepository.getOne(100L));
        financialConfigNew.setFinancialDepartment(financialDepartmentRepository.getOne(financialConfigRequest.getFinancialDepartmentId()));
        financialConfigNew.setUser(applicationUserRepository.getOne(financialConfigRequest.getUserId()));
        financialConfigNew.setFinancialDocumentType(financialDocumentTypeRepository.getOne(financialConfigRequest.getFinancialDocumentTypeId()));
        financialConfigNew.setDocumentDescription(financialConfigRequest.getDocumentDescription());
        financialConfigNew.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialConfigRequest.getFinancialLedgerTypeId()));
        financialConfigNew.setFinancialPeriod(financialPeriodRepository.getOne(financialConfigRequest.getFinancialPeriodId()));
        financialConfigRepository.save(financialConfigNew);
        return true;
    }

    private Boolean convertFinancialConfigToDto(FinancialConfig financialConfig) {
        FinancialConfigDto.builder()
                .id(financialConfig.getId())
                .organizationId(financialConfig.getOrganization().getId())
                .financialDepartmentId(financialConfig.getFinancialDepartment().getId())
                .userId(financialConfig.getUser().getId())
                .financialDocumentTypeId(financialConfig.getFinancialDocumentType().getId())
                .documentDescription(financialConfig.getDocumentDescription())
                .financialLedgerTypeId(financialConfig.getFinancialLedgerType().getId())
                .financialPeriodId(financialConfig.getFinancialPeriod().getId())
                .build();
        return true;
    }

}
//
//
//    List<CentricPersonRole> centricPersonRoles = centricPersonRoleRepository.findByCentricAccountId(centricAccountId);
//    CentricAccount centricAccount;
//        if(!centricPersonRoles.isEmpty())
//
//    {
//        centricPersonRoles.forEach(e -> e.setDeletedDate(LocalDateTime.now()));
//    }
//
//    centricAccount =centricAccountRepository.findById(centricAccountId).
//
//    orElseThrow(() ->new
//
//    RuleException("ایتمی با این شناسه وجود ندارد"));
//        centricAccount.setDeletedDate(LocalDateTime.now());
//        return true;
//}
