package com.salesianos.dam.apipeliculas.controller;

import com.salesianos.dam.apipeliculas.dto.DirectorRequestDTO;
import com.salesianos.dam.apipeliculas.dto.DirectorResponseDTO;
import com.salesianos.dam.apipeliculas.service.DirectorService;
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

@RequiredArgsConstructor
@RequestMapping("/api/v1/director")
@Tag(name = "Gestión del director de una película", description = "Endpoints para gestionar a un director")
@RestController
public class DirectorController {

    private final DirectorService directorService;

    @Operation(summary = "Obtener todos los directores")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Directores recibidos correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DirectorResponseDTO.class)),
                            examples = @ExampleObject(
                                    value = """
                                            [
                                                 {
                                                     "id": 1,
                                                     "nombre": "Pedro Almodovar",
                                                     "anioNacimiento": 1963,
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
            @ApiResponse(responseCode = "404", description = "Error al recibir los directores",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                            "detail": "No se han encontrado directores en el servidor",
                                            "instance": "/api/v1/director",
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
    public List<DirectorResponseDTO> getAll(){
        return directorService.getAll().stream().map(DirectorResponseDTO::of).toList();
    }

    @Operation(summary = "Obtener un director por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Directores recibidos correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DirectorResponseDTO.class)),
                            examples = @ExampleObject(
                                    value = """
                                                 {
                                                     "id": 1,
                                                     "nombre": "Pedro Almodovar",
                                                     "anioNacimiento": 1963,
                                                     "peliculas": [
                                                         {
                                                             "id": 1,
                                                             "titulo": "El viaje"
                                                         }
                                                     ]
                                                 }
                                    """
                            ))),
            @ApiResponse(responseCode = "404", description = "Error al recibir a un director específico",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                            "detail": "No se ha encontrado al director con id 2",
                                            "instance": "/api/v1/director",
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
    public DirectorResponseDTO getById(@PathVariable Long id){
        return DirectorResponseDTO.of(directorService.getById(id));
    }

    @Operation(summary = "Crear un director")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Director creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DirectorResponseDTO.class)),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                            "id" : 1,
                                            "nombre" : "Pedro Almodovar",
                                            "anioNacimiento" : 1963,
                                            "peliculas": []
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
                                            "detail": "Falta el campo del nombre del director",
                                            "instance": "/api/v1/director",
                                            "status": 400,
                                            "title": "Error en los argumentos",
                                            "type": "gestorpeliculas.com/error/argumento-erroneo"
                                        }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Director no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                                {
                                                     "detail": "No se ha encontrado al director con id 3",
                                                     "instance": "/api/v1/director/3",
                                                     "status": 404,
                                                     "title": "Entidad no encontrada",
                                                     "type": "gestorpeliculas.com/error/no-encontrado"
                                                }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Argumento incorrecto en el director",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                               "detail": "El director no puede ser menor de edad",
                                               "instance": "/api/v1/director",
                                               "status": 400,
                                               "title": "Error en el atributo",
                                               "type": "gestorpeliculas.com/error/argumento-erroneo" 
                                        }
                                    """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<DirectorResponseDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo del dto",content = @Content(schema = @Schema(implementation = DirectorRequestDTO.class),
                    mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "nombre":"Pedro Almodovar",
                        "anioNacimiento":1965
                    }
                    """)
                )
            )
            @RequestBody DirectorRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(DirectorResponseDTO.of(directorService.create(dto)));
    }

    @Operation(summary = "Editar un director")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Director editado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DirectorResponseDTO.class)),
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
            @ApiResponse(responseCode = "400", description = "Error al editar la película",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                            "detail": "Falta el campo del nombre del director",
                                            "instance": "/api/v1/director",
                                            "status": 400,
                                            "title": "Error en los argumentos",
                                            "type": "gestorpeliculas.com/error/argumento-erroneo"
                                        }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Director no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                                {
                                                     "detail": "No se ha encontrado al director con id 3",
                                                     "instance": "/api/v1/director/3",
                                                     "status": 404,
                                                     "title": "Entidad no encontrada",
                                                     "type": "gestorpeliculas.com/error/no-encontrado"
                                                }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Argumento incorrecto en el director",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                           "detail": "El director no puede ser menor de edad",
                                           "instance": "/api/v1/director",
                                           "status": 400,
                                           "title": "Error en el atributo",
                                           "type": "gestorpeliculas.com/error/argumento-erroneo"  
                                        }
                                    """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<DirectorResponseDTO> edit(
            @Parameter(description = "Identificador del director",example = "1" )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo del dto",content = @Content(schema = @Schema(implementation = DirectorRequestDTO.class),
                    mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "nombre":"Pedro Almodovar",
                        "anioNacimiento":1965
                    }
                    """)
                )
            )
            @RequestBody DirectorRequestDTO dto){
        return ResponseEntity.ok(DirectorResponseDTO.of(directorService.edit(id,dto)));
    }


    @Operation(summary = "Eliminar director")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Director eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Director no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                        "detail": "No se ha encontrado al director con id 2",
                        "instance": "/api/v1/director",
                        "status": 404,
                        "title": "Entidad no encontrada",
                        "type": "gestorpeliculas.com/error/no-encontrado"
                    }
                """
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        directorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
