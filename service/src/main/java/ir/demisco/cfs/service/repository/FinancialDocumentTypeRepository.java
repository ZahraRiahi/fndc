package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialDocumentTypeRepository extends JpaRepository<FinancialDocumentType,Long> {

    @Query("select fd " +
            "from FinancialDocumentType fd" +
            " where fd.organization.id=:organizationId" +
            " and :isFlag is null or ((:searchStatusFlag=false and fd.activeFlag=true) or (:searchStatusFlag=true))" +
            " and fd.deletedDate is null ")
    List<FinancialDocumentType> findByOrganizationId(Long organizationId, Boolean searchStatusFlag, String isFlag);

    @Query("select count(fdt.id) " +
            "  from FinancialDocumentType fdt" +
            "  join FinancialConfig fc" +
            "    on fc.financialDocumentType.id = fdt.id" +
            "  join FinancialDocument fd" +
            "    on fd.financialDocumentType.id = fdt.id" +
            "  join FinancialDocument fdp" +
            "    on fdp.financialDocumentType.id = fdt.id" +
            " where fdt.id = :financialDocumentTypeId" +
            "   and ( fd.financialDocumentType.id= :financialDocumentTypeId or" +
            "        fdp.financialDocumentType.id= :financialDocumentTypeId or" +
            "         fc.financialDocumentType.id= :financialDocumentTypeId)")
    Long getDocumentTypeIdForDelete(Long financialDocumentTypeId);
}
