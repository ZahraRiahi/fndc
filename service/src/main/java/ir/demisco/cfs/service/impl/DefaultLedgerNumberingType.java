package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.LedgerNumberingTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeResponse;
import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeResponse;
import ir.demisco.cfs.model.dto.response.LedgerNumberingTypeDto;
import ir.demisco.cfs.model.entity.FinancialLedgerType;
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
import java.util.logging.Logger;
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

    @Override
    @Transactional
    public Boolean saveLedgerNumberingType(LedgerNumberingTypeDto ledgerNumberingTypeDto) {
        Long financialNumberingTypeRequest = ledgerNumberingTypeDto.getFinancialNumberingTypeId();
        Long financialLedgerTypeRequest = ledgerNumberingTypeDto.getFinancialLedgerTypeId();
        if (financialLedgerTypeRequest == null) {
            throw new RuleException("fin.ledgerNumberingType.insertLedgerType");
        }
        if (financialNumberingTypeRequest == null) {
            throw new RuleException("fin.ledgerNumberingType.insertNumberingType");
        }
        Long countByLedgerTypeIdAndNumberingTypeIdAndDeleteDate = ledgerNumberingTypeRepository.getCountByLedgerTypeIdAndNumberingTypeIdAndDeleteDate(ledgerNumberingTypeDto.getFinancialLedgerTypeId()
                , ledgerNumberingTypeDto.getFinancialNumberingTypeId());
        if (countByLedgerTypeIdAndNumberingTypeIdAndDeleteDate > 0) {
            throw new RuleException("fin.financialLedgerType.existNumberingTypeInDepartment");
        }
        if (ledgerNumberingTypeDto.getId() == null) {
            insertLedgerNumberingType(financialNumberingTypeRequest, financialLedgerTypeRequest);
            return true;
        } else {
            LedgerNumberingType ledgerNumberingType = ledgerNumberingTypeRepository.findById(ledgerNumberingTypeDto.getId()).orElseThrow(() -> new RuleException("fin.financialLedgerType.notValidNumberingType"));
            if (ledgerNumberingType.getDeletedDate() == null) {
                updateLedgerNumberingType(ledgerNumberingType, financialNumberingTypeRequest, financialLedgerTypeRequest);
            } else {
                insertLedgerNumberingType(financialNumberingTypeRequest, financialLedgerTypeRequest);
            }
            return false;
        }
    }

    private Boolean updateLedgerNumberingType(LedgerNumberingType ledgerNumberingType, Long financialNumberingTypeRequest, long financialLedgerTypeRequest) {
        Long financialNumberingTypeIdPresent = ledgerNumberingType.getFinancialNumberingType().getId();
        ledgerNumberingTypeRepository.deleteById(ledgerNumberingType.getId());
        if (financialNumberingTypeIdPresent.equals(financialNumberingTypeRequest)) {
            FinancialNumberingType financialNumberingType = financialNumberingTypeRepository.findById(financialNumberingTypeIdPresent).orElseThrow(() -> new RuleException("fin.ledgerNumberingType.notfoundNumberingType"));
            ledgerNumberingType.setFinancialNumberingType(financialNumberingType);
        } else {
            FinancialNumberingType financialNumberingType = financialNumberingTypeRepository.findById(financialNumberingTypeRequest).orElseThrow(() -> new RuleException("fin.ledgerNumberingType.notExistFinancialDepartmentLedgerType"));
            ledgerNumberingType.setFinancialNumberingType(financialNumberingType);
            Long financialLedgerTypeIdPresent = ledgerNumberingType.getFinancialLedgerType().getId();
            if (financialLedgerTypeIdPresent.equals(financialLedgerTypeRequest)) {
                Optional<FinancialLedgerType> financialLedgerTypeSame = financialLedgerTypeRepository.findById(financialLedgerTypeIdPresent);
                if(financialLedgerTypeSame.isPresent()){
                ledgerNumberingType.setFinancialLedgerType(financialLedgerTypeSame.get());}
            } else {
                FinancialLedgerType financialLedgerTypeDifferent = financialLedgerTypeRepository.findById(financialLedgerTypeRequest).orElseThrow(() -> new RuleException("fin.ledgerNumberingType.notExistfinancialDepartmentLedgerType"));
                ledgerNumberingType.setFinancialLedgerType(financialLedgerTypeDifferent);
            }
        }
        ledgerNumberingTypeRepository.save(ledgerNumberingType);
        return true;
    }

    private Boolean insertLedgerNumberingType(Long financialNumberingTypeRequest, Long financialLedgerTypeRequest) {
        LedgerNumberingType ledgerNumberingTypeNew = new LedgerNumberingType();
        FinancialLedgerType financialLedgerType = financialLedgerTypeRepository.findById(financialLedgerTypeRequest).orElseThrow(() -> new RuleException("fin.ledgerNumberingType.notfoundLedgerType"));
        ledgerNumberingTypeNew.setFinancialLedgerType(financialLedgerType);
        FinancialNumberingType financialNumberingType = financialNumberingTypeRepository.findById(financialNumberingTypeRequest).orElseThrow(() -> new RuleException("fin.ledgerNumberingType.notfoundNumberingType"));
        ledgerNumberingTypeNew.setFinancialNumberingType(financialNumberingType);
        try {
            ledgerNumberingTypeRepository.save(ledgerNumberingTypeNew);
        } catch (RuleException e) {
            Logger.getLogger(String.valueOf(e));
            throw new RuleException("fin.ledgerNumberingType.operationFailed");
        }
        return true;
    }

}
