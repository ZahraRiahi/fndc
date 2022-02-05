package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDocumentHeaderOutputResponse;
import ir.demisco.cfs.model.dto.response.FinancialDocumentHeaderResponse;
import ir.demisco.cfs.model.entity.FinancialDocument;
import ir.demisco.cfs.service.api.FinancialDocumentHeaderService;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultFinancialDocumentHeader implements FinancialDocumentHeaderService {
    private final FinancialDocumentRepository financialDocumentRepository;

    public DefaultFinancialDocumentHeader(FinancialDocumentRepository financialDocumentRepository) {
        this.financialDocumentRepository = financialDocumentRepository;
    }

    @Override
    @Transactional
    public FinancialDocumentHeaderResponse getFinancialDocumentHeaderByDocumentId(Long financialDocumentId) {
        FinancialDocument financialDocument = financialDocumentRepository.findById(financialDocumentId).orElseThrow(() -> new RuleException("fin.ruleException.notFoundId"));
        FinancialDocumentHeaderResponse financialDocumentHeaderResponse = FinancialDocumentHeaderResponse.builder().id(financialDocumentId)
                .documentDate(financialDocument.getDocumentDate())
                .documentNumber(financialDocument.getDocumentNumber())
                .financialDocumentTypeId(financialDocument.getFinancialDocumentType().getId())
                .financialDocumentTypeDescription(financialDocument.getFinancialDocumentType() == null ? "" : financialDocument.getFinancialDocumentType().getDescription())
                .financialDocumentStatusId(financialDocument.getFinancialDocumentStatus().getId())
                .automaticFlag(financialDocument.getAutomaticFlag())
                .description(financialDocument.getDescription())
                .organizationId(financialDocument.getOrganization().getId())
                .financialLedgerTypeId(financialDocument.getFinancialLedgerType() == null ? 0 : financialDocument.getFinancialLedgerType().getId())
                .financialLedgerTypeDescription(financialDocument.getFinancialLedgerType() == null ? "" : financialDocument.getFinancialLedgerType().getDescription())
                .financialDepartmentId(financialDocument.getFinancialDepartment() == null ? 0 : financialDocument.getFinancialDepartment().getId())
                .financialDepartmentName(financialDocument.getFinancialDepartment() == null ? "" : financialDocument.getFinancialDepartment().getName())
                .financialPeriodId(financialDocument.getFinancialPeriod().getId())
                .financialPeriodDescription(financialDocument.getFinancialPeriod() == null ? "" : financialDocument.getFinancialPeriod().getDescription())
                .financialDocumentStatusCode(financialDocument.getFinancialDocumentStatus() == null ? "" : financialDocument.getFinancialDocumentStatus().getCode())
                .financialDocumentStatusDescription(financialDocument.getFinancialDocumentStatus() == null ? "" : financialDocument.getFinancialDocumentStatus().getName())
                .build();
        return financialDocumentHeaderResponse;
    }

    @Override
    @Transactional
    public FinancialDocumentHeaderOutputResponse getFinancialDocumentHeaderBytId(Long financialDocumentId) {
        FinancialDocument financialDocument = financialDocumentRepository.findById(financialDocumentId).orElseThrow(() -> new RuleException("fin.ruleException.notFoundId"));
        FinancialDocumentHeaderOutputResponse financialDocumentHeaderOutputResponse = FinancialDocumentHeaderOutputResponse.builder().id(financialDocumentId)
                .documentDate(financialDocument.getDocumentDate())
                .description(financialDocument.getDescription())
                .financialDocumentStatusId(financialDocument.getFinancialDocumentStatus().getId())
                .automaticFlag(financialDocument.getAutomaticFlag())
                .organizationId(financialDocument.getOrganization().getId())
                .financialDocumentTypeId(financialDocument.getFinancialDocumentType().getId())
                .financialPeriodId(financialDocument.getFinancialPeriod().getId())
                .financialLedgerTypeId(financialDocument.getFinancialLedgerType() == null ? 0 : financialDocument.getFinancialLedgerType().getId())
                .financialDepartmentId(financialDocument.getFinancialDepartment() == null ? 0 : financialDocument.getFinancialDepartment().getId())
                .documentNumber(financialDocument.getDocumentNumber())
                .creatorId(financialDocument.getCreator().getId())
                .lastModifierId(financialDocument.getLastModifier().getId())
                .departmentId(financialDocument.getFinancialDepartment().getDepartment() == null ? 0 : financialDocument.getFinancialDepartment().getDepartment().getId())
                .financialSystemId(financialDocument.getFinancialDepartment().getFinancialSystem() == null ? 0 : financialDocument.getFinancialDepartment().getFinancialSystem().getId())
                .build();
        return financialDocumentHeaderOutputResponse;
    }
}

