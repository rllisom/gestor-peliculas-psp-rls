package com.salesianos.dam.apipeliculas.dto;

import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.model.Pelicula;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.stream.Collectors;

@Schema(description = "Datos de un director")
public record DirectorResponseDTO(
        @Schema(description = "Identificador del director", example = "1")
        Long id,
        @Schema(description = "Nombre del director", example = "Pedro Almodovar")
        String nombre,
        @Schema(description = "Año de nacimiento del director", example = "1965")
        int anioNacimiento,
        @Schema(description = "Lista de películas pertenecientes al director", example = """
                 {
                    "id": 1,
                    "titulo": "El viaje"
                 }
                """)
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
