package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

}
