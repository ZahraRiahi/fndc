package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialAccountRepository extends JpaRepository<FinancialAccount, Long> {

    @Query("select fa from FinancialAccount fa " +
            " join FinancialAccount  fa2 on fa.accountRelationType.id=fa2.accountRelationType.id " +
            " where fa.id=:accountId and fa2.id=:newAccountId")
    FinancialAccount getFinancialAccountRelationType(Long accountId, Long newAccountId);

    @Query(value = " SELECT 1 " +
            "                FROM FNAC.FINANCIAL_ACCOUNT T " +
            "               WHERE T.ID IN " +
            "                     (:financialAccountListId) " +
            "              And  T.ACCOUNT_PERMANENT_STATUS_ID = 2;  "
            , nativeQuery = true)
    List<Long> getFinancialAccountList(List<Long> financialAccountListId);
}
