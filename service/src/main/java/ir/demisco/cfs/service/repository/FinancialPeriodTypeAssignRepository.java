package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriodTypeAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FinancialPeriodTypeAssignRepository extends JpaRepository<FinancialPeriodTypeAssign, Long> {

    @Query(value = " select " +
            "    TO_CHAR(start_date ,'yyyy/mm/dd') start_date, " +
            "    (select max(ad.pdat_ggdate_c) " +
            "    from clnd.all_date ad " +
            "   where (calendar_type_id = 1 and " +
            "         ad.pdat_hsdate_yer = substr(TO_CHAR(add_months((start_date), 11) ,'yyyy/mm/dd','NLS_CALENDAR=persian'),0,4) and " +
            "         ad.pdat_hsdate_mon = substr(TO_CHAR(add_months((start_date), 11),'yyyy/mm/dd','NLS_CALENDAR=persian'),6,2)) " +
            "      or " +
            "         (calendar_type_id = 2 and " +
            "         ad.pdat_ggdate_yer = substr(TO_CHAR(add_months((start_date), 11),'yyyy/mm/dd'),0,4) " +
            "         and " +
            "         ad.pdat_ggdate_mon = substr(TO_CHAR(add_months((start_date), 11),'yyyy/mm/dd'),6,2))) end_date " +
            "  from (select max( case" +
            "                 when fnpr.end_date is null then" +
            "                  ta.start_date" +
            "                 else" +
            "                  fnpr.end_date + 1" +
            "               end) start_date, calendar_type_id" +
            "           from fnpr.financial_period fnpr" +
            "          inner join fnpr.financial_period_type_assign ta" +
            "             on ta.financial_period_id = fnpr.id" +
            "          inner join fnpr.financial_period_type fnpt" +
            "             on fnpt.id = fnpr.financial_period_type_id " +
            "         where  ta.active_flag = 1 " +
            " and  ( :financialPeriodType is null or fnpt.id = :financialPeriodTypeId)" +
            "         group by calendar_type_id)", nativeQuery = true)
    List<Object[]> getStartDateAndEndDate(Object financialPeriodType, Long financialPeriodTypeId);

}
