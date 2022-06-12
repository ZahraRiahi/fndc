package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialConfigDto;
import ir.demisco.cfs.model.entity.FinancialConfig;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialConfigListGridProvider implements GridDataProvider {
    @Override
    public Class<?> getRootEntityClass() {
        return FinancialConfig.class;
    }

    @Override
    public Selection<?> getCustomSelection(FilterContext filterContext) {
        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
        return criteriaBuilder.array(
                filterContext.getPath("id"),
                filterContext.getPath("organization.id"),
                filterContext.getPath("financialDepartment.id"),
                filterContext.getPath("user.id"),
                filterContext.getPath("financialDocumentType.id"),
                filterContext.getPath("documentDescription"),
                filterContext.getPath("financialLedgerType.id"),
                filterContext.getPath("financialPeriod.id"),
                filterContext.getPath("financialDepartment.code"),
                filterContext.getPath("financialDepartment.name"),
                filterContext.getPath("financialPeriod.startDate"),
                filterContext.getPath("financialPeriod.endDate"),
                filterContext.getPath("financialPeriod.description"),
                filterContext.getPath("financialDocumentType.description"),
                filterContext.getPath("financialLedgerType.description"),
                filterContext.getPath("financialLedgerType.financialCodingType.id"),
                filterContext.getPath("department.id"),
                filterContext.getPath("department.name")
        );
    }

    @Override
    public List<Object> mapToDto(List<Object> resultList) {

        return resultList.stream().map(object -> {
            Object[] array = (Object[]) object;

            return FinancialConfigDto.builder()
                    .id((Long) array[0])
                    .organizationId((Long) array[1])
                    .financialDepartmentId((Long) array[2])
                    .userId((Long) array[3])
                    .financialDocumentTypeId((Long) array[4])
                    .documentDescription((String) array[5])
                    .financialLedgerTypeId((Long) array[6])
                    .financialPeriodId((Long) array[7])
                    .financialDepartmentCode((String) array[8])
                    .financialDepartmentName((String) array[9])
                    .financialPeriodStartDate(((LocalDateTime) array[10]).toLocalDate())
                    .financialPeriodEndDate(((LocalDateTime) array[11]).toLocalDate())
                    .financialPeriodDescription((String) array[12])
                    .financialDocumentTypeDescription((String) array[13])
                    .financialLedgerTypeDescription((String) array[14])
                    .financialCodingTypeId((Long) array[15])
                    .departmentId((Long) array[16])
                    .departmentName((String) array[17])
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public Predicate getCustomRestriction(FilterContext filterContext) {
        DataSourceRequest dataSourceRequest = filterContext.getDataSourceRequest();
        for (DataSourceRequest.FilterDescriptor filter : dataSourceRequest.getFilter().getFilters()) {
            String field = filter.getField();
            if (filter.getValue() == null && ("user.id".equals(field) || "organization.id".equals(field) || "financialDepartment.id".equals(field))) {
                filter.setDisable(true);
            }
        }
        return null;
    }

}
