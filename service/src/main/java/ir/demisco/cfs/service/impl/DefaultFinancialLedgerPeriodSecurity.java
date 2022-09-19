package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.CheckLedgerPermissionInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialSecurityFilterRequest;
import ir.demisco.cfs.model.dto.response.FinancialSecurityOutputResponse;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodSecurityService;
import ir.demisco.cfs.service.api.FinancialSecurityService;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultFinancialLedgerPeriodSecurity implements FinancialLedgerPeriodSecurityService {
    private final FinancialSecurityService financialSecurityService;

    public DefaultFinancialLedgerPeriodSecurity(FinancialSecurityService financialSecurityService) {
        this.financialSecurityService = financialSecurityService;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean checkFinancialLedgerPeriodSecurity(CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest) {
        FinancialSecurityFilterRequest financialSecurityFilterRequest = new FinancialSecurityFilterRequest();
        financialSecurityFilterRequest.setSubjectId(null);
        financialSecurityFilterRequest.setActivityCode(checkLedgerPermissionInputRequest.getActivityCode());
        financialSecurityFilterRequest.setInputFromConfigFlag(false);
        financialSecurityFilterRequest.setFinancialLedgerTypeId(checkLedgerPermissionInputRequest.getLedgerTypeId());
        financialSecurityFilterRequest.setFinancialPeriodId(checkLedgerPermissionInputRequest.getPeriodId());
        financialSecurityFilterRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        financialSecurityFilterRequest.setUserId(SecurityHelper.getCurrentUser().getUserId());
        financialSecurityFilterRequest.setDepartmentId(null);
        financialSecurityFilterRequest.setFinancialDepartmentId(null);
        financialSecurityFilterRequest.setDocumentTypeId(null);
        financialSecurityFilterRequest.setSubjectId(null);
        financialSecurityFilterRequest.setCreatorUserId(SecurityHelper.getCurrentUser().getUserId());
        FinancialSecurityOutputResponse financialSecurityOutputResponse = financialSecurityService.hasPermission(financialSecurityFilterRequest, SecurityHelper.getCurrentUser().getOrganizationId());
        int resultSet = financialSecurityService.resultSet(financialSecurityFilterRequest);
        if (resultSet == 0) {
            if (financialSecurityOutputResponse.getPermissionMessage() == null) {
                throw new RuleException("عدم دسترسی به عملیات");
            } else {
                throw new RuleException(financialSecurityOutputResponse.getPermissionMessage());
            }
        } else if (resultSet > 1) {
            throw new RuleException(financialSecurityOutputResponse.getPermissionMessage());
        } else {
            return true;
        }

    }
}
