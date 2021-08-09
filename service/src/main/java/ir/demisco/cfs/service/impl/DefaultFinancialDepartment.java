package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDepartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ir.demisco.cfs.service.api.FinancialDepartmentService;
import ir.demisco.cfs.service.repository.FinancialDepartmentRepository;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
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
    public DataSourceResult financialDepartmentList(DataSourceRequest dataSourceRequest) {
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Page<Object[]> list1 = financialDepartmentRepository.getFinancialDocumentItemList(100L,pageable);
        List<FinancialDepartmentResponse> financialLedgerTypeResponses = list1.stream().map(item ->
                FinancialDepartmentResponse.builder()
                        .departmentId(Long.parseLong(item[0].toString()))
                        .code(item[1].toString())
                        .name((item[2].toString()))
                        .financialLedgerTypeId(Long.valueOf((item[3].toString())))
                        .ledgerTypeDescription(item[4].toString())
                        .financialDepartmentLedgerId(Long.valueOf(item[5].toString()))
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialLedgerTypeResponses);
        dataSourceResult.setTotal(list1.getTotalElements());
        return  dataSourceResult;

    }
}