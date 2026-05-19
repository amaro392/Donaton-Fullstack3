# Guía de Instalación y Ejecución - Donaton Fullstack

## Tabla de Contenidos
1. [Requisitos Previos](#requisitos-previos)
2. [Instalación](#instalación)
3. [Ejecución Local](#ejecución-local)
4. [Ejecución con Docker](#ejecución-con-docker)
5. [Acceso a la Aplicación](#acceso-a-la-aplicación)
6. [Troubleshooting](#troubleshooting)

## Requisitos Previos

### Desarrollo Local
- Java 17 o superior
- Maven 3.8+
- Node.js 18+ y NPM 9+
- MySQL 8.0+ (instancias locales o vía Docker)

### Con Docker
- Docker 20.10+
- Docker Compose 2.0+

## Instalación

### 1. Clonar Repositorio

```bash
git clone https://github.com/amaro392/Donaton-Fullstack3
cd Donaton-Fullstack3
```

### 2. Backend - Configurar Servicios

Cada microservicio está pre-configurado. Solo es necesario que MySQL esté disponible.

```bash
cd donaton-backend

# Compilar todos los servicios
mvn clean install

# O compilar un servicio específico
cd donation-service
mvn clean install
```

### 3. Frontend - Instalar Dependencias

```bash
cd donaton-frontend
npm install
```

## Ejecución Local

### Opción A: Ejecución Manual

#### 1. Iniciar MySQL (si no está ejecutándose)

```bash
# En Windows (con MySQL instalado)
mysql -u root -p

# O usando Docker
docker run -d \
  --name mysql-donaton \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=donaton_donation_db \
  mysql:8.0
```

#### 2. Iniciar Eureka Server

```bash
cd donaton-backend/eureka-server
mvn spring-boot:run
# Disponible en: http://localhost:8761
```

#### 3. Iniciar Servicios (en terminales separadas)

**Terminal 2 - BFF Service:**
```bash
cd donaton-backend/bff-service
mvn spring-boot:run
# Disponible en: http://localhost:8080
```

**Terminal 3 - Donation Service:**
```bash
cd donaton-backend/donation-service
mvn spring-boot:run
# Disponible en: http://localhost:8082
```

**Terminal 4 - Logistics Service:**
```bash
cd donaton-backend/logistics-service
mvn spring-boot:run
# Disponible en: http://localhost:8083
```

#### 4. Iniciar Frontend

```bash
cd donaton-frontend
npm start
# Disponible en: http://localhost:3000
```

### Orden de Inicio Recomendado

1. MySQL (base de datos)
2. Eureka Server (service registry)
3. BFF Service (gateway)
4. Donation Service (microservicio)
5. Logistics Service (microservicio)
6. Frontend (aplicación web)

Esperar 5-10 segundos entre cada inicio para permitir que los servicios se registren en Eureka.

## Ejecución con Docker

### Opción B: Docker Compose (Recomendado)

La forma más sencilla de ejecutar todos los componentes:

```bash
# Desde la raíz del proyecto
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener
docker-compose down
```

**Servicios disponibles:**
- Frontend: http://localhost:3000
- BFF Gateway: http://localhost:8080
- Eureka Dashboard: http://localhost:8761
- Donation Service API: http://localhost:8082
- Logistics Service API: http://localhost:8083

### Construir Imágenes Localmente (opcional)

```bash
docker-compose build --no-cache
docker-compose up -d
```

## Acceso a la Aplicación

### Frontend React
**URL:** http://localhost:3000

Credenciales de prueba:
- Usuario admin: admin / admin123
- Usuario normal: user / user123

### APIs Backend

#### BFF Gateway (Punto de entrada principal)
- Base URL: http://localhost:8080

**Endpoints:**
- `GET /api/donations` - Listar donaciones
- `POST /api/donations` - Crear donación
- `GET /api/logistics/inventory` - Listar inventario
- `POST /api/logistics/inventory` - Agregar ítem

#### Donation Service
- Base URL: http://localhost:8082
- Docs: http://localhost:8082/swagger-ui.html (si está habilitado)

#### Logistics Service
- Base URL: http://localhost:8083
- Docs: http://localhost:8083/swagger-ui.html (si está habilitado)

### Eureka Service Registry
**URL:** http://localhost:8761

Ver estado de todos los microservicios registrados.

## Ejecutar Tests

### Tests del Backend

```bash
# Ejecutar todos los tests
cd donaton-backend
mvn test

# Tests de un servicio específico
cd donation-service
mvn test

# Con cobertura
mvn test jacoco:report
```

### Tests del Frontend

```bash
cd donaton-frontend
npm test

# Con cobertura
npm test -- --coverage
```

## Troubleshooting

### "Connection refused" en puerto 3306 (MySQL)

**Problema:** MySQL no está ejecutándose

**Soluciones:**
```bash
# Si está instalado localmente (Windows)
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqld.exe"

# O usar Docker
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8.0

# O verificar que Docker Compose no tiene problemas
docker-compose ps
```

### "Cannot connect to Eureka" 

**Problema:** Eureka Server no está disponible

**Solución:**
1. Verificar que Eureka está ejecutándose: `http://localhost:8761`
2. Revisar logs: `docker-compose logs eureka-server`
3. Esperar 30 segundos para que los servicios se registren

### Los servicios no se descubren en Eureka

**Problema:** Los microservicios se registraron pero muestran "DOWN"

**Solución:**
1. Verificar que cada servicio tiene su propia base de datos
2. Revisar configuración de `eureka.instance.instance-id` en application.yml
3. Consultar logs: `docker-compose logs donation-service`

### Error "database 'donaton_donation_db' doesn't exist"

**Problema:** Hibernate no creó la base de datos

**Solución:**
```sql
-- Crear manualmente si es necesario
CREATE DATABASE donaton_donation_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE donaton_logistics_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

O establecer `spring.jpa.hibernate.ddl-auto: create-drop` temporalmente.

### "Port X already in use"

**Problema:** El puerto está siendo usado por otro proceso

**Soluciones:**
```bash
# Windows - Encontrar proceso en puerto 8080
netstat -ano | findstr :8080

# Cambiar puerto en application.yml
server:
  port: 8090  # Nuevo puerto
```

## Información Técnica

### Estructura de Puertos

| Servicio | Puerto | Descripción |
|----------|--------|------------|
| Frontend | 3000 | Aplicación React |
| BFF Service | 8080 | Gateway API principal |
| Donation Service | 8082 | Microservicio de donaciones |
| Logistics Service | 8083 | Microservicio de logística |
| Eureka Server | 8761 | Service Registry |
| MySQL (Donation) | 3306 | Base de datos donation-service |
| MySQL (Logistics) | 3307 | Base de datos logistics-service |

### Estructura de Bases de Datos

```
MySQL
├── donaton_donation_db
│   ├── donation (tabla de donaciones)
│   └── (gestiona donaciones individuales y corporativas)
└── donaton_logistics_db
    ├── inventory (tabla de inventario)
    └── (gestiona inventario de donaciones)
```

## Próximos Pasos

1. Explorar el Dashboard de Eureka
2. Probar los endpoints en Postman o similar
3. Revisar los logs de cada servicio
4. Modificar datos y ver cómo se propagan
5. Ejecutar los tests unitarios
