package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.ControlFinancialAccountNatureTypeInputRequest;
import ir.demisco.cfs.model.dto.response.ControlFinancialAccountNatureTypeOutputResponse;
import ir.demisco.cfs.service.api.ControlFinancialAccountNatureTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api-financialNatureControl")
public class FinancialNatureControlController {
    private final ControlFinancialAccountNatureTypeService controlFinancialAccountNatureTypeService;

    public FinancialNatureControlController(ControlFinancialAccountNatureTypeService controlFinancialAccountNatureTypeService) {
        this.controlFinancialAccountNatureTypeService = controlFinancialAccountNatureTypeService;
    }

    @PostMapping("/Get")
    public ResponseEntity<List<ControlFinancialAccountNatureTypeOutputResponse>> responseEntity(@RequestBody ControlFinancialAccountNatureTypeInputRequest controlFinancialAccountNatureTypeInputRequest) {
        return ResponseEntity.ok(controlFinancialAccountNatureTypeService.getControlFinancialAccountNatureType(controlFinancialAccountNatureTypeInputRequest));
    }
}
