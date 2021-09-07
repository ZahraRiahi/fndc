package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinancialConfigRepository extends JpaRepository<FinancialConfig, Long> {
    @Query("select coalesce(COUNT(fc.id),0) from FinancialConfig fc  where fc.organization.id=:organizationId and fc.user.id=:userId and fc.deletedDate is null")
    Long getCountByFinancialConfigAndOrganizationAndUser(Long organizationId, Long userId);
}
