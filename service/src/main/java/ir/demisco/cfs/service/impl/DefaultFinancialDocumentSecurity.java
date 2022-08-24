package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDocumentSecurityInputRequest;
import ir.demisco.cfs.service.api.FinancialDocumentHeaderService;
import ir.demisco.cfs.service.api.FinancialDocumentSecurityService;
import ir.demisco.cfs.service.api.FinancialSecurityService;
import ir.demisco.cloud.core.middle.exception.RuleException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultFinancialDocumentSecurity implements FinancialDocumentSecurityService {

    public DefaultFinancialDocumentSecurity(FinancialDocumentHeaderService financialDocumentHeaderService, FinancialSecurityService financialSecurityService) {

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean getFinancialDocumentSecurity(FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest) {
        return true;
    }

    private void checkFinancialDocumentSecurity(FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest) {
        if (financialDocumentSecurityInputRequest.getSecurityModelRequest().getUserId() == null) {
            throw new RuleException("لطفا شناسه ی کاربر را وارد نمایید.");
        }
        if (financialDocumentSecurityInputRequest.getSecurityModelRequest().getDepartmentId() == null) {
            throw new RuleException("لطفا شناسه ی شعبه را وارد نمایید.");
        }
        if (financialDocumentSecurityInputRequest.getSecurityModelRequest().getActivityCode() == null) {
            throw new RuleException("لطفا کد نوع فعالیت را وارد نمایید.");
        }
        if (financialDocumentSecurityInputRequest.getSecurityModelRequest().getInputFromConfigFlag() == null) {
            throw new RuleException("لطفا فلگ تنظیمات را وارد نمایید.");
        }
    }
}
