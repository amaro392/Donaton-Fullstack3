# BFF Service - Donaton

Backend For Frontend que actúa como punto de entrada único para el frontend, delegando las solicitudes a los microservicios correspondientes.

## Requisitos
- Java 17
- Maven 3.8+
- Eureka Server corriendo en puerto 8761

## Cómo ejecutar

```bash
mvn spring-boot:run
```

El servicio queda disponible en: `http://localhost:8080`

## Endpoints disponibles

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /api/bff/donations | Obtiene todas las donaciones desde el donation-service |

## Patrón aplicado
**BFF (Backend For Frontend):** este servicio centraliza las llamadas del frontend evitando que este conozca la dirección de cada microservicio. Usa `RestTemplate` como cliente HTTP.