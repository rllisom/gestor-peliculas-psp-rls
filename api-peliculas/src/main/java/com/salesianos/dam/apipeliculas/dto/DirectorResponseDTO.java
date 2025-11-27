package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.model.Pelicula;

import java.util.Set;
import java.util.stream.Collectors;

public record DirectorResponseDTO(
        Long id,
        String nombre,
        int anioNacimiento,
        Set<PeliculaSimpleResponseDTO> peliculas
) {
    public static DirectorResponseDTO of(Director d){
        return new DirectorResponseDTO(
                d.getId(),
                d.getNombre(),
                d.getAnioNacimiento(),
                d.getPeliculas().stream().map(PeliculaSimpleResponseDTO::of).collect(Collectors.toUnmodifiableSet())
        );
    }

}
