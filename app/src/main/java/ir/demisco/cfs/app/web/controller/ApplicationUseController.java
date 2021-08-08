package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.ApplicationUserDto;
import ir.demisco.cfs.service.api.ApplicationUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-ApplicationUser")
public class ApplicationUseController {

    private final ApplicationUserService applicationUserService;

    public ApplicationUseController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/Get")
    public ResponseEntity<List<ApplicationUserDto>> responseEntity(){
        return ResponseEntity.ok(applicationUserService.getUserList());
    }
}
