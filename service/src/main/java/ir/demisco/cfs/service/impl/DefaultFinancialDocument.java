package ir.demisco.cfs.service.impl;


import ir.demisco.cfs.model.dto.response.FinancialDocumentDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentDto;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
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

    public DefaultFinancialDocument(FinancialDocumentRepository financialDocumentRepository) {
        this.financialDocumentRepository = financialDocumentRepository;
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
                        .debitAmount(Long.parseLong(item[7].toString()))
                        .creditAmount(Long.parseLong(item[8].toString()))
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
}
