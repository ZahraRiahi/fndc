package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialDocumentStatusRepository extends JpaRepository<FinancialDocumentStatus,Long> {
}
