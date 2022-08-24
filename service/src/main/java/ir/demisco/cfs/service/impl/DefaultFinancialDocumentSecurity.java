package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDocumentSecurityInputRequest;
import ir.demisco.cfs.service.api.FinancialDocumentHeaderService;
import ir.demisco.cfs.service.api.FinancialDocumentSecurityService;
import ir.demisco.cfs.service.api.FinancialSecurityService;
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

}
