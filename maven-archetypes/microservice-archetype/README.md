# Arquetipo de Microservicio - Donaton

## Descripción

Este arquetipo Maven define la estructura base y las dependencias estándar para cualquier nuevo microservicio dentro del ecosistema Donaton. Proporciona una base sólida y consistente para el desarrollo de microservicios Spring Boot.

## Estructura Generada

El arquetipo genera la siguiente estructura para un nuevo microservicio:

```
nuevo-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/donaton/nuevoservicio/
│   │   │       ├── NuevoServiceApplication.java
│   │   │       ├── config/
│   │   │       │   └── AppConfig.java
│   │   │       ├── controller/
│   │   │       │   └── NuevoServiceController.java
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── logback.xml
│   └── test/
│       ├── java/
│       └── resources/
├── pom.xml
└── README.md
```

## Dependencias Incluidas

- **Spring Boot Starter Web:** REST API support
- **Spring Boot Starter Data JPA:** ORM y persistencia de datos
- **MySQL Connector:** Driver para base de datos MySQL
- **Spring Cloud Eureka Client:** Service discovery
- **Spring Boot Starter Test:** JUnit 5, Mockito para testing
- **Lombok:** Generación automática de getters/setters

## Instrucciones de Uso

### 1. Generar Nuevo Microservicio

Desde la carpeta raíz del backend, ejecuta:

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.donaton.archetype \
  -DarchetypeArtifactId=microservice-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.donaton.nuevoservicio \
  -DartifactId=nuevo-service \
  -DinteractiveMode=false
```

**Parámetros:**
- `groupId`: Identificador del grupo (ej: com.donaton.nombreservicio)
- `artifactId`: Nombre del proyecto Maven (ej: nombre-service)

### 2. Configurar el Nuevo Servicio

Después de generar el proyecto:

1. **Actualizar application.yml:**
   ```yaml
   server:
     port: 8084  # Puerto único para el servicio
   
   spring:
     application:
       name: nuevo-service  # Nombre para Eureka
     datasource:
       url: jdbc:mysql://localhost:3306/donaton_nuevo_db
       username: root
       password: root
   
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:8761/eureka/
   ```

2. **Crear modelos de datos** en `model/`
3. **Crear repositorios** extendiendiendo `JpaRepository`
4. **Implementar controladores** en `controller/`
5. **Agregar tests unitarios** en `src/test/`

### 3. Compilar y Ejecutar

```bash
# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run

# Ejecutar tests
mvn test

# Generar reporte de cobertura
mvn jacoco:report
```

## Patrones Aplicados

El arquetipo fomenta el uso de:

1. **Repository Pattern:** Abstracción de datos mediante JpaRepository
2. **Dependency Injection:** Configuración de beans mediante @Configuration
3. **Service Discovery:** Registro automático en Eureka
4. **Test-Driven Development:** Incluye estructura de tests

## Configuración de Eureka

Todos los microservicios generados se registran automáticamente en Eureka con el nombre especificado en `spring.application.name`. Esto permite que otros servicios y el BFF los descubran dinámicamente.

## Notas Importantes

- Cada microservicio debe tener su **propia base de datos MySQL**
- El nombre del servicio en `application.yml` debe ser **único** en el ecosistema
- Los **puertos** deben ser diferentes para cada servicio (8082, 8083, 8084, etc.)
- Incluir **tests unitarios** desde el inicio del desarrollo
- Mantener la estructura de paquetes consistente con el arquetipo

## Extensiones Futuras

Para agregar nuevas dependencias o modificar el arquetipo:

1. Editar el archivo `pom.xml` en el arquetipo
2. Ejecutar `mvn install` para actualizar el arquetipo localmente
3. Usar la nueva versión al generar nuevos microservicios