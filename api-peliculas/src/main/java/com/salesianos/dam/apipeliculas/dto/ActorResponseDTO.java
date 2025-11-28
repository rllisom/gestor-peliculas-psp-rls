package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Actor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.stream.Collectors;

@Schema(description = "Muestra los atributos de los actores")
public record ActorResponseDTO(
        @Schema(description = "Identificador del actor", example = "1")
        Long id,
        @Schema(description = "Nombre del actor", example = "Antonio Banderas")
        String nombre,
        @Schema(description = "Lista de películas que pertenecen al actor", example = """
                 {
                    "id": 1,
                    "titulo": "El viaje"
                    }
                """  )
        Set<PeliculaSimpleResponseDTO> peliculas
) {
    public static ActorResponseDTO of(Actor a){
        return new ActorResponseDTO(
                a.getId(),
                a.getNombre(),
                a.getPeliculas().stream().map(PeliculaSimpleResponseDTO::of).collect(Collectors.toUnmodifiableSet())
        );
    }
}
