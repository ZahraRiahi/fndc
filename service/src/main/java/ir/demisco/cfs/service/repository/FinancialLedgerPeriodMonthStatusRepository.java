package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerMonthStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialLedgerPeriodMonthStatusRepository extends JpaRepository<FinancialLedgerMonthStatus, Long> {

    @Query(value = "       SELECT MT.MONTH_NUMBER, FP.START_DATE, FP.END_DATE , LP.FINANCIAL_LEDGER_TYPE_ID " +
            "        FROM FNDC.FINANCIAL_LEDGER_MONTH LM" +
            "       INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP" +
            "          ON LP.ID = LM.FINANCIAL_LEDGER_PERIOD_ID" +
            "       INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "          ON FM.ID = LM.FINANCIAL_MONTH_ID" +
            "       INNER JOIN FNPR.FINANCIAL_MONTH_TYPE MT" +
            "          ON MT.ID = FM.FINANCIAL_MONTH_TYPE_ID" +
            "         AND LM.ID = :financialLedgerMonthId " +
            "         AND LM.FINANCIAL_LEDGER_PERIOD_ID = :financialLedgerPeriodId" +
            "       INNER JOIN FNPR.FINANCIAL_PERIOD FP" +
            "          ON FP.ID = LP.FINANCIAL_PERIOD_ID "
            , nativeQuery = true)
    List<Object[]> getFinancialLedgerPeriodMonthStatusList(Long financialLedgerMonthId,Long financialLedgerPeriodId);
}
