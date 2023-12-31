package ir.demisco.cfs.service.repository;


import ir.demisco.cfs.model.entity.FinancialDocumentReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialDocumentReferenceRepository extends JpaRepository<FinancialDocumentReference, Long> {

    @Query(" select fdr from FinancialDocumentReference fdr where fdr.financialDocumentItem.id=:documentItemId ")
    FinancialDocumentReference getByDocumentItemId(Long documentItemId);

    List<FinancialDocumentReference> findByFinancialDocumentItemId(Long financialDocumentItemId);

    @Query(" select 1 from FinancialDocumentReference fr " +
            " where fr.financialDocumentItem.id = :financialDocumentItemId " +
            " and ((fr.referenceDate is null) or (fr.referenceDescription is null))")
    Long getDocumentReference(Long financialDocumentItemId);

    @Query("select fdr from FinancialDocumentReference fdr where fdr.financialDocumentItem.id in (:documentItemIdList) ")
    List<FinancialDocumentReference> findByFinancialDocumentItemReferenceIdList(List<Long> documentItemIdList);

}
