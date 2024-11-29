package com.apiproject.ServiceBookingSystem;

import com.apiproject.ServiceBookingSystem.configs.RateLimitingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class  ServiceBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceBookingSystemApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilterBean() {
        FilterRegistrationBean<RateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimitingFilter());
        registrationBean.addUrlPatterns("/*"); // Register filter for API endpoints
        return registrationBean;
    }
}
