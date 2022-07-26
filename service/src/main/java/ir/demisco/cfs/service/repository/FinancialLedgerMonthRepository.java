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
}
