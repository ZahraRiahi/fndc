package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinancialDepartmentRepository extends JpaRepository<FinancialDepartment,Long>{

    @Query(value = "select fndp.id  as departmentId," +
            "       fndp.code," +
            "       fndp.name," +
            "       fnlg.financial_ledger_type_id," +
            "       fnlt.description," +
            "       fnlg.id    as financial_department_ledger_id" +
            "  from fndc.financial_department fndp" +
            "  left outer join fndc.financial_department_ledger fnlg" +
            "    on fndp.id = fnlg.financial_department_id" +
            "   and fnlg.deleted_date is null" +
            "  left outer join fndc.financial_ledger_type fnlt" +
            "    on fnlt.id = fnlg.financial_ledger_type_id" +
            "   and fnlt.deleted_date is null" +
            " where fndp.deleted_date is null" +
            "   and fnlt.organization_id = :organizationId" +
            "   and fndp.organization_id = :organizationId",nativeQuery = true)
    Page<Object[]> getFinancialDocumentItemList(Long organizationId, Pageable pageable);


}
