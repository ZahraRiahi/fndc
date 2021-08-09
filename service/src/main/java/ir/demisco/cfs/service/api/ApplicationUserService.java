package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.ApplicationUserDto;

import java.util.List;

public interface ApplicationUserService {

    List<ApplicationUserDto> getUserList();
}
