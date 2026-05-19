# BFF Service - Backend for Frontend - Manual de Instrucciones

## Descripción General
El **Backend for Frontend (BFF)** es la capa intermediaria que proporciona una API unificada para el frontend de Donaton. Actúa como fachada entre la interfaz de usuario y los microservicios especializados (Donation Service y Logistics Service).

**Arquitectura**: Spring Boot 3.1.5 con Spring Cloud Netflix Eureka
**Lenguaje**: Java 17
**Gestión de Dependencias**: Maven

## Requisitos Previos
- **Java Development Kit (JDK)**: v17 o superior
- **Apache Maven**: v3.8.0 o superior
- **Git**: para clonar el repositorio
- **Docker** (opcional, para ejecutar en contenedores)
- **Eureka Server**: debe estar ejecutándose para descubrimiento de servicios

## Instalación

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd donaton-backend/bff-service
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

Esto:
- Limpia compilaciones anteriores (`clean`)
- Descarga dependencias
- Compila el código fuente
- Ejecuta tests
- Genera el JAR en `target/`

## Estructura del Proyecto
```
bff-service/
├── pom.xml                      # Configuración Maven
├── Dockerfile                   # Configuración Docker
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/donaton/bff/
│   │   │       ├── BffApplication.java       # Clase principal
│   │   │       ├── config/
│   │   │       │   └── AppBeans.java         # Configuración de beans
│   │   │       └── controller/
│   │   │           └── BffController.java    # Controlador REST
│   │   └── resources/
│   │       └── application.yml               # Configuración aplicación
│   └── test/
│       └── java/
│           └── com/donaton/bff/
│               └── BffControllerTest.java    # Tests unitarios
├── README.md                    # Este archivo
└── target/                      # Archivos compilados
```

## Configuración

### application.yml
Edita `src/main/resources/application.yml` para configurar:

```yaml
spring:
  application:
    name: bff-service
  
server:
  port: 8080
  
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
```

**Parámetros importantes**:
- `server.port`: Puerto donde ejecuta el BFF (default: 8080)
- `eureka.client.service-url.defaultZone`: URL del servidor Eureka

## Ejecución

### Método 1: Línea de Comandos
```bash
mvn spring-boot:run
```

### Método 2: Ejecutar JAR compilado
```bash
mvn package
java -jar target/bff-service-0.0.1-SNAPSHOT.jar
```

### Método 3: Docker
```bash
docker build -t donaton-bff:latest .
docker run -p 8080:8080 --name bff-service \
  -e EUREKA_URL=http://eureka-server:8761/eureka/ \
  donaton-bff:latest
```

### Método 4: Docker Compose
```bash
cd ../../  # Ir a donaton-backend
docker-compose up -d bff-service
```

## Endpoints Principales

### Health Check
```
GET /actuator/health
Response: 200 OK
```

### Status del BFF
```
GET /api/bff/health
Response: 200 OK
{
  "status": "UP",
  "timestamp": "2024-01-15T10:30:00Z"
}
```

### Status de Servicios
```
GET /api/bff/status
Response: 200 OK
{
  "bff": "UP",
  "donation-service": "UP",
  "logistics-service": "UP"
}
```

### Obtener Donaciones (desde frontend)
```
GET /api/bff/donations
Response: 200 OK
Body: Array de donaciones del Donation Service
```

## Patrones de Diseño

### 1. **Patrón Façade**
- El BFF actúa como fachada simplificando la interfaz de múltiples microservicios
- Oculta la complejidad de la orquestación de servicios

### 2. **Patrón Discovery (Eureka)**
- Descubrimiento automático de servicios registrados
- Balanceo de carga implícito

### 3. **Patrón API Gateway**
- Punto de entrada único para el frontend
- Manejo centralizado de seguridad y enrutamiento

## Pruebas

### Pruebas Unitarias
```bash
mvn test
```

Ejecuta tests en `src/test/java/com/donaton/bff/`

### Pruebas con curl
```bash
# Health check
curl -X GET http://localhost:8080/actuator/health

# Status del BFF
curl -X GET http://localhost:8080/api/bff/health

# Status de todos los servicios
curl -X GET http://localhost:8080/api/bff/status

# Obtener donaciones
curl -X GET http://localhost:8080/api/bff/donations
```

### Pruebas Manuales
1. Iniciar Eureka Server: `docker run -d -p 8761:8761 ...`
2. Iniciar Donation Service
3. Iniciar Logistics Service
4. Iniciar BFF Service
5. Verificar en Eureka Dashboard: `http://localhost:8761`
6. Verificar endpoints del BFF

## Dependencias Principales

| Dependencia | Versión | Propósito |
|-------------|---------|----------|
| spring-boot-starter-web | 3.1.5 | REST API |
| spring-cloud-starter-netflix-eureka-client | 2022.0.4 | Service Discovery |
| spring-boot-starter-test | 3.1.5 | Testing framework |

## Integración con Servicios

### Llamadas a Donation Service
```java
@Autowired
private RestTemplate restTemplate;

public ResponseEntity<?> getDonations() {
    return restTemplate.getForEntity(
        "http://donation-service/api/donations",
        List.class
    );
}
```

### Llamadas a Logistics Service
```java
public ResponseEntity<?> getInventory() {
    return restTemplate.getForEntity(
        "http://logistics-service/api/inventory",
        List.class
    );
}
```

## Resolución de Problemas

### Eureka Client no se conecta
```yaml
# Aumentar timeout en application.yml
eureka:
  client:
    fetch-registry: true
    register-with-eureka: true
    healthcheck:
      enabled: true
```

### Puerto 8080 en uso
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8090"
```

### No encuentra servicios en Eureka
1. Verificar que Eureka Server está ejecutándose (`http://localhost:8761`)
2. Verificar que otros servicios están registrados
3. Verificar la URL en `application.yml`

### Timeout en llamadas a servicios
```java
// Aumentar timeout en RestTemplate
@Bean
public RestTemplate restTemplate() {
    HttpComponentsClientHttpRequestFactory factory = 
        new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(5000);
    factory.setReadTimeout(10000);
    return new RestTemplate(factory);
}
```

## Despliegue en Producción

### Requisitos
- Servidor con Java 17
- Base de datos accesible (si es necesaria)
- Acceso a Eureka Server
- CORS configurado correctamente

### Pasos de Despliegue
1. Compilar: `mvn clean package -DskipTests`
2. Transferir JAR a servidor: `scp target/bff-service-*.jar user@server:/app/`
3. Configurar `application.yml` para producción
4. Ejecutar: `java -jar bff-service-*.jar`

### Configuración Segura
```yaml
server:
  ssl:
    key-store: /path/to/keystore.jks
    key-store-password: ${KEYSTORE_PASSWORD}
  
spring:
  security:
    user:
      name: admin
      password: ${ADMIN_PASSWORD}
```

## Logs

### Ver logs en ejecución
```bash
mvn spring-boot:run | grep -E "ERROR|WARN|INFO"
```

### Configurar nivel de logs
```yaml
logging:
  level:
    root: INFO
    com.donaton: DEBUG
    org.springframework: INFO
```

## Documentación Adicional

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud Netflix Eureka](https://spring.io/projects/spring-cloud-netflix)
- [REST API Best Practices](https://restfulapi.net/)

## Contacto y Soporte

Para reportar issues o sugerencias:
1. Crear un issue en el repositorio
2. Describir el problema y pasos para reproducir
3. Incluir logs de la aplicación

## Licencia

Este proyecto es parte de la plataforma Donaton y sigue la licencia del proyecto principal.
