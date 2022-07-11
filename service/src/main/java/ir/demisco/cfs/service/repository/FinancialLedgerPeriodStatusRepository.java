package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerPeriodStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialLedgerPeriodStatusRepository extends JpaRepository<FinancialLedgerPeriodStatus, Long> {
}
