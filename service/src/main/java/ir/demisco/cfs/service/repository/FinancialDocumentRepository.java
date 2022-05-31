package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface FinancialDocumentRepository extends JpaRepository<FinancialDocument, Long> {

    @Query(value = " SELECT FIDC.ID," +
            "       FIDC.DOCUMENT_DATE," +
            "       FIDC.DESCRIPTION," +
            "       FIDC.DOCUMENT_NUMBER," +
            "       FIDC.FINANCIAL_DOCUMENT_TYPE_ID," +
            "       FNDT.DESCRIPTION AS FINANCIAL_DOCUMENT_TYPE_DESCRIPTION," +
            "       FIDC.DESCRIPTION AS FULL_DESCRIPTION," +
            "       SUM(FNDI.DEBIT_AMOUNT) AS SUM_DEBIT_AMOUNT," +
            "       SUM(FNDI.CREDIT_AMOUNT) AS SUM_CREDIT_AMOUNT," +
            "       USR.ID AS USERID," +
            "       USR.NICK_NAME AS USERNAME," +
            "       FIDC.FINANCIAL_DOCUMENT_STATUS_ID," +
            "       DS.NAME DOCUMENT_STATUS_NAME," +
            "       DS.CODE DOCUMENT_STATUS_CODE" +
            "  FROM fndc.FINANCIAL_DOCUMENT FIDC" +
            "  INNER JOIN FNDC.FINANCIAL_DOCUMENT_TYPE FNDT " +
            "    ON FIDC.FINANCIAL_DOCUMENT_TYPE_ID = FNDT.ID " +
            "   AND FNDT.DELETED_DATE IS NULL " +
            "  INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FNDI " +
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
            " TABLE(fnsc.PKG_FINANCIAL_SECURITY.GET_PERMISSION(:organizationId," +
            "                                 :activityCode," +
            "                                 fidc.financial_period_id," +
            "                                 FNDT.ID," +
            "                                 :creatorUserId," +
            "                                 fidc.financial_department_id,                                                  " +
            "       fidc.financial_ledger_type_id," +
            "                                :departmentId," +
            "                                 :userId)) FNSC " +
            " where fidc.document_date >= :startDate " +
            "   And fidc.document_date <= :endDate " +
            " AND FNDI.CREDIT_AMOUNT = CASE " +
            "         WHEN :priceTypeId = 1 THEN" +
            "          0 " +
            "         ELSE" +
            "          FNDI.CREDIT_AMOUNT " +
            "       END" +
            "   AND FNDI.DEBIT_AMOUNT = CASE " +
            "         WHEN :priceTypeId = 2 THEN " +
            "          0 " +
            "         ELSE" +
            "          FNDI.DEBIT_AMOUNT " +
            "       END " +
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
            "   and (:documentUser is null or (fidc.creator_id = :documentUserId or " +
            "       fidc.last_modifier_id = :documentUserId)) " +
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
            "       (fndi.credit_amount <= :toPriceAmount + (:toPriceAmount * nvl(:tolerance, 0)) / 100.0)))))" +
            " and (:financialDocumentType is null or FIDC.FINANCIAL_DOCUMENT_TYPE_ID =:financialDocumentTypeId )" +
            " and FNSC.SEC_RESULT = 1 " +
            "  group by fidc.id,usr.id,usr.nick_name,document_date,fidc.description,fidc.document_number,fidc.financial_document_type_id,fndt.description," +
            " FINANCIAL_DOCUMENT_STATUS_ID, " +
            "          DS.NAME , " +
            "          DS.CODE  " +
            " order by  fidc.document_date desc "
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
            " TABLE(fnsc.PKG_FINANCIAL_SECURITY.GET_PERMISSION(:organizationId," +
            "                                 :activityCode," +
            "                                 fidc.financial_period_id," +
            "                                 FNDT.ID," +
            "                                 :creatorUserId," +
            "                                 fidc.financial_department_id,           " +
            "                                 fidc.financial_ledger_type_id," +
            "                                :departmentId," +
            "                                 :userId)) FNSC " +
            " where fidc.document_date >= :startDate " +
            "   And fidc.document_date <= :endDate " +
            " AND FNDI.CREDIT_AMOUNT = CASE " +
            "         WHEN :priceTypeId = 1 THEN" +
            "          0 " +
            "         ELSE" +
            "          FNDI.CREDIT_AMOUNT " +
            "       END" +
            "   AND FNDI.DEBIT_AMOUNT = CASE " +
            "         WHEN :priceTypeId = 2 THEN " +
            "          0 " +
            "         ELSE" +
            "          FNDI.DEBIT_AMOUNT " +
            "       END " +
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
            "   and (:documentUser is null or (fidc.creator_id = :documentUserId or " +
            "       fidc.last_modifier_id = :documentUserId)) " +
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
            " and (:financialDocumentType is null or FIDC.FINANCIAL_DOCUMENT_TYPE_ID =:financialDocumentTypeId ) " +
            "  and FNSC.SEC_RESULT = 1" +
            "  group by fidc.id,usr.id,usr.nick_name,document_date,fidc.description,fidc.document_number,financial_document_type_id,fndt.description, " +
            "   FINANCIAL_DOCUMENT_STATUS_ID, " +
            "                      DS.NAME , " +
            "                     DS.CODE " +
            " order by  fidc.document_date desc  "
            , nativeQuery = true)
    List<Object[]> getFinancialDocumentList(Long organizationId, String activityCode, Long creatorUserId, Long departmentId, Long userId, LocalDateTime startDate, LocalDateTime endDate, Long priceTypeId, Long financialNumberingTypeId, Object fromNumber, Long fromNumberId
            , Object toNumber, Long toNumberId, String description, Object fromAccount, Long fromAccountCode, Object toAccount,
                                            Long toAccountCode, Object centricAccount, Long centricAccountId,
                                            Object centricAccountType, Long centricAccountTypeId, Object documentUser, Long documentUserId,
                                            Object priceType, Object fromPrice, Long fromPriceAmount, Object toPrice, Long toPriceAmount,
                                            Double tolerance, List<Long> documentStatusId, Object financialDocumentType, Long financialDocumentTypeId);

    @Query("select fd from FinancialDocument fd join fd.financialPeriod   fp where fp.financialPeriodStatus.id=1 and fd.id=:financialDocumentId")
    FinancialDocument getActivePeriodInDocument(Long financialDocumentId);

    @Query("select to_char(:date, 'yyyymmdd', 'NLS_CALENDAR=persian') ||" +
            "      nvl(lpad(max(to_number(substr(to_char(fd.documentNumber), 9, 3)) + 1),3,'0'),'001')" +
            " from FinancialDocument fd" +
            " where fd.organization.id=:organizationId" +
            "       and (fd.financialPeriod.id=:financialPeriodId)" +
            "       and  substr(to_char(fd.documentNumber),0,8)=to_char(:date, 'yyyymmdd', 'NLS_CALENDAR=persian')")
    String getDocumentNumber(Long organizationId, LocalDateTime date, Long financialPeriodId);

    @Query("select fd from FinancialDocument fd where fd.id=:financialDocumentId and fd.deletedDate is null")
    FinancialDocument getActiveDocumentById(Long financialDocumentId);

    @Query(" select fd from FinancialDocument fd join fd.financialPeriod fp where fp.financialPeriodStatus.id=1 and fd.id=:financialDocumentId " +
            " and exists ( select fm from FinancialMonth fm " +
            "              join FinancialMonthType fmt on fmt.id=fm.financialMonthType.id " +
            "              join FinancialPeriodType fpt on fpt.id=fmt.financialPeriodType.id " +
            "              join FinancialPeriodTypeAssign fpts on fpts.financialPeriodType.id=fpt.id " +
            "              join FinancialPeriod fp on fp.financialPeriodTypeAssign.id=fpts.id " +
            "              join FinancialDocument fd on fd.financialPeriod.id=fp.id " +
            "            where fd.id=:financialDocumentId " +
            "                  and fm.financialMonthStatus.id=1" +
            "                  and case fpt.calendarTypeId when 2 then extract(month from TO_DATE(TO_char(fd.documentDate,'mm/dd/yyyy'),'mm/dd/yyyy'))" +
            "                                              when 1 then TO_NUMBER(substr(TO_CHAR(TO_DATE(TO_char(fd.documentDate,'mm/dd/yyyy'),'mm/dd/yyyy'),'yyyy/mm/dd','NLS_CALENDAR=persian'),6,2)) " +
            "                       end = case when fpt.calendarYearFlag = 1 then (fpt.fromMonth + (fmt.monthNumber-1)) " +
            "                       else  " +
            "                       case when (fpt.fromMonth + (fmt.monthNumber-1)) > 12 then (fpt.fromMonth + (fmt.monthNumber-13)) else (fpt.fromMonth+(fmt.monthNumber-1)) end" +
            "                       end" +
            "                     )")
    FinancialDocument getActivePeriodAndMontInDocument(Long financialDocumentId);

    @Query(value = " SELECT " +
            "         NF_ID, " +
            "         FIRST_SERIAL, " +
            "         TRANSLATED_RESETER, " +
            "         NF_SERIAL_LENGTH, " +
            "         1 SERIAL_RESETER " +
            "    FROM (SELECT NF.ID NF_ID, " +
            "                 NF.FIRST_SERIAL - 1 FIRST_SERIAL, " +
            "                 REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(NF.RESETER,'$DAT'," +
            "                              TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE,'mm/dd/yyyy'),'mm/dd/yyyy'),'yyyymmdd', " +
            "                                                                 'NLS_CALENDAR=persian')),'$LEG', " +
            "                                                 (SELECT LT.CODE " +
            "                                                    FROM fndc.FINANCIAL_LEDGER_TYPE LT " +
            "                                                   WHERE LT.ID = " +
            "                                                         FD.FINANCIAL_LEDGER_TYPE_ID)),'$DEP', " +
            "                                         (SELECT DP.CODE " +
            "                                            FROM FNDC.FINANCIAL_DEPARTMENT DP " +
            "                                           WHERE DP.ID = FD.FINANCIAL_DEPARTMENT_ID)), '$ORG', " +
            "                                 (SELECT OG.CODE " +
            "                                    FROM FNDC.FINANCIAL_ORGANIZATION OG " +
            "                                   WHERE OG.ORGANIZATION_ID =:organizationId)), '$PRI'," +
            "                         (SELECT PR.CODE " +
            "                            FROM FNPR.FINANCIAL_PERIOD PR " +
            "                           WHERE PR.ID = FD.FINANCIAL_PERIOD_ID)) TRANSLATED_RESETER, " +
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
            "                 REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(NFT.CODE,'$DAT', " +
            "                         TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE, 'mm/dd/yyyy')," +
            "                                'mm/dd/yyyy'),'yyyymmdd','NLS_CALENDAR=persian')),'$LEG', " +
            "                        (SELECT LT.CODE FROM fndc.FINANCIAL_LEDGER_TYPE LT " +
            "                                WHERE LT.ID =FD.FINANCIAL_LEDGER_TYPE_ID)),'$DEP', " +
            "                        (SELECT DP.CODE  FROM FNDC.FINANCIAL_DEPARTMENT DP " +
            "                                WHERE DP.ID =FD.FINANCIAL_DEPARTMENT_ID)),'$ORG', " +
            "                        (SELECT OG.CODE  FROM FNDC.FINANCIAL_ORGANIZATION OG " +
            "                                WHERE OG.ORGANIZATION_ID = :organizationId )),'$PRI', " +
            "                        (SELECT PR.CODE  FROM FNPR.FINANCIAL_PERIOD PR " +
            "                           WHERE PR.ID = FD.FINANCIAL_PERIOD_ID)) GENERATED_COD " +
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
            "             AND FD.DELETED_DATE IS NULL) " +
            " WHERE SERIAL_RESETER = REPLACE(GENERATED_COD,'$SRL','') "
            , nativeQuery = true)
    List<Object[]> findDocumentNumber(Long organizationId, Long financialDocumentId, Long numberingType);

    @Query(value = "SELECT min(FD.DOCUMENT_DATE) " +
            "      FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER DN " +
            "        ON FD.ID = DN.FINANCIAL_DOCUMENT_ID " +
            "     WHERE FD.DELETED_DATE IS NULL " +
            "       AND FD.ORGANIZATION_ID = :organizationId" +
            "       AND DN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "       AND DN.DOCUMENT_NUMBER = NVL(:fromNumber, DN.DOCUMENT_NUMBER) " +
            "       AND DN.DELETED_DATE IS NULL "
            , nativeQuery = true)
    LocalDateTime findByFinancialDocumentByNumberingTypeAndFromNumber(Long documentNumberingTypeId, String fromNumber, Long organizationId);

    @Query(value = "SELECT max(FD.DOCUMENT_DATE) " +
            "      FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER DN " +
            "        ON FD.ID = DN.FINANCIAL_DOCUMENT_ID " +
            "     WHERE FD.DELETED_DATE IS NULL " +
            "       AND FD.ORGANIZATION_ID = :organizationId" +
            "       AND DN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "       AND DN.DOCUMENT_NUMBER = NVL(:toNumber, DN.DOCUMENT_NUMBER) " +
            "       AND DN.DELETED_DATE IS NULL "
            , nativeQuery = true)
    LocalDateTime findByFinancialDocumentByNumberingAndToNumber(Long documentNumberingTypeId, String toNumber, Long organizationId);


    @Query(value = "SELECT max(FD.DOCUMENT_DATE) " +
            "      FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER DN " +
            "        ON FD.ID = DN.FINANCIAL_DOCUMENT_ID " +
            "     WHERE FD.DELETED_DATE IS NULL " +
            "       AND FD.ORGANIZATION_ID = :organizationId" +
            "       AND DN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "       AND DN.DOCUMENT_NUMBER = NVL(:toNumber, DN.DOCUMENT_NUMBER) " +
            "       AND DN.DELETED_DATE IS NULL "
            , nativeQuery = true)
    LocalDateTime findByFinancialDocumentByNumberingTypeAndToNumber(Long documentNumberingTypeId, String toNumber, Long organizationId);

    @Query(value = " SELECT MIN(DN.DOCUMENT_NUMBER) " +
            "      FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER DN " +
            "        ON FD.ID = DN.FINANCIAL_DOCUMENT_ID " +
            "       AND DN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "       AND DN.DELETED_DATE IS NULL " +
            "     WHERE FD.DELETED_DATE IS NULL " +
            "       AND FD.DOCUMENT_DATE >= NVL(:fromDate, FD.DOCUMENT_DATE) " +
            "       AND FD.ORGANIZATION_ID = :organizationId "
            , nativeQuery = true)
    String findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(Long documentNumberingTypeId, LocalDateTime fromDate, Long organizationId);


    @Query(value = " SELECT MAX(DN.DOCUMENT_NUMBER) " +
            "      FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER DN " +
            "        ON FD.ID = DN.FINANCIAL_DOCUMENT_ID " +
            "       AND DN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "       AND DN.DELETED_DATE IS NULL " +
            "     WHERE FD.DELETED_DATE IS NULL " +
            "       AND FD.DOCUMENT_DATE <= NVL(:toDate, FD.DOCUMENT_DATE) " +
            "       AND FD.ORGANIZATION_ID = :organizationId "
            , nativeQuery = true)
    String findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(Long documentNumberingTypeId, LocalDateTime toDate, Long organizationId);

    @Query(value = "select FND.id  FROM FNDC.FINANCIAL_DOCUMENT FND " +
            " INNER JOIN FNDC.FINANCIAL_DEPARTMENT FNDP " +
            "    ON FND.FINANCIAL_DEPARTMENT_ID = FNDP.ID " +
            " INNER JOIN FNDC.FINANCIAL_DEPARTMENT_LEDGER FDL " +
            "    ON FNDP.DEPARTMENT_ID = FDL.DEPARTMENT_ID " +
            "   AND FND.FINANCIAL_LEDGER_TYPE_ID = FDL.FINANCIAL_LEDGER_TYPE_ID  " +
            " where FDL.id=:financialDepartmentLedgerId "
            , nativeQuery = true)
    List<Long> usedInFinancialDocument(Long financialDepartmentLedgerId);

    @Query("select fd.financialPeriod.id,fd.documentDate from FinancialDocument  fd " +
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
            "       FNDP.DEPARTMENT_ID" +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FS" +
            "    ON FS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID" +
            " INNER JOIN FNDC.FINANCIAL_DEPARTMENT FNDP" +
            "    ON FNDP.ID = FD.FINANCIAL_DEPARTMENT_ID" +
            " WHERE FD.DOCUMENT_NUMBER = :targetDocumentNumber" +
            "   and FD.ORGANIZATION_ID = :organizationId" +
            "   and FD.Financial_Ledger_Type_Id = :financialLedgerTypeId" +
            "   and FD.Financial_Department_Id = :financialDepartmentId" +
            "   and  (:department is null or FNDP.DEPARTMENT_ID=:departmentId) "
            , nativeQuery = true)
    List<Object[]> findDocumentByDocumentNumberAndCode(String targetDocumentNumber, Long organizationId, Long financialLedgerTypeId, Long financialDepartmentId, Object department, Long departmentId);


    @Query(value = " SELECT FD.AUTOMATIC_FLAG," +
            "       FD.ORGANIZATION_ID," +
            "       FD.FINANCIAL_DOCUMENT_TYPE_ID," +
            "       FD.FINANCIAL_PERIOD_ID," +
            "       FD.FINANCIAL_LEDGER_TYPE_ID," +
            "       FD.FINANCIAL_DEPARTMENT_ID," +
            "       FNDP.DEPARTMENT_ID  " +
            "  FROM FNDC.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN FNDC.FINANCIAL_DEPARTMENT FNDP " +
            "    ON FNDP.ID = FD.FINANCIAL_DEPARTMENT_ID " +
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

}
