package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FinancialDocumentRepository extends JpaRepository<FinancialDocument, Long> {

    @Query(value = " SELECT FIDC.ID as documentId ," +
            "       FIDC.DOCUMENT_DATE as documentDate," +
            "       FIDC.DESCRIPTION as description," +
            "       FNDN.DOCUMENT_NUMBER," +
            "       FIDC.FINANCIAL_DOCUMENT_TYPE_ID," +
            "       FNDT.DESCRIPTION AS FINANCIAL_DOCUMENT_TYPE_DESCRIPTION," +
            "       FIDC.DESCRIPTION AS FULL_DESCRIPTION," +
            "       SUM( NVL(FNDI.DEBIT_AMOUNT,0)) AS debitAmount ," +
            " SUM( NVL(FNDI.CREDIT_AMOUNT,0)) AS creditAmount, " +
            "       USR.ID AS USERID," +
            "       USR.NICK_NAME AS USERNAME," +
            "       FIDC.FINANCIAL_DOCUMENT_STATUS_ID," +
            "       DS.NAME DOCUMENT_STATUS_NAME," +
            "       DS.CODE DOCUMENT_STATUS_CODE, " +
            " FIDC.DEPARTMENT_ID as departmentId , " +
            " FIDC.DCHD_NUM " +
            "  FROM fndc.FINANCIAL_DOCUMENT FIDC " +
            "  INNER JOIN FNDC.FINANCIAL_DOCUMENT_TYPE FNDT " +
            "    ON FIDC.FINANCIAL_DOCUMENT_TYPE_ID = FNDT.ID " +
            "   AND FNDT.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FNDI " +
            "    ON FIDC.ID = FNDI.FINANCIAL_DOCUMENT_ID " +
            "   AND FNDI.DELETED_DATE IS NULL  " +
            "  LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER FNDN " +
            "    ON FNDN.FINANCIAL_DOCUMENT_ID = FIDC.ID " +
            "   AND FNDN.DELETED_DATE IS NULL " +
            "  INNER JOIN FNAC.FINANCIAL_ACCOUNT FC " +
            "    ON FC.ID = FNDI.FINANCIAL_ACCOUNT_ID " +
            "   AND FC.DELETED_DATE IS NULL " +
            "  LEFT OUTER JOIN SEC.APPLICATION_USER USR " +
            "    ON USR.ID = FIDC.CREATOR_ID " +
            " INNER JOIN fndc.FINANCIAL_DOCUMENT_STATUS DS " +
            "    ON DS.ID = FINANCIAL_DOCUMENT_STATUS_ID," +
            " TABLE(fnsc.PKG_FINANCIAL_SECURITY.GET_PERMISSION(-1," +
            "                                 :activityCode," +
            "                                 fidc.financial_period_id," +
            "                                 FNDT.ID," +
            "                                 FIDC.CREATOR_ID," +
            "                                 fidc.financial_department_id,                                                  " +
            "       fidc.financial_ledger_type_id," +
            "                                :departmentId," +
            "                                 :userId)) FNSC " +
            "  WHERE FIDC.ORGANIZATION_ID = :organizationId" +
            "   and FIDC.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId " +
            " and FIDC.DEPARTMENT_ID= :departmentId  " +
            "   and FIDC.DOCUMENT_DATE >= trunc(:startDate) " +
            "   AND FIDC.DOCUMENT_DATE <= trunc(:endDate) " +
            "   AND FNDI.CREDIT_AMOUNT = CASE" +
            "         WHEN :priceTypeId = 1 THEN" +
            "          0" +
            "         ELSE" +
            "          FNDI.CREDIT_AMOUNT" +
            "       END" +
            "   AND FNDI.DEBIT_AMOUNT = CASE" +
            "         WHEN :priceTypeId = 2 THEN" +
            "          0" +
            "         ELSE" +
            "          FNDI.DEBIT_AMOUNT" +
            "       END" +
            "   AND FNDN.FINANCIAL_NUMBERING_TYPE_ID = :financialNumberingTypeId" +
            "   AND (FNDN.DOCUMENT_NUMBER >= :fromNumberId OR :fromNumber IS NULL) " +
            "   AND (FNDN.DOCUMENT_NUMBER <= :toNumberId OR :toNumber IS NULL) " +
            "   AND FIDC.FINANCIAL_DOCUMENT_STATUS_ID IN (:documentStatusId) " +
            "   AND (FIDC.DESCRIPTION LIKE '%' || :description || '%' OR" +
            "       :description IS NULL) " +
            "   AND ((FC.CODE >= :fromAccountCode OR :fromAccount IS NULL) AND " +
            "       (FC.CODE <= :toAccountCode OR :toAccount IS NULL)) " +
            "   AND (:centricAccount IS NULL OR" +
            "       (FNDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId OR" +
            "       FNDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId OR" +
            "       FNDI.CENTRIC_ACCOUNT_ID_3 = :centricAccountId OR" +
            "       FNDI.CENTRIC_ACCOUNT_ID_4 = :centricAccountId OR" +
            "       FNDI.CENTRIC_ACCOUNT_ID_5 = :centricAccountId OR" +
            "       FNDI.CENTRIC_ACCOUNT_ID_6 = :centricAccountId)) " +
            "   AND (:centricAccountType IS NULL OR" +
            "       :centricAccountTypeId IN" +
            "       (SELECT CNT.CENTRIC_ACCOUNT_TYPE_ID" +
            "           FROM FNAC.CENTRIC_ACCOUNT CNT" +
            "          WHERE FNDI.CENTRIC_ACCOUNT_ID_1 = CNT.ID" +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_2 = CNT.ID" +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_3 = CNT.ID" +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_4 = CNT.ID" +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_5 = CNT.ID" +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_6 = CNT.ID)) " +
            "   AND (:documentUser IS NULL OR (FIDC.CREATOR_ID = :documentUserId OR" +
            "       FIDC.LAST_MODIFIER_ID = :documentUserId)) " +
            "  AND (:priceType IS NULL OR" +
            "       (:priceTypeId = 1 AND " +
            "       (:fromPrice IS NULL OR" +
            "       (FNDI.DEBIT_AMOUNT >= " +
            "       :fromPriceAmount - (:fromPriceAmount * NVL(:tolerance, 0)) / 100.0)) AND " +
            "       (:toPrice IS NULL OR" +
            "       (FNDI.DEBIT_AMOUNT <= " +
            "       :toPriceAmount + ((:toPriceAmount * NVL(:tolerance, 0)) / 100.0)))) OR" +
            "       (:priceTypeId = 2 AND " +
            "       (:fromPrice IS NULL OR" +
            "       (FNDI.CREDIT_AMOUNT >= " +
            "       :fromPriceAmount - (:fromPriceAmount * NVL(:tolerance, 0)) / 100.0)) AND " +
            "       (:toPrice IS NULL OR" +
            "       (FNDI.CREDIT_AMOUNT <= " +
            "       :toPriceAmount + ((:toPriceAmount * NVL(:tolerance, 0)) / 100.0 ))))) " +
            " and (:financialDocumentType is null or FIDC.FINANCIAL_DOCUMENT_TYPE_ID =:financialDocumentTypeId ) " +
            "   and FNSC.SEC_RESULT = 1 " +
            " AND (NVL(:flgCreationMod, 0) = 0 OR " +
            "       (:flgCreationMod = 1 AND FIDC.DCHD_ID IS NULL) OR " +
            "       (:flgCreationMod = 2 AND FIDC.DCHD_ID IS NOT NULL))" +
            "  group by fidc.id,usr.id,usr.nick_name,document_date,fidc.description,FNDN.document_number,fidc.financial_document_type_id,fndt.description," +
            " FINANCIAL_DOCUMENT_STATUS_ID, " +
            "          DS.NAME , " +
            "          DS.CODE," +
            " FIDC.DEPARTMENT_ID,FIDC.DCHD_NUM  "
            , countQuery = " select count(fidc.id) " +
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
            " INNER JOIN fndc.FINANCIAL_DOCUMENT_STATUS DS " +
            "    ON DS.ID = FINANCIAL_DOCUMENT_STATUS_ID ," +
            " TABLE(fnsc.PKG_FINANCIAL_SECURITY.GET_PERMISSION(-1," +
            "                                 :activityCode," +
            "                                 fidc.financial_period_id," +
            "                                 FNDT.ID," +
            "                                FIDC.CREATOR_ID," +
            "                                 fidc.financial_department_id,           " +
            "                                 fidc.financial_ledger_type_id," +
            "                                :departmentId," +
            "                                 :userId)) FNSC " +
            "  WHERE FIDC.ORGANIZATION_ID = :organizationId" +
            "   and FIDC.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId" +
            " and FIDC.DEPARTMENT_ID= :departmentId  " +
            "   and FIDC.DOCUMENT_DATE >= trunc(:startDate) " +
            "   AND FIDC.DOCUMENT_DATE <= trunc(:endDate) " +
            "   AND FNDI.CREDIT_AMOUNT = CASE" +
            "         WHEN :priceTypeId = 1 THEN" +
            "          0" +
            "         ELSE" +
            "          FNDI.CREDIT_AMOUNT" +
            "       END" +
            "   AND FNDI.DEBIT_AMOUNT = CASE" +
            "         WHEN :priceTypeId = 2 THEN" +
            "          0" +
            "         ELSE" +
            "          FNDI.DEBIT_AMOUNT" +
            "       END" +
            "   AND FNDN.FINANCIAL_NUMBERING_TYPE_ID = :financialNumberingTypeId" +
            "   AND (FNDN.DOCUMENT_NUMBER >= :fromNumberId OR :fromNumber IS NULL) " +
            "   AND (FNDN.DOCUMENT_NUMBER <= :toNumberId OR :toNumber IS NULL) " +
            "   AND FIDC.FINANCIAL_DOCUMENT_STATUS_ID IN (:documentStatusId) " +
            "   AND (FIDC.DESCRIPTION LIKE '%' || :description || '%' OR" +
            "       :description IS NULL) " +
            "   AND ((FC.CODE >= :fromAccountCode OR :fromAccount IS NULL) AND " +
            "       (FC.CODE <= :toAccountCode OR :toAccount IS NULL)) " +
            "   AND (:centricAccount IS NULL OR" +
            "       (FNDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId OR" +
            "       FNDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId OR" +
            "       FNDI.CENTRIC_ACCOUNT_ID_3 = :centricAccountId OR" +
            "       FNDI.CENTRIC_ACCOUNT_ID_4 = :centricAccountId OR" +
            "       FNDI.CENTRIC_ACCOUNT_ID_5 = :centricAccountId OR" +
            "       FNDI.CENTRIC_ACCOUNT_ID_6 = :centricAccountId)) " +
            "   AND (:centricAccountType IS NULL OR" +
            "       :centricAccountTypeId IN" +
            "       (SELECT CNT.CENTRIC_ACCOUNT_TYPE_ID" +
            "           FROM FNAC.CENTRIC_ACCOUNT CNT" +
            "          WHERE FNDI.CENTRIC_ACCOUNT_ID_1 = CNT.ID" +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_2 = CNT.ID" +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_3 = CNT.ID" +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_4 = CNT.ID" +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_5 = CNT.ID" +
            "             OR FNDI.CENTRIC_ACCOUNT_ID_6 = CNT.ID)) " +
            "   AND (:documentUser IS NULL OR (FIDC.CREATOR_ID = :documentUserId OR" +
            "       FIDC.LAST_MODIFIER_ID = :documentUserId)) " +
            "  AND (:priceType IS NULL OR" +
            "       (:priceTypeId = 1 AND " +
            "       (:fromPrice IS NULL OR" +
            "       (FNDI.DEBIT_AMOUNT >= " +
            "       :fromPriceAmount - (:fromPriceAmount * NVL(:tolerance, 0)) / 100.0 )) AND " +
            "       (:toPrice IS NULL OR" +
            "       (FNDI.DEBIT_AMOUNT <= " +
            "       :toPriceAmount + ((:toPriceAmount * NVL(:tolerance, 0)) /  100.0 )))) OR" +
            "       (:priceTypeId = 2 AND " +
            "       (:fromPrice IS NULL OR" +
            "       (FNDI.CREDIT_AMOUNT >= " +
            "       :fromPriceAmount - (:fromPriceAmount * NVL(:tolerance, 0)) / 100.0 )) AND " +
            "       (:toPrice IS NULL OR" +
            "       (FNDI.CREDIT_AMOUNT <= " +
            "       :toPriceAmount + ((:toPriceAmount * NVL(:tolerance, 0)) / 100.0 ))))) " +
            " and (:financialDocumentType is null or FIDC.FINANCIAL_DOCUMENT_TYPE_ID =:financialDocumentTypeId ) " +
            "   and FNSC.SEC_RESULT = 1" +
            " AND (NVL(:flgCreationMod, 0) = 0 OR " +
            "       (:flgCreationMod = 1 AND FIDC.DCHD_ID IS NULL) OR " +
            "       (:flgCreationMod = 2 AND FIDC.DCHD_ID IS NOT NULL))" +
            "  group by fidc.id,usr.id,usr.nick_name,document_date,fidc.description,FNDN.document_number,financial_document_type_id,fndt.description, " +
            "   FINANCIAL_DOCUMENT_STATUS_ID, " +
            "                      DS.NAME , " +
            "                     DS.CODE," +
            " FIDC.DEPARTMENT_ID,FIDC.DCHD_NUM "
            , nativeQuery = true)
    Page<Object[]> getFinancialDocumentList(String activityCode, Long departmentId, Long userId,
                                            Long organizationId, Long ledgerTypeId, LocalDateTime startDate, LocalDateTime endDate,
                                            Long priceTypeId, Long financialNumberingTypeId, Long fromNumberId, Object fromNumber,
                                            Long toNumberId, Object toNumber, List<Long> documentStatusId, String description, String fromAccountCode, Object fromAccount,
                                            String toAccountCode, Object toAccount, Object centricAccount, Long centricAccountId,
                                            Object centricAccountType, Long centricAccountTypeId, Object documentUser, Long documentUserId, Object priceType, Object fromPrice,
                                            Long fromPriceAmount, Object toPrice, Long toPriceAmount,
                                            Double tolerance, Object financialDocumentType, Long financialDocumentTypeId, Long flgCreationMod, Pageable pageable);

    @Query(" SELECT fd from FinancialDocument fd join fd.financialPeriod   fp where fp.financialPeriodStatus.id=1 and fd.id=:financialDocumentId")
    FinancialDocument getActivePeriodInDocument(Long financialDocumentId);

    @Query(" SELECT fd from FinancialDocument fd where fd.id=:financialDocumentId and fd.deletedDate is null")
    FinancialDocument getActiveDocumentById(Long financialDocumentId);

    @Query(value = " select 1" +
            "  from fndc.financial_document fd" +
            " inner join fnpr.financial_period t" +
            "    on t.id = fd.financial_period_id" +
            " where t.financial_period_status_id = 1" +
            "   and fd.id = :financialDocumentId " +
            "   and exists (select 1" +
            "          from fnpr.financial_month fm" +
            "         inner join fnpr.financial_month_type fmt" +
            "            on fmt.id = fm.financial_month_type_id" +
            "         inner join fnpr.financial_period_type fpt" +
            "            on fpt.id = fmt.financial_period_type_id" +
            "         inner join fnpr.financial_period fp" +
            "            on fp.financial_period_type_id = fpt.id" +
            "         inner join fnpr.financial_period_type_assign fpts" +
            "            on fpts.financial_period_id = fp.id" +
            "         inner join fndc.financial_document fd" +
            "            on fp.id = fd.financial_period_id" +
            "         where fd.id=:financialDocumentId" +
            " and case fpt.calendar_type_id" +
            "                 when 2 then" +
            "                  extract(month from TO_DATE(TO_char(fd.document_date," +
            "                                          'mm/dd/yyyy')," +
            "                                  'mm/dd/yyyy')) " +
            "                 when 1 then" +
            "                  TO_NUMBER(substr(TO_CHAR(TO_DATE(TO_char(fd.document_date," +
            "                                                           'mm/dd/yyyy')," +
            "                                                   'mm/dd/yyyy')," +
            "                                           'yyyy/mm/dd'," +
            "                                           'NLS_CALENDAR=persian')," +
            "                                   6," +
            "                                   2)) " +
            "               end = case" +
            "                 when fpt.current_year_flag = 1 then" +
            "                  fpt.from_month + fmt.month_number - 1" +
            "                 else" +
            "                  case" +
            "                    when fpt.from_month + fmt.month_number - 1 > 12 then" +
            "                     fpt.from_month + fmt.month_number - 13" +
            "                    else" +
            "                     fpt.from_month + fmt.month_number - 1" +
            "                  end" +
            "               end" +
            "           and fm.financial_month_status_id = 1" +
            "        " +
            "        ) ", nativeQuery = true)
    Long getActivePeriodAndMontInDocument(Long financialDocumentId);

    @Query(value = " SELECT " +
            "         NF_ID, " +
            "         FIRST_SERIAL, " +
            "         TRANSLATED_RESETER, " +
            "         NF_SERIAL_LENGTH, " +
            "         1 SERIAL_RESETER " +
            "    FROM (SELECT NF.ID NF_ID, " +
            "                 NF.FIRST_SERIAL - 1 FIRST_SERIAL, " +
            "                REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(NF.RESETER," +
            "                                                               '$DAT6'," +
            "                                                               TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE," +
            "                                                                                       'mm/dd/yyyy')," +
            "                                                                               'mm/dd/yyyy')," +
            "                                                                       'yymmdd'," +
            "                                                                       'NLS_CALENDAR=persian'))," +
            "                                                         '$DAT'," +
            "                                                         TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE," +
            "                                                                                 'mm/dd/yyyy')," +
            "                                                                         'mm/dd/yyyy')," +
            "                                                                 'yyyymmdd'," +
            "                                                                 'NLS_CALENDAR=persian'))," +
            "                                                 '$LEG'," +
            "                                                 (SELECT LT.CODE" +
            "                                                    FROM fndc.FINANCIAL_LEDGER_TYPE LT" +
            "                                                   WHERE LT.ID = " +
            "                                                         FD.FINANCIAL_LEDGER_TYPE_ID))," +
            "                                         '$DEP'," +
            "                                         (SELECT DP.CODE" +
            "                                            FROM ORG.DEPARTMENT DP" +
            "                                           WHERE DP.ID = " +
            "                                                 FD.DEPARTMENT_ID))," +
            "                                 '$ORG'," +
            "                                 (SELECT OG.CODE" +
            "                                    FROM FNDC.FINANCIAL_ORGANIZATION OG" +
            "                                   WHERE OG.ORGANIZATION_ID = :organizationId))," +
            "                         '$PRI'," +
            "                         (SELECT PR.CODE" +
            "                            FROM FNPR.FINANCIAL_PERIOD PR" +
            "                           WHERE PR.ID = FD.FINANCIAL_PERIOD_ID)) TRANSLATED_RESETER," +
            "                 NF.SERIAL_LENGTH NF_SERIAL_LENGTH " +
            "            FROM fndc.FINANCIAL_DOCUMENT FD " +
            "           INNER JOIN fndc.LEDGER_NUMBERING_TYPE LNT " +
            "              ON LNT.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
            "             AND LNT.DELETED_DATE IS NULL " +
            "           INNER JOIN fndc.FINANCIAL_NUMBERING_TYPE NT " +
            "              ON NT.ID = LNT.FINANCIAL_NUMBERING_TYPE_ID " +
            "              AND NT.TYPE_STATUS = :numberingType" +
            "             AND NT.DELETED_DATE IS NULL " +
            "           INNER JOIN fndc.FINANCIAL_NUMBERING_FORMAT NF " +
            "              ON NF.FINANCIAL_NUMBERING_TYPE_ID = NT.ID " +
            "             AND NF.ORGANIZATION_ID = :organizationId" +
            "             AND NF.DELETED_DATE IS NULL " +
            "           WHERE FD.ID = :financialDocumentId" +
            "             AND FD.DELETED_DATE IS NULL) " +
            "    LEFT OUTER JOIN fndc.NUMBERING_FORMAT_SERIAL NFS " +
            "      ON NFS.NUMBERING_FORMAT_ID = NF_ID " +
            "     AND SERIAL_RESETER = TRANSLATED_RESETER " +
            "     AND NFS.DELETED_DATE IS NULL " +
            "     AND NFS.SERIAL_LENGTH = NF_SERIAL_LENGTH " +
            "   WHERE NOT EXISTS (SELECT 1 " +
            "            FROM fndc.NUMBERING_FORMAT_SERIAL NFS " +
            "           WHERE NFS.NUMBERING_FORMAT_ID = NF_ID " +
            "             AND SERIAL_RESETER = TRANSLATED_RESETER " +
            "             AND NFS.DELETED_DATE IS NULL " +
            "             AND NFS.SERIAL_LENGTH = NF_SERIAL_LENGTH) ", nativeQuery = true)
    List<Object[]> getSerialNumber(Long organizationId, Long financialDocumentId, Long numberingType);

    @Query(value = " SELECT NUMBERING_TYPE_ID," +
            "         REPLACE(GENERATED_COD,'$SRL'," +
            " LPAD(TO_CHAR(LAST_SERIAL), SERIAL_LENGTH,'0')) Final_Document_Number " +
            "    FROM (SELECT NFS.ID NUMBERING_FORMAT_SERIAL_ID, " +
            "                 NT.ID NUMBERING_TYPE_ID, " +
            "                 NT.DESCRIPTION NUMBERING_TYPE_DESCRIPTION, " +
            "                 NFT.ID NUMBERING_FORMAT_TYPE_ID, " +
            "                 NFT.DESCRIPTION NUMBERING_FORMAT_TYPE_DESCRIPTION, " +
            "                 NFT.CODE, " +
            "                 NF.SERIAL_LENGTH, " +
            "                 LAST_SERIAL, " +
            "                 NFT.CODE FORMAT_CODE," +
            " NFS.SERIAL_RESETER,  " +
            "                 REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(NFT.CODE," +
            "                                                               '$DAT6'," +
            "                                                               TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE," +
            "                                                                                       'mm/dd/yyyy')," +
            "                                                                               'mm/dd/yyyy')," +
            "                                                                       'yymmdd'," +
            "                                                                       'NLS_CALENDAR=persian'))," +
            "                                                        '$DAT'," +
            "                                                        TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE," +
            "                                                                                'mm/dd/yyyy')," +
            "                                                                        'mm/dd/yyyy')," +
            "                                                                'yyyymmdd'," +
            "                                                                'NLS_CALENDAR=persian'))," +
            "                                                '$LEG'," +
            "                                                (SELECT LT.CODE" +
            "                                                   FROM fndc.FINANCIAL_LEDGER_TYPE LT" +
            "                                                  WHERE LT.ID = " +
            "                                                        FD.FINANCIAL_LEDGER_TYPE_ID))," +
            "                                        '$DEP'," +
            "                                        (SELECT DP.CODE" +
            "                                            FROM ORG.DEPARTMENT DP" +
            "                                           WHERE DP.ID = " +
            "                                                 FD.DEPARTMENT_ID))," +
            "                                '$ORG'," +
            "                                (SELECT OG.CODE" +
            "                                   FROM FNDC.FINANCIAL_ORGANIZATION OG" +
            "                                  WHERE OG.ORGANIZATION_ID = :organizationId))," +
            "                        '$PRI'," +
            "                        (SELECT PR.CODE" +
            "                           FROM FNPR.FINANCIAL_PERIOD PR" +
            "                          WHERE PR.ID = FD.FINANCIAL_PERIOD_ID)) GENERATED_COD " +
            "            FROM fndc.FINANCIAL_DOCUMENT FD " +
            "           INNER JOIN fndc.LEDGER_NUMBERING_TYPE LNT " +
            "              ON LNT.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
            "             AND LNT.DELETED_DATE IS NULL " +
            "           INNER JOIN fndc.FINANCIAL_NUMBERING_TYPE NT " +
            "              ON NT.ID = LNT.FINANCIAL_NUMBERING_TYPE_ID " +
            "              AND NT.TYPE_STATUS = :numberingType " +
            "             AND NT.DELETED_DATE IS NULL " +
            "           INNER JOIN fndc.FINANCIAL_NUMBERING_FORMAT NF " +
            "              ON NF.FINANCIAL_NUMBERING_TYPE_ID = NT.ID " +
            "             AND NF.ORGANIZATION_ID = :organizationId" +
            "             AND NF.DELETED_DATE IS NULL " +
            "           INNER JOIN fndc.FINANCIAL_NUMBERING_FORMAT_TYPE NFT " +
            "              ON NFT.ID = NF.NUMBERING_FORMAT_TYPE_ID " +
            "             AND NFT.DELETED_DATE IS NULL " +
            "            LEFT OUTER JOIN fndc.NUMBERING_FORMAT_SERIAL NFS " +
            "              ON NFS.NUMBERING_FORMAT_ID = NF.ID " +
            "             AND NFS.DELETED_DATE IS NULL " +
            "           WHERE FD.ID = :financialDocumentId " +
            " and NFS.SERIAL_LENGTH = nf.serial_length " +
            "             AND FD.DELETED_DATE IS NULL) " +
            " WHERE SERIAL_RESETER = REPLACE(GENERATED_COD,'$SRL','') "
            , nativeQuery = true)
    List<Object[]> findDocumentNumber(Long organizationId, Long financialDocumentId, Long numberingType);

    @Query(value = " SELECT min(FD.DOCUMENT_DATE) " +
            "      FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER DN " +
            "        ON FD.ID = DN.FINANCIAL_DOCUMENT_ID " +
            "     WHERE FD.DELETED_DATE IS NULL " +
            "       AND FD.ORGANIZATION_ID = :organizationId" +
            "       AND DN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "       AND DN.DOCUMENT_NUMBER = NVL(:fromNumber, DN.DOCUMENT_NUMBER) " +
            "       AND DN.DELETED_DATE IS NULL" +
            "        AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId "
            , nativeQuery = true)
    LocalDateTime findByFinancialDocumentByNumberingTypeAndFromNumber(Long documentNumberingTypeId, String fromNumber, Long organizationId, Long ledgerTypeId);

    @Query(value = " SELECT max(FD.DOCUMENT_DATE) " +
            "      FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER DN " +
            "        ON FD.ID = DN.FINANCIAL_DOCUMENT_ID " +
            "     WHERE FD.DELETED_DATE IS NULL " +
            "       AND FD.ORGANIZATION_ID = :organizationId" +
            "       AND DN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "       AND DN.DOCUMENT_NUMBER = NVL(:toNumber, DN.DOCUMENT_NUMBER) " +
            "       AND DN.DELETED_DATE IS NULL " +
            "  AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId "
            , nativeQuery = true)
    LocalDateTime findByFinancialDocumentByNumberingAndToNumber(Long documentNumberingTypeId, String toNumber, Long organizationId, Long ledgerTypeId);

    @Query(value = " SELECT MIN(DN.DOCUMENT_NUMBER) " +
            "      FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER DN " +
            "        ON FD.ID = DN.FINANCIAL_DOCUMENT_ID " +
            "       AND DN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "       AND DN.DELETED_DATE IS NULL " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP " +
            "    ON FP.ID = FD.FINANCIAL_PERIOD_ID " +
            "    AND trunc(:fromDate) BETWEEN FP.START_DATE AND FP.END_DATE " +
            "     WHERE FD.DELETED_DATE IS NULL " +
            "       AND FD.DOCUMENT_DATE >= NVL(:fromDate, FD.DOCUMENT_DATE) " +
            "       AND FD.ORGANIZATION_ID = :organizationId" +
            " AND FD.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId  "
            , nativeQuery = true)
    String findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(Long documentNumberingTypeId, LocalDateTime fromDate, Long organizationId, Long financialLedgerTypeId);


    @Query(value = " SELECT MAX(DN.DOCUMENT_NUMBER) " +
            "      FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER DN " +
            "        ON FD.ID = DN.FINANCIAL_DOCUMENT_ID " +
            "       AND DN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "       AND DN.DELETED_DATE IS NULL" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP " +
            "    ON FP.ID = FD.FINANCIAL_PERIOD_ID " +
            " AND trunc(:toDate) BETWEEN FP.START_DATE AND FP.END_DATE " +
            "     WHERE FD.DELETED_DATE IS NULL " +
            "       AND FD.DOCUMENT_DATE <= NVL(:toDate, FD.DOCUMENT_DATE) " +
            "       AND FD.ORGANIZATION_ID = :organizationId " +
            " AND FD.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId "
            , nativeQuery = true)
    String findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(Long documentNumberingTypeId, LocalDateTime toDate, Long organizationId, Long financialLedgerTypeId);

    @Query(value = " SELECT FND.id  FROM FNDC.FINANCIAL_DOCUMENT FND " +
            " INNER JOIN FNDC.FINANCIAL_DEPARTMENT FNDP " +
            "    ON FND.FINANCIAL_DEPARTMENT_ID = FNDP.ID " +
            " INNER JOIN FNDC.FINANCIAL_DEPARTMENT_LEDGER FDL " +
            "    ON FNDP.DEPARTMENT_ID = FDL.DEPARTMENT_ID " +
            "   AND FND.FINANCIAL_LEDGER_TYPE_ID = FDL.FINANCIAL_LEDGER_TYPE_ID  " +
            " where FDL.id=:financialDepartmentLedgerId "
            , nativeQuery = true)
    List<Long> usedInFinancialDocument(Long financialDepartmentLedgerId);

    @Query(" SELECT fd.financialPeriod.id,fd.documentDate from FinancialDocument  fd " +
            " where fd.id=:financialDocumentId and fd.deletedDate is null")
    List<Object[]> financialDocumentById(Long financialDocumentId);

    @Query(value = " SELECT FD.ID," +
            "       FS.CODE," +
            "       FD.Automatic_Flag," +
            "       FD.ORGANIZATION_ID," +
            "       FD.Financial_Document_Type_Id," +
            "       FD.Financial_Period_Id," +
            "       FD.Financial_Ledger_Type_Id," +
            "       FD.FINANCIAL_DEPARTMENT_ID," +
            "       FD.DEPARTMENT_ID" +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FS" +
            "    ON FS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID" +
            " WHERE FD.DOCUMENT_NUMBER = :targetDocumentNumber" +
            "   and FD.ORGANIZATION_ID = :organizationId" +
            "   and FD.Financial_Ledger_Type_Id = :financialLedgerTypeId" +
            "   and FD.Financial_Department_Id = :financialDepartmentId" +
            "   and  (:department is null or FD.DEPARTMENT_ID=:departmentId) "
            , nativeQuery = true)
    List<Object[]> findDocumentByDocumentNumberAndCode(String targetDocumentNumber, Long organizationId, Long financialLedgerTypeId, Long financialDepartmentId, Object department, Long departmentId);


    @Query(value = " SELECT FD.AUTOMATIC_FLAG," +
            "       FD.ORGANIZATION_ID," +
            "       FD.FINANCIAL_DOCUMENT_TYPE_ID," +
            "       FD.FINANCIAL_PERIOD_ID," +
            "       FD.FINANCIAL_LEDGER_TYPE_ID," +
            "       FD.FINANCIAL_DEPARTMENT_ID," +
            "       FD.DEPARTMENT_ID  " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " WHERE FD.ID = :documentId "
            , nativeQuery = true)
    List<Object[]> findDocumentByFlagAndOrganAndPeriodId(Long documentId);

    @Query(value = " SELECT FS.CODE " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD " +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FS " +
            "    ON FS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID " +
            " WHERE FD.ID = :documentId "
            , nativeQuery = true)
    String findByFinancialDocumentByDocumentId(Long documentId);

    @Query(value = " SELECT FNDC.Document_Date, " +
            "       FNDC.DOCUMENT_NUMBER, " +
            "       FNDC.FINANCIAL_PERIOD_ID, " +
            "       FNDC.DESCRIPTION " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FNDC " +
            " WHERE fndc.id = :documentId  "
            , nativeQuery = true)
    List<Object[]> findFinancialDocumentById(Long documentId);

    @Query(value = " select t.financial_document_status_id from fndc.financial_document t where t.id=:documentId "
            , nativeQuery = true)
    Long findByFinancialDocumentStatusByDocumentId(Long documentId);

    @Query(value = " select 1" +
            "    from fndc.financial_document fnd" +
            "    inner join fndc.financial_document_item fndi" +
            "    on fnd.id = fndi.financial_document_id" +
            "    where fndi.id = :financialDocumentItemId " +
            "    and fnd.financial_document_status_id = 3 "
            , nativeQuery = true)
    Long findFinancialDocumentByDocumentItemId(Long financialDocumentItemId);

    @Query(value = " select 1" +
            "   from fndc.financial_document fnd" +
            "  inner join fndc.financial_document_number fndn" +
            "     on fnd.id = fndn.financial_document_id" +
            "    and fndn.financial_numbering_type_id = 3" +
            "    and fnd.id = (SELECT DI.FINANCIAL_DOCUMENT_ID" +
            "                    FROM FNDC.FINANCIAL_DOCUMENT_ITEM DI" +
            "                   WHERE DI.ID = :financialDocumentItemId) "
            , nativeQuery = true)
    Long findFinancialDocumentByDocumentItemIdDelete(Long financialDocumentItemId);

    @Query(" SELECT fnd.id from FinancialDocument fnd join FinancialDocumentItem fndi on fnd.id=fndi.financialDocument.id where fndi.id=:financialDocumentItemId ")
    Long getDocumentByIdFinancialDocumentItemId(Long financialDocumentItemId);

    @Query(value = " select 1" +
            "   from fndc.financial_document fd" +
            "  where (fd.document_date != :date" +
            "     or organization_id != :organizationId" +
            "     or financial_document_type_id != :financialDocumentTypeId" +
            "     or financial_period_id != :financialPeriodId" +
            "     or financial_ledger_type_id != :financialLedgerTypeId" +
            "     or financial_department_id != :financialDepartmentId) " +
            "  and fd.id = :id "
            , nativeQuery = true)
    Long findFinancialDocumentByDateAndDepartment(LocalDateTime date, Long organizationId, Long financialDocumentTypeId,
                                                  Long financialPeriodId, Long financialLedgerTypeId, Long financialDepartmentId, Long id);

    @Query(value = " SELECT FD.ID " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD " +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH LM " +
            "    ON LM.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
            "   AND LM.ID = :financialLedgerMonthId " +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "    ON LP.ID = LM.FINANCIAL_LEDGER_PERIOD_ID " +
            "   AND LP.FINANCIAL_PERIOD_ID = FD.FINANCIAL_PERIOD_ID " +
            "   AND LP.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_TYPE LT " +
            "    ON LT.ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
            "   AND LT.ORGANIZATION_ID = :organizationId " +
            " INNER JOIN FNPR.FINANCIAL_MONTH FM " +
            "    ON FM.ID = LM.FINANCIAL_MONTH_ID" +
            " WHERE FD.ORGANIZATION_ID = LT.ORGANIZATION_ID " +
            "   AND FD.DOCUMENT_DATE BETWEEN FM.START_DATE AND FM.END_DATE " +
            " ORDER BY FD.DOCUMENT_DATE , FD.DOCUMENT_NUMBER , FD.ID "
            , nativeQuery = true)
    List<Long> findByListFinancialDocumentId(Long financialLedgerMonthId, Long organizationId);

    @Query(" select 1 from FinancialDocument fd " +
            " where fd.financialDocumentStatus.id != 3 " +
            " and fd.id in (:documentIdList)  ")
    List<Long> getFinancialDocumentListId(List<Long> documentIdList);

    @Query(value = " SELECT 1 " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            "  LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER FN" +
            "    ON FN.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "   AND FN.FINANCIAL_NUMBERING_TYPE_ID = 3" +
            " WHERE (FD.PERMANENT_DOCUMENT_NUMBER IS NOT NULL OR FN.ID IS NOT NULL)" +
            "   AND FD.ID = :financialDocumentId "
            , nativeQuery = true)
    Long getFinancialDocumentByNumberAndId(Long financialDocumentId);

    @Procedure(procedureName = "fndc.FRD_BFS_FNDC_TRANSFER_PKG.INSERT_DOCUMENT_DATA", outputParameterName = "P_ERR")
    String CopyDocFromOldSystem(@Param("P_DCHT_ID") Long P_DCHT_ID, @Param("P_DCHT_NUM") String P_DCHT_NUM);


    @Query(value = " SELECT MIN(FD.PERMANENT_DOCUMENT_NUMBER)" +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH LM" +
            "    ON LM.ID = :financialLedgerMonthId " +
            "   AND LM.FINANCIAL_LEDGER_PERIOD_ID = :financialLedgerPeriodId " +
            " INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "    ON FM.ID = LM.FINANCIAL_MONTH_ID" +
            "   AND FM.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            " WHERE FD.DOCUMENT_DATE BETWEEN FM.START_DATE AND FM.END_DATE" +
            "   AND FD.ORGANIZATION_ID = :organizationId  " +
            " AND FD.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId "
            , nativeQuery = true)
    String findByDocumentIdAndLedgerMonth(Long financialLedgerMonthId, Long financialLedgerPeriodId, Long financialPeriodId, Long organizationId, Long financialLedgerTypeId);


    @Query(value = " SELECT FD.ID" +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " WHERE FD.ORGANIZATION_ID = :organizationId " +
            "   AND FD.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "   AND FD.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "   AND FD.PERMANENT_DOCUMENT_NUMBER = :minDocNumber "
            , nativeQuery = true)
    Long findByDocumentIdAndLedgerMonthMinDocId(Long organizationId, Long financialPeriodId, Long financialLedgerTypeId, String minDocNumber);

    @Query(value = " SELECT distinct 1 " +
            "                  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            "                 INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH LM" +
            "                    ON LM.ID = :financialLedgerMonthId" +
            "                   AND LM.FINANCIAL_LEDGER_PERIOD_ID = :financialLedgerPeriodId" +
            "                 INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "                    ON FM.ID = LM.FINANCIAL_MONTH_ID" +
            "                   AND FM.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "                 WHERE FD.DOCUMENT_DATE BETWEEN FM.START_DATE AND FM.END_DATE" +
            "                   AND FD.ORGANIZATION_ID = :organizationId " +
            " AND FD.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId "
            , nativeQuery = true)
    Long findByDocumentByPeriodIdAndOrgId(Long financialLedgerMonthId, Long financialLedgerPeriodId, Long financialPeriodId, Long organizationId, Long financialLedgerTypeId);

    @Query(value = " SELECT distinct 1 " +
            "              FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "             INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "                ON LP.FINANCIAL_PERIOD_ID = FD.FINANCIAL_PERIOD_ID " +
            "               AND LP.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
            "               AND LP.ID = :financialLedgerPeriodId " +
            "             INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH LM " +
            "                ON LM.FINANCIAL_LEDGER_PERIOD_ID = LP.ID" +
            "   AND (:financialLedgerMonth is null or LM.ID =:financialLedgerMonthId )" +
            "             INNER JOIN FNPR.FINANCIAL_PERIOD FP" +
            "                ON FP.ID = FD.FINANCIAL_PERIOD_ID" +
            "             INNER JOIN FNPR.FINANCIAL_MONTH FM" +
            "                ON FM.FINANCIAL_PERIOD_ID = FD.FINANCIAL_PERIOD_ID" +
            "               AND FM.ID = LM.FINANCIAL_MONTH_ID" +
            "              LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER FDN" +
            "                ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "               AND FDN.FINANCIAL_NUMBERING_TYPE_ID = 3" +
            "             WHERE (FDN.ID IS NULL OR FD.PERMANENT_DOCUMENT_NUMBER IS NULL)" +
            "               AND FD.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "               AND FD.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "               AND FD.DOCUMENT_DATE BETWEEN FM.START_DATE AND FM.END_DATE" +
            "               AND FD.ORGANIZATION_ID = :organizationId  "
            , nativeQuery = true)
    Long findByDocumentByPeriodIdAndOrgIdAndLedgerType(Long financialLedgerPeriodId, Object financialLedgerMonth, Long financialLedgerMonthId, Long financialPeriodId, Long financialLedgerTypeId, Long organizationId);

    @Query(value = " SELECT 1 " +
            "  FROM FNDC.FINANCIAL_DOCUMENT T " +
            " WHERE T.FINANCIAL_DOCUMENT_TYPE_ID = 73 " +
            "   AND T.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "   AND T.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "   AND T.ORGANIZATION_ID = :organizationId "
            , nativeQuery = true)
    List<Long> getFinancialDocumentByPeriodId(Long financialLedgerTypeId, Long financialPeriodId, Long organizationId);

    @Query(value = " WITH CENTRIC_QRY AS" +
            " (SELECT FD.ID," +
            "         DV.CENTRIC_ACCOUNT_ID," +
            "         FDI.ID AS FDI_ID," +
            "         FD.DOCUMENT_NUMBER," +
            "         FD.DOCUMENT_DATE," +
            "         4 AS ERROR_TYPE," +
            "         NULL LIST_SEQ," +
            "         ' عدم همخوانی تمرکز و حساب '  AS ERROR_MESSAGE " +
            "    FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "   INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI " +
            "      ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID " +
            "   INNER JOIN FNAC.ACCOUNT_DEFAULT_VALUE DV " +
            "      ON DV.FINANCIAL_ACCOUNT_ID = FDI.FINANCIAL_ACCOUNT_ID" +
            "   WHERE FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate)" +
            "     AND FD.ORGANIZATION_ID = :organizationId" +
            "     AND FD.FINANCIAL_DEPARTMENT_ID =" +
            "         NVL(:financialDepartmentId, FD.FINANCIAL_DEPARTMENT_ID)" +
            "     AND FD.DEPARTMENT_ID = NVL(:departmentId, FD.DEPARTMENT_ID)" +
            "     AND FD.FINANCIAL_LEDGER_TYPE_ID =" +
            "         NVL(:financialLedgerTypeId, FD.FINANCIAL_LEDGER_TYPE_ID)" +
            "  )" +
            " SELECT FD.ID," +
            "       FD.DOCUMENT_NUMBER," +
            "       FD.DOCUMENT_DATE," +
            "       1 AS ERROR_TYPE," +
            "       'ردیفهای : ' || LISTAGG(FDI.SEQUENCE_NUMBER, ',') LIST_SEQ, " +
            "       'وجود ردیفهای دارای بدهکار و بستانکار به صورت همزمان ' AS ERROR_MESSAGE " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD " +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI " +
            "    ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID" +
            " WHERE FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate)" +
            "   AND FD.ORGANIZATION_ID = :organizationId" +
            "   AND FD.FINANCIAL_DEPARTMENT_ID =" +
            "       NVL(:financialDepartmentId, FD.FINANCIAL_DEPARTMENT_ID)" +
            "   AND FD.DEPARTMENT_ID = NVL(:departmentId, FD.DEPARTMENT_ID)" +
            "   AND FD.FINANCIAL_LEDGER_TYPE_ID =" +
            "       NVL(:financialLedgerTypeId, FD.FINANCIAL_LEDGER_TYPE_ID)" +
            "   AND FDI.DEBIT_AMOUNT > 0" +
            "   AND FDI.CREDIT_AMOUNT > 0" +
            " GROUP BY FD.ID, FD.DOCUMENT_NUMBER, FD.DOCUMENT_DATE" +
            " UNION " +
            " SELECT FD.ID," +
            "       FD.DOCUMENT_NUMBER," +
            "       FD.DOCUMENT_DATE," +
            "       2 AS ERROR_TYPE," +
            "       'ردیفهای : ' || LISTAGG(FDI.SEQUENCE_NUMBER, ',') LIST_SEQ, " +
            "       ' وجود ردیفهای بدون مبلغ '  AS ERROR_MESSAGE " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "    ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID" +
            " WHERE FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate) AND" +
            " FD.ORGANIZATION_ID = :organizationId AND" +
            " FD.FINANCIAL_DEPARTMENT_ID =" +
            " NVL(:financialDepartmentId, FD.FINANCIAL_DEPARTMENT_ID) AND" +
            " FD.DEPARTMENT_ID = NVL(:departmentId, FD.DEPARTMENT_ID) AND" +
            " FD.FINANCIAL_LEDGER_TYPE_ID =" +
            " NVL(:financialLedgerTypeId, FD.FINANCIAL_LEDGER_TYPE_ID) AND" +
            " FDI.DEBIT_AMOUNT = 0 AND FDI.CREDIT_AMOUNT = 0 AND" +
            " (FDI.DEBIT_AMOUNT IS NULL OR FDI.CREDIT_AMOUNT IS NULL)" +
            " GROUP BY FD.ID, FD.DOCUMENT_NUMBER, FD.DOCUMENT_DATE" +
            " UNION " +
            " SELECT FD.ID," +
            "       FD.DOCUMENT_NUMBER," +
            "       FD.DOCUMENT_DATE," +
            "       3 AS ERROR_TYPE," +
            "       ' ردیف : ' || LISTAGG(FDI.SEQUENCE_NUMBER, ',') LIST_SEQ, " +
            "       ' وجود ردیف با مبلغ منفی ' AS ERROR_MESSAGE " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "    ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID" +
            " WHERE FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate) AND" +
            " FD.ORGANIZATION_ID = :organizationId AND" +
            " FD.FINANCIAL_DEPARTMENT_ID =" +
            " NVL(:financialDepartmentId, FD.FINANCIAL_DEPARTMENT_ID) AND" +
            " FD.DEPARTMENT_ID = NVL(:departmentId, FD.DEPARTMENT_ID) AND" +
            " FD.FINANCIAL_LEDGER_TYPE_ID =" +
            " NVL(:financialLedgerTypeId, FD.FINANCIAL_LEDGER_TYPE_ID) AND" +
            " (FDI.DEBIT_AMOUNT < 0 OR FDI.CREDIT_AMOUNT < 0)" +
            " GROUP BY FD.ID, FD.DOCUMENT_NUMBER, FD.DOCUMENT_DATE" +
            " UNION " +
            " SELECT CENTRIC_QRY.ID," +
            "       DOCUMENT_NUMBER," +
            "       DOCUMENT_DATE," +
            "       ERROR_TYPE," +
            "       TO_CHAR(DI.SEQUENCE_NUMBER)," +
            "       ERROR_MESSAGE" +
            "  FROM CENTRIC_QRY" +
            " INNER JOIN fndc.FINANCIAL_DOCUMENT_ITEM DI" +
            "    ON DI.ID = FDI_ID" +
            " WHERE ((NOT EXISTS" +
            "  (SELECT 1" +
            "      FROM CENTRIC_QRY DV" +
            "     WHERE DI.CENTRIC_ACCOUNT_ID_1 = DV.CENTRIC_ACCOUNT_ID)) OR" +
            " (NOT EXISTS" +
            "  (SELECT 1" +
            "      FROM CENTRIC_QRY DV" +
            "     WHERE DI.CENTRIC_ACCOUNT_ID_2 = DV.CENTRIC_ACCOUNT_ID)) OR" +
            " (NOT EXISTS" +
            "  (SELECT 1" +
            "      FROM CENTRIC_QRY DV" +
            "     WHERE DI.CENTRIC_ACCOUNT_ID_3 = DV.CENTRIC_ACCOUNT_ID)) OR" +
            " (NOT EXISTS" +
            "  (SELECT 1" +
            "      FROM CENTRIC_QRY DV" +
            "     WHERE DI.CENTRIC_ACCOUNT_ID_4 = DV.CENTRIC_ACCOUNT_ID)) OR" +
            " (NOT EXISTS" +
            "  (SELECT 1" +
            "      FROM CENTRIC_QRY DV" +
            "     WHERE DI.CENTRIC_ACCOUNT_ID_5 = DV.CENTRIC_ACCOUNT_ID)) OR" +
            " (NOT EXISTS" +
            "  (SELECT 1" +
            "      FROM CENTRIC_QRY DV" +
            "     WHERE DI.CENTRIC_ACCOUNT_ID_6 = DV.CENTRIC_ACCOUNT_ID)))" +
            " UNION " +
            " SELECT FD.ID," +
            "       FD.DOCUMENT_NUMBER," +
            "       FD.DOCUMENT_DATE," +
            "       5 AS ERROR_TYPE," +
            "       'ردیفهای : ' || LISTAGG(FDI.SEQUENCE_NUMBER, ',') LIST_SEQ, " +
            "       ' سطح آخر نبودن حساب انتخاب شده روی ردیف ' AS ERROR_MESSAGE " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "    ON FDI.FINANCIAL_DOCUMENT_ID = FD.ID" +
            " INNER JOIN FNAC.FINANCIAL_ACCOUNT FA" +
            "    ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID" +
            " INNER JOIN FNAC.FINANCIAL_ACCOUNT_STRUCTURE FS" +
            "    ON FA.FINANCIAL_ACCOUNT_STRUCTURE_ID = FS.ID" +
            " WHERE EXISTS " +
            " (SELECT 1" +
            "    FROM FNAC.FINANCIAL_ACCOUNT FIAC_INNER" +
            "   WHERE FIAC_INNER.FINANCIAL_ACCOUNT_PARENT_ID = FA.ID" +
            "     AND FIAC_INNER.DELETED_DATE IS NULL) AND" +
            " FA.DISABLE_DATE IS NULL AND FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND " +
            " trunc(:toDate) AND FD.ORGANIZATION_ID = :organizationId AND" +
            " FD.FINANCIAL_DEPARTMENT_ID =" +
            " NVL(:financialDepartmentId, FD.FINANCIAL_DEPARTMENT_ID) AND" +
            " FD.DEPARTMENT_ID = NVL(:departmentId, FD.DEPARTMENT_ID) AND" +
            " FD.FINANCIAL_LEDGER_TYPE_ID =" +
            " NVL(:financialLedgerTypeId, FD.FINANCIAL_LEDGER_TYPE_ID)" +
            " GROUP BY FD.ID, FD.DOCUMENT_NUMBER, FD.DOCUMENT_DATE" +
            " UNION " +
            " SELECT FD.ID," +
            "       FD.DOCUMENT_NUMBER," +
            "       FD.DOCUMENT_DATE," +
            "       6 AS ERROR_TYPE," +
            "       'ردیفهای : ' || LISTAGG(FDI.SEQUENCE_NUMBER, ',') LIST_SEQ, " +
            "       ' اطلاعات ارزی برای ردیفهای ارزی کامل نیست '  AS ERROR_MESSAGE " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "    ON FDI.FINANCIAL_DOCUMENT_ID = FD.ID" +
            " INNER JOIN FNAC.FINANCIAL_ACCOUNT FA" +
            "    ON FDI.FINANCIAL_ACCOUNT_ID = FA.ID AND FA.EXCHANGE_FLAG = 1" +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM_CURRENCY DC" +
            "    ON DC.FINANCIAL_DOCUMENT_ITEM_ID = FDI.ID" +
            " WHERE ((DC.FOREIGN_DEBIT_AMOUNT IS NULL AND" +
            " DC.FOREIGN_CREDIT_AMOUNT IS NULL) OR DC.EXCHANGE_RATE IS NULL OR" +
            " DC.MONEY_PRICING_REFRENCE_ID IS NULL OR DC.MONEY_TYPE_ID IS NULL) AND" +
            " FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate) AND" +
            " FD.ORGANIZATION_ID = :organizationId AND" +
            " FD.FINANCIAL_DEPARTMENT_ID =" +
            " NVL(:financialDepartmentId, FD.FINANCIAL_DEPARTMENT_ID) AND" +
            " FD.DEPARTMENT_ID = NVL(:departmentId, FD.DEPARTMENT_ID) AND" +
            " FD.FINANCIAL_LEDGER_TYPE_ID =" +
            " NVL(:financialLedgerTypeId, FD.FINANCIAL_LEDGER_TYPE_ID)" +
            " GROUP BY FD.ID, FD.DOCUMENT_NUMBER, FD.DOCUMENT_DATE" +
            " UNION " +
            " SELECT FD.ID," +
            "       FD.DOCUMENT_NUMBER," +
            "       FD.DOCUMENT_DATE," +
            "       7 AS ERROR_TYPE," +
            "       'ردیفهای : ' || LISTAGG(FDI.SEQUENCE_NUMBER, ',') LIST_SEQ, " +
            "       ' عدم همخوانی نوع ارز '  AS ERROR_MESSAGE " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "    ON FDI.FINANCIAL_DOCUMENT_ID = FD.ID" +
            " INNER JOIN FNAC.FINANCIAL_ACCOUNT FA" +
            "    ON FDI.FINANCIAL_ACCOUNT_ID = FA.ID AND FA.EXCHANGE_FLAG = 1" +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM_CURRENCY DC" +
            "    ON DC.FINANCIAL_DOCUMENT_ITEM_ID = FDI.ID" +
            " WHERE NOT EXISTS" +
            " (SELECT 1" +
            "    FROM FNAC.ACCOUNT_MONEY_TYPE MT" +
            "   WHERE MT.FINANCIAL_ACCOUNT_ID = FA.ID" +
            "     AND DC.MONEY_TYPE_ID = MT.MONEY_TYPE_ID) AND" +
            " FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate) AND" +
            " FD.ORGANIZATION_ID = :organizationId AND" +
            " FD.FINANCIAL_DEPARTMENT_ID =" +
            " NVL(:financialDepartmentId, FD.FINANCIAL_DEPARTMENT_ID) AND" +
            " FD.DEPARTMENT_ID = NVL(:departmentId, FD.DEPARTMENT_ID) AND" +
            " FD.FINANCIAL_LEDGER_TYPE_ID =" +
            " NVL(:financialLedgerTypeId, FD.FINANCIAL_LEDGER_TYPE_ID)" +
            " GROUP BY FD.ID, FD.DOCUMENT_NUMBER, FD.DOCUMENT_DATE" +
            " UNION " +
            " SELECT FD.ID," +
            "       FD.DOCUMENT_NUMBER," +
            "       FD.DOCUMENT_DATE," +
            "       8 AS ERROR_TYPE," +
            "       NULL LIST_SEQ," +
            "       ' سند بالانس نیست ' AS ERROR_MESSAGE " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "    ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID" +
            " WHERE FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate) AND" +
            " FD.ORGANIZATION_ID = :organizationId AND" +
            " FD.FINANCIAL_DEPARTMENT_ID =" +
            " NVL(:financialDepartmentId, FD.FINANCIAL_DEPARTMENT_ID) AND" +
            " FD.DEPARTMENT_ID = NVL(:departmentId, FD.DEPARTMENT_ID) AND" +
            " FD.FINANCIAL_LEDGER_TYPE_ID =" +
            " NVL(:financialLedgerTypeId, FD.FINANCIAL_LEDGER_TYPE_ID)" +
            " GROUP BY FD.ID, FD.DOCUMENT_NUMBER, FD.DOCUMENT_DATE" +
            " HAVING SUM (FDI.DEBIT_AMOUNT) - SUM (FDI.CREDIT_AMOUNT) != 0" +
            " UNION " +
            " SELECT ID," +
            "       DOCUMENT_NUMBER," +
            "       DOCUMENT_DATE," +
            "       9 AS ERROR_TYPE," +
            "       'ردیفهای : ' || LISTAGG(LIST_SEQ, ',') LIST_SEQ, " +
            "       ' مبالغ ردیف با بدهکار / بستانکار ارزی همخوانی ندارد '  AS ERROR_MESSAGE " +
            "  FROM (SELECT FD.ID," +
            "               FD.DOCUMENT_NUMBER," +
            "               FD.DOCUMENT_DATE," +
            "               LISTAGG(FDI.SEQUENCE_NUMBER, ',') LIST_SEQ" +
            "          FROM FNDC.FINANCIAL_DOCUMENT FD" +
            "         INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "            ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID" +
            "         INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM_CURRENCY FDIC" +
            "            ON FDI.ID = FDIC.FINANCIAL_DOCUMENT_ITEM_ID" +
            "         WHERE FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate)" +
            "           AND FD.ORGANIZATION_ID = :organizationId" +
            "           AND FD.FINANCIAL_DEPARTMENT_ID =" +
            "               NVL(:financialDepartmentId, FD.FINANCIAL_DEPARTMENT_ID)" +
            "           AND FD.DEPARTMENT_ID = NVL(:departmentId, FD.DEPARTMENT_ID)" +
            "           AND FD.FINANCIAL_LEDGER_TYPE_ID =" +
            "               NVL(:financialLedgerTypeId, FD.FINANCIAL_LEDGER_TYPE_ID)" +
            "         GROUP BY FD.ID," +
            "                  FD.DOCUMENT_NUMBER," +
            "                  FD.DOCUMENT_DATE," +
            "                  FDI.DEBIT_AMOUNT," +
            "                  FDI.CREDIT_AMOUNT," +
            "                  FDI.SEQUENCE_NUMBER" +
            "        HAVING((NVL(FDI.DEBIT_AMOUNT, 0) != 0 AND SUM(NVL(FDIC.FOREIGN_DEBIT_AMOUNT, 0)) = 0) OR (NVL(FDI.DEBIT_AMOUNT, 0) = 0 AND SUM(NVL(FDIC.FOREIGN_DEBIT_AMOUNT, 0)) != 0) OR (NVL(FDI.CREDIT_AMOUNT, 0) != 0 AND SUM(NVL(FDIC.FOREIGN_CREDIT_AMOUNT, 0)) = 0) OR (NVL(FDI.CREDIT_AMOUNT, 0) = 0 AND SUM(NVL(FDIC.FOREIGN_CREDIT_AMOUNT, 0)) != 0) OR (NVL(FDI.CREDIT_AMOUNT, 0) != 0 AND NVL(FDI.DEBIT_AMOUNT, 0) != 0)))" +
            " GROUP BY ID, DOCUMENT_NUMBER, DOCUMENT_DATE" +
            " UNION " +
            " SELECT FD.ID," +
            "        DOCUMENT_NUMBER," +
            "        DOCUMENT_DATE," +
            "        10 AS ERROR_TYPE," +
            "        'ردیفهای : ' || LISTAGG(FDI.SEQUENCE_NUMBER, ',') LIST_SEQ, " +
            "        ' عدم همخوانی کد های تمرکز با مراجع' AS ERROR_MESSAGE " +
            "   FROM FNDC.FINANCIAL_DOCUMENT FD" +
            "  INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "     ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID" +
            "   LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN" +
            "     ON FDI.CENTRIC_ACCOUNT_ID_1 = CN.ID" +
            "   LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN2" +
            "     ON FDI.CENTRIC_ACCOUNT_ID_2 = CN2.ID" +
            "   LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN3" +
            "     ON FDI.CENTRIC_ACCOUNT_ID_3 = CN3.ID" +
            "   LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN4" +
            "     ON FDI.CENTRIC_ACCOUNT_ID_4 = CN4.ID" +
            "   LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN5" +
            "     ON FDI.CENTRIC_ACCOUNT_ID_5 = CN5.ID" +
            "   LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CN6" +
            "     ON FDI.CENTRIC_ACCOUNT_ID_6 = CN6.ID" +
            "  WHERE " +
            "  FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate)" +
            "           AND FD.ORGANIZATION_ID = :organizationId" +
            "           AND FD.FINANCIAL_DEPARTMENT_ID =" +
            "               NVL(:financialDepartmentId, FD.FINANCIAL_DEPARTMENT_ID)" +
            "           AND FD.DEPARTMENT_ID = NVL(:departmentId, FD.DEPARTMENT_ID)" +
            "           AND FD.FINANCIAL_LEDGER_TYPE_ID =" +
            "               NVL(:financialLedgerTypeId, FD.FINANCIAL_LEDGER_TYPE_ID) and " +
            "  ((FDI.CENTRIC_ACCOUNT_ID_1 IS NOT NULL AND" +
            "        FDI.CENTRIC_ACCOUNT_ID_2 IS NOT NULL AND" +
            "        FDI.CENTRIC_ACCOUNT_ID_1 != NVL(CN2.PARENT_CENTRIC_ACCOUNT_ID, 0)) OR" +
            "        (FDI.CENTRIC_ACCOUNT_ID_2 IS NOT NULL AND" +
            "        FDI.CENTRIC_ACCOUNT_ID_3 IS NOT NULL AND" +
            "        FDI.CENTRIC_ACCOUNT_ID_2 != NVL(CN3.PARENT_CENTRIC_ACCOUNT_ID, 0)) OR" +
            "        (FDI.CENTRIC_ACCOUNT_ID_3 IS NOT NULL AND" +
            "        FDI.CENTRIC_ACCOUNT_ID_4 IS NOT NULL AND" +
            "        FDI.CENTRIC_ACCOUNT_ID_3 != NVL(CN4.PARENT_CENTRIC_ACCOUNT_ID, 0)) OR" +
            "        (FDI.CENTRIC_ACCOUNT_ID_4 IS NOT NULL AND" +
            "        FDI.CENTRIC_ACCOUNT_ID_5 IS NOT NULL AND" +
            "        FDI.CENTRIC_ACCOUNT_ID_4 != NVL(CN5.PARENT_CENTRIC_ACCOUNT_ID, 0)) OR" +
            "        (FDI.CENTRIC_ACCOUNT_ID_5 IS NOT NULL AND" +
            "        FDI.CENTRIC_ACCOUNT_ID_6 IS NOT NULL AND" +
            "        FDI.CENTRIC_ACCOUNT_ID_5 != NVL(CN6.PARENT_CENTRIC_ACCOUNT_ID, 0)))" +
            " GROUP BY FD.ID, DOCUMENT_NUMBER, DOCUMENT_DATE "
            , nativeQuery = true)
    Page<Object[]> findByFinancialDocument(LocalDateTime fromDate, LocalDateTime toDate, Long organizationId, Long financialDepartmentId,
                                           Long departmentId, Long financialLedgerTypeId, Pageable pageable);
}
