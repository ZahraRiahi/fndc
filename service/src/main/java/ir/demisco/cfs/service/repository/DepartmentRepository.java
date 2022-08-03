package ir.demisco.cfs.service.repository;

import ir.demisco.cloud.basic.model.entity.org.Department;
import org.hibernate.jpa.TypedParameterValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT FNDP.ID AS DEPARTMENTID," +
            "       FNDP.CODE," +
            "       FNDP.NAME," +
            "       FNLG.FINANCIAL_LEDGER_TYPE_ID," +
            "       FNLT.DESCRIPTION AS LEDGER_TYPE_DESCRIPTION," +
            "       FNLG.ID AS FINANCIAL_DEPARTMENT_LEDGER_ID," +
            "       CASE" +
            "         WHEN FNSC.SEC_RESULT = 1 THEN" +
            "          0" +
            "         ELSE" +
            "          1" +
            "       END DISABLED" +
            "  FROM FNDC.FINANCIAL_DEPARTMENT FNDP" +
            "  LEFT OUTER JOIN fndc.FINANCIAL_DEPARTMENT_LEDGER FNLG" +
            "    ON FNDP.DEPARTMENT_ID = FNLG.DEPARTMENT_ID" +
            "  LEFT OUTER JOIN FNDC.FINANCIAL_LEDGER_TYPE FNLT" +
            "    ON FNLT.ID = FNLG.FINANCIAL_LEDGER_TYPE_ID," +
            " TABLE(FNSC.PKG_FINANCIAL_SECURITY.GET_PERMISSION(:organizationIdPKG," +
            "                                                   :activityCode," +
            "                                                   :financialPeriodId," +
            "                                                   :financialDocumentTypeId," +
            "                                                   :creatorUserId," +
            "                                                   FNDP.ID," +
            "                                                   FNLG.FINANCIAL_LEDGER_TYPE_ID," +
            "                                                   :departmentId," +
            "                                                   :userId)) FNSC" +
            " where FNDP.DEPARTMENT_ID =:departmentId " +
            "   OR EXISTS (SELECT 1" +
            "           FROM fndc.FINANCIAL_DEP_ORG_REL INER_ORG_REL" +
            "          WHERE INER_ORG_REL.ORGANIZATION_ID = :organizationId " +
            "           AND (INER_ORG_REL.FINANCIAL_DEPARTMENT_ID = FNDP.ID OR  :departmentId is null) " +
            "            AND INER_ORG_REL.ACTIVE_FLAG = 1) "
            , nativeQuery = true)
    List<Object[]> getFinancialDocumentItemList(Long organizationId, Long organizationIdPKG,
                                                String activityCode,
                                                TypedParameterValue financialPeriodId,
                                                TypedParameterValue financialDocumentTypeId,
                                                TypedParameterValue creatorUserId,
                                                Long departmentId,
                                                Long userId);
}
