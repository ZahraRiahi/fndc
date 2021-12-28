package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDepartmentResponse;
import ir.demisco.cfs.service.api.FinancialDepartmentService;
import ir.demisco.cfs.service.repository.FinancialDepartmentRepository;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialDepartment implements FinancialDepartmentService {
    private final FinancialDepartmentRepository financialDepartmentRepository;

    public DefaultFinancialDepartment(FinancialDepartmentRepository financialDepartmentRepository) {
        this.financialDepartmentRepository = financialDepartmentRepository;
    }

    @Override
    @Transactional
    public DataSourceResult financialDepartmentList() {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        List<Object[]> financialDocumentItemList = financialDepartmentRepository.getFinancialDocumentItemList(organizationId);
        List<FinancialDepartmentResponse> financialDepartmentResponses = financialDocumentItemList.stream().map(item ->
                FinancialDepartmentResponse.builder()
                        .departmentId(Long.parseLong(item[0].toString()))
                        .code(item[1].toString())
                        .name((item[2].toString()))
                        .financialLedgerTypeId(Long.parseLong(item[3] == null ? "0" : item[3].toString()))
                        .ledgerTypeDescription(item[4] == null ? "" : item[4].toString())
                        .financialDepartmentLedgerId(Long.parseLong(item[5] == null ? "0" : item[5].toString()))
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialDepartmentResponses);
        dataSourceResult.setTotal(financialDocumentItemList.size());
        return dataSourceResult;

    }
}
