package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDocumentDescriptionDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentDescriptionOrganizationDto;
import ir.demisco.cfs.model.entity.FinancialDocumentDescription;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Selection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialDocumentDescriptionListGridProvider implements GridDataProvider {

    @Override
    public Class<?> getRootEntityClass() {
        return FinancialDocumentDescription.class;
    }

    @Override
    public Selection<?> getCustomSelection(FilterContext filterContext) {
        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
        return criteriaBuilder.array(
                filterContext.getPath("id"),
                filterContext.getPath("organization.id"),
                filterContext.getPath("description"),
                filterContext.getPath("deletedDate")
        );
    }

    @Override
    public List<Object> mapToDto(List<Object> resultList) {
        return resultList.stream().map(object -> {
            Object[] array = (Object[]) object;
            return FinancialDocumentDescriptionOrganizationDto.builder()
                    .id((Long) array[0])
                    .organizationId((Long) array[1])
                    .description((String) array[2])
                    .build();


        }).collect(Collectors.toList());
    }
}
