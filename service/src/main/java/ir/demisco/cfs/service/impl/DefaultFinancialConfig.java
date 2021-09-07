package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialConfigRequest;
import ir.demisco.cfs.model.entity.FinancialConfig;
import ir.demisco.cfs.service.api.FinancialConfigService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
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
        Long financialAccountStructureCount = financialConfigRepository.getCountByFinancialConfigAndOrganizationAndUser(financialConfigRequest.getOrganizationId(),financialConfigRequest.getUserId());
        if (financialAccountStructureCount > 0) {
            throw new RuleException("برای این کاربر در این سازمان قبلا رکوردی ثبت شده است");
        }
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

}
