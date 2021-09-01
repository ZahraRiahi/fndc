package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialPeriodRepository extends JpaRepository<FinancialPeriod,Long> {
}
