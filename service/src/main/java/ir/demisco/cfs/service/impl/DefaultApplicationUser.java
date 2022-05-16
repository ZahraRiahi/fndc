package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialApplicationUserRequest;
import ir.demisco.cfs.model.dto.response.ApplicationUserDto;
import ir.demisco.cfs.service.api.ApplicationUserService;
import ir.demisco.cfs.service.repository.ApplicationUserRepository;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultApplicationUser implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;

    public DefaultApplicationUser(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    @Transactional
    public DataSourceResult getUserList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialApplicationUserRequest paramSearch = setParameterToDto(filters);
        Map<String, Object> paramMap = paramSearch.getParamMap();
        List<Object[]> list = applicationUserRepository.getUserList(paramSearch.getNickName());
        List<ApplicationUserDto> applicationUserList = list.stream().map(objects ->
                ApplicationUserDto.builder()
                        .id(Long.parseLong(objects[0].toString()))
                        .userName(objects[1].toString())
                        .nickName(objects[2].toString())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(applicationUserList.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setTotal(list.size());
        return dataSourceResult;
    }

    private FinancialApplicationUserRequest setParameterToDto(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialApplicationUserRequest financialApplicationUserRequest = new FinancialApplicationUserRequest();
        Map<String, Object> map = new HashMap<>();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "nickName":
                    if (item.getValue() != null) {
                        map.put("fromAccount", "fromAccount");
                        financialApplicationUserRequest.setParamMap(map);
                        financialApplicationUserRequest.setNickName(item.getValue().toString());
                    } else {
                        map.put("fromAccount", null);
                        financialApplicationUserRequest.setParamMap(map);
                        financialApplicationUserRequest.setNickName(null);
                    }
                    break;
            }
        }
        return financialApplicationUserRequest;
    }
}
