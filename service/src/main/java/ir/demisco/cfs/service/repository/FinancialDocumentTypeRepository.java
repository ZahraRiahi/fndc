package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialDocumentTypeRepository extends JpaRepository<FinancialDocumentType, Long> {

    @Query("select fd " +
            "from FinancialDocumentType fd" +
            " where fd.organization.id=:organizationId" +
            " and :isFlag is null or ((:searchStatusFlag=false and fd.activeFlag=true) or (:searchStatusFlag=true)) " +
            " and (:financialSystem is null or " +
            " fd.financialSystem.id = :financialSystemId)" +
            " and fd.deletedDate is null ")
    List<FinancialDocumentType> findByOrganizationId(Long organizationId, Boolean searchStatusFlag, String isFlag, Object financialSystem, Long financialSystemId);

    @Query(value = "select count(t.id)" +
            "  from fndc.financial_document_type t" +
            "  left join fndc.financial_config fc" +
            "    on fc.financial_document_type_id = t.id" +
            "  left join fndc.financial_document fd" +
            "    on t.id = fd.financial_document_type_id" +
            "  left join fndc.financial_document_pattern fdp" +
            "    on fdp.financial_document_type_id = t.id" +
            " where t.id = :financialDocumentTypeId" +
            "   and ( fd.financial_document_type_id = :financialDocumentTypeId or" +
            "        fdp.financial_document_type_id = :financialDocumentTypeId or" +
            "         fc.financial_document_type_id = :financialDocumentTypeId)", nativeQuery = true)
    Long getDocumentTypeIdForDelete(Long financialDocumentTypeId);
}
