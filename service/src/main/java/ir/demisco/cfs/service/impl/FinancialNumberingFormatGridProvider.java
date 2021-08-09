package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialNumberingFormatDto;
import ir.demisco.cfs.model.entity.FinancialNumberingFormat;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Selection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialNumberingFormatGridProvider implements GridDataProvider {

    @Override
    public Class<?> getRootEntityClass() {
        return FinancialNumberingFormat.class;
    }

    @Override
    public Selection<?> getCustomSelection(FilterContext filterContext) {
        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
        return criteriaBuilder.array(
                filterContext.getPath("id"),
                filterContext.getPath("organization.id"),
                filterContext.getPath("financialNumberingFormatType.id"),
                filterContext.getPath("financialNumberingFormatType.description"),
                filterContext.getPath("financialNumberingType.id"),
                filterContext.getPath("financialNumberingType.description"),
                filterContext.getPath("description"),
                filterContext.getPath("deletedDate")
        );
    }

    @Override
    public List<Object> mapToDto(List<Object> resultList) {
        return resultList.stream().map(object -> {
            Object[] array = (Object[]) object;
            return FinancialNumberingFormatDto.builder()
                    .id((Long) array[0])
                    .organizationId((Long) array[1])
                    .financialNumberingFormatTypeId((Long) array[2])
                    .financialNumberingFormatTypeDescription((String) array[3])
                    .financialNumberingTypeId((Long) array[4])
                    .financialNumberingTypeDescription((String) array[5])
                    .description((String) array[6])
                    .build();
        }).collect(Collectors.toList());
    }
}
