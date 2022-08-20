package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.NumberingFormatSerial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NumberingFormatSerialRepository extends JpaRepository<NumberingFormatSerial, Long> {
    @Query(value = " SELECT NFS.ID" +
            "  FROM fndc.FINANCIAL_DOCUMENT FD" +
            " INNER JOIN fndc.LEDGER_NUMBERING_TYPE LNT" +
            "    ON LNT.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID" +
            "   AND LNT.DELETED_DATE IS NULL" +
            " INNER JOIN fndc.FINANCIAL_NUMBERING_TYPE NT" +
            "    ON NT.ID = LNT.FINANCIAL_NUMBERING_TYPE_ID" +
            "   AND NT.TYPE_STATUS = :numberingType " +
            "   AND NT.DELETED_DATE IS NULL" +
            " INNER JOIN fndc.FINANCIAL_NUMBERING_FORMAT NF" +
            "    ON NF.FINANCIAL_NUMBERING_TYPE_ID = NT.ID" +
            "   AND NF.ORGANIZATION_ID = :organizationId " +
            "   AND NF.DELETED_DATE IS NULL" +
            " INNER JOIN fndc.FINANCIAL_NUMBERING_FORMAT_TYPE NFT" +
            "    ON NFT.ID = NF.NUMBERING_FORMAT_TYPE_ID" +
            "   AND NFT.DELETED_DATE IS NULL" +
            "  LEFT OUTER JOIN fndc.NUMBERING_FORMAT_SERIAL NFS" +
            "    ON NFS.NUMBERING_FORMAT_ID = NF.ID" +
            "   AND NFS.DELETED_DATE IS NULL" +
            " WHERE FD.ID = :financialDocumentId " +
            "   AND FD.DELETED_DATE IS NULL" +
            "   and serial_reseter =" +
            "       REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" +
            "                                                       " +
            "                                                       REPLACE(NFT.CODE," +
            "                                                               '$SRL'," +
            "                                                               '')" +
            "                                                       " +
            "                                                      ," +
            "                                                       '$DAT6'," +
            "                                                       TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE," +
            "                                                                               'mm/dd/yyyy')," +
            "                                                                       'mm/dd/yyyy')," +
            "                                                               'yymmdd'," +
            "                                                               'NLS_CALENDAR=persian'))," +
            "                                               '$DAT'," +
            "                                               TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE," +
            "                                                                       'mm/dd/yyyy')," +
            "                                                               'mm/dd/yyyy')," +
            "                                                       'yyyymmdd'," +
            "                                                       'NLS_CALENDAR=persian'))," +
            "                                       '$LEG'," +
            "                                       (SELECT LT.CODE" +
            "                                          FROM fndc.FINANCIAL_LEDGER_TYPE LT" +
            "                                         WHERE LT.ID =" +
            "                                               FD.FINANCIAL_LEDGER_TYPE_ID))," +
            "                               '$DEP'," +
            "                               (SELECT DP.CODE" +
            "                                  FROM ORG.DEPARTMENT DP" +
            "                                 WHERE DP.ID = FD.DEPARTMENT_ID))," +
            "                       '$ORG'," +
            "                       (SELECT OG.CODE" +
            "                          FROM FNDC.FINANCIAL_ORGANIZATION OG" +
            "                         WHERE OG.ORGANIZATION_ID = :organizationId))," +
            "               '$PRI'," +
            "               (SELECT PR.CODE" +
            "                  FROM FNPR.FINANCIAL_PERIOD PR" +
            "                 WHERE PR.ID = FD.FINANCIAL_PERIOD_ID)) ", nativeQuery = true)
    List<NumberingFormatSerial> findNumberingFormatSerialByParam(Long numberingType,Long organizationId, Long financialDocumentId);

    @Query("select nf from NumberingFormatSerial nf " +
            " where nf.financialNumberingFormat.id=:numberingFormatId  and nf.serialReseter=:serialReseter and nf.serialLength=:serialLength and nf.deletedDate is null")
    NumberingFormatSerial findByNumberingFormatAndDeletedDate(Long numberingFormatId, String serialReseter, Long serialLength);
}
