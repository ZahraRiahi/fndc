//package ir.demisco.cfs.service.impl;
//
//import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeResponse;
//import ir.demisco.cfs.model.entity.LedgerNumberingType;
//import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
//import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.criteria.*;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class FinancialLedgerTypeListGridProvider  implements GridDataProvider {
//
//    @Override
//    public Class<?> getRootEntityClass() {
//        return LedgerNumberingType.class;
//    }
//
//    @Override
//    public Selection<?> getCustomSelection(FilterContext filterContext) {
//        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
//        return criteriaBuilder.array(
//                filterContext.getPath("financialLedgerType.id"),
//                filterContext.getPath("financialLedgerType.description"),
//                filterContext.getPath("financialLedgerType.activeFlag"),
//                filterContext.getPath("financialLedgerType.financialCodingType.id"),
//                filterContext.getPath("financialLedgerType.financialCodingType.description"),
//                filterContext.getPath("financialNumberingType.description")
//
//        );
//    }
//
//    @Override
//    public Predicate getCustomRestriction(FilterContext filterContext) {
//
////        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
////        CriteriaQuery<String> criteriaBuilderQuery = criteriaBuilder.createQuery(String.class);
////        Root<LedgerNumberingType> root = criteriaBuilderQuery.from(LedgerNumberingType.class);
////
////        Join<FinancialLedgerType, FinancialNumberingType> financialNumberingTypeId = root.join("financialNumberingType", JoinType.LEFT);
////        criteriaBuilder.equal(financialNumberingTypeId.get("financialNumberingType.id"), criteriaBuilder.parameter(Integer.class, "id"));
////
////        Join<FinancialLedgerType,LedgerNumberingType> ledgerNumberingTypeId = financialNumberingTypeId.join("ledgerNumberingTypeList", JoinType.LEFT);
////        criteriaBuilder.equal(ledgerNumberingTypeId.get("ledgerNumberingTypeList.id"), criteriaBuilder.parameter(Integer.class, "id"));
//        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
//        Root<Object> root = filterContext.getRoot();
//        Join<Object, Object> financialNumberingType = root.join("financialNumberingType", JoinType.LEFT);
//        criteriaBuilder.equal(financialNumberingType.get("financialNumberingType.id"), root.get("id"));
//        Join<Object, Object> financialLedgerType = root.join("financialLedgerType", JoinType.LEFT);
//        criteriaBuilder.equal(financialLedgerType.get("financialLedgerType.id"), root.get("id"));
//
//
//        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
//        DataSourceRequest dataSourceRequest = filterContext.getDataSourceRequest();
//        for (DataSourceRequest.FilterDescriptor filter : dataSourceRequest.getFilter().getFilters()) {
//            switch (filter.getField()) {
//                case "financialLedgerType.organization.id":
//                case "financialLedgerType.financialCodingType.id":
//            }
//        }
//        criteriaQuery.select(root).where(criteriaBuilder.and(criteriaQuery.select(root).where(criteriaBuilder.isNull(root.get("financialLedgerType.deletedDate"))), ));
//        criteriaQuery.select(root).where(criteriaBuilder.and(, criteriaQuery.select(root).where(criteriaBuilder.isNull(root.get("financialLedgerType.financialCodingType.deletedDate")))));
//
//        criteriaQuery.select(root).where(criteriaBuilder.isNull(root.get("financialLedgerType.financialCodingType.deletedDate")));
//        criteriaQuery.select(root).where(criteriaBuilder.isNull(root.get("financialLedgerType.deletedDate")));
//
//        criteriaQuery.groupBy(root.get("financialLedgerType.description"), root.get("financialLedgerType.financialCodingType.id")
//                ,root.get("financialLedgerType.activeFlag")
//                ,root.get("financialLedgerType.financialCodingType.description"));
//
//
//        return null;
//
//    }
//
//    @Override
//    public List<Object> mapToDto(List<Object> resultList) {
//        return resultList.stream().map(object -> {
//            Object[] array = (Object[]) object;
//
//            return FinancialLedgerTypeResponse.builder()
//                    .id((Long) array[0])
//                    .description((String) array[1])
//                    .activeFlag((Boolean) array[2])
//                    .financialCodingTypeId((Long) array[3])
//                    .financialCodingTypeDescription((String) array[4])
//                    .financialNumberingTypeDescription((String) array[5])
//                    .build();
//        }).collect(Collectors.toList());
//    }
//}
