package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerType;
import org.hibernate.jpa.TypedParameterValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialLedgerTypeRepository extends JpaRepository<FinancialLedgerType, Long> {

    @Query(value = " SELECT LGT.id," +
            "       LGT.CODE," +
            "       LGT.DESCRIPTION," +
            "       CASE" +
            "         WHEN FNSC.SEC_RESULT = 1 THEN" +
            "          0" +
            "         ELSE" +
            "          1" +
            "       END DISABLED" +
            "  FROM fndc.FINANCIAL_LEDGER_TYPE LGT," +
            "       TABLE(FNSC.PKG_FINANCIAL_SECURITY.GET_PERMISSION(" +
            " :organizationId,:activityCode,:financialPeriodId,:financialDocumentTypeId," +
            " :creatorUserId,:financialDepartmentId,LGT.ID,:departmentId, :userId)) FNSC" +
            " WHERE " +
             " LGT.ORGANIZATION_ID = :organizationId ",nativeQuery = true)
    List<Object[]> findFinancialLedgerTypeByOrganizationId(Long organizationId,
                                                                      String activityCode,
                                                                      TypedParameterValue financialPeriodId,
                                                                      TypedParameterValue financialDocumentTypeId
                                                                      ,TypedParameterValue creatorUserId,
                                                                      TypedParameterValue financialDepartmentId,
                                                                      Long departmentId,
                                                                      Long userId);

    @Query(value = "select" +
            "       fnlt.id," +
            "       fnlt.description," +
            "       fnlt.code," +
            "       fnlt.financial_coding_type_id," +
            "       fnlt.active_flag," +
            "       fnct.description as financial_coding_type_Description," +
            "       LISTAGG( fnnt.description,',' )  as numbering_description" +
            "  from fndc.financial_ledger_type fnlt" +
            " inner join fnac.financial_coding_type fnct" +
            "    on fnlt.financial_coding_type_id = fnct.id" +
            "    left outer join fndc.ledger_numbering_type lgnt on lgnt.financial_ledger_type_id=fnlt.id" +
            "    and lgnt.deleted_date is null" +
            "    left outer join fndc.financial_numbering_type fnnt on fnnt.id=lgnt.financial_numbering_type_id" +
            "     where fnlt.deleted_date is null" +
            "    and  fnlt.organization_id = :organizationId" +
            "    and  ( :financialCodingType is null or fnlt.financial_coding_type_id = :financialCodingTypeId)" +
            "    and  ( :financialLedgerType is null or fnlt.id = :financialLedgerTypeId )" +
            "    group by   fnlt.id," +
            "       fnlt.description," +
            "       fnlt.code," +
            "       fnlt.financial_coding_type_id," +
            "       fnlt.active_flag," +
            "       fnct.description"
            , nativeQuery = true)
    List<Object[]> financialLedgerTypeList(Long organizationId, Long financialCodingTypeId, String financialCodingType, Long financialLedgerTypeId, String financialLedgerType);


    @Query(value = " SELECT to_char(nvl(max(t.code), 10) + 1) from" +
            " fndc.financial_ledger_type t where t.organization_id =:organizationId" +
            " and t.deleted_date is null", nativeQuery = true)
    String findFinancialLedgerTypeCodeByOrganizationId(Long organizationId);


}
