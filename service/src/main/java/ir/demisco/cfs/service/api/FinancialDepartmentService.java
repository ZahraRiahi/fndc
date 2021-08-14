package ir.demisco.cfs.service.api;

import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;


public interface FinancialDepartmentService {
    /**لیست انواع شعب مالی
     * @return
     */
    DataSourceResult financialDepartmentList();

}
