# Donaton Frontend

Componente frontend desarrollado con React (NPM) para la gestión de donaciones.

## Requisitos
- Node.js 18+
- NPM 9+

## Instalación y ejecución

```bash
npm install
npm start
```

La aplicación queda disponible en: `http://localhost:3000`

## Estructura del proyecto

donaton-frontend/
├── public/
│   ├── index.html          # Página principal con formulario de login
│   ├── perfil_admin.html   # Vista del administrador
│   └── perfil_usuario.html # Vista del usuario
├── src/
│   ├── App.js              # Componente raíz de React
│   ├── auth.js             # Lógica de validación de formulario
│   └── index.js            # Punto de entrada React
├── style.css               # Estilos globales
└── package.json            # Dependencias NPM

## Scripts disponibles

| Comando | Descripción |
|---------|-------------|
| `npm start` | Inicia el servidor de desarrollo |
| `npm run build` | Genera build de producción |
| `npm test` | Ejecuta los tests |