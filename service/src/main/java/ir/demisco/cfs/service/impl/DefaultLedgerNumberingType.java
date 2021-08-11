package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.LedgerNumberingTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeResponse;
import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeResponse;
import ir.demisco.cfs.model.dto.response.LedgerNumberingTypeDto;
import ir.demisco.cfs.model.entity.FinancialNumberingType;
import ir.demisco.cfs.model.entity.LedgerNumberingType;
import ir.demisco.cfs.service.api.LedgerNumberingTypeService;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cfs.service.repository.FinancialNumberingTypeRepository;
import ir.demisco.cfs.service.repository.LedgerNumberingTypeRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultLedgerNumberingType implements LedgerNumberingTypeService {

    LedgerNumberingTypeRepository ledgerNumberingTypeRepository;
    FinancialLedgerTypeRepository financialLedgerTypeRepository;
    FinancialNumberingTypeRepository financialNumberingTypeRepository;


    public DefaultLedgerNumberingType(LedgerNumberingTypeRepository ledgerNumberingTypeRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository, FinancialNumberingTypeRepository financialNumberingTypeRepository) {
        this.ledgerNumberingTypeRepository = ledgerNumberingTypeRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialNumberingTypeRepository = financialNumberingTypeRepository;
    }

    @Override
    @Transactional
    public List<FinancialNumberingTypeResponse> getLedgerNumberingType(LedgerNumberingTypeRequest ledgerNumberingTypeRequest) {
        FinancialLedgerTypeResponse parameter = new FinancialLedgerTypeResponse();
        Long financialLedgerTypeId = ledgerNumberingTypeRequest.getFinancialLedgerTypeId();
        if (financialLedgerTypeId != null) {
            parameter.setFinancialLedgerTypeId(financialLedgerTypeId);
        } else {
            parameter.setFinancialLedgerType(null);
            parameter.setFinancialLedgerTypeId(0L);
        }
        List<Object[]> ledgerNumberingTypeList = ledgerNumberingTypeRepository.getLedgerNumberingType(parameter.getFinancialLedgerTypeId(), parameter.getFinancialLedgerType());
        return ledgerNumberingTypeList.stream().map(objects -> FinancialNumberingTypeResponse.builder().id(Long.parseLong(objects[0].toString()))
                .description(objects[1].toString())
                .flgExists(Long.valueOf((objects[2].toString())))
                .build()).collect(Collectors.toList());

    }
}
