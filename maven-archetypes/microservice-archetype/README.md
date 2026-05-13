# Arquetipo de Microservicio - Donaton

Este arquetipo Maven define la estructura base y las dependencias estándar (Spring Boot Web, Spring Data MongoDB, Lombok) para cualquier nuevo microservicio dentro del ecosistema Donaton.

## Instrucciones de uso para el equipo de desarrollo

Para generar un nuevo microservicio utilizando este arquetipo, ejecuta el siguiente comando en la terminal desde la carpeta raíz del backend:

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.donaton.archetype \
  -DarchetypeArtifactId=microservice-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.donaton.nuevoservicio \
  -DartifactId=nuevo-service \
  -DinteractiveMode=false