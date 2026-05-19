# Donation Service - Manual de Instrucciones

## Descripción General
El **Donation Service** es un microservicio especializado en la gestión de donaciones de la plataforma Donaton. Maneja dos tipos de donaciones: **Donaciones Individuales** y **Donaciones Corporativas**, aplicando patrones de diseño avanzados como Factory, Strategy y Adapter.

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
cd donaton-backend/donation-service
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

## Estructura del Proyecto
```
donation-service/
├── pom.xml                      # Configuración Maven
├── Dockerfile                   # Configuración Docker
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/donaton/donation/
│   │   │       ├── DonationServiceApplication.java  # Clase principal
│   │   │       ├── adapter/
│   │   │       │   └── DonationAdapter.java        # Patrón Adapter
│   │   │       ├── config/
│   │   │       │   └── AppConfig.java              # Configuración
│   │   │       ├── controller/
│   │   │       │   └── DonationController.java     # Controlador REST
│   │   │       ├── dto/
│   │   │       │   └── DonationDTO.java            # DTO estándar
│   │   │       ├── model/
│   │   │       │   ├── Donation.java               # Clase base
│   │   │       │   ├── IndividualDonation.java     # Donación individual
│   │   │       │   └── CorporateDonation.java      # Donación corporativa
│   │   │       ├── repository/
│   │   │       │   ├── DonationRepository.java     # Interfaz JPA
│   │   │       │   └── factory/
│   │   │       │       └── DonationFactory.java    # Patrón Factory
│   │   │       └── strategy/
│   │   │           ├── DonationStrategy.java       # Patrón Strategy
│   │   │           ├── IndividualDonationStrategy.java
│   │   │           └── CorporateDonationStrategy.java
│   │   └── resources/
│   │       └── application.yml                     # Configuración
│   └── test/
│       └── java/
│           └── com/donaton/donation/
│               ├── DonationControllerTest.java     # Tests controller
│               ├── adapter/
│               │   └── DonationAdapterTest.java    # Tests adapter
│               └── factory/
│                   └── DonationFactoryTest.java    # Tests factory
├── README.md                    # Este archivo
└── target/                      # Archivos compilados
```

## Configuración

### application.yml
Edita `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: donation-service
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
  
  datasource:
    url: jdbc:h2:mem:donationdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  
  h2:
    console:
      enabled: true

server:
  port: 8081

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
```

**Parámetros importantes**:
- `server.port`: Puerto del servicio (default: 8081)
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
java -jar target/donation-service-0.0.1-SNAPSHOT.jar
```

### Método 3: Docker
```bash
docker build -t donaton-donation:latest .
docker run -p 8081:8081 --name donation-service \
  -e EUREKA_URL=http://eureka-server:8761/eureka/ \
  donaton-donation:latest
```

### Método 4: Docker Compose
```bash
cd ../../
docker-compose up -d donation-service
```

## Endpoints Principales

### Obtener Todas las Donaciones
```
GET /api/donations
Response: 200 OK
Body: 
[
  {
    "id": 1,
    "type": "INDIVIDUAL",
    "description": "Donación de ropa",
    "donorIdentifier": "12.345.678-9",
    "donorName": "Ciudadano"
  },
  ...
]
```

### Obtener Donación por ID
```
GET /api/donations/{id}
Response: 200 OK
```

### Crear Nueva Donación
```
POST /api/donations
Content-Type: application/json

Body:
{
  "type": "INDIVIDUAL",
  "description": "Donación de medicinas",
  "citizenRut": "12.345.678-9"
}

Response: 201 Created
```

### Actualizar Donación
```
PUT /api/donations/{id}
Content-Type: application/json

Body:
{
  "type": "CORPORATE",
  "description": "Donación actualizada",
  "companyName": "Tech Corp S.A."
}

Response: 200 OK
```

### Eliminar Donación
```
DELETE /api/donations/{id}
Response: 204 No Content
```

## Patrones de Diseño Implementados

### 1. **Patrón Factory**
**Ubicación**: `com.donaton.donation.repository.factory.DonationFactory`

Crea instancias apropiadas de donaciones según su tipo:
```java
Donation donation = DonationFactory.createDonation("INDIVIDUAL");
```

**Beneficios**:
- Centraliza la creación de objetos
- Facilita agregar nuevos tipos de donaciones
- Encapsula la lógica de instanciación

**Tests**: `DonationFactoryTest.java`

### 2. **Patrón Strategy**
**Ubicación**: `com.donaton.donation.strategy.DonationStrategy`

Define algoritmos intercambiables para procesar diferentes tipos de donaciones:
```java
public interface DonationStrategy {
    String processDonation();
}

// Implementaciones
public class IndividualDonationStrategy implements DonationStrategy {
    @Override
    public String processDonation() {
        // Lógica específica para donaciones individuales
    }
}
```

**Beneficios**:
- Separa algoritmos de procesamiento
- Permite seleccionar algoritmo en tiempo de ejecución
- Facilita testing y mantenimiento

### 3. **Patrón Adapter**
**Ubicación**: `com.donaton.donation.adapter.DonationAdapter`

Convierte diferentes tipos de donaciones a un formato DTO estándar:
```java
DonationDTO dto = DonationAdapter.adaptToDonationDTO(donation);
```

**Beneficios**:
- Normaliza la interfaz de donaciones
- Desacopla modelos internos de la API
- Facilita cambios futuros en la estructura interna

**Tests**: `DonationAdapterTest.java`

## Pruebas

### Ejecutar Todas las Pruebas
```bash
mvn test
```

### Pruebas Disponibles
- **DonationControllerTest**: Tests del controlador REST
- **DonationFactoryTest**: Tests del patrón Factory
- **DonationAdapterTest**: Tests del patrón Adapter

### Cobertura de Tests
```bash
mvn test jacoco:report
# Reporte en: target/site/jacoco/index.html
```

### Pruebas con curl
```bash
# Listar donaciones
curl -X GET http://localhost:8081/api/donations

# Crear donación individual
curl -X POST http://localhost:8081/api/donations \
  -H "Content-Type: application/json" \
  -d '{"type":"INDIVIDUAL","description":"Test","citizenRut":"12.345.678-9"}'

# Crear donación corporativa
curl -X POST http://localhost:8081/api/donations \
  -H "Content-Type: application/json" \
  -d '{"type":"CORPORATE","description":"Test","companyName":"Test Corp"}'

# Obtener donación específica
curl -X GET http://localhost:8081/api/donations/1

# Actualizar donación
curl -X PUT http://localhost:8081/api/donations/1 \
  -H "Content-Type: application/json" \
  -d '{"description":"Actualizada"}'

# Eliminar donación
curl -X DELETE http://localhost:8081/api/donations/1
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
Ya está configurado en `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:donationdb
```

Acceder a H2 Console: `http://localhost:8081/h2-console`

### Cambiar a MySQL (Producción)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/donaton_db
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      dialect: org.hibernate.dialect.MySQL8Dialect
      ddl-auto: update
```

Agregue dependencia MySQL en `pom.xml`:
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
String donationServiceUrl = "http://donation-service/api/donations";
ResponseEntity<List> donations = restTemplate.getForEntity(donationServiceUrl, List.class);
```

## Resolución de Problemas

### No se registra en Eureka
1. Verificar que Eureka Server está en línea: `http://localhost:8761`
2. Revisar logs para errores de conexión
3. Aumentar timeout en `application.yml`

### Error de puerto en uso
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8082"
```

### Error en persistencia de datos
```bash
# Ver logs detallados
mvn spring-boot:run -Dspring-boot.run.arguments="--logging.level.org.hibernate=DEBUG"
```

### H2 Console no accesible
Verificar que está habilitada en `application.yml`:
```yaml
spring:
  h2:
    console:
      enabled: true
```

## Despliegue en Producción

### Requisitos
- Java 17 instalado
- MySQL accesible
- Eureka Server accesible
- Configuración segura de base de datos

### Pasos
1. Compilar: `mvn clean package -DskipTests`
2. Transferir JAR: `scp target/donation-service-*.jar user@server:/app/`
3. Configurar `application.yml` para producción
4. Ejecutar: `java -jar donation-service-*.jar`

## Documentación Adicional

- [JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Design Patterns in Java](https://refactoring.guru/design-patterns/java)
- [Spring Security](https://spring.io/projects/spring-security)

## Contacto y Soporte

Para reportar issues:
1. Crear issue en el repositorio
2. Describir el problema
3. Incluir logs: `target/spring.log`

## Licencia

Parte de la plataforma Donaton.
