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

    List<FinancialDocumentNumber> findByFinancialDocumentIdAndDeletedDateIsNull(Long financialDocumentId);

    @Query(value = "   select * " +
            "          from FNDC.FINANCIAL_DOCUMENT_NUMBER T" +
            "         WHERE T.FINANCIAL_DOCUMENT_ID = :financialDocumentId " +
            "           AND EXISTS " +
            "         (SELECT 1" +
            "                  FROM FNDC.FINANCIAL_NUMBERING_TYPE NFT" +
            "                 WHERE NFT.ID = T.FINANCIAL_NUMBERING_TYPE_ID" +
            "                   AND NFT.TYPE_STATUS = :financialNumberingTypeId)"
            , nativeQuery = true)
    List<FinancialDocumentNumber> findByFinancialDocumentIdList(Long financialDocumentId,Long financialNumberingTypeId);

}
