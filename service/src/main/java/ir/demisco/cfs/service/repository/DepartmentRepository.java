package ir.demisco.cfs.service.repository;

import ir.demisco.cloud.basic.model.entity.org.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select FNDP.id  as departmentId," +
            "       FNDP.code," +
            "       FNDP.name," +
            "       fnlg.financial_ledger_type_id," +
            "       fnlt.description," +
            "       fnlg.id    as financial_department_ledger_id" +
            "  from FNDC.FINANCIAL_DEPARTMENT FNDP " +
            "  left outer join fndc.financial_department_ledger fnlg" +
            "        ON FNDP.DEPARTMENT_ID = FNLG.DEPARTMENT_ID " +
            "  left outer join fndc.financial_ledger_type fnlt" +
            "    on fnlt.id = fnlg.financial_ledger_type_id" +
            "   where FNDP.organization_id = :organizationId" +
            " AND FNDP.DEPARTMENT_ID = :departmentId "
            , nativeQuery = true)
    List<Object[]> getFinancialDocumentItemList(Long organizationId, Long departmentId);
}
