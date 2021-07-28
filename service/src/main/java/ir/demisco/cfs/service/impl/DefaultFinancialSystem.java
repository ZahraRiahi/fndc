package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialSystemDto;
import ir.demisco.cfs.model.entity.FinancialSystem;
import ir.demisco.cfs.service.api.FinancialSystemService;
import ir.demisco.cfs.service.repository.FinancialSystemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialSystem implements FinancialSystemService {
    private final FinancialSystemRepository financialSystemRepository;

    public DefaultFinancialSystem(FinancialSystemRepository financialSystemRepository) {
        this.financialSystemRepository = financialSystemRepository;
    }

    @Override
    @Transactional
    public List<FinancialSystemDto> getFinancialSystem() {
        List<FinancialSystem> financialSystem = financialSystemRepository.findByFinancialSystem();
        return financialSystem.stream().map(e -> FinancialSystemDto.builder().id(e.getId())
                .description(e.getDescription())
                .build()).collect(Collectors.toList());

    }
}
