package com.salesianos.dam.apipeliculas.controller;

import com.salesianos.dam.apipeliculas.dto.PeliculaRequestDTO;
import com.salesianos.dam.apipeliculas.dto.PeliculaResponseDTO;
import com.salesianos.dam.apipeliculas.model.Pelicula;
import com.salesianos.dam.apipeliculas.service.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Gestión de películas", description = "Peticiones CRUD para la gestión de películas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @Operation(summary = "Obtener toda las películas")
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
    @Operation(summary = "Obtener toda las películas")
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
    public PeliculaResponseDTO getById(@PathVariable Long id){
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
            @ApiResponse(responseCode = "400", description = "Error al crear la película",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                        {
                                             "detail": "Failed to read request",
                                             "instance": "/api/v1/peliculas",
                                             "status": 400,
                                             "title": "Bad Request"
                                        }
                                    """
                            )
                    )
            ),
            @ApiResponse
    })
    @PostMapping
    public ResponseEntity<PeliculaResponseDTO> create(@RequestBody PeliculaRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(PeliculaResponseDTO.of(peliculaService.create(dto)));
    }

    @PostMapping("/{peliculaId}/actores/{actorId}")
    public ResponseEntity<PeliculaResponseDTO> ActorToPelicula(@PathVariable Long peliculaId, @PathVariable Long actorId){
        return ResponseEntity.status(HttpStatus.CREATED).body(PeliculaResponseDTO.of(peliculaService.editPeliculaActor(peliculaId,actorId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaResponseDTO> edit(@PathVariable Long id, @RequestBody PeliculaRequestDTO dto){
        return ResponseEntity.ok(PeliculaResponseDTO.of(peliculaService.edit(id,dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        peliculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
