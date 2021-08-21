package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinancialDocumentReferenceRepository extends JpaRepository<FinancialDocumentReference,Long> {

    @Query(" select fdr from FinancialDocumentReference fdr where fdr.financialDocumentItem.id=:documentItemId ")
    FinancialDocumentReference getByDocumentItemId(Long documentItemId);
}
