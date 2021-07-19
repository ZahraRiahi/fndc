package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinancialDocumentDescriptionRepository extends JpaRepository<FinancialDocumentDescription,Long> {
    @Query("select fd from FinancialDocumentDescription fd where fd.organization.id=:organizationId and fd.description=:description")
    FinancialDocumentDescription getByParam(Long organizationId,String description);
}
