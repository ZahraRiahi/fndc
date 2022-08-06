package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDocumentItemDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentItemOutPutResponse;
import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import ir.demisco.cfs.model.entity.FinancialDocumentItemCurrency;
import ir.demisco.cfs.model.entity.FinancialDocumentReference;
import ir.demisco.cfs.service.api.FinancialDocumentItemService;
import ir.demisco.cfs.service.repository.FinancialDocumentItemCurrencyRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentReferenceRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public DefaultFinancialDocumentItem(FinancialDocumentItemRepository financialDocumentItemRepository, FinancialDocumentRepository financialDocumentRepository, EntityManager entityManager, FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository, FinancialDocumentReferenceRepository financialDocumentReferenceRepository, FinancialDocumentItemCurrencyRepository financialDocumentItemCurrencyRepository) {
        this.financialDocumentItemRepository = financialDocumentItemRepository;
        this.financialDocumentRepository = financialDocumentRepository;
        this.entityManager = entityManager;
        this.documentItemCurrencyRepository = documentItemCurrencyRepository;
        this.financialDocumentReferenceRepository = financialDocumentReferenceRepository;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialDocumentItemList(DataSourceRequest dataSourceRequest) {
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
        Page<Object[]> list = financialDocumentItemRepository.getFinancialDocumentItemList(pageable);

        List<FinancialDocumentItemDto> documentItemDtoList = list.stream().map(item ->
                FinancialDocumentItemDto.builder()
                        .id(((BigDecimal) item[0]).longValue())
                        .documentDate((Date) item[1])
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
        dataSourceResult.setData(list.getContent());
        dataSourceResult.setData(documentItemDtoList.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
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
