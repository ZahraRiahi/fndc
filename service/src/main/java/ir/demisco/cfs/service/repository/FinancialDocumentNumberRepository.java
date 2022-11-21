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
    @Query(value = " select DOC.* from FNDC.FINANCIAL_DOCUMENT_NUMBER DOC " +
            " WHERE DOC.FINANCIAL_NUMBERING_TYPE_ID IN " +
            "       (SELECT FT.ID" +
            "          FROM FNDC.FINANCIAL_NUMBERING_TYPE FT " +
            "         WHERE FT.TYPE_STATUS = 3) " +
            "   AND DOC.FINANCIAL_DOCUMENT_ID IN " +
            "       (SELECT FD.ID " +
            "          FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "         INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH LM " +
            "            ON LM.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
            "           AND LM.ID = :financialLedgerMonthId " +
            "         INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "            ON LP.ID = LM.FINANCIAL_LEDGER_PERIOD_ID " +
            "           AND LP.FINANCIAL_PERIOD_ID = FD.FINANCIAL_PERIOD_ID " +
            "           AND LP.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
            "         INNER JOIN FNDC.FINANCIAL_LEDGER_TYPE LT " +
            "            ON LT.ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
            "           AND LT.ORGANIZATION_ID = :organizationId " +
            "         INNER JOIN FNPR.FINANCIAL_MONTH FM " +
            "            ON FM.ID = LM.FINANCIAL_MONTH_ID " +
            "         WHERE FD.ORGANIZATION_ID = LT.ORGANIZATION_ID " +
            "           AND FD.DOCUMENT_DATE BETWEEN FM.START_DATE AND FM.END_DATE " +
            "           AND FD.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "           AND FD.FINANCIAL_PERIOD_ID = :financialPeriodId) ", nativeQuery = true)
    List<FinancialDocumentNumber> getFinancialDocumentNumberByOrgAndPeriodId(Long financialLedgerMonthId, Long organizationId, Long financialLedgerTypeId,Long financialPeriodId);

    @Query(value = "   select T  FROM FNDC.FINANCIAL_DOCUMENT_NUMBER T " +
            " WHERE EXISTS (SELECT 1 " +
            "          FROM FNDC.FINANCIAL_NUMBERING_TYPE TT " +
            "         WHERE TT.TYPE_STATUS = 3 " +
            "           AND TT.ID = T.FINANCIAL_NUMBERING_TYPE_ID)" +
            "   AND T.FINANCIAL_DOCUMENT_ID = :financialDocumentId "
            , nativeQuery = true)
    List<FinancialDocumentNumber> findByFinancialDocumentNumberByDocumentId(Long financialDocumentId);
}
