package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.AccountDefaultValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountDefaultValueRepository extends JpaRepository<AccountDefaultValue,Long> {

    @Query(" select dv from AccountDefaultValue dv " +
            " join FinancialDocumentItem di on di.financialAccount.id=dv.financialAccount.id " +
            " where di.id= :financialDocumentItemId")
    List<AccountDefaultValue> getAccountDefaultValueByDocumentItemId(Long financialDocumentItemId);
}
