package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Pelicula;

public record PeliculaSimpleResponseDTO(
        Long id,
        String titulo
) {
    public  static PeliculaSimpleResponseDTO of(Pelicula pelicula){
        return new PeliculaSimpleResponseDTO(
                pelicula.getId(),
                pelicula.getTitulo()
        );
    }
}
