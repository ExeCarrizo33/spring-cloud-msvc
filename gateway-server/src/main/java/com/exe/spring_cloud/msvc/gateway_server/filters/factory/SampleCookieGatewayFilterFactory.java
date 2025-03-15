package com.exe.spring_cloud.msvc.gateway_server.filters.factory;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SampleCookieGatewayFilterFactory extends AbstractGatewayFilterFactory<SampleCookieGatewayFilterFactory.ConfigCookie> {

    public SampleCookieGatewayFilterFactory() {
        super(ConfigCookie.class);
    }

    @Override
    public GatewayFilter apply(ConfigCookie config) {
        return ((exchange, chain) -> {

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

            }));

        });
    }

    @Data
    public static class ConfigCookie{
        private String name;
        private String value;
        private String message;

    }

}
