package com.exe.spring_cloud.msvc.items_service.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

   /**
         * Configura un bean de `WebClient` con una URL base y una función de filtro de balanceador de carga.
         *
         * @param builder El `WebClient.Builder` es utilizado para construir la instancia de `WebClient`.
         * @param lbFunction La función `ReactorLoadBalancerExchangeFilterFunction` utilizada para habilitar el balanceo de carga.
         * @return Una instancia configurada de `WebClient`.
         */
        @Bean(name = "customWebClient")
        WebClient webClient(WebClient.Builder builder, ReactorLoadBalancerExchangeFilterFunction lbFunction) {

            return builder.filter(lbFunction).build();
        }

//    @Bean
//    @LoadBalanced
//    WebClient.Builder webClientBuilder() {
//        return WebClient.builder();
//    }
}
