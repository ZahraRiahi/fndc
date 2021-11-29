package ir.demisco.cfs.service.impl;


import ir.demisco.cfs.model.dto.response.FinancialDocumentDescriptionOrganizationDto;
import ir.demisco.cfs.model.entity.FinancialDocumentDescription;
import ir.demisco.cfs.service.api.FinancialDocumentDescriptionService;
import ir.demisco.cfs.service.repository.FinancialDocumentDescriptionRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Service
public class DefaultFinancialDocumentDescription implements FinancialDocumentDescriptionService {

    private final GridFilterService gridFilterService;
    private final FinancialDocumentDescriptionListGridProvider documentDescriptionListGridProvider;
    private final OrganizationRepository organizationRepository;
    private final FinancialDocumentDescriptionRepository financialDocumentDescriptionRepository;

    public DefaultFinancialDocumentDescription(GridFilterService gridFilterService, FinancialDocumentDescriptionListGridProvider documentDescriptionListGridProvider, OrganizationRepository organizationRepository, FinancialDocumentDescriptionRepository financialDocumentDescriptionRepository) {
        this.gridFilterService = gridFilterService;
        this.documentDescriptionListGridProvider = documentDescriptionListGridProvider;
        this.organizationRepository = organizationRepository;
        this.financialDocumentDescriptionRepository = financialDocumentDescriptionRepository;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialDocumentByOrganizationId(Long organizationId, DataSourceRequest dataSourceRequest) {
        Asserts.notNull(organizationId, "organizationId is null");
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters()
                .add(DataSourceRequest.FilterDescriptor.create("organization.id", organizationId, DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest, documentDescriptionListGridProvider);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentDescriptionOrganizationDto save(FinancialDocumentDescriptionOrganizationDto documentDescriptionDto) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        FinancialDocumentDescription documentDescription =
                financialDocumentDescriptionRepository.findById(documentDescriptionDto.getId() == null ? 0L : documentDescriptionDto.getId()).orElse(new FinancialDocumentDescription());
        documentDescription.setDescription(documentDescriptionDto.getDescription());
        documentDescription.setOrganization(organizationRepository.getOne(organizationId));
        financialDocumentDescriptionRepository.save(documentDescription);
        return convertDocumentDescription(documentDescription);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentDescriptionOrganizationDto update(FinancialDocumentDescriptionOrganizationDto documentDescriptionDto) {
        FinancialDocumentDescription documentDescription =
                financialDocumentDescriptionRepository.findById(documentDescriptionDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        documentDescription.setOrganization(organizationRepository.getOne(organizationId));
        documentDescription.setDescription(documentDescriptionDto.getDescription());
        return convertDocumentDescription(documentDescription);
    }

    private FinancialDocumentDescriptionOrganizationDto convertDocumentDescription(FinancialDocumentDescription documentDescription) {

        return FinancialDocumentDescriptionOrganizationDto.builder()
                .id(documentDescription.getId())
                .organizationId(documentDescription.getOrganization().getId())
                .description(documentDescription.getDescription())
                .message("عملیات موفقیت آمیز بود")
                .build();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteDocumentDescriptionById(Long documentDescriptionId) {
        FinancialDocumentDescription financialDocumentDescription =
                financialDocumentDescriptionRepository.findById(documentDescriptionId).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        financialDocumentDescription.setDeletedDate(LocalDateTime.now());
        financialDocumentDescriptionRepository.save(financialDocumentDescription);
        return true;
    }
}
