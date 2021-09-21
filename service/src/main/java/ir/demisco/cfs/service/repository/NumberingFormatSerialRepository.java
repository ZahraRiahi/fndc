package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.NumberingFormatSerial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NumberingFormatSerialRepository extends JpaRepository<NumberingFormatSerial,Long> {
    @Query(" select nfs from  FinancialDocument fd" +
            " join LedgerNumberingType lnt on lnt.financialLedgerType.id=fd.financialLedgerType.id and lnt.deletedDate is null " +
            " join FinancialNumberingType nt on nt.id=lnt.financialNumberingType.id and nt.deletedDate is null " +
            " join FinancialNumberingFormat nf on nf.financialNumberingType.id=nt.id and nf.deletedDate is null" +
            " join FinancialNumberingFormatType nft on nft.id=nf.financialNumberingFormatType.id and nft.deletedDate is null " +
            " join NumberingFormatSerial nfs on nfs.financialNumberingFormat.id=nf.id and nfs.deletedDate is null " +
            " where fd.id=:FinancialDocumentId" +
            "   and nt.typeStatus=:numberingType " +
            "   and nf.organization.id=:organizationId " +
            "   and fd.deletedDate is null ")
    List<NumberingFormatSerial> findNumberingFormatSerialByParam(Long organizationId, Long FinancialDocumentId, Long numberingType);
}
