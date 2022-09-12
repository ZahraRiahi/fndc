package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialLedgerPeriodFilterRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerPeriodRequest;
import ir.demisco.cfs.model.dto.response.FinancialLedgerPeriodOutputResponse;
import ir.demisco.cfs.model.entity.FinancialLedgerMonth;
import ir.demisco.cfs.model.entity.FinancialLedgerPeriod;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodService;
import ir.demisco.cfs.service.repository.FinancialLedgerMonthRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerMonthStatusRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerPeriodRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerPeriodStatusRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cfs.service.repository.FinancialMonthRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DefaultFinancialLedgerPeriod implements FinancialLedgerPeriodService {
    private final FinancialLedgerPeriodRepository financialLedgerPeriodRepository;
    private final FinancialLedgerPeriodStatusRepository financialLedgerPeriodStatusRepository;
    private final FinancialPeriodRepository financialPeriodRepository;
    private final FinancialLedgerTypeRepository financialLedgerTypeRepository;
    private final FinancialMonthRepository financialMonthRepository;
    private final FinancialLedgerMonthStatusRepository financialLedgerMonthStatusRepository;
    private final FinancialLedgerMonthRepository financialLedgerMonthRepository;

    public DefaultFinancialLedgerPeriod(FinancialLedgerPeriodRepository financialLedgerPeriodRepository, FinancialLedgerPeriodStatusRepository financialLedgerPeriodStatusRepository, FinancialPeriodRepository financialPeriodRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository, FinancialMonthRepository financialMonthRepository, FinancialLedgerMonthStatusRepository financialLedgerMonthStatusRepository, FinancialLedgerMonthRepository financialLedgerMonthRepository) {
        this.financialLedgerPeriodRepository = financialLedgerPeriodRepository;
        this.financialLedgerPeriodStatusRepository = financialLedgerPeriodStatusRepository;
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialMonthRepository = financialMonthRepository;
        this.financialLedgerMonthStatusRepository = financialLedgerMonthStatusRepository;
        this.financialLedgerMonthRepository = financialLedgerMonthRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean saveFinancialLedgerPeriod(FinancialLedgerPeriodRequest financialLedgerPeriodRequest) {
        if (financialLedgerPeriodRequest.getFinancialPeriodId() == null) {
            throw new RuleException("دوره ی مالی را مشخص نمایید.");
        }
        if (financialLedgerPeriodRequest.getFinancialLedgerTypeId() == null) {
            throw new RuleException("دفتر مالی را مشخص نمایید.");
        }
        Long financialLedgerPeriodUniqueCount = financialLedgerPeriodRepository.getCountByFinancialLedgerPeriodByPeriodIdAndLedgerTypeId(financialLedgerPeriodRequest.getFinancialPeriodId(), financialLedgerPeriodRequest.getFinancialLedgerTypeId());
        if (financialLedgerPeriodUniqueCount > 0) {
            throw new RuleException("fin.financialLedgerPeriodUnique.save");
        }

        FinancialLedgerPeriod financialLedgerPeriod = financialLedgerPeriodRepository.findById(financialLedgerPeriodRequest.getId() == null ? 0 : financialLedgerPeriodRequest.getId()).orElse(new FinancialLedgerPeriod());
        financialLedgerPeriod.setFinancialLedgerPeriodStatus(financialLedgerPeriodStatusRepository.getOne(1L));
        financialLedgerPeriod.setFinancialPeriod(financialPeriodRepository.getOne(financialLedgerPeriodRequest.getFinancialPeriodId()));
        financialLedgerPeriod.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialLedgerPeriodRequest.getFinancialLedgerTypeId()));
        financialLedgerPeriod.setFinancialDocumentOpening(null);
        financialLedgerPeriod.setFinancialDocumentTemprory(null);
        financialLedgerPeriod.setFinancialDocumentPermanent(null);
        financialLedgerPeriodRepository.save(financialLedgerPeriod);

        List<Long> financialMonth = financialMonthRepository.findByFinancialMonth(financialLedgerPeriodRequest.getFinancialPeriodId());
        if (financialMonth.size() == 0) {
            throw new RuleException("به ازای این دوره مالی ماه عملیاتی یافت نشد");
        }
        Long count = financialLedgerMonthRepository.getCountByFinancialLedgerMonthByIdAndLedgerTypeIdAndLedgerPeriod(financialMonth.get(0), financialLedgerPeriodRequest.getFinancialLedgerTypeId(), financialLedgerPeriod.getId());
        if (count == 0) {
            throw new RuleException("عملیات ماهیانه دفتر مالی سازمان با این شناسه دوره دفتر مالی و شناسه ماه عملیاتی و شناسه انوع دفتر مالی سازمان قبلا ثبت شده است.");
        }
        financialMonth.forEach((Long e) -> {
            FinancialLedgerMonth financialLedgerMonth = new FinancialLedgerMonth();
            financialLedgerMonth.setFinancialLedgerMonthStatus(financialLedgerMonthStatusRepository.getOne(1L));
            financialLedgerMonth.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialLedgerPeriodRequest.getFinancialLedgerTypeId()));
            financialLedgerMonth.setFinancialMonth(financialMonthRepository.getOne(financialMonth.get(0)));
            financialLedgerMonth.setFinancialLedgerPeriod(financialLedgerPeriodRepository.getOne(financialLedgerPeriod.getId()));
            financialLedgerMonthRepository.save(financialLedgerMonth);

        });

        return true;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public DataSourceResult getFinancialLedgerPeriodList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        if(dataSourceRequest.getFilter().getFilters().get(0).getValue()==null){
            throw new RuleException("دفتر مالی را مشخص نمایید");
        }
        FinancialLedgerPeriodFilterRequest paramSearch = setParameter(filters);
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
        Page<Object[]> list = financialLedgerPeriodRepository.findByFinancialLedgerTypeIdAndId(paramSearch.getFinancialLedgerTypeId(), pageable);
        List<FinancialLedgerPeriodOutputResponse> financialLedgerPeriodDtoList = list.stream().map(item ->
                FinancialLedgerPeriodOutputResponse.builder()
                        .id(item[0] == null ? null : (Long.parseLong(item[0].toString())))
                        .startDate(item[1] == null ? null : ((Date) item[1]))
                        .endDate(item[2] == null ? null : ((Date) item[2]))
                        .openMonthCount(item[3] == null ? null : (Long.parseLong(item[3].toString())))
                        .description(item[4] == null ? null : item[4].toString())
                        .name(item[5] == null ? null : item[5].toString())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialLedgerPeriodDtoList.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setData(financialLedgerPeriodDtoList);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private FinancialLedgerPeriodFilterRequest setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialLedgerPeriodFilterRequest financialLedgerPeriodFilterRequest = new FinancialLedgerPeriodFilterRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialLedgerTypeId":
                    financialLedgerPeriodFilterRequest.setFinancialLedgerTypeId(Long.parseLong(item.getValue().toString()));
                    break;

                default:
                    break;
            }
        }
        return financialLedgerPeriodFilterRequest;
    }

}
