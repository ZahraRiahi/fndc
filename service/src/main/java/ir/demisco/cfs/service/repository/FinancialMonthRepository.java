package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialMonthRepository extends JpaRepository<FinancialMonth, Long> {
    @Query(" select fm.id from  FinancialMonth fm where fm.financialPeriod.id=:financialPeriodId ")
    List<Long> findByFinancialMonth(Long financialPeriodId);
}
