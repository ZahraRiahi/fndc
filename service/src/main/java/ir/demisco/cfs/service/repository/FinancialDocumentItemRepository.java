package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface FinancialDocumentItemRepository extends JpaRepository<FinancialDocumentItem, Long> {

    @Query(value = "  SELECT FNDI.ID, " +
            "       FIDC.DOCUMENT_DATE, " +
            "       FNDI.DESCRIPTION as financialDocumentItemDescription , " +
            "       FNDI.SEQUENCE_NUMBER, " +
            "       FIDC.DOCUMENT_NUMBER, " +
            "       FIAC.DESCRIPTION as financialAccountDescription , " +
            "       FNDI.FINANCIAL_ACCOUNT_ID, " +
            "       FNDI.DEBIT_AMOUNT, " +
            "       FNDI.CREDIT_AMOUNT, " +
            "       FNDI.DESCRIPTION || ' ' || FIAC.FULL_DESCRIPTION AS FULL_DESCRIPTION, " +
            " FIAC.Code as FINANCIAL_ACCOUNT_CODE," +
            "       FIDC.ID AS FINANCIAL_DOCUMENT_ID, " +
            "       NVL(CN1.CODE, '') || NVL(CN1.NAME, '') || " +
            "       NVL2(CN2.CODE, '-' || CN2.CODE, '') || NVL(CN2.NAME, '') || " +
            "       NVL2(CN3.CODE, '-' || CN3.CODE, '') || NVL(CN3.NAME, '') || " +
            "       NVL2(CN4.CODE, '-' || CN4.CODE, '') || NVL(CN4.NAME, '') || " +
            "       NVL2(CN5.CODE, '-' || CN5.CODE, '') || NVL(CN5.NAME, '') || " +
            "       NVL2(CN6.CODE, '-' || CN6.CODE, '') || NVL(CN6.NAME, '') as CENTRICACCOUNTDESCRIPTION   " +
            "  FROM fndc.FINANCIAL_DOCUMENT FIDC " +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FNDI " +
            "    ON FIDC.ID = FNDI.FINANCIAL_DOCUMENT_ID " +
            "   AND FNDI.DELETED_DATE IS NULL " +
            " INNER JOIN FNAC.FINANCIAL_ACCOUNT FIAC " +
            "    ON FIAC.ID = FNDI.FINANCIAL_ACCOUNT_ID " +
            "   AND FIAC.DELETED_DATE IS NULL " +
            " INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FNDN " +
            "    ON FIDC.ID = FNDN.FINANCIAL_DOCUMENT_ID " +
            "   AND FNDN.DELETED_DATE IS NULL" +
            " LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN1 " +
            "    ON CN1.ID = FNDI.CENTRIC_ACCOUNT_ID_1 " +
            "   AND CN1.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN2 " +
            "    ON CN2.ID = FNDI.CENTRIC_ACCOUNT_ID_2 " +
            "   AND CN2.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN3 " +
            "    ON CN3.ID = FNDI.CENTRIC_ACCOUNT_ID_3 " +
            "   AND CN3.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN4 " +
            "    ON CN4.ID = FNDI.CENTRIC_ACCOUNT_ID_4 " +
            "   AND CN4.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN5 " +
            "    ON CN5.ID = FNDI.CENTRIC_ACCOUNT_ID_5 " +
            "   AND CN5.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN6 " +
            "    ON CN6.ID = FNDI.CENTRIC_ACCOUNT_ID_6  " +
            "    AND CN6.DELETED_DATE IS NULL," +
            " TABLE(FNSC.PKG_FINANCIAL_SECURITY.GET_PERMISSION(:organizationId," +
            "                                                        :activityCode," +
            "                                                        FIDC.FINANCIAL_PERIOD_ID," +
            "                                                        FIDC.FINANCIAL_DOCUMENT_TYPE_ID," +
            "                                                       :creatorUserId," +
            "                                                        FIDC.FINANCIAL_DEPARTMENT_ID," +
            "                                                        FIDC.FINANCIAL_LEDGER_TYPE_ID," +
            "                                                        :departmentId," +
            "                                                        :userId)) FNSC " +
            " WHERE FIDC.DOCUMENT_DATE >= :startDate " +
            "   AND FIDC.DOCUMENT_DATE <= :endDate" +
            " AND FNDI.CREDIT_AMOUNT = CASE " +
            "         WHEN :priceTypeId = 1 THEN " +
            "          0 " +
            "         ELSE " +
            "          FNDI.CREDIT_AMOUNT " +
            "       END " +
            "   AND FNDI.DEBIT_AMOUNT = CASE " +
            "         WHEN :priceTypeId = 2 THEN " +
            "          0 " +
            "         ELSE " +
            "          FNDI.DEBIT_AMOUNT " +
            "       END  " +
            " AND FNDN.FINANCIAL_NUMBERING_TYPE_ID = :financialNumberingTypeId " +
            "   And (:fromNumber is null or FIDC.DOCUMENT_NUMBER >= :fromNumberId) " +
            "   And (:toNumber is null  or FIDC.DOCUMENT_NUMBER <= :toNumberId) " +
            " AND FIDC.FINANCIAL_DOCUMENT_STATUS_ID IN (:documentStatusId) " +
            "   and (:description is null or fidc.description  like %:description%) " +
            "   and ((:fromAccount is null or FIAC.CODE >= :fromAccountCode  ) " +
            "   and (:toAccount is null or FIAC.CODE <= :toAccountCode )) " +
            "   AND (:centricAccount IS NULL OR " +
            "       (FNDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId OR " +
            "       FNDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId OR " +
            "       FNDI.CENTRIC_ACCOUNT_ID_3 = :centricAccountId OR " +
            "       FNDI.CENTRIC_ACCOUNT_ID_4 = :centricAccountId OR " +
            "       FNDI.CENTRIC_ACCOUNT_ID_5 = :centricAccountId OR " +
            "       FNDI.CENTRIC_ACCOUNT_ID_6 = :centricAccountId)) " +
            "   AND FIDC.DELETED_DATE IS NULL " +
            " AND (:centricAccountType IS NULL OR " +
            "       :centricAccountTypeId IN " +
            "       (SELECT CNT.CENTRIC_ACCOUNT_TYPE_ID " +
            "           FROM FNAC.CENTRIC_ACCOUNT CNT " +
            "          WHERE FNDI.CENTRIC_ACCOUNT_ID_1 = CNT.ID " +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_2 = CNT.ID " +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_3 = CNT.ID " +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_4 = CNT.ID " +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_5 = CNT.ID " +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_6 = CNT.ID))" +
            "   and (:documentUser is null or (FIDC.CREATOR_ID= :documentUserId or " +
            "       FIDC.LAST_MODIFIER_ID = :documentUserId))" +
            "    AND (:priceType IS NULL OR " +
            "       (:priceTypeId = 1 AND " +
            "       (:fromPrice IS NULL OR " +
            "       (FNDI.DEBIT_AMOUNT >= " +
            "       :fromPriceAmount - (:fromPriceAmount * NVL(:tolerance, 0)) / 100.0)) AND " +
            "       (:toPrice IS NULL OR " +
            "       (FNDI.DEBIT_AMOUNT <= " +
            "       :toPriceAmount + ((:toPriceAmount * NVL(:tolerance, 0)) / 100.0)))) OR " +
            "       (:priceTypeId = 2 AND " +
            "       (:fromPrice IS NULL OR " +
            "       (FNDI.CREDIT_AMOUNT >= " +
            "       :fromPriceAmount - (:fromPriceAmount * NVL(:tolerance, 0)) / 100.0)) AND " +
            "       (:toPrice IS NULL OR " +
            "       (FNDI.CREDIT_AMOUNT <= " +
            "       :toPriceAmount + ((:toPriceAmount * NVL(:tolerance, 0)) / 100.0)))))  " +
            " and (:financialDocumentType is null or FIDC.FINANCIAL_DOCUMENT_TYPE_ID =:financialDocumentTypeId ) "
            , countQuery = " select count(FIDC.id)   " +
            "  FROM fndc.FINANCIAL_DOCUMENT FIDC " +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FNDI " +
            "    ON FIDC.ID = FNDI.FINANCIAL_DOCUMENT_ID " +
            "   AND FNDI.DELETED_DATE IS NULL " +
            " INNER JOIN FNAC.FINANCIAL_ACCOUNT FIAC " +
            "    ON FIAC.ID = FNDI.FINANCIAL_ACCOUNT_ID " +
            "   AND FIAC.DELETED_DATE IS NULL " +
            " INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FNDN " +
            "    ON FIDC.ID = FNDN.FINANCIAL_DOCUMENT_ID " +
            "   AND FNDN.DELETED_DATE IS NULL" +
            " LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN1 " +
            "    ON CN1.ID = FNDI.CENTRIC_ACCOUNT_ID_1 " +
            "   AND CN1.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN2 " +
            "    ON CN2.ID = FNDI.CENTRIC_ACCOUNT_ID_2 " +
            "   AND CN2.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN3 " +
            "    ON CN3.ID = FNDI.CENTRIC_ACCOUNT_ID_3 " +
            "   AND CN3.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN4 " +
            "    ON CN4.ID = FNDI.CENTRIC_ACCOUNT_ID_4 " +
            "   AND CN4.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN5 " +
            "    ON CN5.ID = FNDI.CENTRIC_ACCOUNT_ID_5 " +
            "   AND CN5.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN6 " +
            "    ON CN6.ID = FNDI.CENTRIC_ACCOUNT_ID_6  " +
            "    AND CN6.DELETED_DATE IS NULL, " +
            " TABLE(FNSC.PKG_FINANCIAL_SECURITY.GET_PERMISSION(:organizationId," +
            "                                                        :activityCode," +
            "                                                        FIDC.FINANCIAL_PERIOD_ID," +
            "                                                        FIDC.FINANCIAL_DOCUMENT_TYPE_ID," +
            "                                                       :creatorUserId," +
            "                                                        FIDC.FINANCIAL_DEPARTMENT_ID," +
            "                                                        FIDC.FINANCIAL_LEDGER_TYPE_ID," +
            "                                                        :departmentId," +
            "                                                        :userId)) FNSC " +
            " WHERE FIDC.DOCUMENT_DATE >= :startDate " +
            "   AND FIDC.DOCUMENT_DATE <= :endDate" +
            " AND FNDI.CREDIT_AMOUNT = CASE " +
            "         WHEN :priceTypeId = 1 THEN " +
            "          1 " +
            "         ELSE " +
            "          FNDI.CREDIT_AMOUNT " +
            "       END " +
            "   AND FNDI.DEBIT_AMOUNT = CASE " +
            "         WHEN :priceTypeId = 2 THEN " +
            "          0 " +
            "         ELSE " +
            "          FNDI.DEBIT_AMOUNT " +
            "       END  " +
            " AND FNDN.FINANCIAL_NUMBERING_TYPE_ID = :financialNumberingTypeId " +
            "   And (:fromNumber is null or FIDC.DOCUMENT_NUMBER >= :fromNumberId) " +
            "   And (:toNumber is null  or FIDC.DOCUMENT_NUMBER <= :toNumberId) " +
            " AND FIDC.FINANCIAL_DOCUMENT_STATUS_ID IN (:documentStatusId) " +
            "   and (:description is null or fidc.description  like %:description%) " +
            "   and ((:fromAccount is null or FIAC.CODE >= :fromAccountCode  ) " +
            "   and (:toAccount is null or FIAC.CODE <= :toAccountCode )) " +
            "   AND (:centricAccount IS NULL OR " +
            "       (FNDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId OR " +
            "       FNDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId OR " +
            "       FNDI.CENTRIC_ACCOUNT_ID_3 = :centricAccountId OR " +
            "       FNDI.CENTRIC_ACCOUNT_ID_4 = :centricAccountId OR " +
            "       FNDI.CENTRIC_ACCOUNT_ID_5 = :centricAccountId OR " +
            "       FNDI.CENTRIC_ACCOUNT_ID_6 = :centricAccountId)) " +
            "   AND FIDC.DELETED_DATE IS NULL " +
            " AND (:centricAccountType IS NULL OR " +
            "       :centricAccountTypeId IN " +
            "       (SELECT CNT.CENTRIC_ACCOUNT_TYPE_ID " +
            "           FROM FNAC.CENTRIC_ACCOUNT CNT " +
            "          WHERE FNDI.CENTRIC_ACCOUNT_ID_1 = CNT.ID " +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_2 = CNT.ID " +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_3 = CNT.ID " +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_4 = CNT.ID " +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_5 = CNT.ID " +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_6 = CNT.ID))" +
            "   and (:documentUser is null or (FIDC.CREATOR_ID= :documentUserId or " +
            "       FIDC.LAST_MODIFIER_ID = :documentUserId))" +
            "    AND (:priceType IS NULL OR " +
            "       (:priceTypeId = 1 AND " +
            "       (:fromPrice IS NULL OR " +
            "       (FNDI.DEBIT_AMOUNT >= " +
            "       :fromPriceAmount - (:fromPriceAmount * NVL(:tolerance, 0)) / 100.0)) AND " +
            "       (:toPrice IS NULL OR " +
            "       (FNDI.DEBIT_AMOUNT <= " +
            "       :toPriceAmount + ((:toPriceAmount * NVL(:tolerance, 0)) / 100.0)))) OR " +
            "       (:priceTypeId = 2 AND " +
            "       (:fromPrice IS NULL OR " +
            "       (FNDI.CREDIT_AMOUNT >= " +
            "       :fromPriceAmount - (:fromPriceAmount * NVL(:tolerance, 0)) / 100.0)) AND " +
            "       (:toPrice IS NULL OR " +
            "       (FNDI.CREDIT_AMOUNT <= " +
            "       :toPriceAmount + ((:toPriceAmount * NVL(:tolerance, 0)) / 100.0)))))  " +
            " and (:financialDocumentType is null or FIDC.FINANCIAL_DOCUMENT_TYPE_ID =:financialDocumentTypeId ) "
            , nativeQuery = true)
    List<Object[]> getFinancialDocumentItemList(Long organizationId, String activityCode, Long creatorUserId, Long departmentId, Long userId,LocalDateTime startDate, LocalDateTime endDate, Long priceTypeId, Long financialNumberingTypeId, Object fromNumber, Long fromNumberId, Object toNumber, Long toNumberId, List<Long> documentStatusId, String description, Object fromAccount, Long fromAccountCode, Object toAccount,
                                                Long toAccountCode, Object centricAccount, Long centricAccountId, Object centricAccountType, Long centricAccountTypeId, Object documentUser, Long documentUserId, Object priceType, Object fromPrice, Long fromPriceAmount, Object toPrice, Long toPriceAmount,
                                                Double tolerance, Object financialDocumentType, Long financialDocumentTypeId);


    List<FinancialDocumentItem> findByFinancialDocumentIdAndDeletedDateIsNull(Long financialDocumentId);

    @Query("select fdi from FinancialDocumentItem fdi where fdi.id in (:documentItemIdList)  and fdi.deletedDate is null")
    List<FinancialDocumentItem> findByFinancialDocumentItemIdList(List<Long> documentItemIdList);


    @Query(value = " select 1 from fndc.financial_document_item fdi " +
            " where fdi.financial_document_id= :financialDocumentId" +
            " having sum(fdi.debit_amount)=sum(fdi.credit_amount)", nativeQuery = true)
    Long getCostDocument(Long financialDocumentId);

    @Query(" select 1 from FinancialDocumentItem fdi " +
            " join FinancialAccount  fa on fa.id=fdi.financialAccount.id " +
            " join FinancialAccountStructure fs on fs.id=fa.financialAccountStructure.id " +
            " where fdi.financialDocument.id= :financialDocumentId " +
            " and not exists (select 1 from FinancialAccount fa2 where fa2.financialAccountParent.id=fa.id and fa2.deletedDate is null) " +
            " and fs.flagShowInAcc=1 " +
            " and fa.disableDate is null"

    )
    Long getFinancialAccount(Long financialDocumentId);

    @Query(" select 1 from FinancialDocumentItem fdi" +
            " join FinancialAccount  fa on fa.id=fdi.financialAccount.id  and fa.exchangeFlag=1 " +
            " join FinancialDocumentItemCurrency dc on dc.financialDocumentItem.id=fdi.id " +
            " where fdi.id=:financialDocumentItemId " +
            " and ((dc.foreignDebitAmount is null and dc.foreignCreditAmount is null) " +
            "      or dc.exchangeRate is null or dc.moneyPricingReference is null or dc.moneyType is null)")
    Long getInfoCurrency(Long financialDocumentItemId);

    @Query("select 1 from FinancialDocumentItem fdi " +
            " join FinancialAccount  fa on fa.id=fdi.financialAccount.id  and fa.exchangeFlag=1 " +
            " join FinancialDocumentItemCurrency dc on dc.financialDocumentItem.id=fdi.id " +
            " where fdi.id=:financialDocumentItemId " +
            "   and not exists (select 1 from AccountMoneyType mt where mt.financialAccount.id=fa.id " +
            "            and mt.moneyType.id=dc.moneyType.id)")
    Long equalCurrency(Long financialDocumentItemId);

    @Query(" select 1 from FinancialDocumentItem fdi  " +
            " join FinancialDocumentItemCurrency fdic on fdic.financialDocumentItem.id=fdi.id " +
            " where fdi.financialDocument.id=:financialDocumentId " +
            " group by fdi.creditAmount,fdi.debitAmount " +
            " having (nvl(fdi.debitAmount,0) != 0 and sum(nvl(fdic.foreignDebitAmount,0)) =0) " +
            " or (nvl(fdi.debitAmount,0) = 0 and sum(nvl(fdic.foreignDebitAmount,0)) != 0)" +
            " or (nvl(fdi.creditAmount,0) != 0 and sum(nvl(fdic.foreignCreditAmount,0)) = 0) " +
            " or (nvl(fdi.creditAmount,0) = 0 and sum(nvl(fdic.foreignCreditAmount,0)) != 0)" +
            " or (nvl(fdi.debitAmount,0) != 0 and nvl(fdi.creditAmount,0) != 0) "
    )
    Long costHarmony(Long financialDocumentId);

    @Query("select 1 from FinancialDocumentItem fdi " +
            " left join CentricAccount cn on cn.id=fdi.centricAccountId1.id " +
            " left join CentricAccount cn2 on cn2.id=fdi.centricAccountId2.id " +
            " left join CentricAccount cn3 on cn3.id=fdi.centricAccountId3.id " +
            " left join CentricAccount cn4 on cn4.id=fdi.centricAccountId4.id " +
            " left join CentricAccount cn5 on cn5.id=fdi.centricAccountId5.id " +
            " left join CentricAccount cn6 on cn6.id=fdi.centricAccountId6.id " +
            " where fdi.financialDocument.id =:financialDocumentId " +
            " and ((fdi.centricAccountId1 is not null and fdi.centricAccountId2 is not null and fdi.centricAccountId1 <> nvl (cn2.parentCentricAccount,0)) or " +
            "      (fdi.centricAccountId2 is not null and fdi.centricAccountId3 is not null and fdi.centricAccountId2<> nvl (cn3.parentCentricAccount,0))  or " +
            "      (fdi.centricAccountId3 is not null and fdi.centricAccountId4 is not null and fdi.centricAccountId3<> nvl (cn4.parentCentricAccount,0))  or " +
            "      (fdi.centricAccountId4 is not null and fdi.centricAccountId5 is not null and fdi.centricAccountId4<> nvl (cn5.parentCentricAccount,0))  or " +
            "      (fdi.centricAccountId5 is not null and fdi.centricAccountId6 is not null and fdi.centricAccountId5<> nvl (cn6.parentCentricAccount,0))) "
    )
    Long referenceCode(Long financialDocumentId);


    @Query(value = "  select sum(t.debit_amount) as debit_amount, " +
            "         sum(t.credit_amount) as credit_amount, " +
            "         max(fiac.full_description)  as fullDescription " +
            "   from fndc.Financial_Document_Item t  " +
            "        inner join fnac.financial_account fiac " +
            "            on fiac.id =t.financial_account_id   " +
            "            and fiac.deleted_date is null  " +
            "    where t.financial_document_id =:financialDocumentId ", nativeQuery = true)
    List<Object[]> findParamByDocumentId(Long financialDocumentId);


    @Query("select fdi from FinancialDocumentItem fdi where fdi.id in (:documentItemIdList)  and fdi.financialAccount.id=:accountId " +
            " and fdi.deletedDate is null")
    List<FinancialDocumentItem> getItemByDocumentItemIdListAndAccountId(List<Long> documentItemIdList, Long accountId);


    @Query(" select fdi from FinancialDocumentItem fdi " +
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

    @Query(value = "select count(fdi.id) as RECORDS_COUNT," +
            " sum(fdi.debitAmount) as SUM_DEBIT_AMOUNT," +
            " sum (fdi.creditAmount) as SUM_CREDIT_AMOUNT," +
            " coalesce(sum (fdi.creditAmount),0) - coalesce(sum(fdi.debitAmount) , 0) as REMAIN_AMOUNT" +
            " from FinancialDocumentItem fdi where fdi.financialDocument.id =:financialDocumentId and fdi.deletedDate is null ")
    List<Object[]> findFinancialDocumentItemByFinancialDocumentId(Long financialDocumentId);

    @Query(value = "select count(fdi.id) as SELECTED_RECORDS_COUNT," +
            " sum(fdi.debitAmount) as SELECTED_SUM_DEBIT_AMOUNT," +
            " sum (fdi.creditAmount) as SELECTED_SUM_CREDIT_AMOUNT," +
            " coalesce(sum (fdi.creditAmount),0) - coalesce(sum(fdi.debitAmount) , 0) as SELECTED_REMAIN_AMOUNT" +
            " from FinancialDocumentItem fdi where fdi.financialDocument.id =:financialDocumentId and fdi.id in (:financialDocumentItemIdList) " +
            " and fdi.deletedDate is null ")
    List<Object[]> findFinancialDocumentItemByFinancialDocumentIdList(Long financialDocumentId, List<Long> financialDocumentItemIdList);


    @Query(value = " SELECT FNDI.ID, " +
            "       FNDI.FINANCIAL_DOCUMENT_ID, " +
            "       FNDI.SEQUENCE_NUMBER," +
            "       FNDI.FINANCIAL_ACCOUNT_ID," +
            "       FNC.DESCRIPTION as FINANCIAL_ACCOUNT_DESCRIPTION," +
            " FNC.CODE AS FINANCIAL_ACCOUNT_CODE, " +
            "       FNDI.DEBIT_AMOUNT," +
            "       FNDI.CREDIT_AMOUNT," +
            "       FNDI.DESCRIPTION," +
            "       NVL(CNAC1.CODE, '') || NVL(CNAC1.NAME, '') ||" +
            "       NVL2(CNAC2.CODE, '-' || CNAC2.CODE, '') || NVL(CNAC2.NAME, '') ||" +
            "       NVL2(CNAC3.CODE, '-' || CNAC3.CODE, '') || NVL(CNAC3.NAME, '')  ||" +
            "       NVL2(CNAC4.CODE, '-' || CNAC4.CODE, '') || NVL(CNAC4.NAME, '')  ||" +
            "       NVL2(CNAC5.CODE, '-' || CNAC5.CODE, '') || NVL(CNAC5.NAME, '')  ||" +
            "       NVL2(CNAC6.CODE, '-' || CNAC6.CODE, '') || NVL(CNAC6.NAME, '')  AS CENTRICACCOUNTDESCRIPTION," +
            "       FNC.ACCOUNT_RELATION_TYPE_ID," +
            "       FNDI.CENTRIC_ACCOUNT_ID_1," +
            "       CNAC1.NAME as CENTRIC_ACCOUNT_DESCRIPTION_1," +
            "       FNDI.CENTRIC_ACCOUNT_ID_2," +
            "       CNAC2.NAME as CENTRIC_ACCOUNT_DESCRIPTION_2," +
            "       FNDI.CENTRIC_ACCOUNT_ID_3," +
            "       CNAC3.NAME as CENTRIC_ACCOUNT_DESCRIPTION_3," +
            "       FNDI.CENTRIC_ACCOUNT_ID_4," +
            "       CNAC4.NAME as CENTRIC_ACCOUNT_DESCRIPTION_4," +
            "       FNDI.CENTRIC_ACCOUNT_ID_5," +
            "       CNAC5.NAME  as CENTRIC_ACCOUNT_DESCRIPTION_5," +
            "       FNDI.CENTRIC_ACCOUNT_ID_6," +
            "       CNAC6.NAME as CENTRIC_ACCOUNT_DESCRIPTION_6," +
            " FNDI.CREATOR_ID " +
            "  FROM FNDC.FINANCIAL_DOCUMENT_ITEM FNDI" +
            " INNER JOIN FNAC.FINANCIAL_ACCOUNT FNC" +
            "    ON FNC.ID = FNDI.FINANCIAL_ACCOUNT_ID" +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC1" +
            "    ON CNAC1.ID = FNDI.CENTRIC_ACCOUNT_ID_1" +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC2" +
            "    ON CNAC2.ID = FNDI.CENTRIC_ACCOUNT_ID_2" +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC3" +
            "    ON CNAC3.ID = FNDI.CENTRIC_ACCOUNT_ID_3" +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC4" +
            "    ON CNAC4.ID = FNDI.CENTRIC_ACCOUNT_ID_4" +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC5" +
            "    ON CNAC5.ID = FNDI.CENTRIC_ACCOUNT_ID_5" +
            "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC6" +
            "    ON CNAC6.ID = FNDI.CENTRIC_ACCOUNT_ID_6" +
            " WHERE FNDI.FINANCIAL_DOCUMENT_ID = :financialDocumentId " +
            " and  ( :financialDocumentItem is null or FNDI.ID = :financialDocumentItemId)",
            nativeQuery = true)
    List<Object[]> findByFinancialDocumentItemId(Long financialDocumentId, Object financialDocumentItem, Long financialDocumentItemId);

    @Query(value = "WITH QRY AS " +
            " (SELECT SUM(NVL(FDI.DEBIT_AMOUNT, 0)) SUM_DEBIT, " +
            "         SUM(NVL(FDI.CREDIT_AMOUNT, 0)) SUM_CREDIT   " +
            "    FROM fndc.FINANCIAL_DOCUMENT_ITEM FDI  " +
            "   INNER JOIN fndc.FINANCIAL_DOCUMENT FD " +
            "      ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID " +
            "     AND FD.ORGANIZATION_ID = :organizationId " +
            "     AND FD.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "     AND FD.FINANCIAL_DEPARTMENT_ID = :financialDepartmentId " +
            "     AND FD.DOCUMENT_DATE <= :date " +
            "     AND FD.DOCUMENT_DATE >= " +
            "         (SELECT MIN(FP.START_DATE) AS FINANCIALPERIODSTARTDATE " +
            "            FROM FNPR.FINANCIAL_PERIOD FP " +
            "           INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT " +
            "              ON FP.FINAN_PERIOD_TYPE_ASSIGN_ID = FPT.ID " +
            "             AND FPT.ORGANIZATION_ID = FD.ORGANIZATION_ID " +
            "             AND FPT.ACTIVE_FLAG = 1 " +
            "           INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY " +
            "              ON FPT.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID " +
            "           WHERE FP.FINANCIAL_PERIOD_STATUS_ID = 1) " +
            "     AND (FD.DOCUMENT_NUMBER <= " +
            "         (SELECT MAX(FD_INER.DOCUMENT_NUMBER) " +
            "             FROM fndc.FINANCIAL_DOCUMENT_ITEM FDI_INER " +
            "            INNER JOIN fndc.FINANCIAL_DOCUMENT FD_INER " +
            "               ON FD_INER.ID = FDI_INER.FINANCIAL_DOCUMENT_ID " +
            "            WHERE FD_INER.FINANCIAL_LEDGER_TYPE_ID = " +
            "                  :financialLedgerTypeId " +
            "              AND FD_INER.FINANCIAL_DEPARTMENT_ID = :financialDepartmentId " +
            "              AND FD_INER.ORGANIZATION_ID = :organizationId " +
            "              AND FD_INER.DOCUMENT_DATE <= :date)) " +
            "   INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FS " +
            "      ON FS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID " +
            "     AND FS.CODE > 10 " +
            "   WHERE FDI.FINANCIAL_ACCOUNT_ID = :financialAccountId) " +
            "SELECT NVL(SUM_CREDIT, 0) SUM_CREDIT, " +
            "       NVL(SUM_DEBIT, 0) SUM_DEBIT, " +
            "       (SELECT ACN.ID " +
            "          FROM FNAC.ACCOUNT_NATURE_TYPE ACN " +
            "         INNER JOIN FNAC.FINANCIAL_ACCOUNT FA " +
            "            ON ACN.ID = FA.ACCOUNT_NATURE_TYPE_ID " +
            "         WHERE FA.ID = :financialAccountId) ACCOUNT_NATURE_TYPE_ID " +
            "  FROM QRY "
            , nativeQuery = true)
    List<Object[]> findByMoneyTypeAndFinancialAccountId(Long organizationId, Long financialLedgerTypeId, Long financialDepartmentId, Date date, Long financialAccountId);

    @Query(value = "WITH QRY AS " +
            " (SELECT SUM(FDI.DEBIT_AMOUNT) SUM_DEBIT," +
            "         SUM(FDI.CREDIT_AMOUNT) SUM_CREDIT," +
            "         ACN.ID ACCOUNT_NATURE_TYPE_ID," +
            "         FA.DESCRIPTION FINANCIAL_ACCOUNT_DESCRIPTION" +
            "    FROM fndc.FINANCIAL_DOCUMENT_ITEM FDI" +
            "   INNER JOIN fndc.FINANCIAL_DOCUMENT FD" +
            "      ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID" +
            "   INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FS" +
            "      ON FS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID" +
            "   INNER JOIN FNAC.FINANCIAL_ACCOUNT FA" +
            "      ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID" +
            "   INNER JOIN FNAC.ACCOUNT_NATURE_TYPE ACN" +
            "      ON ACN.ID = FA.ACCOUNT_NATURE_TYPE_ID" +
            "   INNER JOIN (SELECT DISTINCT FD_INER.DOCUMENT_DATE," +
            "                       FD_INER.DOCUMENT_NUMBER," +
            "                       FDI_INER.FINANCIAL_ACCOUNT_ID," +
            "                       FD_INER.FINANCIAL_LEDGER_TYPE_ID," +
            "                       FD_INER.FINANCIAL_DEPARTMENT_ID              " +
            "                        FROM fndc.FINANCIAL_DOCUMENT_ITEM FDI_INER" +
            "               INNER JOIN fndc.FINANCIAL_DOCUMENT FD_INER" +
            "                  ON FD_INER.ID = FDI_INER.FINANCIAL_DOCUMENT_ID" +
            "               WHERE (:financialDocument is null or FD_INER.ID =:financialDocumentId ) " +
            "                 AND (:financialDocumentItem is null or FDI_INER.ID =:financialDocumentItemId )" +
            "       ) INER_DOCUMENT" +
            "      ON INER_DOCUMENT.FINANCIAL_ACCOUNT_ID = FDI.FINANCIAL_ACCOUNT_ID" +
            "       AND FD.FINANCIAL_LEDGER_TYPE_ID =" +
            "         INER_DOCUMENT.FINANCIAL_LEDGER_TYPE_ID" +
            "     AND FD.FINANCIAL_DEPARTMENT_ID = INER_DOCUMENT.FINANCIAL_DEPARTMENT_ID" +
            "     AND FD.DOCUMENT_DATE <= INER_DOCUMENT.DOCUMENT_DATE" +
            "     AND FD.DOCUMENT_NUMBER <= INER_DOCUMENT.DOCUMENT_NUMBER" +
            "   WHERE ((FS.CODE > 10 AND" +
            "         FD.DOCUMENT_NUMBER != INER_DOCUMENT.DOCUMENT_NUMBER) OR" +
            "         FD.DOCUMENT_NUMBER = INER_DOCUMENT.DOCUMENT_NUMBER)" +
            "     AND FD.DOCUMENT_DATE >=" +
            "         (SELECT MIN(FP.START_DATE) AS FINANCIALPERIODSTARTDATE" +
            "            FROM FNPR.FINANCIAL_PERIOD FP" +
            "           INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT" +
            "              ON FP.FINAN_PERIOD_TYPE_ASSIGN_ID = FPT.ID" +
            "             AND FPT.ORGANIZATION_ID = FD.ORGANIZATION_ID" +
            "             AND FPT.ACTIVE_FLAG = 1" +
            "           INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY" +
            "              ON FPT.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID" +
            "           WHERE FP.FINANCIAL_PERIOD_STATUS_ID = 1)" +
            "   GROUP BY FDI.FINANCIAL_ACCOUNT_ID," +
            "            ACN.ID," +
            "            ACN.DESCRIPTION," +
            "            FA.ID," +
            "            FA.DESCRIPTION)" +
            " SELECT SUM_DEBIT," +
            "       SUM_CREDIT," +
            "       ACCOUNT_NATURE_TYPE_ID," +
            "       FINANCIAL_ACCOUNT_DESCRIPTION," +
            "       'سرجمع حساب' || ' ''' || FINANCIAL_ACCOUNT_DESCRIPTION || " +
            "       ''' با ماهیت آن همخوانی ندارد. ' RESULT_MESSAGE " +
            "  FROM QRY " +
            " WHERE (QRY.ACCOUNT_NATURE_TYPE_ID = 3 AND SUM_DEBIT > SUM_CREDIT)" +
            "    OR (QRY.ACCOUNT_NATURE_TYPE_ID = 2 AND SUM_DEBIT < SUM_CREDIT) "
            , nativeQuery = true)
    List<Object[]> findByFinancialDocumentItemByIdAndFinancialDocumentId(Long financialDocumentId, Long financialDocumentItemId, Object financialDocumentItem, Object financialDocument);

    @Query(value = "select count(t.id)" +
            "  from fndc.financial_document_item t" +
            " left join fndc.financial_document_error fde" +
            "      on fde.financial_document_item_id = t.id" +
            "    left join fndc.financial_document_item_currency fdic" +
            "      on fdic.financial_document_item_id = t.id" +
            "    left join fndc.financial_document_refrence fdr" +
            "      on fdr.financial_document_item_id = t.id" +
            "   where t.id = :documentItemId" +
            "     and (fde.financial_document_item_id = :documentItemId or" +
            "         fdic.financial_document_item_id = :documentItemId or" +
            "         fdr.financial_document_item_id  = :documentItemId)"
            , nativeQuery = true)
    Long getDocumentItemByIdForDelete(Long documentItemId);
}
