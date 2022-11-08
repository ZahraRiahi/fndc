package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface FinancialLedgerPeriodRepository extends JpaRepository<FinancialLedgerPeriod, Long> {
    @Query(value = " select count(flp.id)" +
            "  from fndc.financial_ledger_period flp" +
            " inner join fnpr.financial_period fp" +
            "    on fp.id = flp.financial_period_id" +
            " inner join fndc.financial_ledger_type flt" +
            "    on flt.id = flp.financial_ledger_type_id" +
            " where flp.financial_period_id = :financialPeriodId" +
            "   and flp.financial_ledger_type_id = :financialLedgerTypeId "
            , nativeQuery = true)
    Long getCountByFinancialLedgerPeriodByPeriodIdAndLedgerTypeId(Long financialPeriodId, Long financialLedgerTypeId);

    @Query(value = " SELECT FNP.ID," +
            "       FNP.START_DATE," +
            "       FNP.END_DATE," +
            "       FNP.OPEN_MONTH_COUNT," +
            "       FNP.DESCRIPTION as description," +
            "       FNPS.NAME," +
            "       FNPS.CODE PERIOD_STATUS_CODE," +
            "       LPS.CODE LEDGER_PERIOD_STATUS_CODE," +
            "       LPS.DESCRIPTION LEDGER_PERIOD_STATUS_DES," +
            "       FNLP.ID as FINANCIAL_LEDGER_PERIOD_ID, " +
            "       FNLP.id as financialLedgerPeriodId " +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD FNLP " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FNP " +
            "    ON FNLP.FINANCIAL_PERIOD_ID = FNP.ID " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_STATUS FNPS " +
            "    ON FNPS.ID = FNP.FINANCIAL_PERIOD_STATUS_ID " +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD_STATUS LPS " +
            "    ON LPS.ID = FNLP.FIN_LEDGER_PERIOD_STAT_ID " +
            " WHERE FNLP.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " , nativeQuery = true)
    Page<Object[]> findByFinancialLedgerTypeIdAndId(Long financialLedgerTypeId, Pageable pageable);

    @Query(value = " SELECT FNLP.ID    AS FINANCIAL_LEDGER_PERIOD_ID," +
            "       FNLT.ID   AS FINANCIAL_LEDGER_TYPE_ID," +
            "       FNLT.DESCRIPTION AS FINANCIAL_LEDGER_TYPE_DESC" +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD FNLP " +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_TYPE FNLT" +
            "    ON FNLP.FINANCIAL_LEDGER_TYPE_ID = FNLT.ID" +
            " WHERE FNLP.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "   AND FNLT.ORGANIZATION_ID = :organizationId", nativeQuery = true)
    List<Object[]> getFinancialLedgerTypeByOrganizationAndPeriodId(Long organizationId, Long financialPeriodId);

    @Query(value = " SELECT FNP.ID, FNP.DESCRIPTION" +
            "  FROM FNPR.FINANCIAL_PERIOD FNP" +
            " WHERE NOT EXISTS" +
            " (SELECT 1" +
            "          FROM FNDC.FINANCIAL_LEDGER_PERIOD T" +
            "         WHERE T.FINANCIAL_PERIOD_ID = FNP.ID" +
            "           AND T.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId) ", nativeQuery = true)
    List<Object[]> getFinancialLedgerTypeById(Long financialLedgerTypeId);

    @Query(value = "select T.id" +
            " from  FNDC.FINANCIAL_LEDGER_PERIOD T " +
            "  WHERE T.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "     AND T.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId ", nativeQuery = true)
    Long getFinancialLedgerPeriodForDelete(Long financialPeriodId, Long financialLedgerTypeId);

    @Query(value = "SELECT LM.FIN_LEDGER_MONTH_STAT_ID" +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD LP" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP" +
            "    ON FP.ID = LP.FINANCIAL_PERIOD_ID" +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH LM" +
            "    ON LM.FINANCIAL_LEDGER_PERIOD_ID = LP.ID" +
            " INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "    ON FM.ID = LM.FINANCIAL_MONTH_ID" +
            " WHERE LP.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId" +
            "   AND FM.END_DATE =" +
            "       (SELECT CASE" +
            "                 WHEN :nextPrevMonth = -1 THEN" +
            "                  MAX(FM.END_DATE)" +
            "                 WHEN :nextPrevMonth = 1 THEN" +
            "                  MIN(FM.END_DATE)" +
            "               END" +
            "          FROM FNDC.FINANCIAL_LEDGER_PERIOD LP" +
            "         INNER JOIN FNPR.FINANCIAL_PERIOD FP" +
            "            ON FP.ID = LP.FINANCIAL_PERIOD_ID" +
            "         INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH LM" +
            "            ON LM.FINANCIAL_LEDGER_PERIOD_ID = LP.ID" +
            "         INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "            ON FM.ID = LM.FINANCIAL_MONTH_ID" +
            "           AND ((:nextPrevMonth = -1 AND FM.END_DATE < trunc(:startDate)) OR" +
            "               (:nextPrevMonth = 1 AND FM.END_DATE > trunc(:endDate)))" +
            "         WHERE LP.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId)", nativeQuery = true)
    Long getFinancialLedgerPeriodByLedgerAndNextAndDate(Long financialLedgerTypeId, Long nextPrevMonth, Date startDate, Date endDate);

    @Query(value = " SELECT 1 " +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            " WHERE LP.ID = :financialLedgerPeriodId  " +
            "   AND (LP.FINANCIAL_DOCUMENT_TEMPRORY_ID IS NOT NULL OR " +
            "       LP.FINANCIAL_DOCUMENT_PERMANENT_ID IS NOT NULL) "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodById(Long financialLedgerPeriodId);

    @Query(value = " SELECT 1 " +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            " WHERE LP.ID = :financialLedgerPeriodId  " +
            "   AND (LP.FINANCIAL_DOCUMENT_TEMPRORY_ID IS NOT NULL OR LP.FIN_LEDGER_PERIOD_STAT_ID  != 2) "
            , nativeQuery = true)
    List<Long> getFinancialLedgerPeriodByIdClosingTemp(Long financialLedgerPeriodId);

    @Query(value = " SELECT 1 " +
            "      FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "     WHERE LP.ID = :financialLedgerPeriodId " +
            "       AND LP.FINANCIAL_DOCUMENT_OPENING_ID IS NULL  "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByIdDocumentOpen(Long financialLedgerPeriodId);


    @Query(value = " SELECT 1 " +
            "                  FROM fndc.FINANCIAL_LEDGER_PERIOD FP " +
            "                 WHERE FP.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "                   AND FP.FINANCIAL_PERIOD_ID = :financialPeriodId  "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByIdAndLedgerType(Long financialLedgerTypeId, Long financialPeriodId);

    @Query(value = " SELECT 1 " +
            "              FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "             WHERE LP.ID = :financialLedgerPeriodId " +
            "               AND (LP.FINANCIAL_DOCUMENT_PERMANENT_ID IS NOT NULL OR " +
            "       LP.FIN_LEDGER_PERIOD_STAT_ID != 3)  "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByPeriodId(Long financialLedgerPeriodId);

    @Query(value = " SELECT 1 " +
            "              FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "             WHERE LP.ID = :financialLedgerPeriodId " +
            "               AND (LP.FINANCIAL_DOCUMENT_PERMANENT_ID IS NOT NULL   OR LP.FIN_LEDGER_PERIOD_STAT_ID = 4) "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByPeriodIdDel(Long financialLedgerPeriodId);

    @Query(value = " SELECT LP.FINANCIAL_DOCUMENT_TEMPRORY_ID " +
            "   FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "  WHERE LP.ID = :financialLedgerPeriodId "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByTemprory(Long financialLedgerPeriodId);

    @Query(value = " SELECT 1 " +
            "              FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "             WHERE LP.ID = :financialLedgerPeriodId " +
            "               AND LP.FINANCIAL_DOCUMENT_OPENING_ID IS NOT NULL "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByPeriodIdOpenning(Long financialLedgerPeriodId);

    @Query(value = " SELECT 1 " +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP" +
            "    ON LP.FINANCIAL_PERIOD_ID = FP.ID" +
            " WHERE LP.FINANCIAL_DOCUMENT_OPENING_ID IS NOT NULL" +
            "   AND LP.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "   AND FP.START_DATE =" +
            "       (SELECT MIN(FP_IN.START_DATE)" +
            "          FROM FNDC.FINANCIAL_LEDGER_PERIOD LP_IN" +
            "         INNER JOIN FNPR.FINANCIAL_PERIOD FP_IN" +
            "            ON LP_IN.FINANCIAL_PERIOD_ID = FP_IN.ID" +
            "         INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FA_IN" +
            "            ON FA_IN.FINANCIAL_PERIOD_ID = FP_IN.ID" +
            "           AND FA_IN.ORGANIZATION_ID = :organizationId " +
            "         INNER JOIN FNDC.FINANCIAL_LEDGER_TYPE LT_IN" +
            "            ON LT_IN.ORGANIZATION_ID = FA_IN.ORGANIZATION_ID" +
            "           AND LT_IN.ID = LP_IN.FINANCIAL_LEDGER_TYPE_ID" +
            "         WHERE FP_IN.START_DATE >" +
            "               (SELECT FP_IN2.END_DATE" +
            "                  FROM FNPR.FINANCIAL_PERIOD FP_IN2" +
            "                 WHERE FP_IN2.ID = :financialPeriodId)" +
            "           AND LP_IN.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId) "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByTypeLedgerAndOrgan(Long financialLedgerTypeId, Long organizationId, Long financialPeriodId);

    @Query(value = " SELECT LP.FINANCIAL_DOCUMENT_PERMANENT_ID " +
            "              FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "             WHERE LP.ID = :financialLedgerPeriodId  "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByPeriodIdPermanent(Long financialLedgerPeriodId);

    @Query(value = " SELECT MAX(FP_IN.END_DATE) " +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD LP_IN " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP_IN " +
            "    ON LP_IN.FINANCIAL_PERIOD_ID = FP_IN.ID " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FA_IN " +
            "    ON FA_IN.FINANCIAL_PERIOD_ID = FP_IN.ID " +
            "   AND FA_IN.ORGANIZATION_ID = :organizationId " +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_TYPE LT_IN " +
            "    ON LT_IN.ORGANIZATION_ID = FA_IN.ORGANIZATION_ID " +
            "   AND LT_IN.ID = LP_IN.FINANCIAL_LEDGER_TYPE_ID " +
            " WHERE FP_IN.END_DATE < trunc(:startDate) " +
            "   AND LP_IN.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId  "
            , nativeQuery = true)
    Date getFinancialLedgerPeriodByOrganAndStartDate(Long organizationId, Date startDate, Long financialLedgerTypeId);

    @Query(value = " SELECT LP.ID, LP.FINANCIAL_DOCUMENT_PERMANENT_ID " +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP " +
            "    ON LP.FINANCIAL_PERIOD_ID = FP.ID" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FA " +
            "    ON FA.FINANCIAL_PERIOD_ID = FP.ID " +
            "   AND FA.ORGANIZATION_ID = :organizationId " +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_TYPE LT " +
            "    ON LT.ORGANIZATION_ID = FA.ORGANIZATION_ID " +
            "   AND LT.ID = LP.FINANCIAL_LEDGER_TYPE_ID " +
            " WHERE trunc(FP.END_DATE) = trunc(:endDate) " +
            "   AND LP.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId "
            , nativeQuery = true)
    List<Object[]> getFinancialLedgerPeriodByOrganAndEndDate(Long organizationId, Date endDate, Long financialLedgerTypeId);

    @Query(value = " SELECT LP.FINANCIAL_DOCUMENT_OPENING_ID, LP.FIN_LEDGER_PERIOD_STAT_ID " +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            " WHERE LP.ID = :financialLedgerPeriodId  "
            , nativeQuery = true)
    List<Object[]> getFinancialLedgerPeriodByIdOpen(Long financialLedgerPeriodId);

    @Query(value = " SELECT LP.ID                        FINANCIAL_LEDGER_PERIOD_ID," +
            "       FP.ID                        FINANCIAL_PERIOD_ID," +
            "       FP.START_DATE                PERIOD_START_DATE," +
            "       FP.END_DATE                  PERIOD_END_DATE," +
            "       FP.DESCRIPTION               PERIOD_DESCRIPTION," +
            "       FD_OPENING.DOCUMENT_NUMBER   OPENING_DOC_NUMBER," +
            "       FD_OPENING.DOCUMENT_DATE     OPENING_DOC_DATE," +
            "       FD_OPENING.ID                OPENING_DOC_ID," +
            "       FD_TMP_CLOSE.DOCUMENT_NUMBER TEMPORARY_DOC_NUMBER," +
            "       FD_TMP_CLOSE.DOCUMENT_DATE   TEMPORARY_DOC_DATE," +
            "       FD_TMP_CLOSE.ID              TEMPORARY_DOC_ID," +
            "       FD_PRM_CLOSE.DOCUMENT_NUMBER PERMANENT_DOC_NUMBER," +
            "       FD_PRM_CLOSE.DOCUMENT_DATE   PERMANENT_DOC_DATE," +
            "       FD_PRM_CLOSE.ID              PERMANENT_DOC_ID," +
            "       LS.ID                        LEDGER_PERIOD_STATUS_ID," +
            "       LS.DESCRIPTION               LEDGER_PERIOD_STATUS_DES, " +
            " CASE" +
            "         WHEN (LP.FINANCIAL_DOCUMENT_TEMPRORY_ID IS NOT NULL) THEN" +
            "          1" +
            "         ELSE" +
            "          0" +
            "       END AS TEMP_CLOSED_FLAG," +
            "       CASE" +
            "         WHEN (LP.FINANCIAL_DOCUMENT_OPENING_ID IS NOT NULL) THEN" +
            "          1" +
            "         ELSE" +
            "          0" +
            "       END AS HAS_OPENING_FLAG," +
            "       " +
            "       CASE" +
            "         WHEN (LP.FINANCIAL_DOCUMENT_PERMANENT_ID IS NOT NULL) THEN" +
            "          1" +
            "         ELSE" +
            "          0" +
            "       END AS PERMANENT_CLOSED_FLAG" +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD LP" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP" +
            "    ON FP.ID = LP.FINANCIAL_PERIOD_ID" +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD_STATUS LS" +
            "    ON LS.ID = LP.FIN_LEDGER_PERIOD_STAT_ID" +
            "  LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT FD_OPENING" +
            "    ON FD_OPENING.ID = LP.FINANCIAL_DOCUMENT_OPENING_ID" +
            "  LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT FD_TMP_CLOSE" +
            "    ON FD_TMP_CLOSE.ID = LP.FINANCIAL_DOCUMENT_TEMPRORY_ID" +
            "  LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT FD_PRM_CLOSE" +
            "    ON FD_PRM_CLOSE.ID = LP.FINANCIAL_DOCUMENT_PERMANENT_ID" +
            " WHERE LP.ID = :financialLedgerPeriodId  "
            , nativeQuery = true)
    Page<Object[]> getFinancialLedgerPeriodByPeriodIdGet(Long financialLedgerPeriodId, Pageable pageable);

    @Query(value = " SELECT 1 " +
            "              FROM FNDC.FINANCIAL_LEDGER_PERIOD T " +
            "             inner join FNDC.FINANCIAL_LEDGER_MONTH LM " +
            "                ON LM.FINANCIAL_LEDGER_PERIOD_ID = T.ID " +
            "             INNER JOIN FNPR.FINANCIAL_PERIOD FP " +
            "                ON FP.ID = T.FINANCIAL_PERIOD_ID " +
            "             INNER JOIN FNPR.FINANCIAL_MONTH FM " +
            "                ON FM.ID = LM.FINANCIAL_MONTH_ID " +
            "               AND FM.FINANCIAL_PERIOD_ID = T.FINANCIAL_PERIOD_ID " +
            "               AND FM.START_DATE = FP.START_DATE " +
            "             WHERE T.ID = :financialLedgerPeriodId " +
            "             AND T.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "               AND LM.FIN_LEDGER_MONTH_STAT_ID != 1 "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByPeriodId(Long financialLedgerPeriodId, Long financialPeriodId);

    @Query(value = " SELECT 1 " +
            "              FROM FNDC.FINANCIAL_LEDGER_PERIOD T        " +
            "             WHERE T.ID = :financialLedgerPeriodId  " +
            "               AND t.FIN_LEDGER_PERIOD_STAT_ID != 1  "
            , nativeQuery = true)
    Long getFinancialLedgerPeriod(Long financialLedgerPeriodId);
}



