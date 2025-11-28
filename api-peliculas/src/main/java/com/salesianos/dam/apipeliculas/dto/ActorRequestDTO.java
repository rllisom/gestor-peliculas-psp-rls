package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Actor;
import com.salesianos.dam.apipeliculas.model.Pelicula;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Cuerpo del request necesario para crear o editar a un actor")
public record ActorRequestDTO(
        @Schema(description = "Nombre del actor", example = "Antonio Banderas")
        String nombre
) {
    public Actor toEntity(){
        return Actor.builder()
                .nombre(this.nombre)
                .build();
    }
}
