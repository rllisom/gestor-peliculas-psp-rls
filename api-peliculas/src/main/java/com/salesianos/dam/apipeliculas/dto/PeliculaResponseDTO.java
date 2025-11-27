package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Pelicula;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record PeliculaResponseDTO(
        Long id,
        String titulo,
        LocalDate fechaEstreno,
        String genero,
        DirectorSimpleResponseDTO director,
        Set<ActorSimpleResponseDTO> actores
) {
    public static PeliculaResponseDTO of(Pelicula p){
        return new PeliculaResponseDTO(
                p.getId(),
                p.getTitulo(),
                p.getFechaEstreno(),
                p.getGenero(),
                DirectorSimpleResponseDTO.of(p.getDirector()),
                p.getActores().stream().map(ActorSimpleResponseDTO::of).collect(Collectors.toUnmodifiableSet())
        );
    }
}
