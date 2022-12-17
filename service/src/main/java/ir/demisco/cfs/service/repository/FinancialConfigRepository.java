package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialConfigRepository extends JpaRepository<FinancialConfig, Long> {
    @Query("select coalesce(COUNT(fc.id),0) from FinancialConfig fc  where fc.organization.id=:organizationId and fc.user.id=:userId ")
    Long getCountByFinancialConfigAndOrganizationAndUser(Long organizationId, Long userId);

    @Query(value = "   SELECT 1 " +
            "    FROM FNDC.FINANCIAL_CONFIG T " +
            "   WHERE T.FINANCIAL_DOCUMENT_TYPE_ID =:financialDocumentTypeId "
            , nativeQuery = true)
    List<Long> findByFinancialConfigByFinancialDocumentTypeId(Long financialDocumentTypeId);


}
