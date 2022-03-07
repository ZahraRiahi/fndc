package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.entity.DocumentTypeOrgRel;
import ir.demisco.cfs.service.api.DocumentTypeOrgRelService;
import ir.demisco.cfs.service.repository.DocumentTypeOrgRelRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentTypeRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultDocumentTypeOrgRel implements DocumentTypeOrgRelService {
    private final DocumentTypeOrgRelRepository documentTypeOrgRelRepository;
    private final OrganizationRepository organizationRepository;
    private final FinancialDocumentTypeRepository financialDocumentTypeRepository;

    public DefaultDocumentTypeOrgRel(DocumentTypeOrgRelRepository documentTypeOrgRelRepository, OrganizationRepository organizationRepository, FinancialDocumentTypeRepository financialDocumentTypeRepository) {
        this.documentTypeOrgRelRepository = documentTypeOrgRelRepository;
        this.organizationRepository = organizationRepository;
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
    }

    @Override
    @Transactional
    public void save(Long along, Long organizationId) {
        Long count = documentTypeOrgRelRepository.getDocumentTypeOrgRelByOrganization(SecurityHelper.getCurrentUser().getOrganizationId(), along);
        if (count == null) {
            DocumentTypeOrgRel documentTypeOrgRel = new DocumentTypeOrgRel();
            documentTypeOrgRel.setOrganization(organizationRepository.getOne(SecurityHelper.getCurrentUser().getOrganizationId()));
            documentTypeOrgRel.setFinancialDocumentType(financialDocumentTypeRepository.getById(along));
            documentTypeOrgRel.setActiveFlag(1L);
        }
    }
}
