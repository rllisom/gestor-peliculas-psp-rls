package com.salesianos.dam.apipeliculas.controller;

import com.salesianos.dam.apipeliculas.dto.DirectorResponseDTO;
import com.salesianos.dam.apipeliculas.dto.PeliculaRequestDTO;
import com.salesianos.dam.apipeliculas.dto.PeliculaResponseDTO;
import com.salesianos.dam.apipeliculas.model.Pelicula;
import com.salesianos.dam.apipeliculas.service.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/peliculas")
@RestController
@Tag(name = "Películas", description = "Peticiones CRUD para la gestión de películas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @Operation(summary = "Obtener todas las películas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Películas recibidas correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PeliculaResponseDTO.class)),
                    examples = @ExampleObject(
                            value = """
                                    [
                                        {
                                          "id": 1,
                                          "titulo": "El viaje",
                                          "fechaEstreno": "2024-05-15",
                                          "genero": "Drama",
                                          "director": {
                                            "id": 10,
                                            "nombre": "Pedro Almodóvar"
                                          },
                                          "actores": [
                                            { "id": 2, "nombre": "Ana García" },
                                            { "id": 3, "nombre": "Luis Pérez" }
                                          ]  
                                        }
                                    ]
                                    """
                    ))),
            @ApiResponse(responseCode = "404", description = "Error al recibir la películas",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                                        {
                                            "detail": "No se han encontrado películas en el servidor",
                                            "instance": "/api/v1/peliculas",
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
    public List<PeliculaResponseDTO> getAll(){
        return peliculaService.getAll().stream().map(PeliculaResponseDTO::of).toList();
    }

    @Operation(summary = "Obtener película por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Película recibida correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaResponseDTO.class)),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "id": 1,
                                          "titulo": "El viaje",
                                          "fechaEstreno": "2024-05-15",
                                          "genero": "Drama",
                                          "director": {
                                            "id": 10,
                                            "nombre": "Pedro Almodóvar"
                                          },
                                          "actores": [
                                            { "id": 2, "nombre": "Ana García" },
                                            { "id": 3, "nombre": "Luis Pérez" }
                                          ]  
                                        }
                                    """
                            ))),
            @ApiResponse(responseCode = "404", description = "Error al recibir la película",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                            "detail": "No se ha encontrado la película con id 4",
                                            "instance": "/api/v1/peliculas/4",
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
    public PeliculaResponseDTO getById(
            @Parameter(description = "identificador de la película",example = "1" )
            @PathVariable Long id){
        return PeliculaResponseDTO.of(peliculaService.getById(id));
    }

    @Operation(summary = "Crear una película")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Película creada correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaResponseDTO.class)),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                            "titulo": "El viaje",
                                            "genero": "Acción",
                                            "fechaEstreno": "2024-05-17",
                                            "dir_id": 1
                                        }
                                    """
                            ))),
            @ApiResponse(responseCode = "400", description = "Error al crear la película",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                         {
                                           "detail": "Falta el campo del título de la película",
                                           "instance": "/api/v1/peliculas",
                                           "status": 400,
                                           "title": "Error en los argumentos",
                                           "type": "gestorpeliculas.com/error/argumento-erroneo"
                                        }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "La película ya existe",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                                        {
                                           "detail": "Ya existe una película con el titulo: El viaje",
                                           "instance": "/api/v1/peliculas",
                                           "status": 409,
                                           "title": "Película ya existente",
                                           "type": "gestorpeliculas.com/error/fallo-agregar" 
                                        }
                                    """
                    )
            )
            )
    })
    @PostMapping
    public ResponseEntity<PeliculaResponseDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo del dto",content = @Content(schema = @Schema(implementation = PeliculaRequestDTO.class),
            mediaType = "application/media", examples = @ExampleObject(value = """
                    {
                        "titulo": "El viaje",
                        "genero": "Acción",
                        "fechaEstreno": "2024-05-17",
                        "dir_id": 1
                    }
                    """)
            ))
            @RequestBody PeliculaRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(PeliculaResponseDTO.of(peliculaService.create(dto)));
    }


    @Operation(summary = "Agregar actor a una película")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actor agregado a la películacorrectamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaResponseDTO.class)),
                            examples = @ExampleObject(
                                    value = """
                                    
                                        {
                                          "id": 1,
                                          "titulo": "El viaje",
                                          "fechaEstreno": "2024-05-15",
                                          "genero": "Drama",
                                          "director": {
                                            "id": 1,
                                            "nombre": "Pedro Almodóvar"
                                          },
                                          "actores": [
                                            { "id": 2, "nombre": "Ana García" },
                                          ]  
                                        }
                                    
                                    """
                            ))),
            @ApiResponse(responseCode = "409", description = "Error al agregar el actor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                        "detail": "El actor con id 1 ya está en el reparto de la película",
                        "instance": "/api/v1/peliculas/1/actores/1",
                        "status": 409,
                        "title": "Error al asignar",
                        "type": "gestorpeliculas.com/error/fallo-agregar"
                    }
                """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "No se puede agregar un actor debido un argumento fallido de película",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                                {
                                                   "detail": "No se puede editar la película con id 2",
                                                   "instance": "/api/v1/peliculas/2/actores/1",
                                                   "status": 400,
                                                   "title": "Error en los argumentos",
                                                   "type": "gestorpeliculas.com/error/argumento-erroneo"
                                                }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "No se puede agregar un actor debido un argumento fallido de actor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                                {
                                                    "detail": "No se puede editar la película con un actor de id 3",
                                                    "instance": "/api/v1/peliculas/1/actores/3",
                                                    "status": 400,
                                                    "title": "Error en los argumentos",
                                                    "type": "gestorpeliculas.com/error/argumento-erroneo"
                                                }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/{peliculaId}/actores/{actorId}")
    public ResponseEntity<PeliculaResponseDTO> ActorToPelicula(
            @Parameter(description = "Identificador de la película",example = "1")
            @PathVariable Long peliculaId,
            @Parameter(description = "Identificador del actor", example = "2")
            @PathVariable Long actorId){
        return ResponseEntity.status(HttpStatus.CREATED).body(PeliculaResponseDTO.of(peliculaService.editPeliculaActor(peliculaId,actorId)));
    }

    @Operation(summary = "Editar una película")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Película editada correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaResponseDTO.class)),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                                "id": 1,
                                                "titulo": "Los viaje",
                                                "fechaEstreno": "2024-05-17",
                                                "genero": "Acción",
                                                "director": {
                                                    "id": 1,
                                                    "nombre": "Pedro Almodovar"
                                                },
                                                "actores": [
                                                    {
                                                        "id": 1,
                                                        "nombre": "Antonio Banderas"
                                                    }
                                                ]
                                        }
                                    """
                            ))),
            @ApiResponse(responseCode = "400", description = "Error al editar la película",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                            "detail": "Falta el campo del título de la película",
                                            "instance": "/api/v1/peliculas/1",
                                            "status": 400,
                                            "title": "Error en los argumentos",
                                            "type": "gestorpeliculas.com/error/argumento-erroneo"
                                        }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "No se puede editar la película debido a un identificador erróneo",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                                {
                                                   "detail": "No se puede editar la película con id 2",
                                                   "instance": "/api/v1/peliculas/2/actores/1",
                                                   "status": 400,
                                                   "title": "Error en los argumentos",
                                                   "type": "gestorpeliculas.com/error/argumento-erroneo"
                                                }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "La película ya existe",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                           "detail": "Ya existe una película con el titulo: El viaje",
                                           "instance": "/api/v1/peliculas",
                                           "status": 409,
                                           "title": "Película ya existente",
                                           "type": "gestorpeliculas.com/error/fallo-agregar" 
                                        }
                                    """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<PeliculaResponseDTO> edit(
            @Parameter(description = "Identificador de la película",example = "1" )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo de la película", content = @Content(
                    schema = @Schema(implementation = PeliculaRequestDTO.class),
                    mediaType = "application/json",
                    examples = @ExampleObject( value = """
                            {
                                "titulo": "los viajes",
                                "genero": "Acción",
                                "fechaEstreno": "2024-05-17",
                                "dir_id": 1
                            }
                            """)
            ))
            @RequestBody PeliculaRequestDTO dto){
        return ResponseEntity.ok(PeliculaResponseDTO.of(peliculaService.edit(id,dto)));
    }


    @Operation(summary = "Eliminar película")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Película eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "No se puede eliminar la película debido al identificador",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                        "detail": "No se puede eliminar la película con id 4",
                        "instance": "/api/v1/peliculas/4",
                        "status": 400,
                        "title": "Error en los argumentos",
                        "type": "gestorpeliculas.com/error/argumento-erroneo"
                    }
                """
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Identificador de la película",example = "1" )
            @PathVariable Long id){
        peliculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
