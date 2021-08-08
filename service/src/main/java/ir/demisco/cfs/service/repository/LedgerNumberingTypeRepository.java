package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialNumberingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LedgerNumberingTypeRepository extends JpaRepository<FinancialNumberingType, Long> {

    @Query(value = "select fnnt.id," +
            "       fnnt.description," +
            "       case when lgnt.id is null then 0 else 1 end flg_exists" +
            "  from   fndc.financial_numbering_type fnnt" +
            "  left outer join fndc.ledger_numbering_type lgnt" +
            "    on lgnt.financial_numbering_type_id = fnnt.id" +
            " where fnnt.deleted_date is null" +
            "   and lgnt.deleted_date is null" +
            "   and (:financialLedgerType is null or lgnt.financial_ledger_type_id = :financialLedgerTypeId)", nativeQuery = true)
    List<Object[]> getLedgerNumberingType(Long financialLedgerTypeId, String financialLedgerType);

}
