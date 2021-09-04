package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.MoneyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyTypeRepository extends JpaRepository<MoneyType,Long> {
}
