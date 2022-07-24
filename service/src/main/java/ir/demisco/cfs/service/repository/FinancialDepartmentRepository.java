package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialDepartmentRepository extends JpaRepository<FinancialDepartment, Long> {

}
