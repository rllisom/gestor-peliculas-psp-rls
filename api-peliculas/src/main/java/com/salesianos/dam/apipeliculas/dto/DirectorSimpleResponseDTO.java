package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Director;

public record DirectorSimpleResponseDTO(
        Long id,
        String nombre
) {
    public static DirectorSimpleResponseDTO of(Director d){
        return new DirectorSimpleResponseDTO(
                d.getId(),
                d.getNombre()
        );
    }
}
