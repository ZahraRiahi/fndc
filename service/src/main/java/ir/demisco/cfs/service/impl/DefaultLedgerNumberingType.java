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


    @Override
    @Transactional
    public Boolean saveLedgerNumberingType(LedgerNumberingTypeDto ledgerNumberingTypeDto) {
        Optional<FinancialNumberingType> ledgerNumberingType = ledgerNumberingTypeRepository.findById(ledgerNumberingTypeDto.getId());
        LedgerNumberingType ledgerNumberingTypeNew = new LedgerNumberingType();
        Long financialNumberingTypeRequest = ledgerNumberingTypeDto.getFinancialNumberingTypeId();
        Long financialLedgerTypeRequest = ledgerNumberingTypeDto.getFinancialLedgerTypeId();
        if (financialLedgerTypeRequest == null) {
            throw new RuleException("لطفا نوع دفتر مالی خود را وارد نمایید.");
        }
        if (financialNumberingTypeRequest == null) {
            throw new RuleException("لطفا نوع شماره گذاری خود را وارد نمایید.");
        }
        if (ledgerNumberingType.isPresent()) {
//            updateLedgerNumberingType(ledgerNumberingType, financialNumberingTypeRequest, ledgerNumberingTypeNew, financialLedgerTypeRequest);

        } else {
//            insertLedgerNumberingType(financialNumberingTypeRequest, ledgerNumberingTypeNew, financialLedgerTypeRequest);
        }
        return null;
    }

//    private Boolean insertLedgerNumberingType(Long financialNumberingTypeRequest, LedgerNumberingType ledgerNumberingTypeNew, Long financialLedgerTypeRequest) {
//        Optional<FinancialLedgerType> financialLedgerTypeTbl = financialLedgerTypeRepository.findById(financialLedgerTypeRequest);
//        if (financialLedgerTypeTbl.isPresent()) {
//            throw new RuleException(" نوع دفتر مالی  یافت نشد.");
//        } else {
//            ledgerNumberingTypeNew.setFinancialLedgerType(financialLedgerTypeTbl.get());
//        }
//        Optional<FinancialNumberingType> financialNumberingTypeTbl = financialNumberingTypeRepository.findById(financialNumberingTypeRequest);
//        if (financialNumberingTypeTbl.isPresent()) {
//            throw new RuleException(" نوع  شماره گذاری یافت نشد.");
//        } else {
//            ledgerNumberingTypeNew.setFinancialNumberingType(financialNumberingTypeTbl.get());
//        }
//        ledgerNumberingTypeRepository.save(ledgerNumberingTypeNew);
//        return true;
//    }

//    private Boolean updateLedgerNumberingType(Optional<FinancialNumberingType> ledgerNumberingType, Long financialNumberingTypeRequest, LedgerNumberingType ledgerNumberingTypeNew, long financialLedgerTypeRequest) {
//        FinancialNumberingType financialNumberingType = ledgerNumberingType.get();
//        Long financialNumberingTypeIdPresent = financialNumberingType.getFinancialNumberingType().getId();
//        if (financialNumberingTypeIdPresent.equals(financialNumberingTypeRequest)) {
//            Optional<FinancialNumberingType> financialNumberingTypeSame = financialNumberingTypeRepository.findById(financialNumberingTypeIdPresent);
//            ledgerNumberingTypeNew.setFinancialNumberingType(financialNumberingTypeSame.get());
//        } else {
//            Optional<FinancialNumberingType> financialNumberingTypeDifferent = financialNumberingTypeRepository.findById(financialNumberingTypeRequest);
//            ledgerNumberingTypeNew.setFinancialNumberingType(financialNumberingTypeDifferent.get());
//        }
//        Long financialLedgerTypeIdPresent = financialNumberingType.getFinancialLedgerType().getId();
//        if (financialLedgerTypeIdPresent.equals(financialLedgerTypeRequest)) {
//            Optional<FinancialLedgerType> financialLedgerTypeSame = financialLedgerTypeRepository.findById(financialLedgerTypeIdPresent);
//            ledgerNumberingTypeNew.setFinancialLedgerType(financialLedgerTypeSame.get());
//        } else {
//            Optional<FinancialLedgerType> financialLedgerTypeDifferent = financialLedgerTypeRepository.findById(financialLedgerTypeRequest);
//            ledgerNumberingTypeNew.setFinancialLedgerType(financialLedgerTypeDifferent.get());
//        }
//        return null;
//    }

}
