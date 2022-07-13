package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
