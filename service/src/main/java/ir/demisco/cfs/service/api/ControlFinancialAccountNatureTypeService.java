package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.ControlFinancialAccountNatureTypeByAccountRequest;
import ir.demisco.cfs.model.dto.response.ControlFinancialAccountNatureTypeByAccountResponse;
import java.util.List;

public interface ControlFinancialAccountNatureTypeService {
    List<ControlFinancialAccountNatureTypeByAccountResponse> getControlFinancialAccountNatureType(ControlFinancialAccountNatureTypeByAccountRequest controlFinancialAccountNatureTypeByAccountRequest);

}
