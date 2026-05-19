# Logistics Service - Donaton

Microservicio encargado de gestionar el inventario de donaciones físicas (logística).

## Requisitos
- Java 17
- Maven 3.8+
- MySQL corriendo en puerto 3306 (base de datos: `donaton_db`)
- Eureka Server corriendo en puerto 8761

## Cómo ejecutar

```bash
mvn spring-boot:run
```

El servicio queda disponible en: `http://localhost:8083`

## Endpoints disponibles

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /api/logistics/inventory | Lista todo el inventario |
| POST | /api/logistics/inventory | Agrega un ítem al inventario |

## Patrón aplicado
**Arquitectura de Microservicios:** servicio independiente con su propia base de datos, registrado en Eureka para descubrimiento de servicios.