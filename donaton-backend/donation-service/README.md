# Donation Service - Donaton

Microservicio encargado de gestionar todas las donaciones (individuales y corporativas) con lógica de negocio específica.

## Requisitos
- Java 17
- Maven 3.8+
- MySQL corriendo en puerto 3306 (base de datos: `donaton_donation_db`)
- Eureka Server corriendo en puerto 8761

## Cómo ejecutar

```bash
mvn spring-boot:run
```

El servicio queda disponible en: `http://localhost:8082`

## Endpoints disponibles

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /api/donations | Obtiene todas las donaciones |
| GET | /api/donations/{id} | Obtiene una donación específica |
| POST | /api/donations | Crea una nueva donación |
| DELETE | /api/donations/{id} | Elimina una donación |

## Patrones de Diseño Aplicados

### 1. Factory Pattern
Ubicación: `com.donaton.donation.repository.factory.DonationFactory`

**Propósito:** Crear instancias de donaciones basadas en el tipo (INDIVIDUAL o CORPORATE).

**Justificación:** Encapsula la lógica de creación de objetos complejos, permitiendo que el cliente no necesite conocer las clases concretas. Facilita la extensión si se agregan nuevos tipos de donaciones.

```java
Donation donation = DonationFactory.createDonation("INDIVIDUAL");
```

### 2. Strategy Pattern
Ubicación: `com.donaton.donation.strategy.*`

**Propósito:** Permitir diferentes estrategias de procesamiento según el tipo de donación.

**Justificación:** Separa el algoritmo de procesamiento de donaciones del resto del código, facilitando el mantenimiento y permitiendo cambiar la estrategia en tiempo de ejecución sin modificar el código existente.

Implementaciones:
- `IndividualDonationStrategy`: Procesa donaciones de individuos
- `CorporateDonationStrategy`: Procesa donaciones de corporaciones

### 3. Template Method Pattern (Implícito)
Ubicación: `com.donaton.donation.model.Donation` (clase abstracta)

**Propósito:** Define la estructura común de todas las donaciones mediante herencia.

**Justificación:** Asegura que todas las donaciones tengan propiedades comunes (id, description) mientras permite que subclases especializadas agreguen comportamiento específico.

## Arquitectura de Base de Datos

El servicio usa JPA con MySQL. Se crea automáticamente la tabla mediante Hibernate con `ddl-auto: update`.

Tablas principales:
- `donation` - Tabla única con SINGLE_TABLE inheritance strategy
  - `id` (PK)
  - `description`
  - `dtype` (discriminator para INDIVIDUAL vs CORPORATE)

## Dependencias Principales

- **Spring Boot Web:** REST API support
- **Spring Data JPA:** Persistencia de datos
- **MySQL Connector:** Driver para base de datos
- **Spring Cloud Eureka Client:** Registro en service discovery
- **JUnit 5 & Mockito:** Testing framework

## Notas de Desarrollo

- El servicio se registra automáticamente en Eureka con el nombre `donation-service`
- La base de datos se crea automáticamente si no existe
- Los logs están configurados en DEBUG para el paquete `com.donaton`
