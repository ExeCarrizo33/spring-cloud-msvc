package com.exe.spring_cloud.msvc.gateway_server.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * SampleGlobalFilter es un filtro global para el Spring Cloud Gateway.
 * Registra la ejecución de los filtros pre y post, y añade una cookie y un tipo de contenido a la respuesta.
 */

@Component
public class SampleGlobalFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);

    /**
     * Filtra la solicitud y la respuesta, registrando la ejecución y modificando la respuesta.
     *
     * @param exchange el intercambio del servidor actual
     * @param chain    la cadena de filtros del gateway
     * @return un Mono que indica la finalización del filtro
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        logger.info("Ejecutando el filtro global pre");

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(exchange.getRequest().mutate().headers(h -> h.add("token", "abcdefg")).build())
                .build();

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("Ejecutando el filtro global post");
            String token = mutatedExchange.getRequest().getHeaders().getFirst("token");
            if (token != null) {
                logger.info("token: " + token);
            }


            exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
            exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
