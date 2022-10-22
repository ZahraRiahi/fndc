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
            "       FNP.DESCRIPTION," +
            "       FNPS.NAME," +
            " FNLP.id as financialLedgerPeriodId" +
            "  FROM FNDC.FINANCIAL_LEDGER_PERIOD FNLP" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FNP" +
            "    ON FNLP.FINANCIAL_PERIOD_ID = FNP.ID" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_STATUS FNPS" +
            "    ON FNPS.ID = FNP.FINANCIAL_PERIOD_STATUS_ID" +
            " WHERE FNLP.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId ", nativeQuery = true)
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
            "   AND LP.FINANCIAL_DOCUMENT_TEMPRORY_ID IS NOT NULL  "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByIdClosingTemp(Long financialLedgerPeriodId);

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
            "               AND LP.FINANCIAL_DOCUMENT_PERMANENT_ID IS NOT NULL "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByPeriodId(Long financialLedgerPeriodId);

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

    @Query(value = " SELECT LP.FINANCIAL_DOCUMENT_PERMANENT_ID " +
            "              FROM FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "             WHERE LP.ID = :financialLedgerPeriodId  "
            , nativeQuery = true)
    Long getFinancialLedgerPeriodByPeriodIdPermanent(Long financialLedgerPeriodId);


}
