package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.LedgerNumberingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LedgerNumberingTypeRepository extends JpaRepository<LedgerNumberingType, Long> {

    @Query(value = "select fnnt.id," +
            "       fnnt.description," +
            "       case when :financialLedgerTypeId is null or not exists" +
            "          (select 1 from fndc.ledger_numbering_type lgnt" +
            "                where lgnt.financial_numbering_type_id = fnnt.id" +
            "                  and lgnt.financial_ledger_type_id = :financialLedgerTypeId" +
            "                  and lgnt.deleted_date is null) then 0 else 1 end flg_exists" +
            "  from fndc.financial_numbering_type fnnt" +
            "  where fnnt.deleted_date is null", nativeQuery = true)
    List<Object[]> getLedgerNumberingType(Long financialLedgerTypeId, String financialLedgerType);

    @Query("select coalesce(COUNT(lnt.id),0) from LedgerNumberingType lnt where lnt.financialLedgerType.id=:financialLedgerTypeId" +
            " and lnt.financialNumberingType.id =:financialNumberingTypeId " +
            " and lnt.deletedDate is null")
    Long getCountByLedgerTypeIdAndNumberingTypeIdAndDeleteDate(Long financialLedgerTypeId, Long financialNumberingTypeId);

    @Query("select lgnt.id from LedgerNumberingType lgnt where lgnt.financialLedgerType.id=:financialLedgerTypeId" +
            " and lgnt.deletedDate is null")
    List<Long> getLegerNumberingTypeByFinancialLedgerTypeId(Long financialLedgerTypeId);


    @Query("select 1 from LedgerNumberingType lnt where lnt.financialLedgerType.id=:financialLedgerTypeId " +
            "  and lnt.financialNumberingType.id=2 ")
    Long ledgerNumberingTypeByLedgerTypeId(Long financialLedgerTypeId);
}
