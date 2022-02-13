package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDocumentSecurityInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialSecurityFilterRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentHeaderOutputResponse;
import ir.demisco.cfs.model.dto.response.FinancialSecurityOutputResponse;
import ir.demisco.cfs.service.api.FinancialDocumentHeaderService;
import ir.demisco.cfs.service.api.FinancialDocumentSecurityService;
import ir.demisco.cfs.service.api.FinancialSecurityService;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultFinancialDocumentSecurity implements FinancialDocumentSecurityService {
    private final FinancialDocumentHeaderService financialDocumentHeaderService;
    private final FinancialSecurityService financialSecurityService;

    public DefaultFinancialDocumentSecurity(FinancialDocumentHeaderService financialDocumentHeaderService, FinancialSecurityService financialSecurityService) {
        this.financialDocumentHeaderService = financialDocumentHeaderService;
        this.financialSecurityService = financialSecurityService;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean getFinancialDocumentSecurity(FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest) {
        FinancialSecurityFilterRequest financialSecurityFilterRequest = new FinancialSecurityFilterRequest();
        if (financialDocumentSecurityInputRequest.getFinancialDocumentId() != null) {
            FinancialDocumentHeaderOutputResponse financialDocumentHeaderOutputResponse = financialDocumentHeaderService.getFinancialDocumentHeaderBytId(financialDocumentSecurityInputRequest.getFinancialDocumentId());
            financialSecurityFilterRequest.setSubjectId(null);
            financialSecurityFilterRequest.setActivityCode(financialDocumentSecurityInputRequest.getActivityCode());
            financialSecurityFilterRequest.setInputFromConfigFlag(false);
            financialSecurityFilterRequest.setDepartmentId(financialDocumentHeaderOutputResponse.getDepartmentId());
            financialSecurityFilterRequest.setFinancialDepartmentId(financialDocumentHeaderOutputResponse.getFinancialDepartmentId());
            financialSecurityFilterRequest.setFinancialPeriodId(financialDocumentHeaderOutputResponse.getFinancialPeriodId());
            financialSecurityFilterRequest.setDocumentTypeId(financialDocumentHeaderOutputResponse.getFinancialDocumentTypeId());
            financialSecurityFilterRequest.setCreatorUserId(financialDocumentHeaderOutputResponse.getCreatorId());
            financialSecurityFilterRequest.setUserId(financialDocumentHeaderOutputResponse.getCreatorId());
        } else {
            if (financialDocumentSecurityInputRequest.getSecurityModelRequest().getUserId() == null) {
                throw new RuleException("لطفا شناسه ی کاربر را وارد نمایید.");
            }
            if (financialDocumentSecurityInputRequest.getSecurityModelRequest().getDocumentTypeId() == null) {
                throw new RuleException("لطفا شناسه ی نوع سند را وارد نمایید.");
            }
            if (financialDocumentSecurityInputRequest.getSecurityModelRequest().getActivityCode() == null) {
                throw new RuleException("لطفا کد نوع فعالیت را وارد نمایید.");
            }
            if (financialDocumentSecurityInputRequest.getSecurityModelRequest().getInputFromConfigFlag() == null) {
                throw new RuleException("لطفا فلگ تنظیمات را وارد نمایید.");
            }
            financialSecurityFilterRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
            financialSecurityFilterRequest.setUserId(financialDocumentSecurityInputRequest.getSecurityModelRequest().getUserId());
            financialSecurityFilterRequest.setDepartmentId(financialDocumentSecurityInputRequest.getSecurityModelRequest().getDepartmentId());
            financialSecurityFilterRequest.setFinancialDepartmentId(financialDocumentSecurityInputRequest.getSecurityModelRequest().getFinancialDepartmentId());
            financialSecurityFilterRequest.setFinancialLedgerId(financialDocumentSecurityInputRequest.getSecurityModelRequest().getFinancialLedgerId());
            financialSecurityFilterRequest.setFinancialPeriodId(financialDocumentSecurityInputRequest.getSecurityModelRequest().getFinancialPeriodId());
            financialSecurityFilterRequest.setDocumentTypeId(financialDocumentSecurityInputRequest.getSecurityModelRequest().getDocumentTypeId());
            financialSecurityFilterRequest.setSubjectId(financialDocumentSecurityInputRequest.getSecurityModelRequest().getSubjectId());
            financialSecurityFilterRequest.setActivityCode(financialDocumentSecurityInputRequest.getSecurityModelRequest().getActivityCode());
            financialSecurityFilterRequest.setInputFromConfigFlag(financialDocumentSecurityInputRequest.getSecurityModelRequest().getInputFromConfigFlag());
            financialSecurityFilterRequest.setCreatorUserId(financialDocumentSecurityInputRequest.getSecurityModelRequest().getCreatorUserId());
        }
        FinancialSecurityOutputResponse financialSecurityOutputResponse = financialSecurityService.hasPermission(financialSecurityFilterRequest, SecurityHelper.getCurrentUser().getOrganizationId());
        if (financialSecurityOutputResponse.getHasPermissionStatus() == null) {
            if (financialSecurityOutputResponse.getPermissionMessage() == null) {
                throw new RuleException("عدم دسترسی به عملیات");
            } else {
                throw new RuleException(financialSecurityOutputResponse.getPermissionMessage());
            }
        } else if (financialSecurityOutputResponse.getHasPermissionStatus() > 1) {
            throw new RuleException(financialSecurityOutputResponse.getPermissionMessage());
        } else {
            return true;
        }
    }
}
