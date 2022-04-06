package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDepartmentLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialDepartmentLedgerRepository extends JpaRepository<FinancialDepartmentLedger, Long> {
    @Query("select fdl from FinancialDepartmentLedger fdl where fdl.department.id=:departmentId" +
            " and  fdl.financialLedgerType.id=:financialLedgerTypeId and (:financialDepartmentLedgerTypeId is null or fdl.id <> :financialDepartmentLedgerTypeId ) " +
            " and fdl.deletedDate is null")
    FinancialDepartmentLedger getByLedgerTypeIdAndDepartmentIdAndDeleteDate(Long departmentId, Long financialLedgerTypeId,
                                                                            Long financialDepartmentLedgerTypeId);


    @Query("select fdl.financialLedgerType.id,flt.description from  FinancialDepartmentLedger fdl  join fdl.financialLedgerType flt where fdl.department.id=:departmentId  and fdl.deletedDate is null")
    List<Object[]> findByFinancialDepartmentId(Long departmentId);


}
