package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface FinancialDocumentTypeRepository extends JpaRepository<FinancialDocumentType,Long> {

    @Query("select fd " +
            "from FinancialDocumentType fd" +
            " where fd.organization.id=:organizationId" +
            " and :isFlag is null or ((:searchStatusFlag=false and fd.activeFlag=true) or (:searchStatusFlag=true))")
    List<FinancialDocumentType> findByOrganizationId(Long organizationId, Boolean searchStatusFlag, String isFlag);
}
