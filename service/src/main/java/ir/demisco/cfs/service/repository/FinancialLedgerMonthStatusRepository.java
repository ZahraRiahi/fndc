package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerMonthStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialLedgerMonthStatusRepository extends JpaRepository<FinancialLedgerMonthStatus, Long> {
}
