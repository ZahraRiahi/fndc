package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinancialAccountRepository extends JpaRepository<FinancialAccount,Long> {

    @Query("select fa from FinancialAccount fa " +
            " join FinancialAccount  fa2 on fa.accountRelationType.id=fa2.accountRelationType.id " +
            " where fa.id=:accountId and fa2.id=:newAccountId")
    FinancialAccount getFinancialAccountRelationType(Long accountId,Long newAccountId);
}
