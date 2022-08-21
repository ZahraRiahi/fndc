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
            "       CASE" +
            "         WHEN FNSC.SEC_RESULT = 1 THEN" +
            "          0" +
            "         ELSE" +
            "          1" +
            "       END DISABLED" +
            "  FROM FNDC.FINANCIAL_DEPARTMENT FNDP" +
            " TABLE(FNSC.PKG_FINANCIAL_SECURITY.GET_PERMISSION(:organizationIdPKG," +
            "                                                   :activityCode," +
            "                                                   :financialPeriodId," +
            "                                                   :financialDocumentTypeId," +
            "                                                   :creatorUserId," +
            "                                                   FNDP.ID," +
            "                                                   FNLG.FINANCIAL_LEDGER_TYPE_ID," +
            "                                                   :departmentId," +
            "                                                   :userId)) FNSC" +
            " WHERE EXISTS" +
            " (SELECT 1" +
            "          FROM FINANCIAL_DEP_ORG_REL INER_ORG_REL" +
            "         WHERE INER_ORG_REL.ORGANIZATION_ID = :organizationId" +
            "           AND INER_ORG_REL.FINANCIAL_DEPARTMENT_ID = FNDP.ID" +
            "           AND INER_ORG_REL.ACTIVE_FLAG = 1" +
            "           AND (:departmentId IS NULL OR" +
            "               (:departmentId = INER_ORG_REL.DEPARTMENT_ID OR NOT EXISTS" +
            "                (SELECT 1" +
            "                    FROM FINANCIAL_DEP_ORG_REL INER_ORG_REL2" +
            "                   WHERE INER_ORG_REL2.FINANCIAL_DEPARTMENT_ID = FNDP.ID" +
            "                     AND INER_ORG_REL2.ORGANIZATION_ID =" +
            "                         INER_ORG_REL.ORGANIZATION_ID" +
            "                     AND INER_ORG_REL2.DEPARTMENT_ID IS NOT NULL))))"
            , nativeQuery = true)
    List<Object[]> getFinancialDocumentItemList(Long organizationIdPKG,
                                                String activityCode,
                                                TypedParameterValue financialPeriodId,
                                                TypedParameterValue financialDocumentTypeId,
                                                TypedParameterValue creatorUserId,
                                                Long userId,Long organizationId,Long departmentId);
}
