package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialSecurityFilterRequest;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentResponse;
import ir.demisco.cfs.service.api.FinancialDepartmentService;
import ir.demisco.cfs.service.repository.DepartmentRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialDepartment implements FinancialDepartmentService {
    private final DepartmentRepository departmentRepository;

    public DefaultFinancialDepartment(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public DataSourceResult financialDepartmentList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialSecurityFilterRequest param = setParameterToDto(filters);
        param.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        param.setUserId(SecurityHelper.getCurrentUser().getUserId());
        param.setCreatorUserId(SecurityHelper.getCurrentUser().getUserId());
        if (param.getUserId() == null) {
            throw new RuleException("fin.security.check.user.id");
        }
        if (param.getDepartmentId() == null) {
            throw new RuleException("fin.security.check.department.id");
        }
        if (param.getActivityCode() == null) {
            throw new RuleException("fin.security.check.activity.code");
        }
        if (param.getInputFromConfigFlag() == null) {
            throw new RuleException("fin.security.check.input.from.config.flag");
        }
        // comment jira FIN-1126 organ pakage -1
        Long organizationIdPKG = -1L;
        List<Object[]> financialDocumentItemList = departmentRepository.getFinancialDocumentItemList(
               SecurityHelper.getCurrentUser().getOrganizationId()
                , organizationIdPKG
                , param.getActivityCode()
                , new TypedParameterValue(StandardBasicTypes.LONG, param.getFinancialPeriodId())
                , new TypedParameterValue(StandardBasicTypes.LONG, param.getDocumentTypeId())
                , new TypedParameterValue(StandardBasicTypes.LONG, param.getCreatorUserId())
                , param.getDepartmentId()
                , param.getUserId());
        List<FinancialDepartmentResponse> financialDepartmentResponses = financialDocumentItemList.stream().map(item ->
                FinancialDepartmentResponse.builder()
                        .departmentId(Long.parseLong(item[0].toString()))
                        .code(item[1].toString())
                        .name((item[2].toString()))
                        .financialLedgerTypeId(Long.parseLong(item[3] == null ? "0" : item[3].toString()))
                        .ledgerTypeDescription(item[4] == null ? "" : item[4].toString())
                        .financialDepartmentLedgerId(Long.parseLong(item[5] == null ? "0" : item[5].toString()))
//                        .disabled(Integer.parseInt(item[6].toString()) == 1)
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialDepartmentResponses.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setTotal(financialDocumentItemList.size());
        return dataSourceResult;
    }

    private FinancialSecurityFilterRequest setParameterToDto(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialSecurityFilterRequest financialSecurityFilterRequest = new FinancialSecurityFilterRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "departmentId":
                    if (item.getValue() != null) {
                        financialSecurityFilterRequest.setDepartmentId(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialSecurityFilterRequest.setDepartmentId(null);
                    }
                    break;
                case "financialDepartmentId":
                    if (item.getValue() != null) {
                        financialSecurityFilterRequest.setFinancialDepartmentId(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialSecurityFilterRequest.setFinancialDepartmentId(null);
                    }
                    break;


                case "financialLedgerId":
                    if (item.getValue() != null) {
                        financialSecurityFilterRequest.setFinancialLedgerId(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialSecurityFilterRequest.setFinancialLedgerId(null);
                    }
                    break;
                case "financialPeriodId":
                    if (item.getValue() != null) {
                        financialSecurityFilterRequest.setFinancialPeriodId(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialSecurityFilterRequest.setFinancialPeriodId(null);
                    }
                    break;
                case "documentTypeId":
                    if (item.getValue() != null) {
                        financialSecurityFilterRequest.setDocumentTypeId(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialSecurityFilterRequest.setDocumentTypeId(null);
                    }
                    break;
                case "subjectId":
                    if (item.getValue() != null) {
                        financialSecurityFilterRequest.setSubjectId(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialSecurityFilterRequest.setSubjectId(null);
                    }
                    break;
                case "activityCode":
                    if (item.getValue() != null) {
                        financialSecurityFilterRequest.setActivityCode(item.getValue().toString());
                    } else {
                        financialSecurityFilterRequest.setActivityCode(null);
                    }
                    break;

                case "inputFromConfigFlag":
                    if (item.getValue() != null) {
                        financialSecurityFilterRequest.setInputFromConfigFlag((Boolean) item.getValue());
                    } else {
                        financialSecurityFilterRequest.setInputFromConfigFlag(null);
                    }
                    break;
                default:
            }
        }
        return financialSecurityFilterRequest;
    }
}
