package com.exe.spring_cloud.msvc.gateway_server.filters;

import jakarta.servlet.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SampleGlobalFilter implements Filter, Ordered {


    @Override
    public int getOrder() {
        return 100;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        filterChain.doFilter(servletRequest, servletResponse);
    }
}


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseCookie;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * SampleGlobalFilter es un filtro global para el Spring Cloud Gateway.
// * Registra la ejecución de los filtros pre y post, y añade una cookie y un tipo de contenido a la respuesta.
// */
//
//@Component
//public class SampleGlobalFilter implements GlobalFilter, Ordered {
//
//    private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);
//
//    /**
//     * Filtra la solicitud y la respuesta, registrando la ejecución y modificando la respuesta.
//     *
//     * @param exchange el intercambio del servidor actual
//     * @param chain    la cadena de filtros del gateway
//     * @return un Mono que indica la finalización del filtro
//     */
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        // Registra un mensaje indicando la ejecución del filtro pre
//        logger.info("Ejecutando el filtro global pre");
//
//        // Modifica el intercambio para añadir un encabezado personalizado "token" con el valor "abcdefg"
//        ServerWebExchange mutatedExchange = exchange.mutate()
//                .request(exchange.getRequest().mutate().headers(h -> h.add("token", "abcdefg")).build())
//                .build();
//
//        // Continúa la cadena de filtros y añade una acción post-filtro
//        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//            // Registra un mensaje indicando la ejecución del filtro post
//            logger.info("Ejecutando el filtro global post");
//
//            // Recupera el encabezado "token" del intercambio modificado
//            String token = mutatedExchange.getRequest().getHeaders().getFirst("token");
//            if (token != null) {
//                // Registra el valor del token si existe
//                logger.info("token: " + token);
//            }
//
//            // Añade una cookie llamada "color" con el valor "red" a la respuesta
//            exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
//            // Establece el tipo de contenido de la respuesta a texto plano
//            exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
//        }));
//    }
//
//    @Override
//    public int getOrder() {
//        return 100;
//    }
//}
