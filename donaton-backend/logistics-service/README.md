# Logistics Service - Manual de Instrucciones

## Descripción General
El **Logistics Service** es un microservicio especializado en la gestión de inventario y logística de donaciones en la plataforma Donaton. Maneja el almacenamiento, seguimiento y distribución de artículos donados.

**Arquitectura**: Spring Boot 3.1.5 con Spring Cloud Netflix Eureka
**Lenguaje**: Java 17
**Base de Datos**: H2 (en memoria) / MySQL (producción)
**Gestión de Dependencias**: Maven

## Requisitos Previos
- **Java Development Kit (JDK)**: v17 o superior
- **Apache Maven**: v3.8.0 o superior
- **Git**: para clonar el repositorio
- **Docker** (opcional, para ejecutar en contenedores)
- **Eureka Server**: debe estar ejecutándose en puerto 8761

## Instalación

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd donaton-backend/logistics-service
```

### 2. Verificar Requisitos
```bash
java -version
mvn -version
```

### 3. Compilar el Proyecto
```bash
mvn clean package
```

Esto genera el JAR en `target/logistics-service-0.0.1-SNAPSHOT.jar`

## Estructura del Proyecto
```
logistics-service/
├── pom.xml                      # Configuración Maven
├── Dockerfile                   # Configuración Docker
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/donaton/logistics/
│   │   │       ├── LogisticsApplication.java       # Clase principal
│   │   │       ├── controller/
│   │   │       │   └── LogisticsController.java    # Controlador REST
│   │   │       ├── model/
│   │   │       │   └── Inventory.java              # Modelo de inventario
│   │   │       └── repository/
│   │   │           └── InventoryRepository.java    # Interfaz JPA
│   │   └── resources/
│   │       └── application.yml                     # Configuración
│   └── test/
│       └── java/
│           └── com/donaton/logistics/
│               └── LogisticsControllerTest.java    # Tests unitarios
├── README.md                    # Este archivo
└── target/                      # Archivos compilados
```

## Configuración

### application.yml
Edita `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: logistics-service
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
  
  datasource:
    url: jdbc:h2:mem:logisticsdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  
  h2:
    console:
      enabled: true

server:
  port: 8083

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
```

**Parámetros importantes**:
- `server.port`: Puerto del servicio (default: 8083)
- `spring.datasource.url`: URL de base de datos
- `eureka.client.service-url.defaultZone`: URL del servidor Eureka

## Ejecución

### Método 1: Línea de Comandos
```bash
mvn spring-boot:run
```

### Método 2: Ejecutar JAR compilado
```bash
mvn package
java -jar target/logistics-service-0.0.1-SNAPSHOT.jar
```

### Método 3: Docker
```bash
docker build -t donaton-logistics:latest .
docker run -p 8083:8083 --name logistics-service \
  -e EUREKA_URL=http://eureka-server:8761/eureka/ \
  donaton-logistics:latest
```

### Método 4: Docker Compose
```bash
cd ../../
docker-compose up -d logistics-service
```

## Endpoints Principales

### Obtener Inventario Completo
```
GET /api/inventory
Response: 200 OK
Body:
[
  {
    "id": 1,
    "name": "Ropa infantil",
    "quantity": 150,
    "category": "ROPA",
    "dateReceived": "2024-01-15T10:30:00Z"
  },
  ...
]
```

### Obtener Ítem de Inventario
```
GET /api/inventory/{id}
Response: 200 OK
```

### Crear Ítem de Inventario
```
POST /api/inventory
Content-Type: application/json

Body:
{
  "name": "Medicinas",
  "quantity": 200,
  "category": "MEDICINAS"
}

Response: 201 Created
```

### Actualizar Cantidad en Inventario
```
PUT /api/inventory/{id}
Content-Type: application/json

Body:
{
  "quantity": 100
}

Response: 200 OK
```

### Eliminar Ítem de Inventario
```
DELETE /api/inventory/{id}
Response: 204 No Content
```

### Obtener Disponibilidad (Validación de Stock)
```
GET /api/inventory/{id}/available
Response: 200 OK
Body:
{
  "id": 1,
  "available": true,
  "quantity": 150
}
```

## Modelo de Datos - Inventory

```java
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;              // Nombre del ítem
    private Integer quantity;         // Cantidad disponible
    private String category;          // Categoría (ROPA, MEDICINAS, ALIMENTOS, etc.)
    private LocalDateTime dateReceived; // Fecha de recepción
    
    // Getters y setters...
}
```

## Categorías de Inventario

| Código | Nombre | Descripción |
|--------|--------|-------------|
| ROPA | Ropa | Prendas de vestir |
| MEDICINAS | Medicinas | Medicamentos y suplementos |
| ALIMENTOS | Alimentos | Productos alimenticios no perecederos |
| ELECTRONICA | Electrónica | Dispositivos electrónicos |
| OTROS | Otros | Otros artículos |

## Patrones de Diseño

### 1. **Patrón Repository**
**Ubicación**: `com.donaton.logistics.repository.InventoryRepository`

Interfaz JPA que encapsula el acceso a datos:
```java
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByCategory(String category);
    List<Inventory> findByQuantityGreaterThan(Integer quantity);
}
```

**Beneficios**:
- Abstrae la lógica de acceso a datos
- Facilita cambio de base de datos
- Simplifica testing

### 2. **Patrón Entity (Domain-Driven Design)**
Cada ítem de inventario es una entidad con ciclo de vida propio

### 3. **Patrón Service Layer** (puede implementarse)
Agregar capa de servicio para lógica de negocio

## Pruebas

### Ejecutar Todas las Pruebas
```bash
mvn test
```

### Tests Disponibles
- **LogisticsControllerTest**: Tests del controlador REST
- Pruebas CRUD completas
- Pruebas de validación

### Pruebas con curl
```bash
# Listar inventario
curl -X GET http://localhost:8083/api/inventory

# Crear ítem de inventario
curl -X POST http://localhost:8083/api/inventory \
  -H "Content-Type: application/json" \
  -d '{"name":"Ropa","quantity":100,"category":"ROPA"}'

# Obtener ítem específico
curl -X GET http://localhost:8083/api/inventory/1

# Actualizar cantidad
curl -X PUT http://localhost:8083/api/inventory/1 \
  -H "Content-Type: application/json" \
  -d '{"quantity":50}'

# Verificar disponibilidad
curl -X GET http://localhost:8083/api/inventory/1/available

# Eliminar ítem
curl -X DELETE http://localhost:8083/api/inventory/1
```

## Dependencias Principales

| Dependencia | Versión | Propósito |
|-------------|---------|----------|
| spring-boot-starter-web | 3.1.5 | REST API |
| spring-boot-starter-data-jpa | 3.1.5 | Acceso a datos |
| h2 | 2.1.214 | Base de datos en memoria |
| spring-cloud-starter-netflix-eureka-client | 2022.0.4 | Service Discovery |
| spring-boot-starter-test | 3.1.5 | Testing |

## Configuración de Base de Datos

### Usar H2 (Desarrollo)
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:logisticsdb
  h2:
    console:
      enabled: true
```

Acceder a: `http://localhost:8083/h2-console`

### Usar MySQL (Producción)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/donaton_logistics
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

Agregar en `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
</dependency>
```

## Integración con BFF

El BFF puede consumir este servicio:
```java
String logisticsUrl = "http://logistics-service/api/inventory";
ResponseEntity<List> inventory = restTemplate.getForEntity(logisticsUrl, List.class);
```

## Resolución de Problemas

### No se registra en Eureka
1. Verificar Eureka Server: `http://localhost:8761`
2. Revisar logs de conexión
3. Ajustar timeout

### Puerto 8083 en uso
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8084"
```

### Base de datos no inicializa
```bash
mvn clean package
java -jar target/logistics-service-*.jar
```

### H2 Console no accesible
Verificar que está habilitada en `application.yml`

## Monitoreo y Logs

### Ver logs en tiempo real
```bash
mvn spring-boot:run | tail -f
```

### Configurar nivel de logs
```yaml
logging:
  level:
    root: INFO
    com.donaton: DEBUG
    org.springframework.web: INFO
```

## Despliegue en Producción

### Requisitos
- Java 17 instalado
- MySQL accesible
- Eureka Server en línea
- Configuración segura

### Pasos
1. Compilar: `mvn clean package -DskipTests`
2. Transferir: `scp target/logistics-service-*.jar user@server:/app/`
3. Configurar `application.yml`
4. Ejecutar: `java -jar logistics-service-*.jar`

## Documentación Adicional

- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring REST Docs](https://spring.io/projects/spring-restdocs)
- [Microservices Architecture](https://martinfowler.com/microservices.html)

## Contacto y Soporte

Para reportar issues:
1. Crear issue en repositorio
2. Describir el problema
3. Incluir logs

## Licencia

Parte de la plataforma Donaton.
