package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDocumentDto;

import ir.demisco.cfs.model.entity.FinancialDocument;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Selection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialDocumentListGridProvider  implements GridDataProvider {

    @Override
    public Class<?> getRootEntityClass() {
        return FinancialDocument.class;
    }

    @Override
    public Selection<?> getCustomSelection(FilterContext filterContext) {
        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
        return criteriaBuilder.array(
                filterContext.getPath("id"),
                filterContext.getPath("documentDate"),
                filterContext.getPath("description"),
                filterContext.getPath("financialDocumentStatus.id"),
                filterContext.getPath("financialDocumentStatus.code"),
                filterContext.getPath("financialDocumentStatus.name"),
                filterContext.getPath("permanentDocumentNumber"),
                filterContext.getPath("automaticFlag"),
                filterContext.getPath("organization.id"),
                filterContext.getPath("financialDocumentType.id"),
                filterContext.getPath("financialDocumentType.description"),
                filterContext.getPath("financialPeriodId"),
                filterContext.getPath("financialLedgerType.id"),
                filterContext.getPath("financialLedgerType.description"),
                filterContext.getPath("financialDepartment.id"),
                filterContext.getPath("financialDepartment.code"),
                filterContext.getPath("financialDepartment.name"),
                filterContext.getPath("documentNumber"),
                filterContext.getPath("DeletedDate")
        );
    }

    @Override
    public List<Object> mapToDto(List<Object> resultList) {
        return resultList.stream().map((Object object) -> {
            Object[] array = (Object[]) object;
        return FinancialDocumentDto.builder()
                .id((Long) array[0])
                .documentDate((LocalDateTime) array[1])
                .description((String) array[2])
                .financialDocumentTypeId((Long) array[9])
                .financialDocumentTypeDescription((String) array[10])
                .documentNumber(array[17].toString())
                .deletedDate((LocalDateTime) array[18])
                .build();
        }).collect(Collectors.toList());
    }
}
