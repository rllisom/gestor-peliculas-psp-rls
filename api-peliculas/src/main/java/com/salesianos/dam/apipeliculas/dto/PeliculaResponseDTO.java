package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Pelicula;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Schema(description = "Datos de una película")
public record PeliculaResponseDTO(
        @Schema(description = "Identificador de la película", example = "1")
        Long id,
        @Schema(description = "Título de la película", example = "El viaje")
        String titulo,
        @Schema(description = "Fecha de estreno de la película", example = "2005-04-23")
        LocalDate fechaEstreno,
        @Schema(description = "Género de la película", example = "Drama")
        String genero,
        @Schema(description = "Simplificación de los datos de un director a mostrar", example = """
                {
                    "id": 1,
                    "nombre": "Pedro Almodovar"
                }
                """)
        DirectorSimpleResponseDTO director,
        @Schema(description = "Listado de los actores que pertenecen a una película", example = """
                [
                    {
                        "id": 1,
                        "nombre": "Antonio Banderas"
                    }
                ]
                """)
        Set<ActorSimpleResponseDTO> actores
) {
    public static PeliculaResponseDTO of(Pelicula p){
        return new PeliculaResponseDTO(
                p.getId(),
                p.getTitulo(),
                p.getFechaEstreno(),
                p.getGenero(),
                DirectorSimpleResponseDTO.of(p.getDirector()),
                p.getActores().stream().map(ActorSimpleResponseDTO::of).collect(Collectors.toUnmodifiableSet())
        );
    }
}
