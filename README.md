# API REST con Spring Boot 3.5

Proyecto de ejemplo construido con **Spring Boot 3.5** y **Java 21**. Forma parte de mi portafolio personal y demuestra el uso de hilos virtuales y la generación de una imagen nativa para la aplicación.

## Propósito

Implementar un backend sencillo que exponga operaciones CRUD de clientes y un procesamiento asíncrono. El proyecto sirve para explorar las nuevas capacidades de la plataforma, incluyendo la construcción de un contenedor con imagen nativa.

## Tecnologías principales

- **Maven** para la gestión y empaquetado.
- **Spring Data JPA** + **PostgreSQL** para persistencia.
- **MapStruct** para el mapeo entre entidades y DTOs.
- **Lombok** para eliminar código repetitivo.
- **Jakarta Validation** para validar datos de entrada.
- **SpringDoc OpenAPI** para documentar la API.
- **Spring Boot Actuator** junto con **Micrometer** para observabilidad.
- Procesamiento asíncrono con **hilos virtuales** (ver `VirtualThreadConfig`).
- Pruebas con **JUnit/Mockito** y **Testcontainers**.

## Requisitos previos

- **JDK 21** instalado.
- **PostgreSQL** accesible y configurado según `src/main/resources/application.yml`.

## Imagen nativa

La aplicación puede compilarse en una **imagen nativa** gracias a GraalVM y [Paketo Buildpacks](https://paketo.io/). Una imagen nativa ejecuta código máquina precompilado, lo que reduce el tiempo de arranque y el consumo de memoria, ideal para despliegues en contenedores.

Para construirla basta con ejecutar:

```bash
mvn spring-boot:build-image
```

El `spring-boot-maven-plugin` crea un contenedor optimizado cuando la variable `BP_NATIVE_IMAGE=true` está presente en el `pom.xml`.

## Endpoints principales

- `GET /api` – Lista todos los clientes.
- `POST /api` – Crea un nuevo cliente.
- `PUT /api/{id}` – Actualiza un cliente existente.
- `DELETE /api/{id}` – Elimina un cliente.
- `GET /api/consultar-lote` – Procesa de forma asíncrona un lote de clientes.

El procesamiento en lote se orquesta en `OrchestatorServiceImpl` y utiliza hilos virtuales a través del `virtualThreadTaskExecutor` definido en `VirtualThreadConfig`.

## Observabilidad

El proyecto expone métricas y endpoints de salud mediante Actuator. Además, gracias a **Micrometer** y su registrador de Prometheus, se pueden recolectar métricas desde `/actuator/prometheus`.

## CI y despliegue

Existe un flujo de trabajo en **GitHub Actions** encargado de construir y ejecutar las pruebas con Testcontainers. Tras completarse, se solicita un despliegue en **Render** mediante su API.

## Archivos destacados

- `pom.xml` – Dependencias y configuración del plugin para la imagen nativa.
- `application.yml` – Propiedades de base de datos, Swagger y Actuator.
- Carpeta `src/main/java` – Controladores, servicios y configuración principal.
