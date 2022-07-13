package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodStatusResponse;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialPeriod implements FinancialPeriodService {

    private final FinancialPeriodRepository financialPeriodRepository;
    private final FinancialDocumentRepository financialDocumentRepository;

    public DefaultFinancialPeriod(FinancialPeriodRepository financialPeriodRepository, FinancialDocumentRepository financialDocumentRepository) {
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialDocumentRepository = financialDocumentRepository;
    }

    @Override
    @Transactional
    public FinancialPeriodStatusResponse getFinancialPeriodStatus(FinancialPeriodStatusRequest financialPeriodStatusRequest) {
        checkFinancialPeriodStatus(financialPeriodStatusRequest);
        FinancialPeriodStatusResponse financialPeriodStatusResponses = new FinancialPeriodStatusResponse();

        if (financialPeriodStatusRequest.getFinancialDocumentId() != null) {
            List<Object[]> financialDocument = financialDocumentRepository.financialDocumentById(financialPeriodStatusRequest.getFinancialDocumentId());
            financialPeriodStatusRequest.setDate(financialDocument.get(0)[1] == null ? financialPeriodStatusRequest.getDate() : LocalDateTime.parse(financialDocument.get(0)[1].toString().substring(0, 10) + "T00:00"));
            financialPeriodStatusRequest.setFinancialPeriodId(financialDocument.get(0)[0] == null ? financialPeriodStatusRequest.getFinancialPeriodId() : Long.parseLong(financialDocument.get(0)[0].toString()));
            if (financialPeriodStatusRequest.getFinancialPeriodId() == null || financialPeriodStatusRequest.getDate() == null) {
                throw new RuleException("fin.financialPeriod.list");
            }
        }
        if (financialPeriodStatusRequest.getFinancialDocumentId() == null && financialPeriodStatusRequest.getFinancialPeriodId() == null
                && financialPeriodStatusRequest.getDate() != null && financialPeriodStatusRequest.getOrganizationId() != null) {
            FinancialPeriodRequest financialPeriodRequest = new FinancialPeriodRequest();
            financialPeriodRequest.setDate(financialPeriodStatusRequest.getDate());
            List<FinancialPeriodResponse> accountByDateAndOrgan = this.getFinancialAccountByDateAndOrgan(financialPeriodRequest, SecurityHelper.getCurrentUser().getOrganizationId());
            Long financialPeriodId = null;
            if (!accountByDateAndOrgan.isEmpty()) {
                financialPeriodId = accountByDateAndOrgan.get(0).getId();
            }
            financialPeriodStatusRequest.setFinancialPeriodId(financialPeriodId);
            if (financialPeriodStatusRequest.getFinancialPeriodId() == null) {
                financialPeriodStatusResponses.setMonthStatus(0L);
                financialPeriodStatusResponses.setPeriodStatus(0L);
                return financialPeriodStatusResponses;
            }
        }
        Long periodStatus = financialPeriodRepository.findFinancialPeriodById(financialPeriodStatusRequest.getFinancialPeriodId());
        financialPeriodStatusResponses.setPeriodStatus(periodStatus);
        financialPeriodStatusResponses.setMonthStatus(1L);
        return financialPeriodStatusResponses;
    }

    private void checkFinancialPeriodStatus(FinancialPeriodStatusRequest financialPeriodStatusRequest) {
        if (financialPeriodStatusRequest.getFinancialDocumentId() == null && financialPeriodStatusRequest.getFinancialPeriodId() == null
                && financialPeriodStatusRequest.getDate() == null && financialPeriodStatusRequest.getOrganizationId() == null) {
            throw new RuleException("fin.financialPeriod.getStatus");
        }
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<FinancialPeriodResponse> getFinancialAccountByDateAndOrgan(FinancialPeriodRequest
                                                                                   financialPeriodRequest, Long organizationId) {
        List<Object[]> financialPeriodListObject = financialPeriodRepository.findByFinancialPeriodAndDate(financialPeriodRequest.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), organizationId);
        return financialPeriodListObject.stream().map(objects -> FinancialPeriodResponse.builder().id(Long.parseLong(objects[0].toString()))
                .description(objects[2] == null ? null : objects[2].toString())
                .code(objects[3] == null ? null : objects[3].toString())
                .fullDescription(objects[1].toString())
                .build()).collect(Collectors.toList());
    }

}

