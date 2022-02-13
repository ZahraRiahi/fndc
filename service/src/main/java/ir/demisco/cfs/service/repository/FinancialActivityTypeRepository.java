package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinancialActivityTypeRepository extends JpaRepository<FinancialActivityType, Long> {
    @Query(" select fa.id,fa.description,fa.financialSystem.id,fa.financialSystemSubject.id from FinancialActivityType fa " +
            " where fa.code= :activityCode")
    List<Object[]> getFinancialActivityTypeByActivityCode(String activityCode);


    @Query("select fa from FinancialActivityType fa where fa.code=:code " +
            "and fa.financialSystem.id=:financialSystemId and fa.financialSystemSubject.id=:financialSystemSubjectId ")
    FinancialActivityType getFinancialActivityByCodeAndSystemIdAndSubjectId(String code, Long financialSystemId, Long financialSystemSubjectId);


    @Query(value = "select * from (pkg_financial_security.GET_PERMISSION(:p_organization_id ," +
            "                                                                      :p_activity_code ," +
            "                                                                     :p_financial_period_id ," +
            "                                                                        :p_financial_document_type_id ," +
            "                                                                         :p_creator_user ," +
            "                                                                         :p_financial_department_id ," +
            "                                                                        :p_financial_ledger_type_id ," +
            "                                                                       :p_department_id ," +
            "                                                                       :p_user_id ))"
            , nativeQuery = true)
    List<Object[]> getFinancialSecurityList(@Param("p_organization_id") Long p_organization_id, @Param("p_activity_code") String p_activity_code, @Param("p_financial_period_id") Long p_financial_period_id, @Param("p_financial_document_type_id") Long p_financial_document_type_id,
                                            @Param("p_creator_user") Long p_creator_user, @Param("p_financial_department_id") Long p_financial_department_id, @Param("p_financial_ledger_type_id") Long p_financial_ledger_type_id, @Param("p_department_id") Long p_department_id, @Param("p_user_id") Long p_user_id);
}
