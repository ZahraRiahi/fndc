package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDepartmentLedgerRequest;
import ir.demisco.cfs.model.entity.FinancialDepartment;
import ir.demisco.cfs.model.entity.FinancialDepartmentLedger;
import ir.demisco.cfs.model.entity.FinancialLedgerType;
import ir.demisco.cfs.service.api.FinancialDepartmentLedgerService;
import ir.demisco.cfs.service.repository.FinancialDepartmentLedgerRepository;
import ir.demisco.cfs.service.repository.FinancialDepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultFinancialDepartmentLedger implements FinancialDepartmentLedgerService {

    private final FinancialDepartmentLedgerRepository financialDepartmentLedgerRepository;
    private final FinancialDepartmentRepository financialDepartmentRepository;
    private final FinancialLedgerTypeRepository financialLedgerTypeRepository;


    public DefaultFinancialDepartmentLedger(FinancialDepartmentLedgerRepository financialDepartmentLedgerRepository, FinancialDepartmentRepository financialDepartmentRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository) {
        this.financialDepartmentLedgerRepository = financialDepartmentLedgerRepository;
        this.financialDepartmentRepository = financialDepartmentRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
    }

    @Override
    @Transactional
    public Boolean saveFinancialDepartmentLedger(List<FinancialDepartmentLedgerRequest> financialDepartmentLedgerRequest) {
        for (FinancialDepartmentLedgerRequest financialDepartmentLedgerRequestListId : financialDepartmentLedgerRequest) {

            Long financialLedgerTypeIdRequest = financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId();
            Optional<FinancialLedgerType> financialLedgerTypeRepositoryById = financialLedgerTypeRepository.findById(financialLedgerTypeIdRequest);
            Optional<FinancialDepartmentLedger> financialDepartmentLedgerRepositoryById = financialDepartmentLedgerRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId());
            if (financialDepartmentLedgerRequestListId.getFinancialDepartmentId() == null) {
                throw new RuleException("لطفا شناسه شعبه، مورد نظر خود را وارد نمایید.");
            }
            Boolean financialDepartmentLedger = financialDepartmentLedgerRepository.getFinancialDepartmentLedger(financialDepartmentLedgerRequestListId.getFinancialDepartmentId()
                    , financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId());
            if (financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId() != null) {
                if (financialDepartmentLedger.equals(false) && financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId() == null) {
                    FinancialDepartmentLedger financialDepartmentLedgerNew = new FinancialDepartmentLedger();
                    Optional<FinancialDepartment> financialDepartmentRepositoryById = financialDepartmentRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentId());
                    if (financialDepartmentRepositoryById.isPresent()) {
                        financialDepartmentLedgerNew.setFinancialDepartmentId(financialDepartmentRepositoryById.get());
                    } else {
                        throw new RuleException("شناسه شعبه، وارد شده معتبر نمی باشد.");
                    }
                    if (financialLedgerTypeRepositoryById.isPresent()) {
                        financialDepartmentLedgerNew.setFinancialLedgerTypeId(financialLedgerTypeRepositoryById.get());
                    } else {
                        throw new RuleException("شناسه نوع دفتر مالی، وارد شده معتبر نمی باشد.");
                    }
                    financialDepartmentLedgerRepository.save(financialDepartmentLedgerNew);

                }
            } else if (financialDepartmentLedger.equals(false) && financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId() != null) {
                if (financialDepartmentLedgerRepositoryById.isPresent()
                        && financialDepartmentLedgerRepositoryById.get().getId().equals(financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId())) {
                    FinancialDepartmentLedger financialDepartmentLedgerForUpdate = financialDepartmentLedgerRepositoryById.get();
                    if (financialLedgerTypeRepositoryById.isPresent()) {
                        financialDepartmentLedgerForUpdate.setFinancialLedgerTypeId(financialLedgerTypeRepositoryById.get());
                    } else {
                        throw new RuleException("شناسه نوع دفتر مالی، وارد شده معتبر نمی باشد.");
                    }
                } else {
                    throw new RuleException("شناسه دفتر مالی شعبه، وارد شده معتبر نمی باشد.");
                }

            } else if (financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId() == null) {
                if (financialDepartmentLedgerRepositoryById.isPresent()) {
                    FinancialDepartmentLedger financialDepartmentLedgerForUpdate = financialDepartmentLedgerRepositoryById.get();
                    if (financialLedgerTypeRepositoryById.isPresent()
                            && financialDepartmentLedgerRequestListId.getFinancialDepartmentId().equals(financialDepartmentLedgerForUpdate.getId())) {
                        financialDepartmentLedgerForUpdate.setDeletedDate(LocalDateTime.now());
                    } else {
                        throw new RuleException("شناسه نوع دفتر مالی، وارد شده  معتبر نمی باشد.");
                    }
                } else {
                    throw new RuleException("شناسه دفتر مالی شعبه، وارد شده معتبر نمی باشد.");
                }
            }
        }
        return null;
    }
}
