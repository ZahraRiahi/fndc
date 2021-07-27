package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeGetDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentTypeDto;
import ir.demisco.cfs.service.api.FinancialDocumentTypeService;
import ir.demisco.cfs.service.repository.FinancialDocumentTypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialDocumentType implements FinancialDocumentTypeService {

    private final FinancialDocumentTypeRepository financialDocumentTypeRepository;

    public DefaultFinancialDocumentType(FinancialDocumentTypeRepository financialDocumentTypeRepository) {
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
    }

    @Override
    @Transactional
    public List<FinancialDocumentTypeGetDto> getNumberingFormatByOrganizationId(Long organizationId, ResponseFinancialDocumentTypeDto responseFinancialDocumentTypeDto) {
        return financialDocumentTypeRepository.findByOrganizationId(organizationId,responseFinancialDocumentTypeDto.getSearchStatusFlag(),
                responseFinancialDocumentTypeDto.getSearchStatusFlag()==null ? null:"true")
                .stream().map(financialDocumentType->FinancialDocumentTypeGetDto.builder()
                        .id(financialDocumentType.getId())
                        .description(financialDocumentType.getDescription())
                        .build()).collect(Collectors.toList());
    }
}
