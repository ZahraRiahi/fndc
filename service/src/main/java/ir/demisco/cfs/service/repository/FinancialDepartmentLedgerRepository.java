package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDepartmentLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialDepartmentLedgerRepository extends JpaRepository<FinancialDepartmentLedger, Long>{

    @Query(value = "select 1" +
            "  from fndc.financial_department_ledger fnlg" +
            " where fnlg.financial_department_id = :financialDepartmentId" +
            "   and fnlg.financial_ledger_type_id = :financialLedgerTypeId" +
            "   and deleted_date is null" ,nativeQuery = true)
    Long  getFinancialDepartmentLedger(Long financialDepartmentId, Long financialLedgerTypeId);


    @Query("select coalesce(COUNT(fdl.id),0) from FinancialDepartmentLedger fdl where fdl.financialDepartment.id=:financialDepartmentId" +
            " and (fdl.financialLedgerType.id is null or fdl.financialLedgerType.id=:financialLedgerTypeId)" +
            " and fdl.deletedDate is null")
    Long getCountByIsNullLedgerTypeIdAndDepartmentIdAndDeleteDate(Long financialDepartmentId, Long financialLedgerTypeId);


    @Query("select coalesce(COUNT(fdl.id),0) from FinancialDepartmentLedger fdl where fdl.financialDepartment.id=:financialDepartmentId" +
            " and  fdl.financialLedgerType.id=:financialLedgerTypeId" +
            " and fdl.deletedDate is null")
    Long getCountByLedgerTypeIdAndDepartmentIdAndDeleteDate(Long financialDepartmentId, Long financialLedgerTypeId);


}
