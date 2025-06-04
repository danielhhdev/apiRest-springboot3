# API REST con Spring Boot 3.5

Este proyecto es una demostraci\u00f3n de una API REST desarrollada con **Spring Boot 3.5** y **Java 21**.

## Prop\u00f3sito

Implementar un ejemplo de backend que expone operaciones CRUD y procesamiento as\u00edncrono usando hilos virtuales.

## Tecnolog\u00edas principales

- **Maven** para la gesti\u00f3n del proyecto.
- **Spring Data JPA** con **PostgreSQL** como base de datos.
- **MapStruct** para el mapeo entre entidades y DTOs.
- **Lombok** para reducir c\u00f3digo boilerplate.
- **Jakarta Validation** para validaciones de datos de entrada.
- **SpringDoc OpenAPI** para la documentaci\u00f3n de la API.
- Hilos virtuales para tareas as\u00edncronas (ver `VirtualThreadConfig`).
- Pruebas con **JUnit/Mockito** y **Testcontainers**.

## Requisitos previos

- **JDK 21** instalado.
- **PostgreSQL** accesible y configurado seg\u00fan `src/main/resources/application.yml`.

## Compilar y ejecutar

```bash
mvn spring-boot:run
```

Para ejecutar las pruebas:

```bash
mvn test
```

## Endpoints principales

- `GET /api` - Lista todos los clientes.
- `POST /api` - Crea un nuevo cliente.
- `PUT /api/{id}` - Actualiza un cliente existente.
- `DELETE /api/{id}` - Elimina un cliente.
- `GET /api/consultar-lote` - Procesa de manera as\u00edncrona un lote de clientes.

Estas operaciones se encuentran en `ClientControllerImpl`. El procesamiento as\u00edncrono est\u00e1 orquestado por `OrchestatorServiceImpl` y `ClientThreadVirtualServiceImpl` empleando el `virtualThreadTaskExecutor` definido en `VirtualThreadConfig`.

## Archivos y clases relevantes

- `pom.xml` define todas las dependencias y plugins mencionados.
- `application.yml` contiene la configuraci\u00f3n de la base de datos y de SpringDoc OpenAPI.
- Clases en `src/main/java/com/dhh/apiRestSpringboot3` implementan los controladores, servicios y mapeos de la aplicaci\u00f3n.


