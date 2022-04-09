package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeDto;
import ir.demisco.cfs.model.entity.FinancialDocumentType;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialDocumentTypeProvider implements GridDataProvider {
    @Override
    public Class<?> getRootEntityClass() {
        return FinancialDocumentType.class;
    }

    @Override
    public Selection<?> getCustomSelection(FilterContext filterContext) {
        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
        return criteriaBuilder.array(
                filterContext.getPath("id"),
                filterContext.getPath("description"),
                filterContext.getPath("activeFlag"),
                filterContext.getPath("automaticFlag"),
                filterContext.getPath("financialSystem.id"),
                filterContext.getPath("organization.id"),
                filterContext.getPath("financialSystem.description")
        );
    }

    @Override
    public List<Object> mapToDto(List<Object> resultList) {

        return resultList.stream().map(object -> {
            Object[] array = (Object[]) object;

            return FinancialDocumentTypeDto.builder()
                    .id((Long) array[0])
                    .description((String) array[1])
                    .activeFlag((Boolean) array[2])
                    .automaticFlag((Boolean) array[3])
                    .financialSystemId((Long) array[4])
                    .organizationId((Long) array[5])
                    .financialSystemDescription((String) array[6])
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public Predicate getCustomRestriction(FilterContext filterContext) {
        DataSourceRequest dataSourceRequest = filterContext.getDataSourceRequest();
        for (DataSourceRequest.FilterDescriptor filter : dataSourceRequest.getFilter().getFilters()) {
            String field = filter.getField();
            if (filter.getValue() == null && ("financialSystem.id".equals(field) || "organization.id".equals(field) || "id".equals(field))) {

                filter.setDisable(true);

            }
        }
        return null;
    }
}
