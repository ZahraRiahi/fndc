package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialNumberingFormatTypeDto;
import ir.demisco.cfs.model.entity.FinancialNumberingFormatType;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Selection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialNumberingFormatTypeGridProvider implements GridDataProvider {

    @Override
    public Class<?> getRootEntityClass() {
        return FinancialNumberingFormatType.class;
    }

    @Override
    public Selection<?> getCustomSelection(FilterContext filterContext) {
        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
        return criteriaBuilder.array(
                filterContext.getPath("id"),
                filterContext.getPath("description"),
                filterContext.getPath("code")
        );
    }

    @Override
    public List<Object> mapToDto(List<Object> resultList) {
        return resultList.stream().map((Object object) -> {
            Object[] array = (Object[]) object;
            return FinancialNumberingFormatTypeDto.builder()
                    .id((Long) array[0])
                    .description((String) array[1])
                    .format((String) array[2])
                    .build();
        }).collect(Collectors.toList());
    }
}
