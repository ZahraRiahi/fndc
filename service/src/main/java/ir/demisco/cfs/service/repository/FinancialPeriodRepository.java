package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FinancialPeriodRepository extends JpaRepository<FinancialPeriod, Long> {


    @Query("select fp from FinancialMonth fmn " +
            " join FinancialMonthType  fmt on fmt.id=fmn.financialMonthType.id  " +
            " join FinancialPeriodType fnp on fnp.id=fmt.financialPeriodType.id  " +
            " join FinancialPeriodTypeAssign  pt on pt.financialPeriodType.id=fnp.id  " +
            " join FinancialPeriod fp on fp.financialPeriodTypeAssign.id=pt.id  " +
            " where  TO_DATE(:date, 'yyyy-mm-dd') between fp.startDate and fp.endDate  " +
            "   and extract(month from TO_DATE(:date, 'yyyy-mm-dd')) = (fnp.fromMonth + (fmt.monthNumber - 1) )  " +
            "   and fmn.financialMonthStatus.id = 1  " +
            "   and fp.financialPeriodStatus.id =1  " +
            "   and pt.organization.id=:organizationId")
    List<FinancialPeriod> getPeriodByParam(String date, Long organizationId);

    @Query(value = " SELECT  MIN(FP.START_DATE)  " +
            "  FROM FNPR.FINANCIAL_PERIOD FP  " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT  " +
            "    ON FP.FINAN_PERIOD_TYPE_ASSIGN_ID = FPT.ID  " +
            "   AND FPT.ORGANIZATION_ID = :organizationId  " +
            "   AND FPT.DELETED_DATE IS NULL  " +
            "   AND FPT.ACTIVE_FLAG = 1  " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY  " +
            "    ON FPT.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID  " +
            " WHERE FP.FINANCIAL_PERIOD_STATUS_ID = 1  " +
            "   AND FP.DELETED_DATE IS NULL "
            , nativeQuery = true)
    LocalDateTime findByFinancialPeriodByOrganization(Long organizationId);

    @Query(value = " SELECT  MAX(FP.START_DATE)  " +
            "    FROM FNPR.FINANCIAL_PERIOD FP  " +
            "    INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT  " +
            "    ON FP.FINAN_PERIOD_TYPE_ASSIGN_ID = FPT.ID  " +
            "    AND FPT.ORGANIZATION_ID = :organizationId  " +
            "    AND FPT.DELETED_DATE IS NULL  " +
            "    AND FPT.ACTIVE_FLAG = 1  " +
            "    INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY  " +
            "    ON FPT.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID  " +
            "    WHERE FP.DELETED_DATE IS NULL  " +
            "    AND :startDate > = FP.START_DATE "
            , nativeQuery = true)
    LocalDateTime findByFinancialPeriodByOrganizationStartDate(Long organizationId, LocalDateTime startDate);

    @Query(value = " WITH MAIN_QRY AS " +
            " (SELECT DOCUMENT_DATE, " +
            "         DOCUMENT_SEQUENCE, " +
            "         DOCUMENT_NUMBER, " +
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
            "             AND FD.DOCUMENT_DATE >= :periodStartDate " +
            "             AND ((:dateFilterFlg = 1 AND FD.DOCUMENT_DATE < :fromDate) OR " +
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
            "                 FD.DOCUMENT_DATE BETWEEN :fromDate AND " +
            "                 NVL(:toDate, SYSDATE) OR " +
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
            "               NULL AS DOCUMENT_NUMBER, " +
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
            "             AND FD.DOCUMENT_DATE >= :periodStartDate " +
            "             AND ((:dateFilterFlg = 1 AND FD.DOCUMENT_DATE < :fromDate) OR " +
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
            "                 FD.DOCUMENT_DATE BETWEEN :fromDate AND " +
            "                 NVL(:toDate, SYSDATE) OR " +
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
    Page<Object[]> findByFinancialPeriodByParam(Long organizationId, Long ledgerTypeId, LocalDateTime periodStartDate,
                                                Long dateFilterFlg, LocalDateTime fromDate, Long documentNumberingTypeId,
                                                String fromNumber, Object centricAccount1, Long centricAccountId1,
                                                Object centricAccount2, Long centricAccountId2, Object referenceNumberObject,
                                                Long referenceNumber, String toNumber, Long financialAccountId,
                                                Long summarizingType, LocalDateTime toDate, Pageable pageable);

    @Query(value = " SELECT  MIN(FP.START_DATE)  " +
            "      FROM FNPR.FINANCIAL_PERIOD FP  " +
            "     INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT  " +
            "        ON FP.FINAN_PERIOD_TYPE_ASSIGN_ID = FPT.ID  " +
            "       AND FPT.ORGANIZATION_ID = :organizationId  " +
            "       AND FPT.DELETED_DATE IS NULL  " +
            "       AND FPT.ACTIVE_FLAG = 1  " +
            "     INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY  " +
            "        ON FPT.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID  " +
            "     WHERE FP.DELETED_DATE IS NULL  "
            , nativeQuery = true)
    LocalDateTime findByFinancialPeriodByOrganization2(Long organizationId);

    @Query(value = " WITH MAIN_QRY AS" +
            " (SELECT ID ACCOUNT_ID," +
            "         CODE ACCOUNT_CODE," +
            "         DESCRIPTION ACCOUNT_DESCRIPTION,               " +
            "         CENTRIC_ACCOUNT_ID_1," +
            "         CENTRIC_ACCOUNT_ID_2," +
            "         CENTRIC_ACCOUNT_ID_3," +
            "         CENTRIC_ACCOUNT_ID_4," +
            "         CENTRIC_ACCOUNT_ID_5," +
            "         CENTRIC_ACCOUNT_ID_6," +
            "         NAME_CNAC1 CENTRIC_ACCOUNT_DES_1," +
            "         NAME_CNAC2 CENTRIC_ACCOUNT_DES_2," +
            "         NAME_CNAC3 CENTRIC_ACCOUNT_DES_3," +
            "         NAME_CNAC4 CENTRIC_ACCOUNT_DES_4," +
            "         NAME_CNAC5 CENTRIC_ACCOUNT_DES_5," +
            "         NAME_CNAC6 CENTRIC_ACCOUNT_DES_6," +
            "         DEBIT_AMOUNT," +
            "         CREDIT_AMOUNT," +
            "         ABS(CASE" +
            "               WHEN SUM(CREDIT_AMOUNT - DEBIT_AMOUNT)" +
            "                OVER(ORDER BY ID," +
            "                         CENTRIC_ACCOUNT_ID_1," +
            "                         CENTRIC_ACCOUNT_ID_2," +
            "                         CENTRIC_ACCOUNT_ID_3," +
            "                         CENTRIC_ACCOUNT_ID_4," +
            "                         CENTRIC_ACCOUNT_ID_5," +
            "                         CENTRIC_ACCOUNT_ID_6,TYP) < 0 THEN" +
            "                SUM(CREDIT_AMOUNT - DEBIT_AMOUNT)" +
            "                OVER(ORDER BY ID," +
            "                     CENTRIC_ACCOUNT_ID_1," +
            "                     CENTRIC_ACCOUNT_ID_2," +
            "                     CENTRIC_ACCOUNT_ID_3," +
            "                     CENTRIC_ACCOUNT_ID_4," +
            "                     CENTRIC_ACCOUNT_ID_5," +
            "                     CENTRIC_ACCOUNT_ID_6,TYP)" +
            "               ELSE" +
            "                0" +
            "             END) AS REMAIN_DEBIT," +
            "         CASE" +
            "           WHEN SUM(CREDIT_AMOUNT - DEBIT_AMOUNT)" +
            "            OVER(ORDER BY ID," +
            "                     CENTRIC_ACCOUNT_ID_1," +
            "                     CENTRIC_ACCOUNT_ID_2," +
            "                     CENTRIC_ACCOUNT_ID_3," +
            "                     CENTRIC_ACCOUNT_ID_4," +
            "                     CENTRIC_ACCOUNT_ID_5," +
            "                     CENTRIC_ACCOUNT_ID_6,TYP) > 0 THEN" +
            "            SUM(CREDIT_AMOUNT - DEBIT_AMOUNT)" +
            "            OVER(ORDER BY ID," +
            "                 CENTRIC_ACCOUNT_ID_1," +
            "                 CENTRIC_ACCOUNT_ID_2," +
            "                 CENTRIC_ACCOUNT_ID_3," +
            "                 CENTRIC_ACCOUNT_ID_4," +
            "                 CENTRIC_ACCOUNT_ID_5," +
            "                 CENTRIC_ACCOUNT_ID_6,TYP)" +
            "           ELSE" +
            "            0" +
            "         END REMAIN_CREDIT," +
            "         SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) OVER(ORDER BY ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6,TYP) REMAIN_AMOUNT," +
            "         0 SUM_DEBIT," +
            "         0 SUM_CREDIT," +
            "         0 SUMMERIZE_DEBIT," +
            "         0 SUMMERIZE_CREDIT," +
            "         0 SUMMERIZE_AMOUNT," +
            "         RECORD_TYP" +
            "    FROM (SELECT NULL AS CENTRIC_ACCOUNT_ID_1," +
            "                 NULL AS CENTRIC_ACCOUNT_ID_2," +
            "                 NULL AS CENTRIC_ACCOUNT_ID_3," +
            "                 NULL AS CENTRIC_ACCOUNT_ID_4," +
            "                 NULL AS CENTRIC_ACCOUNT_ID_5," +
            "                 NULL AS CENTRIC_ACCOUNT_ID_6," +
            "                 NULL AS CODE_CNAC1," +
            "                 NULL AS CODE_CNAC2," +
            "                 NULL AS CODE_CNAC3," +
            "                 NULL AS CODE_CNAC4," +
            "                 NULL AS CODE_CNAC5," +
            "                 NULL AS CODE_CNAC6," +
            "                 NULL AS NAME_CNAC1," +
            "                 NULL AS NAME_CNAC2," +
            "                 NULL AS NAME_CNAC3," +
            "                 NULL AS NAME_CNAC4," +
            "                 NULL AS NAME_CNAC5," +
            "                 NULL AS NAME_CNAC6," +
            "                 CASE" +
            "                   WHEN SUM(FDI.CREDIT_AMOUNT) > SUM(FDI.DEBIT_AMOUNT) THEN" +
            "                    SUM(FDI.CREDIT_AMOUNT) - SUM(FDI.DEBIT_AMOUNT)" +
            "                   ELSE" +
            "                    0" +
            "                 END CREDIT_AMOUNT," +
            "                 CASE" +
            "                   WHEN SUM(FDI.DEBIT_AMOUNT) > SUM(FDI.CREDIT_AMOUNT) THEN" +
            "                    SUM(FDI.DEBIT_AMOUNT) - SUM(FDI.CREDIT_AMOUNT)" +
            "                   ELSE" +
            "                    0" +
            "                 END DEBIT_AMOUNT," +
            "                 NULL TYP,                 0 AS ID," +
            "                 NULL AS CODE," +
            "                 NULL AS DESCRIPTION," +
            "                 1 AS RECORD_TYP" +
            "            FROM FNDC.FINANCIAL_DOCUMENT FD" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "              ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID" +
            "             AND FDI.DELETED_DATE IS NULL" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA" +
            "              ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID" +
            "             AND FA.DELETED_DATE IS NULL" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER FDN" +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "             AND FDN.DELETED_DATE IS NULL" +
            "            LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR" +
            "              ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID" +
            "             AND FDR.DELETED_DATE IS NULL" +
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
            "              ON CNAC6.ID = FDI.CENTRIC_ACCOUNT_ID_6" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS" +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID" +
            "           WHERE FD.ORGANIZATION_ID = :organizationId" +
            "             AND FD.DELETED_DATE IS NULL" +
            "             AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId" +
            "             AND FD.DOCUMENT_DATE >= :periodStartDate" +
            "             AND ((:dateFilterFlg = 1 AND FD.DOCUMENT_DATE < :fromDate) OR" +
            "                 (:dateFilterFlg = 0 AND" +
            "                 FD.DOCUMENT_DATE <=" +
            "                 (SELECT INER_DOC.DOCUMENT_DATE" +
            "                      FROM FNDC.FINANCIAL_DOCUMENT INER_DOC" +
            "                     INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM" +
            "                        ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID" +
            "                       AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID =" +
            "                           :documentNumberingTypeId " +
            "                       AND INER_NUM.DOCUMENT_NUMBER = :fromNumber" +
            "                     WHERE INER_DOC.DELETED_DATE IS NULL" +
            "                       AND INER_NUM.DELETED_DATE IS NULL)))" +
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID =" +
            "                 :documentNumberingTypeId" +
            "           AND (:centricAccount1 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId1) " +
            "           AND (:centricAccount2 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId2) " +
            "           AND (:referenceNumberObject IS NULL OR FDR.REFRENCE_NUMBER = :referenceNumber)   " +
            "             AND ((:dateFilterFlg = 0 AND" +
            "                 FDN.DOCUMENT_NUMBER <" +
            "                 NVL(:fromNumber, FDN.DOCUMENT_NUMBER)) OR" +
            "                 :dateFilterFlg = 1)" +
            "             AND (EXISTS (SELECT 1" +
            "                            FROM FNAC.ACCOUNT_STRUCTURE_LEVEL ASL" +
            "                           WHERE ASL.FINANCIAL_ACCOUNT_ID = FA.ID " +
            " AND(:financialAccount IS NULL OR ASL.RELATED_ACCOUNT_ID=:financialAccountId) " +
            "                             AND ASL.DELETED_DATE IS NULL))" +
            "             AND FDS.CODE > 10" +
            "          UNION " +
            "          SELECT FDI.CENTRIC_ACCOUNT_ID_1," +
            "                 FDI.CENTRIC_ACCOUNT_ID_2," +
            "                 FDI.CENTRIC_ACCOUNT_ID_3," +
            "                 FDI.CENTRIC_ACCOUNT_ID_4," +
            "                 FDI.CENTRIC_ACCOUNT_ID_5," +
            "                 FDI.CENTRIC_ACCOUNT_ID_6," +
            "                 CNAC1.CODE               CODE_CNAC1," +
            "                 CNAC2.CODE               CODE_CNAC2," +
            "                 CNAC3.CODE               CODE_CNAC3," +
            "                 CNAC4.CODE               CODE_CNAC4," +
            "                 CNAC5.CODE               CODE_CNAC5," +
            "                 CNAC6.CODE               CODE_CNAC6," +
            "                 CNAC1.NAME               NAME_CNAC1," +
            "                 CNAC2.NAME               NAME_CNAC2," +
            "                 CNAC3.NAME               NAME_CNAC3," +
            "                 CNAC4.NAME               NAME_CNAC4," +
            "                 CNAC5.NAME               NAME_CNAC5," +
            "                 CNAC6.NAME               NAME_CNAC6," +
            "                 SUM(FDI.CREDIT_AMOUNT) CREDIT_AMOUNT," +
            "                 SUM(FDI.DEBIT_AMOUNT) DEBIT_AMOUNT," +
            "                   CASE" +
            "                   WHEN FDI.CREDIT_AMOUNT > 0 THEN" +
            "                    1" +
            "                   WHEN FDI.DEBIT_AMOUNT > 0 THEN" +
            "                    0" +
            "                 END AS TYP," +
            "                 FA.ID," +
            "                 FA.CODE AS CODE," +
            "                 FA.DESCRIPTION," +
            "                 2 AS RECORD_TYP" +
            "            FROM fndc.FINANCIAL_DOCUMENT FD" +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_ITEM FDI" +
            "              ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID" +
            "             AND FDI.DELETED_DATE IS NULL" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA" +
            "              ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID" +
            "             AND FA.DELETED_DATE IS NULL" +
            "             INNER JOIN FNAC.ACCOUNT_STRUCTURE_LEVEL ASL" +
            "              ON ASL.FINANCIAL_ACCOUNT_ID = FA.ID" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA2" +
            "              ON FA2.ID = ASL.RELATED_ACCOUNT_ID" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT_STRUCTURE FAS" +
            "              ON FA2.FINANCIAL_ACCOUNT_STRUCTURE_ID = FAS.ID" +
            "           INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FDN" +
            "              ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "             AND FDN.DELETED_DATE IS NULL" +
            "            LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR" +
            "              ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID" +
            "             AND FDR.DELETED_DATE IS NULL" +
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
            "              ON CNAC6.ID = FDI.CENTRIC_ACCOUNT_ID_6" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS" +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID" +
            "             AND FDS.DELETED_DATE IS NULL" +
            "           WHERE FD.ORGANIZATION_ID = :organizationId" +
            "             AND FD.DELETED_DATE IS NULL" +
            "             AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId" +
            "             AND ((:dateFilterFlg = 1 AND" +
            "                 FD.DOCUMENT_DATE BETWEEN :fromDate AND" +
            "                 NVL(:toDate, SYSDATE) OR" +
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
            "             AND FDN.FINANCIAL_NUMBERING_TYPE_ID =" +
            "                 :documentNumberingTypeId" +
            "           AND (:centricAccount1 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId1) " +
            "           AND (:centricAccount2 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId2) " +
            "           AND (:referenceNumberObject IS NULL OR FDR.REFRENCE_NUMBER = :referenceNumber)   " +
            "             AND ((:dateFilterFlg = 0 AND" +
            "                 FDN.DOCUMENT_NUMBER >=" +
            "                 NVL(:fromNumber, FDN.DOCUMENT_NUMBER) AND" +
            "                 FDN.DOCUMENT_NUMBER <=" +
            "                 NVL(:toNumber, FDN.DOCUMENT_NUMBER)) OR" +
            "                 :dateFilterFlg = 1)" +
            "     AND FA2.ID =nvl(:financialAccountId, fdi.financial_account_id)" +
            "             AND FDS.CODE > 10" +
            "           GROUP BY FA.ID," +
            "                    FA.CODE," +
            "                    FA.DESCRIPTION," +
            "                                  CASE" +
            "                   WHEN FDI.CREDIT_AMOUNT > 0 THEN" +
            "                    1" +
            "                   WHEN FDI.DEBIT_AMOUNT > 0 THEN" +
            "                    0" +
            "                 END ," +
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
            "                    CNAC6.NAME)" +
            "   ORDER BY ID)" +
            "SELECT * " +
            "  FROM (SELECT * " +
            "          FROM MAIN_QRY " +
            "        UNION " +
            "        SELECT NULL ACCOUNT_ID," +
            "               NULL ACCOUNT_CODE," +
            "               NULL ACCOUNT_DESCRIPTION," +
            "               NULL CENTRIC_ACCOUNT_ID_1," +
            "               NULL CENTRIC_ACCOUNT_ID_2," +
            "               NULL CENTRIC_ACCOUNT_ID_3," +
            "               NULL CENTRIC_ACCOUNT_ID_4," +
            "               NULL CENTRIC_ACCOUNT_ID_5," +
            "               NULL CENTRIC_ACCOUNT_ID_6," +
            "               NULL CENTRIC_ACCOUNT_DES_1," +
            "               NULL CENTRIC_ACCOUNT_DES_2," +
            "               NULL CENTRIC_ACCOUNT_DES_3," +
            "               NULL CENTRIC_ACCOUNT_DES_4," +
            "               NULL CENTRIC_ACCOUNT_DES_5," +
            "               NULL CENTRIC_ACCOUNT_DES_6," +
            "               NULL DEBIT_AMOUNT," +
            "               NULL CREDIT_AMOUNT," +
            "               NULL REMAIN_DEBIT," +
            "               NULL REMAIN_CREDIT," +
            "               NULL REMAIN_AMOUNT," +
            "               SUM(MAIN_QRY.DEBIT_AMOUNT) SUM_DEBIT," +
            "               SUM(MAIN_QRY.CREDIT_AMOUNT) SUM_CREDIT," +
            "               CASE" +
            "                 WHEN SUM(MAIN_QRY.DEBIT_AMOUNT) -" +
            "                      SUM(MAIN_QRY.CREDIT_AMOUNT) > 0 THEN" +
            "                  SUM(MAIN_QRY.DEBIT_AMOUNT) - SUM(MAIN_QRY.CREDIT_AMOUNT)" +
            "                 ELSE" +
            "                  0" +
            "               END AS SUMMERIZE_DEBIT," +
            "               CASE" +
            "                 WHEN SUM(MAIN_QRY.CREDIT_AMOUNT) -" +
            "                      SUM(MAIN_QRY.DEBIT_AMOUNT) > 0 THEN" +
            "                  SUM(MAIN_QRY.CREDIT_AMOUNT) - SUM(MAIN_QRY.DEBIT_AMOUNT)" +
            "                 ELSE" +
            "                  0" +
            "               END AS SUMMERIZE_CREDIT," +
            "               SUM(MAIN_QRY.CREDIT_AMOUNT) - SUM(MAIN_QRY.DEBIT_AMOUNT) AS SUMMERIZE_AMOUNT," +
            "               3 AS RECORD_TYP" +
            "          FROM MAIN_QRY)" +
            " ORDER BY RECORD_TYP "
            , nativeQuery = true)
    Page<Object[]> findByFinancialAccountCentricTurnOver(Long organizationId, Long ledgerTypeId, LocalDateTime periodStartDate,
                                                         Long dateFilterFlg, LocalDateTime fromDate, Long documentNumberingTypeId,
                                                         String fromNumber, Object centricAccount1, Long centricAccountId1,
                                                         Object centricAccount2, Long centricAccountId2, Object referenceNumberObject,
                                                         Long referenceNumber, Object financialAccount, Long financialAccountId, LocalDateTime toDate, String toNumber,
                                                         Pageable pageable);


    @Query(value = " SELECT FINANCIAL_ACCOUNT_PARENT_ID, " +
            "       FINANCIAL_ACCOUNT_ID, " +
            "       FINANCIAL_ACCOUNT_code, " +
            "       FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "       FINANCIAL_ACCOUNT_Level, " +
            "       SUM_DEBIT, " +
            "       SUM_CREDIT, " +
            "       BEF_DEBIT, " +
            "       BEF_CREDIT, " +
            "       DECODE(SIGN(SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT), " +
            "              1, " +
            "              SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT, " +
            "              0) REM_DEBIT, " +
            "       DECODE(SIGN(SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT), " +
            "              -1, " +
            "              ABS(SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT), " +
            "              0) REM_CREDIT " +
            "  FROM (SELECT FA2.FINANCIAL_ACCOUNT_PARENT_ID, " +
            "               FA2.ID FINANCIAL_ACCOUNT_ID, " +
            "               FA2.CODE FINANCIAL_ACCOUNT_code, " +
            "               FA2.DESCRIPTION FINANCIAL_ACCOUNT_DESCRIPTION, " +
            "               fas.sequence FINANCIAL_ACCOUNT_Level, " +
            "               SUM(CASE " +
            "                     WHEN (FD.DOCUMENT_DATE BETWEEN :fromDate AND :toDate) AND " +
            "                          (FDN.DOCUMENT_NUMBER BETWEEN :fromNumber AND " +
            "                          :toNumber) THEN " +
            "                      FDI.DEBIT_AMOUNT " +
            "                     ELSE " +
            "                      0 " +
            "                   END) SUM_DEBIT, " +
            "               SUM(CASE" +
            "                     WHEN (FD.DOCUMENT_DATE BETWEEN :fromDate AND :toDate) AND " +
            "                          (FDN.DOCUMENT_NUMBER BETWEEN :fromNumber AND " +
            "                          :toNumber) THEN " +
            "                      FDI.CREDIT_AMOUNT " +
            "                     ELSE " +
            "                      0 " +
            "                   END) SUM_CREDIT, " +
            "               SUM(CASE " +
            "                     WHEN FD.DOCUMENT_DATE <= :fromDate AND " +
            "                          FDN.DOCUMENT_NUMBER < :fromNumber THEN " +
            "                      FDI.DEBIT_AMOUNT " +
            "                     ELSE " +
            "                      0 " +
            "                   END) BEF_DEBIT, " +
            "               SUM(CASE " +
            "                     WHEN FD.DOCUMENT_DATE <= :fromDate AND " +
            "                          FDN.DOCUMENT_NUMBER < :fromNumber THEN " +
            "                      FDI.CREDIT_AMOUNT " +
            "                     ELSE " +
            "                      0 " +
            "                   END) BEF_CREDIT " +
            "          FROM FNDC.FINANCIAL_DOCUMENT FD " +
            "         INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI " +
            "            ON FDI.FINANCIAL_DOCUMENT_ID = FD.ID " +
            "           AND FDI.DELETED_DATE IS NULL " +
            "         INNER JOIN FNAC.FINANCIAL_ACCOUNT FA " +
            "            ON FDI.FINANCIAL_ACCOUNT_ID = FA.ID " +
            "         INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FDN " +
            "            ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID " +
            "           AND FDN.DELETED_DATE IS NULL " +
            "           AND FDN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "         INNER JOIN FNAC.ACCOUNT_STRUCTURE_LEVEL ASL " +
            "            ON ASL.FINANCIAL_ACCOUNT_ID = FA.ID " +
            "           AND ASL.DELETED_DATE IS NULL " +
            "         INNER JOIN FNAC.FINANCIAL_ACCOUNT FA2 " +
            "            ON FA2.ID = ASL.RELATED_ACCOUNT_ID " +
            "         INNER JOIN fnac.financial_account_structure fas " +
            "            ON fa2.financial_account_structure_id = fas.id " +
            " INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS " +
            "            ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID " +
            "           AND FDS.DELETED_DATE IS NULL  " +
            "         WHERE FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId " +
            "           AND ((fas.sequence = :structureLevel AND :showHigherLevels = 0) OR " +
            "               (fas.sequence <= :structureLevel AND " +
            "               :showHigherLevels = 1)) " +
            "           AND FD.DOCUMENT_DATE BETWEEN :periodStartDate AND :toDate " +
            "           AND (FDN.DOCUMENT_NUMBER <= :toNumber OR :toNumber IS NULL) " +
            "           and ((SUBSTR(fa.code, 1, :length) >= :fromFinancialAccountCode) or " +
            "               :fromFinancialAccountCode is null) " +
            "           and ((SUBSTR(fa.code, 1, :length) <= :toFinancialAccountCode) or " +
            "               :toFinancialAccountCode is null) " +
            "           AND FD.DELETED_DATE IS NULL " +
            "           AND FD.ORGANIZATION_ID = :organizationId" +
            " AND FDS.CODE > 10 " +
            "         GROUP BY FA2.FINANCIAL_ACCOUNT_PARENT_ID, " +
            "                  FA2.ID, " +
            "                  FA2.CODE, " +
            "                  FA2.DESCRIPTION, " +
            "                  fas.sequence " +
            "        )" +
            "  WHERE (:hasRemain = 1 AND :showHigherLevels= 0 AND " +
            "       (SUM_DEBIT + BEF_DEBIT - SUM_CREDIT - BEF_CREDIT) <> 0) " +
            "    OR (:hasRemain = 0) " +
            "    OR :showHigherLevels= 1 " +
            " ORDER BY FINANCIAL_ACCOUNT_code "
            , nativeQuery = true)
    Page<Object[]> findByFinancialPeriodByBalanceReport(LocalDateTime fromDate, LocalDateTime toDate, String fromNumber, String toNumber, Long documentNumberingTypeId, Long ledgerTypeId,
                                                        Long structureLevel, Boolean showHigherLevels, LocalDateTime periodStartDate, int length, String fromFinancialAccountCode,
                                                        String toFinancialAccountCode, Long organizationId, Boolean hasRemain, Pageable pageable);

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
            "            on fp.finan_period_type_assign_id = fpt.id   " +
            "            and fpt.organization_id = :organizationId " +
            "            and fpt.deleted_date is null   " +
            "            and fpt.active_flag = 1 " +
            "    inner join" +
            "        fnpr.financial_period_type fpty    " +
            "            on fpt.financial_period_type_id = fpty.id " +
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

    @Query(value = " SELECT CASE " +
            "         WHEN FMN.FINANCIAL_MONTH_STATUS_ID = 2 THEN " +
            "          0 " +
            "         ELSE " +
            "          1 " +
            "       END " +
            "  FROM FNPR.FINANCIAL_MONTH FMN " +
            " INNER JOIN FNPR.FINANCIAL_MONTH_TYPE FMT " +
            "    ON FMT.ID = FMN.FINANCIAL_MONTH_TYPE_ID " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FNP " +
            "    ON FNP.ID = FMT.FINANCIAL_PERIOD_TYPE_ID " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN PT " +
            "    ON PT.FINANCIAL_PERIOD_TYPE_ID = FNP.ID " +
            " INNER JOIN fnpr.FINANCIAL_PERIOD FP " +
            "    ON FP.FINAN_PERIOD_TYPE_ASSIGN_ID = PT.ID " +
            "   AND FMN.FINANCIAL_PERIOD_ID = FP.ID " +
            " WHERE FP.ID = :financialPeriodId " +
            "   AND (CASE CALENDAR_TYPE_ID " +
            "         WHEN 2 THEN " +
            "          EXTRACT(MONTH FROM " +
            "                  to_date(:date, 'mm/dd/yyyy')) " +
            "         WHEN 1 THEN " +
            "          TO_NUMBER(SUBSTR(TO_CHAR(to_date(:date, 'mm/dd/yyyy')," +
            "                                   'yyyy/mm/dd'," +
            "                                   'NLS_CALENDAR=persian')," +
            "                           6," +
            "                           2))" +
            "       END = CASE " +
            "         WHEN FNP.CURRENT_YEAR_FLAG = 1 THEN " +
            "          FNP.FROM_MONTH + FMT.MONTH_NUMBER - 1 " +
            "         ELSE " +
            "          CASE " +
            "            WHEN FNP.FROM_MONTH + FMT.MONTH_NUMBER - 1 > 12 THEN " +
            "             FNP.FROM_MONTH + FMT.MONTH_NUMBER - 13 " +
            "            ELSE " +
            "             FNP.FROM_MONTH + FMT.MONTH_NUMBER - 1 " +
            "          END" +
            "       END ) "
            , nativeQuery = true)
    Long findFinancialPeriodByFinancialPeriodIdAndDate(Long financialPeriodId, String date);

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
            "            INNER JOIN FNPR.FINANCIAL_PERIOD FNP" +
            "               ON FNP.ID = FNMN.FINANCIAL_PERIOD_ID" +
            "            INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FNPT" +
            "               ON FNPT.ID = FNP.FINANCIAL_PERIOD_TYPE_ID" +
            "            INNER JOIN FNPR.FINANCIAL_MONTH_TYPE FNMT" +
            "               ON FNMT.ID = FNMN.FINANCIAL_MONTH_TYPE_ID" +
            "            WHERE FNMN.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "              AND FNLM.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "              AND (CASE CALENDAR_TYPE_ID" +
            "                    WHEN 2 THEN" +
            "          EXTRACT(MONTH FROM " +
            "                  to_date(:date, 'mm/dd/yyyy')) " +
            "         WHEN 1 THEN " +
            "          TO_NUMBER(SUBSTR(TO_CHAR(to_date(:date, 'mm/dd/yyyy')," +
            "                                   'yyyy/mm/dd'," +
            "                                   'NLS_CALENDAR=persian')," +
            "                           6," +
            "                           2))" +
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
            "           0) AS CODE" +
            "  FROM DUAL"
            , nativeQuery = true)
    Long findFinancialPeriodByIdAndLedgerTypeAndDate(Long financialPeriodId, Long financialLedgerTypeId, String date);
}
