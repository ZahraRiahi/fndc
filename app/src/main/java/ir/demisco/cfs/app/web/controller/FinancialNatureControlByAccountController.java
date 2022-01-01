package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.ControlFinancialAccountNatureTypeByAccountRequest;
import ir.demisco.cfs.model.dto.response.ControlFinancialAccountNatureTypeByAccountResponse;
import ir.demisco.cfs.service.api.ControlFinancialAccountNatureTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api-financialNatureControlByAccount")
public class FinancialNatureControlByAccountController {
    private final ControlFinancialAccountNatureTypeService controlFinancialAccountNatureTypeService;

    public FinancialNatureControlByAccountController(ControlFinancialAccountNatureTypeService controlFinancialAccountNatureTypeService) {
        this.controlFinancialAccountNatureTypeService = controlFinancialAccountNatureTypeService;
    }

    @PostMapping("/Get")
    public ResponseEntity<List<ControlFinancialAccountNatureTypeByAccountResponse>> responseEntity(@RequestBody ControlFinancialAccountNatureTypeByAccountRequest controlFinancialAccountNatureTypeByAccountRequest) {
        return ResponseEntity.ok(controlFinancialAccountNatureTypeService.getControlFinancialAccountNatureType(controlFinancialAccountNatureTypeByAccountRequest));
    }
}
