package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentItemCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinancialDocumentItemCurrencyRepository  extends JpaRepository<FinancialDocumentItemCurrency,Long> {

    @Query(" select dic from FinancialDocumentItemCurrency dic where dic.financialDocumentItem.id=:documentItemId ")
    FinancialDocumentItemCurrency getByDocumentItemId(Long documentItemId);
}
