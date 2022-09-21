package ir.demisco.cfs.service.impl;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import ir.demisco.cfs.model.dto.request.ControlFinancialAccountNatureTypeInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentFilterRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentSecurityInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodLedgerStatusRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.request.GetDocFromoldSystemInputRequest;
import ir.demisco.cfs.model.dto.response.FinancialCentricAccountDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentAccountDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentChangeDescriptionDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentErrorDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentFilterResponse;
import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberDto;
import ir.demisco.cfs.model.dto.response.FinancialNumberingRecordDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodStatusResponse;
import ir.demisco.cfs.model.dto.response.RequestDocumentStructureDto;
import ir.demisco.cfs.model.dto.response.ResponseDocumentStructureDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentSetStatusDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentStatusDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentStructureDto;
import ir.demisco.cfs.model.entity.CentricAccount;
import ir.demisco.cfs.model.entity.FinancialAccount;
import ir.demisco.cfs.model.entity.FinancialDocument;
import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import ir.demisco.cfs.model.entity.FinancialDocumentItemCurrency;
import ir.demisco.cfs.model.entity.FinancialDocumentNumber;
import ir.demisco.cfs.model.entity.FinancialDocumentReference;
import ir.demisco.cfs.model.entity.FinancialDocumentStatus;
import ir.demisco.cfs.model.entity.FinancialNumberingFormat;
import ir.demisco.cfs.model.entity.NumberingFormatSerial;
import ir.demisco.cfs.service.api.FinancialDocumentSecurityService;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.api.FinancialSecurityService;
import ir.demisco.cfs.service.repository.CentricAccountRepository;
import ir.demisco.cfs.service.repository.FinancialAccountRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentItemCurrencyRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentNumberRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentReferenceRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentStatusRepository;
import ir.demisco.cfs.service.repository.FinancialNumberingFormatRepository;
import ir.demisco.cfs.service.repository.FinancialNumberingTypeRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cfs.service.repository.NumberingFormatSerialRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static ir.demisco.cloud.core.middle.service.system.impl.MessageBundleImpl.message;

@Service
public class DefaultFinancialDocument implements FinancialDocumentService {


    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialDocumentStatusRepository documentStatusRepository;
    private final FinancialDocumentItemRepository financialDocumentItemRepository;
    private final FinancialDocumentReferenceRepository financialDocumentReferenceRepository;
    private final FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository;
    private final FinancialAccountRepository financialAccountRepository;
    private final EntityManager entityManager;
    private final CentricAccountRepository centricAccountRepository;
    private final FinancialNumberingFormatRepository financialNumberingFormatRepository;
    private final NumberingFormatSerialRepository numberingFormatSerialRepository;
    private final FinancialDocumentNumberRepository financialDocumentNumberRepository;
    private final FinancialNumberingTypeRepository financialNumberingTypeRepository;
    private final FinancialDocumentItemCurrencyRepository financialDocumentItemCurrencyRepository;
    private final FinancialPeriodService financialPeriodService;
    private final FinancialDocumentSecurityService financialDocumentSecurityService;
    private final FinancialPeriodRepository financialPeriodRepository;
    private final FinancialSecurityService financialSecurityService;

    public DefaultFinancialDocument(FinancialDocumentRepository financialDocumentRepository, FinancialDocumentStatusRepository documentStatusRepository,
                                    FinancialDocumentItemRepository financialDocumentItemRepository,
                                    FinancialDocumentReferenceRepository financialDocumentReferenceRepository,
                                    FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository, FinancialAccountRepository financialAccountRepository,
                                    EntityManager entityManager, CentricAccountRepository centricAccountRepository, FinancialNumberingFormatRepository financialNumberingFormatRepository, NumberingFormatSerialRepository numberingFormatSerialRepository, FinancialDocumentNumberRepository financialDocumentNumberRepository, FinancialNumberingTypeRepository financialNumberingTypeRepository, FinancialDocumentItemCurrencyRepository financialDocumentItemCurrencyRepository, FinancialPeriodService financialPeriodService, FinancialDocumentSecurityService financialDocumentSecurityService, FinancialPeriodRepository financialPeriodRepository, FinancialSecurityService financialSecurityService) {
        this.financialDocumentRepository = financialDocumentRepository;
        this.documentStatusRepository = documentStatusRepository;
        this.financialDocumentItemRepository = financialDocumentItemRepository;
        this.financialDocumentReferenceRepository = financialDocumentReferenceRepository;
        this.documentItemCurrencyRepository = documentItemCurrencyRepository;
        this.financialAccountRepository = financialAccountRepository;
        this.entityManager = entityManager;
        this.centricAccountRepository = centricAccountRepository;
        this.financialNumberingFormatRepository = financialNumberingFormatRepository;
        this.numberingFormatSerialRepository = numberingFormatSerialRepository;
        this.financialDocumentNumberRepository = financialDocumentNumberRepository;
        this.financialNumberingTypeRepository = financialNumberingTypeRepository;
        this.financialDocumentItemCurrencyRepository = financialDocumentItemCurrencyRepository;
        this.financialPeriodService = financialPeriodService;
        this.financialDocumentSecurityService = financialDocumentSecurityService;
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialSecurityService = financialSecurityService;
    }


    @Override
    @Transactional
    public DataSourceResult getFinancialDocumentList(DataSourceRequest dataSourceRequest) {
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
        Page<Object[]> list = financialDocumentRepository.getFinancialDocumentList(paramSearch.getActivityCode(), paramSearch.getDepartmentId(),
                SecurityHelper.getCurrentUser().getUserId(), SecurityHelper.getCurrentUser().getOrganizationId(), paramSearch.getLedgerTypeId()
                , paramSearch.getStartDate(), paramSearch.getEndDate(), paramSearch.getPriceTypeId(), paramSearch.getFinancialNumberingTypeId(), paramSearch.getFromNumberId(), paramSearch.getFromNumber(),
                paramSearch.getToNumberId(), paramSearch.getToNumber(), paramSearch.getFinancialDocumentStatusDtoListId(), paramSearch.getDescription(),
                paramSearch.getFromAccountCode(), paramSearch.getFromAccount(),
                paramSearch.getToAccountCode(), paramSearch.getToAccount(), paramSearch.getCentricAccount(), paramSearch.getCentricAccountId(),
                paramSearch.getCentricAccountType(), paramSearch.getCentricAccountTypeId(), paramSearch.getDocumentUser(), paramSearch.getDocumentUserId(),
                paramSearch.getPriceType(), paramSearch.getFromPrice(),
                paramSearch.getFromPriceAmount(), paramSearch.getToPrice(),
                paramSearch.getToPriceAmount(), paramSearch.getTolerance(),
                paramSearch.getFinancialDocumentType(), paramSearch.getFinancialDocumentTypeId(), pageable);
        List<FinancialDocumentDto> documentDtoList = list.stream().map(item ->
                FinancialDocumentDto.builder()
                        .id(((BigDecimal) item[0]).longValue())
                        .documentDate((Date) item[1])
                        .description(item[2].toString())
                        .documentNumber(item[3].toString())
                        .financialDocumentTypeId(Long.parseLong(item[4].toString()))
                        .financialDocumentTypeDescription(item[5].toString())
                        .fullDescription(item[6].toString())
                        .debitAmount(Long.parseLong(String.format("%.0f", Double.parseDouble(item[7].toString()))))
                        .creditAmount(Long.parseLong(String.format("%.0f", Double.parseDouble(item[8].toString()))))
                        .userId(Long.parseLong(item[9].toString()))
                        .userName(item[10].toString())
                        .financialDocumentStatusId(Long.parseLong(item[11].toString()))
                        .financialDocumentStatusName(item[12].toString())
                        .financialDocumentStatusCode(item[13].toString())
                        .departmentId(Long.parseLong(item[14].toString()))
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(documentDtoList.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setData(documentDtoList);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;

    }

    private ResponseFinancialDocumentDto setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        ResponseFinancialDocumentDto responseFinancialDocumentDto = new ResponseFinancialDocumentDto();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "activityCode":
                    responseFinancialDocumentDto.setActivityCode(item.getValue().toString());
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
                case "financialNumberingType.id":
                    responseFinancialDocumentDto.setFinancialNumberingTypeId(Long.parseLong(item.getValue().toString()));
                    break;
                case "priceType.id":
                    checkPriceTypeIdSet(responseFinancialDocumentDto, item);
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
                    checkToPrice(responseFinancialDocumentDto, item);
                    break;

                case "tolerance":
                    checkTolerance(responseFinancialDocumentDto, item);
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

    private void checkFinancialDocumentTypeId(ResponseFinancialDocumentDto
                                                      responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("financialDocumentType", "financialDocumentType");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFinancialDocumentTypeId(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setFinancialDocumentType("financialDocumentType");
        } else {
            map.put("financialDocumentType", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFinancialDocumentTypeId(0L);
            responseFinancialDocumentDto.setFinancialDocumentType(null);
        }
    }

    private void checkTolerance(ResponseFinancialDocumentDto
                                        responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            responseFinancialDocumentDto.setTolerance(Double.parseDouble(item.getValue().toString()));
        } else {
            responseFinancialDocumentDto.setTolerance(0D);
        }
    }

    private void checkToPrice(ResponseFinancialDocumentDto
                                      responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("toPrice", "toPrice");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToPriceAmount(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setToPrice("toPrice");
        } else {
            map.put("toPrice", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToPriceAmount(0L);
            responseFinancialDocumentDto.setToPrice(null);
        }
    }

    private void checkPriceTypeIdSet(ResponseFinancialDocumentDto
                                             responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
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

    private void checkFromNumberSet(ResponseFinancialDocumentDto
                                            responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("fromNumber", "fromNumber");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromNumberId(Long.parseLong(item.getValue().toString()));
            responseFinancialDocumentDto.setFromNumber("fromNumber");
        } else {
            map.put("fromNumber", null);
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromNumber(null);
            responseFinancialDocumentDto.setFromNumberId(0L);
        }
    }

    private void checkToNumberSet(ResponseFinancialDocumentDto
                                          responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
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

    private void checkDescriptionSet(ResponseFinancialDocumentDto
                                             responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            responseFinancialDocumentDto.setDescription(item.getValue().toString());
        } else {
            responseFinancialDocumentDto.setDescription(null);
        }
    }

    private void checkFromAccountCodeSet(ResponseFinancialDocumentDto
                                                 responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("fromAccount", "fromAccount");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromAccountCode(item.getValue().toString());
            responseFinancialDocumentDto.setFromAccount("fromAccount");
        } else {
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setFromAccountCode(null);
            responseFinancialDocumentDto.setFromAccount(null);
        }
    }

    private void checkToAccountCodeSet(ResponseFinancialDocumentDto
                                               responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("toAccount", "toAccount");
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToAccountCode(item.getValue().toString());
            responseFinancialDocumentDto.setToAccount("toAccount");

        } else {
            responseFinancialDocumentDto.setParamMap(map);
            responseFinancialDocumentDto.setToAccountCode(null);
            responseFinancialDocumentDto.setToAccount(null);
        }
    }

    private void checkCentricAccountIdSet(ResponseFinancialDocumentDto
                                                  responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
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

    private void checkCentricAccountTypeIdSet(ResponseFinancialDocumentDto
                                                      responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
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

    private void checkDocumentUserIdSet(ResponseFinancialDocumentDto
                                                responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
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

    private void checkFromPriceSet(ResponseFinancialDocumentDto
                                           responseFinancialDocumentDto, DataSourceRequest.FilterDescriptor item) {
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
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<ResponseFinancialDocumentSetStatusDto> changeStatus(ResponseFinancialDocumentStatusDto
                                                                                      responseFinancialDocumentStatusDto) {
        String activityCode = "FNDC_DOCUMENT_CONF";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(responseFinancialDocumentStatusDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        ResponseFinancialDocumentSetStatusDto responseFinancialDocumentSetStatusDto = new ResponseFinancialDocumentSetStatusDto();
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(responseFinancialDocumentStatusDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }
        FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest = new FinancialPeriodLedgerStatusRequest();
        financialPeriodLedgerStatusRequest.setFinancialDocumentId(responseFinancialDocumentStatusDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatusResponse = getFinancialPeriodStatus(financialPeriodLedgerStatusRequest);
        if (financialPeriodStatusResponse.getPeriodStatus() == 0L || financialPeriodStatusResponse.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه مربوط به دفتر مالی میبایست در وضعیت باز باشند");
        }

        FinancialDocument financialDocument = financialDocumentRepository.findById(responseFinancialDocumentStatusDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialDocumentStatus financialDocumentStatus =
                documentStatusRepository.findFinancialDocumentStatusByCode(responseFinancialDocumentStatusDto.getFinancialDocumentStatusCode());
        if (responseFinancialDocumentStatusDto.getFinancialDocumentStatusCode().equals("20") ||
                responseFinancialDocumentStatusDto.getFinancialDocumentStatusCode().equals("30")) {
            List<FinancialDocumentErrorDto> financialDocumentErrorDtoList = validationSetStatus(financialDocument);
            if (financialDocumentErrorDtoList.isEmpty()) {
                financialDocument.setFinancialDocumentStatus(financialDocumentStatus);
                financialDocumentRepository.save(financialDocument);
                responseFinancialDocumentSetStatusDto = convertFinancialDocumentToDto(financialDocument);
                return ResponseEntity.ok(responseFinancialDocumentSetStatusDto);
            } else {
                responseFinancialDocumentSetStatusDto.setFinancialDocumentErrorDtoList(financialDocumentErrorDtoList);
                responseFinancialDocumentSetStatusDto.setErrorFoundFlag(true);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseFinancialDocumentSetStatusDto);
            }
        } else {

            financialDocument.setFinancialDocumentStatus(financialDocumentStatus);
            financialDocumentRepository.save(financialDocument);
            responseFinancialDocumentSetStatusDto = convertFinancialDocumentToDto(financialDocument);
            return ResponseEntity.ok(responseFinancialDocumentSetStatusDto);
        }

    }

    private List<FinancialDocumentErrorDto> validationSetStatus(FinancialDocument financialDocument) {
        List<FinancialDocumentErrorDto> financialDocumentErrorDtoList = checkValidationSetStatus(financialDocument);
        Long cost = financialDocumentItemRepository.getCostDocument(financialDocument.getId());
        if (cost == null) {
            FinancialDocumentErrorDto financialDocumentCost = new FinancialDocumentErrorDto();
            financialDocumentCost.setFinancialDocumentId(financialDocument.getId());
            financialDocumentCost.setMessage("مجموع مبالغ بستانکار و بدهکار در ردیف های سند بالانس نیست.");
            financialDocumentErrorDtoList.add(financialDocumentCost);
        }

        FinancialDocument documentPeriod = financialDocumentRepository.getActivePeriodInDocument(financialDocument.getId());
        if (documentPeriod == null) {
            FinancialDocumentErrorDto financialDocumentCost = new FinancialDocumentErrorDto();
            financialDocumentCost.setFinancialDocumentId(financialDocument.getId());
            financialDocumentCost.setMessage("وضعیت دوره مالی مربوط به سند بسته است.");
            financialDocumentErrorDtoList.add(financialDocumentCost);
        }

        ControlFinancialAccountNatureTypeInputRequest controlFinancialAccountNatureTypeInputRequest = new ControlFinancialAccountNatureTypeInputRequest();
        controlFinancialAccountNatureTypeInputRequest.setFinancialDocumentId(financialDocument.getId());
        Long financialDocumentItemAccount = financialDocumentItemRepository.getFinancialAccount(financialDocument.getId());
        if (financialDocumentItemAccount == null) {
            FinancialDocumentErrorDto financialAccount = new FinancialDocumentErrorDto();
            financialAccount.setFinancialDocumentId(financialDocument.getId());
            financialAccount.setMessage(" حساب انتخاب شده  روی یک / چند ردیف از سند ، آخرین سطح حساب نمی باشد");
            financialDocumentErrorDtoList.add(financialAccount);
        }

        Long financialDocumentItemCostHarmony = financialDocumentItemRepository.costHarmony(financialDocument.getId());
        if (financialDocumentItemCostHarmony != null) {
            FinancialDocumentErrorDto costHarmony = new FinancialDocumentErrorDto();
            costHarmony.setFinancialDocumentId(financialDocument.getId());
            costHarmony.setMessage(" مبالغ بدهکار یا بستانکار ردیف یا ردیفها با مبالغ بدهکار یا بستانکار ارزی همخوانی ندارد.");
            financialDocumentErrorDtoList.add(costHarmony);
        }

        Long financialDocumentItemReferenceCode = financialDocumentItemRepository.referenceCode(financialDocument.getId());
        if (financialDocumentItemReferenceCode != null) {
            FinancialDocumentErrorDto referenceCode = new FinancialDocumentErrorDto();
            referenceCode.setFinancialDocumentId(financialDocument.getId());
            referenceCode.setMessage(" کدهای تمرکز ثبت شده باید با کد تمرکز مرجع خود همخوانی داشته باشد");
            financialDocumentErrorDtoList.add(referenceCode);
        }

        if (financialDocument.getDescription() == null) {
            FinancialDocumentErrorDto documentDescription = new FinancialDocumentErrorDto();
            documentDescription.setFinancialDocumentId(financialDocument.getId());
            documentDescription.setMessage("سند بدون شرح است.");
            financialDocumentErrorDtoList.add(documentDescription);
        }

        return financialDocumentErrorDtoList;
    }

    List<FinancialDocumentErrorDto> check1(FinancialDocumentItem documentItem, FinancialDocument
            financialDocument, List<FinancialDocumentErrorDto> financialDocumentErrorDtoList) {
        Double creditAmount = documentItem.getCreditAmount() % 1;
        Double debitAmount = documentItem.getDebitAmount() % 1;
        if ((documentItem.getCreditAmount() == 0) && (documentItem.getDebitAmount() == 0)) {
            FinancialDocumentErrorDto checkZeroAmount = new FinancialDocumentErrorDto();
            checkZeroAmount.setFinancialDocumentId(financialDocument.getId());
            checkZeroAmount.setFinancialDocumentItemId(documentItem.getId());
            checkZeroAmount.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
            checkZeroAmount.setMessage("در ردیف  مبلغ بستانکار و بدهکار صفر می باشد");
            financialDocumentErrorDtoList.add(checkZeroAmount);
        }

        if ((documentItem.getCreditAmount() != 0) && (documentItem.getDebitAmount() != 0)) {
            FinancialDocumentErrorDto checkCostAmount = new FinancialDocumentErrorDto();
            checkCostAmount.setFinancialDocumentId(financialDocument.getId());
            checkCostAmount.setFinancialDocumentItemId(documentItem.getId());
            checkCostAmount.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
            checkCostAmount.setMessage("در ردیف  مبلغ بستانکار و بدهکار هر دو دارای مقدار می باشد.");
            financialDocumentErrorDtoList.add(checkCostAmount);
        }

        if (documentItem.getDescription() == null) {
            FinancialDocumentErrorDto documentItemDescription = new FinancialDocumentErrorDto();
            documentItemDescription.setFinancialDocumentId(financialDocument.getId());
            documentItemDescription.setFinancialDocumentItemId(documentItem.getId());
            documentItemDescription.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
            documentItemDescription.setMessage("در سند ردیف بدون شرح وجود دارد.");
            financialDocumentErrorDtoList.add(documentItemDescription);
        }

        if ((creditAmount != 0) || (debitAmount != 0)) {
            FinancialDocumentErrorDto itemAmount = new FinancialDocumentErrorDto();
            itemAmount.setFinancialDocumentId(financialDocument.getId());
            itemAmount.setFinancialDocumentItemId(documentItem.getId());
            itemAmount.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
            itemAmount.setMessage("مبلغ در ردیف اعشاری است.");
            financialDocumentErrorDtoList.add(itemAmount);
        }

        if ((documentItem.getCreditAmount() < 0) || (documentItem.getDebitAmount() < 0)) {
            FinancialDocumentErrorDto documentItemAmount = new FinancialDocumentErrorDto();
            documentItemAmount.setFinancialDocumentId(financialDocument.getId());
            documentItemAmount.setFinancialDocumentItemId(documentItem.getId());
            documentItemAmount.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
            documentItemAmount.setMessage("در ردیف سند مبلغ بستانکار یا بدهکار  منفی می باشد.");
            financialDocumentErrorDtoList.add(documentItemAmount);
        }

        Long documentReference = financialDocumentReferenceRepository.getDocumentReference(documentItem.getId());
        if (documentReference != null) {
            FinancialDocumentErrorDto financialDocumentReference = new FinancialDocumentErrorDto();
            financialDocumentReference.setFinancialDocumentId(financialDocument.getId());
            financialDocumentReference.setFinancialDocumentItemId(documentItem.getId());
            financialDocumentReference.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
            financialDocumentReference.setMessage("تاریخ و شرح در مدارک مرجع پر نشده.");
            financialDocumentErrorDtoList.add(financialDocumentReference);
        }

        Long financialDocumentItemInfoCurrency = financialDocumentItemRepository.getInfoCurrency(documentItem.getId());
        if (financialDocumentItemInfoCurrency != null) {
            FinancialDocumentErrorDto infoCurrency = new FinancialDocumentErrorDto();
            infoCurrency.setFinancialDocumentId(financialDocument.getId());
            infoCurrency.setFinancialDocumentItemId(documentItem.getId());
            infoCurrency.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
            infoCurrency.setMessage("لطفا اطلاعات ارزی را برای ردیفهای ارزی ، به صورت کامل وارد نمایید");
            financialDocumentErrorDtoList.add(infoCurrency);
        }

        Long financialDocumentEqualCurrency = financialDocumentItemRepository.equalCurrency(documentItem.getId());
        if (financialDocumentEqualCurrency != null) {
            FinancialDocumentErrorDto equalCurrency = new FinancialDocumentErrorDto();
            equalCurrency.setFinancialDocumentId(financialDocument.getId());
            equalCurrency.setFinancialDocumentItemId(documentItem.getId());
            equalCurrency.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
            equalCurrency.setMessage("نوع ارز انتخاب شده در ردیفهای ارزی سند ، با نوع ارز یکسان نمیباشد");
            financialDocumentErrorDtoList.add(equalCurrency);
        }
        return financialDocumentErrorDtoList;
    }

    private List<FinancialDocumentErrorDto> checkValidationSetStatus(FinancialDocument financialDocument) {
        final List<FinancialDocumentErrorDto>[] financialDocumentErrorDtoList = new List[]{new ArrayList<>()};
        List<FinancialDocumentItem> documentItemList = financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocument.getId());
        if (documentItemList.isEmpty()) {
            FinancialDocumentErrorDto documentItem = new FinancialDocumentErrorDto();
            documentItem.setFinancialDocumentId(financialDocument.getId());
            documentItem.setMessage("سند بدون ردیف است.");
            financialDocumentErrorDtoList[0].add(documentItem);
        } else {
            documentItemList.forEach((FinancialDocumentItem documentItem) -> {

                financialDocumentErrorDtoList[0] = check1(documentItem, financialDocument, financialDocumentErrorDtoList[0]);


            });
        }
        return financialDocumentErrorDtoList[0];
    }

    private ResponseFinancialDocumentSetStatusDto convertFinancialDocumentToDto(FinancialDocument financialDocument) {
        List<Object[]> objects = financialDocumentItemRepository.findParamByDocumentId(financialDocument.getId());
        return ResponseFinancialDocumentSetStatusDto.builder()
                .id(financialDocument.getId())
                .documentDate(financialDocument.getDocumentDate())
                .documentNumber(financialDocument.getDocumentNumber())
                .financialDocumentTypeId(financialDocument.getFinancialDocumentType().getId())
                .financialDocumentTypeDescription(financialDocument.getFinancialDocumentType().getDescription())
                .userId(financialDocument.getCreator().getId())
                .userName(financialDocument.getCreator().getUsername())
                .description(financialDocument.getDescription())
                .debitAmount(((BigDecimal) objects.get(0)[0]).doubleValue())
                .creditAmount(((BigDecimal) objects.get(0)[1]).doubleValue())
                .fullDescription(objects.get(0)[2] == null ? null : objects.get(0)[2].toString())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String creatDocumentNumber(FinancialDocumentNumberDto financialDocumentNumberDto) {
        List<FinancialNumberingRecordDto> financialNumberingRecordDtoList = new ArrayList<>();
        AtomicReference<String> documentNumber = new AtomicReference<>("");
        List<Object[]> list = financialDocumentRepository.getSerialNumber(SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentNumberDto.getFinancialDocumentId(), financialDocumentNumberDto.getNumberingType());
        if (!list.isEmpty()) {
            list.forEach((Object[] objects) -> {
                FinancialNumberingFormat financialNumberingFormat =
                        financialNumberingFormatRepository.findById(Long.parseLong(objects[0].toString())).orElseThrow(() -> new RuleException("fin.financialDocument.notExistFinancialNumberingFormat"));
                NumberingFormatSerial searchNumberingFormatSerial = numberingFormatSerialRepository.findByNumberingFormatAndDeletedDate(financialNumberingFormat.getId(), objects[2].toString(), Long.parseLong(objects[3].toString()));
                if (searchNumberingFormatSerial == null) {
                    NumberingFormatSerial numberingFormatSerial = new NumberingFormatSerial();
                    numberingFormatSerial.setFinancialNumberingFormat(financialNumberingFormat);
                    numberingFormatSerial.setLastSerial(Long.parseLong(objects[1].toString()));
                    numberingFormatSerial.setSerialReseter(objects[2].toString());
                    numberingFormatSerial.setSerialLength(Long.parseLong(objects[3].toString()));
                    numberingFormatSerialRepository.save(numberingFormatSerial);
                } else {
                    throw new RuleException("fin.financialDocument.serialNumber");
                }

            });
        }

        List<Long> numberingFormatSerialList =
                numberingFormatSerialRepository.findNumberingFormatSerialByParam(financialDocumentNumberDto.getNumberingType(), SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentNumberDto.getFinancialDocumentId());
        numberingFormatSerialList.forEach((Long aLong) -> {
            NumberingFormatSerial numberingFormatSerial = numberingFormatSerialRepository.getById(aLong);
            numberingFormatSerial.setLastSerial(numberingFormatSerial.getLastSerial() + 1);
            numberingFormatSerialRepository.save(numberingFormatSerial);
        });
        List<Object[]> listDocumentNumber =
                financialDocumentRepository.findDocumentNumber(SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentNumberDto.getFinancialDocumentId(), financialDocumentNumberDto.getNumberingType());
        listDocumentNumber.forEach((Object[] documentNumberObject) -> {
            FinancialDocumentNumber financialDocumentNumber = new FinancialDocumentNumber();
            FinancialNumberingRecordDto financialNumberingRecordDto = new FinancialNumberingRecordDto();
            financialNumberingRecordDto.setFinancialDocumentId(financialDocumentNumberDto.getFinancialDocumentId());
            financialNumberingRecordDto.setNumberingTypeId(Long.parseLong(documentNumberObject[0].toString()));
            financialNumberingRecordDto.setFinancialDocumentNumber(documentNumberObject[1].toString());
            financialDocumentNumber.setFinancialDocument(financialDocumentRepository.getOne(financialNumberingRecordDto.getFinancialDocumentId()));
            financialDocumentNumber.setFinancialNumberingType(
                    financialNumberingTypeRepository.getOne(financialNumberingRecordDto.getNumberingTypeId()));
            financialDocumentNumber.setDocumentNumber(financialNumberingRecordDto.getFinancialDocumentNumber());
            financialDocumentNumberRepository.save(financialDocumentNumber);
            financialNumberingRecordDtoList.add(financialNumberingRecordDto);
        });

        financialNumberingRecordDtoList.forEach((FinancialNumberingRecordDto record1) -> {
            if (record1.getNumberingTypeId() == 1) {
                documentNumber.set(listDocumentNumber.get(1)[1].toString());
            }
            if (record1.getNumberingTypeId() == 3) {
                documentNumber.set(record1.getFinancialDocumentNumber());
            }
        });
        return documentNumber.get();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String creatDocumentNumberUpdate(FinancialDocumentNumberDto financialDocumentNumberDto) {
        List<FinancialNumberingRecordDto> financialNumberingRecordDtoList = new ArrayList<>();
        AtomicReference<String> documentNumber = new AtomicReference<>("");
        List<Object[]> list = financialDocumentRepository.getSerialNumber(SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentNumberDto.getFinancialDocumentId(), financialDocumentNumberDto.getNumberingType());
        if (!list.isEmpty()) {
            list.forEach((Object[] objects) -> {
                FinancialNumberingFormat financialNumberingFormat =
                        financialNumberingFormatRepository.findById(Long.parseLong(objects[0].toString())).orElseThrow(() -> new RuleException("fin.financialDocument.notExistFinancialNumberingFormat"));
                NumberingFormatSerial searchNumberingFormatSerial = numberingFormatSerialRepository.findByNumberingFormatAndDeletedDate(financialNumberingFormat.getId(), objects[2].toString(), Long.parseLong(objects[3].toString()));
                if (searchNumberingFormatSerial == null) {
                    NumberingFormatSerial numberingFormatSerial = new NumberingFormatSerial();
                    numberingFormatSerial.setFinancialNumberingFormat(financialNumberingFormat);
                    numberingFormatSerial.setLastSerial(Long.parseLong(objects[1].toString()));
                    numberingFormatSerial.setSerialReseter(objects[2].toString());
                    numberingFormatSerial.setSerialLength(Long.parseLong(objects[3].toString()));
                    numberingFormatSerialRepository.save(numberingFormatSerial);
                } else {
                    throw new RuleException("fin.financialDocument.serialNumber");
                }

            });
        }
        List<Long> numberingFormatSerialList =
                numberingFormatSerialRepository.findNumberingFormatSerialByParam(financialDocumentNumberDto.getNumberingType(), SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentNumberDto.getFinancialDocumentId());
        numberingFormatSerialList.forEach((Long aLong) -> {
            NumberingFormatSerial numberingFormatSerial = numberingFormatSerialRepository.getById(aLong);
            numberingFormatSerial.setLastSerial(numberingFormatSerial.getLastSerial() + 1);
            numberingFormatSerialRepository.save(numberingFormatSerial);
        });
        List<Object[]> listDocumentNumber =
                financialDocumentRepository.findDocumentNumber(SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentNumberDto.getFinancialDocumentId(), financialDocumentNumberDto.getNumberingType());
        List<FinancialDocumentNumber> financialDocumentNumberList =
                financialDocumentNumberRepository.findByFinancialDocumentIdList(financialDocumentNumberDto.getFinancialDocumentId(), financialDocumentNumberDto.getNumberingType());
        financialDocumentNumberList.forEach(financialDocumentNumberRepository::delete);
        listDocumentNumber.forEach((Object[] documentNumberObject) -> {
            FinancialDocumentNumber financialDocumentNumber = new FinancialDocumentNumber();
            FinancialNumberingRecordDto financialNumberingRecordDto = new FinancialNumberingRecordDto();
            financialNumberingRecordDto.setFinancialDocumentId(financialDocumentNumberDto.getFinancialDocumentId());
            financialNumberingRecordDto.setNumberingTypeId(Long.parseLong(documentNumberObject[0].toString()));
            financialNumberingRecordDto.setFinancialDocumentNumber(documentNumberObject[1].toString());
            financialDocumentNumber.setFinancialDocument(financialDocumentRepository.getOne(financialNumberingRecordDto.getFinancialDocumentId()));
            financialDocumentNumber.setFinancialNumberingType(
                    financialNumberingTypeRepository.getOne(financialNumberingRecordDto.getNumberingTypeId()));
            financialDocumentNumber.setDocumentNumber(financialNumberingRecordDto.getFinancialDocumentNumber());
            financialDocumentNumberRepository.save(financialDocumentNumber);
            financialNumberingRecordDtoList.add(financialNumberingRecordDto);
        });

        financialNumberingRecordDtoList.forEach((FinancialNumberingRecordDto record1) -> {
            if (record1.getNumberingTypeId() == 2) {
                documentNumber.set(record1.getFinancialDocumentNumber());
            }
            if (record1.getNumberingTypeId() == 3) {
                documentNumber.set(record1.getFinancialDocumentNumber());
            }
        });
        return documentNumber.get();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String changeDescription(FinancialDocumentChangeDescriptionDto financialDocumentDto) {
        FinancialDocument financialDocument = financialDocumentRepository.getActiveDocumentById(financialDocumentDto.getId());
        if (financialDocument == null) {
            throw new RuleException("fin.financialDocument.notExistDocument");
        }
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialDocumentDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.getDocumentDescription(financialDocumentDto.getFinancialDocumentItemIdList(), financialDocumentDto.getOldDescription());
        if (financialDocumentItemList.isEmpty()) {
            throw new RuleException("fin.financialDocument.notFoundRecordWithParam");
        }
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(financialDocumentDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }

        FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest = new FinancialPeriodLedgerStatusRequest();
        financialPeriodLedgerStatusRequest.setFinancialDocumentId(financialDocumentDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatusResponse = getFinancialPeriodStatus(financialPeriodLedgerStatusRequest);
        if (financialPeriodStatusResponse.getPeriodStatus() == 0L || financialPeriodStatusResponse.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه مربوط به دفتر مالی میبایست در وضعیت باز باشند");
        }


        entityManager.createNativeQuery(" update fndc.financial_document_item " +
                "   set description = replace(description,:description,:newDescription) " +
                "   where id in (:FinancialDocumentItemIdList) " +
                "   And description like '%'|| :description ||'%'").setParameter("description", financialDocumentDto.getOldDescription())
                .setParameter("newDescription", financialDocumentDto.getNewDescription())
                .setParameter("FinancialDocumentItemIdList", financialDocumentDto.getFinancialDocumentItemIdList()).executeUpdate();

        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
        financialDocumentRepository.save(financialDocument);
        return "عملیات با موفقیت انجام شد.";
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteFinancialDocumentById(Long financialDocumentId) {
        FinancialDocument document = financialDocumentRepository.findById(financialDocumentId).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        String activityCode = "FNDC_DOCUMENT_DELETE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialDocumentId);
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);
        Long financialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(document.getId());
        FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest = new FinancialPeriodLedgerStatusRequest();
        financialPeriodLedgerStatusRequest.setFinancialDocumentId(financialDocumentId);
        FinancialPeriodStatusResponse financialPeriodStatusResponse = getFinancialPeriodStatus(financialPeriodLedgerStatusRequest);
        if (financialPeriodStatusResponse.getPeriodStatus() == 0L || financialPeriodStatusResponse.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه مربوط به دفتر مالی میبایست در وضعیت باز باشند");
        }
        if (financialDocument == null) {
            throw new RuleException("fin.financialDocument.openStatusPeriod");

        } else {
            List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocumentId);
            deleteDocumentItem(financialDocumentItemList);
            List<FinancialDocumentNumber> financialDocumentNumberList = financialDocumentNumberRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocumentId);
            financialDocumentNumberList.forEach(financialDocumentNumber -> financialDocumentNumberRepository.deleteById(financialDocumentNumber.getId()));
            financialDocumentRepository.deleteById(financialDocumentId);
        }
        return true;
    }

    public void deleteDocumentItem(List<FinancialDocumentItem> financialDocumentItemList) {
        financialDocumentItemList.forEach((FinancialDocumentItem documentItem) -> {
            Long documentItemByIdForDelete = financialDocumentItemRepository.getDocumentItemByIdForDelete(documentItem.getId());
            if (documentItemByIdForDelete > 0) {
                FinancialDocumentReference financialDocumentReference = financialDocumentReferenceRepository.getByDocumentItemId(documentItem.getId());
                if (financialDocumentReference != null) {
                    financialDocumentReferenceRepository.deleteById(financialDocumentReference.getId());
                }
                FinancialDocumentItemCurrency financialDocumentItemCurrency = documentItemCurrencyRepository.getByDocumentItemId(documentItem.getId());
                if (financialDocumentItemCurrency != null) {
                    documentItemCurrencyRepository.deleteById(financialDocumentItemCurrency.getId());
                }
                financialDocumentItemRepository.deleteById(documentItem.getId());

            } else {
                financialDocumentItemRepository.deleteById(documentItem.getId());
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String changeAccountDocument(FinancialDocumentAccountDto financialDocumentAccountDto) {
        FinancialDocument financialDocument = financialDocumentRepository.findById(financialDocumentAccountDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(financialDocumentAccountDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }
        FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest = new FinancialPeriodLedgerStatusRequest();
        financialPeriodLedgerStatusRequest.setFinancialDocumentId(financialDocumentAccountDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatusResponse = getFinancialPeriodStatus(financialPeriodLedgerStatusRequest);
        if (financialPeriodStatusResponse.getPeriodStatus() == 0L || financialPeriodStatusResponse.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه مربوط به دفتر مالی میبایست در وضعیت باز باشند");
        }
        AtomicReference<String> message = new AtomicReference<>("");
        if (financialDocumentAccountDto.getFinancialAccountId().equals(financialDocumentAccountDto.getNewFinancialAccountId())) {
            throw new RuleException("fin.financialDocument.sameDocumentAccount");
        }
        List<FinancialDocumentItem> financialDocumentItemList =
                financialDocumentItemRepository.getItemByDocumentItemIdListAndAccountId(financialDocumentAccountDto.getFinancialDocumentItemIdList(),
                        financialDocumentAccountDto.getFinancialAccountId());
        if (financialDocumentItemList.size() == 0) {
            throw new RuleException("ردیفی با این حساب یافت نشد.");
        }
        financialDocumentItemList.forEach((FinancialDocumentItem documentItem) -> {
            documentItem.setFinancialAccount(financialAccountRepository.getOne(financialDocumentAccountDto.getNewFinancialAccountId()));
            financialDocumentItemRepository.save(documentItem);
            FinancialAccount financialAccount = financialAccountRepository.getFinancialAccountRelationType(financialDocumentAccountDto.getFinancialAccountId(),
                    financialDocumentAccountDto.getNewFinancialAccountId());
            if (financialAccount == null) {
                documentItem.setCentricAccountId1(null);
                documentItem.setCentricAccountId2(null);
                documentItem.setCentricAccountId3(null);
                documentItem.setCentricAccountId4(null);
                documentItem.setCentricAccountId5(null);
                documentItem.setCentricAccountId6(null);
                message.set(message("به دلیل انواع تفاوت در وابستگی حساب جدید با حساب قبلی،تمام تمرکز های مربوطه حذف شده.لطفا تمرکز های مربوطه را درج نمایید."));
                financialDocumentItemRepository.save(documentItem);
            } else {
                message.set(message("عملیات با موفقیت انجام شد."));
            }

        });
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
        financialDocumentRepository.save(financialDocument);
        return message.get();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String changeCentricAccount(FinancialCentricAccountDto financialCentricAccountDto) {
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        FinancialDocument document = financialDocumentRepository.findById(financialCentricAccountDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        CentricAccount centricAccount = centricAccountRepository.findById(financialCentricAccountDto.getCentricAccountId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistCentricAccount"));
        CentricAccount newCentricAccount = centricAccountRepository.findById(financialCentricAccountDto.getNewCentricAccountId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistCentricAccount"));
        final AtomicInteger[] atomicInteger = {new AtomicInteger(0)};
        if (centricAccount.getCentricAccountType().getId().equals(newCentricAccount.getCentricAccountType().getId())) {
            List<FinancialDocumentItem> financialDocumentItemList =
                    financialDocumentItemRepository.getByDocumentIdAndCentricAccount(financialCentricAccountDto.getFinancialDocumentItemIdList(), financialCentricAccountDto.getNewCentricAccountId(), financialCentricAccountDto.getFinancialAccountId());
            if (financialDocumentItemList.isEmpty()) {
                throw new RuleException("fin.financialDocument.notExistDocumentItem");
            }
            FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
            financialPeriodStatusRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
            FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
            if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
                throw new RuleException("دوره مالی و ماه عملیاتی سند مبدا میبایست در وضعیت باز باشند");
            }
            FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest = new FinancialPeriodLedgerStatusRequest();
            financialPeriodLedgerStatusRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
            FinancialPeriodStatusResponse financialPeriodStatusResponse = getFinancialPeriodStatus(financialPeriodLedgerStatusRequest);
            if (financialPeriodStatusResponse.getPeriodStatus() == 0L || financialPeriodStatusResponse.getMonthStatus() == 0L) {
                throw new RuleException("دوره مالی و ماه مربوط به دفتر مالی میبایست در وضعیت باز باشند");
            }
            Long centricAccountId = financialCentricAccountDto.getCentricAccountId();
            financialDocumentItemList.forEach((FinancialDocumentItem documentItem) -> {


                atomicInteger[0] = check2(documentItem, centricAccountId, financialCentricAccountDto, atomicInteger[0]);

            });

        } else {
            throw new RuleException("fin.financialDocument.notExistCentricAccountType");
        }
        if (atomicInteger[0].get() > 0) {
            document.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
            financialDocumentRepository.save(document);
            financialDocumentRepository.flush();
            return "تمامی تمرکز های سطوح بعد از تمرکز تغییر یافته حذف شدند. لطفا مجددا نسبت به انتخاب تمرکز ها اقدام فرمایید.";
        } else {
            return "هیچ رکوردی بروز رسانی نشد";
        }

    }

    private AtomicInteger check2(FinancialDocumentItem documentItem, Long
            centricAccountId, FinancialCentricAccountDto financialCentricAccountDto, AtomicInteger atomicInteger) {

        if ((documentItem.getCentricAccountId1() != null) && (centricAccountId.equals(documentItem.getCentricAccountId1().getId()))) {
            atomicInteger.getAndIncrement();
            documentItem.setCentricAccountId1(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
            documentItem.setCentricAccountId2(null);
            documentItem.setCentricAccountId3(null);
            documentItem.setCentricAccountId4(null);
            documentItem.setCentricAccountId5(null);
            documentItem.setCentricAccountId6(null);
        } else if (documentItem.getCentricAccountId2() != null && centricAccountId.equals(documentItem.getCentricAccountId2().getId())) {
            atomicInteger.getAndIncrement();
            documentItem.setCentricAccountId2(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
            documentItem.setCentricAccountId3(null);
            documentItem.setCentricAccountId4(null);
            documentItem.setCentricAccountId5(null);
            documentItem.setCentricAccountId6(null);
        } else if (documentItem.getCentricAccountId3() != null && centricAccountId.equals(documentItem.getCentricAccountId3().getId())) {
            atomicInteger.getAndIncrement();
            documentItem.setCentricAccountId3(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
            documentItem.setCentricAccountId4(null);
            documentItem.setCentricAccountId5(null);
            documentItem.setCentricAccountId6(null);
        } else if (documentItem.getCentricAccountId4() != null && centricAccountId.equals(documentItem.getCentricAccountId4().getId())) {
            atomicInteger.getAndIncrement();
            documentItem.setCentricAccountId4(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
            documentItem.setCentricAccountId5(null);
            documentItem.setCentricAccountId6(null);
        } else if (documentItem.getCentricAccountId5() != null && centricAccountId.equals(documentItem.getCentricAccountId5().getId())) {
            atomicInteger.getAndIncrement();
            documentItem.setCentricAccountId5(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
            documentItem.setCentricAccountId6(null);
        } else if (documentItem.getCentricAccountId6() != null && centricAccountId.equals(documentItem.getCentricAccountId6().getId())) {
            atomicInteger.getAndIncrement();
            documentItem.setCentricAccountId6(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
        } else {
            //nothing
        }
        financialDocumentItemRepository.save(documentItem);
        financialDocumentItemRepository.flush();
        return atomicInteger;
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean changeAmountDocument(FinancialCentricAccountDto financialCentricAccountDto) {
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);
        FinancialDocument document = financialDocumentRepository.findById(financialCentricAccountDto.getId())
                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }

        FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest = new FinancialPeriodLedgerStatusRequest();
        financialPeriodLedgerStatusRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatusResponse = getFinancialPeriodStatus(financialPeriodLedgerStatusRequest);
        if (financialPeriodStatusResponse.getPeriodStatus() == 0L || financialPeriodStatusResponse.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه مربوط به دفتر مالی میبایست در وضعیت باز باشند");
        }
        List<FinancialDocumentItem> financialDocumentItemList =
                financialDocumentItemRepository.findByFinancialDocumentItemIdList(financialCentricAccountDto.getFinancialDocumentItemIdList());
        financialDocumentItemList.forEach((FinancialDocumentItem documentItem) -> {
            Double newAmount = documentItem.getCreditAmount();
            documentItem.setCreditAmount(documentItem.getDebitAmount());
            documentItem.setDebitAmount(newAmount);
            financialDocumentItemRepository.save(documentItem);
        });
        List<FinancialDocumentItemCurrency> financialDocumentItemCurrencyList =
                financialDocumentItemCurrencyRepository.findByFinancialDocumentItemCurrencyIdList(financialCentricAccountDto.getFinancialDocumentItemIdList());
        financialDocumentItemCurrencyList.forEach((FinancialDocumentItemCurrency documentItem) -> {
            Double newAmount2 = documentItem.getForeignCreditAmount();
            documentItem.setForeignCreditAmount(documentItem.getForeignDebitAmount());
            documentItem.setForeignDebitAmount(newAmount2);
            financialDocumentItemCurrencyRepository.save(documentItem);
        });
        document.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
        financialDocumentRepository.save(document);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean setAmountDocument(FinancialCentricAccountDto financialCentricAccountDto) {
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);
        FinancialDocument document = financialDocumentRepository.findById(financialCentricAccountDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }
        FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest = new FinancialPeriodLedgerStatusRequest();
        financialPeriodLedgerStatusRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatusResponse = getFinancialPeriodStatus(financialPeriodLedgerStatusRequest);
        if (financialPeriodStatusResponse.getPeriodStatus() == 0L || financialPeriodStatusResponse.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه مربوط به دفتر مالی میبایست در وضعیت باز باشند");
        }
        List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.findByFinancialDocumentItemIdList(financialCentricAccountDto.getFinancialDocumentItemIdList());
        financialDocumentItemList.forEach((FinancialDocumentItem documentItem) -> {
            documentItem.setCreditAmount(0D);
            documentItem.setDebitAmount(0D);
            financialDocumentItemRepository.save(documentItem);
        });

        List<FinancialDocumentItemCurrency> financialDocumentItemCurrencyList =
                financialDocumentItemCurrencyRepository.findByFinancialDocumentItemCurrencyIdList(financialCentricAccountDto.getFinancialDocumentItemIdList());
        financialDocumentItemCurrencyList.forEach((FinancialDocumentItemCurrency documentItem) -> {
            documentItem.setForeignDebitAmount(0D);
            documentItem.setForeignCreditAmount(0D);
            financialDocumentItemCurrencyRepository.save(documentItem);
        });
        document.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
        financialDocumentRepository.save(document);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean setArrangeSequence(FinancialDocumentDto financialDocumentDto) {
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialDocumentDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        FinancialDocument document = financialDocumentRepository.findById(financialDocumentDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(financialDocumentDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مبدا میبایست در وضعیت باز باشند");
        }
        FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest = new FinancialPeriodLedgerStatusRequest();
        financialPeriodLedgerStatusRequest.setFinancialDocumentId(financialDocumentDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatusResponse = getFinancialPeriodStatus(financialPeriodLedgerStatusRequest);
        if (financialPeriodStatusResponse.getPeriodStatus() == 0L || financialPeriodStatusResponse.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه مربوط به دفتر مالی میبایست در وضعیت باز باشند");
        }

        entityManager.createNativeQuery("update fndc.financial_document_item  fndi_outer " +
                "   set fndi_outer.sequence_number = " +
                "       (select rn " +
                "          from (select ROW_NUMBER() over(order by fndi.debit_amount - fndi.credit_amount desc, fnc.code, cnt1.code, cnt2.code, cnt3.code, cnt4.code, cnt5.code, cnt6.code) as rn, " +
                "                       fndi.id " +
                "                  from fndc.financial_document_item fndi " +
                "                 left join fnac.financial_account fnc " +
                "                    on fnc.id = fndi.financial_account_id " +
                "                 left join fnac.centric_account cnt1 " +
                "                    on fndi.centric_account_id_1 = cnt1.id " +
                "                 left join fnac.centric_account cnt2 " +
                "                    on fndi.centric_account_id_2 = cnt2.id " +
                "                 left join fnac.centric_account cnt3 " +
                "                    on fndi.centric_account_id_3 = cnt3.id " +
                "                 left join fnac.centric_account cnt4 " +
                "                    on fndi.centric_account_id_4 = cnt4.id " +
                "                 left join fnac.centric_account cnt5 " +
                "                    on fndi.centric_account_id_5 = cnt5.id " +
                "                 left join fnac.centric_account cnt6 " +
                "                    on fndi.centric_account_id_6 = cnt6.id " +
                "                 where fndi.financial_document_id = :financialDocumentId " +
                "                 ) qry " +
                "         where      " +
                "         qry.id = fndi_outer.id) " +
                " where fndi_outer.financial_document_id = :financialDocumentId ").setParameter("financialDocumentId", document.getId()).executeUpdate();
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DataSourceResult documentByStructure(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        RequestDocumentStructureDto paramSearch = setParamDocumentStructure(filters);
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list = financialDocumentItemRepository.getDocumentByStructure(paramSearch.getFinancialDocumentId(),
                paramSearch.getFinancialStructureId(), pageable);
        List<ResponseDocumentStructureDto> documentStructureDtoList = list.stream().map(item ->
                ResponseDocumentStructureDto.builder()
                        .debit(((BigDecimal) item[0]).doubleValue())
                        .credit(((BigDecimal) item[1]).doubleValue())
                        .financialAccountCode(item[2].toString())
                        .financialAccountDescription(item[3].toString())
                        .financialAccountId(Long.parseLong(item[4].toString()))
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(documentStructureDtoList);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private RequestDocumentStructureDto setParamDocumentStructure
            (List<DataSourceRequest.FilterDescriptor> filters) {
        RequestDocumentStructureDto requestDocumentStructureDto = new RequestDocumentStructureDto();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialDocument.id":
                    requestDocumentStructureDto.setFinancialDocumentId(Long.parseLong(item.getValue().toString()));
                    break;
                case "financialStructure.id":
                    requestDocumentStructureDto.setFinancialStructureId(Long.parseLong(item.getValue().toString()));
                    break;
                default:

                    break;
            }
        }
        return requestDocumentStructureDto;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<ResponseFinancialDocumentStructureDto> getDocumentStructure(RequestDocumentStructureDto
                                                                                    requestDocumentStructureDto) {
        List<Object[]> documentStructure = financialDocumentItemRepository.getDocumentStructurList(requestDocumentStructureDto.getFinancialDocumentId());
        return documentStructure.stream().map(objects ->
                ResponseFinancialDocumentStructureDto.builder()
                        .documentStructureId(Long.parseLong(objects[0].toString()))
                        .sequence(Long.parseLong(objects[1].toString()))
                        .description(objects[2].toString())
                        .financialCodingTypeId(Long.parseLong(objects[3].toString()))
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public FinancialPeriodStatusResponse getFinancialPeriodStatus(FinancialPeriodLedgerStatusRequest
                                                                          financialPeriodLedgerStatusRequest) {
        if (financialPeriodLedgerStatusRequest.getFinancialDocumentId() == null && financialPeriodLedgerStatusRequest.getFinancialLedgerTypeId() == null
                && financialPeriodLedgerStatusRequest.getFinancialPeriodId() == null
                && financialPeriodLedgerStatusRequest.getDate() == null) {
            throw new RuleException("fin.financialPeriod.getStatus");
        }
        if (financialPeriodLedgerStatusRequest.getFinancialDocumentId() != null) {
            List<Object[]> financialPeriodListObject = financialPeriodRepository.getFinancialPeriodByFinancialDocumentId(financialPeriodLedgerStatusRequest.getFinancialDocumentId());
            financialPeriodLedgerStatusRequest.setFinancialPeriodId(financialPeriodListObject.get(0)[0] == null ? financialPeriodLedgerStatusRequest.getFinancialPeriodId() : Long.parseLong(financialPeriodListObject.get(0)[0].toString()));
            financialPeriodLedgerStatusRequest.setDate(financialPeriodListObject.get(0)[1] == null ? financialPeriodLedgerStatusRequest.getDate() : LocalDateTime.parse(financialPeriodListObject.get(0)[1].toString().substring(0, 10) + "T00:00"));
            financialPeriodLedgerStatusRequest.setFinancialLedgerTypeId(financialPeriodListObject.get(0)[2] == null ? financialPeriodLedgerStatusRequest.getFinancialLedgerTypeId() : Long.parseLong(financialPeriodListObject.get(0)[2].toString()));

            if (financialPeriodLedgerStatusRequest.getFinancialPeriodId() == null || financialPeriodLedgerStatusRequest.getDate() == null
                    || financialPeriodLedgerStatusRequest.getFinancialLedgerTypeId() == null) {
                throw new RuleException("fin.financialPeriod.getStatusCheck");
            }
        }
        if (financialPeriodLedgerStatusRequest.getFinancialDocumentId() == null && financialPeriodLedgerStatusRequest.getFinancialPeriodId() == null
                && financialPeriodLedgerStatusRequest.getDate() != null && financialPeriodLedgerStatusRequest.getFinancialLedgerTypeId() != null) {
            FinancialPeriodRequest financialPeriodRequest = new FinancialPeriodRequest();
            financialPeriodRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
            financialPeriodRequest.setDate(financialPeriodLedgerStatusRequest.getDate());
        }

        FinancialPeriodStatusResponse financialPeriodStatusResponses = new FinancialPeriodStatusResponse();
        Long periodStatus = financialPeriodRepository.findFinancialPeriodByIdAndLedgerType(financialPeriodLedgerStatusRequest.getFinancialPeriodId(), financialPeriodLedgerStatusRequest.getFinancialLedgerTypeId());
        Long monthStatus = financialPeriodRepository.findFinancialPeriodByIdAndLedgerTypeAndDate(financialPeriodLedgerStatusRequest.getFinancialPeriodId(), financialPeriodLedgerStatusRequest.getFinancialLedgerTypeId(), financialPeriodLedgerStatusRequest.getDate(), DateUtil.convertStringToDate(financialPeriodLedgerStatusRequest.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));

        financialPeriodStatusResponses.setPeriodStatus(periodStatus);
        financialPeriodStatusResponses.setMonthStatus(monthStatus);
        return financialPeriodStatusResponses;
    }

    private FinancialDocumentFilterRequest setParameterProblem(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialDocumentFilterRequest financialDocumentFilterRequest = new FinancialDocumentFilterRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialDepartmentId":
                    checkFinancialDepartmentIdSet(financialDocumentFilterRequest, item);
                    break;
                case "fromDate":
                    checkFromDateForDate(financialDocumentFilterRequest, item);
                    break;

                case "toDate":
                    checkToDateForDate(financialDocumentFilterRequest, item);
                    break;

                case "departmentId":
                    checkDepartmentIdSet(financialDocumentFilterRequest, item);
                    break;

                case "financialLedgerTypeId":
                    checkFinancialLedgerTypeIdSet(financialDocumentFilterRequest, item);

                    break;

                default:
                    break;
            }
        }

        return financialDocumentFilterRequest;
    }

    private void checkFinancialDepartmentIdSet(FinancialDocumentFilterRequest
                                                       financialDocumentFilterRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("financialDepartment", "financialDepartment");
            financialDocumentFilterRequest.setParamMap(map);
            financialDocumentFilterRequest.setFinancialDepartmentId(Long.parseLong(item.getValue().toString()));
            financialDocumentFilterRequest.setFinancialDepartment("financialDepartment");
        } else {
            map.put("financialDepartment", null);
            financialDocumentFilterRequest.setParamMap(map);
            financialDocumentFilterRequest.setFinancialDepartmentId(0L);
        }
    }

    private void checkFromDateForDate(FinancialDocumentFilterRequest
                                              financialDocumentFilterRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("fromDate", "fromDate");
            financialDocumentFilterRequest.setParamMap(map);
            financialDocumentFilterRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            map.put("fromDate", null);
            financialDocumentFilterRequest.setParamMap(map);
            financialDocumentFilterRequest.setFromDate(null);
        }
    }

    private void checkToDateForDate(FinancialDocumentFilterRequest
                                            financialDocumentFilterRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("toDate", "toDate");
            financialDocumentFilterRequest.setParamMap(map);
            financialDocumentFilterRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            map.put("toDate", null);
            financialDocumentFilterRequest.setParamMap(map);
            financialDocumentFilterRequest.setToDate(null);
        }
    }

    private void checkDepartmentIdSet(FinancialDocumentFilterRequest
                                              financialDocumentFilterRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("department", "department");
            financialDocumentFilterRequest.setParamMap(map);
            financialDocumentFilterRequest.setDepartmentId(Long.parseLong(item.getValue().toString()));
            financialDocumentFilterRequest.setDepartment("department");
        } else {
            map.put("department", null);
            financialDocumentFilterRequest.setParamMap(map);
            financialDocumentFilterRequest.setDepartmentId(0L);
        }

    }

    private void checkFinancialLedgerTypeIdSet(FinancialDocumentFilterRequest
                                                       financialDocumentFilterRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("financialLedgerType", "financialLedgerType");
            financialDocumentFilterRequest.setParamMap(map);
            financialDocumentFilterRequest.setFinancialLedgerTypeId(Long.parseLong(item.getValue().toString()));
            financialDocumentFilterRequest.setFinancialLedgerType("financialLedgerType");
        } else {
            map.put("financialLedgerType", null);
            financialDocumentFilterRequest.setParamMap(map);
            financialDocumentFilterRequest.setFinancialLedgerTypeId(0L);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataSourceResult getProblemReport(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialDocumentFilterRequest paramSearch = setParameterProblem(filters);
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
        Pageable pageable = PageRequest.of((dataSourceRequest.getSkip() / dataSourceRequest.getTake()), dataSourceRequest.getTake(), Sort.by(sorts));
        Page<Object[]> list = financialDocumentRepository.findByFinancialDocument(SecurityHelper.getCurrentUser().getOrganizationId(), paramSearch.getFromDate(), paramSearch.getToDate(),
                paramSearch.getFinancialDepartment(), paramSearch.getFinancialDepartmentId(), paramSearch.getDepartment(), paramSearch.getDepartmentId(),
                paramSearch.getFinancialLedgerType(), paramSearch.getFinancialLedgerTypeId(), pageable);
        List<FinancialDocumentFilterResponse> documentDtoList = list.stream().map(item ->
                FinancialDocumentFilterResponse.builder()
                        .id(item[0] == null ? null : ((BigDecimal) item[0]).longValue())
                        .documentNumber(item[1] == null ? null : item[1].toString())
                        .documentDate(item[2] == null ? null : ((Timestamp) item[2]).toLocalDateTime())
                        .errorType(item[3] == null ? null : ((BigDecimal) item[3]).longValue())
                        .listSeq(item[4] == null ? null : item[4].toString())
                        .errorMessage(item[5] == null ? null : item[5].toString())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(documentDtoList.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setData(documentDtoList);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean copyDocFromOldSystem(GetDocFromoldSystemInputRequest getDocFromoldSystemInputRequest) {
        if (getDocFromoldSystemInputRequest.getDchdId() == null || getDocFromoldSystemInputRequest.getDchdNum() == null) {
            throw new RuleException("لطفا یکی از مقادیر را وارد نمایید.");
        }
        return financialSecurityService.resultSetCopyDocFromOld(getDocFromoldSystemInputRequest) !=0;
    }
}
