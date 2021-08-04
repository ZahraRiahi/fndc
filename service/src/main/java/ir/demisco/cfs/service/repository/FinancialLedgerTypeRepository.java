package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialLedgerTypeRepository extends JpaRepository<FinancialLedgerType, Long> {

    @Query(value = " select flt" +
            " from FinancialLedgerType flt " +
            " where flt.deletedDate is null " +
            " and flt.organization.id =:organizationId ")
    List<FinancialLedgerType> findFinancialLedgerTypeByOrganizationId(Long organizationId);

    @Query(value = "select" +
            "       fnlt.id," +
            "       fnlt.description," +
            "       fnlt.financial_coding_type_id," +
            "       fnlt.active_flag," +
            "       fnct.description as financial_coding_type_Description," +
            "       LISTAGG( fnnt.description,',' )  as numbering_description" +
            "  from fndc.financial_ledger_type fnlt" +
            " inner join fnac.financial_coding_type fnct" +
            "    on fnlt.financial_coding_type_id = fnct.id" +
            " left outer join fndc.ledger_numbering_type lgnt on lgnt.financial_ledger_type_id=fnlt.id" +
            " left outer join fndc.financial_numbering_type fnnt on fnnt.id=lgnt.financial_numbering_type_id" +
            "  where fnlt.deleted_date is null" +
            "   and  fnlt.organization_id = :organizationId" +
            "   and  (fnlt.financial_coding_type_id = :financialCodingTypeId or" +
            "        :financialCodingType is null)" +
            "   and  (:financialLedgerType is null or fnlt.id = :financialLedgerTypeId)" +
            "   group by fnlt.id," +
            "       fnlt.description," +
            "       fnlt.financial_coding_type_id," +
            "       fnlt.active_flag," +
            "       fnct.description"
            , nativeQuery = true)
    Page<Object[]> financialLedgerTypeList(Long organizationId, Long financialCodingTypeId, String financialCodingType, Long financialLedgerTypeId, String financialLedgerType, Pageable pageable);

}
