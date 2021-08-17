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


    public DefaultFinancialDepartmentLedger(FinancialDepartmentLedgerRepository financialDepartmentLedgerRepository, FinancialDepartmentRepository financialDepartmentRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository, FinancialDocumentRepository financialDocumentRepository) {
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
            if (financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId() == null) {
                Optional<FinancialDepartmentLedger> financialDepartmentLedgerRepositoryById = financialDepartmentLedgerRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId());
                Optional<FinancialLedgerType> financialLedgerTypeRepositoryById = null;
                if (financialLedgerTypeIdRequest != null) {
                    financialLedgerTypeRepositoryById = financialLedgerTypeRepository.findById(financialLedgerTypeIdRequest);
                }
                updateAndInsertFinancialDepLedger(financialDepartmentLedgerRepositoryById, financialLedgerTypeRepositoryById, financialDepartmentLedgerRequestListId);
                return true;
            }
            Long financialDepartmentLedger = financialDepartmentLedgerRepository.getFinancialDepartmentLedger(
                    financialDepartmentLedgerRequestListId.getFinancialDepartmentId()
                    , financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId());
            if (financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId() != null
                    && financialDepartmentLedger == null && financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId() == null) {
                if (financialLedgerTypeIdRequest != null) {
                    Optional<FinancialLedgerType> financialLedgerTypeRepositoryById = financialLedgerTypeRepository.findById(financialLedgerTypeIdRequest);
                    insertFinancialDepartmentLedger(financialLedgerTypeRepositoryById, financialDepartmentLedgerRequestListId);
                    return true;
                }
            } else if (financialDepartmentLedger == null && financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId() != null) {
                Optional<FinancialDepartmentLedger> financialDepartmentLedgerRepositoryById = financialDepartmentLedgerRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId());
                if (financialLedgerTypeIdRequest != null) {
                    Optional<FinancialLedgerType> financialLedgerTypeRepositoryById = financialLedgerTypeRepository.findById(financialLedgerTypeIdRequest);
                    updateFinancialDepartmentLedger(financialDepartmentLedgerRepositoryById, financialDepartmentLedgerRequestListId, financialLedgerTypeRepositoryById);
                    return true;
                }
            }
//            else if (financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId() == null) {
//                Optional<FinancialDepartmentLedger> financialDepartmentLedgerRepositoryById = financialDepartmentLedgerRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId());
//                if (financialLedgerTypeIdRequest != null) {
//                    Optional<FinancialLedgerType> financialLedgerTypeRepositoryById = financialLedgerTypeRepository.findById(financialLedgerTypeIdRequest);
//                    updateAndInsertFinancialDepLedger(financialDepartmentLedgerRepositoryById, financialLedgerTypeRepositoryById, financialDepartmentLedgerRequestListId);
//                    return true;
//                }
//            }
        }
        return true;
    }

    private boolean insertFinancialDepartmentLedger(Optional<FinancialLedgerType> financialLedgerTypeRepositoryById, FinancialDepartmentLedgerRequest financialDepartmentLedgerRequestListId) {
        FinancialDepartmentLedger financialDepartmentLedgerNew = new FinancialDepartmentLedger();
        Optional<FinancialDepartment> financialDepartmentRepositoryById = financialDepartmentRepository.findById(financialDepartmentLedgerRequestListId.getFinancialDepartmentId());
        if (financialDepartmentRepositoryById.isPresent()) {
            financialDepartmentLedgerNew.setFinancialDepartment(financialDepartmentRepositoryById.get());
        } else {
            throw new RuleException("شناسه شعبه، وارد شده معتبر نمی باشد.");
        }
        if (financialDepartmentLedgerRequestListId.getFinancialLedgerTypeId() == null) {
            financialDepartmentLedgerNew.setFinancialLedgerType(null);
            boolean hasInFinancialDepartmentLedger = checkFinancialDepartmentLedgerIsNull(financialDepartmentRepositoryById.get().getId(), null);
            if (hasInFinancialDepartmentLedger) {
                financialDepartmentLedgerRepository.save(financialDepartmentLedgerNew);
            } else {
                throw new RuleException("این نوع دفتر مالی، برای این شعبه، قبلا ثبت شده است.");
            }
            return true;
        }
        if (financialLedgerTypeRepositoryById.isPresent()) {
            financialDepartmentLedgerNew.setFinancialLedgerType(financialLedgerTypeRepositoryById.get());
        } else {
            throw new RuleException("شناسه نوع دفتر مالی، وارد شده معتبر نمی باشد.");
        }
        boolean hasInFinancialDepartmentLedger = checkFinancialDepartmentLedger(financialDepartmentRepositoryById.get().getId(), financialLedgerTypeRepositoryById.get().getId());
        if (hasInFinancialDepartmentLedger) {
            financialDepartmentLedgerRepository.save(financialDepartmentLedgerNew);
        } else {
            throw new RuleException("این نوع دفتر مالی، برای این شعبه، قبلا ثبت شده است.");
        }
        return true;
    }

    private boolean updateFinancialDepartmentLedger(Optional<FinancialDepartmentLedger> financialDepartmentLedgerRepositoryById, FinancialDepartmentLedgerRequest financialDepartmentLedgerRequestListId, Optional<FinancialLedgerType> financialLedgerTypeRepositoryById) {
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
        return true;
    }

    private boolean updateAndInsertFinancialDepLedger(Optional<FinancialDepartmentLedger> financialDepartmentLedgerRepositoryById, Optional<FinancialLedgerType> financialLedgerTypeRepositoryById, FinancialDepartmentLedgerRequest financialDepartmentLedgerRequestListId) {
        if (financialDepartmentLedgerRepositoryById.isPresent()) {
            FinancialDepartmentLedger financialDepartmentLedgerForUpdate = financialDepartmentLedgerRepositoryById.get();
            if (financialDepartmentLedgerRequestListId.getFinancialDepartmentLedgerId().equals(financialDepartmentLedgerForUpdate.getId())) {
                financialDepartmentLedgerForUpdate.setDeletedDate(LocalDateTime.now());
                financialDepartmentLedgerRepository.save(financialDepartmentLedgerForUpdate);
                insertFinancialDepartmentLedger(financialLedgerTypeRepositoryById, financialDepartmentLedgerRequestListId);
                return true;
            }
        } else {
            throw new RuleException("شناسه دفتر مالی شعبه، وارد شده معتبر نمی باشد.");
        }
        return true;
    }

    private Boolean checkDocument(Long financialDepartmentId, Long financialLedgerTypeId) {
        Long countByLedgerTypeIdAndDepartmentIdAndDeleteDate =
                financialDocumentRepository.getCountByLedgerTypeIdAndDepartmentIdAndDeleteDate(financialDepartmentId, financialLedgerTypeId);
        return countByLedgerTypeIdAndDepartmentIdAndDeleteDate == 0 ? true : false;
    }

    private boolean checkFinancialDepartmentLedger(Long financialDepartmentId, Long financialLedgerTypeId) {
        Long countByLedgerTypeIdAndDepartmentIdAndDeleteDate = financialDepartmentLedgerRepository.getCountByLedgerTypeIdAndDepartmentIdAndDeleteDate(financialDepartmentId,
                financialLedgerTypeId);
        return countByLedgerTypeIdAndDepartmentIdAndDeleteDate == 0 ? true : false;

    }  private boolean checkFinancialDepartmentLedgerIsNull(Long financialDepartmentId, Long financialLedgerTypeId) {
        Long countByLedgerTypeIdAndDepartmentIdAndDeleteDate = financialDepartmentLedgerRepository.getCountByIsNullLedgerTypeIdAndDepartmentIdAndDeleteDate(financialDepartmentId,
                financialLedgerTypeId);
        return countByLedgerTypeIdAndDepartmentIdAndDeleteDate == 0 ? true : false;

    }
}
