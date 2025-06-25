# Servidor de descubrimiento EUREKA-SERVER
## Dependencias
* Eureka Server
* Spring Web
* DevTools
 ## Configuracion del properties.yml
```
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
  server:
    enable-self-preservation: false
```

## Habilitar el servicio de Eureka
```java

@SpringBootApplication
@EnableEurekaServer
public class MsEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsEurekaServerApplication.class, args);
    }

}
```

# Api Gateway
## Dependencias
* Spring Boot Starter Web
* Spring Cloud Gateway
* Eureka Client
* Circuit Breaker (Resilience4j)
* Actuator para monitoreo
## Configurar properties.yml
```
server:
  port: 8080

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: servicio-publicaciones
          uri: lb://SERVICIO-1
          predicates:
            - Path=/api/servicio1/**
          filters:
            - StripPrefix=2
        - id: servicio2
          uri: lb://SERVICIO-2
          predicates:
            - Path=/api/servicio2/**
          filters:
            - StripPrefix=2
        - id: servicio3
          uri: lb://SERVICIO-3
          predicates:
            - Path=/api/servicio3/**
          filters:
            - StripPrefix=2

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    fetch-registry: true
    register-with-eureka: true
  instance:
    prefer-ip-address: true

management:
  endpoints:
    web:
      exposure:
        include: health,info,gateway
```

## Incluir los modulos
### ms-publicaciones 
### Version de cloud
```
<properties>
    <java.version>21</java.version>
    <spring-cloud.version>2025.0.0</spring-cloud.version>
</properties>
```
#### Incluir dependecia de eureka

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
#### Incluir dependecia de eureka
Incluir el dependencyManagement para Spring Cloud:
```
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

```
