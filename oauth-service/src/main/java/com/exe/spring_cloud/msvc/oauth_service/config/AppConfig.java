package com.exe.spring_cloud.msvc.oauth_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {


    @Bean(name = "customWebClient")
    WebClient webClient(WebClient.Builder builder, ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        return builder.baseUrl("http://users-service").filter(lbFunction).build();
    }


//    @Bean
//    @LoadBalanced
//    WebClient.Builder webClient() {
//        return WebClient.builder().baseUrl("http://users-service");
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
