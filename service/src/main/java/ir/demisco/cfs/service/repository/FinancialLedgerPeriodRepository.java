package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
            "       FNPS.NAME" +
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
}
