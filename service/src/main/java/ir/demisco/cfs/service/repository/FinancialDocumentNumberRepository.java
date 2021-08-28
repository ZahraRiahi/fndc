package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialDocumentNumberRepository extends JpaRepository<FinancialDocumentNumber,Long> {
}
