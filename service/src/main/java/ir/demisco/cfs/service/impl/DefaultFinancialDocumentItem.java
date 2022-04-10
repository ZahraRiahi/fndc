package ir.demisco.cfs.service.impl;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import ir.demisco.cfs.model.dto.response.FinancialDocumentItemDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentItemOutPutResponse;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentDto;
import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import ir.demisco.cfs.service.api.FinancialDocumentItemService;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ir.demisco.core.utils.DateUtil;

@Service
public class DefaultFinancialDocumentItem implements FinancialDocumentItemService {

    private final FinancialDocumentItemRepository financialDocumentItemRepository;


    public DefaultFinancialDocumentItem(FinancialDocumentItemRepository financialDocumentItemRepository) {
        this.financialDocumentItemRepository = financialDocumentItemRepository;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialDocumentItemList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        ResponseFinancialDocumentDto paramSearch = setParameter(filters);
        Map<String, Object> paramMap = paramSearch.getParamMap();
        List<Object[]> list = financialDocumentItemRepository.getFinancialDocumentItemList(SecurityHelper.getCurrentUser().getOrganizationId(), paramSearch.getActivityCode(), SecurityHelper.getCurrentUser().getUserId(), paramSearch.getDepartmentId(), SecurityHelper.getCurrentUser().getUserId(), paramSearch.getStartDate(),
                paramSearch.getEndDate(), paramSearch.getPriceTypeId(), paramSearch.getFinancialNumberingTypeId(), paramMap.get("fromNumber"), paramSearch.getFromNumber(),
                paramMap.get("toNumber"), paramSearch.getToNumber(), paramSearch.getFinancialDocumentStatusDtoListId(), paramSearch.getDescription(), paramMap.get("fromAccount"), paramSearch.getFromAccountCode(),
                paramMap.get("toAccount"), paramSearch.getToAccountCode(), paramMap.get("centricAccount"), paramSearch.getCentricAccountId(), paramMap.get("centricAccountType"), paramSearch.getCentricAccountTypeId(), paramMap.get("documentUser"), paramSearch.getDocumentUserId(), paramMap.get("priceType"), paramMap.get("fromPrice"), paramSearch.getFromPrice(), paramMap.get("toPrice"),
                paramSearch.getToPrice(), paramSearch.getTolerance(), paramMap.get("financialDocumentType"), paramSearch.getFinancialDocumentTypeId());
        List<FinancialDocumentItemDto> documentItemDtoList = list.stream().map(item ->
                FinancialDocumentItemDto.builder()
                        .id(((BigDecimal) item[0]).longValue())
                        .date((Date) item[1])
                        .description(item[2] != null ? item[2].toString() : null)
                        .documentNumber(Long.parseLong(item[4].toString()))
                        .sequenceNumber(Long.parseLong(item[3].toString()))
                        .financialAccountDescription(item[5].toString())
                        .financialAccountId(Long.parseLong(item[6].toString()))
                        .debitAmount(Long.parseLong(String.format("%.0f", Double.parseDouble(item[7].toString()))))
                        .creditAmount(Long.parseLong(String.format("%.0f", Double.parseDouble(item[8].toString()))))
                        .fullDescription(item[9] == null ? null : item[9].toString())
                        .financialAccountCode(item[10] == null ? null : item[10].toString())
                        .financialDocumentId(Long.parseLong(item[11].toString()))
                        .centricAccountDescription(item[12] == null ? null : item[12].toString())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(documentItemDtoList.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setTotal(list.size());
        return dataSourceResult;
    }

    private ResponseFinancialDocumentDto setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        ResponseFinancialDocumentDto responseFinancialDocumentDto = new ResponseFinancialDocumentDto();
        Map<String, Object> map = new HashMap<>();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "activityCode":
                    checkActivityCodeSet(responseFinancialDocumentDto, item);
                    break;
                case "departmentId":
                    responseFinancialDocumentDto.setDepartmentId(Long.parseLong(item.getValue().toString()));
                    break;
                case "startDate":
                    responseFinancialDocumentDto.setStartDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
                    break;

                case "endDate":
                    responseFinancialDocumentDto.setEndDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
                    break;
                case "priceType.id":
                    checkPriceTypeSet(responseFinancialDocumentDto, item);
                    break;

                case "financialNumberingType.id":
                    responseFinancialDocumentDto.setFinancialNumberingTypeId(Long.parseLong(item.getValue().toString()));
                    break;

                case "fromNumber.id":
                    checkFromNumberSet(responseFinancialDocumentDto, item);
                    break;

                case "toNumber.id":
                    checkToNumberSet(responseFinancialDocumentDto, item);
                    break;

                case "financialDocumentStatusDtoList":
                    responseFinancialDocumentDto.setFinancialDocumentStatusDtoListId((List<Long>) item.getValue());
                    break;

                case "description":
                    checkDescriptionSet(responseFinancialDocumentDto, item);
                    break;

                case "fromAccount.code":
                    checkFromAccountCodeSet(responseFinancialDocumentDto, item);
                    break;

                case "toAccount.code":
                    checkToAccountCodeSet(responseFinancialDocumentDto, item);
                    break;

                case "centricAccount.id":
                    checkCentricAccountIdSet(responseFinancialDocumentDto, item);
                    break;
                case "centricAccountType.id":
                    checkCentricAccountTypeIdSet(responseFinancialDocumentDto, item);
                    break;

                case "documentUser.id":
                    checkDocumentUserIdSet(responseFinancialDocumentDto, item);
                    break;
                case "fromPriceAmount":
                    checkFromPriceSet(responseFinancialDocumentDto, item);
                    break;

                case "toPriceAmount":
                    checkToPriceSet(responseFinancialDocumentDto, item);
                    break;

                case "tolerance":
                    checkToleranceSet(responseFinancialDocumentDto, item);
                    break;
                case "financialDocumentType.id":
                    if (item.getValue() != null) {
                        map.put("financialDocumentType", "financialDocumentType");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFinancialDocumentTypeId(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("financialDocumentType", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFinancialDocumentTypeId(0L);
                    }
                    break;
                default:

                    break;
            }
        }

        return responseFinancialDocumentDto;
    }

    private void checkActivityCodeSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            responseFinancialDocumentDto.setActivityCode(item.getValue().toString());
        } else {
            throw new RuleException("fin.document.activityCode.is.null");
        }
    }

    private void checkPriceTypeSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("priceType", "priceType");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setPriceTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            map.put("priceType", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setPriceTypeId(0L);
        }
    }

    private void checkFromNumberSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("fromNumber", "fromNumber");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromNumber(Long.parseLong(item.getValue().toString()));
        } else {
            map.put("fromNumber", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromNumber(0L);
        }
    }

    private void checkFromAccountCodeSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("fromAccount", "fromAccount");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromAccountCode(Long.parseLong(item.getValue().toString()));
        } else {
            map.put("fromAccount", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromAccountCode(0L);
        }
    }

    private void checkDescriptionSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            responseFinancialDocumentDto.setDescription(item.getValue().toString());
        } else {
            responseFinancialDocumentDto.setDescription("");
        }
    }

    private void checkToNumberSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("toNumber", "toNumber");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToNumber(Long.parseLong(item.getValue().toString()));
        } else {
            map.put("toNumber", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToNumber(0L);
        }
    }

    private void checkToAccountCodeSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("toAccount", "toAccount");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToAccountCode(Long.parseLong(item.getValue().toString()));
        } else {
            map.put("toAccount", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToAccountCode(0L);
        }
    }

    private void checkCentricAccountIdSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("centricAccount", "centricAccount");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setCentricAccountId(Long.parseLong(item.getValue().toString()));
        } else {
            map.put("centricAccount", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setCentricAccountId(0L);
        }
    }

    private void checkCentricAccountTypeIdSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("centricAccountType", "centricAccountType");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setCentricAccountTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            map.put("centricAccountType", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setCentricAccountTypeId(0L);
        }
    }

    private void checkDocumentUserIdSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("documentUser", "documentUser");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setDocumentUserId(Long.parseLong(item.getValue().toString()));
        } else {
            map.put("documentUser", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setDocumentUserId(0L);
        }
    }

    private void checkFromPriceSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("fromPrice", "fromPrice");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromPrice(Long.parseLong(item.getValue().toString()));
        } else {
            map.put("fromPrice", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromPrice(0L);
        }
    }

    private void checkToPriceSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("toPrice", "toPrice");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToPrice(Long.parseLong(item.getValue().toString()));
        } else {
            map.put("toPrice", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToPrice(0L);
        }
    }

    private void checkToleranceSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            responseFinancialDocumentDto.setTolerance(Double.parseDouble(item.getValue().toString()));
        } else {
            responseFinancialDocumentDto.setTolerance(0D);
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

    private LocalDateTime checkTry(Object input, boolean truncateDate) {
        try {
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

    @Override
    @Transactional
    public FinancialDocumentItemOutPutResponse getFinancialDocumentItemById(Long financialDocumentItemId) {
        FinancialDocumentItem financialDocumentItem = financialDocumentItemRepository.findById(financialDocumentItemId).orElseThrow(() -> new RuleException("fin.ruleException.notFoundId"));
        return FinancialDocumentItemOutPutResponse.builder().id(financialDocumentItemId)
                .financialDocumentId(financialDocumentItem.getFinancialDocument().getId())
                .sequenceNumber(financialDocumentItem.getSequenceNumber())
                .debitAmount(financialDocumentItem.getDebitAmount())
                .creditAmount(financialDocumentItem.getCreditAmount())
                .description(financialDocumentItem.getDescription())
                .description(financialDocumentItem.getDescription() == null ? "" : financialDocumentItem.getDescription())
                .financialAccountId(financialDocumentItem.getFinancialAccount().getId())
                .centricAccountId1(financialDocumentItem.getCentricAccountId1() == null ? null : financialDocumentItem.getCentricAccountId1().getId())
                .centricAccountId2(financialDocumentItem.getCentricAccountId2() == null ? null : financialDocumentItem.getCentricAccountId2().getId())
                .centricAccountId3(financialDocumentItem.getCentricAccountId3() == null ? null : financialDocumentItem.getCentricAccountId3().getId())
                .centricAccountId4(financialDocumentItem.getCentricAccountId4() == null ? null : financialDocumentItem.getCentricAccountId4().getId())
                .centricAccountId5(financialDocumentItem.getCentricAccountId5() == null ? null : financialDocumentItem.getCentricAccountId5().getId())
                .centricAccountId6(financialDocumentItem.getCentricAccountId6() == null ? null : financialDocumentItem.getCentricAccountId6().getId())
                .creatorId(financialDocumentItem.getCreator().getId())
                .lastModifierId(financialDocumentItem.getLastModifier().getId())
                .build();
    }
}
