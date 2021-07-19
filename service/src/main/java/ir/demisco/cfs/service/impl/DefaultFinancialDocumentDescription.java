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
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


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
    public Long save(FinancialDocumentDescriptionOrganizationDto documentDescriptionDto) {
        //        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Long organizationId = 100L;
        FinancialDocumentDescription financialDocumentDescription =
                financialDocumentDescriptionRepository.getByParam(organizationId, documentDescriptionDto.getDescription());
        if (financialDocumentDescription != null) {
            throw new RuleException("سند با این شرح درج شده.");
        } else {
            FinancialDocumentDescription documentDescription =
                    financialDocumentDescriptionRepository.findById(documentDescriptionDto.getId() == null ? 0L : documentDescriptionDto.getId()).orElse(new FinancialDocumentDescription());
            documentDescription.setDescription(documentDescriptionDto.getDescription());
            documentDescription.setOrganization(organizationRepository.getOne(organizationId));
            return financialDocumentDescriptionRepository.save(documentDescription).getId();
        }
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentDescriptionOrganizationDto update(FinancialDocumentDescriptionOrganizationDto documentDescriptionDto) {
        FinancialDocumentDescription documentDescription =
                financialDocumentDescriptionRepository.findById(documentDescriptionDto.getId()).orElseThrow(() -> new RuleException("سند یافت نشد"));
        //        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Long organizationId = 100L;
        documentDescription.setOrganization(organizationRepository.getOne(organizationId));
        documentDescription.setDescription(documentDescriptionDto.getDescription());
        return convertDocumentDescription(documentDescription);
    }

    private FinancialDocumentDescriptionOrganizationDto convertDocumentDescription(FinancialDocumentDescription documentDescription) {

            return FinancialDocumentDescriptionOrganizationDto.builder()
                    .id(documentDescription.getId())
                    .organizationId(documentDescription.getOrganization().getId())
                    .description(documentDescription.getDescription())
                    .build();
    }


}
