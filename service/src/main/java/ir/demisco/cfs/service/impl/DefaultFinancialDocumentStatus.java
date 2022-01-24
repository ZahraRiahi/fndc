package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialDocumentStatusListDto;
import ir.demisco.cfs.service.api.FinancialDocumentStatusService;
import ir.demisco.cfs.service.repository.FinancialDocumentStatusRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialDocumentStatus implements FinancialDocumentStatusService {

    private final FinancialDocumentStatusRepository documentStatusRepository;

    public DefaultFinancialDocumentStatus(FinancialDocumentStatusRepository documentStatusRepository) {
        this.documentStatusRepository = documentStatusRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
        public List<FinancialDocumentStatusListDto> getStatusList() {
        return documentStatusRepository.getFinancialDocumentStatusList().stream().map(documentStatus -> FinancialDocumentStatusListDto.builder()
        .id(documentStatus.getId())
         .Code(documentStatus.getCode())
        .name(documentStatus.getName())
        .build()).collect(Collectors.toList());
    }
}
