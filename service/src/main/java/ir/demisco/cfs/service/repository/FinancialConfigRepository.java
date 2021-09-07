package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialConfigRepository extends JpaRepository<FinancialConfig, Long> {
    Long getFinancialConfigById(Long financialConfigId);
}
