package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.model.Pelicula;
import org.springframework.util.StringUtils;

import java.util.Set;

public record DirectorRequestDTO(
        String nombre,
        Integer anioNacimiento
) {

    public Director toEntity(){
        return  Director.builder()
                .nombre(this.nombre)
                .anioNacimiento(this.anioNacimiento)
                .build();
    }

    public static void validarDTO(DirectorRequestDTO dto){
        if(!StringUtils.hasText(dto.nombre())) throw new IllegalArgumentException("Falta el campo del nombre del director");

        if(dto.anioNacimiento() == null)
            throw new IllegalArgumentException("Falta el campo del año de nacimiento del director");
    }
}
