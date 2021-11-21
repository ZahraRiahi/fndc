package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
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

    @Query(value = " SELECT alll.DOCUMENT_DATE,  " +
            "                       alll.DOCUMENT_NUMBER,  " +
            "                       alll.DESCRIPTION,  " +
            "                       alll.DEBIT_AMOUNT,  " +
            "                       alll.CREDIT_AMOUNT,  " +
            "                       ABS(CASE  " +
            "                             WHEN SUM(alll.CREDIT_AMOUNT - alll.DEBIT_AMOUNT)  " +
            "                              OVER(ORDER BY alll.ID,  " +
            "                                       alll.DOCUMENT_NUMBER,  " +
            "                                       alll.DOCUMENT_DATE,  " +
            "                                       alll.FINANCIAL_DOCUMENT_ID) < 0 THEN  " +
            "                              SUM(alll.CREDIT_AMOUNT - alll.DEBIT_AMOUNT)  " +
            "                              OVER(ORDER BY ID,  " +
            "                                   alll.DOCUMENT_NUMBER,  " +
            "                                   alll.DOCUMENT_DATE,  " +
            "                                   alll.FINANCIAL_DOCUMENT_ID)  " +
            "                             ELSE " +
            "                              0  " +
            "                           END) AS REMAIN_DEBIT,  " +
            "                       CASE  " +
            "                         WHEN SUM(alll.CREDIT_AMOUNT - alll.DEBIT_AMOUNT)  " +
            "                          OVER(ORDER BY alll.ID,  " +
            "                                   alll.DOCUMENT_NUMBER,  " +
            "                                   alll.DOCUMENT_DATE) > 0 THEN  " +
            "                          SUM(alll.CREDIT_AMOUNT - alll.DEBIT_AMOUNT)  " +
            "                          OVER(ORDER BY alll.ID,  " +
            "                               alll.DOCUMENT_NUMBER,  " +
            "                               alll.DOCUMENT_DATE,  " +
            "                               alll.FINANCIAL_DOCUMENT_ID)  " +
            "                         ELSE  " +
            "                          0  " +
            "                       END REMAIN_CREDIT,  " +
            "                       SUM(alll.CREDIT_AMOUNT - alll.DEBIT_AMOUNT) OVER(ORDER BY alll.ID, alll.DOCUMENT_NUMBER, alll.DOCUMENT_DATE, FINANCIAL_DOCUMENT_ID) REMAIN_AMOUNT  " +
            "                  FROM (SELECT NULL AS FINANCIAL_DOCUMENT_ID,  " +
            "                               SUM(FDI.CREDIT_AMOUNT) CREDIT_AMOUNT,  " +
            "                               SUM(FDI.DEBIT_AMOUNT) DEBIT_AMOUNT,  " +
            "                               NULL AS DOCUMENT_NUMBER,  " +
            "                               NULL AS DOCUMENT_DATE,  " +
            "                               'قبل از دوره' AS DESCRIPTION,  " +
            "                               0 AS ID,  " +
            "                               NULL AS CODE  " +
            "                          FROM FNDC.FINANCIAL_DOCUMENT FD  " +
            "                         INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI  " +
            "                            ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID  " +
            "                           AND FDI.DELETED_DATE IS NULL  " +
            "                         INNER JOIN FNAC.FINANCIAL_ACCOUNT FA  " +
            "                            ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID  " +
            "                           AND FA.DELETED_DATE IS NULL  " +
            "                         INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER FDN  " +
            "                            ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID  " +
            "                           AND FDN.DELETED_DATE IS NULL  " +
            "                          LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR  " +
            "                            ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID  " +
            "                           AND FDR.DELETED_DATE IS NULL  " +
            "                         WHERE FD.ORGANIZATION_ID = :organizationId  " +
            "                           AND FD.DELETED_DATE IS NULL  " +
            "                           AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId  " +
            "                           AND FD.DOCUMENT_DATE >= :periodStartDate  " +
            "                           AND ((:dateFilterFlg= 1 AND FD.DOCUMENT_DATE < :fromDate) OR  " +
            "                               (:dateFilterFlg= 0 AND  " +
            "                               FD.DOCUMENT_DATE <=  " +
            "                               (SELECT INER_DOC.DOCUMENT_DATE  " +
            "                                    FROM FNDC.FINANCIAL_DOCUMENT INER_DOC  " +
            "                                   INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM  " +
            "                                      ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID  " +
            "                                     AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID =  " +
            "                                         :documentNumberingTypeId  " +
            "                                     AND INER_NUM.DOCUMENT_NUMBER = :fromNumber  " +
            "                                   WHERE INER_DOC.DELETED_DATE IS NULL  " +
            "                                     AND INER_NUM.DELETED_DATE IS NULL)))  " +
            "                           AND FDN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId  " +
            "                 AND ( :centricAccount1 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId1 )   " +
            "                                       AND ( :centricAccount2 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId2 )   " +
            "                                       AND (:referenceNumberObject IS NULL OR FDR.REFRENCE_NUMBER = :referenceNumber)   " +
            "                           AND ((:dateFilterFlg= 0 AND  " +
            "                               FDN.DOCUMENT_NUMBER <  " +
            "                               NVL(:fromNumber, FDN.DOCUMENT_NUMBER)) OR  " +
            "                               :dateFilterFlg= 1)  " +
            "                           AND (EXISTS (SELECT 1  " +
            "                                          FROM FNAC.ACCOUNT_STRUCTURE_LEVEL ASL  " +
            "                                         WHERE ASL.FINANCIAL_ACCOUNT_ID = FA.ID  " +
            "                                           AND ASL.RELATED_ACCOUNT_ID = :financialAccountId  " +
            "                                           AND ASL.DELETED_DATE IS NULL))  " +
            "                        UNION  " +
            "                        select all2.FINANCIAL_DOCUMENT_ID,  " +
            "                               sum(all2.CREDIT_AMOUNT) as CREDIT_AMOUNT,  " +
            "                               SUM(all2.DEBIT_AMOUNT) as DEBIT_AMOUNT,  " +
            "                               all2.DOCUMENT_NUMBER,  " +
            "                               all2.DOCUMENT_DATE,  " +
            "                               all2.DESCRIPTION,  " +
            "                               all2.ID,  " +
            "                               all2.CODE  " +
            "                          from (SELECT CASE  " +
            "                                         WHEN :summarizingType = 1 THEN  " +
            "                                          FD.ID  " +
            "                                         ELSE  " +
            "                                          NULL  " +
            "                                       END as FINANCIAL_DOCUMENT_ID,  " +
            "                                       FDI.CREDIT_AMOUNT,  " +
            "                                       FDI.DEBIT_AMOUNT,  " +
            "                                       CASE  " +
            "                                         WHEN :summarizingType = 1 THEN  " +
            "                                          FDN.DOCUMENT_NUMBER  " +
            "                                         ELSE  " +
            "                                          NULL  " +
            "                                       END AS DOCUMENT_NUMBER,  " +
            "                                       CASE  " +
            "                                         WHEN :summarizingType IN (1, 2) THEN  " +
            "                                          TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE,  " +
            "                                                                  'mm/dd/yyyy'),  " +
            "                                                          'mm/dd/yyyy'),  " +
            "                                                  'YYYY/MM/DD' , " +
            "                                                  'NLS_CALENDAR=persian')  " +
            "                                         ELSE  " +
            "                                          TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE,  " +
            "                                                                  'mm/dd/yyyy'),  " +
            "                                                          'mm/dd/yyyy'),  " +
            "                                                  'YYYY/MM' , " +
            "                                                  'NLS_CALENDAR=persian')  " +
            "                                       END  " +
            "                                       AS DOCUMENT_DATE , " +
            "                                       CASE  " +
            "                                         WHEN :summarizingType = 1 THEN  " +
            "                                          FD.DESCRIPTION  " +
            "                                         ELSE  " +
            "                                          NULL  " +
            "                                       END AS DESCRIPTION,  " +
            "                                       CASE  " +
            "                                         WHEN :summarizingType = 1 THEN  " +
            "                                          FA.ID  " +
            "                                         ELSE  " +
            "                                          NULL  " +
            "                                       END as ID,  " +
            "                                       CASE  " +
            "                                         WHEN :summarizingType = 1 THEN  " +
            "                                          FA.CODE  " +
            "                                         ELSE  " +
            "                                          NULL  " +
            "                                       END as CODE  " +
            "                                  FROM fndc.FINANCIAL_DOCUMENT FD  " +
            "                                 INNER JOIN fndc.FINANCIAL_DOCUMENT_ITEM FDI  " +
            "                                    ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID  " +
            "                                   AND FDI.DELETED_DATE IS NULL  " +
            "                                 INNER JOIN FNAC.FINANCIAL_ACCOUNT FA  " +
            "                                    ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID  " +
            "                                   AND FA.DELETED_DATE IS NULL  " +
            "                                 INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FDN  " +
            "                                    ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID  " +
            "                                   AND FDN.DELETED_DATE IS NULL  " +
            "                                  LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR  " +
            "                                    ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID  " +
            "                                   AND FDR.DELETED_DATE IS NULL  " +
            "                                 WHERE FD.ORGANIZATION_ID = :organizationId  " +
            "                                   AND FD.DELETED_DATE IS NULL  " +
            "                                   AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId  " +
            "                                   AND ((:dateFilterFlg= 1 AND  " +
            "                                       FD.DOCUMENT_DATE BETWEEN :fromDate AND  " +
            "                                       NVL(:toDate, SYSDATE) OR  " +
            "                                       (:dateFilterFlg= 0 AND  " +
            "                                       FD.DOCUMENT_DATE >=  " +
            "                                       (SELECT INER_DOC.DOCUMENT_DATE  " +
            "                                             FROM FNDC.FINANCIAL_DOCUMENT INER_DOC  " +
            "                                            INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM  " +
            "                                               ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID  " +
            "                                              AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID =  " +
            "                                                  :documentNumberingTypeId  " +
            "                                              AND INER_NUM.DOCUMENT_NUMBER = :fromNumber  " +
            "                                            WHERE INER_DOC.DELETED_DATE IS NULL  " +
            "                                              AND INER_NUM.DELETED_DATE IS NULL))))  " +
            "                                   AND FDN.FINANCIAL_NUMBERING_TYPE_ID =  " +
            "                                       :documentNumberingTypeId  " +
            "                                       AND ( :centricAccount1 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId1 )   " +
            "                                       AND ( :centricAccount2 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId2 )   " +
            "                                       AND (:referenceNumberObject IS NULL OR FDR.REFRENCE_NUMBER = :referenceNumber)   " +
            "                                   AND ((:dateFilterFlg= 0 AND  " +
            "                                       FDN.DOCUMENT_NUMBER >=  " +
            "                                       NVL(:fromNumber, FDN.DOCUMENT_NUMBER) AND  " +
            "                                       FDN.DOCUMENT_NUMBER <=  " +
            "                                       NVL(:toNumber, FDN.DOCUMENT_NUMBER)) OR  " +
            "                                       :dateFilterFlg= 1)  " +
            "                                   AND (EXISTS  " +
            "                                        (SELECT 1  " +
            "                                           FROM FNAC.ACCOUNT_STRUCTURE_LEVEL ASL  " +
            "                                          WHERE ASL.FINANCIAL_ACCOUNT_ID = FA.ID  " +
            "                                            AND ASL.RELATED_ACCOUNT_ID = :financialAccountId  " +
            "                                            AND ASL.DELETED_DATE IS NULL))  " +
            "                                ) all2  " +
            "                         group by all2.FINANCIAL_DOCUMENT_ID,  " +
            "                                  all2.DOCUMENT_NUMBER,  " +
            "                                  all2.DOCUMENT_DATE,  " +
            "                                  all2.DESCRIPTION,  " +
            "                                  all2.ID,  " +
            "                                  all2.CODE) alll  " +
            "                 ORDER BY alll.ID "
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

    @Query(value = " SELECT ID ACCOUNT_ID, " +
            "       CODE ACCOUNT_CODE, " +
            "       DESCRIPTION ACCOUNT_DESCRIPTION, " +
            "       CENTRIC_ACCOUNT_ID_1, " +
            "       CENTRIC_ACCOUNT_ID_2, " +
            "       CENTRIC_ACCOUNT_ID_3, " +
            "       CENTRIC_ACCOUNT_ID_4, " +
            "       CENTRIC_ACCOUNT_ID_5, " +
            "       CENTRIC_ACCOUNT_ID_6, " +
            "       NAME_CNAC1 CENTRIC_ACCOUNT_DES_1, " +
            "       NAME_CNAC2 CENTRIC_ACCOUNT_DES_2, " +
            "       NAME_CNAC3 CENTRIC_ACCOUNT_DES_3, " +
            "       NAME_CNAC4 CENTRIC_ACCOUNT_DES_4, " +
            "       NAME_CNAC5 CENTRIC_ACCOUNT_DES_5, " +
            "       NAME_CNAC6 CENTRIC_ACCOUNT_DES_6, " +
            "       DEBIT_AMOUNT, " +
            "       CREDIT_AMOUNT, " +
            "      ABS(CASE " +
            "             WHEN SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) " +
            "              OVER(ORDER BY ID, " +
            "                       CENTRIC_ACCOUNT_ID_1, " +
            "                       CENTRIC_ACCOUNT_ID_2, " +
            "                       CENTRIC_ACCOUNT_ID_3, " +
            "                       CENTRIC_ACCOUNT_ID_4, " +
            "                       CENTRIC_ACCOUNT_ID_5, " +
            "                       CENTRIC_ACCOUNT_ID_6) < 0 THEN " +
            "              SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) " +
            "              OVER(ORDER BY ID, " +
            "                   CENTRIC_ACCOUNT_ID_1, " +
            "                   CENTRIC_ACCOUNT_ID_2, " +
            "                   CENTRIC_ACCOUNT_ID_3, " +
            "                   CENTRIC_ACCOUNT_ID_4, " +
            "                   CENTRIC_ACCOUNT_ID_5, " +
            "                   CENTRIC_ACCOUNT_ID_6) " +
            "             ELSE " +
            "              0 " +
            "           END) AS REMAIN_DEBIT, " +
            "      CASE " +
            "         WHEN SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) " +
            "          OVER(ORDER BY ID, " +
            "                   CENTRIC_ACCOUNT_ID_1, " +
            "                   CENTRIC_ACCOUNT_ID_2, " +
            "                   CENTRIC_ACCOUNT_ID_3, " +
            "                   CENTRIC_ACCOUNT_ID_4, " +
            "                   CENTRIC_ACCOUNT_ID_5, " +
            "                   CENTRIC_ACCOUNT_ID_6) > 0 THEN " +
            "          SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) " +
            "          OVER(ORDER BY ID, " +
            "               CENTRIC_ACCOUNT_ID_1, " +
            "               CENTRIC_ACCOUNT_ID_2, " +
            "               CENTRIC_ACCOUNT_ID_3, " +
            "               CENTRIC_ACCOUNT_ID_4, " +
            "               CENTRIC_ACCOUNT_ID_5, " +
            "               CENTRIC_ACCOUNT_ID_6) " +
            "         ELSE " +
            "          0 " +
            "       END REMAIN_CREDIT, " +
            "       SUM(CREDIT_AMOUNT - DEBIT_AMOUNT) OVER(ORDER BY ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6) REMAIN_AMOUNT  " +
            "  FROM (SELECT NULL AS CENTRIC_ACCOUNT_ID_1, " +
            "               NULL AS CENTRIC_ACCOUNT_ID_2, " +
            "               NULL AS CENTRIC_ACCOUNT_ID_3, " +
            "               NULL AS CENTRIC_ACCOUNT_ID_4, " +
            "               NULL AS CENTRIC_ACCOUNT_ID_5, " +
            "               NULL AS CENTRIC_ACCOUNT_ID_6, " +
            "               NULL AS CODE_CNAC1, " +
            "               NULL AS CODE_CNAC2, " +
            "               NULL AS CODE_CNAC3, " +
            "               NULL AS CODE_CNAC4, " +
            "               NULL AS CODE_CNAC5, " +
            "               NULL AS CODE_CNAC6, " +
            "               NULL AS NAME_CNAC1, " +
            "               NULL AS NAME_CNAC2, " +
            "               NULL AS NAME_CNAC3, " +
            "               NULL AS NAME_CNAC4, " +
            "               NULL AS NAME_CNAC5, " +
            "               NULL AS NAME_CNAC6, " +
            "               SUM(FDI.CREDIT_AMOUNT) CREDIT_AMOUNT, " +
            "               SUM(FDI.DEBIT_AMOUNT) DEBIT_AMOUNT, " +
            "               0 AS ID, " +
            "               NULL AS CODE, " +
            "               NULL AS DESCRIPTION  " +
            "          FROM FNDC.FINANCIAL_DOCUMENT FD  " +
            "         INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI  " +
            "            ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID  " +
            "           AND FDI.DELETED_DATE IS NULL  " +
            "         INNER JOIN FNAC.FINANCIAL_ACCOUNT FA  " +
            "            ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID  " +
            "           AND FA.DELETED_DATE IS NULL  " +
            "         INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER FDN  " +
            "            ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID  " +
            "           AND FDN.DELETED_DATE IS NULL  " +
            "          LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR  " +
            "            ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID  " +
            "           AND FDR.DELETED_DATE IS NULL  " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC1  " +
            "            ON CNAC1.ID = FDI.CENTRIC_ACCOUNT_ID_1  " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC2  " +
            "            ON CNAC2.ID = FDI.CENTRIC_ACCOUNT_ID_2  " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC3  " +
            "            ON CNAC3.ID = FDI.CENTRIC_ACCOUNT_ID_3  " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC4  " +
            "            ON CNAC4.ID = FDI.CENTRIC_ACCOUNT_ID_4  " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC5  " +
            "            ON CNAC5.ID = FDI.CENTRIC_ACCOUNT_ID_5  " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC6  " +
            "            ON CNAC6.ID = FDI.CENTRIC_ACCOUNT_ID_6  " +
            "         WHERE FD.ORGANIZATION_ID = :organizationId  " +
            "           AND FD.DELETED_DATE IS NULL  " +
            "           AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId  " +
            "           AND FD.DOCUMENT_DATE >= :periodStartDate  " +
            "           AND ((:dateFilterFlg = 1 AND FD.DOCUMENT_DATE < :fromDate) OR  " +
            "               (:dateFilterFlg = 0 AND  " +
            "               FD.DOCUMENT_DATE <=  " +
            "               (SELECT INER_DOC.DOCUMENT_DATE  " +
            "                    FROM FNDC.FINANCIAL_DOCUMENT INER_DOC  " +
            "                   INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM  " +
            "                      ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID  " +
            "                     AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID =  " +
            "                         :documentNumberingTypeId  " +
            "                     AND INER_NUM.DOCUMENT_NUMBER = :fromNumber  " +
            "                   WHERE INER_DOC.DELETED_DATE IS NULL  " +
            "                     AND INER_NUM.DELETED_DATE IS NULL)))  " +
            "           AND FDN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId  " +
            "           AND (:centricAccount1 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId1)  " +
            "           AND (:centricAccount2 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId2) " +
            "           AND (:referenceNumberObject IS NULL OR FDR.REFRENCE_NUMBER = :referenceNumber)   " +
            "           AND ((:dateFilterFlg = 0 AND  " +
            "               FDN.DOCUMENT_NUMBER <  " +
            "               NVL(:fromNumber, FDN.DOCUMENT_NUMBER)) OR  " +
            "               :dateFilterFlg = 1)  " +
            "           AND (EXISTS (SELECT 1  " +
            "                          FROM FNAC.ACCOUNT_STRUCTURE_LEVEL ASL  " +
            "                         WHERE ASL.FINANCIAL_ACCOUNT_ID = FA.ID  " +
            "                           AND ASL.RELATED_ACCOUNT_ID = :financialAccountId  " +
            "                           AND ASL.DELETED_DATE IS NULL))  " +
            "        UNION  " +
            "        SELECT FDI.CENTRIC_ACCOUNT_ID_1, " +
            "               FDI.CENTRIC_ACCOUNT_ID_2, " +
            "               FDI.CENTRIC_ACCOUNT_ID_3, " +
            "               FDI.CENTRIC_ACCOUNT_ID_4, " +
            "               FDI.CENTRIC_ACCOUNT_ID_5, " +
            "               FDI.CENTRIC_ACCOUNT_ID_6, " +
            "               CNAC1.CODE               CODE_CNAC1, " +
            "               CNAC2.CODE               CODE_CNAC2, " +
            "               CNAC3.CODE               CODE_CNAC3, " +
            "               CNAC4.CODE               CODE_CNAC4, " +
            "               CNAC5.CODE               CODE_CNAC5, " +
            "               CNAC6.CODE               CODE_CNAC6, " +
            "               CNAC1.NAME               NAME_CNAC1, " +
            "               CNAC2.NAME               NAME_CNAC2, " +
            "               CNAC3.NAME               NAME_CNAC3, " +
            "               CNAC4.NAME               NAME_CNAC4, " +
            "               CNAC5.NAME               NAME_CNAC5, " +
            "               CNAC6.NAME               NAME_CNAC6, " +
            "               SUM(FDI.CREDIT_AMOUNT) CREDIT_AMOUNT, " +
            "               SUM(FDI.DEBIT_AMOUNT) DEBIT_AMOUNT, " +
            "               FA.ID, " +
            "               FA.CODE as CODE, " +
            "               FA.DESCRIPTION  " +
            "          FROM fndc.FINANCIAL_DOCUMENT FD  " +
            "         INNER JOIN fndc.FINANCIAL_DOCUMENT_ITEM FDI  " +
            "            ON FD.ID = FDI.FINANCIAL_DOCUMENT_ID  " +
            "           AND FDI.DELETED_DATE IS NULL  " +
            "         INNER JOIN FNAC.FINANCIAL_ACCOUNT FA  " +
            "            ON FA.ID = FDI.FINANCIAL_ACCOUNT_ID  " +
            "           AND FA.DELETED_DATE IS NULL " +
            "         INNER JOIN fndc.FINANCIAL_DOCUMENT_NUMBER FDN " +
            "            ON FDN.FINANCIAL_DOCUMENT_ID = FD.ID " +
            "           AND FDN.DELETED_DATE IS NULL " +
            "          LEFT OUTER JOIN FNDC.FINANCIAL_DOCUMENT_REFRENCE FDR " +
            "            ON FDI.ID = FDR.FINANCIAL_DOCUMENT_ITEM_ID " +
            "           AND FDR.DELETED_DATE IS NULL " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC1 " +
            "            ON CNAC1.ID = FDI.CENTRIC_ACCOUNT_ID_1 " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC2 " +
            "            ON CNAC2.ID = FDI.CENTRIC_ACCOUNT_ID_2 " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC3 " +
            "            ON CNAC3.ID = FDI.CENTRIC_ACCOUNT_ID_3 " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC4 " +
            "            ON CNAC4.ID = FDI.CENTRIC_ACCOUNT_ID_4 " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC5 " +
            "            ON CNAC5.ID = FDI.CENTRIC_ACCOUNT_ID_5 " +
            "          LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC6 " +
            "            ON CNAC6.ID = FDI.CENTRIC_ACCOUNT_ID_6 " +
            "         WHERE FD.ORGANIZATION_ID = :organizationId " +
            "           AND FD.DELETED_DATE IS NULL " +
            "           AND FD.FINANCIAL_LEDGER_TYPE_ID = :ledgerTypeId " +
            "           AND ((:dateFilterFlg = 1 AND " +
            "               FD.DOCUMENT_DATE BETWEEN :fromDate AND " +
            "               NVL(:toDate, SYSDATE) OR " +
            "               (:dateFilterFlg = 0 AND " +
            "               FD.DOCUMENT_DATE >= " +
            "               (SELECT INER_DOC.DOCUMENT_DATE " +
            "                     FROM FNDC.FINANCIAL_DOCUMENT INER_DOC " +
            "                    INNER JOIN FNDC.FINANCIAL_DOCUMENT_NUMBER INER_NUM " +
            "                       ON INER_DOC.ID = INER_NUM.FINANCIAL_DOCUMENT_ID " +
            "                      AND INER_NUM.FINANCIAL_NUMBERING_TYPE_ID = " +
            "                          :documentNumberingTypeId " +
            "                      AND INER_NUM.DOCUMENT_NUMBER = :fromNumber " +
            "                    WHERE INER_DOC.DELETED_DATE IS NULL " +
            "                      AND INER_NUM.DELETED_DATE IS NULL)))) " +
            "           AND FDN.FINANCIAL_NUMBERING_TYPE_ID = :documentNumberingTypeId " +
            "           AND (:centricAccount1 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_1 = :centricAccountId1) " +
            "           AND (:centricAccount2 IS NULL OR FDI.CENTRIC_ACCOUNT_ID_2 = :centricAccountId2) " +
            "           AND (:referenceNumberObject IS NULL OR FDR.REFRENCE_NUMBER = :referenceNumber)   " +
            "           AND ((:dateFilterFlg = 0 AND " +
            "               FDN.DOCUMENT_NUMBER >= " +
            "               NVL(:fromNumber, FDN.DOCUMENT_NUMBER) AND " +
            "               FDN.DOCUMENT_NUMBER <= NVL(:toNumber, FDN.DOCUMENT_NUMBER)) OR " +
            "               :dateFilterFlg = 1) " +
            "           AND (EXISTS (SELECT 1 " +
            "                          FROM FNAC.ACCOUNT_STRUCTURE_LEVEL ASL " +
            "                         WHERE ASL.FINANCIAL_ACCOUNT_ID = FA.ID " +
            "                           AND ASL.RELATED_ACCOUNT_ID = :financialAccountId " +
            "                           AND ASL.DELETED_DATE IS NULL)) " +
            "        GROUP BY FA.ID, " +
            "                  FA.CODE, " +
            "                  FA.DESCRIPTION, " +
            "                  FDI.CENTRIC_ACCOUNT_ID_1, " +
            "                  FDI.CENTRIC_ACCOUNT_ID_2, " +
            "                  FDI.CENTRIC_ACCOUNT_ID_3, " +
            "                  FDI.CENTRIC_ACCOUNT_ID_4, " +
            "                  FDI.CENTRIC_ACCOUNT_ID_5, " +
            "                  FDI.CENTRIC_ACCOUNT_ID_6, " +
            "                  CNAC1.CODE, " +
            "                  CNAC2.CODE, " +
            "                  CNAC3.CODE, " +
            "                  CNAC4.CODE, " +
            "                  CNAC5.CODE, " +
            "                  CNAC6.CODE, " +
            "                  CNAC1.NAME, " +
            "                  CNAC2.NAME, " +
            "                  CNAC3.NAME, " +
            "                  CNAC4.NAME, " +
            "                  CNAC5.NAME, " +
            "                  CNAC6.NAME) " +
            " ORDER BY ID "
            , nativeQuery = true)
    Page<Object[]> findByFinancialAccountCentricTurnOver(Long organizationId, Long ledgerTypeId, LocalDateTime periodStartDate,
                                                         Long dateFilterFlg, LocalDateTime fromDate, Long documentNumberingTypeId,
                                                         String fromNumber, Object centricAccount1, Long centricAccountId1,
                                                         Object centricAccount2, Long centricAccountId2, Object referenceNumberObject,
                                                         Long referenceNumber, Long financialAccountId, LocalDateTime toDate, String toNumber,
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
            "         INNER JOIN FINANCIAL_DOCUMENT_NUMBER FDN " +
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
            "           AND FD.ORGANIZATION_ID = :organizationId " +
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

}
