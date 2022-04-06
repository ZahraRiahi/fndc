package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberSaveDto;
import ir.demisco.cfs.model.entity.FinancialDocumentNumber;
import ir.demisco.cfs.service.api.FinancialDocumentNumberService;
import ir.demisco.cfs.service.repository.FinancialDocumentNumberRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialNumberingTypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultFinancialDocumentNumber implements FinancialDocumentNumberService {

    private final FinancialDocumentNumberRepository financialDocumentNumberRepository;
    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialNumberingTypeRepository financialNumberingTypeRepository;

    public DefaultFinancialDocumentNumber(FinancialDocumentNumberRepository financialDocumentNumberRepository, FinancialDocumentRepository financialDocumentRepository, FinancialNumberingTypeRepository financialNumberingTypeRepository) {
        this.financialDocumentNumberRepository = financialDocumentNumberRepository;
        this.financialDocumentRepository = financialDocumentRepository;
        this.financialNumberingTypeRepository = financialNumberingTypeRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentNumberSaveDto documentNumberSave(FinancialDocumentNumberSaveDto financialDocumentNumberSaveDto) {


        FinancialDocumentNumber financialDocumentNumber=financialDocumentNumberRepository.
                findById(financialDocumentNumberSaveDto.getId()==null ? 0L:financialDocumentNumberSaveDto.getId()).orElse(new FinancialDocumentNumber());

        financialDocumentNumber.setFinancialDocument(financialDocumentRepository.getOne(financialDocumentNumberSaveDto.getFinancialDocumentId()));
        financialDocumentNumber.setFinancialNumberingType(financialNumberingTypeRepository.getOne(financialDocumentNumberSaveDto.getFinancialNumberingTypeId()));
        financialDocumentNumber.setDocumentNumber(financialDocumentNumberSaveDto.getDocumentNumber());
        financialDocumentNumberRepository.save(financialDocumentNumber);
        return convertDocumentNumberToDto(financialDocumentNumber);
    }

    private FinancialDocumentNumberSaveDto convertDocumentNumberToDto(FinancialDocumentNumber financialDocumentNumber) {
        return FinancialDocumentNumberSaveDto.builder()
                .id(financialDocumentNumber.getId())
                .financialNumberingTypeId(financialDocumentNumber.getFinancialNumberingType().getId())
                .financialNumberingTypeDescription(financialDocumentNumber.getFinancialNumberingType().getDescription())
                .financialDocumentId(financialDocumentNumber.getFinancialDocument().getId())
                .documentNumber(financialDocumentNumber.getDocumentNumber())
                .build();
    }
}
