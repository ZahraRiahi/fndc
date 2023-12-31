package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialNumberingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FinancialNumberingTypeRepository extends JpaRepository<FinancialNumberingType, Long> {

    @Query(value = " WITH QRY AS" +
            " (SELECT FNNT.ID," +
            "         FNNT.DESCRIPTION," +
            "         NF.SERIAL_LENGTH," +
            "         (SELECT REPLACE(REPLACE(REPLACE(REPLACE(NFT.CODE," +
            "                                                 '$LEG'," +
            "                                                 (SELECT LT.CODE" +
            "                                                    FROM fndc.FINANCIAL_LEDGER_TYPE LT" +
            "                                                   WHERE LT.ID =" +
            "                                                         T.FINANCIAL_LEDGER_TYPE_ID))," +
            "                                         '$DEP'," +
            "                                         (SELECT DP.CODE" +
            "                                            FROM ORG.DEPARTMENT DP" +
            "                                           WHERE DP.ID = :departmentId))," +
            "                                 '$ORG'," +
            "                                 (SELECT OG.CODE" +
            "                                    FROM FNDC.FINANCIAL_ORGANIZATION OG" +
            "                                   WHERE OG.ORGANIZATION_ID = :organizationId))," +
            "                         '$SRL'," +
            "                         '')" +
            "            FROM FNDC.FINANCIAL_CONFIG T" +
            "           WHERE T.ORGANIZATION_ID = :organizationId" +
            "             AND T.USER_ID = :userId) CODE" +
            "    FROM FNDC.FINANCIAL_NUMBERING_TYPE FNNT" +
            "   INNER JOIN FNDC.FINANCIAL_NUMBERING_FORMAT NF" +
            "      ON NF.FINANCIAL_NUMBERING_TYPE_ID = FNNT.ID" +
            "     AND NF.ORGANIZATION_ID = :organizationId" +
            "   INNER JOIN FNDC.FINANCIAL_NUMBERING_FORMAT_TYPE NFT" +
            "      ON NFT.ID = NF.NUMBERING_FORMAT_TYPE_ID)" +
            "SELECT QRY.ID," +
            "       QRY.DESCRIPTION," +
            "       QRY.SERIAL_LENGTH," +
            "       REPLACE(REPLACE(REPLACE(QRY.CODE," +
            "                               '$DAT6'," +
            "                               TO_CHAR(TO_DATE(TO_CHAR(:fromDate," +
            "                                                       'mm/dd/yyyy')," +
            "                                               'mm/dd/yyyy')," +
            "                                       'yymmdd'," +
            "                                       'NLS_CALENDAR=persian'))," +
            "                       '$DAT'," +
            "                       TO_CHAR(TO_DATE(TO_CHAR(:fromDate, 'mm/dd/yyyy')," +
            "                                       'mm/dd/yyyy')," +
            "                               'yyyymmdd'," +
            "                               'NLS_CALENDAR=persian'))," +
            "               " +
            "               '$PRI'," +
            "               (SELECT FP.CODE" +
            "                  FROM FNPR.FINANCIAL_PERIOD FP" +
            "                 INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT" +
            "                    ON FP.ID = FPT.FINANCIAL_PERIOD_ID" +
            "                   AND FPT.ORGANIZATION_ID = :organizationId" +
            "                   AND FPT.ACTIVE_FLAG = 1" +
            "                 INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY" +
            "                    ON FP.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID" +
            "                 WHERE TO_DATE(TO_CHAR(:fromDate, 'mm/dd/yyyy')," +
            "                               'mm/dd/yyyy') BETWEEN FP.START_DATE AND" +
            "                       FP.END_DATE))" +
            "       FROM_CODE," +
            "       REPLACE(REPLACE(REPLACE(QRY.CODE," +
            "                               '$DAT6'," +
            "                               TO_CHAR(TO_DATE(TO_CHAR(:toDate," +
            "                                                       'mm/dd/yyyy')," +
            "                                               'mm/dd/yyyy')," +
            "                                       'yymmdd'," +
            "                                       'NLS_CALENDAR=persian'))," +
            "                       '$DAT'," +
            "                       TO_CHAR(TO_DATE(TO_CHAR(:toDate, 'mm/dd/yyyy')," +
            "                                       'mm/dd/yyyy')," +
            "                               'yyyymmdd'," +
            "                               'NLS_CALENDAR=persian'))," +
            "               '$PRI'," +
            "               (SELECT FP.CODE" +
            "                  FROM FNPR.FINANCIAL_PERIOD FP" +
            "                 INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT" +
            "                    ON FP.ID = FPT.FINANCIAL_PERIOD_ID" +
            "                   AND FPT.ORGANIZATION_ID = :organizationId" +
            "                   AND FPT.ACTIVE_FLAG = 1" +
            "                 INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY" +
            "                    ON FP.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID" +
            "                 WHERE TO_DATE(TO_CHAR(:toDate, 'mm/dd/yyyy'), 'mm/dd/yyyy') BETWEEN" +
            "                       FP.START_DATE AND FP.END_DATE)) TO_CODE" +
            "  FROM QRY "
            , nativeQuery = true)
    List<Object[]> findByFinancialNumberingTypeAndOrganizationIdAndFromAndToDate(Long departmentId, Long organizationId, Long userId, LocalDateTime fromDate, LocalDateTime toDate);

    @Query(value = "   SELECT *  " +
            "    FROM FNDC.FINANCIAL_NUMBERING_TYPE T "
            , nativeQuery = true)
    List<Object[]> findByFinancialNumberingType();
}
