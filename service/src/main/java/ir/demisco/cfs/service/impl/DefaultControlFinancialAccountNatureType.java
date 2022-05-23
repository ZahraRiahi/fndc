package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.ControlFinancialAccountNatureTypeByAccountRequest;
import ir.demisco.cfs.model.dto.request.ControlFinancialAccountNatureTypeInputRequest;
import ir.demisco.cfs.model.dto.response.ControlFinancialAccountNatureTypeByAccountResponse;
import ir.demisco.cfs.model.dto.response.ControlFinancialAccountNatureTypeOutputResponse;
import ir.demisco.cfs.service.api.ControlFinancialAccountNatureTypeService;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultControlFinancialAccountNatureType implements ControlFinancialAccountNatureTypeService {
    private final FinancialDocumentItemRepository financialDocumentItemRepository;

    public DefaultControlFinancialAccountNatureType(FinancialDocumentItemRepository financialDocumentItemRepository) {
        this.financialDocumentItemRepository = financialDocumentItemRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<ControlFinancialAccountNatureTypeByAccountResponse> getFinancialNatureControlByAccount(ControlFinancialAccountNatureTypeByAccountRequest controlFinancialAccountNatureTypeByAccountRequest) {
        List<Object[]> controlFinancialAccountNatureTypeObject = financialDocumentItemRepository.findByMoneyTypeAndFinancialAccountId(SecurityHelper.getCurrentUser().getOrganizationId(), controlFinancialAccountNatureTypeByAccountRequest.getFinancialLedgerTypeId(),
                controlFinancialAccountNatureTypeByAccountRequest.getFinancialDepartmentId(), DateUtil.convertStringToDate(controlFinancialAccountNatureTypeByAccountRequest.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))), controlFinancialAccountNatureTypeByAccountRequest.getFinancialAccountId()
        );
        return controlFinancialAccountNatureTypeObject.stream().map(objects -> ControlFinancialAccountNatureTypeByAccountResponse.builder().sumDebit(objects[0] == null ? null : ((BigDecimal) objects[0]).doubleValue())
                .sumCredit(objects[1] == null ? null : ((BigDecimal) objects[1]).doubleValue())
                .accountNatureTypeId(objects[2] == null ? 0 : Long.parseLong(objects[2].toString()))
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<ControlFinancialAccountNatureTypeOutputResponse> getControlFinancialAccountNatureType(ControlFinancialAccountNatureTypeInputRequest controlFinancialAccountNatureTypeInputRequest) {
        checkFinancialNatureControl(controlFinancialAccountNatureTypeInputRequest);
        Object financialDocumentItem = null;
        if (controlFinancialAccountNatureTypeInputRequest.getFinancialDocumentItemId() != null) {
            financialDocumentItem = "financialDocumentItem";
        } else {
            controlFinancialAccountNatureTypeInputRequest.setFinancialDocumentItemId(0L);
        }

        Object financialDocument = null;
        if (controlFinancialAccountNatureTypeInputRequest.getFinancialDocumentId() != null) {
            financialDocument = "financialDocument";
        } else {
            controlFinancialAccountNatureTypeInputRequest.setFinancialDocumentId(0L);
        }
        List<Object[]> controlFinancialAccountObject = financialDocumentItemRepository.findByFinancialDocumentItemByIdAndFinancialDocumentId(controlFinancialAccountNatureTypeInputRequest.getFinancialDocumentId(), controlFinancialAccountNatureTypeInputRequest.getFinancialDocumentItemId(), financialDocumentItem, financialDocument);
        return controlFinancialAccountObject.stream().map(objects -> ControlFinancialAccountNatureTypeOutputResponse.builder().sumDebit(objects[0] == null ? null : ((BigDecimal) objects[0]).doubleValue())
                .sumCredit(objects[1] == null ? null : ((BigDecimal) objects[1]).doubleValue())
                .accountNatureTypeId(objects[2] == null ? 0 : Long.parseLong(objects[2].toString()))
                .financialAccountDescription(objects[3] == null ? null : objects[3].toString())
                .resultMessage(objects[4] == null ? null : objects[4].toString())
                .natureTypeDescription(objects[5] == null ? null : objects[5].toString())
                .build()).collect(Collectors.toList());
    }

    private void checkFinancialNatureControl(ControlFinancialAccountNatureTypeInputRequest controlFinancialAccountNatureTypeInputRequest) {
        if (controlFinancialAccountNatureTypeInputRequest.getFinancialDocumentId() == null && controlFinancialAccountNatureTypeInputRequest.getFinancialDocumentItemId() == null) {
            throw new RuleException("fin.financialNatureControl.getControl");
        }
    }
}


