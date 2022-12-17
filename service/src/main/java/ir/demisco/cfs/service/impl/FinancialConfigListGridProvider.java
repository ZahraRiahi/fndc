package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialConfigDto;
import ir.demisco.cfs.model.entity.FinancialConfig;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

        return resultList.stream().map((Object object)-> {
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
        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
        Root<Object> root = filterContext.getRoot();
        Join<Object, Object> financialPeriod = root.join("financialPeriod", JoinType.LEFT);
        Join<Object, Object> financialLedgerType = root.join("financialLedgerType", JoinType.LEFT);
        Join<Object, Object> financialDocumentType = root.join("financialDocumentType", JoinType.LEFT);
        Join<Object, Object> organization = root.join("organization", JoinType.LEFT);
        Join<Object, Object> user = root.join("user", JoinType.LEFT);
        Join<Object, Object> financialDepartment = root.join("financialDepartment", JoinType.LEFT);
        criteriaBuilder.equal(financialPeriod.get("id"), root.get("id"));
        criteriaBuilder.equal(financialLedgerType.get("id"), root.get("id"));
        criteriaBuilder.equal(financialDocumentType.get("id"), root.get("id"));
        criteriaBuilder.equal(organization.get("id"), root.get("id"));
        criteriaBuilder.equal(user.get("id"), root.get("id"));
        criteriaBuilder.equal(financialDepartment.get("id"), root.get("id"));

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
