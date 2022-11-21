package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentType;
import org.hibernate.jpa.TypedParameterValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialDocumentTypeRepository extends JpaRepository<FinancialDocumentType, Long> {
    @Query(value = " SELECT FNDT.ID, " +
            "       FNDT.DESCRIPTION, " +
            "       FNDT.ACTIVE_FLAG," +
            "       CASE" +
            "         WHEN FNSC.SEC_RESULT = 1 THEN" +
            "          0" +
            "         ELSE" +
            "          1" +
            "       END disabled" +
            "  FROM fndc.FINANCIAL_DOCUMENT_TYPE FNDT," +
            "       TABLE(fnsc.PKG_FINANCIAL_SECURITY.GET_PERMISSION(CASE" +
            "                                                     WHEN :searchStatusFlag = 1 THEN" +
            "                                                      -1" +
            "                                                     ELSE" +
            "                                                      :organizationId" +
            "                                                   END," +
            "                                                   :activityCode," +
            "                                                   :financialPeriodId," +
            "                                                   FNDT.ID," +
            "                                                   NULL," +
            "                                                   :financialDepartmentId," +
            "                                                   :financialLedgerTypeId," +
            "                                                   :departmentId," +
            "                                                   :userId)) FNSC" +
            "  WHERE   EXISTS (SELECT 1" +
            "          FROM fndc.DOCUMENT_TYPE_ORG_REL INER_ORG_REL" +
            "         WHERE INER_ORG_REL.ORGANIZATION_ID = :organizationId" +
            "           AND INER_ORG_REL.FINANCIAL_DOCUMENT_TYPE_ID =" +
            "               FNDT.ID" +
            "           AND INER_ORG_REL.ACTIVE_FLAG = 1) " +
            " and (:financialSystem is null or FNDT.FINANCIAL_SYSTEM_ID =:financialSystemId )", nativeQuery = true)
    List<Object[]> findByOrganizationId(Long searchStatusFlag, Long organizationId, String activityCode, TypedParameterValue financialPeriodId, TypedParameterValue financialDepartmentId, TypedParameterValue financialLedgerTypeId, TypedParameterValue departmentId
            , Long userId, Object financialSystem, Long financialSystemId);

    @Query(value = "select count(t.id)" +
            "  from fndc.financial_document_type t" +
            "  left join fndc.financial_config fc" +
            "    on fc.financial_document_type_id = t.id" +
            "  left join fndc.financial_document fd" +
            "    on t.id = fd.financial_document_type_id" +
            "  left join fndc.financial_document_pattern fdp" +
            "    on fdp.financial_document_type_id = t.id" +
            " where t.id = :financialDocumentTypeId" +
            "   and ( fd.financial_document_type_id = :financialDocumentTypeId or" +
            "        fdp.financial_document_type_id = :financialDocumentTypeId or" +
            "         fc.financial_document_type_id = :financialDocumentTypeId)", nativeQuery = true)
    Long getDocumentTypeIdForDelete(Long financialDocumentTypeId);

    @Query(value = " SELECT FNDT.id," +
            "       FNDT.Description," +
            "       ACTIVE_FLAG," +
            "       AUTOMATIC_FLAG," +
            "       ORGANIZATION_ID," +
            "       FINANCIAL_SYSTEM_ID," +
            "       FNS.DESCRIPTION FINANCIALSYSTEM_DESCRIPTION" +
            "  FROM fndc.FINANCIAL_DOCUMENT_TYPE FNDT" +
            " INNER JOIN FNSC.FINANCIAL_SYSTEM FNS" +
            "    ON FNDT.FINANCIAL_SYSTEM_ID = FNS.ID" +
            " WHERE (:financialSystem is null or FNDT.FINANCIAL_SYSTEM_ID =:financialSystemId )" +
            "  and EXISTS (SELECT 1" +
            "          FROM fndc.DOCUMENT_TYPE_ORG_REL INER_ORG_REL" +
            "         WHERE INER_ORG_REL.ORGANIZATION_ID = :organizationId" +
            "           AND INER_ORG_REL.FINANCIAL_DOCUMENT_TYPE_ID = FNDT.ID" +
            "           AND INER_ORG_REL.ACTIVE_FLAG = 1)" +
            " and (:idObject is null or FNDT.id =:id )"
            , nativeQuery = true)
    Page<Object[]> financialDocumentType(Object financialSystem, Long financialSystemId, Long organizationId, Object idObject, Long id, Pageable pageable);


    @Query(value = " SELECT T.FINANCIAL_DOCUMENT_TYPE_ID " +
            "  FROM FNDC.FINANCIAL_DOCUMENT T " +
            " WHERE T.ID = :financialDocumentId "
            , nativeQuery = true)
    Long findByFinancialDocumentTypeAndfinancialDocumentId(Long financialDocumentId);
}
