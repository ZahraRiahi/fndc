package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialActivityTypeRepository extends JpaRepository<FinancialActivityType, Long> {
    @Query(" select fa.id,fa.description,fa.financialSystem.id,fa.financialSystemSubject.id from FinancialActivityType fa " +
            " where fa.code= :activityCode")
    List<Object[]> getFinancialActivityTypeByActivityCode(String activityCode);
}
