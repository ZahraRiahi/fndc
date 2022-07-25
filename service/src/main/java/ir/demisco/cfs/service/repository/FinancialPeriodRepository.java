package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface FinancialPeriodRepository extends JpaRepository<FinancialPeriod, Long> {

    @Query(value = " SELECT " +
            " MIN(FP.START_DATE)" +
            "  FROM FNPR.FINANCIAL_PERIOD FP" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT" +
            "    ON FP.ID = FPT.FINANCIAL_PERIOD_ID" +
            "   AND FPT.ORGANIZATION_ID = :organizationId" +
            "   AND FPT.DELETED_DATE IS NULL" +
            "   AND FPT.ACTIVE_FLAG = 1" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY" +
            "    ON FP.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID" +
            " WHERE FP.FINANCIAL_PERIOD_STATUS_ID = 1" +
            "   AND FP.DELETED_DATE IS NULL "
            , nativeQuery = true)
    LocalDateTime findByFinancialPeriodByOrganization(Long organizationId);


    @Query(value = " SELECT  MAX(FP.START_DATE)  " +
            "    FROM FNPR.FINANCIAL_PERIOD FP  " +
            "    INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT  " +
            "    ON FP.id = FPT.FINANCIAL_PERIOD_ID  " +
            "    AND FPT.ORGANIZATION_ID = :organizationId  " +
            "    AND FPT.ACTIVE_FLAG = 1  " +
            "    INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY  " +
            "    ON FP.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID  " +
            "    WHERE FP.DELETED_DATE IS NULL  " +
            "    AND trunc(:startDate) > = FP.START_DATE "
            , nativeQuery = true)
    LocalDateTime findByFinancialPeriodByOrganizationStartDate(Long organizationId, LocalDateTime startDate);

    @Query(value = " WITH MAIN_QRY AS " +
            " (SELECT DOCUMENT_DATE, " +
            "         DOCUMENT_SEQUENCE, " +
            "         DOCUMENT_NUMBER, " +
            "         FINANCIAL_DOCUMENT_ID, " +
            "         DESCRIPTION, " +
            "         ID FINANCIAL_ACCOUNT_ID, " +
            "         FINANCIAL_ACCOUNT_CODE, " +
            "         FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "         DEBIT_AMOUNT, " +
            "         CREDIT_AMOUNT, " +
            "         ABS(CASE " +
            "               WHEN SUM(DEBIT_AMOUNT - CREDIT_AMOUNT) OVER(ORDER BY ID, " +
            "                         DOCUMENT_NUMBER, " +
            "                         DOCUMENT_DATE, " +
            "                         FINANCIAL_DOCUMENT_ID, " +
            "                         FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                         FINANCIAL_ACCOUNT_CODE, " +
            "                         FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                         TYP) > 0 THEN " +
            "                SUM(DEBIT_AMOUNT - CREDIT_AMOUNT) " +
            "                OVER(ORDER BY ID, " +
            "                     DOCUMENT_NUMBER, " +
            "                     DOCUMENT_DATE, " +
            "                     FINANCIAL_DOCUMENT_ID, " +
            "                     FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                     FINANCIAL_ACCOUNT_CODE, " +
            "                     FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                     TYP) " +
            "               ELSE " +
            "                0 " +
            "             END) AS REMAIN_DEBIT, " +
            "         CASE " +
            "           WHEN SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) OVER(ORDER BY ID, " +
            "                     DOCUMENT_NUMBER, " +
            "                     DOCUMENT_DATE, " +
            "                     FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                     FINANCIAL_ACCOUNT_CODE, " +
            "                     FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                     TYP) > 0 THEN " +
            "            SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) " +
            "            OVER(ORDER BY ID, " +
            "                 DOCUMENT_NUMBER, " +
            "                 DOCUMENT_DATE, " +
            "                 FINANCIAL_DOCUMENT_ID, " +
            "                 FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                 FINANCIAL_ACCOUNT_CODE, " +
            "                 FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                 TYP) " +
            "           ELSE " +
            "            0 " +
            "         END REMAIN_CREDIT, " +
            "         SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) OVER(ORDER BY ID, DOCUMENT_NUMBER, DOCUMENT_DATE, FINANCIAL_DOCUMENT_ID, FINANCIAL_DOCUMENT_ITEM_ID, FINANCIAL_ACCOUNT_CODE, FINANCIAL_ACCOUNT_DESCRIPTION, TYP) REMAIN_AMOUNT, " +
            "         0 SUM_DEBIT, " +
            "         0 SUM_CREDIT, " +
            "         0 SUMMERIZE_DEBIT, " +
            "         0 SUMMERIZE_CREDIT, " +
            "         0 SUMMERIZE_AMOUNT, " +
            "         RECORD_TYP " +
            "    FROM (SELECT NULL AS FINANCIAL_DOCUMENT_ID, " +
            "                 NULL AS FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                 CASE " +
            "                   WHEN SUM(FDI.CREDIT_AMOUNT) > SUM(FDI.DEBIT_AMOUNT) THEN " +
            "                    SUM(FDI.CREDIT_AMOUNT) - SUM(FDI.DEBIT_AMOUNT) " +
            "                   ELSE " +
            "                    0 " +
            "                 END CREDIT_AMOUNT, " +
            "                 CASE " +
            "                   WHEN SUM(FDI.DEBIT_AMOUNT) > SUM(FDI.CREDIT_AMOUNT) THEN " +
            "                    SUM(FDI.DEBIT_AMOUNT) - SUM(FDI.CREDIT_AMOUNT) " +
            "                   ELSE " +
            "                    0 " +
            "                 END DEBIT_AMOUNT, " +
            "                 NULL AS TYP, " +
            "                 NULL AS DOCUMENT_NUMBER, " +
            "                 NULL AS DOCUMENT_DATE, " +
            "                 'قبل از دوره' AS DESCRIPTION, " +
            "                 0 AS ID, " +
            "                 NULL AS FINANCIAL_ACCOUNT_CODE, " +
            "                 NULL AS FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                 NULL AS DOCUMENT_SEQUENCE, " +
            "                 1    AS RECORD_TYP " +
            "            FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI " +
            "              ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA " +
            "              ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER FDN " +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID " +
            "            LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR " +
            "              ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS " +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID " +
            "           WHERE FD.ORGANIZATION_ID = :organizationId " +
            "             AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId " +
            "             AND FD.DOCUMENT_DATE >= trunc(:periodStartDate) " +
            "             AND ((:dateFilterFlg = 1 AND FD.DOCUMENT_DATE < trunc(:fromDate)) OR " +
            "                 (:dateFilterFlg = 0 AND " +
            "                 FD.DOCUMENT_DATE < " +
            "                 (SELECT INER_DOC.DOCUMENT_DATE " +
            "                      FROM FNDC.FINANCIAL_DOCUMENT INER_DOC " +
            "                     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM " +
            "                        ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID " +
            "                       AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID = " +
            "                           :documentNumberingTypeId " +
            "                       AND INER_NUM.DOCUMENT_NUMBER = :fromNumber))) " +
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID = " +
            "                 :documentNumberingTypeId " +
            "   AND ( :centricAccount1 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId1 )   " +
            "   AND ( :centricAccount2 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId2 )  " +
            "   AND (:referenceNumberObject IS NULL OR FDR.REFRENCE_NUMBER = :referenceNumber) " +
            "             AND ((:dateFilterFlg = 0 AND " +
            "                 FDN.DOCUMENT_NUMBER < " +
            "                 NVL(:fromNumber, FDN.DOCUMENT_NUMBER)) OR " +
            "                 :dateFilterFlg = 1) " +
            "             AND (EXISTS " +
            "                  (SELECT 1 " +
            "                     FROM FNAC.ACCOUNT_STRUCTURE_LEVEL ASL " +
            "                    WHERE ASL.FINANCIAL_ACCOUNT_ID = FA.ID " +
            "                      AND ASL.RELATED_ACCOUNT_ID = :financialAccountId)) " +
            "             AND FDS.CODE > 10 " +
            "          UNION " +
            "          select alll.FINANCIAL_DOCUMENT_ID, " +
            "                 alll.FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                 sum(alll.CREDIT_AMOUNT) as CREDIT_AMOUNT, " +
            "                 sum(alll.DEBIT_AMOUNT) as DEBIT_AMOUNT, " +
            "                 alll.TYP, " +
            "                 alll.DOCUMENT_NUMBER, " +
            "                 alll.DOCUMENT_DATE, " +
            "                 alll.DESCRIPTION, " +
            "                 alll.ID, " +
            "                 alll.FINANCIAL_ACCOUNT_CODE, " +
            "                 alll.FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                 alll.DOCUMENT_SEQUENCE, " +
            "                 alll.RECORD_TYP " +
            "          from " +
            "          (SELECT CASE " +
            "                   WHEN :summarizingType IN (1, 2) THEN " +
            "                    FD.ID " +
            "                   ELSE " +
            "                    NULL " +
            "                 END FINANCIAL_DOCUMENT_ID, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1) THEN " +
            "                    FDI.ID " +
            "                   ELSE " +
            "                    NULL " +
            "                 END FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                 FDI.CREDIT_AMOUNT as CREDIT_AMOUNT, " +
            "                 FDI.DEBIT_AMOUNT as DEBIT_AMOUNT, " +
            "                 CASE " +
            "                   WHEN FDI.CREDIT_AMOUNT > 0 THEN " +
            "                    1 " +
            "                   WHEN FDI.DEBIT_AMOUNT > 0 THEN " +
            "                    0 " +
            "                 END AS TYP, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1, 2) THEN " +
            "                    FDN.DOCUMENT_NUMBER " +
            "                   ELSE " +
            "                    NULL " +
            "                 END AS DOCUMENT_NUMBER, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1, 2, 3) THEN " +
            "                    TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE, 'mm/dd/yyyy'), " +
            "                                    'mm/dd/yyyy'), " +
            "                            'YYYY/MM/DD', " +
            "                            'NLS_CALENDAR=persian') " +
            "                   ELSE " +
            "                    TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE, 'mm/dd/yyyy'), " +
            "                                    'mm/dd/yyyy'), " +
            "                            'YYYY/MM', " +
            "                            'NLS_CALENDAR=persian') " +
            "                 END " +
            "                 AS DOCUMENT_DATE, " +
            "                 CASE " +
            "                   WHEN :summarizingType = 1 THEN " +
            "                    FDI.DESCRIPTION " +
            "                   ELSE " +
            "     'به شرح سند' " +
            "                 END AS DESCRIPTION, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1, 2) THEN " +
            "                    FA.ID " +
            "                   WHEN :summarizingType IN (3, 4) THEN " +
            "                    FA_FILTER.ID " +
            "                   ELSE " +
            "                    NULL " +
            "                 END ID, " +
            "                  " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1, 2) THEN " +
            "                    FA.CODE " +
            "                   WHEN :summarizingType IN (3, 4) THEN " +
            "                    FA_FILTER.CODE " +
            "                   ELSE " +
            "                    NULL " +
            "                 END FINANCIAL_ACCOUNT_CODE, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1, 2) THEN " +
            "                    FA.DESCRIPTION " +
            "                   WHEN :summarizingType IN (3, 4) THEN " +
            "                    FA_FILTER.DESCRIPTION " +
            "                   ELSE " +
            "                    NULL " +
            "                 END FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1) THEN " +
            "                    FDI.SEQUENCE_NUMBER " +
            "                   ELSE " +
            "                    NULL " +
            "                 END AS DOCUMENT_SEQUENCE, " +
            "                 2 AS RECORD_TYP " +
            "            FROM fndc.FINANCIAL_DOCUMENT FD " +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_ITEM FDI " +
            "              ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA " +
            "              ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID " +
            "           INNER JOIN FNAC.ACCOUNT_STRUCTURE_LEVEL ASL " +
            "              ON ASL.FINANCIAL_ACCOUNT_ID = FA.ID " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA2 " +
            "              ON FA2.ID = ASL.RELATED_ACCOUNT_ID " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT_STRUCTURE FAS " +
            "              ON FA2.FINANCIAL_ACCOUNT_STRUCTURE_ID = FAS.ID " +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FDN " +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID " +
            "            LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR " +
            "              ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS " +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID " +
            "            LEFT OUTER JOIN FNAC.FINANCIAL_ACCOUNT FA_FILTER " +
            "              ON FA_FILTER.ID = :financialAccountId " +
            "           WHERE FD.ORGANIZATION_ID = :organizationId " +
            "             AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId " +
            "             AND ((:dateFilterFlg = 1 AND " +
            "                 FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND " +
            "                 NVL(trunc(:toDate), SYSDATE) OR " +
            "                 (:dateFilterFlg = 0 AND " +
            "                 FD.DOCUMENT_DATE >= " +
            "                 (SELECT INER_DOC.DOCUMENT_DATE " +
            "                       FROM FNDC.FINANCIAL_DOCUMENT INER_DOC " +
            "                      INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM " +
            "                         ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID " +
            "                        AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID = " +
            "                            :documentNumberingTypeId " +
            "                        AND INER_NUM.DOCUMENT_NUMBER = :fromNumber)))) " +
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID = " +
            "                 :documentNumberingTypeId " +
            "   AND ( :centricAccount1 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId1 )   " +
            "   AND ( :centricAccount2 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId2 )  " +
            "   AND (:referenceNumberObject IS NULL OR FDR.REFRENCE_NUMBER = :referenceNumber) " +
            "             AND ((:dateFilterFlg = 0 AND " +
            "                 FDN.DOCUMENT_NUMBER >= " +
            "                 NVL(:fromNumber, FDN.DOCUMENT_NUMBER) AND " +
            "                 FDN.DOCUMENT_NUMBER <= " +
            "                 NVL(:toNumber, FDN.DOCUMENT_NUMBER)) OR " +
            "                 :dateFilterFlg = 1)            " +
            "             AND FA2.ID = " +
            "                 nvl(:financialAccountId, fdi.financial_account_id) " +
            "             AND FDS.CODE > 10)alll " +
            "             group by " +
            "             alll.FINANCIAL_DOCUMENT_ID, " +
            "                 alll.FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                 alll.TYP, " +
            "                 alll.DOCUMENT_NUMBER, " +
            "                 alll.DOCUMENT_DATE, " +
            "                 alll.DESCRIPTION, " +
            "                 alll.ID, " +
            "                 alll.FINANCIAL_ACCOUNT_CODE, " +
            "                 alll.FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                 alll.DOCUMENT_SEQUENCE, " +
            "                 alll.RECORD_TYP " +
            "          ) " +
            "   ORDER BY ID) " +
            " SELECT * " +
            "  FROM (SELECT * " +
            "          FROM MAIN_QRY " +
            "        UNION " +
            "        SELECT NULL AS DOCUMENT_DATE, " +
            "               NULL AS DOCUMENT_NUMBER," +
            "               NULL AS FINANCIAL_DOCUMENT_ID, " +
            "               NULL AS DOCUMENT_SEQUENCE, " +
            "               NULL AS DESCRIPTION, " +
            "               NULL AS FINANCIAL_ACCOUNT_ID, " +
            "               NULL AS FINANCIAL_ACCOUNT_CODE, " +
            "               NULL AS FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "               0 DEBIT_AMOUNT, " +
            "               0 CREDIT_AMOUNT, " +
            "               0 REMAIN_DEBIT, " +
            "               0 REMAIN_CREDIT, " +
            "               0 REMAIN_AMOUNT, " +
            "               SUM(MAIN_QRY.DEBIT_AMOUNT) SUM_DEBIT, " +
            "               SUM(MAIN_QRY.CREDIT_AMOUNT) SUM_CREDIT, " +
            "               CASE " +
            "                 WHEN SUM(MAIN_QRY.DEBIT_AMOUNT) - " +
            "                      SUM(MAIN_QRY.CREDIT_AMOUNT) > 0 THEN " +
            "                  SUM(MAIN_QRY.DEBIT_AMOUNT) - SUM(MAIN_QRY.CREDIT_AMOUNT) " +
            "                 ELSE " +
            "                  0 " +
            "               END AS SUMMERIZE_DEBIT, " +
            "               CASE " +
            "                 WHEN SUM(MAIN_QRY.CREDIT_AMOUNT) - " +
            "                      SUM(MAIN_QRY.DEBIT_AMOUNT) > 0 THEN " +
            "                  SUM(MAIN_QRY.CREDIT_AMOUNT) - SUM(MAIN_QRY.DEBIT_AMOUNT) " +
            "                 ELSE " +
            "                  0 " +
            "               END AS SUMMERIZE_CREDIT, " +
            "               SUM(MAIN_QRY.CREDIT_AMOUNT) - SUM(MAIN_QRY.DEBIT_AMOUNT) AS SUMMERIZE_AMOUNT, " +
            "               3 AS RECORD_TYP " +
            "          FROM MAIN_QRY) " +
            " ORDER BY FINANCIAL_ACCOUNT_ID, " +
            "          RECORD_TYP, " +
            "          DOCUMENT_DATE, " +
            "          DOCUMENT_NUMBER, " +
            "          CREDIT_AMOUNT, " +
            "          DEBIT_AMOUNT  "
            , countQuery = " WITH MAIN_QRY AS " +
            " (SELECT DOCUMENT_DATE, " +
            "         DOCUMENT_SEQUENCE, " +
            "         DOCUMENT_NUMBER, " +
            "         FINANCIAL_DOCUMENT_ID, " +
            "         DESCRIPTION, " +
            "         ID FINANCIAL_ACCOUNT_ID, " +
            "         FINANCIAL_ACCOUNT_CODE, " +
            "         FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "         DEBIT_AMOUNT, " +
            "         CREDIT_AMOUNT, " +
            "         ABS(CASE " +
            "               WHEN SUM(DEBIT_AMOUNT - CREDIT_AMOUNT) OVER(ORDER BY ID, " +
            "                         DOCUMENT_NUMBER, " +
            "                         DOCUMENT_DATE, " +
            "                         FINANCIAL_DOCUMENT_ID, " +
            "                         FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                         FINANCIAL_ACCOUNT_CODE, " +
            "                         FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                         TYP) > 0 THEN " +
            "                SUM(DEBIT_AMOUNT - CREDIT_AMOUNT) " +
            "                OVER(ORDER BY ID, " +
            "                     DOCUMENT_NUMBER, " +
            "                     DOCUMENT_DATE, " +
            "                     FINANCIAL_DOCUMENT_ID, " +
            "                     FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                     FINANCIAL_ACCOUNT_CODE, " +
            "                     FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                     TYP) " +
            "               ELSE " +
            "                0 " +
            "             END) AS REMAIN_DEBIT, " +
            "         CASE " +
            "           WHEN SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) OVER(ORDER BY ID, " +
            "                     DOCUMENT_NUMBER, " +
            "                     DOCUMENT_DATE, " +
            "                     FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                     FINANCIAL_ACCOUNT_CODE, " +
            "                     FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                     TYP) > 0 THEN " +
            "            SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) " +
            "            OVER(ORDER BY ID, " +
            "                 DOCUMENT_NUMBER, " +
            "                 DOCUMENT_DATE, " +
            "                 FINANCIAL_DOCUMENT_ID, " +
            "                 FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                 FINANCIAL_ACCOUNT_CODE, " +
            "                 FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                 TYP) " +
            "           ELSE " +
            "            0 " +
            "         END REMAIN_CREDIT, " +
            "         SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) OVER(ORDER BY ID, DOCUMENT_NUMBER, DOCUMENT_DATE, FINANCIAL_DOCUMENT_ID, FINANCIAL_DOCUMENT_ITEM_ID, FINANCIAL_ACCOUNT_CODE, FINANCIAL_ACCOUNT_DESCRIPTION, TYP) REMAIN_AMOUNT, " +
            "         0 SUM_DEBIT, " +
            "         0 SUM_CREDIT, " +
            "         0 SUMMERIZE_DEBIT, " +
            "         0 SUMMERIZE_CREDIT, " +
            "         0 SUMMERIZE_AMOUNT, " +
            "         RECORD_TYP " +
            "    FROM (SELECT NULL AS FINANCIAL_DOCUMENT_ID, " +
            "                 NULL AS FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                 CASE " +
            "                   WHEN SUM(FDI.CREDIT_AMOUNT) > SUM(FDI.DEBIT_AMOUNT) THEN " +
            "                    SUM(FDI.CREDIT_AMOUNT) - SUM(FDI.DEBIT_AMOUNT) " +
            "                   ELSE " +
            "                    0 " +
            "                 END CREDIT_AMOUNT, " +
            "                 CASE " +
            "                   WHEN SUM(FDI.DEBIT_AMOUNT) > SUM(FDI.CREDIT_AMOUNT) THEN " +
            "                    SUM(FDI.DEBIT_AMOUNT) - SUM(FDI.CREDIT_AMOUNT) " +
            "                   ELSE " +
            "                    0 " +
            "                 END DEBIT_AMOUNT, " +
            "                 NULL AS TYP, " +
            "                 NULL AS DOCUMENT_NUMBER, " +
            "                 NULL AS DOCUMENT_DATE, " +
            "                 'قبل از دوره' AS DESCRIPTION, " +
            "                 0 AS ID, " +
            "                 NULL AS FINANCIAL_ACCOUNT_CODE, " +
            "                 NULL AS FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                 NULL AS DOCUMENT_SEQUENCE, " +
            "                 1    AS RECORD_TYP " +
            "            FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI " +
            "              ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA " +
            "              ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER FDN " +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID " +
            "            LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR " +
            "              ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS " +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID " +
            "           WHERE FD.ORGANIZATION_ID = :organizationId " +
            "             AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId " +
            "             AND FD.DOCUMENT_DATE >= trunc(:periodStartDate) " +
            "             AND ((:dateFilterFlg = 1 AND FD.DOCUMENT_DATE < trunc(:fromDate)) OR " +
            "                 (:dateFilterFlg = 0 AND " +
            "                 FD.DOCUMENT_DATE < " +
            "                 (SELECT INER_DOC.DOCUMENT_DATE " +
            "                      FROM FNDC.FINANCIAL_DOCUMENT INER_DOC " +
            "                     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM " +
            "                        ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID " +
            "                       AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID = " +
            "                           :documentNumberingTypeId " +
            "                       AND INER_NUM.DOCUMENT_NUMBER = :fromNumber))) " +
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID = " +
            "                 :documentNumberingTypeId " +
            "   AND ( :centricAccount1 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId1 )   " +
            "   AND ( :centricAccount2 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId2 )  " +
            "   AND (:referenceNumberObject IS NULL OR FDR.REFRENCE_NUMBER = :referenceNumber) " +
            "             AND ((:dateFilterFlg = 0 AND " +
            "                 FDN.DOCUMENT_NUMBER < " +
            "                 NVL(:fromNumber, FDN.DOCUMENT_NUMBER)) OR " +
            "                 :dateFilterFlg = 1) " +
            "             AND (EXISTS " +
            "                  (SELECT 1 " +
            "                     FROM FNAC.ACCOUNT_STRUCTURE_LEVEL ASL " +
            "                    WHERE ASL.FINANCIAL_ACCOUNT_ID = FA.ID " +
            "                      AND ASL.RELATED_ACCOUNT_ID = :financialAccountId)) " +
            "             AND FDS.CODE > 10 " +
            "          UNION " +
            "          select alll.FINANCIAL_DOCUMENT_ID, " +
            "                 alll.FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                 sum(alll.CREDIT_AMOUNT) as CREDIT_AMOUNT, " +
            "                 sum(alll.DEBIT_AMOUNT) as DEBIT_AMOUNT, " +
            "                 alll.TYP, " +
            "                 alll.DOCUMENT_NUMBER, " +
            "                 alll.DOCUMENT_DATE, " +
            "                 alll.DESCRIPTION, " +
            "                 alll.ID, " +
            "                 alll.FINANCIAL_ACCOUNT_CODE, " +
            "                 alll.FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                 alll.DOCUMENT_SEQUENCE, " +
            "                 alll.RECORD_TYP " +
            "          from " +
            "          (SELECT CASE " +
            "                   WHEN :summarizingType IN (1, 2) THEN " +
            "                    FD.ID " +
            "                   ELSE " +
            "                    NULL " +
            "                 END FINANCIAL_DOCUMENT_ID, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1) THEN " +
            "                    FDI.ID " +
            "                   ELSE " +
            "                    NULL " +
            "                 END FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                 FDI.CREDIT_AMOUNT as CREDIT_AMOUNT, " +
            "                 FDI.DEBIT_AMOUNT as DEBIT_AMOUNT, " +
            "                 CASE " +
            "                   WHEN FDI.CREDIT_AMOUNT > 0 THEN " +
            "                    1 " +
            "                   WHEN FDI.DEBIT_AMOUNT > 0 THEN " +
            "                    0 " +
            "                 END AS TYP, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1, 2) THEN " +
            "                    FDN.DOCUMENT_NUMBER " +
            "                   ELSE " +
            "                    NULL " +
            "                 END AS DOCUMENT_NUMBER, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1, 2, 3) THEN " +
            "                    TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE, 'mm/dd/yyyy'), " +
            "                                    'mm/dd/yyyy'), " +
            "                            'YYYY/MM/DD', " +
            "                            'NLS_CALENDAR=persian') " +
            "                   ELSE " +
            "                    TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE, 'mm/dd/yyyy'), " +
            "                                    'mm/dd/yyyy'), " +
            "                            'YYYY/MM', " +
            "                            'NLS_CALENDAR=persian') " +
            "                 END " +
            "                 AS DOCUMENT_DATE, " +
            "                 CASE " +
            "                   WHEN :summarizingType = 1 THEN " +
            "                    FDI.DESCRIPTION " +
            "                   ELSE " +
            "     'به شرح سند' " +
            "                 END AS DESCRIPTION, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1, 2) THEN " +
            "                    FA.ID " +
            "                   WHEN :summarizingType IN (3, 4) THEN " +
            "                    FA_FILTER.ID " +
            "                   ELSE " +
            "                    NULL " +
            "                 END ID, " +
            "                  " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1, 2) THEN " +
            "                    FA.CODE " +
            "                   WHEN :summarizingType IN (3, 4) THEN " +
            "                    FA_FILTER.CODE " +
            "                   ELSE " +
            "                    NULL " +
            "                 END FINANCIAL_ACCOUNT_CODE, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1, 2) THEN " +
            "                    FA.DESCRIPTION " +
            "                   WHEN :summarizingType IN (3, 4) THEN " +
            "                    FA_FILTER.DESCRIPTION " +
            "                   ELSE " +
            "                    NULL " +
            "                 END FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                 CASE " +
            "                   WHEN :summarizingType IN (1) THEN " +
            "                    FDI.SEQUENCE_NUMBER " +
            "                   ELSE " +
            "                    NULL " +
            "                 END AS DOCUMENT_SEQUENCE, " +
            "                 2 AS RECORD_TYP " +
            "            FROM fndc.FINANCIAL_DOCUMENT FD " +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_ITEM FDI " +
            "              ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA " +
            "              ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID " +
            "           INNER JOIN FNAC.ACCOUNT_STRUCTURE_LEVEL ASL " +
            "              ON ASL.FINANCIAL_ACCOUNT_ID = FA.ID " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA2 " +
            "              ON FA2.ID = ASL.RELATED_ACCOUNT_ID " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT_STRUCTURE FAS " +
            "              ON FA2.FINANCIAL_ACCOUNT_STRUCTURE_ID = FAS.ID " +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FDN " +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID " +
            "            LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR " +
            "              ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS " +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID " +
            "            LEFT OUTER JOIN FNAC.FINANCIAL_ACCOUNT FA_FILTER " +
            "              ON FA_FILTER.ID = :financialAccountId " +
            "           WHERE FD.ORGANIZATION_ID = :organizationId " +
            "             AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId " +
            "             AND ((:dateFilterFlg = 1 AND " +
            "                 FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND " +
            "                 NVL(trunc(:toDate), SYSDATE) OR " +
            "                 (:dateFilterFlg = 0 AND " +
            "                 FD.DOCUMENT_DATE >= " +
            "                 (SELECT INER_DOC.DOCUMENT_DATE " +
            "                       FROM FNDC.FINANCIAL_DOCUMENT INER_DOC " +
            "                      INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM " +
            "                         ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID " +
            "                        AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID = " +
            "                            :documentNumberingTypeId " +
            "                        AND INER_NUM.DOCUMENT_NUMBER = :fromNumber)))) " +
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID = " +
            "                 :documentNumberingTypeId " +
            "   AND ( :centricAccount1 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId1 )   " +
            "   AND ( :centricAccount2 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId2 )  " +
            "   AND (:referenceNumberObject IS NULL OR FDR.REFRENCE_NUMBER = :referenceNumber) " +
            "             AND ((:dateFilterFlg = 0 AND " +
            "                 FDN.DOCUMENT_NUMBER >= " +
            "                 NVL(:fromNumber, FDN.DOCUMENT_NUMBER) AND " +
            "                 FDN.DOCUMENT_NUMBER <= " +
            "                 NVL(:toNumber, FDN.DOCUMENT_NUMBER)) OR " +
            "                 :dateFilterFlg = 1)            " +
            "             AND FA2.ID = " +
            "                 nvl(:financialAccountId, fdi.financial_account_id) " +
            "             AND FDS.CODE > 10)alll " +
            "             group by " +
            "             alll.FINANCIAL_DOCUMENT_ID, " +
            "                 alll.FINANCIAL_DOCUMENT_ITEM_ID, " +
            "                 alll.TYP, " +
            "                 alll.DOCUMENT_NUMBER, " +
            "                 alll.DOCUMENT_DATE, " +
            "                 alll.DESCRIPTION, " +
            "                 alll.ID, " +
            "                 alll.FINANCIAL_ACCOUNT_CODE, " +
            "                 alll.FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "                 alll.DOCUMENT_SEQUENCE, " +
            "                 alll.RECORD_TYP " +
            "          ) " +
            "   ORDER BY ID) " +
            " SELECT count(*)as count_  " +
            "  FROM (SELECT * " +
            "          FROM MAIN_QRY " +
            "        UNION " +
            "        SELECT NULL AS DOCUMENT_DATE, " +
            "               NULL AS DOCUMENT_NUMBER, " +
            "  NULL AS FINANCIAL_DOCUMENT_ID " +
            "               NULL AS DOCUMENT_SEQUENCE, " +
            "               NULL AS DESCRIPTION, " +
            "               NULL AS FINANCIAL_ACCOUNT_ID, " +
            "               NULL AS FINANCIAL_ACCOUNT_CODE, " +
            "               NULL AS FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "               0 DEBIT_AMOUNT, " +
            "               0 CREDIT_AMOUNT, " +
            "               0 REMAIN_DEBIT, " +
            "               0 REMAIN_CREDIT, " +
            "               0 REMAIN_AMOUNT, " +
            "               SUM(MAIN_QRY.DEBIT_AMOUNT) SUM_DEBIT, " +
            "               SUM(MAIN_QRY.CREDIT_AMOUNT) SUM_CREDIT, " +
            "               CASE " +
            "                 WHEN SUM(MAIN_QRY.DEBIT_AMOUNT) - " +
            "                      SUM(MAIN_QRY.CREDIT_AMOUNT) > 0 THEN " +
            "                  SUM(MAIN_QRY.DEBIT_AMOUNT) - SUM(MAIN_QRY.CREDIT_AMOUNT) " +
            "                 ELSE " +
            "                  0 " +
            "               END AS SUMMERIZE_DEBIT, " +
            "               CASE " +
            "                 WHEN SUM(MAIN_QRY.CREDIT_AMOUNT) - " +
            "                      SUM(MAIN_QRY.DEBIT_AMOUNT) > 0 THEN " +
            "                  SUM(MAIN_QRY.CREDIT_AMOUNT) - SUM(MAIN_QRY.DEBIT_AMOUNT) " +
            "                 ELSE " +
            "                  0 " +
            "               END AS SUMMERIZE_CREDIT, " +
            "               SUM(MAIN_QRY.CREDIT_AMOUNT) - SUM(MAIN_QRY.DEBIT_AMOUNT) AS SUMMERIZE_AMOUNT, " +
            "               3 AS RECORD_TYP " +
            "          FROM MAIN_QRY)abb "
            , nativeQuery = true)
    List<Object[]> findByFinancialPeriodByParam(Long organizationId, Long ledgerTypeId, LocalDateTime periodStartDate,
                                                Long dateFilterFlg, LocalDateTime fromDate, Long documentNumberingTypeId,
                                                String fromNumber, Object centricAccount1, Long centricAccountId1,
                                                Object centricAccount2, Long centricAccountId2, Object referenceNumberObject,
                                                Long referenceNumber, String toNumber, Long financialAccountId,
                                                Long summarizingType, LocalDateTime toDate);

    @Query(value = " SELECT  MIN(FP.START_DATE)  " +
            "      FROM FNPR.FINANCIAL_PERIOD FP  " +
            "     INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT  " +
            "       ON FP.ID = FPT.FINANCIAL_PERIOD_ID  " +
            "       AND FPT.ORGANIZATION_ID = :organizationId  " +
            "       AND FPT.ACTIVE_FLAG = 1  " +
            "     INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY  " +
            " ON FP.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID " +
            "     WHERE FP.DELETED_DATE IS NULL  "
            , nativeQuery = true)
    LocalDateTime findByFinancialPeriodByOrganization2(Long organizationId);

    @Query(value = " WITH MAIN_QRY AS " +
            " (SELECT DOCUMENT_NUMBER, " +
            "         FINANCIAL_DOCUMENT_ID, " +
            "         ID ACCOUNT_ID, " +
            "         CODE ACCOUNT_CODE, " +
            "         DESCRIPTION ACCOUNT_DESCRIPTION, " +
            "         CENTRIC_ACCOUNT_ID_1, " +
            "         CENTRIC_ACCOUNT_ID_2, " +
            "         CENTRIC_ACCOUNT_ID_3, " +
            "         CENTRIC_ACCOUNT_ID_4, " +
            "         CENTRIC_ACCOUNT_ID_5, " +
            "         CENTRIC_ACCOUNT_ID_6, " +
            "         NAME_CNAC1 CENTRIC_ACCOUNT_DES_1, " +
            "         NAME_CNAC2 CENTRIC_ACCOUNT_DES_2, " +
            "         NAME_CNAC3 CENTRIC_ACCOUNT_DES_3, " +
            "         NAME_CNAC4 CENTRIC_ACCOUNT_DES_4, " +
            "         NAME_CNAC5 CENTRIC_ACCOUNT_DES_5, " +
            "         NAME_CNAC6 CENTRIC_ACCOUNT_DES_6, " +
            "         DEBIT_AMOUNT, " +
            "         CREDIT_AMOUNT, " +
            "         ABS(CASE " +
            "               WHEN SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) OVER(ORDER BY ID, " +
            "                         CENTRIC_ACCOUNT_ID_1, " +
            "                         CENTRIC_ACCOUNT_ID_2, " +
            "                         CENTRIC_ACCOUNT_ID_3, " +
            "                         CENTRIC_ACCOUNT_ID_4, " +
            "                         CENTRIC_ACCOUNT_ID_5, " +
            "                         CENTRIC_ACCOUNT_ID_6, " +
            "                         TYP) < 0 THEN " +
            "                SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) " +
            "                OVER(ORDER BY ID, " +
            "                     CENTRIC_ACCOUNT_ID_1, " +
            "                     CENTRIC_ACCOUNT_ID_2, " +
            "                     CENTRIC_ACCOUNT_ID_3, " +
            "                     CENTRIC_ACCOUNT_ID_4, " +
            "                     CENTRIC_ACCOUNT_ID_5, " +
            "                     CENTRIC_ACCOUNT_ID_6, " +
            "                     TYP) " +
            "               ELSE " +
            "                0 " +
            "             END) AS REMAIN_DEBIT, " +
            "         CASE " +
            "           WHEN SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) OVER(ORDER BY ID, " +
            "                     CENTRIC_ACCOUNT_ID_1, " +
            "                     CENTRIC_ACCOUNT_ID_2, " +
            "                     CENTRIC_ACCOUNT_ID_3, " +
            "                     CENTRIC_ACCOUNT_ID_4, " +
            "                     CENTRIC_ACCOUNT_ID_5, " +
            "                     CENTRIC_ACCOUNT_ID_6, " +
            "                     TYP) > 0 THEN " +
            "            SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) " +
            "            OVER(ORDER BY ID, " +
            "                 CENTRIC_ACCOUNT_ID_1, " +
            "                 CENTRIC_ACCOUNT_ID_2, " +
            "                 CENTRIC_ACCOUNT_ID_3, " +
            "                 CENTRIC_ACCOUNT_ID_4, " +
            "                 CENTRIC_ACCOUNT_ID_5, " +
            "                 CENTRIC_ACCOUNT_ID_6, " +
            "                 TYP) " +
            "           ELSE " +
            "            0 " +
            "         END REMAIN_CREDIT, " +
            "         SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) OVER(ORDER BY ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, TYP) REMAIN_AMOUNT, " +
            "         0 SUM_DEBIT, " +
            "         0 SUM_CREDIT, " +
            "         0 SUMMERIZE_DEBIT, " +
            "         0 SUMMERIZE_CREDIT, " +
            "         0 SUMMERIZE_AMOUNT, " +
            "         RECORD_TYP " +
            "    FROM (SELECT  NULL DOCUMENT_NUMBER, " +
            "                 NULL FINANCIAL_DOCUMENT_ID, " +
            "                 NULL AS CENTRIC_ACCOUNT_ID_1, " +
            "                 NULL AS CENTRIC_ACCOUNT_ID_2, " +
            "                 NULL AS CENTRIC_ACCOUNT_ID_3, " +
            "                 NULL AS CENTRIC_ACCOUNT_ID_4, " +
            "                 NULL AS CENTRIC_ACCOUNT_ID_5, " +
            "                 NULL AS CENTRIC_ACCOUNT_ID_6, " +
            "                 NULL AS CODE_CNAC1, " +
            "                 NULL AS CODE_CNAC2, " +
            "                 NULL AS CODE_CNAC3, " +
            "                 NULL AS CODE_CNAC4, " +
            "                 NULL AS CODE_CNAC5, " +
            "                 NULL AS CODE_CNAC6, " +
            "                 NULL AS NAME_CNAC1, " +
            "                 NULL AS NAME_CNAC2, " +
            "                 NULL AS NAME_CNAC3, " +
            "                 NULL AS NAME_CNAC4, " +
            "                 NULL AS NAME_CNAC5, " +
            "                 NULL AS NAME_CNAC6, " +
            "                 CASE " +
            "                   WHEN SUM(FDI.CREDIT_AMOUNT) > SUM(FDI.DEBIT_AMOUNT) THEN " +
            "                    SUM(FDI.CREDIT_AMOUNT) - SUM(FDI.DEBIT_AMOUNT) " +
            "                   ELSE " +
            "                    0 " +
            "                 END CREDIT_AMOUNT, " +
            "                 CASE " +
            "                   WHEN SUM(FDI.DEBIT_AMOUNT) > SUM(FDI.CREDIT_AMOUNT) THEN " +
            "                    SUM(FDI.DEBIT_AMOUNT) - SUM(FDI.CREDIT_AMOUNT) " +
            "                   ELSE " +
            "                    0 " +
            "                 END DEBIT_AMOUNT, " +
            "                 NULL TYP, " +
            "                 0 AS ID, " +
            "                 NULL AS CODE, " +
            "                 NULL AS DESCRIPTION, " +
            "                 1 AS RECORD_TYP " +
            "            FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI " +
            "              ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID " +
            "             AND FDI.DELETED_DATE IS NULL " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA " +
            "              ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID " +
            "             AND FA.DELETED_DATE IS NULL " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER FDN " +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID " +
            "             AND FDN.DELETED_DATE IS NULL " +
            "            LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR " +
            "              ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID " +
            "             AND FDR.DELETED_DATE IS NULL " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC1 " +
            "              ON CNAC1.ID = FDI.CENTRIC_ACCOUNT_ID_1 " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC2 " +
            "              ON CNAC2.ID = FDI.CENTRIC_ACCOUNT_ID_2 " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC3 " +
            "              ON CNAC3.ID = FDI.CENTRIC_ACCOUNT_ID_3 " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC4 " +
            "              ON CNAC4.ID = FDI.CENTRIC_ACCOUNT_ID_4 " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC5 " +
            "              ON CNAC5.ID = FDI.CENTRIC_ACCOUNT_ID_5 " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC6 " +
            "              ON CNAC6.ID = FDI.CENTRIC_ACCOUNT_ID_6 " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS " +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT1" +
            "              ON CNAC1.CENTRIC_ACCOUNT_TYPE_ID = CNAT1.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT2" +
            "              ON CNAC2.CENTRIC_ACCOUNT_TYPE_ID = CNAT2.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT3" +
            "              ON CNAC3.CENTRIC_ACCOUNT_TYPE_ID = CNAT3.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT4" +
            "              ON CNAC4.CENTRIC_ACCOUNT_TYPE_ID = CNAT4.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT5" +
            "              ON CNAC5.CENTRIC_ACCOUNT_TYPE_ID = CNAT5.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT6" +
            "              ON CNAC6.CENTRIC_ACCOUNT_TYPE_ID = CNAT6.ID" +
            "           WHERE FD.ORGANIZATION_ID = :organizationId " +
            "             AND FD.DELETED_DATE IS NULL " +
            "             AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId " +
            "             AND FD.DOCUMENT_DATE >= trunc(:periodStartDate) " +
            "             AND ((:dateFilterFlg = 1 AND FD.DOCUMENT_DATE < trunc(:fromDate)) OR" +
            "                 (:dateFilterFlg = 0 AND" +
            "                 FD.DOCUMENT_DATE <=" +
            "                 (SELECT INER_DOC.DOCUMENT_DATE" +
            "                      FROM FNDC.FINANCIAL_DOCUMENT INER_DOC" +
            "                     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM" +
            "                        ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID" +
            "                       AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID =" +
            "                           :documentNumberingTypeId" +
            "                       AND INER_NUM.DOCUMENT_NUMBER = :fromNumber" +
            "                     WHERE INER_DOC.DELETED_DATE IS NULL" +
            "                       AND INER_NUM.DELETED_DATE IS NULL)))" +
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId" +
            "              AND ((:cnacIdObj1 IS NOT NULL AND" +
            "                 :cnacIdObj2 IS NOT NULL AND" +
            "                 ((:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_1 AND" +
            "                 FDI.CENTRIC_ACCOUNT_ID_2 = :cnacId2) OR" +
            "                 (:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_2 AND" +
            "                 FDI.CENTRIC_ACCOUNT_ID_3 = :cnacId2) OR" +
            "                 (:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_3 AND" +
            "                 FDI.CENTRIC_ACCOUNT_ID_4 = :cnacId2) OR" +
            "                 (:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_4 AND" +
            "                 FDI.CENTRIC_ACCOUNT_ID_5 = :cnacId2) OR" +
            "                 (:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_5 AND" +
            "                 FDI.CENTRIC_ACCOUNT_ID_6 = :cnacId2)))" +
            "                 OR" +
            "                 ((:cnacIdObj1 IS NOT NULL AND" +
            "                 :cnacIdObj2 IS NULL) AND" +
            "                 (:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_1 OR" +
            "                 :cnacId1 = FDI.CENTRIC_ACCOUNT_ID_2 OR" +
            "                 :cnacId1 = FDI.CENTRIC_ACCOUNT_ID_3 OR" +
            "                 :cnacId1 = FDI.CENTRIC_ACCOUNT_ID_4 OR" +
            "                 :cnacId1 = FDI.CENTRIC_ACCOUNT_ID_5 OR" +
            "                 :cnacId1 = FDI.CENTRIC_ACCOUNT_ID_6))" +
            "                 OR (:cnacIdObj1 IS NULL AND" +
            "                 :cnacIdObj2 IS NULL))" +
            "             AND ((:cnatIdObj1 IS NOT NULL AND :cnatIdObj2 IS NOT NULL AND" +
            "                 ((:cnatId1 = CNAT1.ID AND CNAT2.ID = :cnatId2) OR" +
            "                 (:cnatId1 = CNAT2.ID AND CNAT3.ID = :cnatId2) OR" +
            "                 (:cnatId1 = CNAT3.ID AND CNAT4.ID = :cnatId2) OR" +
            "                 (:cnatId1 = CNAT4.ID AND CNAT5.ID = :cnatId2) OR" +
            "                 (:cnatId1= CNAT5.ID AND CNAT6.ID = :cnatId2)))" +
            "                 OR" +
            "                 ((:cnatIdObj1 IS NOT NULL AND :cnatIdObj2 IS NULL) AND" +
            "                 (:cnatId1 = CNAT1.ID OR :cnatId1 = CNAT2.ID OR" +
            "                 :cnatId1 = CNAT3.ID OR :cnatId1 = CNAT4.ID OR" +
            "                 :cnatId1 = CNAT5.ID OR :cnatId1 = CNAT6.ID))" +
            "                 OR (:cnatIdObj1 IS NULL AND :cnatIdObj2 IS NULL)) " +
            "             AND (:referenceNumberObject IS NULL OR " +
            "                 FDR.REFRENCE_NUMBER = :referenceNumber) " +
            "  AND ((:dateFilterFlg = 0 AND " +
            "                 FDN.DOCUMENT_NUMBER < " +
            "                 NVL(:fromNumber, FDN.DOCUMENT_NUMBER)) OR " +
            "                 :dateFilterFlg = 1)" +
            "             AND (EXISTS " +
            "                  (SELECT 1 " +
            "                     FROM FNAC.ACCOUNT_STRUCTURE_LEVEL ASL " +
            "                    WHERE ASL.FINANCIAL_ACCOUNT_ID = FA.ID " +
            "                      AND (:financialAccount is null or  ASL.RELATED_ACCOUNT_ID = :financialAccountId ) " +
            "                      and (  ASL.RELATED_ACCOUNT_ID =   ASL.RELATED_ACCOUNT_ID  ) " +
            "                              AND ASL.RELATED_ACCOUNT_ID = nvl(:financialAccountId,ASL.RELATED_ACCOUNT_ID) " +
            "                      AND ASL.DELETED_DATE IS NULL)) " +
            "             AND FDS.CODE > 10 " +
            "          UNION " +
            "          SELECT FD.DOCUMENT_NUMBER," +
            "                 FD.ID  FINANCIAL_DOCUMENT_ID," +
            "                 FDI.CENTRIC_ACCOUNT_ID_1, " +
            "                 FDI.CENTRIC_ACCOUNT_ID_2, " +
            "                 FDI.CENTRIC_ACCOUNT_ID_3, " +
            "                 FDI.CENTRIC_ACCOUNT_ID_4, " +
            "                 FDI.CENTRIC_ACCOUNT_ID_5, " +
            "                 FDI.CENTRIC_ACCOUNT_ID_6, " +
            "                 CNAC1.CODE CODE_CNAC1, " +
            "                 CNAC2.CODE CODE_CNAC2, " +
            "                 CNAC3.CODE CODE_CNAC3, " +
            "                 CNAC4.CODE CODE_CNAC4, " +
            "                 CNAC5.CODE CODE_CNAC5, " +
            "                 CNAC6.CODE CODE_CNAC6, " +
            "                 CNAC1.NAME NAME_CNAC1, " +
            "                 CNAC2.NAME NAME_CNAC2, " +
            "                 CNAC3.NAME NAME_CNAC3, " +
            "                 CNAC4.NAME NAME_CNAC4, " +
            "                 CNAC5.NAME NAME_CNAC5, " +
            "                 CNAC6.NAME NAME_CNAC6, " +
            "                 SUM(FDI.CREDIT_AMOUNT) CREDIT_AMOUNT, " +
            "                 SUM(FDI.DEBIT_AMOUNT) DEBIT_AMOUNT, " +
            "                 CASE " +
            "                   WHEN FDI.CREDIT_AMOUNT > 0 THEN " +
            "                    1 " +
            "                   WHEN FDI.DEBIT_AMOUNT > 0 THEN " +
            "                    0 " +
            "                 END AS TYP, " +
            "                 FA.ID, " +
            "                 FA.CODE AS CODE, " +
            "                 FA.DESCRIPTION, " +
            "                 2 AS RECORD_TYP " +
            "            FROM fndc.FINANCIAL_DOCUMENT FD " +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_ITEM FDI " +
            "              ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID " +
            "             AND FDI.DELETED_DATE IS NULL " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA " +
            "              ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID " +
            "             AND FA.DELETED_DATE IS NULL " +
            "           INNER JOIN FNAC.ACCOUNT_STRUCTURE_LEVEL ASL " +
            "              ON ASL.FINANCIAL_ACCOUNT_ID = FA.ID " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA2 " +
            "              ON FA2.ID = ASL.RELATED_ACCOUNT_ID " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT_STRUCTURE FAS " +
            "              ON FA2.FINANCIAL_ACCOUNT_STRUCTURE_ID = FAS.ID " +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FDN " +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID " +
            "             AND FDN.DELETED_DATE IS NULL " +
            "            LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR " +
            "              ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID " +
            "             AND FDR.DELETED_DATE IS NULL " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC1 " +
            "              ON CNAC1.ID = FDI.CENTRIC_ACCOUNT_ID_1 " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC2 " +
            "              ON CNAC2.ID = FDI.CENTRIC_ACCOUNT_ID_2 " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC3 " +
            "              ON CNAC3.ID = FDI.CENTRIC_ACCOUNT_ID_3 " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC4 " +
            "              ON CNAC4.ID = FDI.CENTRIC_ACCOUNT_ID_4 " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC5 " +
            "              ON CNAC5.ID = FDI.CENTRIC_ACCOUNT_ID_5 " +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC6 " +
            "              ON CNAC6.ID = FDI.CENTRIC_ACCOUNT_ID_6" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT1" +
            "              ON CNAC1.CENTRIC_ACCOUNT_TYPE_ID = CNAT1.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT2" +
            "              ON CNAC2.CENTRIC_ACCOUNT_TYPE_ID = CNAT2.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT3" +
            "              ON CNAC3.CENTRIC_ACCOUNT_TYPE_ID = CNAT3.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE   CNAT4" +
            "              ON CNAC4.CENTRIC_ACCOUNT_TYPE_ID = CNAT4.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT5" +
            "              ON CNAC5.CENTRIC_ACCOUNT_TYPE_ID = CNAT5.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT6" +
            "              ON CNAC6.CENTRIC_ACCOUNT_TYPE_ID = CNAT6.ID " +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS " +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID " +
            "             AND FDS.DELETED_DATE IS NULL " +
            "           WHERE FD.ORGANIZATION_ID = :organizationId " +
            "             AND FD.DELETED_DATE IS NULL " +
            "             AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId " +
            "             AND ((:dateFilterFlg = 1 AND FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND" +
            "                 NVL(trunc(:toDate), SYSDATE) OR" +
            "                 (:dateFilterFlg = 0 AND" +
            "                 FD.DOCUMENT_DATE >=" +
            "                 (SELECT INER_DOC.DOCUMENT_DATE" +
            "                       FROM FNDC.FINANCIAL_DOCUMENT INER_DOC" +
            "                      INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM" +
            "                         ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID" +
            "                        AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID =" +
            "                            :documentNumberingTypeId" +
            "                        AND INER_NUM.DOCUMENT_NUMBER = :fromNumber" +
            "                      WHERE INER_DOC.DELETED_DATE IS NULL" +
            "                        AND INER_NUM.DELETED_DATE IS NULL))))" +
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "              AND ((:cnacIdObj1 IS NOT NULL AND" +
            "                 :cnacIdObj2 IS NOT NULL AND" +
            "                 ((:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_1 AND" +
            "                 FDI.CENTRIC_ACCOUNT_ID_2 = :cnacId2) OR" +
            "                 (:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_2 AND" +
            "                 FDI.CENTRIC_ACCOUNT_ID_3 = :cnacId2) OR" +
            "                 (:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_3 AND" +
            "                 FDI.CENTRIC_ACCOUNT_ID_4 = :cnacId2) OR" +
            "                 (:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_4 AND" +
            "                 FDI.CENTRIC_ACCOUNT_ID_5 = :cnacId2) OR" +
            "                 (:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_5 AND" +
            "                 FDI.CENTRIC_ACCOUNT_ID_6 = :cnacId2)))" +
            "                 OR" +
            "                 ((:cnacIdObj1 IS NOT NULL AND" +
            "                 :cnacIdObj2 IS NULL) AND" +
            "                 (:cnacId1 = FDI.CENTRIC_ACCOUNT_ID_1 OR" +
            "                 :cnacId1 = FDI.CENTRIC_ACCOUNT_ID_2 OR" +
            "                 :cnacId1 = FDI.CENTRIC_ACCOUNT_ID_3 OR" +
            "                 :cnacId1 = FDI.CENTRIC_ACCOUNT_ID_4 OR" +
            "                 :cnacId1 = FDI.CENTRIC_ACCOUNT_ID_5 OR" +
            "                 :cnacId1 = FDI.CENTRIC_ACCOUNT_ID_6))" +
            "                 OR (:cnacIdObj1 IS NULL AND" +
            "                 :cnacIdObj2 IS NULL))" +
            "             AND ((:cnatIdObj1 IS NOT NULL AND :cnatIdObj2 IS NOT NULL AND" +
            "                 ((:cnatId1 = CNAT1.ID AND CNAT2.ID = :cnatId2) OR" +
            "                 (:cnatId1 = CNAT2.ID AND CNAT3.ID = :cnatId2) OR" +
            "                 (:cnatId1 = CNAT3.ID AND CNAT4.ID = :cnatId2) OR" +
            "                 (:cnatId1 = CNAT4.ID AND CNAT5.ID = :cnatId2) OR" +
            "                 (:cnatId1= CNAT5.ID AND CNAT6.ID = :cnatId2)))" +
            "                 OR" +
            "                 ((:cnatIdObj1 IS NOT NULL AND :cnatIdObj2 IS NULL) AND" +
            "                 (:cnatId1 = CNAT1.ID OR :cnatId1 = CNAT2.ID OR" +
            "                 :cnatId1 = CNAT3.ID OR :cnatId1 = CNAT4.ID OR" +
            "                 :cnatId1 = CNAT5.ID OR :cnatId1 = CNAT6.ID))" +
            "                 OR (:cnatIdObj1 IS NULL AND :cnatIdObj2 IS NULL)) " +
            "             AND (:referenceNumberObject IS NULL OR " +
            "                 FDR.REFRENCE_NUMBER = :referenceNumber) " +
            "             AND ((:dateFilterFlg = 0 AND " +
            "                 FDN.DOCUMENT_NUMBER >= " +
            "                 NVL(:fromNumber, FDN.DOCUMENT_NUMBER) AND " +
            "                 FDN.DOCUMENT_NUMBER <= NVL(:toNumber, FDN.DOCUMENT_NUMBER)) OR " +
            "                 :dateFilterFlg = 1) " +
            "    AND (:financialAccount is null or  fdi.financial_account_id  = :financialAccountId ) " +
            "                                and (  FA2.ID =   fdi.financial_account_id ) " +
            "             AND FDS.CODE > 10 " +
            "           GROUP BY FD.DOCUMENT_NUMBER," +
            "                    FD.ID," +
            "                    FA.ID," +
            "                    FA.CODE," +
            "                    FA.DESCRIPTION," +
            "                    CASE" +
            "                      WHEN FDI.CREDIT_AMOUNT > 0 THEN" +
            "                       1" +
            "                      WHEN FDI.DEBIT_AMOUNT > 0 THEN" +
            "                       0" +
            "                    END," +
            "                    FDI.CENTRIC_ACCOUNT_ID_1," +
            "                    FDI.CENTRIC_ACCOUNT_ID_2," +
            "                    FDI.CENTRIC_ACCOUNT_ID_3," +
            "                    FDI.CENTRIC_ACCOUNT_ID_4," +
            "                    FDI.CENTRIC_ACCOUNT_ID_5," +
            "                    FDI.CENTRIC_ACCOUNT_ID_6," +
            "                    CNAC1.CODE," +
            "                    CNAC2.CODE," +
            "                    CNAC3.CODE," +
            "                    CNAC4.CODE," +
            "                    CNAC5.CODE," +
            "                    CNAC6.CODE," +
            "                    CNAC1.NAME," +
            "                    CNAC2.NAME," +
            "                    CNAC3.NAME," +
            "                    CNAC4.NAME," +
            "                    CNAC5.NAME," +
            "                    CNAC6.NAME) " +
            "   ORDER BY ID) " +
            " SELECT * " +
            "  FROM (SELECT * " +
            "          FROM MAIN_QRY " +
            "        UNION " +
            "        SELECT NULL DOCUMENT_NUMBER," +
            "               NULL FINANCIAL_DOCUMENT_ID," +
            "               NULL ACCOUNT_ID, " +
            "               NULL ACCOUNT_CODE, " +
            "               NULL ACCOUNT_DESCRIPTION, " +
            "               NULL CENTRIC_ACCOUNT_ID_1, " +
            "               NULL CENTRIC_ACCOUNT_ID_2, " +
            "               NULL CENTRIC_ACCOUNT_ID_3, " +
            "               NULL CENTRIC_ACCOUNT_ID_4, " +
            "               NULL CENTRIC_ACCOUNT_ID_5, " +
            "               NULL CENTRIC_ACCOUNT_ID_6, " +
            "               NULL CENTRIC_ACCOUNT_DES_1, " +
            "               NULL CENTRIC_ACCOUNT_DES_2, " +
            "               NULL CENTRIC_ACCOUNT_DES_3, " +
            "               NULL CENTRIC_ACCOUNT_DES_4, " +
            "               NULL CENTRIC_ACCOUNT_DES_5, " +
            "               NULL CENTRIC_ACCOUNT_DES_6, " +
            "               NULL DEBIT_AMOUNT, " +
            "               NULL CREDIT_AMOUNT, " +
            "               NULL REMAIN_DEBIT, " +
            "               NULL REMAIN_CREDIT, " +
            "               NULL REMAIN_AMOUNT, " +
            "               SUM(MAIN_QRY.DEBIT_AMOUNT) SUM_DEBIT, " +
            "               SUM(MAIN_QRY.CREDIT_AMOUNT) SUM_CREDIT, " +
            "               CASE " +
            "                 WHEN SUM(MAIN_QRY.DEBIT_AMOUNT) - " +
            "                      SUM(MAIN_QRY.CREDIT_AMOUNT) > 0 THEN " +
            "                  SUM(MAIN_QRY.DEBIT_AMOUNT) - SUM(MAIN_QRY.CREDIT_AMOUNT) " +
            "                 ELSE " +
            "                  0 " +
            "               END AS SUMMERIZE_DEBIT, " +
            "               CASE " +
            "                 WHEN SUM(MAIN_QRY.CREDIT_AMOUNT) - " +
            "                      SUM(MAIN_QRY.DEBIT_AMOUNT) > 0 THEN " +
            "                  SUM(MAIN_QRY.CREDIT_AMOUNT) - SUM(MAIN_QRY.DEBIT_AMOUNT) " +
            "                 ELSE " +
            "                  0 " +
            "               END AS SUMMERIZE_CREDIT, " +
            "               SUM(MAIN_QRY.CREDIT_AMOUNT) - SUM(MAIN_QRY.DEBIT_AMOUNT) AS SUMMERIZE_AMOUNT, " +
            "               3 AS RECORD_TYP " +
            "          FROM MAIN_QRY) " +
            " ORDER BY RECORD_TYP "
            , nativeQuery = true)
    List<Object[]> findByFinancialAccountCentricTurnOver(Long organizationId, Long ledgerTypeId, LocalDateTime periodStartDate, Long dateFilterFlg, LocalDateTime fromDate, Long documentNumberingTypeId, String fromNumber,
                                                         Object cnacIdObj1, Object cnacIdObj2, Long cnacId1, Long cnacId2, Object cnatIdObj1, Object cnatIdObj2,
                                                         Long cnatId1, Long cnatId2,
                                                         Object referenceNumberObject,
                                                         Long referenceNumber, Object financialAccount, Long financialAccountId, LocalDateTime toDate, String toNumber);

    @Query(value = " WITH QRY AS " +
            " (SELECT FINANCIAL_ACCOUNT_PARENT_ID," +
            "         FINANCIAL_ACCOUNT_ID," +
            "         FINANCIAL_ACCOUNT_CODE," +
            "         FINANCIAL_ACCOUNT_DESCRIPTION," +
            "         FINANCIAL_ACCOUNT_LEVEL," +
            "         SUM_DEBIT," +
            "         SUM_CREDIT," +
            "         BEF_DEBIT," +
            "         BEF_CREDIT," +
            "         DECODE(SIGN(SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT)," +
            "                1," +
            "                SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT," +
            "                0) REM_DEBIT," +
            "         DECODE(SIGN(SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT)," +
            "                -1," +
            "                ABS(SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT)," +
            "                0) REM_CREDIT," +
            "         color," +
            "         0 SUMMERIZE_AMOUNT," +
            "         1 AS RECORD_TYP" +
            "    FROM (SELECT FA2.FINANCIAL_ACCOUNT_PARENT_ID," +
            "                 FA2.ID FINANCIAL_ACCOUNT_ID," +
            "                 FA2.CODE FINANCIAL_ACCOUNT_CODE," +
            "                 FA2.DESCRIPTION FINANCIAL_ACCOUNT_DESCRIPTION," +
            "                 FAS.SEQUENCE FINANCIAL_ACCOUNT_LEVEL," +
            "                 SUM(CASE" +
            "                       WHEN (FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate)) AND" +
            "                            (FDN.DOCUMENT_NUMBER BETWEEN :fromNumber AND" +
            "                            :toNumber) THEN" +
            "                        FDI.DEBIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) SUM_DEBIT," +
            "                 SUM(CASE" +
            "                       WHEN (FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate)) AND" +
            "                            (FDN.DOCUMENT_NUMBER BETWEEN :fromNumber AND" +
            "                            :toNumber) THEN" +
            "                        FDI.CREDIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) SUM_CREDIT," +
            "                 SUM(CASE" +
            "                       WHEN FD.DOCUMENT_DATE <= trunc(:fromDate) AND" +
            "                            FDN.DOCUMENT_NUMBER < :fromNumber THEN" +
            "                        FDI.DEBIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) BEF_DEBIT," +
            "                 SUM(CASE" +
            "                       WHEN FD.DOCUMENT_DATE <= trunc(:fromDate) AND" +
            "                            FDN.DOCUMENT_NUMBER < :fromNumber THEN" +
            "                        FDI.CREDIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) BEF_CREDIT," +
            "                 FAS.Color" +
            "            FROM FNDC.FINANCIAL_DOCUMENT FD" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "              ON FDI.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "             AND FDI.DELETED_DATE IS NULL" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA" +
            "              ON FDI.FINANCIAL_ACCOUNT_ID = FA.ID" +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FDN" +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "             AND FDN.DELETED_DATE IS NULL" +
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID =" +
            "                 :documentNumberingTypeId " +
            "           INNER JOIN FNAC.ACCOUNT_STRUCTURE_LEVEL ASL" +
            "              ON ASL.FINANCIAL_ACCOUNT_ID = FA.ID" +
            "             AND ASL.DELETED_DATE IS NULL" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA2" +
            "              ON FA2.ID = ASL.RELATED_ACCOUNT_ID" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT_STRUCTURE FAS" +
            "              ON FA2.FINANCIAL_ACCOUNT_STRUCTURE_ID = FAS.ID" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS" +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID" +
            "             AND FDS.DELETED_DATE IS NULL" +
            "           WHERE FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId" +
            "          AND (((FAS.SEQUENCE = :structureLevel OR " +
            "                 (FAS.SEQUENCE < :structureLevel AND NOT EXISTS " +
            "                  (SELECT 1 " +
            "                        FROM FNAC.FINANCIAL_ACCOUNT FA_INER " +
            "                       WHERE FA_INER.FINANCIAL_ACCOUNT_PARENT_ID = FA2.ID))) AND " +
            "                 :showHigherLevels = 0) OR " +
            "                 (FAS.SEQUENCE <= :structureLevel AND " +
            "                 :showHigherLevels = 1)) " +
            "             AND FD.DOCUMENT_DATE BETWEEN trunc(:periodStartDate)  AND trunc(:toDate)" +
            "             AND (FDN.DOCUMENT_NUMBER <= :toNumber OR :toNumber IS NULL)" +
            "       AND ((SUBSTR(" +
            "                          case" +
            "                            when (:showHigherLevels = 0 and" +
            "                                 " +
            "                                 (FAS.SEQUENCE = :structureLevel or" +
            "                                 (FAS.SEQUENCE < :structureLevel and not exists" +
            "                                  (select 1" +
            "                                       from fnac.financial_account fa_iner" +
            "                                      where fa_iner.financial_account_parent_id = FA2.ID)))" +
            "                                 ) then" +
            "                             rpad(FA.CODE, :length, '0')" +
            "                            else" +
            "                             FA.CODE" +
            "                          end , " +
            "            1, :length) >= :fromFinancialAccountCode) or " +
            "               :fromFinancialAccountCode is null) " +
            "           and ((SUBSTR(fa.code, 1, :length) <= :toFinancialAccountCode) or " +
            "               :toFinancialAccountCode is null) " +
            "             AND FD.DELETED_DATE IS NULL" +
            "             AND FD.ORGANIZATION_ID = :organizationId " +
            "             AND FDS.CODE > 10" +
            "           GROUP BY FA2.FINANCIAL_ACCOUNT_PARENT_ID," +
            "                    FA2.ID," +
            "                    FA2.CODE," +
            "                    FA2.DESCRIPTION," +
            "                    FAS.SEQUENCE," +
            "                    FAS.Color" +
            "          )" +
            "  WHERE (:hasRemain = 1 AND :showHigherLevels= 0 AND " +
            "       (SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT) <> 0) " +
            "    OR (:hasRemain = 0) " +
            "    OR :showHigherLevels= 1 " +
            "  union" +
            "  SELECT null FINANCIAL_ACCOUNT_PARENT_ID," +
            "         null FINANCIAL_ACCOUNT_ID," +
            "         null FINANCIAL_ACCOUNT_CODE," +
            "         null FINANCIAL_ACCOUNT_DESCRIPTION," +
            "         null FINANCIAL_ACCOUNT_LEVEL," +
            "         SUM(SUM_DEBIT) SUMMERIZE_DEBIT," +
            "         SUM(SUM_CREDIT) SUMMERIZE_CREDIT," +
            "         0 BEF_DEBIT," +
            "         0 BEF_CREDIT," +
            "         0 REM_DEBIT," +
            "         0 REM_CREDIT," +
            "         null color," +
            "         SUM(SUM_CREDIT) - SUM(SUM_DEBIT) SUMMERIZE_AMOUNT," +
            "         3 AS RECORD_TYP" +
            "    FROM (SELECT FA2.FINANCIAL_ACCOUNT_PARENT_ID," +
            "                 FA2.ID FINANCIAL_ACCOUNT_ID," +
            "                 FA2.CODE FINANCIAL_ACCOUNT_CODE," +
            "                 FA2.DESCRIPTION FINANCIAL_ACCOUNT_DESCRIPTION," +
            "                 FAS.SEQUENCE FINANCIAL_ACCOUNT_LEVEL," +
            "                 SUM(CASE" +
            "                       WHEN (FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate)) AND" +
            "                            (FDN.DOCUMENT_NUMBER BETWEEN :fromNumber AND" +
            "                            :toNumber) THEN" +
            "                        FDI.DEBIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) SUM_DEBIT," +
            "                 SUM(CASE" +
            "                       WHEN (FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate)) AND" +
            "                            (FDN.DOCUMENT_NUMBER BETWEEN :fromNumber AND" +
            "                            :toNumber) THEN" +
            "                        FDI.CREDIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) SUM_CREDIT," +
            "                 SUM(CASE" +
            "                       WHEN FD.DOCUMENT_DATE <= trunc(:fromDate) AND" +
            "                            FDN.DOCUMENT_NUMBER < :fromNumber THEN" +
            "                        FDI.DEBIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) BEF_DEBIT," +
            "                 SUM(CASE" +
            "                       WHEN FD.DOCUMENT_DATE <= trunc(:fromDate) AND" +
            "                            FDN.DOCUMENT_NUMBER < :fromNumber THEN" +
            "                        FDI.CREDIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) BEF_CREDIT," +
            "                 FAS.Color" +
            "            FROM FNDC.FINANCIAL_DOCUMENT FD" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "              ON FDI.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "             AND FDI.DELETED_DATE IS NULL" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA" +
            "              ON FDI.FINANCIAL_ACCOUNT_ID = FA.ID" +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FDN" +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "             AND FDN.DELETED_DATE IS NULL" +
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID =" +
            "                 :documentNumberingTypeId " +
            "           INNER JOIN FNAC.ACCOUNT_STRUCTURE_LEVEL ASL" +
            "              ON ASL.FINANCIAL_ACCOUNT_ID = FA.ID" +
            "             AND ASL.DELETED_DATE IS NULL" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA2" +
            "              ON FA2.ID = ASL.RELATED_ACCOUNT_ID" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT_STRUCTURE FAS" +
            "              ON FA2.FINANCIAL_ACCOUNT_STRUCTURE_ID = FAS.ID" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS" +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID" +
            "             AND FDS.DELETED_DATE IS NULL" +
            "           WHERE FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId " +
            "              AND FAS.SEQUENCE = :structureLevel" +
            "             AND FD.DOCUMENT_DATE BETWEEN trunc(:periodStartDate)  AND trunc(:toDate)" +
            "             AND (FDN.DOCUMENT_NUMBER <= :toNumber OR :toNumber IS NULL)" +
            "       AND ((SUBSTR(" +
            "                          case" +
            "                            when (:showHigherLevels = 0 and" +
            "                                 " +
            "                                 (FAS.SEQUENCE = :structureLevel or" +
            "                                 (FAS.SEQUENCE < :structureLevel and not exists" +
            "                                  (select 1" +
            "                                       from fnac.financial_account fa_iner" +
            "                                      where fa_iner.financial_account_parent_id = FA2.ID)))" +
            "                                 ) then" +
            "                             rpad(FA.CODE, :length, '0')" +
            "                            else" +
            "                             FA.CODE" +
            "                          end , " +
            "            1, :length) >= :fromFinancialAccountCode) or " +
            "               :fromFinancialAccountCode is null) " +
            "           and ((SUBSTR(fa.code, 1, :length) <= :toFinancialAccountCode) or " +
            "               :toFinancialAccountCode is null) " +
            "             AND FD.DELETED_DATE IS NULL" +
            "             AND FD.ORGANIZATION_ID = :organizationId" +
            "             AND FDS.CODE > 10" +
            "           GROUP BY FA2.FINANCIAL_ACCOUNT_PARENT_ID," +
            "                    FA2.ID," +
            "                    FA2.CODE," +
            "                    FA2.DESCRIPTION," +
            "                    FAS.SEQUENCE," +
            "                    FAS.COLOR))" +
            "SELECT * FROM QRY ORDER BY FINANCIAL_ACCOUNT_CODE "
            , nativeQuery = true)
    List<Object[]> findByFinancialPeriodByBalanceReport(LocalDateTime fromDate, LocalDateTime toDate, String fromNumber, String toNumber, Long documentNumberingTypeId, Long ledgerTypeId,
                                                        Long structureLevel, Boolean showHigherLevels, LocalDateTime periodStartDate, int length, String fromFinancialAccountCode,
                                                        String toFinancialAccountCode, Long organizationId, Boolean hasRemain);

    @Query(value = "select fp.id, " +
            "       ' دوره مالی از ' || case" +
            "         when fpty.calendar_type_id = 1 then " +
            "          TO_CHAR(TO_DATE(TO_char(fp.start_date, 'mm/dd/yyyy'), 'mm/dd/yyyy'), " +
            "                  'yyyy/mm/dd', " +
            "                  'NLS_CALENDAR=persian') || ' تا ' || " +
            "          TO_CHAR(TO_DATE(TO_char(fp.end_date, 'mm/dd/yyyy'), 'mm/dd/yyyy'), " +
            "                  'yyyy/mm/dd'," +
            "                  'NLS_CALENDAR=persian') " +
            "         when fpty.calendar_type_id = 2 then " +
            "          TO_CHAR(TO_DATE(TO_char(fp.start_date, 'mm/dd/yyyy'), 'mm/dd/yyyy'), " +
            "                  'yyyy/mm/dd') || ' تا ' || " +
            "          TO_CHAR(TO_DATE(TO_char(fp.end_date, 'mm/dd/yyyy'), 'mm/dd/yyyy'), " +
            "                  'yyyy/mm/dd') " +
            "       end Full_description , " +
            "       fp.description , " +
            "       fp.code  " +
            "    from " +
            "        fnpr.financial_period fp " +
            "    inner join" +
            "        fnpr.financial_period_type_assign fpt    " +
            "            on fp.id = fpt.FINANCIAL_PERIOD_ID  " +
            "            and fpt.organization_id = :organizationId " +
            "            and fpt.deleted_date is null   " +
            "            and fpt.active_flag = 1 " +
            "    inner join" +
            "        fnpr.financial_period_type fpty    " +
            " on FP.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID" +
            "    where " +
            "        fp.financial_period_status_id = 1   " +
            "        and to_date(:localDate, 'yyyy-mm-dd') between fp.start_date and fp.end_date   " +
            "        and fp.deleted_date is null "
            , nativeQuery = true)
    List<Object[]> findByFinancialPeriodAndDate(String localDate, Long organizationId);

    @Query(value = "SELECT  CASE " +
            "         WHEN T.FINANCIAL_PERIOD_STATUS_ID = 2 THEN " +
            "          0 " +
            "         ELSE " +
            "          1 " +
            "       END " +
            "  FROM FNPR.FINANCIAL_PERIOD T" +
            " WHERE T.ID = :financialPeriodId " +
            " and t.deleted_date is null "
            , nativeQuery = true)
    Long findFinancialPeriodById(Long financialPeriodId);

    @Query(value = "SELECT 1 " +
            "                      FROM FNPR.FINANCIAL_PERIOD T " +
            "                       WHERE T.ID = :financialPeriodId " +
            "                          AND :documentDate BETWEEN T.START_DATE AND T.END_DATE "
            , nativeQuery = true)
    Long findFinancialPeriodByFinancialPeriodIdAndDocumentDate(Long financialPeriodId, LocalDateTime documentDate);

    @Query(value = " SELECT " +
            "       NVL((SELECT FLPS.CODE" +
            "             FROM FNDC.FINANCIAL_LEDGER_PERIOD FNLP" +
            "            INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD_STATUS FLPS" +
            "               ON FNLP.FIN_LEDGER_PERIOD_STAT_ID = FLPS.ID" +
            "            WHERE FNLP.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "              AND FNLP.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId)," +
            "           0) AS CODE" +
            "  FROM DUAL "
            , nativeQuery = true)
    Long findFinancialPeriodByIdAndLedgerType(Long financialPeriodId, Long financialLedgerTypeId);

    @Query(value = " SELECT  NVL((SELECT  FLMS.CODE" +
            "             FROM FNDC.FINANCIAL_LEDGER_MONTH FNLM" +
            "            INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH_STATUS FLMS" +
            "               ON FNLM.FIN_LEDGER_MONTH_STAT_ID = FLMS.ID" +
            "            INNER JOIN FNPR.FINANCIAL_MONTH FNMN" +
            "               ON FNMN.ID = FNLM.FINANCIAL_MONTH_ID" +
            "  INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD FLPR" +
            "               ON FLPR.ID = FNLM.FINANCIAL_LEDGER_PERIOD_ID" +
            "            INNER JOIN FNPR.FINANCIAL_PERIOD FNP" +
            "               ON FNP.ID = FNMN.FINANCIAL_PERIOD_ID" +
            "              AND FLPR.FINANCIAL_PERIOD_ID = FNP.ID" +
            "            INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FNPT" +
            "               ON FNPT.ID = FNP.FINANCIAL_PERIOD_TYPE_ID" +
            "            INNER JOIN FNPR.FINANCIAL_MONTH_TYPE FNMT" +
            "               ON FNMT.ID = FNMN.FINANCIAL_MONTH_TYPE_ID" +
            "            WHERE FNMN.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "              AND FNLM.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "        and :dateBetween  BETWEEN FNP.START_DATE AND FNP.END_DATE  " +
            "              AND (CASE CALENDAR_TYPE_ID" +
            "                    WHEN 2 THEN" +
            "                     EXTRACT(MONTH FROM TO_DATE(TO_CHAR(trunc(:date), 'mm/dd/yyyy')," +
            "                                     'mm/dd/yyyy'))" +
            "                    WHEN 1 THEN" +
            "                     TO_NUMBER(SUBSTR(TO_CHAR(TO_DATE(TO_CHAR(trunc(:date)," +
            "                                                              'mm/dd/yyyy')," +
            "                                                      'mm/dd/yyyy')," +
            "                                              'yyyy/mm/dd'," +
            "                                              'NLS_CALENDAR=persian')," +
            "                                      6," +
            "                                      2))" +
            "                  END = CASE" +
            "                    WHEN FNPT.CURRENT_YEAR_FLAG = 1 THEN" +
            "                     FNPT.FROM_MONTH + FNMT.MONTH_NUMBER - 1" +
            "                    ELSE" +
            "                     CASE" +
            "                       WHEN FNPT.FROM_MONTH + FNMT.MONTH_NUMBER - 1 > 12 THEN" +
            "                        FNPT.FROM_MONTH + FNMT.MONTH_NUMBER - 13" +
            "                       ELSE" +
            "                        FNPT.FROM_MONTH + FNMT.MONTH_NUMBER - 1" +
            "                     END" +
            "                  END AND FNMN.FINANCIAL_MONTH_STATUS_ID = 1))," +
            "           0) AS CODE " +
            "  FROM DUAL"
            , nativeQuery = true)
    Long findFinancialPeriodByIdAndLedgerTypeAndDate(Long financialPeriodId, Long financialLedgerTypeId, LocalDateTime dateBetween, Date date);

    @Query(value = " WITH QRY AS" +
            " (SELECT NVL(FINANCIAL_ACCOUNT_CODE, '') ||" +
            "         NVL(FINANCIAL_ACCOUNT_DESCRIPTION, '') FINANCIAL_ACCOUNT_DESC," +
            "         FINANCIAL_ACCOUNT_ID, " +
            "         SUM_DEBIT," +
            "         SUM_CREDIT," +
            "         BEF_DEBIT," +
            "         BEF_CREDIT," +
            "         DECODE(SIGN(SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT)," +
            "                1," +
            "                SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT," +
            "                0) REM_DEBIT," +
            "         DECODE(SIGN(SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT)," +
            "                -1," +
            "                ABS(SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT)," +
            "                0) REM_CREDIT," +
            "         NVL(CODE_CNAC1, '') || NVL(NAME_CNAC1, '') ||" +
            "         NVL2(CODE_CNAC2, '-' || CODE_CNAC2, '') || NVL(NAME_CNAC2, '') ||" +
            "         NVL2(CODE_CNAC3, '-' || CODE_CNAC3, '') || NVL(NAME_CNAC3, '') ||" +
            "         NVL2(CODE_CNAC4, '-' || CODE_CNAC4, '') || NVL(NAME_CNAC4, '') ||" +
            "         NVL2(CODE_CNAC5, '-' || CODE_CNAC5, '') || NVL(NAME_CNAC5, '') ||" +
            "         NVL2(CODE_CNAC6, '-' || CODE_CNAC6, '') || NVL(NAME_CNAC6, '') CENTRIC_ACCOUNT_DES" +
            "    FROM (SELECT FA2.FINANCIAL_ACCOUNT_PARENT_ID," +
            "                 FA2.ID                          FINANCIAL_ACCOUNT_ID," +
            "                 FA2.CODE                        FINANCIAL_ACCOUNT_CODE," +
            "                 FA2.DESCRIPTION                 FINANCIAL_ACCOUNT_DESCRIPTION," +
            "                 SUM(CASE" +
            "                       WHEN (FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate)) AND" +
            "                            (FDN.DOCUMENT_NUMBER BETWEEN :fromNumber AND" +
            "                            :toNumber) THEN" +
            "                        FDI.DEBIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) SUM_DEBIT," +
            "                 SUM(CASE" +
            "                       WHEN (FD.DOCUMENT_DATE BETWEEN trunc(:fromDate) AND trunc(:toDate)) AND" +
            "                            (FDN.DOCUMENT_NUMBER BETWEEN :fromNumber AND" +
            "                            :toNumber) THEN" +
            "                        FDI.CREDIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) SUM_CREDIT," +
            "                 SUM(CASE" +
            "                       WHEN FD.DOCUMENT_DATE <= trunc(:fromDate) AND" +
            "                            FDN.DOCUMENT_NUMBER < :fromNumber THEN" +
            "                        FDI.DEBIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) BEF_DEBIT," +
            "                 SUM(CASE" +
            "                       WHEN FD.DOCUMENT_DATE <= trunc(:fromDate) AND" +
            "                            FDN.DOCUMENT_NUMBER < :fromNumber THEN" +
            "                        FDI.CREDIT_AMOUNT" +
            "                       ELSE" +
            "                        0" +
            "                     END) BEF_CREDIT," +
            "                 CNAC1.CODE CODE_CNAC1," +
            "                 CNAC2.CODE CODE_CNAC2," +
            "                 CNAC3.CODE CODE_CNAC3," +
            "                 CNAC4.CODE CODE_CNAC4," +
            "                 CNAC5.CODE CODE_CNAC5," +
            "                 CNAC6.CODE CODE_CNAC6," +
            "                 CNAC1.NAME NAME_CNAC1," +
            "                 CNAC2.NAME NAME_CNAC2," +
            "                 CNAC3.NAME NAME_CNAC3," +
            "                 CNAC4.NAME NAME_CNAC4," +
            "                 CNAC5.NAME NAME_CNAC5," +
            "                 CNAC6.NAME NAME_CNAC6" +
            "            FROM FNDC.FINANCIAL_DOCUMENT FD" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "              ON FDI.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "             AND FDI.DELETED_DATE IS NULL" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA2" +
            "              ON FDI.FINANCIAL_ACCOUNT_ID = FA2.ID" +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FDN" +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "             AND FDN.DELETED_DATE IS NULL" +
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID =" +
            "                 :documentNumberingTypeId " +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT_STRUCTURE FAS" +
            "              ON FA2.FINANCIAL_ACCOUNT_STRUCTURE_ID = FAS.ID" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS" +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID" +
            "             AND FDS.DELETED_DATE IS NULL" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC1" +
            "              ON CNAC1.ID = FDI.CENTRIC_ACCOUNT_ID_1" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC2" +
            "              ON CNAC2.ID = FDI.CENTRIC_ACCOUNT_ID_2" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC3" +
            "              ON CNAC3.ID = FDI.CENTRIC_ACCOUNT_ID_3" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC4" +
            "              ON CNAC4.ID = FDI.CENTRIC_ACCOUNT_ID_4" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC5" +
            "              ON CNAC5.ID = FDI.CENTRIC_ACCOUNT_ID_5" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC6" +
            "              ON CNAC6.ID = FDI.CENTRIC_ACCOUNT_ID_6 " +
            " LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT1" +
            "              ON CNAC1.CENTRIC_ACCOUNT_TYPE_ID = CNAT1.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT2" +
            "              ON CNAC2.CENTRIC_ACCOUNT_TYPE_ID = CNAT2.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT3" +
            "              ON CNAC3.CENTRIC_ACCOUNT_TYPE_ID = CNAT3.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT4" +
            "              ON CNAC4.CENTRIC_ACCOUNT_TYPE_ID = CNAT4.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT5" +
            "              ON CNAC5.CENTRIC_ACCOUNT_TYPE_ID = CNAT5.ID" +
            "            LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT_TYPE  CNAT6" +
            "              ON CNAC6.CENTRIC_ACCOUNT_TYPE_ID = CNAT6.ID" +
            "           WHERE FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId" +
            "             AND FD.DOCUMENT_DATE BETWEEN trunc(:periodStartDate) AND trunc(:toDate)" +
            "             AND (FDN.DOCUMENT_NUMBER <= :toNumber OR :toNumber IS NULL)" +
            "             AND ((SUBSTR(FA2.CODE, 1, :length) >=" +
            "                 :fromFinancialAccountCode) OR" +
            "                 :fromFinancialAccountCode IS NULL)" +
            "             AND ((SUBSTR(FA2.CODE, 1, :length) <= :toFinancialAccountCode) OR" +
            "                 :toFinancialAccountCode IS NULL)" +
            "             AND FD.DELETED_DATE IS NULL" +
            "             AND FD.ORGANIZATION_ID = :organizationId" +
            "             AND FDS.CODE > 10" +
            "             AND ((:cnacIdObj1 IS NOT NULL AND :cnacIdObj2 IS NOT NULL AND" +
            "                 ((:cnacId1 = CNAC1.ID AND cnac2.id = :cnacId2) or" +
            "                 (:cnacId1 = CNAC2.ID AND cnac3.id = :cnacId2) OR" +
            "                 (:cnacId1 = CNAC3.ID AND cnac4.id = :cnacId2) OR" +
            "                 (:cnacId1 = CNAC4.ID AND cnac5.id = :cnacId2) OR" +
            "                 (:cnacId1 = CNAC5.ID AND cnac6.id = :cnacId2)))" +
            "                 OR " +
            "                 ((:cnacIdObj1 IS NOT NULL AND :cnacIdObj2 IS NULL) AND" +
            "                 (:cnacId1 = CNAC1.ID OR :cnacId1 = CNAC2.ID OR" +
            "                 :cnacId1 = CNAC3.ID OR :cnacId1 = CNAC4.ID OR" +
            "                 :cnacId1 = CNAC5.ID OR :cnacId1 = CNAC6.ID))" +
            "                 OR (:cnacIdObj1 IS NULL AND :cnacIdObj2 IS NULL)) " +

            "             AND ((:cnatIdObj1 IS NOT NULL AND :cnatIdObj2 IS NOT NULL AND" +
            "                 ((:cnatId1 = CNAT1.ID AND CNAT2.ID = :cnatId2) OR" +
            "                 (:cnatId1 = CNAT2.ID AND CNAT3.ID = :cnatId2) OR" +
            "                 (:cnatId1 = CNAT3.ID AND CNAT4.ID = :cnatId2) OR" +
            "                 (:cnatId1 = CNAT4.ID AND CNAT5.ID = :cnatId2) OR" +
            "                 (:cnatId1 = CNAT5.ID AND CNAT6.ID = :cnatId2)))" +
            "                 OR " +
            "                 ((:cnatIdObj1 IS NOT NULL AND :cnatIdObj2 IS NULL) AND" +
            "                 (:cnatId1 = CNAT1.ID OR :cnatId1 = CNAT2.ID OR" +
            "                 :cnatId1 = CNAT3.ID OR :cnatId1 = CNAT4.ID OR" +
            "                 :cnatId1 = CNAT5.ID OR :cnatId1 = CNAT6.ID))" +
            "                 OR (:cnatIdObj1 IS NULL AND :cnatIdObj2 IS NULL))" +

            "           GROUP BY FA2.FINANCIAL_ACCOUNT_PARENT_ID," +
            "                    FA2.ID," +
            "                    FA2.CODE," +
            "                    FA2.DESCRIPTION," +
            "                    FAS.SEQUENCE," +
            "                    CNAC1.CODE," +
            "                    CNAC2.CODE," +
            "                    CNAC3.CODE," +
            "                    CNAC4.CODE," +
            "                    CNAC5.CODE," +
            "                    CNAC6.CODE," +
            "                    CNAC1.NAME," +
            "                    CNAC2.NAME," +
            "                    CNAC3.NAME," +
            "                    CNAC4.NAME," +
            "                    CNAC5.NAME," +
            "                    CNAC6.NAME)" +
            "   WHERE (:remainOption = 0)" +
            "      OR (:remainOption = 1 AND" +
            "         (SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT) <> 0)" +
            "      OR (:remainOption = 2 AND" +
            "         (SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT) > 0)" +
            "      OR (:remainOption = 3 AND" +
            "         (SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT) < 0)" +
            "      OR (:remainOption = 4 AND" +
            "         (SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT) = 0)" +
            "   ORDER BY FINANCIAL_ACCOUNT_CODE)" +
            "SELECT FINANCIAL_ACCOUNT_DESC," +
            " FINANCIAL_ACCOUNT_ID, " +
            "       SUM_DEBIT," +
            "       SUM_CREDIT," +
            "       BEF_DEBIT," +
            "       BEF_CREDIT," +
            "       REM_DEBIT," +
            "       REM_CREDIT," +
            "       CENTRIC_ACCOUNT_DES," +
            "       0                      SUMMERIZE_AMOUNT," +
            "       1                      AS RECORD_TYPE" +
            "  FROM QRY" +
            " UNION " +
            " SELECT NULL FINANCIAL_ACCOUNT_DESC," +
            " NULL FINANCIAL_ACCOUNT_ID," +
            "       SUM(SUM_DEBIT) SUM_DEBIT," +
            "       SUM(SUM_CREDIT) SUM_CREDIT," +
            "       0 BEF_DEBIT," +
            "       0 BEF_CREDIT," +
            "       0 REM_DEBIT," +
            "       0 REM_CREDIT," +
            "       NULL CENTRIC_ACCOUNT_DES," +
            "       SUM(SUM_CREDIT) - SUM(SUM_DEBIT) SUMMERIZE_AMOUNT," +
            "       3 AS RECORD_TYPE" +
            "  FROM QRY "
            , nativeQuery = true)
    List<Object[]> findByFinancialPeriodByCentricBalanceReport(LocalDateTime fromDate, LocalDateTime toDate, String fromNumber, String toNumber, Long documentNumberingTypeId, Long ledgerTypeId
            , LocalDateTime periodStartDate, int length, String fromFinancialAccountCode,
                                                               String toFinancialAccountCode, Long organizationId, Object cnacIdObj1, Long cnacId1, Object cnacIdObj2, Long cnacId2, Object cnatIdObj1, Long cnatId1, Object cnatIdObj2, Long cnatId2, Long remainOption);

    @Query(value = " SELECT T.FINANCIAL_PERIOD_ID, T.DOCUMENT_DATE, T.financial_ledger_type_id " +
            "  FROM FNDC.FINANCIAL_DOCUMENT T " +
            " WHERE T.ID = :financialDocumentId "
            , nativeQuery = true)
    List<Object[]> getFinancialPeriodByFinancialDocumentId(Long financialDocumentId);
}
