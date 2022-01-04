package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.ControlFinancialAccountNatureTypeByAccountRequest;
import ir.demisco.cfs.model.dto.request.ControlFinancialAccountNatureTypeInputRequest;
import ir.demisco.cfs.model.dto.response.ControlFinancialAccountNatureTypeByAccountResponse;
import ir.demisco.cfs.service.api.ControlFinancialAccountNatureTypeService;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
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
        List<Object[]> controlFinancialAccountNatureTypeObject = financialDocumentItemRepository.findByMoneyTypeAndFinancialAccountId(controlFinancialAccountNatureTypeByAccountRequest.getFinancialAccountId(),
                controlFinancialAccountNatureTypeByAccountRequest.getFinancialLedgerTypeId(), controlFinancialAccountNatureTypeByAccountRequest.getFinancialDepartmentId(), controlFinancialAccountNatureTypeByAccountRequest.getOrganizationId()
                , DateUtil.convertStringToDate(controlFinancialAccountNatureTypeByAccountRequest.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
        return controlFinancialAccountNatureTypeObject.stream().map(objects -> ControlFinancialAccountNatureTypeByAccountResponse.builder().sumDebit(objects[0] == null ? null : ((BigDecimal) objects[0]).doubleValue())
                .sumCredit(objects[1] == null ? null : ((BigDecimal) objects[1]).doubleValue())
                .accountNatureTypeId(objects[2] == null ? 0 : Long.parseLong(objects[2].toString()))
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<ControlFinancialAccountNatureTypeByAccountResponse> getControlFinancialAccountNatureType(ControlFinancialAccountNatureTypeInputRequest controlFinancialAccountNatureTypeInputRequest) {
        if(controlFinancialAccountNatureTypeInputRequest.getFinancialDocumentId()==null && controlFinancialAccountNatureTypeInputRequest.getFinancialDocumentItemId()==null){
            throw new RuleException("برای کنترل ماهیت میبایست شناسه سند یا ردیف پر شده باشد");
        }

        return null;
    }

}

