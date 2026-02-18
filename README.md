# 🛡️ Plataforma de Seguros — Emisión de Pólizas con Evaluación de Riesgo

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3-green)
![WebFlux](https://img.shields.io/badge/WebFlux-Reactivo-purple)
![Build](https://img.shields.io/badge/Build-OK-brightgreen)

> Sistema backend construido con Spring Boot 3 y Java 17 que demuestra programación reactiva, resiliencia, diseño limpio y evaluación de riesgo en tiempo real.

---

## 📖 Storytelling — El Problema

Las aseguradoras necesitan emitir pólizas rápidamente mientras evalúan el riesgo del cliente y mantienen la continuidad del servicio incluso cuando otros sistemas fallan.

Este proyecto simula un backend real donde:

- Se registran clientes
- Se emiten pólizas
- Se evalúa el riesgo dinámicamente
- Los servicios se comunican de forma reactiva
- Se manejan fallos de dependencias sin afectar la operación

El objetivo es demostrar prácticas de ingeniería backend listas para producción.

---

## 🧠 Visión de Arquitectura

```mermaid
flowchart LR
    Usuario --> ClienteService
    Usuario --> PolizaService
    PolizaService --> ClienteService
    PolizaService --> RiskEngine
    PolizaService --> BaseDatos
    ClienteService --> BaseDatos
````

---

## ⚙️ Stack Tecnológico

* Java 17
* Spring Boot 3
* Spring WebFlux
* Spring Data JPA
* H2 Database
* WebClient
* Resilience4j (Circuit Breaker)
* Lombok
* JUnit 5 + Mockito
* Swagger / OpenAPI

---

## 🚀 Características Principales

✅ APIs REST reactivas
✅ Programación funcional
✅ Motor de riesgo dinámico
✅ Circuit breaker con fallback
✅ Arquitectura desacoplada
✅ Comunicación entre microservicios
✅ Pruebas unitarias
✅ Documentación automática
✅ Base de datos en memoria

---

## 🛠 Microservicios

### 👤 Cliente Service

Responsable de:

* Registro de clientes
* Consulta de clientes
* Validación de estado

Puerto:

```
http://localhost:8082
```

---

### 📄 Poliza Service

Responsable de:

* Emisión de pólizas
* Evaluación de riesgo
* Ajuste de prima
* Manejo de resiliencia

Puerto:

```
http://localhost:8083
```

---

## 🧩 Lógica del Motor de Riesgo

El sistema evalúa reglas como:

* Estado del cliente
* Tipo de seguro
* Monto de prima

Resultados:

| Nivel | Resultado       |
| ----- | --------------- |
| BAJO  | Emitida         |
| MEDIO | Ajuste de prima |
| ALTO  | Rechazada       |

Simula procesos reales de underwriting.

---

## 🛡 Estrategia de Resiliencia

Si Cliente Service falla:

* Se activa Circuit Breaker
* Se aplica fallback automático
* La póliza queda:

```
PENDIENTE_REVISION
```

El sistema continúa funcionando.

---

## 🧪 Pruebas

Ejecutar:

```bash
mvn clean test
```

Incluye:

* Tests unitarios
* Validación de reglas
* Mocking
* Flujos reactivos

---

## 📚 Documentación de APIs

Disponible en:

```
http://localhost:8082/swagger-ui.html
http://localhost:8083/swagger-ui.html
```

---

## ▶️ Ejecutar Localmente

```bash
cd cliente-service
mvn spring-boot:run

cd poliza-service
mvn spring-boot:run
```

---

## 🧪 Flujo de Ejemplo

1 — Registrar cliente
2 — Emitir póliza
3 — Evaluación de riesgo
4 — Persistencia

---

## 🧭 Principios de Diseño

* Clean Code
* Separación de responsabilidades
* Programación reactiva
* Resiliencia por diseño
* Composición funcional

---

## 📈 ¿Por qué WebFlux?

* IO no bloqueante
* Alta concurrencia
* Escalabilidad
* Arquitectura moderna

---

## 🏗 Mejoras Futuras

* Docker
* API Gateway
* Service Discovery
* Kafka / eventos
* Observabilidad
* Seguridad JWT
* CI/CD pipeline

---

## 👨‍💻 Autor: Pool Meneses

Proyecto desarrollado como demostración de:

* Arquitectura de microservicios
* Backend resiliente
* Programación reactiva
* Buenas prácticas empresariales

---



