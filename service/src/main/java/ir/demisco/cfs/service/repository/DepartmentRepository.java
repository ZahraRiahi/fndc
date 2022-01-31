package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.CentricAccount;
import ir.demisco.cloud.basic.model.entity.org.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
