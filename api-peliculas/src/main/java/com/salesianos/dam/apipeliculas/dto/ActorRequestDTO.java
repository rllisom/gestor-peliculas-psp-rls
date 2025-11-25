package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Actor;
import com.salesianos.dam.apipeliculas.model.Pelicula;

import java.util.Set;

public record ActorRequestDTO(
        String nombre
) {
    public Actor toEntity(Set<Pelicula> peliculas){
        return Actor.builder()
                .nombre(this.nombre)
                .build();
    }
}
