package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Actor;

import java.util.Set;
import java.util.stream.Collectors;

public record ActorResponseDTO(
        Long id,
        String nombre,
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
