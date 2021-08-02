package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;
import ir.demisco.cfs.model.entity.FinancialLedgerType;
import ir.demisco.cfs.service.api.FinancialLedgerTypeService;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DefaultFinancialLedgerType implements FinancialLedgerTypeService {

    private final FinancialLedgerTypeRepository financialDocumentTypeRepository;

    public DefaultFinancialLedgerType(FinancialLedgerTypeRepository financialDocumentTypeRepository) {
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
    }

    @Override
    @Transactional()
    public List<FinancialLedgerTypeDto> getFinancialLedgerType(Long organizationId) {
        List<FinancialLedgerType> financialLedgerType = financialDocumentTypeRepository.findFinancialLedgerTypeByOrganizationId(organizationId);
        return financialLedgerType.stream().map(e -> FinancialLedgerTypeDto.builder().id(e.getId())
                .description(e.getDescription())
                .build()).collect(Collectors.toList());
    }
}
