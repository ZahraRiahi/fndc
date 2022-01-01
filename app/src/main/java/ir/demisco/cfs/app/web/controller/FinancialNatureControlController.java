package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.service.api.ControlFinancialAccountNatureTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api-financialNature")
public class FinancialNatureControlController {
    private final ControlFinancialAccountNatureTypeService controlFinancialAccountNatureTypeService;

    public FinancialNatureControlController(ControlFinancialAccountNatureTypeService controlFinancialAccountNatureTypeService) {
        this.controlFinancialAccountNatureTypeService = controlFinancialAccountNatureTypeService;
    }

//    @PostMapping("/Get")
//    public ResponseEntity<List<ControlFinancialAccountNatureTypeOutputResponse>> responseEntity(@RequestBody ControlFinancialAccountNatureTypeRequest controlFinancialAccountNatureTypeRequest) {
//        return ResponseEntity.ok(controlFinancialAccountNatureTypeService.getControlFinancialAccountNatureType(controlFinancialAccountNatureTypeRequest));
//    }
}
