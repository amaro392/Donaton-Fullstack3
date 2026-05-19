# BFF Service - Donaton

Backend For Frontend que actúa como punto de entrada único para el frontend, delegando las solicitudes a los microservicios correspondientes mediante Spring Cloud Gateway.

## Requisitos
- Java 17
- Maven 3.8+
- Eureka Server corriendo en puerto 8761
- Donation Service disponible (microservicio de donaciones)
- Logistics Service disponible (microservicio de logística)

## Cómo ejecutar

```bash
mvn spring-boot:run
```

El servicio queda disponible en: `http://localhost:8080`

## Endpoints disponibles

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /api/donations | Obtiene todas las donaciones (proxy a donation-service) |
| GET | /api/donations/{id} | Obtiene una donación específica |
| POST | /api/donations | Crea una nueva donación |
| DELETE | /api/donations/{id} | Elimina una donación |
| GET | /api/logistics/inventory | Lista el inventario (proxy a logistics-service) |
| POST | /api/logistics/inventory | Agrega ítem al inventario |

## Patrones de Diseño Aplicados

### BFF (Backend For Frontend) Pattern
**Ubicación:** `com.donaton.bff.controller.BffController`

**Propósito:** Servir como punto de entrada único para el frontend, ocultando la complejidad de múltiples microservicios detrás de una única interfaz.

**Justificación:**
- **Abstracción:** El frontend no necesita conocer la dirección ni estructura de cada microservicio
- **Ruta centralizada:** Todas las peticiones pasan por un único punto, facilitando logging, seguridad y rate limiting
- **Evolución independiente:** Los microservicios pueden cambiar internamente sin afectar al frontend
- **Escalabilidad:** Permite agregar nuevos microservicios sin modificar el código del frontend

**Ventajas en Donaton:**
- El frontend realiza peticiones a `http://localhost:8080`, sin conocer que detrás hay múltiples servicios
- Spring Cloud Gateway gestiona automáticamente el enrutamiento a través de Eureka
- Facilita la implementación de middleware compartido (autenticación, validación, etc.)

### API Gateway Pattern
**Ubicación:** `application.yml` - Spring Cloud Gateway configuration

**Propósito:** Gestionar el enrutamiento de peticiones HTTP hacia los microservicios adecuados.

**Justificación:**
- Reduce el acoplamiento entre frontend y microservicios
- Centraliza configuraciones de rutas y filtros
- Proporciona load balancing automático mediante Eureka

## Arquitectura

```
┌─────────────────┐
│   Frontend      │
│   (React)       │
└────────┬────────┘
         │ HTTP
         │
┌────────▼────────┐
│  BFF Service    │
│  (Gateway)      │ ← Este servicio
└────────┬────────┘
         │
    ┌────┴─────┐
    │           │
    ▼           ▼
┌─────────┐  ┌──────────┐
│Donation │  │Logistics │
│Service  │  │Service   │
└─────────┘  └──────────┘
    │           │
    └─────┬─────┘
          │
      ┌───▼───┐
      │MySQL  │
      │DBs    │
      └───────┘
```

## Dependencias Principales

- **Spring Boot Web:** REST API support
- **Spring Cloud Gateway:** API Gateway con enrutamiento inteligente
- **Spring Cloud Eureka Client:** Descubrimiento automático de servicios
- **RestTemplate:** Cliente HTTP para llamadas sincrónicas a otros servicios

## Configuración de Rutas

Las rutas se definen en `application.yml`:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: donation-service-route
          uri: lb://donation-service  # Load balance a donation-service
          predicates:
            - Path=/api/donations/**  # Captura peticiones a /api/donations/*
```

La ruta utiliza `lb://` (load balancer) que se resuelve automáticamente mediante Eureka.

## Notas de Desarrollo

- El servicio se registra automáticamente en Eureka con el nombre `bff-service`
- Los tiempos de respuesta dependen de la disponibilidad de los microservicios
- Los errores de los microservicios se propagan hacia el frontend
- La configuración de rutas permite agregar filters y transformaciones si es necesario