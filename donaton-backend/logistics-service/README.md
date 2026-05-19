# Logistics Service - Donaton

Microservicio encargado de gestionar el inventario de donaciones físicas (logística) de manera independiente y escalable.

## Requisitos
- Java 17
- Maven 3.8+
- MySQL corriendo en puerto 3306 (base de datos: `donaton_logistics_db`)
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
| GET | /api/logistics/inventory/{id} | Obtiene un ítem específico |
| POST | /api/logistics/inventory | Agrega un ítem al inventario |
| PUT | /api/logistics/inventory/{id} | Actualiza un ítem |
| DELETE | /api/logistics/inventory/{id} | Elimina un ítem |

## Patrones de Diseño Aplicados

### Microservices Architecture Pattern
**Ubicación:** Toda la estructura de `logistics-service`

**Propósito:** Encapsular la lógica de inventario en un servicio independiente y autónomo.

**Justificación:**
- **Independencia:** Tiene su propia base de datos (`donaton_logistics_db`) sin compartir estado con otros servicios
- **Escalabilidad:** Se puede desplegar, escalar y actualizar independientemente
- **Responsabilidad única:** Gestiona exclusivamente la logística de donaciones
- **Resiliencia:** Si el servicio falla, el resto del sistema continúa funcionando

### Repository Pattern
**Ubicación:** `com.donaton.logistics.repository.InventoryRepository`

**Propósito:** Abstrae el acceso a datos mediante Spring Data JPA.

**Justificación:**
- Separa la lógica de negocio de la persistencia
- Facilita testing con repositorios mock
- Proporciona operaciones CRUD automáticas
- Permite cambiar la implementación sin afectar el resto del código

## Arquitectura de Base de Datos

El servicio usa JPA con MySQL. Se crea automáticamente mediante Hibernate.

Tablas principales:
- `inventory` - Tabla de items de inventario
  - `id` (PK)
  - `description` 
  - `quantity`
  - `created_at`
  - `updated_at`

## Dependencias Principales

- **Spring Boot Web:** REST API support
- **Spring Data JPA:** Persistencia de datos
- **MySQL Connector:** Driver para base de datos
- **Spring Cloud Eureka Client:** Registro en service discovery
- **Spring Boot Test:** Testing framework (JUnit 5, Mockito)

## Integración con el Ecosistema

```
┌─────────────────────┐
│   Frontend (React)  │
└──────────┬──────────┘
           │
        ┌──▼───┐
        │ BFF  │
        └──┬───┘
           │
    ┌──────┴────────┐
    │               │
    ▼               ▼
┌─────────────┐  ┌──────────────────┐
│   Donation  │  │ Logistics        │ ← Este servicio
│   Service   │  │ Service          │
└────┬────────┘  └────┬─────────────┘
     │                │
     ▼                ▼
┌─────────────────────────────┐
│   Eureka Service Registry   │
└─────────────────────────────┘
```

## Notas de Desarrollo

- El servicio se registra automáticamente en Eureka con el nombre `logistics-service`
- La base de datos se crea automáticamente si no existe
- Los logs están configurados en DEBUG para el paquete `com.donaton`
- El timeout de Eureka está configurado para detectar desconexiones en ~30 segundos