package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Director;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Simplificación de datos de un director")
public record DirectorSimpleResponseDTO(
        @Schema(description = "Identificador del director", example = "1")
        Long id,
        @Schema(description = "Nombre del director", example = "Pedro Almodovar")
        String nombre
) {
    public static DirectorSimpleResponseDTO of(Director d){
        return new DirectorSimpleResponseDTO(
                d.getId(),
                d.getNombre()
        );
    }
}
