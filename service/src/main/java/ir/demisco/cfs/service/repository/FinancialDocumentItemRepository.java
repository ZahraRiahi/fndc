package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialAccountStructure;
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
            "  cnac.code|| '-' || cnac.name as centricAccountDescription," +
            "  fidc.id  as FinancialDocumenyId " +
            "  from fndc.financial_document fidc " +
            " inner join fndc.financial_document_item fndi " +
            "    on fidc.id = fndi.financial_document_id" +
            "   and fndi.deleted_date is null " +
            " inner join fnac.financial_account fiac " +
            "    on fiac.id = fndi.financial_account_id " +
            "   and fiac.deleted_date is null " +
            " inner join fnac.centric_account cnac " +
            "    on cnac.id = fndi.centric_account_id_1 " +
            "   and cnac.deleted_date is null " +
            " inner join fndc.financial_document_number fndn " +
            "    on fndn.financial_document_id = fidc.id " +
            "   and fndn.deleted_date is null " +
            " where fidc.document_date >= :startDate " +
            "   And fidc.document_date <= :endDate " +
            "   and fidc.deleted_date is null " +
            "   And fndn.financial_numbering_type_id = :financialNumberingTypeId " +
            "   And (:fromNumber is null or fidc.document_number >= :fromNumberId) " +
            "   And (:toNumber is null  or fidc.document_number <= :toNumberId) " +
            "   And fidc.financial_document_status_id in (:documentStatusId ) " +
            "   and (:description is null or fidc.description  like %:description%) " +
            "   and ((:fromAccount is null or fiac.code >= :fromAccountCode  ) " +
            "   and (:toAccount is null or fiac.code <= :toAccountCode )) " +
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
            ,countQuery = "  select count(fidc.id) "+
            "  from fndc.financial_document fidc " +
            " inner join fndc.financial_document_item fndi " +
            "    on fidc.id = fndi.financial_document_id" +
            "   and fndi.deleted_date is null " +
            " inner join fnac.financial_account fiac " +
            "    on fiac.id = fndi.financial_account_id " +
            "   and fiac.deleted_date is null " +
            " inner join fnac.centric_account cnac " +
            "    on cnac.id = fndi.centric_account_id_1 " +
            "   and cnac.deleted_date is null " +
            " inner join fndc.financial_document_number fndn " +
            "    on fndn.financial_document_id = fidc.id " +
            "   and fndn.deleted_date is null " +
            " where fidc.document_date >= :startDate " +
            "   And fidc.document_date <= :endDate " +
            "   and fidc.deleted_date is null " +
            "   And fndn.financial_numbering_type_id = :financialNumberingTypeId " +
            "   And (:fromNumber is null or fidc.document_number >= :fromNumberId) " +
            "   And (:toNumber is null  or fidc.document_number <= :toNumberId) " +
            "   And fidc.financial_document_status_id in (:documentStatusId ) " +
            "   and (:description is null or fidc.description  like %:description%) " +
            "   and ((:fromAccount is null or fiac.code >= :fromAccountCode  ) " +
            "   and (:toAccount is null or fiac.code <= :toAccountCode )) " +
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
            "       (fndi.credit_amount <= :toPriceAmount + (:toPriceAmount * nvl(:tolerance, 0)) / 100.0))))) "
            , nativeQuery = true)
    Page<Object[]> getFinancialDocumentItemList(LocalDateTime startDate, LocalDateTime endDate, Long financialNumberingTypeId, Object fromNumber
            , Long fromNumberId, Object toNumber, Long toNumberId, String description, Object fromAccount, Long fromAccountCode,
                                                Object toAccount, Long toAccountCode, Object centricAccount, Long centricAccountId, Object centricAccountType,
                                                Long centricAccountTypeId, Object user, Long userId, Object priceType, Long priceTypeId, Object fromPrice,
                                                Long fromPriceAmount, Object toPrice, Long toPriceAmount, Double tolerance, List<Long> documentStatusId,
                                                Pageable pageable);


    List<FinancialDocumentItem> findByFinancialDocumentIdAndDeletedDateIsNull(Long FinancialDocumentId);

    @Query("select fdi from FinancialDocumentItem fdi where fdi.id in (:documentItemIdList)  and fdi.deletedDate is null")
    List<FinancialDocumentItem> findByFinancialDocumentItemIdList(List<Long> documentItemIdList);


    @Query(value = " select 1 from fndc.financial_document_item fdi " +
            " where fdi.financial_document_id= :financialDocumentId" +
            " having sum(fdi.debit_amount)=sum(fdi.credit_amount)", nativeQuery = true)
    Long getCostDocument(Long financialDocumentId);

    @Query(" select 1 from FinancialDocumentItem fdi " +
            " join FinancialAccount  fa on fa.id=fdi.financialAccount.id " +
            " join FinancialAccountStructure fs on fs.id=fa.financialAccountStructure.id " +
            " where fdi.financialDocument.id= :FinancialDocumentId " +
            " and not exists (select 1 from FinancialAccount fa2 where fa2.financialAccountParent.id=fa.id and fa2.deletedDate is null) " +
            " and fs.flagShowInAcc=1 " +
            " and fa.disableDate is null"

    )
    Long getFinancialAccount(Long FinancialDocumentId);

    @Query(" select 1 from FinancialDocumentItem fdi" +
            " join FinancialAccount  fa on fa.id=fdi.financialAccount.id  and fa.exchangeFlag=1 " +
            " join FinancialDocumentItemCurrency dc on dc.financialDocumentItem.id=fdi.id " +
            " where fdi.id=:FinancialDocumentItemId " +
            " and ((dc.foreignDebitAmount is null and dc.foreignCreditAmount is null) " +
            "      or dc.exchangeRate is null or dc.moneyPricingReference is null or dc.moneyType is null)")
    Long getInfoCurrency(Long FinancialDocumentItemId);

    @Query("select 1 from FinancialDocumentItem fdi " +
            " join FinancialAccount  fa on fa.id=fdi.financialAccount.id  and fa.exchangeFlag=1 " +
            " join FinancialDocumentItemCurrency dc on dc.financialDocumentItem.id=fdi.id " +
            " where fdi.id=:FinancialDocumentItemId " +
            "   and not exists (select 1 from AccountMoneyType mt where mt.financialAccount.id=fa.id " +
            "            and mt.moneyType.id=dc.moneyType.id)")
    Long equalCurrency(Long FinancialDocumentItemId);

    @Query(" select 1 from FinancialDocumentItem fdi  " +
            " join FinancialDocumentItemCurrency fdic on fdic.financialDocumentItem.id=fdi.id " +
            " where fdi.financialDocument.id=:FinancialDocumentId " +
            " group by fdi.creditAmount,fdi.debitAmount " +
            " having (nvl(fdi.debitAmount,0) != 0 and sum(nvl(fdic.foreignDebitAmount,0)) =0) " +
            " or (nvl(fdi.debitAmount,0) = 0 and sum(nvl(fdic.foreignDebitAmount,0)) != 0)" +
            " or (nvl(fdi.creditAmount,0) != 0 and sum(nvl(fdic.foreignCreditAmount,0)) = 0) " +
            " or (nvl(fdi.creditAmount,0) = 0 and sum(nvl(fdic.foreignCreditAmount,0)) != 0)" +
            " or (nvl(fdi.debitAmount,0) != 0 and nvl(fdi.creditAmount,0) != 0) "
    )
    Long costHarmony(Long FinancialDocumentId);

    @Query("select 1 from FinancialDocumentItem fdi " +
            " left join CentricAccount cn on cn.id=fdi.centricAccountId1.id " +
            " left join CentricAccount cn2 on cn2.id=fdi.centricAccountId2.id " +
            " left join CentricAccount cn3 on cn3.id=fdi.centricAccountId3.id " +
            " left join CentricAccount cn4 on cn4.id=fdi.centricAccountId4.id " +
            " left join CentricAccount cn5 on cn5.id=fdi.centricAccountId5.id " +
            " left join CentricAccount cn6 on cn6.id=fdi.centricAccountId6.id " +
            " where fdi.financialDocument.id =:FinancialDocumentId " +
            " and ((fdi.centricAccountId1 is not null and fdi.centricAccountId2 is not null and fdi.centricAccountId1 <> nvl (cn2.parentCentricAccount,0)) or " +
            "      (fdi.centricAccountId2 is not null and fdi.centricAccountId3 is not null and fdi.centricAccountId2<> nvl (cn3.parentCentricAccount,0))  or " +
            "      (fdi.centricAccountId3 is not null and fdi.centricAccountId4 is not null and fdi.centricAccountId3<> nvl (cn4.parentCentricAccount,0))  or " +
            "      (fdi.centricAccountId4 is not null and fdi.centricAccountId5 is not null and fdi.centricAccountId4<> nvl (cn5.parentCentricAccount,0))  or " +
            "      (fdi.centricAccountId5 is not null and fdi.centricAccountId6 is not null and fdi.centricAccountId5<> nvl (cn6.parentCentricAccount,0))) "
    )
    Long referenceCode(Long FinancialDocumentId);


    @Query(value = "  select sum(t.debit_amount) as debit_amount, " +
            "         sum(t.credit_amount) as credit_amount, " +
            "         max(fiac.full_description)  as fullDescription " +
            "   from fndc.Financial_Document_Item t  " +
            "        inner join fnac.financial_account fiac " +
            "            on fiac.id =t.financial_account_id   " +
            "            and fiac.deleted_date is null  " +
            "    where t.financial_document_id =:FinancialDocumentId ", nativeQuery = true)
    List<Object[]> findParamByDocumentId(Long FinancialDocumentId);


    @Query("select fdi from FinancialDocumentItem fdi where fdi.id in (:documentItemIdList)  and fdi.financialAccount.id=:accountId " +
            " and fdi.deletedDate is null")
    List<FinancialDocumentItem> getItemByDocumentItemIdListAndAccountId(List<Long> documentItemIdList, Long accountId);


    @Query("select fdi from FinancialDocumentItem fdi " +
            " where fdi.id in (:documentItemIdList) " +
            " and fdi.financialAccount.id=:financialAccountId" +
            " and fdi.deletedDate is null" +
            " and nvl(fdi.centricAccountId1.id,0) <>:newCentricAccountId and nvl(fdi.centricAccountId2.id,0) <>:newCentricAccountId " +
            " and nvl(fdi.centricAccountId3.id,0) <>:newCentricAccountId and nvl(fdi.centricAccountId4.id,0) <>:newCentricAccountId " +
            " and nvl(fdi.centricAccountId5.id,0) <>:newCentricAccountId and nvl(fdi.centricAccountId6.id,0) <>:newCentricAccountId ")
    List<FinancialDocumentItem> getByDocumentIdAndCentricAccount(List<Long> documentItemIdList, Long newCentricAccountId, Long financialAccountId);

    @Query("select fdi from FinancialDocumentItem fdi" +
            "   where fdi.id in(:financialDocumentItemIdList)" +
            "   And fdi.deletedDate is null " +
            "   And fdi.description like  %:oldDescription% ")
    List<FinancialDocumentItem> getDocumentDescription(List<Long> financialDocumentItemIdList, String oldDescription);

    @Query("select fdi.id from FinancialDocumentItem fdi where fdi.financialDocument.id=:financialDocumentId and fdi.deletedDate is null")
    List<Long> findByFinancialDocumentIdByDocumentId(Long financialDocumentId);

    @Query(value = " SELECT SUM(DI.DEBIT_AMOUNT) SUM_DEBIT, " +
            "       SUM(DI.CREDIT_AMOUNT) SUM_CREDIT, " +
            "       FA.CODE FINANCIAL_ACCOUNTCODE,    " +
            "       FA.DESCRIPTION FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "       FA.ID FINANCIAL_ACCOUNT_ID " +
            "  FROM FNDC.FINANCIAL_DOCUMENT_ITEM DI " +
            " INNER JOIN FNAC.FINANCIAL_ACCOUNT FA " +
            "    ON FA.ID = DI.FINANCIAL_ACCOUNT_ID " +
            "   AND FA.DELETED_DATE IS NULL " +
            " WHERE DI.FINANCIAL_DOCUMENT_ID = :financialDocumentId" +
            "   AND EXISTS " +
            " (SELECT 1 " +
            "          FROM FNAC.ACCOUNT_STRUCTURE_LEVEL SL_OUTER " +
            "         WHERE SL_OUTER.FINANCIAL_ACCOUNT_ID = DI.FINANCIAL_ACCOUNT_ID " +
            "           AND SL_OUTER.DELETED_DATE IS NULL " +
            "           AND SL_OUTER.STRUCTURE_LEVEL > = " +
            "               (SELECT DISTINCT SL.STRUCTURE_LEVEL " +
            "                  FROM FNAC.ACCOUNT_STRUCTURE_LEVEL SL " +
            "                 WHERE SL.FINANCIAL_STRUCTURE_ID = :financialStructureId " +
            "                   AND SL.FINANCIAL_ACCOUNT_ID = DI.FINANCIAL_ACCOUNT_ID " +
            "                   AND SL.DELETED_DATE IS NULL)) " +
            "   AND DI.DELETED_DATE IS NULL " +
            "  GROUP BY FA.ID,FA.CODE, FA.DESCRIPTION", nativeQuery = true)
    Page<Object[]> getDocumentByStructure(Long financialDocumentId, Long financialStructureId, Pageable pageable);

    @Query(" select fdi from  FinancialDocumentItem fdi " +
            "where fdi.financialDocument.id=:financialDocumentId " +
            "and fdi.sequenceNumber=:sequenceNumber " +
            "and fdi.deletedDate is null")
    FinancialDocumentItem findBySequence(Long financialDocumentId, Long sequenceNumber);

    @Query(" select DISTINCT fs_final.id as documentStructueId," +
            "       fs_final.sequence as sequence, " +
            "       fs_final.description as description, " +
            "       fs_final.financialCodingType.id" +
            " from FinancialDocumentItem fd " +
            " join FinancialAccount  fa  on fa.id=fd.financialAccount.id and fa.deletedDate is null " +
            " join FinancialAccountStructure fs on fs.id=fa.financialAccountStructure.id and fs.deletedDate is null " +
            " join FinancialAccountStructure fs_final on fs_final.financialCodingType.id=fs.financialCodingType.id and fs_final.deletedDate is null" +
            " where fd.financialDocument.id=:financialDocumentId" +
            " and fd.deletedDate is null" +
            " order by fs_final.sequence ")
    List<Object[]> getDocumentStructurList(Long financialDocumentId);
}
