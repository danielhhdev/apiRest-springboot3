package com.dhh.apiRestSpringboot3.controller;

import com.dhh.apiRestSpringboot3.dto.ClientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Cliente", description = "Operaciones CRUD para clientes")
public interface ClientController {

    @Operation(
            summary = "Obtener todos los clientes",
            description = "Devuelve una lista de todos los clientes registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    ResponseEntity<List<ClientDTO>> getAll();

    @Operation(
            summary = "Crear un cliente",
            description = "Crea un nuevo cliente en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    ResponseEntity<ClientDTO> create(
            @Parameter(description = "DTO del cliente a crear", required = true) @Valid @RequestBody ClientDTO clienteDTO
            );

    @Operation(
            summary = "Actualizar un cliente",
            description = "Actualiza un cliente por ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    ResponseEntity<ClientDTO> update(
            @Parameter(description = "ID del cliente a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "DTO del cliente con los nuevos datos", required = true) @Valid @RequestBody ClientDTO clienteDTO
    );

    @Operation(
            summary = "Eliminar un cliente",
            description = "Elimina un cliente por ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID del cliente a eliminar", required = true) @PathVariable Long id
    );
}