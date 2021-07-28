package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialSystemDto;

import java.util.List;

public interface FinancialSystemService {
    List<FinancialSystemDto> getFinancialSystem();
}
