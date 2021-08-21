package ir.demisco.cfs.service.impl;


import ir.demisco.cfs.model.dto.response.*;
import ir.demisco.cfs.model.entity.FinancialDocument;
import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import ir.demisco.cfs.model.entity.FinancialDocumentItemCurrency;
import ir.demisco.cfs.model.entity.FinancialDocumentReference;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.codehaus.jackson.map.util.ISO8601Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialDocument implements FinancialDocumentService {


    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialDocumentStatusRepository  documentStatusRepository;
    private final FinancialDocumentItemRepository  financialDocumentItemRepository;
    private final FinancialDocumentReferenceRepository financialDocumentReferenceRepository;
    private final FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository;

    public DefaultFinancialDocument(FinancialDocumentRepository financialDocumentRepository, FinancialDocumentStatusRepository documentStatusRepository, FinancialDocumentItemRepository financialDocumentItemRepository, FinancialDocumentReferenceRepository financialDocumentReferenceRepository, FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository) {
        this.financialDocumentRepository = financialDocumentRepository;
        this.documentStatusRepository = documentStatusRepository;
        this.financialDocumentItemRepository = financialDocumentItemRepository;
        this.financialDocumentReferenceRepository = financialDocumentReferenceRepository;
        this.documentItemCurrencyRepository = documentItemCurrencyRepository;
    }


    @Override
    @Transactional
    public DataSourceResult getFinancialDocumentList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        ResponseFinancialDocumentDto  paramSearch=setParameter(filters);
        Map<String, Object> paramMap = paramSearch.getParamMap();
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list =financialDocumentRepository.getFinancialDocumentList(paramSearch.getStartDate(),
                paramSearch.getEndDate(),paramSearch.getFinancialNumberingTypeId(),paramMap.get("fromNumber"),paramSearch.getFromNumber(),
                paramMap.get("toNumber"),paramSearch.getToNumber(),paramSearch.getDescription(),paramMap.get("fromAccount"),paramSearch.getFromAccountCode(),
                paramMap.get("toAccountCode"),paramSearch.getToAccountCode(),paramMap.get("centricAccount"),paramSearch.getCentricAccountId()
                ,paramMap.get("centricAccountType"),paramSearch.getCentricAccountTypeId(),paramMap.get("user"),paramSearch.getUserId()
                ,paramMap.get("priceType"),paramSearch.getPriceTypeId(),paramMap.get("fromPrice"),paramSearch.getFromPrice(),paramMap.get("toPrice"),
                paramSearch.getToPrice(),paramSearch.getTolerance(),paramSearch.getFinancialDocumentStatusDtoListId(),pageable);
        List<FinancialDocumentDto> documentDtoList = list.stream().map(item ->
                FinancialDocumentDto.builder()
                        .id(((BigDecimal) item[0]).longValue())
                        .documentDate((Date) item[1])
                        .description(item[2].toString())
                        .documentNumber(Long.parseLong(item[3].toString()))
                        .financialDocumentTypeId(Long.parseLong(item[4].toString()))
                        .financialDocumentTypeDescription(item[5].toString())
                         .fullDescription(item[6].toString())
                        .debitAmount(((BigDecimal)item[7]).doubleValue())
                        .creditAmount(((BigDecimal)item[8]).doubleValue())
                        .userId(Long.parseLong(item[9].toString()))
                        .userName(item[10].toString())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(documentDtoList);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private ResponseFinancialDocumentDto setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        ResponseFinancialDocumentDto responseFinancialDocumentDto=new ResponseFinancialDocumentDto();
        Map<String, Object> map = new HashMap<>();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {

                case "startDate":
                    responseFinancialDocumentDto.setStartDate(parseStringToLocalDateTime(String.valueOf(item.getValue()),false));
                    break;

                case "endDate":
                    responseFinancialDocumentDto.setEndDate(parseStringToLocalDateTime(String.valueOf(item.getValue()),false));
                    break;

                case "financialNumberingType.id" :
                    responseFinancialDocumentDto.setFinancialNumberingTypeId(Long.parseLong(item.getValue().toString()));
                    break;

                case "fromNumber.id" :
                    if (item.getValue() != null) {
                        map.put("fromNumber", "fromNumber");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromNumber(Long.parseLong(item.getValue().toString()));
                    }else{
                        map.put("fromNumber", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromNumber(0L);
                    }
                    break;

                case "toNumber.id" :
                    if (item.getValue() != null) {
                        map.put("toNumber", "toNumber");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToNumber(Long.parseLong(item.getValue().toString()));
                    }else{
                        map.put("toNumber", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToNumber(0L);
                    }
                    break;

                case "financialDocumentStatusDtoList" :
                      responseFinancialDocumentDto.setFinancialDocumentStatusDtoListId((List<Long>) item.getValue());
                    break;

                case "description" :
                    if (item.getValue() != null) {
                        responseFinancialDocumentDto.setDescription(item.getValue().toString());
                    }else{
                        responseFinancialDocumentDto.setDescription("");
                    }
                    break;

                case "fromAccount.code" :
                    if (item.getValue() != null) {
                        map.put("fromAccount", "fromAccount");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromAccountCode(item.getValue().toString());
                    }else{
                        map.put("fromAccount", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromAccountCode("");
                    }
                    break;

                case "toAccount.code" :
                    if (item.getValue() != null) {
                        map.put("toAccount", "toAccount");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToAccountCode(item.getValue().toString());
                    }else{
                        map.put("toAccount", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToAccountCode("");
                    }
                    break;

                case "centricAccount.id" :
                    if (item.getValue() != null) {
                        map.put("centricAccount", "centricAccount");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setCentricAccountId(Long.parseLong(item.getValue().toString()));
                    }else{
                        map.put("centricAccount", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setCentricAccountId(0L);
                    }
                    break;
                case "centricAccountType.id" :
                    if (item.getValue() != null) {
                        map.put("centricAccountType", "centricAccountType");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setCentricAccountTypeId(Long.parseLong(item.getValue().toString()));
                    }else{
                        map.put("centricAccountType", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setCentricAccountTypeId(0L);
                    }
                    break;

                case "user.id" :
                    if (item.getValue() != null) {
                        map.put("user", "user");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setUserId(Long.parseLong(item.getValue().toString()));
                    }else{
                        map.put("user", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setUserId(0L);
                    }
                    break;

                case "priceType.id" :
                    if (item.getValue() != null) {
                        map.put("priceType", "priceType");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setPriceTypeId(Long.parseLong(item.getValue().toString()));
                    }else{
                        map.put("priceType",null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setPriceTypeId(0L);
                    }
                    break;

                case "fromPriceAmount" :
                    if (item.getValue() != null) {
                        map.put("fromPrice", "fromPrice");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromPrice(Long.parseLong(item.getValue().toString()));
                    }else{
                        map.put("fromPrice", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromPrice(0L);
                    }
                    break;

                case "toPriceAmount" :
                    if (item.getValue() != null) {
                        map.put("toPrice", "toPrice");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToPrice(Long.parseLong(item.getValue().toString()));
                    }else{
                        map.put("toPrice", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToPrice(0L);
                    }
                    break;

                case "tolerance" :
                    if (item.getValue() != null) {
                        responseFinancialDocumentDto.setTolerance(Double.parseDouble(item.getValue().toString()));
                    } else {
                        responseFinancialDocumentDto.setTolerance(0D);
                    }

                    break;
            }
        }

        return responseFinancialDocumentDto;
    }

    private LocalDateTime parseStringToLocalDateTime(Object input, boolean truncateDate) {
        if (input instanceof String) {
            try {
                Date date = ISO8601Utils.parse((String)input);
                LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                return truncateDate ? DateUtil.truncate(localDateTime) : localDateTime;
            } catch (Exception var4) {
                if (((String)input).equalsIgnoreCase("current_date")) {
                    return truncateDate ? DateUtil.truncate(LocalDateTime.now()) : LocalDateTime.now();
                } else {
                    return ((String)input).equalsIgnoreCase("current_timestamp") ? LocalDateTime.now() : LocalDateTime.parse((String)input);
                }
            }
        } else if (input instanceof LocalDateTime) {
            return truncateDate ? DateUtil.truncate((LocalDateTime)input) : (LocalDateTime) input;
        } else {
            throw new IllegalArgumentException("Filter for LocalDateTime has error :" + input + " with class" + input.getClass());
        }
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentDto changeStatus(ResponseFinancialDocumentStatusDto responseFinancialDocumentStatusDto) {
        FinancialDocument financialDocument=financialDocumentRepository.findById(responseFinancialDocumentStatusDto.getId()).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        validationSetStatus(financialDocument);
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(responseFinancialDocumentStatusDto.getFinancialDocumentStatusId()));
        financialDocumentRepository.save(financialDocument);
        return financialDocumentToDto(financialDocument);
    }

    private void validationSetStatus(FinancialDocument financialDocument) {

        if(financialDocument.getDescription() == null)
        {
            throw new RuleException("سند بدون شرح است.");
        }

        List<FinancialDocumentItem> documentItemList=financialDocumentItemRepository.getDocumentItem(financialDocument.getId());
        if(documentItemList == null){
            throw new RuleException("سند بدون ردیف است.");
        }else{
            documentItemList.forEach(documentItem ->{
                double creditAmount = documentItem.getCreditAmount() % 1;
                double debitAmount=documentItem.getDebitAmount()%1;

                if((creditAmount!=000) || (debitAmount!= 000))
                {
                    throw new RuleException("مبلغ در ردیف اعشاری است.");
                }

                if((documentItem.getCreditAmount()==0) && (documentItem.getDebitAmount()==0)){

                    throw new RuleException("در ردیف سند مبلغ بستانکار و بدهکار صفر می باشد.");
                }

                if((documentItem.getCreditAmount()!=0) && (documentItem.getDebitAmount()!=0)){

                    throw new RuleException("در ردیف سند مبلغ بستانکار و بدهکار هر دو دارای مقدار می باشد.");
                }

                if((documentItem.getCreditAmount() < 0) && (documentItem.getDebitAmount()< 0)){
                    throw new RuleException("در ردیف سند مبلغ بستانکار یا بدهکار  منفی می باشد.");
                }

                if(documentItem.getDescription()==null){

                    throw new RuleException("در سند ردیف بدون شرح وجود دارد.");
                }

            });
        }



        List<Object[]> cost=financialDocumentItemRepository.getCostDocument(financialDocument.getId());
        if(cost==null)
        {
            throw new RuleException("مجموع مبالغ بستانکار و بدهکار در ردیف های سند بالانس نیست.");
        }

       FinancialDocument documentPeriod=financialDocumentRepository.getActivePeriodInDocument(financialDocument.getId());
        if(documentPeriod == null){
            throw new RuleException("وضعیت دوره مالی مربوط به سند بسته است.");
        }

        FinancialDocumentItem documentItemMoneyType=financialDocumentItemRepository.getDocumentMoneyType(financialDocument.getId());
        if(documentItemMoneyType!= null){
            throw new RuleException("نوع ارز ریال می باشد.");
        }
//        List<FinancialDocumentItem>  documentReference=financialDocumentItemRepository.getDocumentRefrnce(financialDocument.getId());
//        if(documentReference!= null){
//            throw new RuleException("تاریخ و شرح در مدارک مرجع پر نشده.");
//        }



    }

    private FinancialDocumentDto financialDocumentToDto(FinancialDocument financialDocument) {
        Object[] objects=financialDocumentItemRepository.getParamByDocumentId(financialDocument.getId());
        return FinancialDocumentDto.builder()
                .id(financialDocument.getId())
                .documentDate(java.util.Date.from(financialDocument.getDocumentDate().atZone(ZoneId.systemDefault()).toInstant()))
                .documentNumber(financialDocument.getDocumentNumber())
                .financialDocumentTypeId(financialDocument.getFinancialDocumentType().getId())
                .financialDocumentTypeDescription(financialDocument.getFinancialDocumentType().getDescription())
                .userId(financialDocument.getCreator().getId())
                .userName(financialDocument.getCreator().getUsername())
                .description(financialDocument.getDescription())
//                .creditAmount(Long.parseLong(objects[1].toString()))
//                .debitAmount(Long.parseLong(objects[0].toString()))
//                .fullDescription(objects[2].toString())

                .build();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public String creatDocumentNumber(FinancialDocumentNumberDto financialDocumentNumberDto) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        String documentNumber=financialDocumentRepository.getDocumentNumber(organizationId,financialDocumentNumberDto.getDate(),
                financialDocumentNumberDto.getFinancialPeriodId());
        if(documentNumber==null){
            throw new RuleException("سند یافت نشد.");
        }
        return documentNumber;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public String changeDescription(FinancialDocumentChengDescriptionDto financialDocumentDto) {
        FinancialDocument financialDocument=financialDocumentRepository.getActiveDocumentById(financialDocumentDto.getId());
        if(financialDocument==null){
            throw new RuleException("سند یافت نشد.");
        }
        List<FinancialDocumentItem> financialDocumentItemList=financialDocumentItemRepository.findDocumentItemByDescription(financialDocumentDto.getId(),financialDocumentDto.getOldDescription());
         if(financialDocumentItemList.isEmpty()){
             throw new RuleException("ردیف یافت نشد.");
         }else {
             financialDocumentItemList.forEach(documentItem -> {
                 documentItem.setDescription(financialDocumentDto.getNewDescription());
                 financialDocumentItemRepository.save(documentItem);
             });
         }
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
        financialDocumentRepository.save(financialDocument);
        return "عملیات با موفقیت انجام شد.";
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteFinancialDocumentById(Long financialDocumentId) {
        FinancialDocument  financialDocument=financialDocumentRepository.getActivePeriodAndMontInDocument(financialDocumentId);

        if(financialDocument == null) {
          throw new RuleException("دوره / ماه عملیاتی میبایست در وضعیت باز باشد.");
        }else {
            financialDocument.setDeletedDate(LocalDateTime.now());
            financialDocumentRepository.save(financialDocument);
            List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.getDocumentItem(financialDocumentId);
            financialDocumentItemList.forEach(documentItem -> {
                documentItem.setDeletedDate(LocalDateTime.now());
                financialDocumentItemRepository.save(documentItem);
                FinancialDocumentReference financialDocumentReference = financialDocumentReferenceRepository.getByDocumentItemId(documentItem.getId());
                if(financialDocumentReference!=null) {
                    financialDocumentReference.setDeletedDate(LocalDateTime.now());
                    financialDocumentReferenceRepository.save(financialDocumentReference);
                }
                FinancialDocumentItemCurrency financialDocumentItemCurrency = documentItemCurrencyRepository.getByDocumentItemId(documentItem.getId());
                if(financialDocumentItemCurrency!= null) {
                    financialDocumentItemCurrency.setDeletedDate(LocalDateTime.now());
                    documentItemCurrencyRepository.save(financialDocumentItemCurrency);
                }
            });
        }

        return true;
    }
}
