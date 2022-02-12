package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.FinancialLedgerTypeParameterDto;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentResponse;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeResponse;
import ir.demisco.cfs.service.api.FinancialDepartmentService;
import ir.demisco.cfs.service.repository.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;

    public DefaultFinancialDepartment(FinancialDepartmentRepository financialDepartmentRepository, DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

//    @Override
//    @Transactional
//    public DataSourceResult financialDepartmentList() {
//        Long organizationId = 100L;
//        List<Object[]> financialDocumentItemList = departmentRepository.getFinancialDocumentItemList(organizationId, );
//        List<FinancialDepartmentResponse> financialDepartmentResponses = financialDocumentItemList.stream().map(item ->
//                FinancialDepartmentResponse.builder()
//                        .departmentId(Long.parseLong(item[0].toString()))
//                        .code(item[1].toString())
//                        .name((item[2].toString()))
//                        .financialLedgerTypeId(Long.parseLong(item[3] == null ? "0" : item[3].toString()))
//                        .ledgerTypeDescription(item[4] == null ? "" : item[4].toString())
//                        .financialDepartmentLedgerId(Long.parseLong(item[5] == null ? "0" : item[5].toString()))
//                        .build()).collect(Collectors.toList());
//        DataSourceResult dataSourceResult = new DataSourceResult();
//        dataSourceResult.setData(financialDepartmentResponses);
//        dataSourceResult.setTotal(financialDocumentItemList.size());
//        return dataSourceResult;
//
//    }

    @Override
    @Transactional
    public DataSourceResult financialDepartmentList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialDepartmentResponse param = setParameterToDto(filters);
        List<Object[]> financialDocumentItemList = departmentRepository.getFinancialDocumentItemList(SecurityHelper.getCurrentUser().getOrganizationId(), param.getDepartmentId());
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
        dataSourceResult.setData(financialDepartmentResponses.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setTotal(financialDocumentItemList.size());
        return dataSourceResult;
    }

    private FinancialDepartmentResponse setParameterToDto(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialDepartmentResponse financialDepartmentResponse = new FinancialDepartmentResponse();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "department.id":
                    if (item.getValue() != null) {
                        financialDepartmentResponse.setDepartment("department");
                        financialDepartmentResponse.setDepartmentId(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialDepartmentResponse.setDepartment(null);
                        financialDepartmentResponse.setDepartmentId(0L);
                    }
                    break;
            }
        }
        return financialDepartmentResponse;
    }
}
