package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialLedgerPeriodDocItemsRepository extends JpaRepository<FinancialDocument, Long> {
    @Query(value = " WITH QRY AS " +
            " (SELECT FINANCIAL_PERIOD_ID, " +
            "         FINANCIAL_LEDGER_TYPE_ID, " +
            "         NVL(FINANCIAL_ACCOUNT_CODE, '') || " +
            "         NVL(FINANCIAL_ACCOUNT_DESCRIPTION, '') FINANCIAL_ACCOUNT_DESC," +
            "         FINANCIAL_ACCOUNT_ID," +
            "         SUM_DEBIT," +
            "         SUM_CREDIT," +
            "         CENTRIC_ACCOUNT_ID_1," +
            "         CENTRIC_ACCOUNT_ID_2," +
            "         CENTRIC_ACCOUNT_ID_3," +
            "         CENTRIC_ACCOUNT_ID_4," +
            "         CENTRIC_ACCOUNT_ID_5," +
            "         CENTRIC_ACCOUNT_ID_6" +
            "    FROM (SELECT FD.FINANCIAL_PERIOD_ID," +
            "                 FD.FINANCIAL_LEDGER_TYPE_ID," +
            "                 FA2.FINANCIAL_ACCOUNT_PARENT_ID," +
            "                 FA2.ID FINANCIAL_ACCOUNT_ID," +
            "                 FA2.CODE FINANCIAL_ACCOUNT_CODE," +
            "                 FA2.DESCRIPTION FINANCIAL_ACCOUNT_DESCRIPTION," +
            "                 SUM(FDI.DEBIT_AMOUNT) SUM_DEBIT," +
            "                 SUM(FDI.CREDIT_AMOUNT) SUM_CREDIT," +
            "                 FDI.CENTRIC_ACCOUNT_ID_1," +
            "                 FDI.CENTRIC_ACCOUNT_ID_2," +
            "                 FDI.CENTRIC_ACCOUNT_ID_3," +
            "                 FDI.CENTRIC_ACCOUNT_ID_4," +
            "                 FDI.CENTRIC_ACCOUNT_ID_5," +
            "                 FDI.CENTRIC_ACCOUNT_ID_6                " +
            "            FROM FNDC.FINANCIAL_DOCUMENT FD" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_ITEM FDI" +
            "              ON FDI.FINANCIAL_DOCUMENT_ID = FD.ID" +
            "           INNER JOIN FNAC.FINANCIAL_ACCOUNT FA2" +
            "              ON FDI.FINANCIAL_ACCOUNT_ID = FA2.ID" +
            "           INNER JOIN FNDC.FINANCIAL_DOCUMENT_STATUS FDS" +
            "              ON FDS.ID = FD.FINANCIAL_DOCUMENT_STATUS_ID" +
            "           INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP" +
            "              ON LP.FINANCIAL_PERIOD_ID = FD.FINANCIAL_PERIOD_ID" +
            "             AND LP.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID" +
            "           INNER JOIN FNDC.FINANCIAL_LEDGER_TYPE LT" +
            "              ON LT.ID = LP.FINANCIAL_LEDGER_TYPE_ID" +
            "             AND LT.ORGANIZATION_ID = FD.ORGANIZATION_ID" +
            "           INNER JOIN FNPR.FINANCIAL_PERIOD FP" +
            "              ON FP.ID = FD.FINANCIAL_PERIOD_ID" +
            "           WHERE FD.ORGANIZATION_ID = FD.ORGANIZATION_ID" +
            "             AND FDS.CODE = 30 " +
            "             AND FD.DOCUMENT_DATE BETWEEN FP.START_DATE AND FP.END_DATE" +
            "             AND FD.ORGANIZATION_ID = :organizationId " +
            "             AND FD.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
            "             AND FD.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "             AND ((:permanentStatus != 2 AND" +
            "                 FA2.ACCOUNT_PERMANENT_STATUS_ID IN (1, 3)) OR" +
            "                 (:permanentStatus = 2 AND" +
            "                 FA2.ACCOUNT_PERMANENT_STATUS_ID = 2))" +
            "           GROUP BY FD.FINANCIAL_PERIOD_ID," +
            "                    FD.FINANCIAL_LEDGER_TYPE_ID," +
            "                    FA2.FINANCIAL_ACCOUNT_PARENT_ID," +
            "                    FA2.ID," +
            "                    FA2.CODE," +
            "                    FA2.DESCRIPTION," +
            "                    CENTRIC_ACCOUNT_ID_1," +
            "                    CENTRIC_ACCOUNT_ID_2," +
            "                    CENTRIC_ACCOUNT_ID_3," +
            "                    CENTRIC_ACCOUNT_ID_4," +
            "                    CENTRIC_ACCOUNT_ID_5," +
            "                    CENTRIC_ACCOUNT_ID_6)" +
            "   WHERE (SUM_DEBIT - SUM_CREDIT) <> 0" +
            "   ORDER BY (SUM_DEBIT - SUM_CREDIT))" +
            " SELECT ROWNUM SEQUENCE," +
            "       FINANCIAL_ACCOUNT_ID," +
            "       CENTRIC_ACCOUNT_ID_1," +
            "       CENTRIC_ACCOUNT_ID_2," +
            "       CENTRIC_ACCOUNT_ID_3," +
            "       CENTRIC_ACCOUNT_ID_4," +
            "       CENTRIC_ACCOUNT_ID_5," +
            "       CENTRIC_ACCOUNT_ID_6," +
            "       CASE" +
            "         WHEN (SUM_DEBIT - SUM_CREDIT) > 0 THEN" +
            "          (SUM_DEBIT - SUM_CREDIT)" +
            "         ELSE" +
            "          0" +
            "       END REM_DEBIT," +
            "       CASE" +
            "         WHEN (SUM_DEBIT - SUM_CREDIT) < 0 THEN" +
            "          (SUM_CREDIT - SUM_DEBIT)" +
            "         ELSE" +
            "          0" +
            "       END REM_CREDIT," +
            "       ' سند بستن حسابهای ' || CASE " +
            "         WHEN :permanentStatus = 1 THEN" +
            "          ' دائمی'" +
            "         ELSE" +
            "          'موقت '" +
            "       END || ' ' || :financialPeriodDes DOC_ITEM_DES" +
            "  FROM QRY"
            , nativeQuery = true)
    List<Object[]> findByFinancialDocumentItemByIdAndFinancialDocumentId(Long organizationId, Long financialLedgerTypeId, Long financialPeriodId,Long permanentStatus,String  financialPeriodDes);
}
