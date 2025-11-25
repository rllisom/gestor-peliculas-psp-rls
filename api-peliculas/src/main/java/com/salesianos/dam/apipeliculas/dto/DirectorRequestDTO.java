package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.model.Pelicula;

import java.util.Set;

public record DirectorRequestDTO(
        String nombre,
        int anioNacimiento
) {

    public Director toEntity(){
        return  Director.builder()
                .nombre(this.nombre)
                .anioNacimiento(this.anioNacimiento)
                .build();
    }
}
