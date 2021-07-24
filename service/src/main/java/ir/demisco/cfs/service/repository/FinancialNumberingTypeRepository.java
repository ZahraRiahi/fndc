package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialNumberingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialNumberingTypeRepository extends JpaRepository<FinancialNumberingType,Long> {
}
