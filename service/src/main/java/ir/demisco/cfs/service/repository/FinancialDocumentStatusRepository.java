package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialDocumentStatusRepository extends JpaRepository<FinancialDocumentStatus,Long> {

    @Query("select fds from FinancialDocumentStatus fds where fds.deletedDate is null")
    List<FinancialDocumentStatus> getFinancialDocumentStatusList();
}
