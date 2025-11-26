package com.salesianos.dam.apipeliculas.controller;

import com.salesianos.dam.apipeliculas.dto.ActorRequestDTO;
import com.salesianos.dam.apipeliculas.dto.ActorResponseDTO;
import com.salesianos.dam.apipeliculas.service.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("/api/v1/actores")
@RequiredArgsConstructor
@RestController
@Tag(name = "Gestión de los actores de una película", description = "Endpoints para gestionar las peticiones al servidor de los actores")
public class ActorController {

    private final ActorService actorService;

    @Operation(summary = "Obtener todos los actores")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actores recibidos correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActorResponseDTO.class)),
                            examples = @ExampleObject(
                                    value = """
                                            [
                                              {
                                                  "id": 1,
                                                  "nombre": "Pedro Almodo",
                                                  "anioNacimiento": 1965,
                                                  "peliculas": [
                                                      {
                                                          "id": 1,
                                                          "titulo": "El viaje"
                                                      }
                                                  ]
                                              }
                                          ]
                                    """
                            ))),
            @ApiResponse(responseCode = "404", description = "Error al recibir a los actores",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                            "detail": "No se han encontrado actores en el servidor",
                                            "instance": "/api/v1/actores",
                                            "status": 404,
                                            "title": "Entidad no encontrada",
                                            "type": "gestorpeliculas.com/error/no-encontrado"
                                        }
                                    """
                            )
                    )
            )
    })
    @GetMapping
    public List<ActorResponseDTO> getAll(){
        return actorService.getAll().stream().map(ActorResponseDTO::of).toList();
    }

    @Operation(summary = "Obtener un actor por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actor recibido correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActorResponseDTO.class)),
                            examples = @ExampleObject(
                                    value = """
                                              {
                                                  "id": 1,
                                                  "nombre": "Pedro Almodo",
                                                  "anioNacimiento": 1965,
                                                  "peliculas": [
                                                      {
                                                          "id": 1,
                                                          "titulo": "El viaje"
                                                      }
                                                  ]
                                              }
                                    """
                            ))),
            @ApiResponse(responseCode = "404", description = "Error al recibir al actor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                                 "detail": "No se ha encontrado al actor con id 3",
                                                 "instance": "/api/v1/actores/3",
                                                 "status": 404,
                                                 "title": "Entidad no encontrada",
                                                 "type": "gestorpeliculas.com/error/no-encontrado"
                                        }
                                    """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ActorResponseDTO getById(
            @Parameter(description = "Identificador del actor",example = "1")
            @PathVariable Long id){
        return ActorResponseDTO.of(actorService.getById(id));
    }

    @Operation(summary = "Crear un actor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Actor creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActorResponseDTO.class)),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                            "id": 1,
                                            "nombre": "Antonio Banderas",
                                            "peliculas": []
                                        }
                                    """
                            ))),
            @ApiResponse(responseCode = "400", description = "Error al crear al actor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                            "detail": "Falta el campo nombre para crear al actor",
                                            "instance": "/api/v1/actores",
                                            "status": 400,
                                            "title": "Error en los argumentos",
                                            "type": "gestorpeliculas.com/error/argumento-erroneo"
                                        }
                                    """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ActorResponseDTO> create (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo del dto",content = @Content(schema = @Schema(implementation = ActorRequestDTO.class),
                    mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "nombre":"Antonio Banderas"
                    }
                    """)
            )
            )
            @RequestBody ActorRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ActorResponseDTO.of(actorService.create(dto)));
    }
}
