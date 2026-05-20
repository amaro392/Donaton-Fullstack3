# Frontend Donaton - Manual de Instrucciones

## Descripción General
Frontend de la plataforma Donaton desarrollado con **React.js** siguiendo el estándar **NPM**. Proporciona una interfaz de usuario moderna y responsiva para gestionar donaciones, usuarios y administración del sistema.

## Requisitos Previos
- **Node.js**: v16.x o superior
- **npm**: v8.x o superior
- **Git**: para clonar el repositorio

## Instalación

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd donaton-frontend
```

### 2. Instalar Dependencias
```bash
npm install
```

Esto instalará todas las dependencias especificadas en `package.json`:
- React 18.2.0
- React DOM 18.2.0
- React Scripts 5.0.1

## Estructura del Proyecto
```
donaton-frontend/
├── public/                 # Archivos estáticos
│   ├── index.html         # Página principal HTML
│   ├── perfil_admin.html  # Página de perfil administrador
│   ├── perfil_usuario.html# Página de perfil usuario
│   └── registro.html      # Página de registro
├── src/                    # Código fuente
│   ├── App.js            # Componente raíz
│   ├── index.js          # Punto de entrada
│   ├── auth.js           # Lógica de autenticación
│   ├── registro.js       # Lógica de registro
│   └── components/       # Componentes reutilizables
├── assets/               # Recursos multimedia
├── package.json          # Configuración NPM
├── README.md             # Este archivo
└── *.css                 # Hojas de estilos
```

## Ejecución

### Modo Desarrollo
```bash
npm start
```
- Inicia el servidor de desarrollo en `http://localhost:3000`
- La aplicación se recargará automáticamente al realizar cambios
- Se abrirá en tu navegador por defecto

### Compilación para Producción
```bash
npm run build
```
- Crea una compilación optimizada en la carpeta `build/`
- Los archivos están minificados y listos para desplegar

### Pruebas
```bash
npm test
```
- Ejecuta los tests en modo interactivo
- Presiona `a` para ejecutar todos los tests

## Configuración de Conexión Backend

Para conectar con el BFF (Backend for Frontend), configura la URL del servidor en `src/auth.js`:

```javascript
const BFF_API_URL = 'http://localhost:8080';
```

Asegúrate de que:
1. El BFF esté ejecutándose en el puerto 8080 (o el que hayas configurado)
2. CORS esté habilitado en el backend para aceptar solicitudes desde `http://localhost:3000`

## Características Principales

### Autenticación
- Sistema de login/logout
- Validación de credenciales
- Gestión de sesiones

### Gestión de Usuarios
- Registro de nuevos usuarios
- Perfiles de usuario y administrador
- Visualización de datos personales

### Gestión de Donaciones
- Ver historial de donaciones
- Crear nuevas donaciones
- Filtrado de donaciones por tipo

## Patrones de Diseño Utilizados

### 1. **Patrón Componente** (React)
- Componentes funcionales reutilizables
- Props para pasar datos entre componentes
- Hooks para manejo de estado

### 2. **Patrón MVC** (Adaptado a React)
- Separación de lógica de negocio (`auth.js`, `registro.js`)
- Componentes como vistas
- Servicios para comunicación con API

## Pruebas

### Pruebas Manuales
1. Registrar un nuevo usuario en `registro.html`
2. Iniciar sesión con las credenciales
3. Navegar entre secciones (Perfil, Donaciones)
4. Crear y enviar una donación
5. Verificar que los datos se persisten en el backend

### Pruebas Automatizadas
```bash
npm test -- --coverage
```

## Despliegue

### Despliegue Local
1. Compilar: `npm run build`
2. Servir carpeta `build/` con servidor estático
3. Ejemplo con `serve`: `npx serve build -l 3000`

### Despliegue en Producción
1. Compilar: `npm run build`
2. Subir carpeta `build/` a tu servidor web (Nginx, Apache, etc.)
3. Configurar URL del BFF en variables de entorno
4. Asegurar que CORS esté correctamente configurado

## Resolución de Problemas

### Puerto 3000 en uso
```bash
npm start -- --port 3001
```

### Módulos no encontrados
```bash
rm -rf node_modules package-lock.json
npm install
```

### Errores de conexión con Backend
- Verificar que el BFF esté ejecutándose
- Verificar la URL del BFF en `auth.js`
- Verificar CORS en el backend

## Dependencias

| Paquete | Versión | Propósito |
|---------|---------|----------|
| react | 18.2.0 | Framework UI |
| react-dom | 18.2.0 | Renderizado DOM |
| react-scripts | 5.0.1 | CLI de Create React App |

## Scripts Disponibles

- `npm start` - Inicia servidor de desarrollo
- `npm run build` - Compila para producción
- `npm test` - Ejecuta tests
- `npm eject` - Expone configuración de webpack (irreversible)

## Contacto y Soporte

Para reportar bugs o sugerencias:
1. Crear un issue en el repositorio
2. Describir el problema detalladamente
3. Incluir pasos para reproducir

## Licencia

Este proyecto es parte de la plataforma Donaton y sigue la licencia del proyecto principal.
