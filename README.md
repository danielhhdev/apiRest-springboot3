# API REST con Spring Boot 3.5 + Java 21

Proyecto de ejemplo construido con **Spring Boot 3.5** y **Java 21**. Forma parte de un portafolio personal y demuestra el uso de hilos virtuales de Project Loom, procesamiento asíncrono en lotes y la generación de una imagen nativa para despliegues ligeros.

## Propósito

Implementar un backend sencillo que exponga operaciones CRUD de clientes y un procesamiento asíncrono de lotes. El proyecto sirve para explorar:

- **Virtual Threads** (hilos virtuales) para alta concurrencia.
- **GraalVM Native Image** Inicio instantáneo y consumo de memoria reducido.
- Integración con **Spring Boot Actuator** y **Micrometer** para observabilidad.
- Documentación automática con **SpringDoc OpenAPI (Swagger UI)**.
- Pruebas unitarias e integración con **JUnit**, **Mockito** y **Testcontainers**.

## Tecnologías principales

- **Maven** para gestión de dependencias y empaquetado.
- **Spring Data JPA** + **PostgreSQL** para persistencia.
- **MapStruct** para el mapeo entre entidades y DTOs.
- **Lombok** para reducir código repetitivo.
- **Jakarta Validation** para validación de datos de entrada.
- **Spring Boot Actuator** y **Micrometer** para métricas y salud.
- **SpringDoc OpenAPI** para generación de documentación REST.
- **Procesamiento asíncrono** con hilos virtuales (`VirtualThreadConfig`).
- **Pruebas** con JUnit 5, Mockito y Testcontainers.

## Pre-requisitos

- **JDK 21** instalado y en el `PATH`.
- **PostgreSQL** accesible y configurado (ver `application.yml`).
- (Opcional) **Docker** si se desea generar la imagen nativa localmente o ejecutar contenedores de prueba.

## Instalación y configuración

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/spring-boot-3.5-java21-rest.git
   cd spring-boot-3.5-java21-rest
   ```
2. Configurar la conexión a la base de datos en `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/mi_db
       username: postgres
       password: secret
     jpa:
       hibernate:
         ddl-auto: update
     threads:
       virtual:
         enable: true
      
   ```
3. Ajustar parámetros adicionales (puertos, credenciales, etc.) según necesidad.

## Construir y ejecutar

### Modo JVM

```bash
mvn clean spring-boot:run
```

### Imagen nativa (GraalVM + Paketo Buildpacks)

1. Asegurarse de tener `BP_NATIVE_IMAGE=true` en el `pom.xml`.
2. Ejecutar:
   ```bash
   mvn clean spring-boot:build-image
   docker run --rm -p 8080:8080 tu-imagen-nativa:latest
   ```

## Endpoints REST

| Método | Ruta                   | Descripción                      |
|-------:|------------------------|----------------------------------|
| GET    | `/api`                 | Listar todos los clientes        |
| POST   | `/api`                 | Crear un nuevo cliente           |
| PUT    | `/api/{id}`            | Actualizar un cliente existente  |
| DELETE | `/api/{id}`            | Eliminar un cliente              |
| GET    | `/api/consultar-lote`  | Procesar un lote de clientes     |

## Procesamiento asíncrono de lotes

- Servicio orquestador: `OrchestratorServiceImpl`.
- Configuración de hilos virtuales en `VirtualThreadConfig`:
  ```java
  @Bean
  VirtualThreadTaskExecutor virtualThreadTaskExecutor() {
      return new VirtualThreadTaskExecutor();
  }
  ```
- El endpoint dispara tareas en hilos virtuales, maneja resultados de forma no bloqueante.

## Observabilidad y métricas

- Endpoints de Actuator:
    - `/actuator/health`
    - `/actuator/metrics`
    - `/actuator/prometheus` (Micrometer)
- Trazas distribuidas con OpenTelemetry.

## Pruebas

- **Unitarias**: JUnit 5 + Mockito (`mvn test`).
- **Integración**: Testcontainers para PostgreSQL.

## CI / CD

Flujo de GitHub Actions para:

1. Ejecutar pruebas unitarias e integración.
2. Construir y empaquetar la aplicación.
3. Generar y publicar la imagen nativa.
4. (Opcional) Desplegar a Render u otro servicio.

## Contribuciones

1. Hacer fork del repositorio.
2. Crear una rama feature (`git checkout -b feature/nueva-funcionalidad`).
3. Realizar cambios y commit.
4. Enviar pull request.

## Licencia

Este proyecto está bajo la **Licencia MIT**.
