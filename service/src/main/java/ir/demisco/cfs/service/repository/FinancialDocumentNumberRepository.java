package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialDocumentNumberRepository extends JpaRepository<FinancialDocumentNumber, Long> {

    @Query("select fdn from FinancialDocumentNumber fdn where fdn.financialDocument.id =:financialDocumentId ")
    List<FinancialDocumentNumber> findByFinancialDocumentNumberAndFinancialDocumentId(Long financialDocumentId);

    @Query("select fdn from FinancialDocumentNumber fdn where  fdn.financialDocument.id in (:targetDocumentId,:financialDocumentId) ")
    List<FinancialDocumentNumber> findByFinancialDocumentNumberAndFinancialDocumentIdAndTarget(Long financialDocumentId, Long targetDocumentId);
}
