package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FinancialPeriodRepository extends JpaRepository<FinancialPeriod,Long> {


    @Query("select fp from FinancialMonth fmn" +
            " join FinancialMonthType  fmt on fmt.id=fmn.financialMonthType.id " +
            " join FinancialPeriodType fnp on fnp.id=fmt.financialPeriodType.id " +
            " join FinancialPeriodTypeAssign  pt on pt.financialPeriodType.id=fnp.id " +
            " join FinancialPeriod fp on fp.financialPeriodTypeAssign.id=pt.id " +
            " where  TO_DATE(:date, 'yyyy-mm-dd') between fp.startDate and fp.endDate " +
            "   and extract(month from TO_DATE(:date, 'yyyy-mm-dd')) = (fnp.fromMonth + (fmt.monthNumber - 1) ) " +
            "   and fmn.financialMonthStatus.id = 1 " +
            "   and fp.financialPeriodStatus.id =1 " +
            "   and pt.organization.id=:organizationId")
    List<FinancialPeriod>  getPeriodByParam(String date,Long organizationId);
}
