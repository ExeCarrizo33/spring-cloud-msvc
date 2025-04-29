# 🧩 Microservicios con Spring Boot y Spring Cloud

Este proyecto es el resultado del curso **Microservicios con Spring Boot y Spring Cloud** dictado por el profesor Andrés Guzmán en Udemy. Implementa una arquitectura de microservicios utilizando herramientas modernas del ecosistema Spring, permitiendo la creación de servicios distribuidos, escalables y seguros.

## 🚀 Tecnologías y herramientas utilizadas

### Backend:
- **Java 17**
- **Spring Boot 3**
- **Spring Cloud**
  - Eureka Server (Service Discovery)
  - Spring Cloud Config (Centralización de configuración)
  - Spring Cloud Gateway (API Gateway)
  - OpenFeign (Comunicación entre servicios)
  - Spring Cloud Bus + RabbitMQ (Actualización de configuración en caliente)
- **Spring Security con JWT**
- **Spring Data JPA + Hibernate**
- **Spring Web**
- **Swagger / OpenAPI**
- **Kafka (Integración básica)**
- **Lombok**
- **MySQL / MongoDB**
- **Docker**

## 🧱 Microservicios incluidos

| Servicio | Descripción |
|---------|-------------|
| `eureka-server` | Registro de servicios (Service Discovery) |
| `gateway` | API Gateway que enruta y protege las peticiones |
| `config-server` | Servidor centralizado de configuración |
| `msvc-usuarios` | Gestión de usuarios |
| `msvc-cursos` | Gestión de cursos |
| `msvc-auth` | Servicio de autenticación con JWT |
| `commons` | Librería compartida entre microservicios |

## 🧪 Funcionalidades principales

- Comunicación entre microservicios vía **OpenFeign**
- Gestión de configuración dinámica con **Spring Cloud Config + Bus**
- Seguridad con **JWT y Spring Security**
- Registro y descubrimiento de servicios con **Eureka**
- Documentación de API con **Swagger**
- Manejo de errores y validaciones personalizadas
- Contenerización con **Docker**

## 🗂️ Estructura del proyecto

```bash
spring-cloud-msvc/
│
├── commons/                 # Librerías compartidas (DTOs, modelos)
├── config-server/           # Servidor de configuración central
├── eureka-server/           # Service registry (Eureka)
├── gateway/                 # API Gateway con rutas y seguridad
├── msvc-auth/               # Servicio de autenticación (JWT)
├── msvc-cursos/             # Servicio de cursos
├── msvc-usuarios/           # Servicio de usuarios
├── docker-compose.yml       # Configuración de Docker
└── README.md                # Este archivo
