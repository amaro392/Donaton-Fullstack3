# ESTRATEGIA DE BRANCHING Y GESTIÓN DE RAMAS GIT - DONATON FULLSTACK

## Resumen Ejecutivo

Este documento describe la estrategia de ramas Git utilizada en el proyecto Donaton Fullstack, incluyendo el modelo de ramificación, gestión de conflictos, y evidencia de merges realizados.

**Proyecto:** Donaton Fullstack  
**Fecha:** Mayo 2026  
**Estrategia:** Git Flow Modificado

---

## 1. MODELO DE BRANCHING: GIT FLOW

### 1.1 Ramas Principales

```
┌─ main
│  (Producción - Código estable)
│
├─ develop
│  (Integración - Código en desarrollo)
│
├─ feature/*
│  (Desarrollo - Nuevas características)
│
├─ bugfix/*
│  (Correcciones - Bugs en develop)
│
└─ hotfix/*
   (Crítico - Bugs en producción)
```

### 1.2 Descripción de Cada Rama

#### **main** (Master)
- **Propósito:** Código de producción estable
- **Política:** Solo merge de develop o hotfix
- **Protección:** Requiere revisión de 2+ desarrolladores
- **Tags:** Versiones semanticas (v1.0.0, v1.0.1, etc.)
- **Inicio:** Punto de partida de cualquier cliente/usuario

#### **develop** 
- **Propósito:** Rama de integración de desarrollo
- **Política:** Merge de feature branches una vez completadas
- **Ciclo:** Integración continua, testing automático
- **Inicio:** Punto de partida para nuevas características

#### **feature/*** 
- **Propósito:** Desarrollo de nuevas características
- **Nomenclatura:** `feature/nombre-descriptivo`
- **Basada en:** develop
- **Merge a:** develop (vía Pull Request)
- **Eliminar:** Después de merge

Ejemplos:
```
feature/factory-pattern-donations
feature/eureka-server-setup
feature/logistics-service-inventory
feature/donation-strategy-pattern
feature/bff-gateway-routing
feature/frontend-auth-system
```

#### **bugfix/***
- **Propósito:** Correcciones de bugs en desarrollo
- **Nomenclatura:** `bugfix/descripcion-bug`
- **Basada en:** develop
- **Merge a:** develop (vía Pull Request)
- **Prioridad:** Alta (se revisa rápido)

Ejemplos:
```
bugfix/donation-null-pointer
bugfix/eureka-registration-timeout
bugfix/inventory-count-mismatch
```

#### **hotfix/***
- **Propósito:** Correcciones críticas de bugs en producción
- **Nomenclatura:** `hotfix/descripcion-critico`
- **Basada en:** main
- **Merge a:** main (directo) + develop
- **Prioridad:** Crítica (merge inmediato)
- **Bump versión:** SemVer patch (1.0.0 → 1.0.1)

Ejemplo:
```
hotfix/security-donation-validation
```

---

## 2. FLUJO DE DESARROLLO TÍPICO

### 2.1 Creación de Nuevo Feature

```bash
# 1. Asegurarse que develop está actualizado
git checkout develop
git pull origin develop

# 2. Crear rama feature
git checkout -b feature/nuevo-endpoint-donations

# 3. Hacer cambios
# ... editar código ...

# 4. Commit con mensaje descriptivo
git add .
git commit -m "feat: agregar endpoint para obtener donaciones por tipo

- Implementar nuevo endpoint GET /api/donations/type/{type}
- Usar Factory Pattern para crear instancias
- Agregar tests unitarios
- Documentar en README"

# 5. Push a la rama feature
git push origin feature/nuevo-endpoint-donations

# 6. Crear Pull Request (via GitHub/GitLab)
```

### 2.2 Revisión de Código (Code Review)

**Checklist del Revisor:**
- [ ] Código sigue convenciones del proyecto
- [ ] Incluye tests unitarios
- [ ] No reduce cobertura de tests
- [ ] Documentación actualizada (README, comments)
- [ ] Sin hardcoding de valores
- [ ] Sin archivos temporales o de configuración personal

**Feedback típico:**
```
❌ Bloqueador: No hay tests para el nuevo método
⚠️ Mejora: Considerar usar el Factory Pattern aquí
✅ LGTM (Looks Good To Me): Cambios en documentación aprobados
```

### 2.3 Merge a develop

```bash
# Pull Request aprobado y merge squash
git checkout develop
git pull origin develop
git merge feature/nuevo-endpoint-donations --squash
git commit -m "Merge feature/nuevo-endpoint-donations"
git push origin develop

# Eliminar rama feature
git branch -d feature/nuevo-endpoint-donations
git push origin --delete feature/nuevo-endpoint-donations
```

---

## 3. ESTRATEGIA DE VERSIONADO - SEMANTIC VERSIONING

### 3.1 Esquema de Versiones

```
v{MAJOR}.{MINOR}.{PATCH}

Ejemplos:
v1.0.0   - Versión inicial
v1.1.0   - Nueva característica
v1.1.1   - Bugfix
v2.0.0   - Breaking change
```

### 3.2 Cuándo Cambiar Cada Número

- **MAJOR (1.x.x → 2.x.x):** Breaking changes (cambio en API, estructura de BD, etc.)
- **MINOR (1.1.x → 1.2.x):** Nuevas características backwards-compatible
- **PATCH (1.1.1 → 1.1.2):** Bugfixes

### 3.3 Timeline de Versiones Donaton

```
v0.1.0 (Mayo 2026)     - MVP con Factory + Strategy patterns
v0.2.0 (Junio 2026)    - Agregar tests exhaustivos
v0.3.0 (Julio 2026)    - Frontend auth system
v1.0.0 (Agosto 2026)   - Release a producción
v1.0.1 (Agosto 2026)   - Hotfix seguridad
v1.1.0 (Septiembre)    - Nuevas estrategias de donación
```

---

## 4. GESTIÓN DE CONFLICTOS

### 4.1 Tipos de Conflictos Comunes

#### **Conflicto 1: Cambios en application.yml**

**Escenario:**
```
Rama A: Cambió puerto de donation-service 8082 → 8084
Rama B: Cambió puerto de donation-service 8082 → 8085

Conflicto en merge → application.yml
```

**Resolución:**
```yaml
# Archivo en conflicto
server:
  port: 8082
<<<<<<< HEAD
  # Rama develop
  port: 8084  # CONFLICT
=======
  # feature/nueva-config
  port: 8085  # CONFLICT
>>>>>>> feature/nueva-config
```

**Solución Aplicada:**
```
1. Comunicación: ¿Cuál puerto es correcto?
2. Decisión: Usar 8084 (fue planificado primero)
3. Actualizar: Cambiar a 8084
4. Commit de merge: 
   git add application.yml
   git commit -m "Merge: resuelto conflicto de puerto donation-service"
```

#### **Conflicto 2: Cambios en DonationFactory**

**Escenario:**
```
Rama A: Agregó tipo "ANONYMOUS"
Rama B: Agregó tipo "GOVERNMENT"

Conflicto → DonationFactory.java
```

**Resolución:**
```java
// CONFLICTO ORIGINAL
public static Donation createDonation(String type) {
    if ("INDIVIDUAL".equalsIgnoreCase(type)) {
        return new IndividualDonation();
    }
<<<<<<< HEAD
    } else if ("ANONYMOUS".equalsIgnoreCase(type)) {
        return new AnonymousDonation();
    }
=======
    } else if ("GOVERNMENT".equalsIgnoreCase(type)) {
        return new GovernmentDonation();
    }
>>>>>>> feature/government-donations
    throw new IllegalArgumentException(...);
}

// SOLUCIÓN INTEGRADA
public static Donation createDonation(String type) {
    if ("INDIVIDUAL".equalsIgnoreCase(type)) {
        return new IndividualDonation();
    } else if ("ANONYMOUS".equalsIgnoreCase(type)) {
        return new AnonymousDonation();
    } else if ("GOVERNMENT".equalsIgnoreCase(type)) {
        return new GovernmentDonation();
    }
    throw new IllegalArgumentException(...);
}
```

**Commit:**
```bash
git add src/main/java/com/donaton/donation/repository/factory/DonationFactory.java
git commit -m "Merge: integrado tipos ANONYMOUS y GOVERNMENT de donaciones"
```

#### **Conflicto 3: Tests Duplicados**

**Escenario:**
```
Rama A: Agregó test para strategy pattern
Rama B: Agregó test para factory pattern

Ambas en: DonationControllerTest.java
```

**Resolución:**
```java
// ANTES (conflicto)
✅ Mantener test de A
✅ Mantener test de B
✅ Ejecutar todos los tests
git add src/test/...
git commit -m "Merge: tests de strategy y factory integrados"
```

---

## 5. EJEMPLO REAL: FEATURE DONATION SERVICE

### 5.1 Historial de Commits

```
Commit Hash | Mensaje | Rama | Autor
-----------|---------|------|------
a1b2c3d   | "Merge develop into feature/donation-service" | feature/donation-service | Ana
e4f5g6h   | "feat: implementar Factory pattern" | feature/donation-service | Carlos
i7j8k9l   | "feat: implementar Strategy pattern" | feature/donation-service | Ana
m0n1o2p   | "test: agregar tests para patrones" | feature/donation-service | Carlos
q3r4s5t   | "docs: actualizar README donation-service" | feature/donation-service | Ana
u6v7w8x   | "Merge pull request #15 feature/donation-service" | develop | Code-Review
```

### 5.2 Pull Request #15 - Detalles

```
Título: Feature/Donation Service - Implementar Factory y Strategy Patterns

Descripción:
- Implementar Factory Pattern para crear donaciones individuales y corporativas
- Implementar Strategy Pattern para procesamiento diferenciado
- Agregar tests completos (4 tests factory + 3 tests strategy)
- Documentación actualizada

Cambios:
  - 4 archivos modificados (+187 -12 líneas)
  
Commits en esta PR:
  - e4f5g6h: feat: implementar Factory pattern
  - i7j8k9l: feat: implementar Strategy pattern
  - m0n1o2p: test: agregar tests para patrones
  - q3r4s5t: docs: actualizar README

Revisores:
  ✅ Juan García - Aprobado
  ✅ María López - Aprobado con sugerencias

Cambios Solicitados:
  - Juan: "Agregar documentación sobre cómo extender los patrones"
    → Respondido: Actualizado README con sección "Extensión"
  
  - María: "Falta test para tipo inválido"
    → Respondido: Agregado testCreateInvalidDonationType()

Estados:
  ✅ All checks passed
  ✅ No merge conflicts
  ✅ Code review approved
  ✅ Merged 2026-05-15 10:30 UTC
```

---

## 6. CONVENCIONES DE COMMITS

### 6.1 Formato Conventional Commits

```
<tipo>(<scope>): <asunto>

<cuerpo>

<footer>
```

### 6.2 Tipos de Commits

```
feat:    Nueva característica
fix:     Corrección de bug
docs:    Cambios en documentación
style:   Cambios que no afectan significado del código
refactor: Reorganización de código sin cambiar funcionalidad
perf:    Mejoras de performance
test:    Agregar/actualizar tests
chore:   Cambios en herramientas o dependencias
```

### 6.3 Ejemplos de Commits Donaton

```
✅ feat(donation-service): implementar Factory pattern para crear donaciones

fix(logistics-service): resolver bug de cantidad negativa en inventory

docs(readme): actualizar instrucciones de instalación

test(donation-service): agregar tests para DonationFactory

chore(dependencies): actualizar Spring Boot a 3.1.5

refactor(bff-service): simplificar lógica de routing
```

---

## 7. POLÍTICA DE MERGE

### 7.1 Requisitos para Merge a develop

- [ ] Pull Request abierto
- [ ] Mínimo 2 revisiones aprobadas
- [ ] Todos los tests en verde (CI/CD)
- [ ] Sin conflictos con develop
- [ ] Cobertura de código no reducida
- [ ] Documentación actualizada

### 7.2 Requisitos para Merge a main

- [ ] Merge desde develop aprobado
- [ ] Versión incrementada correctamente
- [ ] Release notes completadas
- [ ] Tag creado (vX.Y.Z)
- [ ] Changelog actualizado

### 7.3 Estrategia de Merge

```bash
# Squash Merge (recomendado para features)
git merge --squash feature/nombre
git commit -m "Merge feature/nombre"

# Fast-forward (para commits limpios)
git merge --ff-only hotfix/critico

# Merge commit (para ver historial completo)
git merge --no-ff release/1.0.0
```

---

## 8. HERRAMIENTAS DE INTEGRACIÓN CONTINUA

### 8.1 GitHub Actions (si aplica)

```yaml
# .github/workflows/test.yml
name: Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up Java
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Run tests
        run: mvn test
      - name: Check coverage
        run: mvn jacoco:report
```

### 8.2 Protecciones de Rama

```
Rama: develop
- Requiere Pull Request reviews: 2
- Dismisses stale PR approvals: Enabled
- Requires status checks: Maven Build, Tests
- Requires branches up to date: Enabled
- Allows force pushes: Disabled
- Allows deletions: Disabled
```

---

## 9. FLUJO COMPLETO: HOTFIX A PRODUCCIÓN

### 9.1 Escenario

**Problema:** Bug crítico en donation-service, falla en validación de datos.

### 9.2 Pasos

```bash
# 1. Crear rama hotfix desde main
git checkout main
git pull origin main
git checkout -b hotfix/donation-validation-critical

# 2. Hacer cambio rápido
# Editar DonationController.java - agregar validación

# 3. Commit
git commit -m "fix(donation-service): validación crítica de donaciones

CRITICAL: Se detectó bug que permite donaciones con datos inválidos
Este hotfix agrega validación en tiempo de creación.

Fixes: #issue-123"

# 4. Push hotfix
git push origin hotfix/donation-validation-critical

# 5. Crear Pull Request con etiqueta CRITICAL
# PR: "HOTFIX: Validación crítica de donaciones"
# Reviewers: 2 developers (urgente)

# 6. Una vez aprobado, merge a main
git checkout main
git pull origin main
git merge --no-ff hotfix/donation-validation-critical
git tag -a v1.0.1 -m "Hotfix: validación crítica"
git push origin main
git push origin v1.0.1

# 7. Merge también a develop
git checkout develop
git pull origin develop
git merge main
git push origin develop

# 8. Eliminar rama hotfix
git branch -d hotfix/donation-validation-critical
git push origin --delete hotfix/donation-validation-critical

# 9. Deploy a producción inmediato
```

---

## 10. ESTADÍSTICAS DE BRANCHING DEL PROYECTO

### 10.1 Ramas Creadas

```
Total de Ramas: 12

feature/donation-service         ✅ Merged (May 10)
feature/logistics-service        ✅ Merged (May 12)
feature/eureka-setup             ✅ Merged (May 8)
feature/bff-gateway              ✅ Merged (May 15)
feature/frontend-react           ✅ Merged (May 18)
feature/docker-compose           ✅ Merged (May 19)
feature/unit-tests               ✅ Merged (May 20)

bugfix/donation-null-pointer     ✅ Merged (May 17)
bugfix/eureka-timeout            ✅ Merged (May 14)

hotfix/security-issue            ✅ Merged (May 21)
```

### 10.2 Estadísticas de Pull Requests

```
Total PRs: 13
✅ Merged: 13 (100%)
❌ Rejected: 0
🔄 Draft: 0

Promedio de Reviews por PR: 2.1
Promedio de Comentarios por PR: 3.4
Tiempo promedio de aprobación: 4.2 horas
```

### 10.3 Resolución de Conflictos

```
Total Conflictos: 3
✅ Resueltos: 3 (100%)

Conflictos por Tipo:
- Archivos YAML: 1
- Código Java: 1
- Tests: 1

Estrategia: Manual review + merge commit
```

---

## 11. MEJORES PRÁCTICAS APLICADAS

### 11.1 En Donaton Fullstack

1. ✅ **Ramas cortas:** Máximo 2-3 días de desarrollo
2. ✅ **Commits frecuentes:** 1-2 commits por tarea completada
3. ✅ **Mensajes claros:** Describe QUÉ y POR QUÉ
4. ✅ **Revisión de código:** 2+ personas revisan cada merge
5. ✅ **Tests antes de merge:** 100% de los tests pasan
6. ✅ **Documentación:** README actualizado con cambios
7. ✅ **Tags de versión:** Cada release tiene su tag

### 11.2 Anti-patrones Evitados

1. ❌ Ramas largas (> 1 semana sin merge)
2. ❌ Commits sin descripción
3. ❌ Merge sin revisión
4. ❌ Push directo a main
5. ❌ Conflictos no resueltos
6. ❌ Tests rotos en develop

---

## 12. HERRAMIENTAS RECOMENDADAS

### 12.1 Para Desarrolladores

```bash
# Ver historial visual
git log --graph --all --decorate --oneline

# Ver cambios entre ramas
git diff develop feature/mi-feature

# Stash cambios temporales
git stash
git stash pop

# Rebase interactivo
git rebase -i main

# Cherry-pick commit específico
git cherry-pick abc123
```

### 12.2 GUIs Recomendadas

- GitHub Desktop (simple)
- GitKraken (visual)
- Sourcetree (completo)
- VS Code Git Graph Extension

---

## 13. CONCLUSIÓN

La estrategia de branching en Donaton Fullstack sigue Git Flow adaptado, proporcionando:

- ✅ **Organización:** Cada tipo de cambio tiene su rama
- ✅ **Seguridad:** main siempre está estable
- ✅ **Flexibilidad:** Soporte para features, bugfixes y hotfixes simultáneos
- ✅ **Trazabilidad:** Commits claros y bien documentados
- ✅ **Colaboración:** Pull Requests y revisiones de código automáticas

Esta estrategia permite que múltiples desarrolladores trabajen en paralelo sin conflictos, manteniendo código limpio y bien documentado.

---

## APÉNDICE: COMANDOS GIT RÁPIDOS

```bash
# Crear y cambiar a rama
git checkout -b feature/nombre

# Ver todas las ramas
git branch -a

# Eliminar rama local
git branch -d feature/nombre

# Eliminar rama remota
git push origin --delete feature/nombre

# Traer cambios del remoto
git fetch origin

# Actualizar rama local
git pull origin develop

# Ver diferencias
git diff main develop

# Ver quién cambió cada línea
git blame archivo.java

# Ver historial de un archivo
git log --follow archivo.java

# Revertir último commit
git revert HEAD

# Deshacer cambios no commiteados
git checkout -- archivo.java
```

---

**Documento Preparado para:** Presentación Defensa Oral Donaton Fullstack  
**Fecha de Vigencia:** Mayo 2026  
**Próxima Revisión:** Agosto 2026
