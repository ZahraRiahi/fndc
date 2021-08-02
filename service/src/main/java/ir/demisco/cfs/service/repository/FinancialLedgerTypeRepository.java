package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialLedgerTypeRepository extends JpaRepository<FinancialLedgerType, Long> {

    @Query(value = " select flt" +
            " from FinancialLedgerType flt " +
            " where flt.deletedDate is null " +
            " and flt.organization.id =:organizationId ")
    List<FinancialLedgerType> findFinancialLedgerTypeByOrganizationId(Long organizationId);
}
