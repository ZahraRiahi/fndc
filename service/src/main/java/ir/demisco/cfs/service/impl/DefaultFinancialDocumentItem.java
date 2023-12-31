package ir.demisco.cfs.service.impl;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import ir.demisco.cfs.model.dto.request.FinancialDocumentSecurityInputRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentItemDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentItemOutPutResponse;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentDto;
import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import ir.demisco.cfs.model.entity.FinancialDocumentItemCurrency;
import ir.demisco.cfs.model.entity.FinancialDocumentReference;
import ir.demisco.cfs.service.api.FinancialDocumentItemService;
import ir.demisco.cfs.service.api.FinancialDocumentSecurityService;
import ir.demisco.cfs.service.repository.FinancialDocumentItemCurrencyRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentReferenceRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class DefaultFinancialDocumentItem implements FinancialDocumentItemService {

    private final FinancialDocumentItemRepository financialDocumentItemRepository;
    private final FinancialDocumentRepository financialDocumentRepository;
    private final EntityManager entityManager;
    private final FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository;
    private final FinancialDocumentReferenceRepository financialDocumentReferenceRepository;
    private final FinancialDocumentSecurityService financialDocumentSecurityService;

    public DefaultFinancialDocumentItem(FinancialDocumentItemRepository financialDocumentItemRepository, FinancialDocumentRepository financialDocumentRepository, EntityManager entityManager, FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository, FinancialDocumentReferenceRepository financialDocumentReferenceRepository, FinancialDocumentSecurityService financialDocumentSecurityService) {
        this.financialDocumentItemRepository = financialDocumentItemRepository;
        this.financialDocumentRepository = financialDocumentRepository;
        this.entityManager = entityManager;
        this.documentItemCurrencyRepository = documentItemCurrencyRepository;
        this.financialDocumentReferenceRepository = financialDocumentReferenceRepository;
        this.financialDocumentSecurityService = financialDocumentSecurityService;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialDocumentItemList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        ResponseFinancialDocumentDto paramSearch = setParameter(filters);
        List<Sort.Order> sorts = new ArrayList<>();
        dataSourceRequest.getSort()
                .forEach((DataSourceRequest.SortDescriptor sortDescriptor) ->
                        {
                            if (sortDescriptor.getDir().equals("asc")) {
                                sorts.add(Sort.Order.asc(sortDescriptor.getField()));
                            } else {
                                sorts.add(Sort.Order.desc(sortDescriptor.getField()));
                            }
                        }
                );
        sorts.add(Sort.Order.asc("documentId"));
        Pageable pageable = PageRequest.of((dataSourceRequest.getSkip() / dataSourceRequest.getTake()), dataSourceRequest.getTake(), Sort.by(sorts));
        Page<Object[]> list = financialDocumentItemRepository.getFinancialDocumentItemList(paramSearch.getActivityCode(), paramSearch.getDepartmentId(),
                SecurityHelper.getCurrentUser().getUserId(),
                SecurityHelper.getCurrentUser().getOrganizationId(),
                paramSearch.getLedgerTypeId(), paramSearch.getStartDate(), paramSearch.getEndDate(),
                paramSearch.getPriceTypeId(), paramSearch.getFinancialNumberingTypeId(), paramSearch.getFromNumberId(), paramSearch.getFromNumber(),
                paramSearch.getToNumberId(), paramSearch.getToNumber(), paramSearch.getFinancialDocumentStatusDtoListId(),
                paramSearch.getDescription(), paramSearch.getFromAccountCode(),
                paramSearch.getToAccountCode(), paramSearch.getCentricAccount(), paramSearch.getCentricAccountId(),
                paramSearch.getCentricAccountType(), paramSearch.getCentricAccountTypeId(), paramSearch.getDocumentUser(), paramSearch.getDocumentUserId(),
                paramSearch.getPriceType(), paramSearch.getFromPrice(), paramSearch.getFromPriceAmount()
                , paramSearch.getTolerance(), paramSearch.getToPrice(), paramSearch.getToPriceAmount(),
                paramSearch.getFinancialDocumentType(), paramSearch.getFinancialDocumentTypeId(), pageable);
        List<FinancialDocumentItemDto> documentItemDtoList = list.stream().map(item ->
                FinancialDocumentItemDto.builder()
                        .id(((BigDecimal) item[0]).longValue())
                        .documentDate(item[1] == null ? null : ((Timestamp) item[1]).toLocalDateTime())
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
        dataSourceResult.setData(documentItemDtoList);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private ResponseFinancialDocumentDto setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        ResponseFinancialDocumentDto responseFinancialDocumentDto = new ResponseFinancialDocumentDto();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "activityCode":
                    checkActivityCodeSet(responseFinancialDocumentDto, item);
                    break;
                case "departmentId":
                    responseFinancialDocumentDto.setDepartmentId(Long.parseLong(item.getValue().toString()));
                    break;
                case "ledgerTypeId":
                    responseFinancialDocumentDto.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
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
                    checkFinancialDocumentTypeId(responseFinancialDocumentDto, item);
                    break;
                default:

                    break;
            }
        }

        return responseFinancialDocumentDto;
    }

    private void checkFinancialDocumentTypeId(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("financialDocumentType", "financialDocumentType");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFinancialDocumentTypeId(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setFinancialDocumentType("financialDocumentType");
        } else {
            map.put("financialDocumentType", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFinancialDocumentType(null);
            responseFinancialDocumentDto.setFinancialDocumentTypeId(0L);
        }
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
            responseFinancialDocumentDto.setPriceType("priceType");

        } else {
            map.put("priceType", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setPriceType(null);
            responseFinancialDocumentDto.setPriceTypeId(0L);
        }
    }

    private void checkFromNumberSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("fromNumber", "fromNumber");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromNumberId(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setFromNumber("fromNumber");
        } else {
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromNumber(null);
            responseFinancialDocumentDto.setFromNumberId(0L);
        }
    }

    private void checkFromAccountCodeSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromAccountCode(item.getValue().toString());
        } else {
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromAccountCode(null);
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
            responseFinancialDocumentDto.setToNumberId(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setToNumber("toNumber");
        } else {
            map.put("toNumber", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToNumber(null);
            responseFinancialDocumentDto.setToNumberId(0L);
        }
    }

    private void checkToAccountCodeSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToAccountCode(item.getValue().toString());
        } else {
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToAccountCode(null);
        }
    }

    private void checkCentricAccountIdSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("centricAccount", "centricAccount");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setCentricAccountId(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setCentricAccount("centricAccount");
        } else {
            map.put("centricAccount", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setCentricAccount(null);
            responseFinancialDocumentDto.setCentricAccountId(0L);
        }
    }

    private void checkCentricAccountTypeIdSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("centricAccountType", "centricAccountType");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setCentricAccountTypeId(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setCentricAccountType("centricAccountType");
        } else {
            map.put("centricAccountType", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setCentricAccountType(null);
            responseFinancialDocumentDto.setCentricAccountTypeId(0L);
        }
    }

    private void checkDocumentUserIdSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("documentUser", "documentUser");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setDocumentUserId(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setDocumentUser("documentUser");
        } else {
            map.put("documentUser", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setDocumentUser(null);
            responseFinancialDocumentDto.setDocumentUserId(0L);
        }
    }

    private void checkFromPriceSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("fromPrice", "fromPrice");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromPriceAmount(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setFromPrice("fromPrice");
        } else {
            map.put("fromPrice", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromPrice(null);
            responseFinancialDocumentDto.setFromPriceAmount(0L);
        }
    }

    private void checkToPriceSet(ResponseFinancialDocumentDto responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("toPrice", "toPrice");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToPriceAmount(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setToPrice("toPrice");
        } else {
            map.put("toPrice", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToPrice(null);
            responseFinancialDocumentDto.setToPriceAmount(0L);
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
        } catch (ParseException var4) {
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

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean deleteFinancialDocumentItemById(Long financialDocumentItemId) {
        Long documentId = financialDocumentRepository.getDocumentByIdFinancialDocumentItemId(financialDocumentItemId);
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(documentId);
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);
        Long count = financialDocumentRepository.findFinancialDocumentByDocumentItemId(financialDocumentItemId);
        if (count != null) {
            throw new RuleException("سند در وضعیت قطعی می باشد");
        }
        Long countDelete = financialDocumentRepository.findFinancialDocumentByDocumentItemIdDelete(financialDocumentItemId);
        if (countDelete != null) {
            throw new RuleException("این سند دارای شماره دائمی است و امکان حذف ردیف وجود ندارد");
        }
        entityManager.createNativeQuery(" update fndc.financial_document T" +
                "   set   T.FINANCIAL_DOCUMENT_STATUS_ID = 1 " +
                "   WHERE ID = (SELECT DI.FINANCIAL_DOCUMENT_ID" +
                "               FROM FNDC.FINANCIAL_DOCUMENT_ITEM DI" +
                "              WHERE DI.ID = :financialDocumentItemId) " +
                "   AND T.FINANCIAL_DOCUMENT_STATUS_ID = 2 ").setParameter("financialDocumentItemId", financialDocumentItemId).executeUpdate();
        financialDocumentItemRepository.findById(financialDocumentItemId).orElseThrow(() -> new RuleException("fin.financialDocument.notExistFinancialDocumentItem"));
        List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.getFinancialDocumentItemByDocumentId(financialDocumentItemId);
        deleteDocumentItem(financialDocumentItemList);
        financialDocumentItemRepository.deleteById(financialDocumentItemId);
        return true;
    }

    public void deleteDocumentItem(List<FinancialDocumentItem> financialDocumentItemList) {
        financialDocumentItemList.forEach((FinancialDocumentItem documentItem) -> {
            documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItem.getId())
                    .forEach((FinancialDocumentItemCurrency financialDocumentItem) -> {
                        documentItemCurrencyRepository.deleteById(financialDocumentItem.getId());
                    });

            financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItem.getId())
                    .forEach((FinancialDocumentReference documentReference) -> {
                        financialDocumentReferenceRepository.deleteById(documentReference.getId());
                    });

        });

    }
}
