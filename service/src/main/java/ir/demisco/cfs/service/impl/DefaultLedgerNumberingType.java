package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.LedgerNumberingTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeResponse;
import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeResponse;
import ir.demisco.cfs.service.api.LedgerNumberingTypeService;
import ir.demisco.cfs.service.repository.LedgerNumberingTypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultLedgerNumberingType implements LedgerNumberingTypeService {

    LedgerNumberingTypeRepository financialNumberingTypeRepository;

    public DefaultLedgerNumberingType(LedgerNumberingTypeRepository financialNumberingTypeRepository) {
        this.financialNumberingTypeRepository = financialNumberingTypeRepository;
    }

    @Override
    @Transactional
    public List<FinancialNumberingTypeResponse> getLedgerNumberingType(LedgerNumberingTypeRequest ledgerNumberingTypeRequest) {
        FinancialLedgerTypeResponse parameter = new FinancialLedgerTypeResponse();
        Long financialLedgerTypeId = ledgerNumberingTypeRequest.getFinancialLedgerTypeId();
        if ( financialLedgerTypeId!= null) {
            parameter.setFinancialLedgerType("financialLedgerType");
            parameter.setFinancialLedgerTypeId(financialLedgerTypeId);
        } else {
            parameter.setFinancialLedgerTypeId(0L);
            parameter.setFinancialLedgerType(null);
        }
        List<Object[]> ledgerNumberingTypeList = financialNumberingTypeRepository.getLedgerNumberingType(parameter.getFinancialLedgerTypeId(), parameter.getFinancialLedgerType());
        return ledgerNumberingTypeList.stream().map(objects -> FinancialNumberingTypeResponse.builder().id(Long.parseLong(objects[0].toString()))
                .description(objects[1].toString())
                .flgExists(Boolean.valueOf((objects[2].toString())))
                .build()).collect(Collectors.toList());

    }
}
