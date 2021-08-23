package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface FinancialDocumentItemRepository extends JpaRepository<FinancialDocumentItem, Long> {

    @Query(value = "  select fndi.id, " +
            "  fidc.document_date, " +
            "  fndi.description, " +
            "  fidc.document_number, " +
            "  fndi.sequence_number, " +
            "  fiac.description  as financialAccountDescription, " +
            "  fndi.financial_account_id, " +
            "  fndi.debit_amount, " +
            "  fndi.credit_amount, " +
            "  fndi.description  || '-' ||  fiac.full_description as full_description, " +
            "  cnac.code|| '-' || cnac.name as centricAccountDescription " +
            "  from fndc.financial_document fidc " +
            " inner join fndc.financial_document_item fndi " +
            "    on fidc.id = fndi.financial_document_id" +
            "   and fndi.deleted_date is null " +
            " inner join fnac.financial_account fiac " +
            "    on fiac.id = fndi.financial_account_id " +
            "   and fiac.deleted_date is null " +
            "  left outer join fndc.financial_document_number fndn " +
            "    on fndn.financial_document_id = fidc.id " +
            "   and fndn.deleted_date is null " +
            " inner join fnac.centric_account cnac " +
            "    on cnac.id = fndi.centric_account_id_1 " +
            "   and cnac.deleted_date is null " +
            " inner join fnac.financial_account fc " +
            "    on fc.id = fndi.financial_account_id  " +
            "   and fc.deleted_date is null " +
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
            "  order by  fndi.sequence_number "
            , nativeQuery = true)
    Page<Object[]> getFinancialDocumentItemList(LocalDateTime startDate, LocalDateTime endDate, Long financialNumberingTypeId, Object fromNumber
            , Long fromNumberId, Object toNumber, Long toNumberId, String description, Object fromAccount, String fromAccountCode,
                                                Object toAccount, String toAccountCode, Object centricAccount, Long centricAccountId, Object centricAccountType,
                                                Long centricAccountTypeId, Object user, Long userId, Object priceType, Long priceTypeId, Object fromPrice,
                                                Long fromPriceAmount, Object toPrice, Long toPriceAmount, Double tolerance, List<Long> documentStatusId,
                                                Pageable pageable);


    @Query("select fd from FinancialDocumentItem fd where fd.financialDocument.id=:FinancialDocumentId ")
    List<FinancialDocumentItem> getDocumentItem(Long FinancialDocumentId);

    @Query(value = " select sum(t.debit_amount) as debit_amount,sum(t.credit_amount) as credit_amount " +
            " from fndc.Financial_Document_Item t " +
            " where t.financial_document_id = :FinancialDocumentId " +
            " having(sum(t.debit_amount)=sum(t.credit_amount))", nativeQuery = true)
    List<Object[]> getCostDocument(Long FinancialDocumentId);

    @Query("select fdi from FinancialDocumentItem fdi " +
            " join FinancialDocumentItemCurrency fdic on fdic.financialDocumentItem.id=fdi.id " +
            "  where fdi.financialDocument.id=:FinancialDocumentId " +
            "  and fdic.moneyType.id=1")
    FinancialDocumentItem getDocumentMoneyType(Long FinancialDocumentId);

    @Query(value = " select fd from FinancialDocumentItem fd " +
            " join FinancialDocumentRefrence fr on fr.financialDocumentItem.id=fd.id " +
            " where fd.financialDocument.id=:FinancialDocumentId " +
            " and ((fr.referenceDate is null) or (fr.referenceDescription is null))", nativeQuery = true)
    List<FinancialDocumentItem> getDocumentRefernce(Long FinancialDocumentId);

    @Query(value = "  select sum(t.debit_amount) as debit_amount, " +
                   "         sum(t.credit_amount) as credit_amount, " +
                   "         max(fiac.full_description)  as fullDescription " +
                   "   from fndc.Financial_Document_Item t  " +
                   "        inner join fnac.financial_account fiac " +
                   "            on fiac.id =t.financial_account_id   " +
                   "            and fiac.deleted_date is null  " +
                   "    where t.financial_document_id =:FinancialDocumentId ", nativeQuery = true)
    Object[] getParamByDocumentId(Long FinancialDocumentId);



    @Query("select fdi from FinancialDocumentItem fdi where fdi.financialDocument.id=:documentId  and fdi.financialAccount.id=:accountId ")
    List<FinancialDocumentItem> getItemByDocumentIdAndAccountId(Long documentId,Long accountId);

    @Query("select fdi from FinancialDocumentItem fdi where fdi.financialDocument.id=:documentId and fdi.financialAccount.id=:newAccountId")
    List<FinancialDocumentItem> getByNewAccount(Long documentId,Long newAccountId);

    @Query("select fdi from FinancialDocumentItem fdi " +
            " where fdi.financialDocument.id=:documentId and fdi.financialAccount.id=:accountId " +
            " and fdi.centricAccountId1.id <>:newCentricAccountId and fdi.centricAccountId2.id <>:newCentricAccountId and fdi.centricAccountId3.id <>:newCentricAccountId " +
            " and fdi.centricAccountId4.id <>:newCentricAccountId and fdi.centricAccountId5.id <>:newCentricAccountId and fdi.centricAccountId6.id <>:newCentricAccountId")
    List<FinancialDocumentItem> getByDocumentIdAndCentricAccount(Long documentId,Long accountId,Long newCentricAccountId);
}
