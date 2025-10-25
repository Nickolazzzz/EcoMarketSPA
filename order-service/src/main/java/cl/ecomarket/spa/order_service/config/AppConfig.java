package cl.ecomarket.spa.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Esta clase es necesaria para poder inyectar RestTemplate en el controlador
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


### Paso 5: Código del API Gateway

Este es el punto de entrada. Solo necesita configuración.

**1. Añadir la dependencia de Spring Cloud Gateway** (si no lo hiciste en start.spring.io):
En tu `pom.xml` de `gateway-service`, asegúrate de tener:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
