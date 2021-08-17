package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDepartmentLedgerRequest;
import ir.demisco.cfs.model.entity.FinancialDepartment;
import ir.demisco.cfs.model.entity.FinancialDepartmentLedger;
import ir.demisco.cfs.model.entity.FinancialLedgerType;
import ir.demisco.cfs.service.api.FinancialDepartmentLedgerService;
import ir.demisco.cfs.service.repository.FinancialDepartmentLedgerRepository;
import ir.demisco.cfs.service.repository.FinancialDepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
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
    private final FinancialDocumentRepository financialDocumentRepository;


    public DefaultFinancialDepartmentLedger(FinancialDepartmentLedgerRepository financialDepartmentLedgerRepository,
                                            FinancialDepartmentRepository financialDepartmentRepository,
                                            FinancialLedgerTypeRepository financialLedgerTypeRepository,
                                            FinancialDocumentRepository financialDocumentRepository) {
        this.financialDepartmentLedgerRepository = financialDepartmentLedgerRepository;
        this.financialDepartmentRepository = financialDepartmentRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialDocumentRepository = financialDocumentRepository;
    }

    @Override
    @Transactional
    public Boolean saveFinancialDepartmentLedger(List<FinancialDepartmentLedgerRequest> financialDepartmentLedgerRequest) {
        for (FinancialDepartmentLedgerRequest financialDepartmentLedgerRequestListId : financialDepartmentLedgerRequest) {
            Long financialLedgerTypeIdRequest = financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId();
            if (financialDepartmentLedgerRequestListId.getFinancialDepartmentId() == null) {
                throw new RuleException("لطفا شناسه شعبه، مورد نظر خود را وارد نمایید.");
            }
            Optional<FinancialLedgerType> financialLedgerTypeRepositoryById = null;
            if (financialLedgerTypeIdRequest != null) {
                financialLedgerTypeRepositoryById = financialLedgerTypeRepository.findById(financialLedgerTypeIdRequest);
            }
            Optional<FinancialDepartment> financialDepartmentRepositoryById = null;
            if (financialDepartmentLedgerRequestListId.getFinancialDepartmentId() != null) {
                financialDepartmentRepositoryById = financialDepartmentRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentId());
            }
            boolean hasInFinancialDepartmentLedger = true;
            if (financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId() == null) {
                hasInFinancialDepartmentLedger = checkWhenFinancialLedgerIsNull(financialDepartmentRepositoryById.get().getId());
            } else {
                hasInFinancialDepartmentLedger = checkFinancialDepartmentLedger(financialDepartmentRepositoryById.get().getId(),
                        financialLedgerTypeRepositoryById.get().getId());
            }
            if (!hasInFinancialDepartmentLedger) {
                throw new RuleException("این نوع دفتر مالی، برای این شعبه، قبلا ثبت شده است.");
            }
            if (financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId() == null) {
                Optional<FinancialDepartmentLedger> financialDepartmentLedgerRepositoryById = financialDepartmentLedgerRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId());
                updateDeleteDateAndInsertFinancialDepLedger(financialDepartmentLedgerRepositoryById, financialLedgerTypeRepositoryById, financialDepartmentLedgerRequestListId);
            } else {
                Long financialDepartmentLedger = financialDepartmentLedgerRepository.getFinancialDepartmentLedger(
                        financialDepartmentLedgerRequestListId.getFinancialDepartmentId()
                        , financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId());
                if (financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId() != null
                        && financialDepartmentLedger == null && financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId() == null) {
                    insertFinancialDepartmentLedger(financialLedgerTypeRepositoryById, financialDepartmentLedgerRequestListId);
                } else if (financialDepartmentLedger == null && financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId() != null) {
                    Optional<FinancialDepartmentLedger> financialDepartmentLedgerRepositoryById = financialDepartmentLedgerRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId());
                    updateFinancialDepartmentLedger(financialDepartmentLedgerRepositoryById, financialDepartmentLedgerRequestListId, financialLedgerTypeRepositoryById);
                }
            }
        }
        return true;
    }

    private void insertFinancialDepartmentLedger(Optional<FinancialLedgerType> financialLedgerTypeRepositoryById,
                                                 FinancialDepartmentLedgerRequest financialDepartmentLedgerRequestListId) {
        FinancialDepartmentLedger financialDepartmentLedgerNew = new FinancialDepartmentLedger();
        Optional<FinancialDepartment> financialDepartmentRepositoryById = financialDepartmentRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentId());
        if (financialDepartmentRepositoryById.isPresent()) {
            financialDepartmentLedgerNew.setFinancialDepartment(financialDepartmentRepositoryById.get());
        } else {
            throw new RuleException("شناسه شعبه، وارد شده معتبر نمی باشد.");
        }
        if (financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId() == null) {
            financialDepartmentLedgerNew.setFinancialLedgerType(null);
            saveNewFinancialDepartmentLedger(financialDepartmentRepositoryById, financialLedgerTypeRepositoryById, financialDepartmentLedgerNew);
            return;
        }
        if (financialLedgerTypeRepositoryById.isPresent()) {
            financialDepartmentLedgerNew.setFinancialLedgerType(financialLedgerTypeRepositoryById.get());
        } else {
            throw new RuleException("شناسه نوع دفتر مالی، وارد شده معتبر نمی باشد.");
        }
        saveNewFinancialDepartmentLedger(financialDepartmentRepositoryById, financialLedgerTypeRepositoryById, financialDepartmentLedgerNew);
    }

    private void saveNewFinancialDepartmentLedger(Optional<FinancialDepartment> financialDepartmentRepositoryById,
                                                  Optional<FinancialLedgerType> financialLedgerTypeRepositoryById,
                                                  FinancialDepartmentLedger financialDepartmentLedgerNew) {
        boolean hasInFinancialDepartmentLedger = true;
        if (financialDepartmentLedgerNew.getFinancialLedgerType() == null) {
            hasInFinancialDepartmentLedger = checkWhenFinancialLedgerIsNull(financialDepartmentRepositoryById.get().getId());
        } else {
            hasInFinancialDepartmentLedger = checkFinancialDepartmentLedger(financialDepartmentRepositoryById.get().getId(),
                    financialLedgerTypeRepositoryById.get().getId());
        }
        if (hasInFinancialDepartmentLedger) {
            financialDepartmentLedgerRepository.save(financialDepartmentLedgerNew);
        } else {
            throw new RuleException("این نوع دفتر مالی، برای این شعبه، قبلا ثبت شده است.");
        }
    }

    private void updateFinancialDepartmentLedger(Optional<FinancialDepartmentLedger> financialDepartmentLedgerRepositoryById, FinancialDepartmentLedgerRequest financialDepartmentLedgerRequestListId, Optional<FinancialLedgerType> financialLedgerTypeRepositoryById) {
        if (financialDepartmentLedgerRepositoryById.isPresent()
                && financialDepartmentLedgerRepositoryById.get().getId().equals(financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId())) {
            FinancialDepartmentLedger financialDepartmentLedgerForUpdate = financialDepartmentLedgerRepositoryById.get();
            Optional<FinancialDepartment> financialDepartmentRepositoryById = financialDepartmentRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentId());
            if (financialLedgerTypeRepositoryById.isPresent()) {
                Boolean hasInDocument = checkDocument(financialDepartmentRepositoryById.get().getId(), financialLedgerTypeRepositoryById.get().getId());
                if (hasInDocument) {
                    financialDepartmentLedgerForUpdate.setFinancialLedgerType(financialLedgerTypeRepositoryById.get());
                    boolean hasInFinancialDepartmentLedger = checkFinancialDepartmentLedger(financialDepartmentLedgerRepositoryById.get().getId(), financialLedgerTypeRepositoryById.get().getId());
                    if (hasInFinancialDepartmentLedger) {
                        financialDepartmentLedgerRepository.save(financialDepartmentLedgerForUpdate);
                    } else {
                        throw new RuleException("این نوع دفتر مالی، برای این شعبه، قبلا ثبت شده است.");
                    }
                } else {
                    throw new RuleException(" شعبه و نوع دفترمالی در سندی ثبت شده است امکان ویرایش آن وجود ندارد ");
                }
            } else {
                throw new RuleException("شناسه نوع دفتر مالی، وارد شده معتبر نمی باشد.");
            }
        } else {
            throw new RuleException("شناسه دفتر مالی شعبه، وارد شده معتبر نمی باشد.");
        }
    }

    private void updateDeleteDateAndInsertFinancialDepLedger(Optional<FinancialDepartmentLedger> financialDepartmentLedgerRepositoryById, Optional<FinancialLedgerType> financialLedgerTypeRepositoryById, FinancialDepartmentLedgerRequest financialDepartmentLedgerRequestListId) {
        if (financialDepartmentLedgerRepositoryById.isPresent()) {
            FinancialDepartmentLedger financialDepartmentLedgerForUpdate = financialDepartmentLedgerRepositoryById.get();
            if (financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId().equals(financialDepartmentLedgerForUpdate.getId())) {
                financialDepartmentLedgerForUpdate.setDeletedDate(LocalDateTime.now());
                financialDepartmentLedgerRepository.save(financialDepartmentLedgerForUpdate);
                insertFinancialDepartmentLedger(financialLedgerTypeRepositoryById, financialDepartmentLedgerRequestListId);
            }
        } else {
            throw new RuleException("شناسه دفتر مالی شعبه، وارد شده معتبر نمی باشد.");
        }
    }

    private Boolean checkDocument(Long financialDepartmentId, Long financialLedgerTypeId) {
        Long countByLedgerTypeIdAndDepartmentIdAndDeleteDate =
                financialDocumentRepository.getCountByLedgerTypeIdAndDepartmentIdAndDeleteDate(financialDepartmentId, financialLedgerTypeId);
        return countByLedgerTypeIdAndDepartmentIdAndDeleteDate == 0 ? true : false;
    }

    private Boolean checkFinancialDepartmentLedger(Long financialDepartmentId, Long financialLedgerTypeId) {
        Long countByLedgerTypeIdAndDepartmentIdAndDeleteDate = financialDepartmentLedgerRepository.getCountByLedgerTypeIdAndDepartmentIdAndDeleteDate(financialDepartmentId,
                financialLedgerTypeId);
        return countByLedgerTypeIdAndDepartmentIdAndDeleteDate == 0 ? true : false;

    }

    private Boolean checkWhenFinancialLedgerIsNull(Long financialDepartment) {
        Long countByLedgerTypeIdAndDepartmentIdAndDeleteDate = financialDepartmentLedgerRepository.getCountByIsNullLedgerTypeIdAndDepartmentIdAndDeleteDate
                (financialDepartment, null);
        return countByLedgerTypeIdAndDepartmentIdAndDeleteDate == 0 ? true : false;

    }
}