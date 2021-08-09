package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialSystemRepository extends JpaRepository<FinancialSystem, Long> {

    @Query(value = "select fs from  FinancialSystem fs  where  fs.deletedDate is null ")
    List<FinancialSystem> findByFinancialSystem();



}
