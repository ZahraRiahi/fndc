package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentItemCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FinancialDocumentItemCurrencyRepository extends JpaRepository<FinancialDocumentItemCurrency, Long> {

    @Query(" select dic from FinancialDocumentItemCurrency dic where dic.financialDocumentItem.id=:documentItemId ")
    FinancialDocumentItemCurrency getByDocumentItemId(Long documentItemId);

    List<FinancialDocumentItemCurrency> findByFinancialDocumentItemIdAndDeletedDateIsNull(Long documentItemId);

    @Query("select dic from FinancialDocumentItemCurrency dic where dic.financialDocumentItem.id in (:documentItemIdList)  and dic.deletedDate is null")
    List<FinancialDocumentItemCurrency> findByFinancialDocumentItemCurrencyIdList(List<Long> documentItemIdList);

    @Query(value = " SELECT T.ID, " +
            "       FINANCIAL_DOCUMENT_ITEM_ID, " +
            "       FOREIGN_DEBIT_AMOUNT, " +
            "       FOREIGN_CREDIT_AMOUNT, " +
            "       EXCHANGE_RATE, " +
            "       MONEY_TYPE_ID, " +
            "       M.DESCRIPTION       as       MONEY_TYPE_DESCRIPTION, " +
            "       MONEY_PRICING_REFRENCE_ID," +
            "       P.DESCRIPTION       as       MONEY_PRICING_REFRENCE_DESCRIPTION " +
            "  FROM fndc.financial_document_item_currency T " +
            " INNER JOIN FNCR.MONEY_TYPE M " +
            "    ON M.ID = T.MONEY_TYPE_ID " +
            " INNER JOIN FNCR.MONEY_PRICING_REFRENCE P " +
            "    ON P.ID = T.MONEY_PRICING_REFRENCE_ID " +
            " where T.FINANCIAL_DOCUMENT_ITEM_ID =:documentItemId ", nativeQuery = true)
    List<Object[]> findByFinancialDocumentItemCurrency(Long documentItemId);

    Optional<FinancialDocumentItemCurrency> findByFinancialDocumentItemId(Long financialDocumentItemId);


}
