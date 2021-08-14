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
import java.time.LocalDateTime;
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
        Long financialNumberingTypeRequest = ledgerNumberingTypeDto.getFinancialNumberingTypeId();
        Long financialLedgerTypeRequest = ledgerNumberingTypeDto.getFinancialLedgerTypeId();
        if (financialLedgerTypeRequest == null) {
            throw new RuleException("لطفا  شناسه نوع دفتر مالی خود را وارد نمایید.");
        }
        if (financialNumberingTypeRequest == null) {
            throw new RuleException("لطفا شناسه نوع شماره گذاری خود را وارد نمایید.");
        }
        Long countByLedgerTypeIdAndNumberingTypeIdAndDeleteDate = ledgerNumberingTypeRepository.getCountByLedgerTypeIdAndNumberingTypeIdAndDeleteDate(ledgerNumberingTypeDto.getFinancialLedgerTypeId()
                , ledgerNumberingTypeDto.getFinancialNumberingTypeId());
        if (countByLedgerTypeIdAndNumberingTypeIdAndDeleteDate > 0) {
            throw new RuleException("نوع شماره گذاری، برای این نوع دفتر مالی، قبلا ثبت شده است.");
        }
        if (ledgerNumberingTypeDto.getId() == null) {
            insertLedgerNumberingType(financialNumberingTypeRequest, financialLedgerTypeRequest);
            return true;
        } else {
            Optional<LedgerNumberingType> ledgerNumberingType = ledgerNumberingTypeRepository.findById(ledgerNumberingTypeDto.getId());
            LedgerNumberingType ledgerNumberingTypeTBL = ledgerNumberingType.get();
            if (ledgerNumberingType.isPresent() && ledgerNumberingTypeTBL.getDeletedDate() == null) {
                try {
                    updateLedgerNumberingType(ledgerNumberingTypeTBL, financialNumberingTypeRequest, financialLedgerTypeRequest);
                } catch (Exception e) {
                    throw new RuleException(" بروزرسانی انجام نشد");
                }
            } else {
                insertLedgerNumberingType(financialNumberingTypeRequest, financialLedgerTypeRequest);
            }
            return null;
        }
    }

    private Boolean updateLedgerNumberingType(LedgerNumberingType ledgerNumberingType, Long financialNumberingTypeRequest, long financialLedgerTypeRequest) {
        Long financialNumberingTypeIdPresent = ledgerNumberingType.getFinancialNumberingType().getId();
        ledgerNumberingType.setDeletedDate(LocalDateTime.now());
        ledgerNumberingTypeRepository.save(ledgerNumberingType);
        if (financialNumberingTypeIdPresent.equals(financialNumberingTypeRequest)) {
            Optional<FinancialNumberingType> financialNumberingTypeSame = financialNumberingTypeRepository.findById(financialNumberingTypeIdPresent);
            ledgerNumberingType.setFinancialNumberingType(financialNumberingTypeSame.get());
        } else {
            Optional<FinancialNumberingType> financialNumberingTypeDifferent = financialNumberingTypeRepository.findById(financialNumberingTypeRequest);
            if (financialNumberingTypeDifferent.isPresent()) {
                ledgerNumberingType.setFinancialNumberingType(financialNumberingTypeDifferent.get());
            } else {
                throw new RuleException("شناسه انواع شماره گذاری  وجود ندارد");
            }
        }
        Long financialLedgerTypeIdPresent = ledgerNumberingType.getFinancialLedgerType().getId();
        if (financialLedgerTypeIdPresent.equals(financialLedgerTypeRequest)) {
            Optional<FinancialLedgerType> financialLedgerTypeSame = financialLedgerTypeRepository.findById(financialLedgerTypeIdPresent);
            ledgerNumberingType.setFinancialLedgerType(financialLedgerTypeSame.get());
        } else {
            Optional<FinancialLedgerType> financialLedgerTypeDifferent = financialLedgerTypeRepository.findById(financialLedgerTypeRequest);
            if (financialLedgerTypeDifferent.isPresent()) {
                ledgerNumberingType.setFinancialLedgerType(financialLedgerTypeDifferent.get());
            } else {
                throw new RuleException("شناسه انواع دفتر مالی سازمان وجود ندارد");
            }
        }
        ledgerNumberingTypeRepository.save(ledgerNumberingType);
        return true;
    }

    private Boolean insertLedgerNumberingType(Long financialNumberingTypeRequest, Long financialLedgerTypeRequest) {
        LedgerNumberingType ledgerNumberingTypeNew = new LedgerNumberingType();
        Optional<FinancialLedgerType> financialLedgerTypeTbl = financialLedgerTypeRepository.findById(financialLedgerTypeRequest);
        if (!financialLedgerTypeTbl.isPresent()) {
            throw new RuleException(" نوع دفتر مالی  یافت نشد.");
        } else {
            ledgerNumberingTypeNew.setFinancialLedgerType(financialLedgerTypeTbl.get());
        }
        Optional<FinancialNumberingType> financialNumberingTypeTbl = financialNumberingTypeRepository.findById(financialNumberingTypeRequest);
        if (!financialNumberingTypeTbl.isPresent()) {
            throw new RuleException(" نوع  شماره گذاری یافت نشد.");
        } else {
            ledgerNumberingTypeNew.setFinancialNumberingType(financialNumberingTypeTbl.get());
        }
        try {
            ledgerNumberingTypeRepository.save(ledgerNumberingTypeNew);
        } catch (Exception e) {
            throw new RuleException("عملیات با موفقیت ثبت نشد.");
        }
        return true;
    }

}
