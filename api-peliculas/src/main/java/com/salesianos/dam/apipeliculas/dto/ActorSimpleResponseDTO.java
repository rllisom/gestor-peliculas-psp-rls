package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Actor;

public record ActorSimpleResponseDTO(
        Long id,
        String nombre
) {
    public static ActorSimpleResponseDTO of(Actor a){
        return new ActorSimpleResponseDTO(
                a.getId(),
                a.getNombre()
        );
    }
}
