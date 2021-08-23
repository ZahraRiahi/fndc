package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.CentricAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CentricAccountRepository extends JpaRepository<CentricAccount,Long> {
}
