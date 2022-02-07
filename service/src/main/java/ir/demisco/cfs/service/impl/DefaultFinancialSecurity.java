package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialSecurityFilterRequest;
import ir.demisco.cfs.model.dto.response.FinancialSecurityOutputResponse;
import ir.demisco.cfs.service.api.FinancialSecurityService;
import ir.demisco.cfs.service.repository.FinancialActivityTypeRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultFinancialSecurity implements FinancialSecurityService {
    private final FinancialActivityTypeRepository financialActivityTypeRepository;

    public DefaultFinancialSecurity(FinancialActivityTypeRepository financialActivityTypeRepository) {
        this.financialActivityTypeRepository = financialActivityTypeRepository;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public FinancialSecurityOutputResponse hasPermission(FinancialSecurityFilterRequest financialSecurityFilterRequest, Long organizationId) {
        if (financialSecurityFilterRequest.getUserId() == null) {
            throw new RuleException("لطفا شناسه ی کاربر را وارد نمایید.");
        }
        if (financialSecurityFilterRequest.getDocumentTypeId() == null) {
            throw new RuleException("لطفا شناسه ی نوع سند را وارد نمایید.");
        }
        if (financialSecurityFilterRequest.getActivityCode() == null) {
            throw new RuleException("لطفا کد نوع فعالیت را وارد نمایید.");
        }
        if (financialSecurityFilterRequest.getInputFromConfigFlag() == null) {
            throw new RuleException("لطفا فلگ تنظیمات را وارد نمایید.");
        }
        List<Object[]> financialDocumentItemList = financialActivityTypeRepository.getFinancialActivityTypeByActivityCode(financialSecurityFilterRequest.getActivityCode());
        FinancialSecurityOutputResponse financialSecurityOutputResponse = new FinancialSecurityOutputResponse();
        if (financialDocumentItemList.size() != 0 && financialDocumentItemList.get(0)[0] == null) {
            financialSecurityOutputResponse.setPermissionMessage("اشکال در واکشی اطلاعات امنیت سیستم");
            financialSecurityOutputResponse.setHasPermissionStatus(2L);
        } else {
            financialSecurityOutputResponse.setHasPermissionStatus(1L);
        }
        return financialSecurityOutputResponse;
    }
}
