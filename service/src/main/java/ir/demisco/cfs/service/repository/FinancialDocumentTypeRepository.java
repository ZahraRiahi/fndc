package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentType;
import org.hibernate.jpa.TypedParameterValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialDocumentTypeRepository extends JpaRepository<FinancialDocumentType, Long> {

//    @Query("select fd " +
//            "from FinancialDocumentType fd" +
//            " where fd.organization.id=:organizationId" +
//            " and :isFlag is null or ((:searchStatusFlag=false and fd.activeFlag=true) or (:searchStatusFlag=true)) " +
//            " and (:financialSystem is null or " +
//            " fd.financialSystem.id = :financialSystemId)" +
//            " and fd.deletedDate is null ")
//    List<FinancialDocumentType> findByOrganizationId(Long organizationId, Boolean searchStatusFlag, String isFlag, Object financialSystem, Long financialSystemId);


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
            "  WHERE FNDT.ORGANIZATION_ID = :organizationId " +
//            " and :isFlag is null or ((:searchStatusFlag=0 and FNDT.ACTIVE_FLAG =1) or (:searchStatusFlag=1)) " +
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
}
