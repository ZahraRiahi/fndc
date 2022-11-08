package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerMonth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialLedgerMonthRepository extends JpaRepository<FinancialLedgerMonth, Long> {
    @Query(value = " select count(flm.id)" +
            "  from fndc.financial_ledger_month flm" +
            " inner join fndc.financial_ledger_type flt" +
            "    on flt.id = flm.financial_ledger_type_id" +
            " inner join fndc.financial_ledger_period flp" +
            " on flp.id=flm.financial_ledger_period_id  " +
            " inner join fnpr.financial_month fm " +
            " on fm.id=flm.financial_month_id " +
            " where flm.financial_month_id = :financialMonthId" +
            "   and flm.financial_ledger_type_id = :financialLedgerTypeId" +
            " and flm. financial_ledger_period_id =:financialLedgerPeriodId"
            , nativeQuery = true)
    Long getCountByFinancialLedgerMonthByIdAndLedgerTypeIdAndLedgerPeriod(Long financialMonthId, Long financialLedgerTypeId, Long financialLedgerPeriodId);

    @Query(value = " SELECT LM.FIN_LEDGER_MONTH_STAT_ID" +
            "  FROM FNDC.FINANCIAL_LEDGER_MONTH LM" +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP" +
            "    ON LP.ID = LM.FINANCIAL_LEDGER_PERIOD_ID" +
            " INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "    ON FM.ID = LM.FINANCIAL_MONTH_ID" +
            " INNER JOIN FNPR.FINANCIAL_MONTH_TYPE MT" +
            "    ON MT.ID = FM.FINANCIAL_MONTH_TYPE_ID" +
            "   AND LM.FINANCIAL_LEDGER_PERIOD_ID = :financialLedgerPeriodId " +
            "   AND MT.MONTH_NUMBER =" +
            "       (SELECT MT.MONTH_NUMBER + :nextPrevMonth " +
            "          FROM FNDC.FINANCIAL_LEDGER_MONTH LM" +
            "         INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP" +
            "            ON LP.ID = LM.FINANCIAL_LEDGER_PERIOD_ID" +
            "         INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "            ON FM.ID = LM.FINANCIAL_MONTH_ID" +
            "         INNER JOIN FNPR.FINANCIAL_MONTH_TYPE MT" +
            "            ON MT.ID = FM.FINANCIAL_MONTH_TYPE_ID" +
            "         WHERE LM.ID = :financialLedgerMonthId)"
            , nativeQuery = true)
    Long getLedgerMonthByLedgerPeriodAndPrevMonth(Long financialLedgerPeriodId, Long nextPrevMonth, Long financialLedgerMonthId);

    @Query(" select 1 from FinancialLedgerMonth flm " +
            " where flm.id = :financialLedgerMonthId " +
            " and flm.financialLedgerMonthStatus.id=2 ")
    Long getFinancialLedgerMonthById(Long financialLedgerMonthId);

    @Query(" select 1 from FinancialLedgerMonth flm " +
            " where flm.id = :financialLedgerMonthId " +
            " and flm.financialLedgerMonthStatus.id=1 ")
    Long getOpenFinancialLedgerMonthById(Long financialLedgerMonthId);

    @Query(value = " SELECT 1 " +
            "              FROM FNDC.FINANCIAL_LEDGER_MONTH LM " +
            "             WHERE LM.FINANCIAL_LEDGER_PERIOD_ID = :financialLedgerPeriodId  " +
            "               AND LM.FIN_LEDGER_MONTH_STAT_ID = 1 " +
            "             AND LM.ID <> :financialLedgerMonthId  " +
            "             HAVING(COUNT(*) + 1) >  " +
            "                   (SELECT FP.OPEN_MONTH_COUNT " +
            "                      FROM FNPR.FINANCIAL_PERIOD FP " +
            "                     WHERE FP.ID = :financialPeriodId) "
            , nativeQuery = true)
    Long getOpenFinancialLedgerMonthByIdAndPeriodId(Long financialLedgerPeriodId, Long financialLedgerMonthId, Long financialPeriodId);

    @Query(" select 1 from FinancialLedgerMonth flm " +
            " where flm.financialLedgerPeriod.id = :financialLedgerPeriodId " +
            " and flm.financialLedgerMonthStatus.id=1 ")
    Long getFinancialLedgerMonthByLedgerPeriodId(Long financialLedgerPeriodId);

    @Query(value = " SELECT MIN(FD.DOCUMENT_NUMBER) MIN_DOC_TMP_NUMBER, " +
            "       MAX(FD.DOCUMENT_NUMBER) MAX_DOC_TMP_NUMBER," +
            "       MIN(FD.PERMANENT_DOCUMENT_NUMBER) MIN_DOC_PRM_NUMBER, " +
            "       MAX(FD.PERMANENT_DOCUMENT_NUMBER) MAX_DOC_PRM_NUMBER, " +
            "       LP.FINANCIAL_PERIOD_ID , " +
            "       LP.FINANCIAL_LEDGER_TYPE_ID, " +
            "       FM.DESCRIPTION MONTH_DESCRIPTION, " +
            "       FM.START_DATE MONTH_START_DATE, " +
            "       FM.END_DATE MONTH_END_DATE, " +
            "       LM.ID FINANCIAL_LEDGER_MONTH_ID, " +
            "       LMS.ID MONTH_STATUS_ID, " +
            "       LMS.CODE MONTH_STATUS_CODE, " +
            "       LMS.DESCRIPTION MONTH_STATUS_DESCRIPTION, " +
            "       LP.FINANCIAL_DOCUMENT_OPENING_ID, " +
            "       LP.FINANCIAL_DOCUMENT_TEMPRORY_ID, " +
            "       LP.FINANCIAL_DOCUMENT_PERMANENT_ID, " +
            "       CASE " +
            "         WHEN (LP.FINANCIAL_DOCUMENT_TEMPRORY_ID IS NOT NULL) THEN " +
            "          1 " +
            "         ELSE " +
            "          0 " +
            "       END AS TEMP_CLOSED_FLAG, " +
            "       CASE " +
            "         WHEN (LP.FINANCIAL_DOCUMENT_OPENING_ID IS NOT NULL) THEN " +
            "          1 " +
            "         ELSE"  +
            "          0 " +
            "       END AS HAS_OPENING_FLAG, " +
            "       CASE " +
            "         WHEN (LP.FINANCIAL_DOCUMENT_PERMANENT_ID IS NOT NULL) THEN " +
            "          1 " +
            "         ELSE " +
            "          0 " +
            "       END AS PERMANENT_CLOSED_FLAG " +
            "  FROM FNDC.FINANCIAL_LEDGER_MONTH LM " +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "    ON LP.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "   AND LP.ID = LM.FINANCIAL_LEDGER_PERIOD_ID" +
            "   AND LM.FINANCIAL_LEDGER_TYPE_ID = LP.FINANCIAL_LEDGER_TYPE_ID " +
            " INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "    ON FM.FINANCIAL_PERIOD_ID = LP.FINANCIAL_PERIOD_ID" +
            "   AND FM.ID = LM.FINANCIAL_MONTH_ID" +
            " LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT FD" +
            "    ON FD.FINANCIAL_PERIOD_ID = LP.FINANCIAL_PERIOD_ID" +
            "   AND FD.FINANCIAL_LEDGER_TYPE_ID = LP.FINANCIAL_LEDGER_TYPE_ID" +
            "   AND FD.DOCUMENT_DATE BETWEEN FM.START_DATE AND FM.END_DATE " +
            "   AND FD.FINANCIAL_DOCUMENT_TYPE_ID NOT IN (70, 71, 72) " +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH_STATUS LMS " +
            "    ON LMS.ID = LM.FIN_LEDGER_MONTH_STAT_ID " +
            " WHERE LM.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            " GROUP BY LP.FINANCIAL_PERIOD_ID, " +
            "          LP.FINANCIAL_LEDGER_TYPE_ID, " +
            "          FM.START_DATE, " +
            "          FM.END_DATE, " +
            "          LM.ID, " +
            "          LP.FINANCIAL_DOCUMENT_OPENING_ID, " +
            "          LP.FINANCIAL_DOCUMENT_TEMPRORY_ID, " +
            "          LP.FINANCIAL_DOCUMENT_PERMANENT_ID, " +
            "          FM.DESCRIPTION, " +
            "          LMS.ID," +
            "          LMS.CODE," +
            "          LMS.DESCRIPTION " +
            " ORDER BY FM.START_DATE "
            , countQuery = " SELECT count(LM.id)" +
            "             FROM FNDC.FINANCIAL_LEDGER_MONTH LM " +
            "            INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "               ON LP.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "              AND LP.ID = LM.FINANCIAL_LEDGER_PERIOD_ID " +
            "              AND LM.FINANCIAL_LEDGER_TYPE_ID = LP.FINANCIAL_LEDGER_TYPE_ID " +
            "            INNER JOIN FNPR.FINANCIAL_MONTH FM "  +
            "               ON FM.FINANCIAL_PERIOD_ID = LP.FINANCIAL_PERIOD_ID " +
            "              AND FM.ID = LM.FINANCIAL_MONTH_ID " +
            "            LEFT OUTER JOIN  FNDC.FINANCIAL_DOCUMENT FD " +
            "               ON FD.FINANCIAL_PERIOD_ID = LP.FINANCIAL_PERIOD_ID " +
            "              AND FD.FINANCIAL_LEDGER_TYPE_ID = LP.FINANCIAL_LEDGER_TYPE_ID " +
            "              AND FD.DOCUMENT_DATE BETWEEN FM.START_DATE AND FM.END_DATE " +
            "              AND FD.FINANCIAL_DOCUMENT_TYPE_ID NOT IN (70, 71, 72) " +
            "            INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH_STATUS LMS " +
            "               ON LMS.ID = LM.FIN_LEDGER_MONTH_STAT_ID " +
            "            AND LM.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "            GROUP BY LP.FINANCIAL_PERIOD_ID, " +
            "                     LP.FINANCIAL_LEDGER_TYPE_ID, " +
            "                     FM.START_DATE, " +
            "                     FM.END_DATE, " +
            "                     LM.ID, " +
            "                     LP.FINANCIAL_DOCUMENT_OPENING_ID, " +
            "                     LP.FINANCIAL_DOCUMENT_TEMPRORY_ID, " +
            "                     LP.FINANCIAL_DOCUMENT_PERMANENT_ID, " +
            "                     FM.DESCRIPTION, " +
            "                     LMS.ID," +
            "                     LMS.CODE," +
            "                     LMS.DESCRIPTION " +
            "            ORDER BY FM.START_DATE "
            , nativeQuery = true)
    Page<Object[]> getFinancialLedgerMonthList(Long financialPeriodId, Long financialLedgerTypeId, Pageable pageable);
    @Query(value = " SELECT 1 " +
            "  FROM FNDC.FINANCIAL_LEDGER_MONTH LM" +
            " INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "    ON LM.FINANCIAL_MONTH_ID = FM.ID" +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP" +
            "    ON LP.ID = :financialLedgerPeriodId " +
            "   AND LM.FINANCIAL_LEDGER_TYPE_ID = LP.FINANCIAL_LEDGER_TYPE_ID" +
            "   AND LP.FINANCIAL_PERIOD_ID = FM.FINANCIAL_PERIOD_ID" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP" +
            "    ON FP.ID = LP.FINANCIAL_PERIOD_ID" +
            " WHERE FP.START_DATE = FM.START_DATE" +
            "   AND LM.FIN_LEDGER_MONTH_STAT_ID = 2 "
            , nativeQuery = true)
    List<Long> getFinancialLedgerMonth(Long financialLedgerPeriodId);

    @Query(value = " SELECT CASE " +
            "         WHEN MT.MONTH_NUMBER <= FP.OPEN_MONTH_COUNT THEN " +
            "          1 " +
            "         ELSE " +
            "          2 " +
            "       END, " +
            "       T.ID " +
            "  FROM FNPR.FINANCIAL_MONTH T " +
            " INNER JOIN FNPR.FINANCIAL_MONTH_TYPE MT " +
            "    ON MT.ID = T.FINANCIAL_MONTH_TYPE_ID " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP " +
            "    ON FP.ID = T.FINANCIAL_PERIOD_ID " +
            " WHERE T.FINANCIAL_PERIOD_ID = :financialPeriodId  ", nativeQuery = true)
    List<Object[]> findByFinancialPeriodId(Long financialPeriodId);


    @Query(value = " SELECT MT.MONTH_NUMBER," +
            "       FP.START_DATE," +
            "       FP.END_DATE," +
            "       LP.FINANCIAL_LEDGER_TYPE_ID" +
            "  FROM FNDC.FINANCIAL_LEDGER_MONTH LM" +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP" +
            "    ON LP.ID = LM.FINANCIAL_LEDGER_PERIOD_ID" +
            " INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "    ON FM.ID = LM.FINANCIAL_MONTH_ID" +
            " INNER JOIN FNPR.FINANCIAL_MONTH_TYPE MT" +
            "    ON MT.ID = FM.FINANCIAL_MONTH_TYPE_ID" +
            "   AND LM.ID = :financialLedgerMonthId  " +
            "   AND LM.FINANCIAL_LEDGER_PERIOD_ID = :financialLedgerPeriodId " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP" +
            "    ON FP.ID = LP.FINANCIAL_PERIOD_ID ", nativeQuery = true)
    List<Object[]> findByFinancialLedgerMonthByPeriodId(Long financialLedgerMonthId,Long financialLedgerPeriodId);


}
