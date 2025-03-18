package com.exe.spring_cloud.msvc.gateway_server.filters.factory;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Component
public class SampleCookieGatewayFilterFactory extends AbstractGatewayFilterFactory<SampleCookieGatewayFilterFactory.ConfigCookie> {

    private final Logger logger = LoggerFactory.getLogger(SampleCookieGatewayFilterFactory.class);

    public SampleCookieGatewayFilterFactory() {
        super(ConfigCookie.class);
    }

    @Override
    public GatewayFilter apply(ConfigCookie config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            logger.info("Ejecutando el pre gateway filter factory: " + config.message);

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                Optional.ofNullable(config.value).ifPresent(cookie -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.name, cookie).build());
                });
                logger.info("Ejecutando el post gateway filter factory: " + config.message);
            }));

        }, 100);
    }

    @Data
    public static class ConfigCookie {
        private String name;
        private String value;
        private String message;

    }

}
