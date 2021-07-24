package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialNumberingFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinancialNumberingFormatRepository extends JpaRepository<FinancialNumberingFormat,Long> {

   @Query("select fn from FinancialNumberingFormat fn where fn.organization.id=:organizationId and fn.financialNumberingFormatType.id=:formatTypeId " +
           "and fn.financialNumberingType.id=:numberingTypeId and fn.deletedDate is null")

    FinancialNumberingFormat getFormatByType(Long formatTypeId,Long numberingTypeId,Long organizationId);

    @Query("select fn from FinancialNumberingFormat fn where fn.organization.id=:organizationId and fn.financialNumberingFormatType.id=:formatTypeId " +
            "and fn.financialNumberingType.id=:numberingTypeId and fn.deletedDate is null and fn.id <> :numberFormatId")

    FinancialNumberingFormat getFormatByTypeForEdit(Long formatTypeId,Long numberingTypeId,Long organizationId,Long numberFormatId);
}
