package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialConfigRequest;
import ir.demisco.cfs.model.dto.response.FinancialConfigResponse;
import ir.demisco.cfs.model.entity.FinancialConfig;
import ir.demisco.cfs.service.api.FinancialConfigService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public Boolean saveOrUpdateFinancialConfig(FinancialConfigRequest financialConfigRequest) {
        Long financialConfigCount = financialConfigRepository.getFinancialConfigById(financialConfigRequest.getId());
//        if (financialConfigCount == null) {
//            FinancialConfig financialConfig = new FinancialConfig();
//            financialConfig.setOrganization(organizationRepository.getOne(100L));
//            financialConfig.setFinancialDepartment(financialDepartmentRepository.getOne(financialConfigRequest.getFinancialDepartmentId()));
//            financialConfig.setUser(applicationUserRepository.getOne(financialConfigRequest.getUserId()));
//            financialConfig.setFinancialDocumentType(financialDocumentTypeRepository.getOne(financialConfigRequest.getFinancialDocumentTypeId()));
//            financialConfig.setDocumentDescription(financialConfigRequest.getDocumentDescription());
//            financialConfig.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialConfigRequest.getFinancialLedgerTypeId()));
//            financialConfig.setFinancialPeriod(financialPeriodRepository.getOne(financialConfigRequest.getFinancialPeriodId()));
//
//            accountRelatedDescription = financialConfigRepository.save(accountRelatedDescription);
//            return convertAccountRelatedDescriptionDto(accountRelatedDescription);
//        }

        return null;
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
