# Arquetipo Maven para Microservicios Donaton - Guía Completa

## Descripción General

Este arquetipo Maven define la **estructura base y dependencias estándar** para crear nuevos microservicios dentro del ecosistema Donaton. Proporciona una plantilla reutilizable que asegura consistencia, calidad y mejores prácticas en todos los microservicios del proyecto.

**Incluye**:
- ✅ Spring Boot 3.2.0
- ✅ Spring Data JPA (acceso a datos)
- ✅ MySQL Connector (base de datos)
- ✅ Lombok (reducción de código boilerplate)
- ✅ Spring Cloud Eureka Client (descubrimiento de servicios)
- ✅ Spring Boot Test (testing)

## Estructura del Arquetipo

```
microservice-archetype/
├── pom.xml                        # Configuración Maven del arquetipo
├── README.md                      # Este archivo (guía de uso)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── ${package}/
│   │   │   │   └── ${classNamePrefix}Application.java
│   │   │   ├── ${package}/config/
│   │   │   │   └── AppConfiguration.java
│   │   │   ├── ${package}/controller/
│   │   │   │   └── ${classNamePrefix}Controller.java
│   │   │   ├── ${package}/dto/
│   │   │   │   └── ${classNamePrefix}DTO.java
│   │   │   ├── ${package}/model/
│   │   │   │   └── ${classNamePrefix}Entity.java
│   │   │   ├── ${package}/repository/
│   │   │   │   └── ${classNamePrefix}Repository.java
│   │   │   └── ${package}/service/
│   │   │       └── ${classNamePrefix}Service.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/
│           ├── ${package}/
│           │   └── ${classNamePrefix}ApplicationTests.java
│           ├── ${package}/controller/
│           │   └── ${classNamePrefix}ControllerTest.java
│           └── ${package}/service/
│               └── ${classNamePrefix}ServiceTest.java
└── archetype-resources/           # Templates del arquetipo
    └── [estructura anterior]
```

## Requisitos Previos

Antes de usar el arquetipo, asegúrate de tener:

- **Java Development Kit (JDK)**: v17 o superior
- **Apache Maven**: v3.8.0 o superior
- **Git**: para control de versiones
- **IDE**: IntelliJ IDEA o Eclipse
- **Docker** (opcional, para testing con contenedores)

Verifica las versiones:
```bash
java -version
mvn -version
```

## Instalación del Arquetipo

### Opción 1: Instalar Localmente (Recomendado)

1. Navega a la carpeta del arquetipo:
```bash
cd maven-archetypes/microservice-archetype
```

2. Instala el arquetipo en tu repositorio local Maven:
```bash
mvn clean install
```

Esto registra el arquetipo en `~/.m2/repository/` permitiendo usarlo en cualquier proyecto.

### Opción 2: Compilar sin Instalar (Testing)

```bash
mvn clean package
```

## Uso Básico: Generar Nuevo Microservicio

### Método 1: Comando Directo (Recomendado)

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.donaton.archetype \
  -DarchetypeArtifactId=microservice-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.donaton.miservicio \
  -DartifactId=mi-service \
  -DclassNamePrefix=MiServicio \
  -DinteractiveMode=false
```

**Parámetros**:
- `archetypeGroupId`: Identificador del arquetipo (fijo)
- `archetypeArtifactId`: Nombre del arquetipo (fijo)
- `archetypeVersion`: Versión del arquetipo (1.0.0-SNAPSHOT)
- `groupId`: Identificador del grupo (ej: com.donaton.inventory)
- `artifactId`: Nombre del proyecto (ej: inventory-service)
- `classNamePrefix`: Prefijo para clases (ej: Inventory)

### Método 2: Modo Interactivo

```bash
mvn archetype:generate -DarchetypeGroupId=com.donaton.archetype \
  -DarchetypeArtifactId=microservice-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT
```

Maven pedirá los parámetros interactivamente.

### Método 3: Usando Maven Properties File

Crea `archetype.properties`:
```properties
groupId=com.donaton.notifications
artifactId=notifications-service
classNamePrefix=Notification
```

Luego:
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.donaton.archetype \
  -DarchetypeArtifactId=microservice-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  @archetype.properties
```

## Ejemplo Práctico: Crear "Notifications Service"

### Paso 1: Generar desde Arquetipo

```bash
cd donaton-backend  # Ir a carpeta backend

mvn archetype:generate \
  -DarchetypeGroupId=com.donaton.archetype \
  -DarchetypeArtifactId=microservice-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.donaton.notifications \
  -DartifactId=notifications-service \
  -DclassNamePrefix=Notification \
  -DinteractiveMode=false
```

### Paso 2: Verificar Estructura Generada

```
notifications-service/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/donaton/notifications/
    │   │   ├── NotificationApplication.java
    │   │   ├── config/AppConfiguration.java
    │   │   ├── controller/NotificationController.java
    │   │   ├── dto/NotificationDTO.java
    │   │   ├── model/NotificationEntity.java
    │   │   ├── repository/NotificationRepository.java
    │   │   └── service/NotificationService.java
    │   └── resources/application.yml
    └── test/...
```

### Paso 3: Personalizar el Servicio

1. **Actualizar `pom.xml`** con dependencias específicas:
```xml
<!-- Agregar si necesitas envío de emails -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

<!-- Agregar si necesitas RabbitMQ o Kafka -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

2. **Implementar la lógica de negocio** en el controlador y servicio

3. **Agregar tests** en la carpeta test

4. **Configurar `application.yml`**:
```yaml
spring:
  application:
    name: notifications-service
  datasource:
    url: jdbc:mysql://localhost:3306/notifications_db
    username: root
    password: password

server:
  port: 8084

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

### Paso 4: Compilar y Ejecutar

```bash
cd notifications-service
mvn clean package
java -jar target/notifications-service-0.0.1-SNAPSHOT.jar
```

## Variables del Arquetipo (Placeholders)

El arquetipo reemplaza automáticamente estas variables:

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `${groupId}` | Identificador del grupo | com.donaton.inventory |
| `${artifactId}` | Nombre del proyecto | inventory-service |
| `${classNamePrefix}` | Prefijo para clases | Inventory |
| `${package}` | Paquete raíz | com.donaton.inventory |

## Personalización Avanzada

### Agregar Nuevas Funcionalidades

Después de generar, puedes agregar:

1. **Patrón Strategy**:
```java
// Crear carpeta strategy
src/main/java/com/donaton/miservicio/strategy/MiStrategy.java
```

2. **Patrón Factory**:
```java
// Crear carpeta factory
src/main/java/com/donaton/miservicio/factory/MiFactory.java
```

3. **Patrón Adapter**:
```java
// Crear carpeta adapter
src/main/java/com/donaton/miservicio/adapter/MiAdapter.java
```

4. **Configuración de Seguridad**:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Configuración de seguridad
}
```

### Agregar Dependencias Comunes

Según el tipo de microservicio, puedes agregar:

**Para acceso a datos avanzado**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

**Para logging avanzado**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```

**Para cache distribuido**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

**Para mensajería**:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-kafka</artifactId>
</dependency>
```

## Estructura de Carpetas Generada (Explicación)

```
┌─ pom.xml                  → Configuración Maven y dependencias
├─ src/main/java/
│  ├─ ${package}/
│  │  ├─ *Application.java     → Clase principal de Spring Boot
│  │  ├─ config/               → Configuración de beans y contexto
│  │  ├─ controller/           → Controladores REST
│  │  ├─ dto/                  → Data Transfer Objects
│  │  ├─ model/                → Entidades JPA
│  │  ├─ repository/           → Acceso a datos
│  │  └─ service/              → Lógica de negocio
│  └─ resources/
│     └─ application.yml       → Propiedades de aplicación
└─ src/test/java/
   ├─ *ApplicationTests.java   → Tests de integración
   ├─ controller/              → Tests de controladores
   └─ service/                 → Tests de servicios
```

## Buenas Prácticas

### 1. Denominación Consistente
```bash
# ✅ BIEN
artifactId: payment-service
classNamePrefix: Payment
groupId: com.donaton.payment

# ❌ MAL
artifactId: PaymentService
classNamePrefix: paymentservice
groupId: com.Payment
```

### 2. Versionado Semántico
```bash
-DarchetypeVersion=1.0.0-SNAPSHOT  # Desarrollo
-DarchetypeVersion=1.0.0           # Release
-DarchetypeVersion=2.0.0           # Major cambios
```

### 3. Configuración por Ambiente

En `application.yml`:
```yaml
---
spring:
  config:
    activate:
      on-profile: dev
  datasource:
    url: jdbc:mysql://localhost:3306/db_dev

---
spring:
  config:
    activate:
      on-profile: prod
  datasource:
    url: jdbc:mysql://prod-db-server:3306/db_prod
```

Ejecutar: `java -jar app.jar --spring.profiles.active=prod`

### 4. Testing Completo

Agregar a `pom.xml`:
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>
```

## Integración con Docker

Después de generar, agrega `Dockerfile` en la raíz del proyecto:

```dockerfile
FROM openjdk:17-slim
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Construir imagen:
```bash
mvn clean package
docker build -t donaton-mi-servicio:latest .
docker run -d -p 8080:8080 --name mi-servicio donaton-mi-servicio:latest
```

## Troubleshooting

### Arquetipo no encontrado
```bash
# Solución 1: Instalar nuevamente
cd maven-archetypes/microservice-archetype
mvn clean install

# Solución 2: Verificar caché
rm -rf ~/.m2/repository/com/donaton/archetype
mvn clean install
```

### Error de permiso
```bash
chmod +x mvn  # En Linux/Mac si es necesario
```

### Puerto en uso
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

## Actualizar el Arquetipo

Si deseas mejorar el arquetipo base:

1. Modifica `microservice-archetype/src/main/resources/archetype-resources/`
2. Actualiza versión en `pom.xml`
3. Reinstala: `mvn clean install`

## Checklist para Nuevo Microservicio

- [ ] Generar desde arquetipo
- [ ] Actualizar `pom.xml` con dependencias específicas
- [ ] Configurar `application.yml`
- [ ] Implementar controladores
- [ ] Implementar servicios
- [ ] Agregar tests unitarios
- [ ] Agregar tests de integración
- [ ] Documentar en README.md
- [ ] Crear Dockerfile
- [ ] Registrar en docker-compose.yml
- [ ] Registrar en Eureka

## Documentación de Referencia

- [Maven Archetype Plugin](https://maven.apache.org/archetype/maven-archetype-plugin/)
- [Spring Boot Starters](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Project Structure Best Practices](https://spring.io/guides/gs/spring-boot/)

## Ejemplos Adicionales

### Crear Account Service
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.donaton.archetype \
  -DarchetypeArtifactId=microservice-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.donaton.accounts \
  -DartifactId=accounts-service \
  -DclassNamePrefix=Account \
  -DinteractiveMode=false
```

### Crear Reporting Service
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.donaton.archetype \
  -DarchetypeArtifactId=microservice-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.donaton.reporting \
  -DartifactId=reporting-service \
  -DclassNamePrefix=Report \
  -DinteractiveMode=false
```

## Soporte y Contribuciones

Para reportar problemas o sugerir mejoras:

1. Crear issue en el repositorio
2. Describir el problema
3. Adjuntar logs si es posible
4. Sugerir solución si tienes

## Licencia

Este arquetipo es parte del proyecto Donaton y sigue la licencia principal del proyecto.
