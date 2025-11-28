package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Actor;
import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.model.Pelicula;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Set;

@Schema(description = "Cuerpo del dto necesario para crear o editar una película")
public record PeliculaRequestDTO(
        @Schema(description = "Título de la película", example = "El viaje")
        String titulo,
        @Schema(description = "Género de la película", example = "Drama")
       String genero,
        @Schema(description = "Fecha de estreno de la película", example = "2005-04-23")
        LocalDate fechaEstreno,
       @Schema(description = "Identificador del director que hae la película", example = "1")
        Long dir_id
) {
    public Pelicula toEntity(){
        return Pelicula.builder()
                .titulo(this.titulo)
                .genero(this.genero)
                .fechaEstreno(this.fechaEstreno)
                .build();
    }
}
