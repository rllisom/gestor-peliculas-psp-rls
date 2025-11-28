package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Pelicula;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Simplificación de datos de una película")
public record PeliculaSimpleResponseDTO(
        @Schema(description = "Identificador de la película", example = "1")
        Long id,
        @Schema(description = "Título de la película", example = "El viaje")
        String titulo
) {
    public  static PeliculaSimpleResponseDTO of(Pelicula pelicula){
        return new PeliculaSimpleResponseDTO(
                pelicula.getId(),
                pelicula.getTitulo()
        );
    }
}
