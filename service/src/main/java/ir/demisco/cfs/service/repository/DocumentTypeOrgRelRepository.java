package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.DocumentTypeOrgRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentTypeOrgRelRepository extends JpaRepository<DocumentTypeOrgRel, Long> {
    @Query(value = " SELECT 1" +
            "            FROM fndc.DOCUMENT_TYPE_ORG_REL INER_ORG_REL" +
            "           WHERE INER_ORG_REL.ORGANIZATION_ID = :organizationId" +
            "             AND INER_ORG_REL.FINANCIAL_DOCUMENT_TYPE_ID = :financialDocumentTypeId" +
            "             AND INER_ORG_REL.ACTIVE_FLAG = 1 "
            , nativeQuery = true)
    Long getDocumentTypeOrgRelByOrganization(Long organizationId, Long financialDocumentTypeId);

    @Query(value = "select t.id from fndc.document_type_org_rel t WHERE t.financial_document_type_id = :financialDocumentTypeId "
            , nativeQuery = true)
    Long findByFinancialDocumentTypeIdForDelete(Long financialDocumentTypeId);
}
