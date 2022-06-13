package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialConfigRequest;
import ir.demisco.cfs.model.entity.FinancialConfig;
import ir.demisco.cfs.service.api.FinancialConfigService;
import ir.demisco.cfs.service.repository.ApplicationUserRepository;
import ir.demisco.cfs.service.repository.DepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialConfigRepository;
import ir.demisco.cfs.service.repository.FinancialDepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentTypeRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
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
    private final DepartmentRepository departmentRepository;

    public DefaultFinancialConfig(GridFilterService gridFilterService, FinancialConfigListGridProvider financialConfigListGridProvider, FinancialConfigRepository financialConfigRepository, OrganizationRepository organizationRepository, FinancialDepartmentRepository financialDepartmentRepository, ApplicationUserRepository applicationUserRepository, FinancialDocumentTypeRepository financialDocumentTypeRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository, FinancialPeriodRepository financialPeriodRepository, DepartmentRepository departmentRepository) {
        this.gridFilterService = gridFilterService;
        this.financialConfigListGridProvider = financialConfigListGridProvider;
        this.financialConfigRepository = financialConfigRepository;
        this.organizationRepository = organizationRepository;
        this.financialDepartmentRepository = financialDepartmentRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialPeriodRepository = financialPeriodRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DataSourceResult getFinancialConfigByOrganizationIdAndUserAndDepartment(DataSourceRequest dataSourceRequest, Long organizationId) {
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("organization.id", SecurityHelper.getCurrentUser().getOrganizationId(), DataSourceRequest.Operators.EQUALS));
        return gridFilterService.filter(dataSourceRequest, financialConfigListGridProvider);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean saveOrUpdateFinancialConfig(FinancialConfigRequest financialConfigRequest) {
        FinancialConfig financialConfig = financialConfigRepository.findById(financialConfigRequest.getId() == null ? 0 : financialConfigRequest.getId()).orElse(new FinancialConfig());
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        if (financialConfig.getId() != null) {
            financialConfigRepository.deleteById(financialConfigRequest.getId());
        }
        Long financialAccountStructureCount = financialConfigRepository.getCountByFinancialConfigAndOrganizationAndUser(organizationId, financialConfigRequest.getUserId());
        if (financialAccountStructureCount > 0) {
            throw new RuleException("fin.financialConfig.existAccountStructure");
        }
        FinancialConfig financialConfigNew = new FinancialConfig();
        financialConfigNew.setOrganization(organizationRepository.getOne(organizationId));
        if (financialConfigRequest.getFinancialDepartmentId() != null) {
            financialConfigNew.setFinancialDepartment(financialDepartmentRepository.getOne(financialConfigRequest.getFinancialDepartmentId()));
        }
        if (financialConfigRequest.getUserId() != null) {
            financialConfigNew.setUser(applicationUserRepository.getOne(financialConfigRequest.getUserId()));
        }
        if (financialConfigRequest.getFinancialDocumentTypeId() != null) {
            financialConfigNew.setFinancialDocumentType(financialDocumentTypeRepository.getOne(financialConfigRequest.getFinancialDocumentTypeId()));
        }
        financialConfigNew.setDocumentDescription(financialConfigRequest.getDocumentDescription());
        if (financialConfigRequest.getFinancialLedgerTypeId() != null) {
            financialConfigNew.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialConfigRequest.getFinancialLedgerTypeId()));
        }
        if (financialConfigRequest.getFinancialPeriodId() != null) {
            financialConfigNew.setFinancialPeriod(financialPeriodRepository.getOne(financialConfigRequest.getFinancialPeriodId()));
        }

        if (financialConfigRequest.getDepartmentId() != null) {
            financialConfigNew.setDepartment(departmentRepository.getOne(financialConfigRequest.getDepartmentId()));
        }
        financialConfigRepository.save(financialConfigNew);
        return true;
    }

}
