package ir.demisco.cfs.config;

import ir.demisco.cloud.core.security.model.DmsUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class TestConfig {

    @Bean
    public UserDetailsService inMemoryDBUserDetailService() {
        DmsUserDetails userDetails = new DmsUserDetails();
        userDetails.setUsername("test");
        userDetails.setUserId(1000L);
        userDetails.setOrganizationId(1L);
        return new InMemoryUserDetailsManager(userDetails);
    }

}
