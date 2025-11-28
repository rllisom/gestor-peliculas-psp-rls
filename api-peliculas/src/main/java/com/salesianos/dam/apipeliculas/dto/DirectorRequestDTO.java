package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.model.Pelicula;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.util.StringUtils;

import java.util.Set;

@Schema(description = "Cuerpo del dto necesario para crear o editar un director")
public record DirectorRequestDTO(
        @Schema(description = "Nombre del director", example = "Pedro Almodovar")
        String nombre,
        @Schema(description = "Año de nacimiento del director", example = "1965")
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
