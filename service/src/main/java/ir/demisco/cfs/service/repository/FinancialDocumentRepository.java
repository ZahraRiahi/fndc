package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FinancialDocumentRepository extends JpaRepository<FinancialDocument, Long> {

    @Query(value = " select fidc.id , " +
                   "      fidc.document_date , " +
                   "        fidc.description, " +
                   "        fidc.document_number, " +
                   "        fidc.financial_document_type_id ," +
                   "        fndt.description as financial_document_type_Description, " +
                   "        fidc.description as full_description, " +
                   "        sum(fndi.debit_amount) as sum_debit_amount, " +
                   "        sum(fndi.credit_amount) as sum_cdebit_amount, " +
                   "        usr.id as userId, " +
                   "        usr.nick_name as userName " +
                   "  from fndc.financial_document fidc " +
                   "  inner join fndc.financial_document_type fndt " +
                   "    on fidc.financial_document_type_id = fndt.id " +
                   "   and fndt.deleted_date is null " +
                   " inner join fndc.financial_document_item fndi " +
                   "    on fidc.id = fndi.financial_document_id" +
                   "   and fndi.deleted_date is null " +
                   "  left outer join fndc.financial_document_number fndn " +
                   "    on fndn.financial_document_id = fidc.id " +
                   "   and fndn.deleted_date is null " +
                   "  inner join fnac.financial_account fc " +
                   "    on fc.id = fndi.financial_account_id " +
                   "   and fc.deleted_date is null " +
                   "  left outer join sec.application_user usr" +
                   "    on usr.id = fidc.creator_id " +
                   " where fidc.document_date >= :startDate " +
                   "   And fidc.document_date <= :endDate " +
                   "   and fidc.deleted_date is null " +
                   "   And fndn.financial_numbering_type_id = :financialNumberingTypeId " +
                   "   And (:fromNumber is null or fidc.document_number >= :fromNumberId) " +
                   "   And (:toNumber is null  or fidc.document_number <= :toNumberId) " +
                   "   And fidc.financial_document_status_id in (:documentStatusId ) " +
                   "   and (:description is null or fidc.description  like %:description%) " +
                   "   and ((:fromAccount is null or fc.code >= :fromAccountCode  ) " +
                   "   and (:toAccount is null or fc.code <= :toAccountCode )) " +
                   "   and (:centricAccount is null or " +
                   "       (fndi.centric_account_id_1 = :centricAccountId or " +
                   "        fndi.centric_account_id_2 = :centricAccountId  or " +
                   "        fndi.centric_account_id_3 = :centricAccountId  or " +
                   "        fndi.centric_account_id_4 = :centricAccountId  or " +
                   "        fndi.centric_account_id_5 = :centricAccountId  or " +
                   "        fndi.centric_account_id_6 = :centricAccountId ))  " +
                   "   and (:centricAccountType is null or " +
                   "       :centricAccountTypeId in " +
                   "       (select cnt.centric_account_type_id " +
                   "           from fnac.centric_account cnt " +
                   "          where fndi.centric_account_id_1 = cnt.id " +
                   "             or fndi.centric_account_id_2 = cnt.id " +
                   "             or fndi.centric_account_id_3 = cnt.id " +
                   "             or fndi.centric_account_id_4 = cnt.id " +
                   "             or fndi.centric_account_id_5 = cnt.id " +
                   "             or fndi.centric_account_id_6 = cnt.id)) " +
                   "   and (:user is null or (fidc.creator_id = :userId or " +
                   "       fidc.last_modifier_id = :userId)) " +
                   "   and ((:priceType is null or " +
                   "       (:priceTypeId = 1  " +
                   "   and (:fromPrice is null or " +
                   "       (fndi.debit_amount >= :fromPriceAmount - (:fromPriceAmount * nvl(:tolerance, 0)) / 100.0)) " +
                   "   and (:toPrice is null or " +
                   "       (fndi.debit_amount <= :toPriceAmount + (:toPriceAmount * nvl(:tolerance, 0)) / 100.0)))) or " +
                   "       (:priceType is null or (:priceTypeId = 2  " +
                   "   and  (:fromPrice is null or " +
                   "       (fndi.credit_amount >= :fromPriceAmount - (:fromPriceAmount * nvl(:tolerance, 0)) / 100.0))  " +
                   "   and   (:toPrice is null  or " +
                   "       (fndi.credit_amount <= :toPriceAmount + (:toPriceAmount * nvl(:tolerance, 0)) / 100.0))))) " +
                   "  group by fidc.id,usr.id,usr.nick_name,document_date,fidc.description,fidc.document_number,financial_document_type_id,fndt.description "
            , nativeQuery = true)
    Page<Object[]> getFinancialDocumentList(LocalDateTime startDate, LocalDateTime endDate, Long financialNumberingTypeId,Object fromNumber,Long fromNumberId
                                            ,Object toNumber,Long toNumberId,String description,Object fromAccount,String fromAccountCode, Object toAccount,
                                            String toAccountCode, Object centricAccount,Long centricAccountId,
                                            Object centricAccountType,Long centricAccountTypeId, Object user,Long userId,
                                            Object priceType,Long priceTypeId,Object fromPrice,Long fromPriceAmount,Object toPrice,Long toPriceAmount,
                                            Double tolerance,List<Long> documentStatusId,Pageable pageable);

//    @Query("select fd from FinancialDocument fd join fd.financialPeriod   fp where fp.financialPeriodStatus.id=1 and fd.id=:FinancialDocumentId")
//    FinancialDocument getActivePeriodInDocument(Long FinancialDocumentId);

    @Query("select to_char(:date, 'yyyymmdd', 'NLS_CALENDAR=persian') ||" +
            "      nvl(lpad(max(to_number(substr(to_char(fd.documentNumber), 9, 3)) + 1),3,'0'),'001')" +
            " from FinancialDocument fd" +
            " where fd.organization.id=:organizationId" +
            "       and (fd.financialPeriod.id=:financialPeriodId)" +
            "       and  substr(to_char(fd.documentNumber),0,8)=to_char(:date, 'yyyymmdd', 'NLS_CALENDAR=persian')")
    Long getDocumentNumber(Long organizationId,LocalDateTime date,Long financialPeriodId);


    @Query("select coalesce(COUNT(fd.id),0) from FinancialDocument fd where fd.financialDepartment.id=:financialDepartmentId" +
            " and fd.financialLedgerType.id=:financialLedgerTypeId " +
            " and fd.deletedDate is null")
    Long getCountByLedgerTypeIdAndDepartmentIdAndDeleteDate(Long financialDepartmentId, Long financialLedgerTypeId);

    @Query("select fd from FinancialDocument fd where fd.id=:FinancialDocumentId and fd.deletedDate is null")
    FinancialDocument getActiveDocumentById(Long FinancialDocumentId);

    @Query(" select fd from FinancialDocument fd join fd.financialPeriod fp where fp.financialPeriodStatus.id=1 and fd.id=:FinancialDocumentId " +
            " and exists ( select fm from FinancialMonth fm " +
            "              join FinancialMonthType fmt on fmt.id=fm.financialMonthType.id " +
            "              join FinancialPeriodType fpt on fpt.id=fmt.financialPeriodType.id " +
            "              join FinancialPeriodTypeAssign fpts on fpts.financialPeriodType.id=fpt.id " +
            "              join FinancialPeriod fp on fp.financialPeriodTypeAssign.id=fpts.id " +
            "              join FinancialDocument fd on fd.financialPeriod.id=fp.id " +
            "            where fd.id=:FinancialDocumentId " +
            "                  and fm.financialMonthStatus.id=1" +
            "                  and case fpt.calendarTypeId when 2 then extract(month from TO_DATE(TO_char(fd.documentDate,'mm/dd/yyyy'),'mm/dd/yyyy'))" +
            "                                              when 1 then TO_NUMBER(substr(TO_CHAR(TO_DATE(TO_char(fd.documentDate,'mm/dd/yyyy'),'mm/dd/yyyy'),'yyyy/mm/dd','NLS_CALENDAR=persian'),6,2)) " +
            "                       end = case when fpt.calendarYearFlag = 1 then (fpt.fromMonth + (fmt.monthNumber-1)) "+
            "                       else  " +
            "                       case when (fpt.fromMonth + (fmt.monthNumber-1)) > 12 then (fpt.fromMonth + (fmt.monthNumber-13)) else (fpt.fromMonth+(fmt.monthNumber-1)) end" +
            "                       end" +
            "                     )")
    FinancialDocument getActivePeriodAndMontInDocument(Long FinancialDocumentId);

    @Query("select fd from FinancialDocument fd where fd.documentNumber = :documentNumber and fd.deletedDate is null")
    FinancialDocument getFinancialDocumentByDocumentNumber(Long documentNumber);
}
