package ir.demisco.cfs.service.impl;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import ir.demisco.cfs.model.dto.request.FinancialNumberingTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeOutputResponse;
import ir.demisco.cfs.service.api.FinancialNumberingTypeService;
import ir.demisco.cfs.service.repository.FinancialNumberingTypeRepository;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialNumberingType implements FinancialNumberingTypeService {
    private final FinancialNumberingTypeRepository financialNumberingTypeRepository;
    private final GridFilterService gridFilterService;
    private final FinancialNumberingTypeGridProvider financialNumberingTypeGridProvider;

    public DefaultFinancialNumberingType(FinancialNumberingTypeRepository financialNumberingTypeRepository, GridFilterService gridFilterService, FinancialNumberingTypeGridProvider financialNumberingTypeGridProvider) {
        this.financialNumberingTypeRepository = financialNumberingTypeRepository;
        this.gridFilterService = gridFilterService;
        this.financialNumberingTypeGridProvider = financialNumberingTypeGridProvider;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<FinancialNumberingTypeOutputResponse> getNumberingTypeLov(FinancialNumberingTypeRequest financialNumberingTypeRequest) {
        if (financialNumberingTypeRequest.getFromDate() == null && financialNumberingTypeRequest.getToDate() == null) {
            List<Object[]> financialNumberingTypeList = financialNumberingTypeRepository.findByFinancialNumberingType();
            return financialNumberingTypeList.stream().map(e -> FinancialNumberingTypeOutputResponse.builder().id(Long.parseLong(e[0].toString()))
                    .description(e[1] == null ? "" : e[1].toString())
                    .fromCode(null)
                    .toCode(null)
                    .serialLength(0L)
                    .build()).collect(Collectors.toList());
        } else {
            Object fromDateObject;
            LocalDateTime fromDateFormat = null;
            if (financialNumberingTypeRequest.getFromDate() == null) {
                fromDateObject = null;
                fromDateFormat=LocalDateTime.now();
            } else {
                fromDateObject = "fromDateObject";
                fromDateFormat = financialNumberingTypeRequest.getFromDate();
            }

            Object toDateObject;
            LocalDateTime toDateFormat = null;
            if (financialNumberingTypeRequest.getToDate() == null) {
                toDateObject = null;
                toDateFormat=LocalDateTime.now();
            } else {
                toDateObject = "toDateObject";
                toDateFormat = financialNumberingTypeRequest.getToDate();
            }
            List<Object[]> financialNumberingTypeList = financialNumberingTypeRepository.findByFinancialNumberingTypeAndOrganizationIdAndFromAndToDate(SecurityHelper.getCurrentUser().getOrganizationId(), fromDateObject, fromDateFormat,toDateObject, toDateFormat, SecurityHelper.getCurrentUser().getUserId());

            return financialNumberingTypeList.stream().map(e -> FinancialNumberingTypeOutputResponse.builder().id(Long.parseLong(e[0].toString()))
                    .description(gatItemForString(e, 1))
                    .fromCode(e[3] == null ? "" : e[3].toString())
                    .toCode(e[4] == null ? "" : e[4].toString())
                    .serialLength(Long.parseLong(e[2] == null ? null : e[2].toString()))
                    .build()).collect(Collectors.toList());
        }

    }

    private LocalDateTime checkTry(Object input, boolean truncateDate) {
        try {
            //                Date date = ISO8601Utils.parse((String) input);
            Date date = ISO8601Utils.parse((String) input, new ParsePosition(0));
            LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return truncateDate ? DateUtil.truncate(localDateTime) : localDateTime;
        } catch (Exception var4) {
            if (((String) input).equalsIgnoreCase("current_date")) {
                return truncateDate ? DateUtil.truncate(LocalDateTime.now()) : LocalDateTime.now();
            } else {
                return ((String) input).equalsIgnoreCase("current_timestamp") ? LocalDateTime.now() : LocalDateTime.parse((String) input);
            }
        }
    }

    private LocalDateTime parseStringToLocalDateTime(Object input, boolean truncateDate) {
        if (input instanceof String) {
            return checkTry(input, truncateDate);
        } else if (input instanceof LocalDateTime) {
            return truncateDate ? DateUtil.truncate((LocalDateTime) input) : (LocalDateTime) input;
        } else {
            throw new IllegalArgumentException("Filter for LocalDateTime has error :" + input + " with class" + input.getClass());
        }
    }

    private String gatItemForString(Object[] e, int i) {
        return e[i] == null ? null : e[i].toString();
    }

    @Override
    public DataSourceResult getNumberingType(DataSourceRequest dataSourceRequest) {
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest, financialNumberingTypeGridProvider);
    }
}

