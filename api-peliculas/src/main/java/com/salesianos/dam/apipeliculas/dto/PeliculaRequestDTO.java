package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Actor;
import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.model.Pelicula;

import java.time.LocalDate;
import java.util.Set;

public record PeliculaRequestDTO(
       String titulo,
       String genero,
       LocalDate fechaEstreno,
       Long dir_id,
       Long actor_id
) {
    public Pelicula toEntity(Director director){
        return Pelicula.builder()
                .titulo(this.titulo)
                .genero(this.genero)
                .fechaEstreno(this.fechaEstreno)
                .director(director)
                .build();
    }
}
