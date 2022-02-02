package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.CentricAccount;
import ir.demisco.cloud.basic.model.entity.org.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    @Query(value = "select dp.id  as departmentId," +
            "       dp.code," +
            "       dp.name," +
            "       fnlg.financial_ledger_type_id," +
            "       fnlt.description," +
            "       fnlg.id    as financial_department_ledger_id" +
            "  from org.department dp " +
            "  left outer join fndc.financial_department_ledger fnlg" +
            "        on dp.id = fnlg.department_id " +
            "   and fnlg.deleted_date is null" +
            "  left outer join fndc.financial_ledger_type fnlt" +
            "    on fnlt.id = fnlg.financial_ledger_type_id" +
            "   where dp.organization_id = :organizationId"+
            "   order by dp.creation_date ", nativeQuery = true)
    List<Object[]> getFinancialDocumentItemList(Long organizationId);
}
