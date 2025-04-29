# Spring Cloud Microservicios

Este proyecto es el resultado del curso **"Microservicios con Spring Boot y Spring Cloud"** impartido por el profesor **Andrés Guzmán** en Udemy.  
Implementa una arquitectura de microservicios utilizando herramientas modernas del ecosistema Spring, permitiendo la creación de servicios distribuidos, escalables y seguros.

## Tecnologías y herramientas utilizadas

### Backend
- Java 17
- Spring Boot 3
- Spring Cloud
  - Eureka Server (Service Discovery)
  - Spring Cloud Config (Centralización de configuración)
  - Spring Cloud Gateway (API Gateway)
  - Spring Security (Autenticación y autorización)
  - Spring Cloud Sleuth y Zipkin (Trazabilidad de servicios)
- OAuth2 (Autenticación)
- Feign (Comunicación entre microservicios)
- Lombok (Reducción de código boilerplate)

### Base de datos
- MySQL

### Contenedores y orquestación
- Docker
- Docker Compose

### Observabilidad
- Zipkin (Trazabilidad de solicitudes)

## Estructura del proyecto

El proyecto está compuesto por los siguientes módulos:

- `config-server`: Servidor de configuración centralizada.
- `discovery-server`: Servidor Eureka para el descubrimiento de servicios.
- `gateway-server`: API Gateway que enruta las solicitudes a los microservicios correspondientes.
- `oauth-service`: Servicio de autenticación y autorización utilizando OAuth2.
- `users-service`: Microservicio para la gestión de usuarios.
- `products-service`: Microservicio para la gestión de productos.
- `items-service`: Microservicio para la gestión de ítems.
- `libs-commons-service`: Librería común utilizada por los microservicios.
- `zipkin`: Servicio para la trazabilidad de solicitudes entre microservicios.

## Configuración y ejecución

### Prerrequisitos
- Java 17 instalado.
- Docker y Docker Compose instalados.
- MySQL en ejecución (puedes utilizar Docker para esto).

### Pasos para la ejecución

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/ExeCarrizo33/spring-cloud-msvc.git
   cd spring-cloud-msvc
