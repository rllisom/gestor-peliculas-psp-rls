package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Actor;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Simplificación de datos de un actor")
public record ActorSimpleResponseDTO(
        @Schema(description = "Identificador del actor", example = "1")
        Long id,
        @Schema(description = "Nombre del actor", example = "Antonio Banderas")
        String nombre
) {
    public static ActorSimpleResponseDTO of(Actor a){
        return new ActorSimpleResponseDTO(
                a.getId(),
                a.getNombre()
        );
    }
}
