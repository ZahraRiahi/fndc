package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.ApplicationUserDto;
import ir.demisco.cfs.service.api.ApplicationUserService;
import ir.demisco.cfs.service.repository.ApplicationUserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultApplicationUser  implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;

    public DefaultApplicationUser(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<ApplicationUserDto> getUserList() {
        return applicationUserRepository.getUserList().stream().map(objects -> ApplicationUserDto.builder()
                .id(Long.parseLong(objects[0].toString()))
                .userName(objects[1].toString())
                .nickName(objects[2].toString())
                .build()
        ).collect(Collectors.toList());
    }
}
