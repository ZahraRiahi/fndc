package ir.demisco.cfs.service.impl;

import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import java.util.List;

@Component
public class FinancialNumberingFormatGridProvider implements GridDataProvider {


    @Override
    public Class<?> getRootEntityClass() {
        return null;
    }

    @Override
    public Selection<?> getCustomSelection(FilterContext filterContext) {
        return GridDataProvider.super.getCustomSelection(filterContext);
    }

    @Override
    public Predicate getCustomRestriction(FilterContext filterContext) {
        return GridDataProvider.super.getCustomRestriction(filterContext);
    }

    @Override
    public List<Object> mapToDto(List<Object> resultList) {
        return GridDataProvider.super.mapToDto(resultList);
    }
}
