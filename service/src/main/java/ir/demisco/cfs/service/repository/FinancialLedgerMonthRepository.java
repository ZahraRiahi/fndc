package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerMonth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialLedgerMonthRepository extends JpaRepository<FinancialLedgerMonth, Long> {
}
